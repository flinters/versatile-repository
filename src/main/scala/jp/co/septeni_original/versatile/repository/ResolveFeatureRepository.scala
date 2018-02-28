package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds
import scalaz.MonadError

trait ResolveFeatureRepository[ID, Entity, F[_]] {
  def resolveBy(id: ID)(implicit me: MonadError[F, Throwable]): F[Option[Entity]]
}
