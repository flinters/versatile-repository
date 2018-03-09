import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "jp.co.septeni_original",
      scalaVersion := "2.12.4",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "versatile-repository",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.scalaz" %% "scalaz-core" % "7.2.20",
      "org.skinny-framework" %% "skinny-orm" % "2.5.2",
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.1.0" % Test,
      "ch.qos.logback" % "logback-classic" % "1.2.+",
      "com.h2database" % "h2" % "1.4.192",
      "mysql" % "mysql-connector-java" % "5.1.44",
      "io.getquill" %% "quill-jdbc" % "2.3.2",
      "com.chuusai" %% "shapeless" % "2.3.3",
      //"jp.co.septeni-original.LIB" %% "solib-nekonote" % "0.1.2", Test
    ),
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")
  )

