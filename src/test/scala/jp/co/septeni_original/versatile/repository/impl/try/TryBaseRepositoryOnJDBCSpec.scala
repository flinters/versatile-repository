package jp.co.septeni_original.versatile.repository.impl.`try`

import jp.co.septeni_original.versatile.repository.impl.BaseRepositoryOnJDBC
import org.scalatest.FlatSpec

import scala.util.Try

trait TryBaseRepositoryOnJDBCSpec[ID <: Long, Entity, Record] extends FlatSpec {

  protected val repository: BaseRepositoryOnJDBC[ID, Entity, Record, Try]

  protected def createTestEntity(id: Long): Entity

  protected def generateId: ID

  "store(entity)" should "success" in {
    val entity = createTestEntity(generateId)
    repository.store(entity).map(res =>
      assert(res == ())
    )
  }

  "existBy(id)" should "success" in {
    val id = generateId
    val entity = createTestEntity(id)
    for {
      _ <- repository.store(entity)
      res <- repository.existBy(id)
    } yield assert(res)
  }

}
