import sbt._
import Keys._

object Settings {

  object Versions {
    val scala = "2.11.8"
  }

  lazy val common = Seq(
    scalaVersion := Versions.scala
  )

}

