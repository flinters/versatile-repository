package jp.co.septeni_original.versatile.repository.impl

import jp.co.septeni_original.versatile.repository.DeleteFeatureRepository
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.MonadError

trait DeleteFeatureRepositoryOnJDBC[ID <: Long, Record, F[_]] extends DeleteFeatureRepository[ID, F] {

  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def deleteBy(id: ID)(implicit me: MonadError[F, Throwable]): F[Unit] = {
    try {
      me.point(dao.deleteById(id))
    } catch {
      case NonFatal(e) => me.raiseError(e)
    }
  }

}
