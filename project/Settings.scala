import sbt._
import Keys._

object Versions {
  val scala = "2.11.8"
  val scalaz = "7.2.7"
  val scalaMeta = "1.3.0"
}

object Dependencies {
  lazy val scalaz = "org.scalaz" %% "scalaz-core" % Versions.scalaz
  lazy val scalaMeta = "org.scalameta" %% "scalameta" % Versions.scalaMeta
}

object Settings {

  lazy val common = Seq(
    scalaVersion := Versions.scala
  )

}

