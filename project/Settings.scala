import sbt._
import Keys._

object Versions {
  val scala = "2.11.8"
  val scalaz = "7.2.7"
  val scalaMeta = "1.3.0"
  val monocle = "1.3.2"
}

object Dependencies {
  lazy val scalaz = "org.scalaz" %% "scalaz-core" % Versions.scalaz
  lazy val scalaMeta = "org.scalameta" %% "scalameta" % Versions.scalaMeta
  lazy val scalaReflect = "org.scala-lang" % "scala-reflect" % Versions.scala
  lazy val pprint = "com.lihaoyi" %% "pprint" % "0.4.3"

  lazy val monocle = Seq(
    "com.github.julien-truffaut" %% "monocle-core" % Versions.monocle,
    "com.github.julien-truffaut" %% "monocle-macro" % Versions.monocle
  )
}

object Settings {

  lazy val common = Seq(
    scalaVersion := Versions.scala
  )

  lazy val macros = Seq(
    resolvers += Resolver.url(
      "scalameta",
      url("http://dl.bintray.com/scalameta/maven"))(Resolver.ivyStylePatterns),
    addCompilerPlugin(
      "org.scalameta" % "paradise" % "3.0.0.132" cross CrossVersion.full),
    scalacOptions += "-Xplugin-require:macroparadise",
    libraryDependencies += Dependencies.scalaReflect

  )
}

