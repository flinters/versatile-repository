package jp.co.septeni_original.versatile.repository.impl.skinny.future

import jp.co.septeni_original.versatile.repository.impl.skinny.{BaseRepositoryOnJDBC, UpdateFeatureRepositoryOnJDBC}
import jp.co.septeni_original.versatile.repository.impl.support.mysql.EntityDuplicateException
import org.scalatest.fixture
import scalikejdbc.scalatest.AsyncAutoRollback

import scala.concurrent.Future

trait FutureUpdateFeatureRepositoryOnJDBCSpec {
  self: fixture.AsyncFlatSpec with AsyncAutoRollback =>

  protected val repository: UpdateFeatureRepositoryOnJDBC[Future] with BaseRepositoryOnJDBC[Future]

  protected def createTestEntity(id: Long): repository.Entity

  protected def updateEntity(entity: repository.Entity): repository.Entity

  protected def generateId: repository.ID

  import scalaz.Scalaz._

  "update(entity)" should "success" in { implicit s =>
    val id = generateId
    val entity = createTestEntity(id)
    (for {
      _ <- repository.store(entity)
      res <- repository.update(updateEntity(entity))
    } yield res).run(s)
      .map(x => assert(x == ()))
  }

  it should "success: if it does not exist, not entry and no effect" in { implicit s =>
    val id = generateId
    val entity = createTestEntity(id)
    repository.update(entity).run(s).flatMap(res =>
      assert(res == ())
    )
  }

  /**
    * When you can skip duplicate test, you implement to return None.
    */
  protected def duplicatedEntity(old: repository.Entity, id: Option[repository.ID]): Option[repository.Entity]

  it should "fail in duplicate entity" in { implicit s =>
    val entity = createTestEntity(generateId)
    val entity2 = createTestEntity(generateId)
    duplicatedEntity(entity, Some(entity2.id)) match {
      case Some(d) =>
        recoverToSucceededIf[EntityDuplicateException] {
          (for {
            _ <- repository.store(entity)
            _ <- repository.store(entity2)
            res <- repository.update(d)
          } yield res).run(s)
        }
      case None => cancel()
    }
  }

}
