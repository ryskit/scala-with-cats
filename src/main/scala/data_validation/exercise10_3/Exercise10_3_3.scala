package data_validation.exercise10_3

import cats.Semigroup
import cats.syntax.either._
import cats.syntax.semigroup._

object Exercise10_3_3 extends App {

  final case class CheckF[E, A](func: A => Either[E, A]) {
    def apply(a: A): Either[E, A] = func(a)

    def and(that: CheckF[E, A])(implicit s: Semigroup[E]): CheckF[E, A] =
      CheckF { a =>
        (this(a), that(a)) match {
          case (Left(e1), Left(e2)) => (e1 |+| e2).asLeft
          case (Left(e), Right(_))  => e.asLeft
          case (Right(_), Left(e))  => e.asLeft
          case (Right(_), Right(_)) => a.asRight
        }
      }
  }

  val a: CheckF[List[String], Int] =
    CheckF { v =>
      if (v > 2) v.asRight
      else List("Must be > 2").asLeft
    }

  val b: CheckF[List[String], Int] =
    CheckF { v =>
      if (v < -2) v.asRight
      else List("Must be < -2").asLeft
    }

  val check = a and b
  println(check(1))
}
