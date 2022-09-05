package testing_asynchronous_code.exercise8_1

import cats.implicits.toTraverseOps
import cats.syntax.functor._
import cats.{Applicative, Id}

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

  class UptimeService[F[_]](client: UptimeClient[F]) {
    def getTotalUptime(hostnames: List[String]): F[Int] = ???
  }

  class UptimeService2[F[_]](client: UptimeClient[F])(implicit
    a: Applicative[F]
  ) {
    def getTotalUptime(hostnames: List[String]): F[Int] =
      hostnames.traverse(client.getUptime).map(_.sum)
  }

  class UptimeService3[F[_]: Applicative](client: UptimeClient[F]) {
    def getTotalUptime(hostnames: List[String]): F[Int] =
      hostnames.traverse(client.getUptime).map(_.sum)
  }

  def testTotalUptime() = {
    val hosts    = Map("host1" -> 10, "host2" -> 6)
    val client   = new TestUptimeClient2(hosts)
    val service  = new UptimeService2(client)
    val actual   = service.getTotalUptime(hosts.keys.toList)
    val expected = hosts.values.sum
    assert(actual == expected)
  }

  testTotalUptime()
}
