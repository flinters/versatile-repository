package jp.co.septeni_original.versatile.repository

import scala.language.higherKinds

trait CRUDFeatureRepository[F[_]]
  extends BaseRepository[F]
    with ResolveFeatureRepository[F]
    with UpdateFeatureRepository[F]
    with DeleteFeatureRepository[F]
