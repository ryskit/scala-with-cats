package foldable_and_traverse.playground

import cats.Foldable
import cats.Traverse

object Playground1 extends App {

  val ints = List(1, 2, 3)
  println(Foldable[List].foldLeft(ints, 0)(_ + _))

}
