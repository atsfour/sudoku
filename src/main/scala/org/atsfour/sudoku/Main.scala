package org.atsfour.sudoku

import scala.math._
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.event.EventHandler
import scalafx.geometry.{ Insets, Orientation, Pos }
import scalafx.scene.input.{ KeyEvent, KeyCode, MouseEvent, MouseButton }
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.control.{ Button, TextField, TextArea, Label }
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.{ FlowPane, GridPane }
import scalafx.scene.text.Font

/**
 * Created by Atsushi on 13/12/10.
 */
object Main extends JFXApp {
  val ui = new AbstracuUI

  val mainPaneWidth = 600.0
  val mainPaneHeight = 600.0
  val subPaneWidth = 200.0
  val subPaneHeight = 600.0

  class SudokuCell(val posX: Int, val posY: Int) extends TextField {
    editable = false
    padding = Insets(1)
    font = Font(40)
    alignment = Pos.CENTER
    onKeyPressed = (ke: KeyEvent) => {
      ke.code match {
        case KeyCode.LEFT => ui.leftKey
        case KeyCode.RIGHT => ui.rightKey
        case KeyCode.UP => ui.upKey
        case KeyCode.DOWN => ui.downKey
        case KeyCode.BACK_SPACE => ui.buckSpace
        case k if k.isDigitKey => ui.numKey(ke.getText())
        case k:KeyCode => ui.updateMessage(k.name)
        case _ => 
      }
      repaint
    }
    onMouseClicked = (me: MouseEvent) => {
      me.button match {
        case MouseButton.PRIMARY => ui.mouseClick(posX, posY)
        case _ =>
      }
      repaint
    }
  }

  val btEnd: Button = new Button {
    text = "end"
    padding = Insets(20)
    onAction = handle {
      javafx.application.Platform.exit()
    }
  }

  val messageBox = new TextArea {
    prefWidth = subPaneWidth
    prefHeight = subPaneHeight / 2
  }

  val mainPane: GridPane = {
    val pane = new GridPane { padding = Insets(10) }
    pane.maxWidth = mainPaneWidth
    pane.maxHeight = mainPaneHeight
    pane
  }

  val subPane = {
    val pane = new GridPane { padding = Insets(10) }
    pane.maxHeight = subPaneHeight
    pane.maxWidth = subPaneWidth
    pane.add(btEnd, 0, 0)
    pane.add(messageBox, 0, 1)
    pane
  }

  stage = new JFXApp.PrimaryStage {
    title.value = "scaka sudoku"
    width = 900
    height = 700
    scene = new Scene {
      content = new FlowPane {
        children ++= List(mainPane, subPane)
        orientation = Orientation.VERTICAL
        readProblem
        repaint
      }
    }
  }

  def repaint: Unit = {
    def currentCell: SudokuCell = mainPane.children(ui.currentCellLinerIndex)
    def moveFocus: Unit = {
      currentCell.requestFocus
    }
    def updateMessage: Unit = {
      this.messageBox.text = ui.message
    }
    def updateCurrentCell: Unit = {
      currentCell.text = ui.numberString()
    }
    updateCurrentCell
    moveFocus
    updateMessage
  }

  def readProblem: Unit = {
    mainPane.children.clear()
    for {
      y <- Range(0, ui.columns)
      x <- Range(0, ui.rows)
    } {
      val cell = new SudokuCell(x, y)
      cell.text = ui.numberString(x, y)
      mainPane.add(cell, x, y)
    }
    mainPane.autosize
  }
}