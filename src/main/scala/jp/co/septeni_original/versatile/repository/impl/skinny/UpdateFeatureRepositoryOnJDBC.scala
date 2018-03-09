package jp.co.septeni_original.versatile.repository.impl.skinny

import jp.co.septeni_original.versatile.repository.UpdateFeatureRepository
import jp.co.septeni_original.versatile.repository.impl.support.EntityConverter
import jp.co.septeni_original.versatile.repository.impl.support.mysql.MysqlErrorSupportSyntax
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import scalikejdbc.DBSession
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.{Kleisli, MonadError}

trait UpdateFeatureRepositoryOnJDBC[F[_]]
  extends UpdateFeatureRepository[F]
    with EntityConverter
    with MysqlErrorSupportSyntax {

  type Ctx = DBSession
  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def update(entity: Entity)(implicit me: MonadError[F, Throwable]): DB[F, Unit] = {
    Kleisli { implicit s =>
      try {
        me.point {
          val attributes = dao.mapValuesPhrase(convert(entity))
          dao.updateById(entity.id).withAttributes(attributes: _*)(s)
          ()
        }.adjustDuplicateOrNoReferenceException(me)
      } catch {
        case NonFatal(e) => {
          printf("Exception@@: " + e.getMessage)
          me.raiseError(e)
        }
      }
    }
  }

}
