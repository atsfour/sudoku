package org.atsfour.sudoku

import scala.math._

/**
 * Created by Atsushi on 13/12/12.
 */

class Problem(val name: String, inputList: List[Int]) {

  val size = sqrt(sqrt(inputList.length)).toInt
  val rows = size * size
  val field = Field(size, inputList)

  var currentCell = field.findCellIndex( (c:Cell) => c.isFixed )

  def getCurrentCell: Int = field.linerIndex(currentCell)

  def moveLeft: Unit = move(-1, 0)
  def moveRight: Unit = move(1, 0)
  def moveUp: Unit = move(0, -1)
  def moveDown: Unit = move(0, 1)

  def move(dx: Int, dy: Int): Unit = {
    val moved = (currentCell._1 + dx, currentCell._2 + dy)
    currentCell = (moved._1 % rows, moved._2 % rows)
  }

  def moveTo(x: Int, y: Int): Unit = {
    currentCell = (x, y)
  }

  require(inputList.length > 0,
    "Empty list is not valid")
  require(inputList.length == Math.pow(size, 4),
    "A list should be dim*dim square")
  require(inputList.forall(i => i >= 0 && i <= size * size),
    "List numbers should be 0 to dim*dim")

  def isNotEmpty: Boolean = inputList.length > 0

  def isSquareShaped: Boolean = inputList.length == pow(size, 4)

  def hasOnlyLegalNumbers: Boolean = inputList.forall(i => i >= 0 || i <= size * size)

  //def getCellIndex(f: => Boolean) = field.
}

object Problem {
  def readFromFile(url: java.net.URL): Problem = {
    val source = scala.io.Source.fromURL(url)
    val problemString = try {
        source.getLines.toList
      }finally{
        source.close
      }
    val problemName = problemString.takeWhile(_.head == '#').mkString(" ")
    val problemList = problemString.dropWhile(_.head == '#').mkString(",").replaceAll(" ", "0").split(",").toList.map(_.toInt)
    new Problem(problemName, problemList)
  }
}