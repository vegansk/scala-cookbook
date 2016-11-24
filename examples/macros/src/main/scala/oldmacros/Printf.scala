package oldmacros

import scala.language.experimental.macros
import scala.reflect.macros.Context

object Printf {

  def printf(format: String, args: Any*): Unit = macro PrintfImpl.printfImpl

}

object PrintfImpl {

  def printfImpl(c: Context)(format: c.Expr[String], args: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._
    // val Literal(Constant(formatStr: String)) = format
    c.Expr[Unit](q"""println("Hello, world!")""")
  }

}
