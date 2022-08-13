package monoid_and_semigroups.exercise2_5

import cats.Monoid
import cats.instances.int._
import cats.instances.option._
import cats.implicits.catsSyntaxSemigroup

object Exercise2_5 extends App {
  case class Order(totalCost: Double, quantity: Double)
  implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
    override def empty: Order = Order(totalCost = 0, quantity = 0)

    override def combine(x: Order, y: Order): Order =
      Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
  }


  def add(items: List[Int]): Int = {
    items.foldLeft(Monoid[Int].empty)(_ |+| _)
  }

  def add2[A](items: List[A])(implicit monoid: Monoid[A]): A = {
    items.foldLeft(Monoid[A].empty)(_ |+| _)
  }

  println(add(List(1, 2, 3)))
  println(add2(List(Some(1), None, Some(2), Some(3))))
  println(add2(List(Order(5, 2), Order(2, 2), Order(10, 8))))
}
