package jp.co.septeni_original.versatile.repository.impl.support.mysql

final class EntityDuplicateException(message: String, cause: Option[Throwable]) extends Exception(message, cause.orNull)
