package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.MonadError

trait UpdateFeatureRepository[Entity, F[_]] {
  def update(entity: Entity)(implicit me: MonadError[F, Throwable]): F[Unit]
}
