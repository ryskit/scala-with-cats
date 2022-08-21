package monads.exercise4_7

import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object Exercise4_7 extends App {

  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  type Logged[A] = Writer[Vector[String], A]
  def factorialW(n: Int): Logged[Int] = for {
    ans <- if (n == 0) 1.pure[Logged]
           else slowly(factorialW(n - 1).map(_ * n))
    _ <- Vector(s"fact $n $ans").tell
  } yield ans

//  Await.result(Future.sequence(Vector(
//    Future(factorial(5)),
//    Future(factorial(5))
//  )), 5.seconds)

//  val (logs, ans) = factorialW(5).run
//  println(logs)
//  println(ans)

  Await.result(Future.sequence(Vector(
    Future(factorialW(5)),
    Future(factorialW(5)),
  )).map(_.map(_.written)), 5.seconds)
}
