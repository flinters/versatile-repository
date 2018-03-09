package jp.co.septeni_original.versatile.repository.impl

import scalikejdbc.DBSession

import scalaz.Kleisli
import scala.language.higherKinds

package object skinny {
  type DB[F[_], A] = Kleisli[F, DBSession, A]
}
