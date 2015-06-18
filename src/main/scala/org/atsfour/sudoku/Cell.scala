package org.atsfour.sudoku

/**
 * Created by Atsushi on 13/12/10.
 */
case class Cell(val size: Int, val number: Int) {

    require(number >= 0 && number <= size * size)

  def numberOp: Option[Int] = this.number match{
    case x:Int if x > 0 => Some(x)
    case x:Int if x == 0 => None
  }

  def isFixed: Boolean = {
    !numberOp.isEmpty
  }
}
