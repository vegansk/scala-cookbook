import scala.scalajs.js
import js.annotation._
import scala.scalajs.js.JSApp

import japgolly.scalajs.react._
import org.scalajs.dom
import japgolly.scalajs.react.vdom.prefix_<^._

case class State(
  counter: Int
)

sealed trait Action;

case object Increase extends Action;

case object Decrease extends Action;

object App {

  object StateDisplay {

    case class Props(counter: Int)

    val component = ReactComponentB[Props]("StateDisplay")
      .render_P { props =>
        <.span()(s"Clicked: ${props.counter} times")
      }
      .build

    def apply(counter: Int) = component(Props(counter))

    val factory = ReactRedux.createFactory(
      (s: State, _: Redux.Dispatcher[_]) => Props(s.counter)
    )(component.reactClass)

    def connected(counter: Int) = factory(Props(counter))
  }

  object Button {

    case class Props(value: String, onClick: () => Unit)

    val component = ReactComponentB[Props]("Button")
      .render_P { props =>
        <.button(
          ^.onClick --> Callback {
            props.onClick()
          }
        )(props.value)
      }
      .build

    def apply(value: String, onClick: () => Unit) = component(Props(value, onClick))

    def connected(value: String, click: Redux.Dispatcher[Action] => Unit) =
      ReactRedux.createFactory(
        (s: State, d: Redux.Dispatcher[Action]) => Props(
          value = value,
          onClick = () => click(d)
        )
      )(component.reactClass)(Props("", () => ()))

  }

  def apply(store: Redux.Store[State, Action]) = {
    val ch: List[TagMod] = List(
      StateDisplay.connected(0),
      <.br(),
      Button.connected("-", d => d(Decrease)),
      Button.connected("+", d => d(Increase))
    )
    ReactRedux.Provider(store)(
      <.div()(ch: _*)
    )
  }

}

object Main extends JSApp {

  def reducer(s: State, a: Action): State =
    a match {
      case Increase =>
        s.copy(counter = s.counter + 1)
      case Decrease =>
        s.copy(counter = s.counter - 1)
    }

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

    val store = Redux.createStore[State, Action](reducer _, State(1))

    val _ = ReactDOM.render(App(store), mountNode)

  }

}
