import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSImport

@JSImport("redux", JSImport.Namespace)
@js.native
object Redux extends js.Object {

  type Reducer[S, A] = js.Function2[S, A, S]

  type StateGetter[S] = js.Function0[S]

  type Dispatcher[A] = js.Function1[A, js.Any]

  @js.native
  trait MiddlewareArg[S, A] extends js.Object {
    val getState: StateGetter[S] = js.native
    val dispatch: Dispatcher[A] = js.native
  }

  type Middleware[S, A] = js.Function1[MiddlewareArg[S, A], js.Function1[Dispatcher[A], Dispatcher[A]]]

  @js.native
  trait Store[S, A] extends js.Object {
  }

  type Enhancer[S, A] = js.Function1[Store[S, A], Store[S, A]]

  def createStore[S, A](
    reducer: Reducer[S, A],
    initialState: js.UndefOr[S],
    enhancer: js.UndefOr[Enhancer[S, A]]
  ): Store[S, A] = js.native

}
