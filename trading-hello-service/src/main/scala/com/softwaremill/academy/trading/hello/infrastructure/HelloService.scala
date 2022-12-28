package com.softwaremill.academy.trading.hello.infrastructure

import cats.Applicative
import cats.syntax.applicative._
import com.softwaremill.academy.trading.hello.domain.HelloService

object HelloService {

  def make[F[_]: Applicative]: HelloService[F] = new HelloService[F] {

    override def hello(name: String): F[String] = s"Hello, $name".pure
  }
}
