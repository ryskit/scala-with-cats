package data_validation.exercise10_4

import cats.Semigroup
import cats.data.Validated
import data_validation.predicates.Example10_4_1.Predicate

object Exercise10_4_2 extends App {

  sealed trait Check[E, A, B] {
    import Check._

    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, B]

    def map[C](func: B => C): Check[E, A, C] =
      Map[E, A, B, C](this, func)
  }

  object Check {
    final case class Map[E, A, B, C](check: Check[E, A, B], func: B => C)
        extends Check[E, A, C] {
      def apply(in: A)(implicit s: Semigroup[E]): Validated[E, C] =
        check(in).map(func)
    }

    final case class Pure[E, A](pred: Predicate[E, A]) extends Check[E, A, A] {
      def apply(in: A)(implicit s: Semigroup[E]): Validated[E, A] =
        pred(in)
    }

    def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] =
      Pure(pred)
  }
}
