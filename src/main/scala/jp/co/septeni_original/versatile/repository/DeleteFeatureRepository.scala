package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.{Kleisli, MonadError}

trait DeleteFeatureRepository[F[_]] {
  type ID <: Long
  type Ctx

  def deleteBy(id: ID)(implicit me: MonadError[F, Throwable]): Kleisli[F, Ctx, Unit]
}
