package oldmacros

import scala.language.experimental.macros
import scala.reflect.macros.Context

class Main extends scala.annotation.StaticAnnotation {

  def apply(defn: Any): Any = macro MainImpl.mainImpl

}

object MainImpl {
  def mainImpl(c: Context)(defn: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    reify(())
  }
}
