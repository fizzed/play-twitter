import sbt._
import Keys._
import play.Project._
import com.typesafe.sbt.SbtPgp._

object ApplicationBuild extends Build {

  val appName         = "fizzed-play-module-twitter"
  val appVersion      = "2.0.1"

  val appDependencies = Seq(
    "org.twitter4j" % "twitter4j-core" % "4.0.2",
    "com.twitter" % "twitter-text" % "1.6.1",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "com.fizzed",
    organizationName := "Fizzed, Inc.",
    organizationHomepage := Some(new URL("http://fizzed.com")),
    
    // required for publishing artifact to maven central via sonatype
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype.credentials"),
    publishMavenStyle := true,
    publishTo <<= version { v: String =>
	  val nexus = "https://oss.sonatype.org/"
	  if (v.trim.endsWith("SNAPSHOT"))
	    Some("snapshots" at nexus + "content/repositories/snapshots")
	  else
	    Some("releases" at nexus + "service/local/staging/deploy/maven2")
	},
	
	// in order to pass sonatype's requirements the following properties are required as well
	startYear := Some(2013),
	description := "PlayFramework 2.x module to fetch, cache, and display tweets from Twitter",
    licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    homepage := Some(url("http://fizzed.com/oss/play-module-twitter")),
    scmInfo := Some(ScmInfo(url("https://github.com/fizzed/play-module-twitter"), "https://github.com/fizzed/play-module-twitter.git")),
    pomExtra := (
      <developers>
        <developer>
    	    <name>Fizzed Inc</name>
          <email>oss@fizzed.com</email>
        </developer>
      </developers>
    )
  )
}
