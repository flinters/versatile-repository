package jp.co.septeni_original.versatile.repository.impl

import scala.language.higherKinds

trait CRUDFeatureRepositoryOnJDBC[ID <: Long, Entity, Record, F[_]]
  extends BaseRepositoryOnJDBC[ID, Entity, Record, F]
    with ResolveFeatureRepositoryOnJDBC[ID, Entity, Record, F]
    with UpdateFeatureRepositoryOnJDBC[ID, Entity, Record, F]
    with DeleteFeatureRepositoryOnJDBC[ID, Record, F]
