package semigroupal_and_applicative.playground

import cats.Semigroupal
import cats.syntax.parallel._
import cats.syntax.apply._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object PlayGround extends App {

  case class Cat(name: String, yearOfBirth: Int, favoriteFoods: List[String])

  val futureCat =
    (Future("Garfield"), Future(1978), Future(List("Lasagne"))).mapN(Cat)

  println(Await.result(futureCat, 1.second))

  println(Semigroupal[List].product(List(1, 2), List(3, 4)))

  type ErrorOr[A] = Either[Vector[String], A]
  val error1: ErrorOr[Int] = Left(Vector("Error 1"))
  val error2: ErrorOr[Int] = Left(Vector("Error 2"))

  println(Semigroupal[ErrorOr].product(error1, error2))
  println((error1, error2).tupled)
  println((error1, error2).parTupled)

}
