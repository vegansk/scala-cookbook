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

    type SelectorFactory[S, A, P] = js.Function2[Redux.Dispatcher[Redux.WrappedAction], js.UndefOr[js.Any], js.Function2[S, WrapObj[P], WrapObj[P]]]

    @JSImport("react-redux", JSImport.Namespace)
    @js.native
    object Funcs extends js.Object {
      def connectAdvanced[S, A, P, C <: ReactClass[P, _, _, Element]](selectorFactory: SelectorFactory[S, A, P]): js.Function1[C, C] = js.native
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

  type Connector[S, A, P] = Function2[S, Redux.Dispatcher[Redux.WrappedAction], P]

  def connect[S, A, P, C <: ReactClass[P, _, _, Element]](connector: Connector[S, A, P])(cls: C): C = {
    def mkConnector(dispatch: Redux.Dispatcher[Redux.WrappedAction]): js.Function2[S, WrapObj[P], WrapObj[P]] =
      (state: S, _: WrapObj[P]) => WrapObj(connector(state, dispatch))

    val f: Impl.SelectorFactory[S, A, P] =
      (dispatch: Redux.Dispatcher[Redux.WrappedAction], _: js.UndefOr[js.Any]) => mkConnector(dispatch)

    Impl.Funcs.connectAdvanced(f)(cls)
  }

}
