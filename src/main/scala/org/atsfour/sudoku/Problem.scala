package org.atsfour.sudoku

import scala.math._

/**
 * Created by Atsushi on 13/12/12.
 */
class Problem(val inputList: List[Int]) {
  val size = sqrt(sqrt(inputList.length)).toInt
  val rows = size * size
  val field = Field(size, inputList)

  require(inputList.length > 0,
    "Empty list is not valid")
  require(inputList.length == Math.pow(size, 4),
    "A list should be dim*dim square")
  require(inputList.forall(i => i >= 0 && i <= size * size),
    "List numbers should be 0 to dim*dim")

  def apply(i: Int):Int = inputList(i)

  def apply(i: Int, j:Int):Int = apply(i * rows + j)

  def isNotEmpty: Boolean = inputList.length > 0

  def isSquareShaped: Boolean = inputList.length == pow(size, 4)

  def hasOnlyLegalNumbers: Boolean = inputList.forall(i => i >= 0 || i <= size * size)
}