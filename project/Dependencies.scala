import sbt._

object Dependencies {

  object V {
    val helloServiceApi = "0.1-SNAPSHOT"

    val scalaLogging = "3.9.4"
    val logback = "1.4.5"

    val cats = "2.9.0"
    val catsEffect = "3.4.3"

    val pureConfig = "0.17.2"

    val grpcServices = "1.51.1"

    val scalatest = "3.2.14"
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

    val grpcNetty = "io.grpc" % "grpc-netty-shaded" % scalapb.compiler.Version.grpcJavaVersion
    val grpcServices = "io.grpc" % "grpc-services" % V.grpcServices
    val grpc = Seq(grpcNetty, grpcServices)
    val grpcScalaPB =
      Seq(
        "com.thesamet.scalapb.common-protos" %% "proto-google-common-protos-scalapb_0.11" % "2.9.6-0" % "protobuf",
        "com.thesamet.scalapb.common-protos" %% "proto-google-common-protos-scalapb_0.11" % "2.9.6-0"
      )

    val scalatest = "org.scalatest" %% "scalatest" % V.scalatest

    val test = Seq(scalatest).map(_ % Test)
  }
}
