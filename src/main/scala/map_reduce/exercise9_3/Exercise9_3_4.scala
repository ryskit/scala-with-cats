package map_reduce.exercise9_3

import cats.Monoid
import cats.instances.int._
import cats.instances.future._
import cats.instances.vector._
import cats.syntax.monoid._
import cats.syntax.foldable._
import cats.syntax.traverse._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Exercise9_3_4 extends App {

  def parallelFoldMap[A, B: Monoid](values: Vector[A])(f: A => B): Future[B] = {
    val cpus      = Runtime.getRuntime.availableProcessors()
    val groupSize = (1.0 * values.size / cpus).ceil.toInt
    values
      .grouped(groupSize)
      .toVector
      .traverse(group => Future(group.foldMap(f)))
      .map(_.combineAll)
  }
}
