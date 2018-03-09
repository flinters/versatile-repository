package jp.co.septeni_original.versatile.repository.impl.skinny

import scalikejdbc.DBSession

import scala.language.higherKinds

trait CRUDFeatureRepositoryOnJDBC[F[_]]
  extends BaseRepositoryOnJDBC[F]
    with ResolveFeatureRepositoryOnJDBC[F]
    with UpdateFeatureRepositoryOnJDBC[F]
    with DeleteFeatureRepositoryOnJDBC[F] {

  override type Ctx = DBSession
}
