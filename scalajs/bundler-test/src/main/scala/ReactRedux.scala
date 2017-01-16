import scala.scalajs.js
import js.annotation._

import japgolly.scalajs.react._
import org.scalajs.dom
import dom.raw.Element
import japgolly.scalajs.react.vdom.prefix_<^._

object ReactRedux {

  object Impl {

    @js.native
    trait ProviderProps extends js.Object {
      val store: js.Any = js.native
    }

    object ProviderProps {
      def apply(store: js.Any): ProviderProps =
        js.Dynamic.literal(
          store = store
        ).asInstanceOf[ProviderProps]
    }

    @JSImport("react-redux", "Provider")
    @js.native
    object Provider extends JsComponentType[ProviderProps, js.Any, Element]

  }

  object Provider {

    def apply[S, A](store: Redux.Store[S, A])(child: ReactNode) =
      React.createFactory(Impl.Provider)(
        Impl.ProviderProps(store), child
      )

  }

}
