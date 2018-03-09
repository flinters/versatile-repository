package jp.co.septeni_original.versatile.repository.impl.support

import jp.co.septeni_original.versatile.repository.AbstractEntity

trait EntityConverter {

  type ID <: Long
  type Entity <: AbstractEntity[ID]
  type Record

  def convert(entity: Entity): Record

}

trait RecordConverter {

  type ID <: Long
  type Entity <: AbstractEntity[ID]
  type Record

  def convert(record: Record): Entity
}

/*
trait Converter[E <: Product, R <: Product] {

  def convertRecord(entity: E): R

  /*
    val genRecord = Generic[Record]
    val genEntity = Generic[E]
    genRecord.from(genEntity.to(entity))
    */
  //def convertEntity(record: Record): E
}

object Converter {

  def convert[E <: Product, R <: Product](entity: E)(implicit c: Converter[E, R]): R = {
    c.convertRecord(entity)
  }

  implicit def ConverterImpl[
  E <: Product,
  R <: Product,
  ](
    implicit e: Generic.Aux[E, R],
    //r: Generic.Aux[R, HList],
  ): Converter[E, R] = new Converter[E, R] {
    def convertRecord(entity: E): R = {
      val g = e.to(entity)
      //r.from(g)
      g
    }
  }

}
}
*/
