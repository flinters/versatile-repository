package jp.co.septeni_original.versatile.repository.impl.support.mysql

import com.mysql.jdbc.MysqlErrorNumbers
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException

import scala.util.control.NonFatal
import scalaz.MonadError
import scala.language.higherKinds

trait MysqlErrorSupportSyntax {

  implicit class MysqlErrorSupportOps[F[_]](func: F[Unit]) {
    def adjustDuplicateOrNoReferenceException(me: MonadError[F, Throwable]): F[Unit] = {
      me.handleError(func) {
        case e: MySQLIntegrityConstraintViolationException =>
          e.getErrorCode match {
            case MysqlErrorNumbers.ER_DUP_ENTRY =>
              me.raiseError(new EntityDuplicateException(e.getMessage, Some(e)))
            case MysqlErrorNumbers.ER_NO_REFERENCED_ROW_2 =>
              me.raiseError(new InvalidReferenceException(e.getMessage, Some(e)))
            case _ => me.raiseError(e)
          }
        case NonFatal(e) => me.raiseError(e)
      }
    }
  }

}
