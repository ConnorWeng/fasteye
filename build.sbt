name := "fasteye"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

lazy val root = (project in file(".")).enablePlugins(JettyPlugin)