import Cookbook._

scalaVersion in ThisBuild := Versions.scala

// Projects

val root = Projects.root
val typesOfTypes = Projects.typesOfTypes
val examplesMacros = Projects.examplesMacros
val examplesCore = Projects.examplesCore
val jsBundlerTest = Projects.jsBundlerTest

// Consoles

val conScalaz = Consoles.conScalaz
val conScalaMeta = Consoles.conScalaMeta
val conMonocle = Consoles.conMonocle
val conMacros = Consoles.conMacros
