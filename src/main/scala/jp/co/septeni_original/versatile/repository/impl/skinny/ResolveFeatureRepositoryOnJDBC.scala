package jp.co.septeni_original.versatile.repository.impl.skinny

import jp.co.septeni_original.versatile.repository.ResolveFeatureRepository
import jp.co.septeni_original.versatile.repository.impl.support.RecordConverter
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import scalikejdbc.DBSession
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.{Kleisli, MonadError}

trait ResolveFeatureRepositoryOnJDBC[F[_]]
  extends ResolveFeatureRepository[F]
    with RecordConverter {

  type Ctx = DBSession

  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def resolveBy(id: ID)(implicit me: MonadError[F, Throwable]): DB[F, Option[Entity]] = {
    Kleisli { s =>
      try {
        me.point(dao.findById(id)(s).map(convert))
      } catch {
        case NonFatal(e) => me.raiseError(e)
      }
    }
  }

}
