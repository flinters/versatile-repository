package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds

trait BaseRepository[ID, Entity, F[_]] {
  def store(entity: Entity): F[Unit]

  def existBy(id: ID): F[Boolean]
}




