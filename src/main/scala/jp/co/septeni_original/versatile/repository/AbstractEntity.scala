package jp.co.septeni_original.versatile.repository

trait AbstractEntity[ID <: Long] {
  val id: ID
}
