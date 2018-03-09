package jp.co.septeni_original.versatile.repository.impl.skinny.future

import jp.co.septeni_original.versatile.repository.impl.skinny.{BaseRepositoryOnJDBC, DeleteFeatureRepositoryOnJDBC, ResolveFeatureRepositoryOnJDBC}
import org.scalatest.fixture
import scalikejdbc.DBSession
import scalikejdbc.scalatest.AsyncAutoRollback

import scala.concurrent.Future

trait FutureDeleteFeatureRepositoryOnJDBCSpec {
  self: fixture.AsyncFlatSpec with AsyncAutoRollback =>

  protected val repository: DeleteFeatureRepositoryOnJDBC[Future] with BaseRepositoryOnJDBC[Future]

  protected def createTestEntity(id: Long): repository.Entity

  protected def storeEntity(entity: repository.Entity)(implicit s: DBSession): Future[Unit]

  protected def generateId: repository.ID

  import scalaz.Scalaz._

  "deleteBy(id)" should "success" in { implicit s =>
    val id = generateId
    val entity = createTestEntity(id)
    for {
      _ <- storeEntity(entity)
      res <- repository.deleteBy(id).run(s)
    } yield assert(res == ())
  }

  it should "success: if it does not exist, no effect" in { implicit s =>
    val id = generateId
    repository.deleteBy(id).run(s).flatMap(res =>
      assert(res == ())
    )
  }

}
