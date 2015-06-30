package org.atsfour.sudoku

class AbstracuUI {

  var currentProblem = {
    val filename = "sample1.txt"
    val res = Problem.readFromFile(
      this.getClass.getClassLoader.getResource(filename))
    res
  }

  def rows:Int = currentProblem.rows
  //ひとまず正方形の問題を扱うため、columns = rows が成立している。
  def columns: Int = currentProblem.rows

  def message: String = currentProblem.message.toString()

  def currentCellIndex: (Int, Int) = currentProblem.currentCellIndex
  def currentCellLinerIndex: Int = currentProblem.currentCellLinerIndex

  def numberString(x: Int, y: Int): String = currentProblem.field(x, y).numberString
  def currentCellNumberString: String = currentProblem.currentCell.numberString

  def leftKey(): Unit = currentProblem.moveLeft()
  def rightKey(): Unit = currentProblem.moveRight()
  def upKey(): Unit = currentProblem.moveUp()
  def downKey(): Unit = currentProblem.moveDown()

  def numKey(strNum :String): Unit = currentProblem.editCurrentCell(strNum, keepPrevious = true)

  def buckSpace(): Unit = currentProblem.editCurrentCell("0", keepPrevious = false)

  def mouseClick(x:Int, y:Int): Unit = currentProblem.moveTo(x, y)

  def updateMessage(m: String): Unit = {
    currentProblem.message.append(m + "\n")
  }
}