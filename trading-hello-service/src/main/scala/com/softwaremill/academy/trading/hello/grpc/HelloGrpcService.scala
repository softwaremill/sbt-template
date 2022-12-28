package com.softwaremill.academy.trading.hello.grpc

import cats.effect.kernel.Async
import cats.syntax.functor._
import com.softwaremill.academy.trading.hello.api.{HelloRequest, HelloResponse, HelloServiceFs2Grpc}
import com.softwaremill.academy.trading.hello.domain.HelloService
import io.grpc.Metadata

trait HelloGrpcService[F[_]] extends HelloServiceFs2Grpc[F, Metadata]

object HelloGrpcService {

  def make[F[_]: Async](helloService: HelloService[F]): HelloGrpcService[F] = new HelloGrpcService[F] {

    override def hello(request: HelloRequest, ctx: Metadata): F[HelloResponse] =
      helloService.hello(request.name).map(greeting => HelloResponse(greeting))
  }
}
