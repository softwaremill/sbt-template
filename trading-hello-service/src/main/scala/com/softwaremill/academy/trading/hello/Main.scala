package com.softwaremill.academy.trading.hello

import cats.effect.{IO, Resource, ResourceApp}
import com.softwaremill.academy.trading.hello.api.HelloServiceFs2Grpc
import com.softwaremill.academy.trading.hello.grpc.HelloGrpcService
import com.softwaremill.academy.trading.hello.infrastructure.HelloService
import com.softwaremill.academy.trading.hello.infrastructure.grpc.GrpcServerConfig
import fs2.grpc.syntax.all._
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.{HealthStatusManager, ProtoReflectionService}
import io.grpc.{Server, ServerServiceDefinition}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

object Main extends ResourceApp.Forever {

  private val grpcServerConfig = ConfigSource.default.at("grpc.server").loadOrThrow[GrpcServerConfig]

  private def grpcServer(service: ServerServiceDefinition): Resource[IO, Server] = {
    NettyServerBuilder
      .forPort(grpcServerConfig.port)
      .addService(ProtoReflectionService.newInstance())
      .addService(new HealthStatusManager().getHealthService)
      .addService(service)
      .resource[IO]
      .evalMap(server => IO(server.start()))
  }

  override def run(args: List[String]): Resource[IO, Unit] = {
    val helloService = HelloService.make[IO]

    for {
      helloGrpcService <- HelloServiceFs2Grpc.bindServiceResource(HelloGrpcService.make(helloService))
      _ <- grpcServer(helloGrpcService)
    } yield ()
  }
}
