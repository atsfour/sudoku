package org.atsfour.sudoku

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.{Scene, Node}
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseButton, MouseEvent}
import scalafx.scene.layout.{FlowPane, GridPane, TilePane}
import scalafx.scene.text.Font

/**
 * Created by Atsushi on 13/12/10.
 */
object Main extends JFXApp {
  val ui = AbstractUI

  val mainPaneWidth = 600.0
  val mainPaneHeight = 600.0
  val subPaneWidth = 200.0
  val subPaneHeight = 600.0

  class SudokuCell(val posX: Int, val posY: Int) extends TextField {
    id = s"Cell(${posX},${posY})"
    editable = false
    padding = Insets(1)
    alignment = Pos.CENTER
    font = Font(40)
    prefWidth = mainPaneWidth * 0.9 / ui.columns
    prefHeight = mainPaneHeight * 0.9 / ui.rows
    onKeyPressed = (ke: KeyEvent) => {
      ke.code match {
        case KeyCode.LEFT => ui.leftKey()
        case KeyCode.RIGHT => ui.rightKey()
        case KeyCode.UP => ui.upKey()
        case KeyCode.DOWN => ui.downKey()
        case KeyCode.BACK_SPACE =>
          ui.buckSpace()
          this.text = ""
        case k if k.isDigitKey =>
          ui.numKey(ke.getText)
          this.update()
        case k:KeyCode => ui.updateMessage(currentCell.id.get)
        case _ => 
      }
      repaint()
    }
    onMouseClicked = (me: MouseEvent) => {
      me.button match {
        case MouseButton.PRIMARY => ui.mouseClick(posX, posY)
        case _ =>
      }
      repaint()
    }
    def update(): Unit = this.text = ui.numberString(posX, posY)
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
    wrapText = true
  }

  val mainPane: TilePane = {
    val pane = new TilePane { padding = Insets(10) }
    pane.prefRows = ui.rows
    pane.prefColumns = ui.columns
    pane.maxHeight = mainPaneHeight
    pane.maxWidth = mainPaneWidth
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
        readProblem()
        repaint()
      }
    }
  }

  def currentCell:Node = mainPane.children(ui.currentCellLinerIndex)

  def repaint(): Unit = {
    def moveFocus(): Unit = {
      currentCell.requestFocus()
    }
    def updateMessage(): Unit = {
      this.messageBox.text = ui.message.result()
    }
    def updateCell(): Unit = currentCell match {
      case t: TextField => t.text = ui.currentCellValue
      case _ =>
    }

    moveFocus()
    updateCell()
    updateMessage()
  }

  def readProblem(): Unit = {
    mainPane.children.clear()
    for {
      y <- Range(0, ui.columns)
      x <- Range(0, ui.rows)
    } {
      val cell = new SudokuCell(x, y)
      cell.text = ui.numberString(x, y)
      mainPane.children += cell
    }
    mainPane.autosize()
  }
}