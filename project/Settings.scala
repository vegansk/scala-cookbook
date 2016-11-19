import sbt._
import Keys._

object Versions {
  val scala = "2.11.8"
  val scalaz = "7.2.7"
}

object Dependencies {
  lazy val scalaz = "org.scalaz" %% "scalaz-core" % Versions.scalaz
}

object Settings {

  lazy val common = Seq(
    scalaVersion := Versions.scala
  )

}

