package jp.co.septeni_original.versatile.repository.impl.future

import jp.co.septeni_original.versatile.repository.impl.BaseRepositoryOnJDBC
import org.scalatest.fixture
import scalikejdbc.scalatest.AsyncAutoRollback

import scala.concurrent.Future

trait FutureBaseRepositoryOnJDBCSpec[ID <: Long, Entity, Record] extends fixture.AsyncFlatSpec with AsyncAutoRollback {

  protected val repository: BaseRepositoryOnJDBC[ID, Entity, Record, Future]

  protected def createTestEntity(id: Long): Entity

  protected def generateId: ID

  "store(entity)" should "success" in { implicit s =>
    val entity = createTestEntity(generateId)
    repository.store(entity).flatMap { res =>
      assert(res == ())
    }
  }

  "existBy(id)" should "success" in { implicit s =>
    val id = generateId
    val entity = createTestEntity(id)
    for {
      _ <- repository.store(entity)
      res <- repository.existBy(id)
    } yield assert(res)
  }

}
