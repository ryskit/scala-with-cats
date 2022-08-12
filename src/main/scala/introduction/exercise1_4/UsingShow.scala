package introduction.exercise1_4

import cats.Show
import cats.syntax.show._

object UsingShow extends App {
  final case class Cat(name: String, age: Int, color: String)

  implicit val catShow: Show[Cat] =
    Show.show(value =>  s"${value.name} is a ${value.age} year-old ${value.color} cat."
    )

  println(Cat("Daniel", 2, "blown").show)
}
