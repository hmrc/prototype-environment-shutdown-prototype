import sbt.Keys.libraryDependencies

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

lazy val root = (project in file(".")).
  settings(
    name := "aws-learning",
    version := "1.0",
    scalaVersion := "2.11.8",
    retrieveManaged := true,
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.0.0",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-ec2" % "1.11.250",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-elasticbeanstalk" % "1.11.250",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "1.1.0",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
    libraryDependencies += "com.github.seratch" %% "awscala" % "0.5.+",
    libraryDependencies += "org.zeroturnaround" % "zt-zip" % "1.10",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.11.86",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-elasticbeanstalk" % "1.11.86",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-sns" % "1.11.86",
    libraryDependencies += "com.typesafe.play" % "play-json_2.11" % "2.5.12",
    libraryDependencies += "commons-lang" % "commons-lang" % "2.6",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-log4j" % "1.0.0",
    libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25",
    libraryDependencies += "log4j" % "log4j" % "1.2.17",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    libraryDependencies += "commons-io" % "commons-io" % "2.5",
    libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test",
    libraryDependencies += "com.github.tomakehurst" % "wiremock" % "1.52" % "test",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-autoscaling" % "1.11.226"
  )
