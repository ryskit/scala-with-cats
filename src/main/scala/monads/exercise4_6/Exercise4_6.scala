package monads.exercise4_6

import cats.Eval

object Exercise4_6 extends App {
  def evalFoldRight[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(fn(head, evalFoldRight(tail, acc)(fn)))
      case Nil => acc
    }

  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    evalFoldRight(as, Eval.now(acc)) { (a, b) =>
      b.map(fn(a, _))
    }.value

  (1 to 100000).foldRight(0L)(_ + _)
  println(foldRight((1 to 100000).toList, 0L)(_ + _))
}
