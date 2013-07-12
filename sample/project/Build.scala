import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "mfz-play-module-twitter-sample"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
  )
  
  // sub-project this depends on
  val module = RootProject(file("../module"))

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "com.mfizz",
    organizationName := "Mfizz Inc",
    organizationHomepage := Some(new URL("http://mfizz.com"))
  ).dependsOn(module)

}
