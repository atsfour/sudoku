package org.atsfour.sudoku

import scala.math._

/**
 * Created by Atsushi on 13/12/12.
 */
class Problem(val inputList: List[Int]) {
  val dimension = sqrt(sqrt(inputList.length)).toInt
  val rows = dimension * dimension
  
  require(inputList.length > 0,
    "Empty list is not valid")
  require(inputList.length == Math.pow(dimension, 4),
    "A list should be dim*dim square")
  require(inputList.forall(i => i >= 0 && i <= dimension * dimension),
    "List numbers should be 0 to dim*dim")

  def apply(i: Int):Int = inputList(i)
  
  def apply(i: Int, j:Int):Int = apply(i * rows + j)

  def isNotEmpty: Boolean = inputList.length > 0

  def isSquareShaped: Boolean = inputList.length == pow(dimension, 4)

  def hasOnlyLegalNumbers: Boolean = inputList.forall(i => i >= 0 || i <= dimension * dimension)
}