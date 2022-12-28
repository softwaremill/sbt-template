package com.softwaremill.academy.trading.hello.infrastructure

import cats.Id
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HelloServiceSpec extends AnyFlatSpec with Matchers {

  behavior of "Hello service"

  it should "greet" in {
    // given
    val service = HelloService.make[Id]

    // when
    val greeting = service.hello("SML Academy")

    // then
    greeting shouldBe "Hello, SML Academy"
  }
}
