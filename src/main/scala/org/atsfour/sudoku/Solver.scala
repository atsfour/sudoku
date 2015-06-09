

package org.atsfour.sudoku
/**
 * @author atsfour
 */
class Solver {
  
  var cells: Seq[Cell]
  
  private def makeFieldLikeString(func: (Cell) => String): String = {
    for { i <- 0 until rows } yield cells.map(func).slice(i * rows, (i + 1) * rows).mkString(" ")
  }.mkString(System.getProperty("line.separator"))

  override def toString = fieldName + " " + whatIfFieldName +
    System.getProperty("line.separator") + makeFieldLikeString(_.toString)
  
  def candidate(i1: Int, i1: Int): Set[Int] = {}
  
  def toCandidateString = makeFieldLikeString(_.candidateString)
  
  def candidateString = {
    val candidateSeq = (1 to rows) map (i => if (candidate contains i) i.toString else " ")
    "|" + candidateSeq.mkString("").formatted("%9s")
  }
  
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
    
  private def updateNumber() {
    if (number == 0) {
      if (hasOnly1Candidate) {
        number = candidate.head
        println("Cell(" + rowNum + "," + columnNum + ")の数値が " + number +" に決定されました。")
      }
      else {
        candidate.foreach(i => if (onlyThisCellHasThisNumberInCandidates(i)) {
          this.number = i
          println("Cell(" + rowNum + "," + columnNum + ")の数値が " + number +" に決定されました。")
        })
      }
    }
  }

  private def updateCandidate() {
    if (!this.hasOnly1Candidate) {
      if (this.number > 0) {
        this.candidate = Set(this.number)
      }
      else {
        val prevCandidate = candidate
        candidate = possibleNumbers -- field.cells.toSet.filter(c => c.number > 0 && c.isSameScope(this)).map(_.number)
        val deletedNum = prevCandidate -- candidate
        if (deletedNum.nonEmpty) {
          println("Cell(" + rowNum + "," + columnNum + ") から，候補 " + deletedNum.mkString("") + " が削除されました")
        }
      }
    }
  }

  def update(): Cell = {
    prevCandidate = candidate
    updateCandidate()
    updateNumber()
    this
  }

  def hasChanged: Boolean = candidate != prevCandidate
  
  def apply(a: Int, b: Int): Cell = apply(a * rows + b)

  def apply(a: Int): Cell = cells(a)



}