scalaVersion in ThisBuild := Versions.scala

def mkProject(name: String, path: File): Project = (
  Project(name, path)
  settings(Settings.common)
)

lazy val typesOfTypes = mkProject("typesOfTypes", file("types_of_types"))

lazy val examplesMacros = mkProject("examplesMacros", file("examples") / "macros")
  .settings(
  libraryDependencies += Dependencies.scalaMeta
)
  .settings(Settings.macros)

lazy val examples = mkProject("examples", file("examples") / "core")
  .settings(
  libraryDependencies += Dependencies.scalaMeta
)
  .settings(Settings.macros)
  .dependsOn(examplesMacros)

lazy val conScalaz = mkProject("conScalaz", file("consoles") / "scalaz")
  .settings(
  libraryDependencies += Dependencies.scalaz,
  initialCommands in console := """
import scalaz._
import Scalaz._
"""
)

lazy val conScalaMeta = mkProject("conScalaMeta", file("consoles") / "scalameta")
  .settings(
  libraryDependencies += Dependencies.scalaMeta,
  libraryDependencies += Dependencies.pprint,
  initialCommands in console := """
import scala.meta._
import pprint._
"""
)

lazy val conMonocle = mkProject("conMonocle", file("consoles") / "monocle")
  .settings(
  libraryDependencies ++= Dependencies.monocle,
  addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full),
  initialCommands in console := """
import monocle._
import monocle.macros._
"""
)

lazy val conMacros = mkProject("conMacros", file("consoles") / "macros")
  .settings(
  libraryDependencies += Dependencies.scalaReflect,
  libraryDependencies += Dependencies.pprint,
  initialCommands in console := """
import scala.language.experimental.macros
import scala.reflect.runtime.universe
import universe._
import pprint._
"""
)
