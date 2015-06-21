package org.atsfour.sudoku

import scala.math._
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.control.{ Button, TextField, Cell => fxCell }
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.{ FlowPane, GridPane }
import scalafx.scene.text.Font

/**
 * Created by Atsushi on 13/12/10.
 */
object Main extends JFXApp {
  val ui = new AbstracuUI
  val field = ui.currentProblem.field

  val mainPaneWidth = 600.0
  val mainPaneHeight = 600.0
  val subPaneWidth = 200.0
  val subPaneHeight = 600.0

  val fieldBoxes: Seq[TextField] = {
    val cellBoxex = field.cells.map(c =>
      new TextField {
        text = c.numberOp.getOrElse(0).toString
        padding = Insets(1)
        prefWidth = mainPaneWidth / field.rows
        prefHeight = mainPaneHeight / field.rows
        font = Font(40)
      })
    cellBoxex
  }

  val btEnd: Button = new Button {
    text = "end"
    padding = Insets(20)
    onAction = handle {
      javafx.application.Platform.exit()
    }
  }

  val messageBox = new TextField{
    prefWidth = subPaneWidth
    prefHeight = subPaneHeight / 2
  }

  val mainPane: GridPane = {
    val pane = new GridPane { padding = Insets(10) }
    pane.maxWidth = mainPaneWidth
    pane.maxHeight = mainPaneHeight
    fieldBoxes.zipWithIndex.foreach {
      case (c, i) => {
        pane.add(c, (i % field.rows), (i / field.rows).toInt)
      }
    }
    pane
  }

  val subPane = {
    val pane = new GridPane {padding = Insets(10)}
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
      }
    }
  }

}
