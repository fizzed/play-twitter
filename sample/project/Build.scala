import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "fizzed-play-module-twitter-sample"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaCore,
    javaJdbc,
    javaEbean
  )
  
  // sub-project this depends on
  val module = RootProject(file("../module"))

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "com.fizzed",
    organizationName := "Fizzed, Inc.",
    organizationHomepage := Some(new URL("http://fizzed.com"))
  ).dependsOn(module)
}