package org.atsfour.sudoku

import scala.math._

/**
 * Created by Atsfour on 13/12/12.
 */

case class Problem(val name: String, inputList: List[Int]) {

  val size = sqrt(sqrt(inputList.length)).toInt
  val rows = size * size

  val field: Field = Field(size, inputList)

  var currentCellIndex = field.findCellIndex((c: Cell) => !c.isFixed)
  var editing: Boolean = false

  def currentCell: Cell = field(currentCellIndex)
  def currentCellValue: String = currentCell.numberString
  def currentCellLinerIndex: Int = field.index2dToLiner(currentCellIndex)

  def editCurrentCell(numString: String, keepPrevious: Boolean = true): Unit = {
    val cc = currentCell
    if (!cc.isFixed) {
      if (keepPrevious) { cc.update((cc.numberString + numString).toInt) }
      else { cc.update(numString.toInt) }
    }
  }

  def moveLeft(): Unit = moveBy(-1, 0)
  def moveRight(): Unit = moveBy(1, 0)
  def moveUp(): Unit = moveBy(0, -1)
  def moveDown(): Unit = moveBy(0, 1)

  def moveBy(dx: Int, dy: Int): Unit = {
    //マイナスを避けるためにrowsを足す。もっとエレガントにできる？
    val moved = (currentCellIndex._1 + dx + rows, currentCellIndex._2 + dy + rows)
    moveTo(moved._1 % rows, moved._2 % rows)
  }

  def moveTo(x: Int, y: Int): Unit = {
    currentCellIndex = (x, y)
  }

  //def getCellIndex(f: => Boolean) = field.
}

object Problem {

  def empty: Problem = Problem("empty", List(0))

}
