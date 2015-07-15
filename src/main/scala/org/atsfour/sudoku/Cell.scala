/**
 * Created by Atsfour on 13/12/10.
 */

package org.atsfour.sudoku

case class Cell(var number: Int, isFixed: Boolean) {

  def numberOp: Option[Int] = this.number match {
    case x: Int if x > 0 => Some(x)
    case x: Int if x == 0 => None
  }

  def numberString: String = numberOp.map(_.toString).getOrElse("")

  def numberWritten: Boolean = this.numberOp.isDefined

  def update(n: Int): Unit = {
    number = n
  }
}