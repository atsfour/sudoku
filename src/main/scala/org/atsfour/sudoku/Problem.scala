package org.atsfour.sudoku

import scala.math._

/**
 * Created by Atsushi on 13/12/12.
 */

case class Problem(val name: String, inputList: List[Int]) {

  val size = sqrt(sqrt(inputList.length)).toInt
  val rows = size * size
  val fieldOp: Option[Field] = try {
    Some(Field(size, inputList))
  } catch {
    case e: IllegalArgumentException => {
      message.append(e.getMessage())
      None
    }
  }

  var currentCellIndex = field.findCellIndex((c: Cell) => c.isFixed)
  var editing: Boolean = false
  val message: StringBuilder = new StringBuilder()


  def field: Field = fieldOp.get

  def currentCell: Cell = field(currentCellIndex)
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
  def readFromFile(url: java.net.URL): Problem = {
    val source = scala.io.Source.fromURL(url)
    try {
      val problemString = source.getLines().toList
      val problemName = problemString.takeWhile(_.head == '#').mkString(" ")
      val problemList = problemString.dropWhile(_.head == '#').mkString(",").replaceAll(" ", "0").split(",").toList.map(_.toInt)
      Problem(problemName, problemList)
    } catch {
      case e: Throwable => Problem.empty
    } finally {
      source.close()
    }
  }

  def empty = Problem("empty", List(0))

}