package jp.co.septeni_original.versatile.repository.impl.skinny.future

import jp.co.septeni_original.versatile.repository.impl.skinny.BaseRepositoryOnJDBC
import jp.co.septeni_original.versatile.repository.impl.support.mysql.EntityDuplicateException
import org.scalatest.fixture
import scalikejdbc.scalatest.AsyncAutoRollback

import scala.concurrent.Future

trait FutureBaseRepositoryOnJDBCSpec {
  self: fixture.AsyncFlatSpec with AsyncAutoRollback =>

  protected val repository: BaseRepositoryOnJDBC[Future]

  protected def createTestEntity(id: Long): repository.Entity

  protected def generateId: repository.ID

  protected def duplicatedEntity(old: repository.Entity, id: Option[repository.ID] = None): Option[repository.Entity]

  import scalaz.Scalaz._

  "store(entity)" should "success" in { implicit s =>
    val entity = createTestEntity(generateId)
    repository.store(entity).run(s).flatMap(res =>
      assert(res == ())
    )
  }

  it should "fail in duplicate entity" in { implicit s =>
    val entity = createTestEntity(generateId)
    duplicatedEntity(entity) match {
      case Some(d) =>
        recoverToSucceededIf[EntityDuplicateException] {
          (for {
            _ <- repository.store(entity)
            res <- repository.store(d)
          } yield res).run(s)
            .map(x => assert(x == ()))
        }
      case None => cancel()
    }
  }

  "existBy(id)" should "success" in { implicit s =>
    val id = generateId
    val entity = createTestEntity(id)
    (for {
      _ <- repository.store(entity)
      res <- repository.existBy(id)
    } yield res).run(s).map(b => assert(b))
  }

}
