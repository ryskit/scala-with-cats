package testing_asynchronous_code.exercise8_1

import cats.Id

import scala.concurrent.Future

object Exercise8_1 extends App {

  trait UptimeClient[F[_]] {
    def getUptime(hostname: String): F[Int]
  }

  trait RealUptimeClient extends UptimeClient[Future] {
    override def getUptime(hostname: String): Future[Int] = ???
  }

  trait TestUptimeClient extends UptimeClient[Id] {
    override def getUptime(hostname: String): Id[Int] = ???
  }

  class TestUptimeClient2(hosts: Map[String, Int]) extends UptimeClient[Id] {
    override def getUptime(hostname: String): Id[Int] =
      hosts.getOrElse(hostname, 0)
  }
}
