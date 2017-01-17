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

    type SelectorFactory[S, A, P] = js.Function2[Redux.Dispatcher[A], js.UndefOr[js.Any], js.Function2[S, P, P]]

    @JSImport("react-redux", JSImport.Namespace)
    @js.native
    object Funcs extends js.Object {
      def connectAdvanced[S, A, P](selectorFactory: SelectorFactory[S, A, P]): js.Function1[ReactComponentC.ReqProps[P, _, _, Element], ReactComponentC.ReqProps[P, _, _, Element]] = js.native
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

  type Connector[S, A, P] = Function2[S, Redux.Dispatcher[A], P]

  def apply[S, A, P](connector: Connector[S, A, P])(comp: ReactComponentC.ReqProps[P, _, _, Element]): ReactComponentC.ReqProps[P, _, _, Element] = {
    def mkConnector(dispatch: Redux.Dispatcher[A]): js.Function2[S, P, P] =
      (state: S, _: P) => connector(state, dispatch)

    val f: Impl.SelectorFactory[S, A, P] =
      (dispatch: Redux.Dispatcher[A], _: js.UndefOr[js.Any]) => mkConnector(dispatch)

    Impl.Funcs.connectAdvanced[S, A, P](f)(comp)
  }

}
