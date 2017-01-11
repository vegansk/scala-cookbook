import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin

object Cookbook {

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

  type PC = Project => Project

  def commonSettings: PC =
    _.settings(
      scalaVersion := Versions.scala
    )

  def macrosSettings: PC =
    _.settings(
      resolvers += Resolver.url(
        "scalameta",
        url("http://dl.bintray.com/scalameta/maven"))(Resolver.ivyStylePatterns),
      addCompilerPlugin(
        "org.scalameta" % "paradise" % "3.0.0.132" cross CrossVersion.full),
      scalacOptions += "-Xplugin-require:macroparadise",
      libraryDependencies ++= Seq(
        Dependencies.scalaReflect,
        Dependencies.scalaMeta
      )
    )

  def scalajsSettings: PC =
    _.enablePlugins(ScalaJSPlugin)

  def jsBundlerSettings: PC =
    _.enablePlugins(ScalaJSBundlerPlugin)

  object Projects {

    lazy val root = project.in(file("."))
      .aggregate(typesOfTypes, examplesCore)

    lazy val typesOfTypes = project.in(file("types_of_types"))
      .configure(commonSettings)

    lazy val examplesMacros = project.in(file("examples") / "macros")
      .configure(commonSettings, macrosSettings)

    lazy val examplesCore = project.in(file("examples") / "core")
      .configure(commonSettings, macrosSettings)
      .dependsOn(examplesMacros)

    lazy val jsBundlerTest = project.in(file("scalajs") / "bundler-test")
      .configure(commonSettings, jsBundlerSettings)
  }

  object Consoles {

    lazy val conScalaz = project.in(file("consoles") / "scalaz")
      .configure(commonSettings)
      .settings(
        libraryDependencies += Dependencies.scalaz,
        initialCommands in console := """
          | import scalaz._
          | import Scalaz._
        """.stripMargin
      )

    lazy val conScalaMeta = project.in(file("consoles") / "scalameta")
      .configure(commonSettings)
      .settings(
        libraryDependencies += Dependencies.scalaMeta,
        libraryDependencies += Dependencies.pprint,
        initialCommands in console := """
          | import scala.meta._
          | import pprint._
        """.stripMargin
      )

    lazy val conMonocle = project.in(file("consoles") / "monocle")
      .configure(commonSettings)
      .settings(
        libraryDependencies ++= Dependencies.monocle,
        addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full),
        initialCommands in console := """
          | import monocle._
          | import monocle.macros._
        """.stripMargin
      )

    lazy val conMacros = project.in(file("consoles") / "macros")
      .configure(commonSettings)
      .settings(
        scalacOptions += "-Yreify-copypaste",
        libraryDependencies += Dependencies.scalaReflect,
        libraryDependencies += Dependencies.pprint,
        initialCommands in console := """
          | import scala.language.experimental.macros
          | import scala.reflect.runtime.universe
          | import universe._
          | import pprint._
        """.stripMargin
      )
  }
}
