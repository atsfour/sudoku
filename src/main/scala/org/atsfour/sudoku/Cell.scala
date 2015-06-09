package org.atsfour.sudoku

//import scala.collection.mutable.{Map => MMap, Set => MSet}

/**
 * Created by Atsushi on 13/12/10.
 */
case class Cell(val number: Int) {
  
  def numberOp: Option[Int] = this.number match{
    case x:Int if x > 0 => Some(x)
    case x:Int if x == 0 => None
  }

  def isFixed: Boolean = {
    number > 0
  }
}
