package jp.co.septeni_original.versatile.repository.impl.skinny.example

import jp.co.septeni_original.versatile.repository.AbstractEntity
import jp.co.septeni_original.versatile.repository.impl.skinny._
import jp.co.septeni_original.versatile.repository.impl.skinny.future.{FutureBaseRepositoryOnJDBCSpec, FutureDeleteFeatureRepositoryOnJDBCSpec, FutureResolveFeatureRepositoryOnJDBCSpec, FutureUpdateFeatureRepositoryOnJDBCSpec}
import jp.co.septeni_original.versatile.repository.support.SkinnyMapperSupport
import scalikejdbc._
import skinny.orm.Alias

import scala.concurrent.Future
import scala.util.Random

final class EmployeeFutureRepositoryOnJDBCSpec
  extends DBTestSupport
    with FutureBaseRepositoryOnJDBCSpec
    with FutureResolveFeatureRepositoryOnJDBCSpec
    with FutureUpdateFeatureRepositoryOnJDBCSpec
    with FutureDeleteFeatureRepositoryOnJDBCSpec {

  case class Employee(
    id: Long,
    name: String,
    employeeNumber: String
  ) extends AbstractEntity[Long]

  case class EmployeeRecord(
    id: Long,
    name: String,
    employeeNumber: String
  )

  class EmployeeRecordSqlSyntaxSupport
    extends SkinnyMapperSupport[EmployeeRecord] {

    override val tableName = "employees"

    override def defaultAlias: Alias[EmployeeRecord] = createAlias("ey")

    override lazy val columns: Seq[String] = autoColumns[EmployeeRecord]()

    override def extract(rs: WrappedResultSet, n: ResultName[EmployeeRecord]): EmployeeRecord =
      autoConstruct(rs, n)

    override def mapValuesPhrase(row: EmployeeRecord): Seq[(Symbol, Any)] =
      Seq('id -> row.id, 'name -> row.name, 'employee_number -> row.employeeNumber)
  }

  class EmployeeRepositoryOnJDBC
    extends CRUDFeatureRepositoryOnJDBC[Future] {

    override type ID = Long
    override type Entity = Employee
    override type Ctx = DBSession
    override type Record = EmployeeRecord

    override type DAO = EmployeeRecordSqlSyntaxSupport
    val dao: EmployeeRecordSqlSyntaxSupport = new EmployeeRecordSqlSyntaxSupport

    def convert(record: EmployeeRecord): Employee = Employee(record.id, record.name, record.employeeNumber)

    def convert(entity: Employee): EmployeeRecord = EmployeeRecord(entity.id, entity.name, entity.employeeNumber)
  }

  override def fixture(implicit session: FixtureParam): Unit = {
    sql"""
     CREATE TABLE if not exists employees (
      id BIGINT UNSIGNED not null primary key,
      name varchar(64) not null,
      employee_number varchar(64) not null unique
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    """.execute().apply()
  }

  protected def storeEntity(entity: Employee)(implicit s: DBSession): Future[Unit] = {
    import scalaz.Scalaz._
    repository.store(entity).run(s)
  }

  protected val repository = new EmployeeRepositoryOnJDBC

  protected def createTestEntity(id: Long): Employee = Employee(id, "name" + id, "number-" + id)

  protected def updateEntity(entity: Employee): Employee =
    entity.copy(employeeNumber = entity.employeeNumber + "changed")

  protected def duplicatedEntity(old: Employee, id: Option[Long] = None): Option[Employee] =
    Some(Employee(
      id = id.getOrElse(generateId),
      name = "name",
      employeeNumber = old.employeeNumber
    ))

  protected def generateId: Long = List.fill(10)(Random.nextInt(10)).mkString.toLong

}
