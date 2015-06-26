package org.atsfour.sudoku
import org.scalatest._

class FieldTest() extends FlatSpec with Matchers {

  val f3 = Field(3, Seq.fill(81)(1))

  "isSameRow/isSameColumn/isSameSquare" should "3*3のFiledでは0〜8、27〜35などが同じ行" in {
    f3.isSameRow(0)(7) should be (true)
    f3.isSameRow(76)(80) should be (true)
    f3.isSameRow(0)(9) should be (false)
    Field(2, Seq.fill(16)(1)).isSameRow(0)(3) should be (true)
    Field(5, Seq.fill(625)(1)).isSameRow(0)(24) should be (true)
  }

  it should "同じ列"

  it should "自分自身は同じ行ではないとみなす" in {
    f3.isSameRow(0)(0) should be (false)
  }
}