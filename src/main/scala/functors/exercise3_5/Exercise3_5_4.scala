package functors.exercise3_5

import cats.Functor
import cats.syntax.functor._

object Exercise3_5_4 extends App {

  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]

  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
      Branch(left, right)

    def leaf[A](value: A): Tree[A] = Leaf(value)
  }

  implicit def binaryTreeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =
      fa match {
        case Branch(left, right) => Branch(map(left)(f), map(right)(f))
        case Leaf(value) => Leaf(f(value))
      }
  }
  println(Tree.branch(Tree.leaf(1), Tree.branch(Tree.leaf(2), Tree.leaf(3))).map(_ * 2))
}
