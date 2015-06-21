package org.atsfour.sudoku

/**
 * Created by Atsushi on 13/12/10.
 */
case class Field(val size: Int, inputs: Seq[Int]) {

  lazy val rows = size * size
  lazy val rowsIndices: Range = 0 until rows
  lazy val possibleNumbers: Range = 1 to rows

  var cells: Seq[Cell] = inputs.map(Cell(size, _, true))
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

  def rowNum(index: Int): Int = index / rows
  def columnNum(index: Int): Int = index % rows
  def squareNum(index: Int): Int = (columnNum(index) / size).toInt +
    (rowNum(index) / size).toInt * size

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
