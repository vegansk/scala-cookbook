import scala.scalajs.js
import js.annotation._
import scala.scalajs.js.JSApp
import scala.scalajs.js.|

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

  def main(): Unit = {
    println("Create the store...")

    val store = Redux.createStore[State, Action](reducer _, State(0))

    println(s"Initial state is ${store.getState()}")

    store.dispatch(Redux.createAction(Increase))

    println(s"The state after increase action is ${store.getState()}")

    store.dispatch(Redux.createAction(Decrease))
    store.dispatch(Redux.createAction(Decrease))

    println(s"The state after two decrease actions is ${store.getState()}")
  }

}
