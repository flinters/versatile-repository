/*
package jp.co.septeni_original.versatile.repository.impl.skinny.`try`

import jp.co.septeni_original.versatile.repository.impl.skinny.BaseRepositoryOnJDBC
import org.scalatest.FlatSpec
import scalikejdbc.{AutoSession, DBSession}

import scala.util.Try
import scalaz.Kleisli

trait TryBaseRepositoryOnJDBCSpec extends FlatSpec {

  protected val repository: BaseRepositoryOnJDBC[Kleisli[Try, DBSession, ?]]

  protected def createTestEntity(id: Long): repository.Entity

  protected def generateId: repository.ID

  "store(entity)" should "success" in {
    val entity = createTestEntity(generateId)
    repository.store(entity).run(AutoSession).map(res =>
      assert(res == ())
    )
  }

  "existBy(id)" should "success" in {
    val id = generateId
    val entity = createTestEntity(id)
    val s = AutoSession
    for {
      _ <- repository.store(entity).run(s)
      res <- repository.existBy(id).run(s)
    } yield assert(res)
  }

}
*/
