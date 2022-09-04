package foldable_and_traverse.exercise7_2

import cats.Applicative
import cats.data.Validated
import cats.instances.option._
import cats.syntax.apply._
import cats.syntax.applicative._
import cats.Traverse

object Exercise7_2_2_3 extends App {
  def listTraverse[F[_]: Applicative, A, B](
    list: List[A]
  )(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum, func(item)).mapN(_ :+ _)
    }

  def listSequence[F[_]: Applicative, B](list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)

  type ErrorsOr[A] = Validated[List[String], A]

  def process(inputs: List[Int]): ErrorsOr[List[Int]] =
    listTraverse(inputs) { n =>
      if (n % 2 == 0) Validated.Valid(n)
      else Validated.invalid(List(s"$n is not even"))
    }

  println(process(List(2, 4, 6)))
  println(process(List(1, 2, 3)))
}
