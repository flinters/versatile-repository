package jp.co.septeni_original.versatile.repository.impl

import jp.co.septeni_original.versatile.repository.ResolveFeatureRepository
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.MonadError

trait ResolveFeatureRepositoryOnJDBC[ID <: Long, Entity, Record, F[_]] extends ResolveFeatureRepository[ID, Entity, F] {

  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def resolveBy(id: ID)(implicit me: MonadError[F, Throwable]): F[Option[Entity]] = {
    try {
      me.point {
        dao.findById(id).map(convertEntity)
      }
    } catch {
      case NonFatal(e) => me.raiseError(e)
    }
  }

  protected def convertEntity(record: Record): Entity
}
