package com.softwaremill.academy.trading.hello.domain

trait HelloService[F[_]] {

  def hello(name: String): F[String]
}
