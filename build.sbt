val scala3Version = "3.2.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "thorium-tutorial",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version
  )

libraryDependencies ++= Seq(
  "com.greenfossil" %% "thorium" % "0.6.0" withSources(),
  "org.scalameta" %% "munit" % "0.7.29" % Test
)
