name := "fasteye"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"

lazy val root = (project in file(".")).enablePlugins(JettyPlugin)