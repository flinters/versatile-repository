package jp.co.septeni_original.versatile.repository.support

import skinny.orm.SkinnyCRUDMapper

trait SkinnyMapperSupport[Record] extends SkinnyCRUDMapper[Record] {

  override def useAutoIncrementPrimaryKey: Boolean = false

  override def useExternalIdGenerator: Boolean = false

  def mapValuesPhrase(row: Record): Seq[(Symbol, Any)]
}
