package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds

trait ResolveFeatureRepository[ID, Entity, F[_]] {
  def resolveBy(id: ID): F[Option[Entity]]
}
