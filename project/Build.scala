import sbt._
import Keys._
import com.typesafe.sbt.SbtNativePackager._

object BingeWatchBuild extends Build {
  lazy val bingeWatchSettings = Project.defaultSettings ++ packageArchetype.java_application ++ Seq(
    name := "BingeWatch",
    version := "0.1",
    scalaVersion := "2.11.0",

    libraryDependencies ++= Seq(
    )
  )

  lazy val project = Project(
    id = "BingeWatch",
    base = file("."),
    settings = bingeWatchSettings
  )
}
