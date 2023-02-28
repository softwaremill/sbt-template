import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings
import Dependencies._
import scala.concurrent.duration._
import ReleaseTransformations._

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  organization := "com.softwaremill.academy.trading.hello",
  scalaVersion := "2.13.10",
  // use sbt-tpolecat, but without fatal warnings
  scalacOptions ~= (_.filterNot(Set("-Xfatal-warnings"))),
  // when using 2.13, fail on non-exhaustive matches
  scalacOptions := {
    val current = scalacOptions.value
    if (scalaVersion.value.startsWith("2.13"))
      current :+ "-Wconf:cat=other-match-analysis:error"
    else current
  },
  // Silence warnings for generated code
  scalacOptions += "-Wconf:src=src_managed/.*:s",
  credentials += Credentials(Path.userHome / ".sbt" / ".credentials_sml_nexus"),
  csrConfiguration := csrConfiguration.value
    .withTtl(Some(1.minute)),
  publishTo := {
    val nexus = "https://nexus3.softwaremill.com/repository/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "scala-academy-snapshots/")
    else
      Some("releases" at nexus + "scala-academy-releases/")
  },
  Compile / packageDoc / publishArtifact := false,
  resolvers ++= Seq(
    "SoftwareMill Snapshots" at "https://nexus3.softwaremill.com/repository/scala-academy-snapshots/",
    "SoftwareMill Releases" at "https://nexus3.softwaremill.com/repository/scala-academy-releases/"
  )
)

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  dockerPublish,
  setNextVersion,
  commitNextVersion,
  pushChanges
)

lazy val dockerPublish: ReleaseStep = { st: State =>
  val extracted = Project.extract(st)
  val ref       = extracted.get(thisProjectRef)
  extracted.runAggregated(ref / Docker / publish, st)
}

lazy val noPublishSettings = Seq(
  publish      := {},
  publishLocal := {}
)

lazy val dockerSettings = Seq(
  dockerBaseImage      := "eclipse-temurin:17.0.5_8-jre",
  dockerUpdateLatest   := false,
  Docker / packageName := "smlacademyacr.azurecr.io/trading-hello-service"
)

lazy val dockerNoPublishSettings = Seq(
  Docker / publishLocal := {},
  Docker / publish      := {}
)

lazy val rootProject = (project in file("."))
  .settings(commonSettings: _*)
  .settings(noPublishSettings: _*)
  .settings(dockerNoPublishSettings: _*)
  .settings(name := "trading-hello")
  .aggregate(tradingHelloServiceApi, tradingHelloService)

lazy val tradingHelloServiceApi: Project = (project in file("trading-hello-service-api"))
  .settings(commonSettings: _*)
  .settings(dockerNoPublishSettings: _*)
  .settings(
    name := "trading-hello-service-api",
    libraryDependencies ++= Libraries.grpcNetty +: Libraries.scalapbCommonProtos
  )
  .enablePlugins(Fs2Grpc)

lazy val tradingHelloService: Project = (project in file("trading-hello-service"))
  .enablePlugins(JavaServerAppPackaging, DockerPlugin)
  .settings(commonSettings: _*)
  .settings(noPublishSettings: _*)
  .settings(dockerSettings: _*)
  .settings(
    name := "trading-hello-service",
    libraryDependencies ++= Libraries.logging ++ Libraries.cats ++ Libraries.grpc ++ Libraries.test ++ Seq(
      Libraries.pureConfig
    )
  )
  .dependsOn(tradingHelloServiceApi)
