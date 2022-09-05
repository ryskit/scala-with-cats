package map_reduce.exercise9_3

import cats.Monoid
import cats.syntax.monoid._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.Inf

object Exercise9_3 extends App {

  def parallelFoldMap[A, B: Monoid](values: Vector[A])(f: A => B): Future[B] = {
    val cpus      = Runtime.getRuntime.availableProcessors()
    val groupSize = (1.0 * values.size / cpus).ceil.toInt
    val groups    = values.grouped(groupSize)
    val futures = groups.map { g =>
      Future(g.foldLeft(Monoid.empty[B])(_ |+| f(_)))
    }
    Future.sequence(futures).map { iterable =>
      iterable.foldLeft(Monoid[B].empty)(_ |+| _)
    }
  }

  val result = parallelFoldMap((1 to 1000000).toVector)(identity)
  println(Await.result(result, Inf))
}
