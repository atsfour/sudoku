package org.atsfour.sudoku

import scala.collection.immutable.IndexedSeq

/**
 * Created by Atsushi on 13/12/10.
 */
case class Field(val dimension: Int) {
  
  lazy val rows = dimension * dimension
  lazy val rowsIndices: Range = 0 until rows
  lazy val possibleNumbers = (1 to rows).toSet

  def position(x: Int, y: Int): Int = x * rows + y

  def rowNum(index: Int): Int = index / rows
  def columnNum(index: Int) = index % rows
  def squareNum(index: Int): Int = (columnNum(index) / dimension).toInt +
    (rowNum(index) / dimension).toInt * dimension

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

}