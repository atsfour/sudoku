//import scala.collection.mutable.{Map => MMap, Set => MSet}

/**
 * Created by Atsushi on 13/12/10.
 */
case class Cell(val dimension: Int, val index: Int, var number: Int) {
  private val rows = dimension*dimension
  private val grids = rows*rows
  private val possibleNumbers = (1 to rows).toSet
  val rowNum = index / rows
  val columnNum = index % rows
  val squareNum = columnNum / dimension + (rowNum / dimension) * dimension
  var candidate: Set[Int] = possibleNumbers
  var prevCandidate: Set[Int] = Set.empty

  def Same(that: Cell) = {
    this.number == that.number
  }

  override def toString = number.toString

  def candidateString = {
    val candidateSeq = (1 to rows) map (i => if (candidate contains i) i.toString else " ")
    "|" + candidateSeq.mkString("").formatted("%9s")
  }

  private def isSameRow(that: Cell): Boolean = {
    //自分自身とは同じ行ではないとみなす。
    this.rowNum == that.rowNum && this.index != that.index
  }

  private def isSameColumn(that: Cell): Boolean = {
    //自分自身とは同じ列ではないとみなす。
    this.columnNum == that.columnNum && this.index != that.index
  }

  private def isSameSquare(that: Cell): Boolean = {
    //自分自身とは同じ正方形ではないとみなす。
    this.squareNum == that.squareNum && this.index != that.index
  }

  def isSameScope(that: Cell): Boolean = {
    //同じ行or列or正方形なら同じスコープ。
    this.isSameRow(that) || this.isSameColumn(that) || this.isSameSquare(that)
  }

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
