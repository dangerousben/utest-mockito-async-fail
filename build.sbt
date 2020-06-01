lazy val root = (project in file("."))
  .settings(
    scalaVersion := "2.12.11",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "utest" % "0.7.4" % Test,
      "org.mockito" %% "mockito-scala" % "1.14.4" % Test,
    ),
    testFrameworks += new TestFramework("utest.runner.Framework"),
  )
