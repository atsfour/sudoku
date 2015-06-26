package org.atsfour.sudoku

class AbstracuUI {

  var currentProblem = Problem.readFromFile(
    this.getClass.getClassLoader.getResource("sample1.txt")
    )

  var message: String = ""

  def currentCell:Int = currentProblem.getCurrentCell

  def leftKey: Unit = currentProblem.moveLeft
  def rightKey: Unit = currentProblem.moveRight
  def upKey: Unit = currentProblem.moveUp
  def downKey: Unit = currentProblem.moveDown

  def keyNum: Unit = {

  }

  def mouseClick: Unit = {

  }

  def updateMessage(m: String): Unit = {
    message = message + m + "\n"
  }
}