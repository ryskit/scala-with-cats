package monads.exercise4_10

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._

import scala.annotation.tailrec

object Exercise4_10 extends App {
  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A)                        extends Tree[A]

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
  def leaf[A](value: A): Tree[A]                        = Leaf(value)

  //  implicit val treeNonRecursiveMonad = new Monad[Tree] {
//    override def pure[A](x: A): Tree[A] = Tree.leaf(x)
//    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] =
//      fa match {
//        case Branch(left, right) =>
//          Tree.branch(flatMap(left)(f), flatMap(right)(f))
//        case Leaf(value) => f(value)
//      }
//
//    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] =
//      flatMap(f(a)) {
//        case Left(value) =>
//          tailRecM(value)(f)
//        case Right(value) =>
//          Leaf(value)
//      }
//  }

  implicit val treeRecursiveMonad = new Monad[Tree] {
    override def pure[A](x: A): Tree[A] = leaf(x)

    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] =
      fa match {
        case Branch(left, right) =>
          branch(flatMap(left)(f), flatMap(right)(f))
        case Leaf(value) => f(value)
      }

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = {
      @tailrec
      def loop(
        open: List[Tree[Either[A, B]]],
        closed: List[Option[Tree[B]]]
      ): List[Tree[B]] =
        open match {
          case Branch(l, r) :: next =>
            loop(l :: r :: next, None :: closed)
          case Leaf(Left(value)) :: next =>
            loop(f(value) :: next, closed)
          case Leaf(Right(value)) :: next =>
            loop(next, Some(pure(value)) :: closed)
          case Nil =>
            closed.foldLeft(Nil: List[Tree[B]]) { (acc, maybeTree) =>
              maybeTree.map(_ :: acc).getOrElse {
                val left :: right :: tail = acc
                branch(left, right) :: tail
              }
            }
        }

      loop(List(f(a)), Nil).head
    }
  }

  println(
    branch(leaf(100), leaf(200))
      .flatMap(x => branch(leaf(x - 1), leaf(x + 1)))
  )

  val result = for {
    a <- branch(leaf(100), leaf(200))
    b <- branch(leaf(a - 10), leaf(a + 10))
    c <- branch(leaf(b - 1), leaf(b + 1))
  } yield c
  println(result)
}
