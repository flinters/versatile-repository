package jp.co.septeni_original.versatile.repository.impl.future

import jp.co.septeni_original.versatile.repository.impl.ResolveFeatureRepositoryOnJDBC
import org.scalatest.fixture
import scalikejdbc.scalatest.AsyncAutoRollback

import scala.concurrent.Future

trait FutureResolveRepositoryOnJDBCSpec[ID <: Long, Entity, Record] extends fixture.AsyncFlatSpec with AsyncAutoRollback {

  protected val repository: ResolveFeatureRepositoryOnJDBC[ID, Entity, Record, Future]

  protected def createTestEntity(id: Long): Entity

  protected def storeEntity(entity: Entity): Future[Unit]

  protected def generateId: ID

  "resolveBy(id)" should "success" in { implicit s =>
    val id = generateId
    val entity = createTestEntity(id)
    for {
      _ <- storeEntity(entity)
      res <- repository.resolveBy(id)
    } yield assert(res.contains(entity))
  }

  "resolveBy(id)" should "response is None" in { implicit s =>
    val id = generateId
    repository.resolveBy(id).flatMap(res =>
      assert(res.isEmpty)
    )
  }

}
