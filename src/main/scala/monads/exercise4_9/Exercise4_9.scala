package monads.exercise4_9

import cats.data.State
import cats.implicits.catsSyntaxApplicativeId

object Exercise4_9 extends App {

  type CalcState[A] = State[List[Int], A]
  def evalOne(sym: String): CalcState[Int] =
    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "*" => operator(_ / _)
      case num => operand(num.toInt)
    }

  def operand(num: Int): CalcState[Int] =
    State[List[Int], Int] { stack =>
      (num :: stack, num)
    }

  def operator(func: (Int, Int) => Int): CalcState[Int] =
    State[List[Int], Int] {
      case b :: a :: tail =>
        val ans = func(a, b)
        (ans :: tail, ans)
      case _ =>
        sys.error("Fail!")
    }

  val program = for {
    _   <- evalOne("1")
    _   <- evalOne("2")
    ans <- evalOne("-")
  } yield ans

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(0.pure[CalcState])((a, b) => a.flatMap(_ => evalOne(b)))

  println(program.runA(Nil).value)

  val evalAllProgram = evalAll(List("1", "2", "-", "5", "+", "2", "*"))
  println(evalAllProgram.runA(Nil).value)
}
