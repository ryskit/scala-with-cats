package data_validation.exercise10_3

import cats.Semigroup
import cats.syntax.list._
import cats.syntax.semigroup._

object Exercise10_3_1 extends App {

  val semigroup = Semigroup[List[String]]
  println(semigroup.combine(List("s1"), List("s2")))
  println(List("s1") |+| List("s2"))
}
