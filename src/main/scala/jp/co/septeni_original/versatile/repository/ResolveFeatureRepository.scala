package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.{Kleisli, MonadError}

trait ResolveFeatureRepository[F[_]] {

  type ID <: Long
  type Entity <: AbstractEntity[ID]
  type Ctx

  def resolveBy(id: ID)(implicit me: MonadError[F, Throwable]): Kleisli[F, Ctx, Option[Entity]]
}
