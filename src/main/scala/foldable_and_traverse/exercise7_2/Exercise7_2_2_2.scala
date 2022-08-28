package foldable_and_traverse.exercise7_2

import cats.Applicative
import cats.instances.option._
import cats.syntax.apply._
import cats.syntax.applicative._

object Exercise7_2_2_2 extends App {
  def listTraverse[F[_]: Applicative, A, B](
    list: List[A]
  )(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum, func(item)).mapN(_ :+ _)
    }

  def listSequence[F[_]: Applicative, B](list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)

  def process(inputs: List[Int]): Option[List[Int]] =
    listTraverse(inputs)(n => if (n % 2 == 0) Some(n) else None)

  println(process(List(2, 4, 6)))
  println(process(List(1, 2, 3)))
}
