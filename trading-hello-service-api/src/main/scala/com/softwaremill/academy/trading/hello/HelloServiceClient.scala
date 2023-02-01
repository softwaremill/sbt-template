package com.softwaremill.academy.trading.hello

import cats.effect.{ Async, Resource }
import com.softwaremill.academy.trading.hello.api.HelloServiceFs2Grpc
import fs2.grpc.syntax.all._
import io.grpc.Metadata
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder

object HelloServiceClient {

  def apply[F[_]: Async](grpcClientConfig: GrpcClientConfig): Resource[F, HelloServiceFs2Grpc[F, Metadata]] =
    for {
      channel <- NettyChannelBuilder
        .forAddress(grpcClientConfig.host, grpcClientConfig.port)
        .usePlaintext()
        .resource
      endpoint <- HelloServiceFs2Grpc.stubResource(channel)
    } yield endpoint
}
