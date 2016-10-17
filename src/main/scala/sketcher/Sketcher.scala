package sketcher

import javafx.scene.text.FontWeight

import org.scalajs.dom.html
import paperjs.Projects.{FrameEvent, View}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import paperjs._
import rx._
import Basic._
import Paths._
import Styling._
import Tools._
import paperjs.Items.{Item, Shape}
import paperjs.Typography.PointText


@JSExport
class Button(x: Int, y: Int, name: String)(onClick: (Item, ToolEvent) => Unit) {

  val width = name.length * 12
  val btn = Shape.Rectangle(new Rect(x, y, width, 15), 3)
  btn.strokeColor = Color("grey")
  btn.fillColor = Color("white")
  btn.onMouseEnter = (item: Item, event: ToolEvent) => btn.fillColor = Color(0.9, 1.0)
  btn.onMouseLeave = (item: Item, event: ToolEvent) => btn.fillColor = Color("white")
  btn.onClick = onClick

  val text = new PointText(new Point(x + 10, y + 10))
  text.content = name
  text.fontFamily = "monospace"

  btn.addChild(text)
}

@JSExport
class MatrixView(x: Int, y: Int, m: Array[Array[Int]]) {

  val cellSize = new Size(20, 20)
  val cells = Array.ofDim[Shape](4, 4)

  init

  def sumMatrix(m: Array[Array[Int]]) = m.map(_.sum).sum

  @JSExport
  def init = {

    m.zipWithIndex.foreach { case (row: Array[Int], i: Int) =>
      row.zipWithIndex.foreach { case (cell: Int, j: Int) =>

        val point = Point(x + (i * cellSize.width), y + (j * cellSize.height))
        val shape = Shape.Rectangle(point, cellSize)
        shape.strokeColor = Color(255, 0, 0, 0.2)
        shape.fillColor = Color("white")

        shape.onClick = (item: Item, event: ToolEvent) => {
          m(i)(j) += 1
          refresh
        }

        cells(i)(j) = shape

      }
    }
  }

  @JSExport
  def refresh = {

    m.zipWithIndex.foreach { case (row: Array[Int], i: Int) =>
      row.zipWithIndex.foreach { case (cell: Int, j: Int) =>
        val c = m(i)(j) / sumMatrix(m).toDouble + 0.0001
        cells(i)(j).fillColor = Color(1.0 - c, 1.0)
      }
    }

  }

}

@JSExport
object Sketcher {

  @JSExport
  def main(canvas: html.Canvas): Unit = {

    val ex1 = Array.ofDim[Int](4, 4)
    val ex2 = Array.ofDim[Int](4, 4)
    val ex3 = Array.ofDim[Int](4, 4)

    Paper.setup(canvas)
    val width, height = Var(0.0)
    initEx()

    def dump(ms: Array[Array[Int]]*) = {
      ms.foreach { m =>
        println("[")
        println("\t" + m.map(_.mkString(",")).mkString("\n\t"))
        println("]")
      }
      println("")
    }

    def initEx() = {
      val mv1 = new MatrixView(100, 50, ex1)
      val mv2 = new MatrixView(200, 50, ex2)
      val mv3 = new MatrixView(300, 50, ex3)

      val dumpBtn = new Button(500, 100, "Dump")((i: Item, e: ToolEvent) => dump(ex1, ex2, ex3))

    }

    Paper.view.onResize = (view: View, event: FrameEvent) => {
      initEx()
    }

  }

}


