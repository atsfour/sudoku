/**
 * Created by Atsfour on 13/12/10.
 */

package org.atsfour.sudoku

case class Field(val size: Int, inputs: Seq[Int]) {

  private[this]type Index2d = (Int, Int)
  private[this]type IndexLiner = Int

  private[this] def isSquareShaped: Boolean = inputs.length == Math.pow(size, 4)
  private[this] def hasOnlyLegalNumbers: Boolean = inputs.forall(i => i >= 0 && i <= size * size)

  require(isSquareShaped, "問題のマス目の数が不正です")
  require(hasOnlyLegalNumbers, "不正な数が入ったマス目があります")

  lazy val rows = size * size
  lazy val rowsIndices: Range = 0 until rows
  lazy val possibleNumbers: Range = 1 to rows

  val cells: Seq[Cell] = inputs.map(i => Cell(i, i > 0))

  def indexLinerTo2d(index: IndexLiner): Index2d = {
    (this.rowIndex(index), this.columnIndex(index))
  }
  def index2dToLiner(index: Index2d): IndexLiner = {
    index._1 + rows * index._2
  }
  def findCellIndex(f: Cell => Boolean): Index2d = indexLinerTo2d(cells.indexWhere(f(_)))

  def apply(i: IndexLiner): Cell = this.cells(i)
  def apply(i: Index2d): Cell = this.apply(index2dToLiner(i))
  def apply(x:Int, y:Int): Cell = this.apply((x,y))

  def rowIndex(index: IndexLiner): Int = index / rows
  def columnIndex(index: IndexLiner): Int = index % rows
  def squareIndex(index: IndexLiner): Int = {
    (columnIndex(index) / size) + (rowIndex(index) / size) * size
  }

  def isSameRow(i1: IndexLiner)(i2: IndexLiner): Boolean = {
    //自分自身とは同じ行ではないとみなす。
    rowIndex(i1) == rowIndex(i2) && i1 != i2
  }

  def isSameColumn(i1: IndexLiner)(i2: IndexLiner): Boolean = {
    //自分自身とは同じ列ではないとみなす。
    columnIndex(i1) == columnIndex(i2) && i1 != i2
  }

  def isSameSquare(i1: IndexLiner)(i2: IndexLiner): Boolean = {
    //自分自身とは同じ正方形ではないとみなす。
    squareIndex(i1) == squareIndex(i2) && i1 != i2
  }

  def isSameScope(i1: Int, i2: Int): Boolean = {
    //同じ行or列or正方形なら同じスコープ。
    isSameRow(i1)(i2) || isSameColumn(i1)(i2) || isSameSquare(i1)(i2)
  }
/*
  def columnCells(i: Int): Seq[Cell] = {
     val indices = 
  }
*/
  def isSolved: Boolean = {
    false
  }

  /*
  def numbersInSameScope(index: Int): Set(Int) = {
    cells.zipWithIndex.filter((i, c) => )
  }
*/

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

}

object Field {

}
