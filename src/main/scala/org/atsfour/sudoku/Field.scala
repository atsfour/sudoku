package org.atsfour.sudoku

/**
 * Created by Atsushi on 13/12/10.
 */
case class Field(val size: Int, inputs: Seq[Int]) {

  private[this]type Index2d = (Int, Int)
  private[this]type IndexLiner = Int

  private[this] def isSquareShaped: Boolean = inputs.length == Math.pow(size, 4)
  private[this] def hasOnlyLegalNumbers: Boolean = inputs.forall(i => i >= 0 || i <= size * size)

  require(isSquareShaped, "Problem should be size*size square")
  require(hasOnlyLegalNumbers, "Numbers should be 0 to size*size")

  lazy val rows = size * size
  lazy val rowsIndices: Range = 0 until rows
  lazy val possibleNumbers: Range = 1 to rows

  val cells: Seq[Cell] = inputs.map(i => Cell(size, i, i > 0))

  def indexLinerTo2d(index: IndexLiner): Index2d = {
    (this.rowNum(index), this.columnNum(index))
  }
  def index2dToLiner(index: Index2d): IndexLiner = {
    index._1 + rows * index._2
  }
  def findCellIndex(f: Cell => Boolean): Index2d = indexLinerTo2d(cells.indexWhere(f(_)))

  def apply(i: IndexLiner): Cell = this.cells(i)
  def apply(i: Index2d): Cell = this.apply(index2dToLiner(i))
  def apply(x:Int, y:Int): Cell = this.apply((x,y))

  /*
  var candidates: Seq[Set[Int]] = {
    cells.zipWithIndex.map( t => c._1.numberOp match{
      case Some(x) => Set(x)
      case _ => Set(0)
      })
  }
*/
  /*
  def isValidIndex(index: Int): Boolean = indices.contains(index)
  def isValidIndex(x: Int, y:Int): Boolean = {
    rowsIndices.contains(x) && rowsIndices.contains(y)
}
*/

  def rowNum(index: IndexLiner): Int = index / rows
  def columnNum(index: IndexLiner): Int = index % rows
  def squareNum(index: IndexLiner): Int = (columnNum(index) / size) +
    (rowNum(index) / size) * size

  def isSameRow(i1: Int)(i2: Int): Boolean = {
    //自分自身とは同じ行ではないとみなす。
    rowNum(i1) == rowNum(i2) && i1 != i2
  }

  def isSameColumn(i1: Int)(i2: Int): Boolean = {
    //自分自身とは同じ列ではないとみなす。
    columnNum(i1) == columnNum(i2) && i1 != i2
  }

  def isSameSquare(i1: Int)(i2: Int): Boolean = {
    //自分自身とは同じ正方形ではないとみなす。
    squareNum(i1) == squareNum(i2) && i1 != i2
  }

  def isSameScope(i1: Int, i2: Int): Boolean = {
    //同じ行or列or正方形なら同じスコープ。
    isSameRow(i1)(i2) || isSameColumn(i1)(i2) || isSameSquare(i1)(i2)
  }

  /*
  def numbersInSameScope(index: Int): Set(Int) = {
    cells.zipWithIndex.filter((i, c) => )
  }
*/

}

object Field {

}