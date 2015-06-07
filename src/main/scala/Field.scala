import scala.collection.immutable.IndexedSeq

/**
 * Created by Atsushi on 13/12/10.
 */
abstract class Field {
  val dimension: Int
  val nestLevel: Int
  val fieldName: String
  val whatIfFieldName: String
  var cells: List[Cell]
  val timesOfWhatIf: Int
  var maxNestLevel = 0
  lazy val rows = dimension * dimension
  lazy val rowsIndices: Range = 0 until rows
  lazy val grids = rows * rows
  lazy val possibleNumbers = (1 to rows).toSet

  private def makeFieldLikeString(func: (Cell) => String): String = {
    for {i <- 0 until rows} yield cells.map(func).slice(i * rows, (i + 1) * rows).mkString(" ")
  }.mkString(System.getProperty("line.separator"))

  override def toString = fieldName + " " + whatIfFieldName +
    System.getProperty("line.separator") + makeFieldLikeString(_.toString)

  def toCandidateString = makeFieldLikeString(_.candidateString)

  //同じスコープに同じ数字が入るとfalseを返す
  def isValid: Boolean = cells.filter(_.number > 0).forall(
    i => !cells.filter(_.number == i.number).exists(_.isSameScope(i)))

  private def updateNumber() {
    cells.foreach(_.update())
    //assert(this.cells forall (_.candidate.size > 0), "矛盾が発生しました。候補が0のセルがあります。")
  }

  def weakestCellIndices: List[Int] = {
    cells.filter(!_.isFixed).filter(
      i => i.candidate.size == cells.filter(!_.isFixed).map(_.candidate.size).min).map(_.index)
  }

  def simpleSolve(): Unit = {
    while (hasChanged) {
      updateNumber()
      println(this)
      println(this.toCandidateString)
      assert(this.isValid, "矛盾が発生しました。同じスコープに同じ数字が入っています。")
    }
  }

  private def hasChanged: Boolean = cells.exists(_.hasChanged)

  def isSolved: Boolean = cells.forall(_.number > 0)

  def finish(): Unit = {
    println("問題が解けました。")
    println(this)
  }

  def apply(a: Int, b: Int): Cell = apply(a * rows + b)

  def apply(a: Int): Cell = cells(a)
}

object Field {

  private class problemField(val input: Input) extends Field {
    val timesOfWhatIf = 0
    val problemNum: Int = input.problemNum
    val fieldName = "problem" + problemNum
    val whatIfFieldName = "normal"
    val dimension = input.dimension
    val nestLevel = 0
    var cells = input.inputList.indices.toList map (i => new Cell(dimension, i, input(i)))
  }

  private class whatIfField(val num: Int, val prevField: Field) extends Field {
    val timesOfWhatIf = num
    val dimension = prevField.dimension
    val nestLevel = prevField.nestLevel + 1
    val fieldName = prevField.fieldName
    val whatIfFieldName = "what-if field number = " + num + ", nest level = " + nestLevel
    var cells = prevField.cells.map(_.copy(field = this))
    val defaultCells = {
      updateNumber()
      cells
    }
    val targetCellIndices = prevField.weakestCellIndices

    def isContradicted: Boolean = {
      cells.exists(_.candidate.size == 0)
    }

    def selectTarget = {}

    def assumeNumber() = {
      for (targetCellIndex <- targetCellIndices) {
        val targetNumber: Int = cells(targetCellIndex).candidate.head
        val prevCells = defaultCells
        cells(targetCellIndex).number = targetNumber
        simpleSolve()
        if (isContradicted) {
          cells = prevCells
          cells(targetCellIndex).candidate -= targetNumber
          simpleSolve()
        }
      }
    }
  }

  def makeField(input: Input): Field = {
    val problem = new problemField(input)
    problem.updateNumber()
    assert(problem.isValid)
    problem
  }

  def solve(field: Field) {
    field.simpleSolve()
  }

  def whatIfSolve(prevField: Field): Unit = {
    println(
      """
        |**************************
        |*whatIf開始
        |**************************
      """.stripMargin)
    val whatIfField = new whatIfField(prevField.timesOfWhatIf + 1, prevField)
    whatIfField.assumeNumber()
    if (whatIfField.isSolved) whatIfField.finish()
    else whatIfSolve(whatIfField)
  }
}