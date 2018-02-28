package jp.co.septeni_original.versatile.repository.impl

import jp.co.septeni_original.versatile.repository.UpdateFeatureRepository
import jp.co.septeni_original.versatile.repository.impl.support.mysql.MysqlErrorSupportSyntax
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import scalikejdbc.AutoSession
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.MonadError

trait UpdateFeatureRepositoryOnJDBC[ID <: Long, Entity, Record, F[_]] extends UpdateFeatureRepository[Entity, F]
  with MysqlErrorSupportSyntax {

  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def update(entity: Entity)(implicit me: MonadError[F, Throwable]): F[Unit] = {
    try {
      me.point {
        val attributes = dao.mapValuesPhrase(convertRecord(entity))
        dao.updateById(convertRecordId(entity)).withAttributes(attributes: _*)(AutoSession) //TODO
        ()
      }.adjustDuplicateOrNoReferenceException(me)
    } catch {
      case NonFatal(e) => me.raiseError(e)
    }
  }

  protected def convertRecord(entity: Entity): Record

  protected def convertRecordId(entity: Entity): ID

}
