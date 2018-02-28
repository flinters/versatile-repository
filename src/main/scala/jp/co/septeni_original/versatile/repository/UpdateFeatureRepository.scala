package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds

trait UpdateFeatureRepository[Entity, F[_]] {
  def update(entity: Entity): F[Unit]
}
