import scala.scalajs.js
import js.annotation._

import japgolly.scalajs.react._
import org.scalajs.dom
import dom.raw.Element
import japgolly.scalajs.react.vdom.prefix_<^._

object ReactRedux {

  object Impl {

    @js.native
    trait ProviderProps[S, A] extends js.Object {
      val store: Redux.Store[S, A] = js.native
    }

    object ProviderProps {
      def apply[S, A](store: Redux.Store[S, A]): ProviderProps[S, A] =
        js.Dynamic.literal(
          store = store
        ).asInstanceOf[ProviderProps[S, A]]
    }

    @JSImport("react-redux", "Provider")
    @js.native
    class Provider[S, A] extends JsComponentType[ProviderProps[S, A], js.Any, Element] {}
  }

  object Provider {

    def apply[S, A](store: Redux.Store[S, A])(children: ReactNode*) =
      React.createFactory(new Impl.Provider[S, A]())(
        Impl.ProviderProps(store), children
      )

  }

}
