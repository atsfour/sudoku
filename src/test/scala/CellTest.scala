
package org.atsfour.sudoku

import org.scalatest._

class CellSpec extends FlatSpec with Matchers {

  "numberOp" should "オプション値が入る" in {
    Cell(3, 9, false).numberOp should be (Some(9))
    Cell(3, 0, false).numberOp should be (None)
  }

  it should "範囲外の数値に対してはエラーを返す" in {
    an [IllegalArgumentException] should be thrownBy Cell(3, -1, false)
    an [IllegalArgumentException] should be thrownBy Cell(2, 5, false)
  }

  "numberWritten" should "0以外の数値が与えられると、そのセルは埋まったとする" in {
    Cell(3, 0, false).numberWritten should be (false)
    Cell(3, 4, false).numberWritten should be (true)
  }

  "numberSrting" should "入っている数字の文字列を返す" in {
    Cell(3, 5, false).numberString should be ("5")
  }
}