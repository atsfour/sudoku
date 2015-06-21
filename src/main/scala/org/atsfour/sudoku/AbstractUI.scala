package org.atsfour.sudoku

class AbstracuUI {

  var currentProblem = Problem.readFromFile(
    this.getClass.getClassLoader.getResource("sample1.txt")
    )
  var currentCell = (0, 0)

  def leftKey {
  }
}