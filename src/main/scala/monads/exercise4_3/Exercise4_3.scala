package monads.exercise4_3

import cats.{Id, Monad}
import cats.syntax.functor._
import cats.syntax.flatMap._

object Exercise4_3 extends App {

  def pure[A](value: A): Id[A] = value
  def flatMap[A, B](value: Id[A])(func: A => B): Id[B] = func(value)
  def map[A, B](value: Id[A])(func: A => B): Id[B] = func(value)

  def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] = for {
    x <- a
    y <- b
  } yield x * x + y * y

  println(pure(10))
  println(flatMap(20)(_ * 2))
  println(map(30)(_ * 3))
  println(sumSquare(Id(2), Id(4)))
}
