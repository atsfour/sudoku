name := """org.atsfour.sudoku"""

version := "0.1"

scalaVersion := "2.11.6"

libraryDependencies ++= List(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalafx" %% "scalafx" % "8.0.0-R4"
  )

unmanagedJars in Compile +=
Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))