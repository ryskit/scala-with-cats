package functors.exercise3_5

object Exercise3_5_5 extends App {
  trait Printable[A] {
    def format(value: A): String

    def contramap[B](func: B => A) = new Printable[B] {
        override def format(value: B): String = {
          Printable.this.format(func(value))
        }
      }
  }

  def format[A](value: A)(implicit printable: Printable[A]): String =
    printable.format(value)

  implicit val stringPrintable: Printable[String] = new Printable[String] {
    override def format(value: String): String = s"'$value'"
  }

  implicit val booleanPrintable: Printable[Boolean] = new Printable[Boolean] {
    override def format(value: Boolean): String =
      if (value) "yes" else "no"
  }

  println(format("hello"))
  println(format(true))

  final case class Box[A](value: A)

  implicit def boxPrintable[A](implicit printable: Printable[A]): Printable[Box[A]] =
    printable.contramap(_.value)

  println(format(Box("hello")))
  println(format(Box(true)))

}
