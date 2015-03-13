name := """fizzed-scala-sample"""

version := "1.0-SNAPSHOT"

organization := "com.fizzed"

organizationName := "Fizzed, Inc."

organizationHomepage := Some(new URL("http://fizzed.com"))

lazy val module = RootProject(file("../../module"))

lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(module)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(jdbc, anorm, cache, ws)
