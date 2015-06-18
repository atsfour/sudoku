package org.atsfour.sudoku

import org.scalatest._

class CellSpec extends FlatSpec with Matchers {

  "numberOp" should "オプション値が入る" in {
    Cell(3, 9).numberOp should be (Some(9))
    Cell(3, 0).numberOp should be (None)
  }

  it should "範囲外の数値に対してはエラーを返す" in {
    an [IllegalArgumentException] should be thrownBy Cell(3, -1)
    an [IllegalArgumentException] should be thrownBy Cell(2, 5)
  }

  "isFixed" should "0以外の数値が与えられると、そのセルは決定済みとする" in {
    Cell(3,0).isFixed should be (false)
    Cell(3,4).isFixed should be (true)
  }
}