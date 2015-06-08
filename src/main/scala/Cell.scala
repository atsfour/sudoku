//import scala.collection.mutable.{Map => MMap, Set => MSet}

/**
 * Created by Atsushi on 13/12/10.
 */
case class Cell(val dimension: Int, val index: Int, val number: Int) {
  private val rows = dimension*dimension
  private val grids = rows*rows
  private val possibleNumbers = (1 to rows).toSet
  var candidate: Set[Int] = possibleNumbers
  var prevCandidate: Set[Int] = Set.empty

  def Same(that: Cell) = {
    this.number == that.number
  }

  override def toString = number.toString

  private def hasOnly1Candidate: Boolean = this.candidate.size == 1

  private def onlyThisCellHasThisNumberInCandidates(num: Int): Boolean = {
    this.candidate.contains(num) && (
      !field.cells.filter(_.isSameRow(this)).exists(_.candidate.contains(num)) ||
        !field.cells.filter(_.isSameColumn(this)).exists(_.candidate.contains(num)) ||
        !field.cells.filter(_.isSameSquare(this)).exists(_.candidate.contains(num))
      )
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

  def isFixed: Boolean = {
    number > 0
  }
}
