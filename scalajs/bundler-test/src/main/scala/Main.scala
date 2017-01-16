import scala.scalajs.js
import js.annotation._
import scala.scalajs.js.JSApp
import scala.scalajs.js.|

import japgolly.scalajs.react._
import org.scalajs.dom
import japgolly.scalajs.react.vdom.prefix_<^._

case class State(
  counter: Int
)

sealed trait Action;

case object Increase extends Action;

case object Decrease extends Action;

object Main extends JSApp {

  def reducer(s: State, a: Action): State =
    a match {
      case Increase =>
        s.copy(counter = s.counter + 1)
      case Decrease =>
        s.copy(counter = s.counter - 1)
    }

  def render =
    <.p("Hello, world!")

  private object Dependencies {

    @JSImport("react", JSImport.Namespace)
    @js.native
    object React extends js.Object {}

    @JSImport("react-dom", JSImport.Namespace)
    @js.native
    object ReactDOM extends js.Object {}

    def setup = {
      js.Dynamic.global.React = React
      js.Dynamic.global.ReactDOM = ReactDOM
    }
  }

  def main(): Unit = {

    Dependencies.setup

    val mountNode = dom.document.getElementById("root")

    val _ = ReactDOM.render(render(), mountNode)

  }

}
