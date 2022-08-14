package functors.exercise3_5

object Exercise3_5_6 extends App {
  trait Codec[A] {
    def encode(value: A): String
    def decode(value: String): A
    def imap[B](dec: A => B, enc: B => A): Codec[B] =
      new Codec[B] {
        override def encode(value: B): String =
          Codec.this.encode(enc(value))
        override def decode(value: String): B =
          dec(Codec.this.decode(value))
      }
  }

  def encode[A](value: A)(implicit c: Codec[A]): String =
    c.encode(value)

  def decode[A](value: String)(implicit c: Codec[A]): A =
    c.decode(value)

  implicit val stringCodec: Codec[String] = new Codec[String] {
    override def encode(value: String): String = value
    override def decode(value: String): String = value
  }

  implicit def doubleCoded: Codec[Double] = stringCodec.imap(_.toDouble, _.toString)

  final case class Box[A](value: A)

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] = new Codec[Box[A]] {
    override def encode(value: Box[A]): String =
      c.encode(value.value)
    override def decode(value: String): Box[A] =
      Box(c.decode(value))
  }

  println(encode(123.4))
  println(decode[Double]("123.4"))

  println(encode(Box(123.4)))
  println(decode[Box[Double]]("123.4"))
}
