package oldmacros

import scala.collection.mutable.Stack
import scala.language.experimental.macros
import scala.reflect.macros.Context

object Printf {

  def printf(format: String, args: Any*): Unit = macro PrintfImpl.printfImpl

}

object PrintfImpl {

  def printfImpl(c: Context)(format: c.Expr[String], args: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._

    val Literal(Constant(formatS: String)) = format.tree

    def mkValDef(v: Tree, t: Type): (Option[ValDef], Ident) = {
      val termName = TermName(c.freshName())
      (
        Some(ValDef(Modifiers(), termName, TypeTree(t), v)),
        Ident(termName)
      )
    }

    val paramStack = Stack[Tree](args.map(_.tree): _*)

    val (defsO, parts) = (formatS.split("(?<=%[\\w%])|(?=%[\\w%])") map {
      case "%d" => mkValDef(paramStack.pop(), typeOf[Int])
      case "%s" => mkValDef(paramStack.pop(), typeOf[String])
      case "%%" => (None, Literal(Constant("%")))
      case other => (None, Literal(Constant(other)))
    }).map(_ match { case (d, p) => (d, q"print($p)") }).unzip

    val defs = defsO.flatMap(_.toList)

    c.Expr[Unit](q"""
..${defs}
..${parts}
""")
  }

}
