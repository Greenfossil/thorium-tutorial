val scala3Version = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "thorium-tutorial",
    version := "0.1.0",

    scalaVersion := scala3Version
  )

libraryDependencies ++= Seq(
  "com.greenfossil" %% "thorium" % "0.8.0" withSources(),
  "org.scalameta" %% "munit" % "1.0.0" % Test
)
