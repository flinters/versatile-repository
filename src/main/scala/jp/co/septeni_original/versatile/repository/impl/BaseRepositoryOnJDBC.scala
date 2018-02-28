package jp.co.septeni_original.versatile.repository.impl

import jp.co.septeni_original.versatile.repository.BaseRepository
import jp.co.septeni_original.versatile.repository.impl.support.mysql.MysqlErrorSupportSyntax
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

import scala.language.higherKinds
import scala.util.control.NonFatal
import scalaz.MonadError

trait BaseRepositoryOnJDBC[ID <: Long, Entity, Record, F[_]] extends BaseRepository[ID, Entity, F]
  with MysqlErrorSupportSyntax {

  type DAO <: SkinnyCRUDMapper[Record] with SkinnyMapperSupport[Record]
  protected val dao: DAO

  def store(entity: Entity)(implicit me: MonadError[F, Throwable]): F[Unit] = {
    try {
      me.point {
        val attributes = dao.mapValuesPhrase(convertRecord(entity))
        dao.createWithAttributes(attributes: _*)(AutoSession) //TODO
        ()
      }.adjustDuplicateOrNoReferenceException(me)
    } catch {
      case NonFatal(e) => me.raiseError(e)
    }
  }

  def existBy(id: ID)(implicit me: MonadError[F, Throwable]): F[Boolean] = {
    try {
      me.point(dao.findById(id).isDefined)
    } catch {
      case NonFatal(e) => me.raiseError(e)
    }
  }

  protected def convertRecord(entity: Entity): Record
}
