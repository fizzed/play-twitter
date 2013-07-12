import sbt._
import Keys._
import play.Project._
import com.typesafe.sbt.SbtPgp._

object ApplicationBuild extends Build {

  val appName         = "mfz-play-module-twitter"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    "org.twitter4j" % "twitter4j-core" % "[3.0.3,)",
    "com.twitter" % "twitter-text" % "[1.6.1,)",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "com.mfizz",
    organizationName := "Mfizz Inc",
    organizationHomepage := Some(new URL("http://mfizz.com")),
    
    // required for publishing artifact to maven central via sonatype
    publishMavenStyle := true,
    publishTo <<= version { v: String =>
	  val nexus = "https://oss.sonatype.org/"
	  if (v.trim.endsWith("SNAPSHOT"))
	    Some("snapshots" at nexus + "content/repositories/snapshots")
	  else
	    Some("releases" at nexus + "service/local/staging/deploy/maven2")
	},
    
	// optional gpg key to use (if multiple exist for your setup)
	//usePgpKeyHex("75058B7ADB2DA683"),
	
	// in order to pass sonatype's requirements the following properties are required as well
	startYear := Some(2013),
	description := "Play framework 2.x module to fetch, cache, and display tweets from Twitter",
    licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    homepage := Some(url("http://mfizz.com/oss/play-module-twitter")),
    scmInfo := Some(ScmInfo(url("https://github.com/mfizz-inc/play-module-twitter"), "https://github.com/mfizz-inc/play-module-twitter.git")),
    pomExtra := (
      <developers>
        <developer>
    	  <name>Mfizz Inc</name>
          <email>oss@mfizz.com</email>
          <twitter>@mfizz_inc</twitter>
        </developer>
        <developer>
    	  <name>Joe Lauer</name>
          <twitter>@jjlauer</twitter>
        </developer>
      </developers>
    )
  )

}
