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
      Libraries.helloServiceApi,
      Libraries.pureConfig
    )
  )
