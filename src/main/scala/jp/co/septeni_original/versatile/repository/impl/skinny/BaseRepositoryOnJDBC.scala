
package jp.co.septeni_original.versatile.repository.impl.skinny

import jp.co.septeni_original.versatile.repository.impl.support.EntityConverter
import jp.co.septeni_original.versatile.repository.impl.support.mysql.MysqlErrorSupportSyntax
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import jp.co.septeni_original.versatile.repository.{AbstractEntity, BaseRepository}
import scalikejdbc.DBSession
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.{Kleisli, MonadError}

trait BaseRepositoryOnJDBC[F[_]] extends BaseRepository[F]
  with EntityConverter
  with MysqlErrorSupportSyntax {

  type ID <: Long
  type Entity <: AbstractEntity[ID]
  type Record
  type Ctx = DBSession
  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def store(entity: Entity)(implicit me: MonadError[F, Throwable]): DB[F, Unit] = {
    Kleisli { implicit s =>
      try {
        me.point {
          val attributes = dao.mapValuesPhrase(convert(entity))
          dao.createWithAttributes(attributes: _*)
          ()
        }.adjustDuplicateOrNoReferenceException(me)
      } catch {
        case NonFatal(e) => me.raiseError(e)
      }
    }
  }

  def existBy(id: ID)(implicit me: MonadError[F, Throwable]): DB[F, Boolean] = {
    Kleisli { s =>
      try {
        me.point(dao.findById(id)(s).isDefined)
      } catch {
        case NonFatal(e) => me.raiseError(e)
      }
    }
  }

}

