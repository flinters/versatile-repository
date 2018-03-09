package jp.co.septeni_original.versatile.repository.impl.skinny.future

import jp.co.septeni_original.versatile.repository.impl.skinny.ResolveFeatureRepositoryOnJDBC
import org.scalatest._
import scalikejdbc.DBSession
import scalikejdbc.scalatest.AsyncAutoRollback

import scala.concurrent.Future

trait FutureResolveFeatureRepositoryOnJDBCSpec {
  self: fixture.AsyncFlatSpec with AsyncAutoRollback =>

  protected val repository: ResolveFeatureRepositoryOnJDBC[Future]

  protected def createTestEntity(id: Long): repository.Entity

  protected def storeEntity(entity: repository.Entity)(implicit s: DBSession): Future[Unit]

  protected def generateId: repository.ID

  import scalaz.Scalaz._

  "resolveBy(id)" should "success: response is Some(entity)" in { implicit s =>
    val id = generateId
    val entity = createTestEntity(id)
    for {
      _ <- storeEntity(entity)
      res <- repository.resolveBy(id).run(s)
    } yield assert(res.contains(entity))
  }

  it should "success: response is None" in { implicit s =>
    val id = generateId
    repository.resolveBy(id).run(s).flatMap(res =>
      assert(res.isEmpty)
    )
  }

}
