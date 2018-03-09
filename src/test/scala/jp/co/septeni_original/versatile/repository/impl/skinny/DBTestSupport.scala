package jp.co.septeni_original.versatile.repository.impl.skinny

import org.scalatest.fixture
import scalikejdbc.config.DBs
import scalikejdbc.scalatest.AsyncAutoRollback

private[skinny] trait DBTestSupport extends fixture.AsyncFlatSpec with AsyncAutoRollback {
  DBs.setupAll()
}
