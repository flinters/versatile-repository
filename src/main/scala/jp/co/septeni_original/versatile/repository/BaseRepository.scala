package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.{Kleisli, MonadError}

trait BaseRepository[F[_]] {

  type ID <: Long
  type Entity <: AbstractEntity[ID]
  type Ctx

  def store(entity: Entity)(implicit me: MonadError[F, Throwable]): Kleisli[F, Ctx, Unit]

  def existBy(id: ID)(implicit me: MonadError[F, Throwable]): Kleisli[F, Ctx, Boolean]
}




