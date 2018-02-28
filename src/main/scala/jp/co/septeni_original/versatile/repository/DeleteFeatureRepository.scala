package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.MonadError

trait DeleteFeatureRepository[ID, F[_]] {
  def deleteBy(id: ID)(implicit me: MonadError[F, Throwable]): F[Unit]
}
