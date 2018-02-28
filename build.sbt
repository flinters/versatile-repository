import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "versatile-repository",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.scalaz" %% "scalaz-core" % "7.2.20",
      "mysql" % "mysql-connector-java" % "5.1.44",
      "org.skinny-framework" %% "skinny-orm" % "2.5.2",
      "org.scalikejdbc"            %% "scalikejdbc-test"    % "3.1.0" % Test,
      "com.h2database" % "h2" % "1.4.+",
      "ch.qos.logback" % "logback-classic" % "1.1.+",
      "jp.co.septeni-original.LIB" %% "solib-nekonote" % "0.1.2"
    )
  )
