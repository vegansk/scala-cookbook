package scalameta.tutorial

import scala.meta._
import Token._

object BalancedSuite extends App {

  def isBalanced(tokens: Tokens): Boolean = {
    var stack = List.empty[Token]
    var balanced = true

    def isMatch(x: Token, y: Token): Boolean = (x, y) match {
      case (LeftParen(), RightParen()) => true
      case (LeftBrace(), RightBrace()) => true
      case (LeftBracket(), RightBracket()) => true
      case _ => false
    }

    tokens.foreach(
      _ match {
        case v@(LeftParen() | LeftBrace() | LeftBracket()) =>
          stack = v :: stack
        case v@(RightParen() | RightBrace() | RightBracket()) =>
          stack match {
            case Nil => balanced = false
            case x::xs => {
              stack = xs
              if(!isMatch(x, v))
                balanced = false
            }
          }
        case _ =>
      }
    )

    balanced && stack.isEmpty
  }

  def check(s: String) = assert(isBalanced(s.tokenize.get))
  def checkNot(s: String) = assert(!isBalanced(s.tokenize.get))

  checkNot("{")
  check("{}")
  checkNot("}{")
  check("{}{}{}")
  checkNot("}{}{}")
  check("[](){}")
  checkNot("[(){}")
  checkNot("[]){}")
  checkNot("[]()}")

  checkNot("(}")
  checkNot("(][)")
  checkNot("{(})")

  checkNot(""" val x = "{" + `{` + }  `}` """)

  check("val x = { 2 }")
  check("""|def x = {
           |  List(1, 2).map { case x => x }
           |}
           |""".stripMargin)
  checkNot("""|def x =
              |  List(1, 2).map { case x => x }
              |}
              |""".stripMargin)
  check("val x = { function(2) }")
  check("""|def foo[T](args: T*): Unit = {
           |  foo(bar(kaz[T](args:_*)))
           |}
           |""".stripMargin)
  checkNot("""|def fooT](args: T*): Unit = {
              |  foo(bar(kaz[T](args:_*)))
              |}
              |""".stripMargin)
  checkNot("""|def fooT](args: T*): Unit = {
              |  foo(bar(kaz[T](args:_*
              |}
              |""".stripMargin)
  checkNot("""|def foo[T](args: T*): Unit = {
              |  foo(bar(kaz[T(args:_*))
              |}
              |""".stripMargin)

}
