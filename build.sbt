val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "thorium-tutorial",
    version := "0.1.0",

    scalaVersion := scala3Version
  )

libraryDependencies ++= Seq(
  "com.greenfossil" %% "thorium" % "0.9.0" withSources(),
  "org.scalameta" %% "munit" % "1.1.0" % Test
)
