package org.atsfour.sudoku

object AbstractUI {

  val reader = ProblemReader
  val message: StringBuilder = new StringBuilder

  var currentProblem = {
    val filename = "sample1.txt"
    reader.readFromFile(filename) match {
      case p: Some[Problem] => p.get
      case _ => {
        message.append(reader.message.toString())
        Problem.empty
      }
    }
  }

  def rows: Int = currentProblem.rows
  //ひとまず正方形の問題を扱うため、columns = rows が成立している。
  def columns: Int = currentProblem.rows

  def currentCellIndex: (Int, Int) = currentProblem.currentCellIndex
  def currentCellLinerIndex: Int = currentProblem.currentCellLinerIndex
  def currentCellValue: String = currentProblem.currentCellValue

  def numberString(x: Int, y: Int): String = currentProblem.field(x, y).numberString
  def currentCellNumberString: String = currentProblem.currentCell.numberString

  def leftKey(): Unit = currentProblem.moveLeft()
  def rightKey(): Unit = currentProblem.moveRight()
  def upKey(): Unit = currentProblem.moveUp()
  def downKey(): Unit = currentProblem.moveDown()

  def numKey(strNum: String): Unit = currentProblem.editCurrentCell(strNum, keepPrevious = true)

  def buckSpace(): Unit = currentProblem.editCurrentCell("0", keepPrevious = false)

  def mouseClick(x: Int, y: Int): Unit = currentProblem.moveTo(x, y)

  def updateMessage(m: String): Unit = {
    message.append(m + "\n")
  }
}

object ProblemReader {

  val message: StringBuilder = new StringBuilder

  def readFromFile(filename: String): Option[Problem] = {

    def findFile(filename: String): Option[scala.io.Source] = {
      val url: java.net.URL = this.getClass.getClassLoader.getResource(filename)
      url match {
        case x: java.net.URL => Some(scala.io.Source.fromURL(url))
        case _ => {
          message.append("問題ファイルが見つかりません")
          None
        }
      }
    }

    def readFile(source: scala.io.Source): Option[List[String]] = try {
      Some(source.getLines().toList)
    } catch {
      case e: Throwable => {
        message.append("ファイルを読み込めません")
        None
      }
    } finally {
      source.close()
    }

    def buildProblem(problemString: List[String]): Option[Problem] = {
      val problemName = problemString.takeWhile(_.head == '#').mkString(" ")
      val problemList = problemString.dropWhile(_.head == '#').mkString(",").replaceAll(" ", "0").split(",").toList.map(_.toInt)
      try{
        Some(Problem(problemName, problemList))
      } catch {
        case e: IllegalArgumentException => {
          message.append(e.getMessage)
          None
        }
      }
    }

    findFile(filename).flatMap(readFile).flatMap(buildProblem)

  }
}