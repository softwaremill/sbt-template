import sbt._

object Dependencies {

  object V {
    val cats = "2.9.0"

    val catsEffect = "3.4.5"

    val commonProtos = "2.9.6-0"

    val grpcServices = "1.51.3"

    val helloServiceApi = "0.1-SNAPSHOT"

    val logback = "1.4.5"

    val pureConfig = "0.17.2"

    val scalaLogging = "3.9.4"

    val scalatest = "3.2.15"
  }

  object Libraries {
    val helloServiceApi = "com.softwaremill.academy.trading.hello" %% "trading-hello-service-api" % V.helloServiceApi

    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % V.scalaLogging
    val logback = "ch.qos.logback" % "logback-classic" % V.logback

    val logging = Seq(scalaLogging, logback)

    val catsCore = "org.typelevel" %% "cats-core" % V.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect
    val cats = Seq(catsCore, catsEffect)

    val pureConfig = "com.github.pureconfig" %% "pureconfig" % V.pureConfig

    // https://scalapb.github.io/docs/third-party-protos/#common-protos-maybe-a-scala-package-for-the-protos-already-exists
    val scalapbCommonProtos = Seq(
      "com.thesamet.scalapb.common-protos" %% "proto-google-common-protos-scalapb_0.11" % V.commonProtos % "protobuf",
      "com.thesamet.scalapb.common-protos" %% "proto-google-common-protos-scalapb_0.11" % V.commonProtos
    )

    val grpcNetty = "io.grpc" % "grpc-netty-shaded" % scalapb.compiler.Version.grpcJavaVersion
    val grpcServices = "io.grpc" % "grpc-services" % V.grpcServices
    val grpc = Seq(grpcNetty, grpcServices)

    val scalatest = "org.scalatest" %% "scalatest" % V.scalatest

    val test = Seq(scalatest).map(_ % Test)
  }
}
