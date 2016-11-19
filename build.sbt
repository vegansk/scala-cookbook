scalaVersion in ThisBuild := Settings.Versions.scala

def mkProject(name: String, path: File): Project = (
  Project(name, path)
  settings(Settings.common)
)

lazy val typesOfTypes = mkProject("typesOfTypes", file("types_of_types"))
