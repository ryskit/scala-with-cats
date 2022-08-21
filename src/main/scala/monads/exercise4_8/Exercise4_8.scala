package monads.exercise4_8

import cats.data.Reader
import cats.syntax.applicative._

object Exercise4_8 extends App {
  final case class Db(
    usernames: Map[Int, String],
    passwords: Map[String, String]
  )

  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(db => {
      db.usernames
        .find(u => u._1 == userId)
        .map(_._2)
    })

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(db => {
      db.passwords
        .find(p => p._1 == username)
        .fold(false)(pw => pw._2 == password)
    })

  def checkLogin(userId: Int, password: String): DbReader[Boolean] = for {
    username <- findUsername(userId)
    canLogin <- username match {
      case Some(username) => checkPassword(username, password)
      case None           => false.pure[DbReader]
    }
  } yield canLogin

  val users = Map(1 -> "dade", 2 -> "kate", 3 -> "margo")
  val passwords =
    Map("dade" -> "zerocool", "kate" -> "acidburn", "margo" -> "secret")

  val db = Db(users, passwords)

  println(checkLogin(1, "zerocool").run(db))
  println(checkLogin(4, "davinci").run(db))
}
