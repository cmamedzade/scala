import com.lightbend.cinnamon.sbt.Cinnamon

name := "classicToTyped"

version := "0.1"

scalaVersion := "2.13.3"

val AkkaVersion = "2.6.8"
lazy val logback = "ch.qos.logback" % "logback-classic" % "1.0.9"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "org.slf4j" % "slf4j-api" % "1.7.30" ,
  "ch.qos.logback" % "logback-classic" % "1.0.9",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.1" % Test,
  Cinnamon.library.cinnamonCHMetrics,
  Cinnamon.library.cinnamonAkka,
  Cinnamon.library.cinnamonAkkaTyped,
  Cinnamon.library.cinnamonAkkaPersistence,
  Cinnamon.library.cinnamonAkkaStream,
  Cinnamon.library.cinnamonAkkaHttp,
  Cinnamon.library.cinnamonPrometheus,
  Cinnamon.library.cinnamonPrometheusHttpServer
)


// Enable the Lightbend Telemetry (Cinnamon) sbt plugin
lazy val app = project in file(".")
enablePlugins(Cinnamon)


cinnamon in run := true
cinnamon in test := true

// Set the Cinnamon Agent log level
cinnamonLogLevel := "INFO"

