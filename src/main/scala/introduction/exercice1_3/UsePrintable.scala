package introduction.exercice1_3

import PrintableSyntax._

object UsePrintable extends App {
  final case class Cat(name: String, age: Int, color: String)

  implicit val catPrintable = new Printable[Cat] {
    override def format(value: Cat): String =
      s"${value.name} is a ${value.age} year-old ${value.color} cat."
  }

  val cat = Cat("Daniel", 2, "blown")
  Printable.print(cat)
  cat.print
}
