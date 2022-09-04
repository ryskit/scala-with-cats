package foldable_and_traverse.playground

import cats.Foldable
import cats.Traverse
import cats.instances.future._
import cats.instances.list._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object Playground1 extends App {

  val ints = List(1, 2, 3)
  println(Foldable[List].foldLeft(ints, 0)(_ + _))

  val hostnames = List("google.com", "facebook.com", "instagram.com")

  def getUptime(hostname: String): Future[Int] =
    Future(hostname.length * 60)

  val totalUptime: Future[List[Int]] =
    Traverse[List].traverse(hostnames)(getUptime)

  println(Await.result(totalUptime, 1.second))

  val numbers = List(Future(1), Future(2), Future(3))

  val numbers2: Future[List[Int]] =
    Traverse[List].sequence(numbers)

  println(Await.result(numbers2, 1.second))
}
