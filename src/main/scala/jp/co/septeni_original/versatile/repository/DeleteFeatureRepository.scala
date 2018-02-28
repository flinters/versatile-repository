package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds

trait DeleteFeatureRepository[ID, F[_]] {
  def deleteBy(id: ID): F[Unit]
}
