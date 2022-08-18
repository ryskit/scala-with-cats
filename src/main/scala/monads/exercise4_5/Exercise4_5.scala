package monads.exercise4_5

import cats.MonadError
import cats.syntax.applicative._
import cats.syntax.applicativeError._

import scala.util.Try

object Exercise4_5 extends App {

  def validateAdult[F[_]](age: Int)(implicit me: MonadError[F, Throwable]): F[Int] =
    if (18 <= age) age.pure[F]
    else new IllegalArgumentException(s"Age must be greater than or equal to 18").raiseError[F, Int]

  println(validateAdult[Try](18))
  println(validateAdult[Try](8))
  type ExceptionOr[A] = Either[Throwable, A]
  println(validateAdult[ExceptionOr](-1))
}
