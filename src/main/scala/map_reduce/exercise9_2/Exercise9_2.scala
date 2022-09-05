package map_reduce.exercise9_2

import cats.Monoid
import cats.instances.int._
import cats.instances.string._
import cats.syntax.monoid._

object Exercise9_2 extends App {

  def foldMap[A, B: Monoid](v: Vector[A])(f: A => B): B =
    v.map(f).foldLeft(Monoid.empty[B])(_ |+| _)

  println(foldMap(Vector(1, 2, 3))(identity))

  println(foldMap(Vector(1, 2, 3))(_.toString + "! "))

  println(foldMap("Hello world!".toVector)(_.toString.toUpperCase))
}
