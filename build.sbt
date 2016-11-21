scalaVersion in ThisBuild := Versions.scala

def mkProject(name: String, path: File): Project = (
  Project(name, path)
  settings(Settings.common)
)

lazy val typesOfTypes = mkProject("typesOfTypes", file("types_of_types"))

lazy val examples = mkProject("examples", file("examples"))

lazy val conScalaz = mkProject("conScalaz", file("."))
  .settings(
  libraryDependencies += Dependencies.scalaz,
  initialCommands in console := """
import scalaz._
import Scalaz._
"""
)

lazy val conScalaMeta = mkProject("conScalaMeta", file("."))
  .settings(
  libraryDependencies += Dependencies.scalaMeta,
  initialCommands in console := """
import scala.meta._
"""
)
