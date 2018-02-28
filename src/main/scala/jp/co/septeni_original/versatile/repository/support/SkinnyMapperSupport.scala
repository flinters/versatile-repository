package jp.co.septeni_original.versatile.repository.support

trait SkinnyMapperSupport[Record] {
  def mapValuesPhrase(row: Record): Seq[(Symbol, Any)]
}
