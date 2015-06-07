import scala.math._

/**
 * Created by Atsushi on 13/12/12.
 */
class Input(val problemNum: Int, val inputList: List[Int]) {
  val dimension = sqrt(sqrt(inputList.length)).toInt
  require(inputList.length > 0,
    "Empty list is not valid")
  require(inputList.length == Math.pow(dimension, 4),
    "A list should be dim*dim square")
  require(inputList.forall(i => i >= 0 && i <= dimension * dimension),
    "List numbers should be 0 to dim*dim")

  def apply(i: Int) = inputList(i)

  def isNotEmpty: Boolean = inputList.length > 0

  def isSquareShaped: Boolean = inputList.length == Math.pow(dimension, 4)

  def hasOnlyLegalNumbers: Boolean = inputList.forall(i => i >= 0 || i <= dimension * dimension)
}