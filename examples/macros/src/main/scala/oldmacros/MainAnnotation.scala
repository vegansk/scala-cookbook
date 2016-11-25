package oldmacros

import scala.language.experimental.macros
import scala.reflect.macros.Context

class Main extends scala.annotation.StaticAnnotation {

  def macroTransform(annottees: Any*): Any = macro MainImpl.mainImpl

}

object MainImpl {
  def mainImpl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val inputs = annottees.map(_.tree).toList

    val res: List[Tree] = inputs match {
      case q"object $name { $body }" :: rest => {
        val main = q"def main(args: Array[String]): Unit = { $body }"
        q"object $name { $main }" :: rest
      }
      case _ => inputs
    }

    c.Expr[Any](q"..$res")
  }
}
