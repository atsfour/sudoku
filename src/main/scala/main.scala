import scala.math._
import scala.io.Source
import sudoku.Field._

/**
 * Created by Atsushi on 13/12/10.
 */
object main {

  def main(args: Array[String]) = {
    val problemList = readFile("src/main/resources/problem.txt")
    val Inputs = problemList.indices.map(i => new Input(i, problemList(i)))
    val problem = makeField(Inputs(0))
    solve(problem)
    //whatIfSolve(problem)
  }

  def readFile(file: String): List[List[Int]] = {
    val source = Source.fromFile(file)
    val problemList = source.getLines().toList.map(_.map(_.toString.toInt).toList)
    problemList
  }

  def debugPrint(problem: Field): Unit = {
    println(problem)
    println(problem.toCandidateString)
  }

}
