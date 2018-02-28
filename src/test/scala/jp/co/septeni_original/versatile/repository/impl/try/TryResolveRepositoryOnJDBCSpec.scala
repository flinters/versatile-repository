package jp.co.septeni_original.versatile.repository.impl.`try`

import jp.co.septeni_original.versatile.repository.impl.ResolveFeatureRepositoryOnJDBC
import org.scalatest.FlatSpec

import scala.concurrent.Future
import scala.util.Try

trait TryResolveRepositoryOnJDBCSpec[ID <: Long, Entity, Record] extends FlatSpec {

  protected val repository: ResolveFeatureRepositoryOnJDBC[ID, Entity, Record, Try]

  protected def createTestEntity(id: Long): Entity

  protected def storeEntity(entity: Entity): Future[Unit]

  protected def generateId: ID

  "resolveBy(id)" should "success" in {
    val id = generateId
    val entity = createTestEntity(id)
    for {
      _ <- storeEntity(entity)
      res <- repository.resolveBy(id)
    } yield assert(res.contains(entity))
  }

  "resolveBy(id)" should "response is None" in {
    val id = generateId
    repository.resolveBy(id).map(res =>
      assert(res.isEmpty)
    )
  }
}
