package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds

trait CRUDFeatureRepository[ID, Entity, F[_]]
  extends BaseRepository[ID, Entity, F]
    with ResolveFeatureRepository[ID, Entity, F]
    with UpdateFeatureRepository[Entity, F]
    with DeleteFeatureRepository[ID, F]
