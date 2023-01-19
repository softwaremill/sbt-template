import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings
import Dependencies._

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  organization := "com.softwaremill.academy.trading.hello",
  scalaVersion := "2.13.10"
)

lazy val rootProject = (project in file("."))
  .settings(commonSettings: _*)
  .settings(publishArtifact := false, name := "trading-hello")
  .aggregate(tradingHelloServiceApi, tradingHelloService)

lazy val tradingHelloServiceApi: Project = (project in file("trading-hello-service-api"))
  .settings(commonSettings: _*)
  .settings(
    name := "trading-hello-service-api",
    libraryDependencies += Libraries.grpcNetty
  )
  .enablePlugins(Fs2Grpc)

lazy val tradingHelloService: Project = (project in file("trading-hello-service"))
  .settings(commonSettings: _*)
  .settings(
    name := "trading-hello-service",
    libraryDependencies ++= Libraries.logging ++ Libraries.cats ++ Libraries.grpc ++ Libraries.test ++ Seq(
      // Libraries.helloServiceApi, // don't pull reruired project from Nexus
      Libraries.pureConfig
    )
  )
  .dependsOn(tradingHelloServiceApi) // build required project instead of pulling it from Nexus

// Nexus configuration
publishTo := {
  val nexus = "https://nexus3.softwaremill.com/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "repository/scala-academy-snapshots/")
  else
    Some("releases"  at nexus + "repository/scala-academy-release/")
}

resolvers ++= Seq(
  "SoftwareMill Snapshots" at "https://nexus3.softwaremill.com/repository/scala-academy-snapshots/",
  "SoftwareMill Releases" at "https://nexus3.softwaremill.com/repository/scala-academy-release/"
)
