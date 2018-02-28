package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.MonadError

trait BaseRepository[ID, Entity, F[_]] {
  def store(entity: Entity)(implicit me: MonadError[F, Throwable]): F[Unit]

  def existBy(id: ID)(implicit me: MonadError[F, Throwable]): F[Boolean]
}




