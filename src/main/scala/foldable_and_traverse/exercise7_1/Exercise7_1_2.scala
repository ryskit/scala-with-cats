package foldable_and_traverse.exercise7_1

object Exercise7_1_2 extends App {

  println(List(1, 2, 3).foldLeft(List.empty[Int])((acc, i) => i :: acc))
  println(List(1, 2, 3).foldRight(List.empty[Int])((i, acc) => i :: acc))
}
