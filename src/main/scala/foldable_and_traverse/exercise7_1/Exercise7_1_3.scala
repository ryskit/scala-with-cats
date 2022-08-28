package foldable_and_traverse.exercise7_1

import cats.Monoid

class Exercise7_1_3 extends App {

  def map[A, B](l: List[A])(f: A => B): List[B] =
    l.foldRight(List.empty[B])((i, acc) => f(i) +: acc)

  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] =
    l.foldRight(List.empty[B])((i, acc) => f(i) ++ acc)

  def filter[A](l: List[A])(f: A => Boolean): List[A] =
    l.foldRight(List.empty[A])((i, acc) => {
      if (f(i)) i +: acc else acc
    })

  def sum[A](l: List[A])(implicit m: Monoid[A]): A =
    l.foldRight(m.empty)(m.combine)
}
