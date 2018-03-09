package jp.co.septeni_original.versatile.repository.impl.skinny

import jp.co.septeni_original.versatile.repository.DeleteFeatureRepository
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import scalikejdbc.DBSession
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.{Kleisli, MonadError}

trait DeleteFeatureRepositoryOnJDBC[F[_]]
  extends DeleteFeatureRepository[F] {

  type Ctx = DBSession
  type Record
  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def deleteBy(id: ID)(implicit me: MonadError[F, Throwable]): DB[F, Unit] = {
    Kleisli { implicit s =>
      try {
        me.point(dao.deleteById(id))
      } catch {
        case NonFatal(e) => me.raiseError(e)
      }
    }
  }

}
