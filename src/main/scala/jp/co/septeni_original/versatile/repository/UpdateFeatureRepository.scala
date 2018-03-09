package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.{Kleisli, MonadError}

trait UpdateFeatureRepository[F[_]] {
  type ID <: Long
  type Entity <: AbstractEntity[ID]
  type Ctx

  def update(entity: Entity)(implicit me: MonadError[F, Throwable]): Kleisli[F, Ctx, Unit]
}
