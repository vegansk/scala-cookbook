package scalameta.tutorial

import scala.meta._

object TrailingCommaSuite extends App {

  def stripTrailingCommas(tokens: Tokens): String = {
    val tokensI = tokens.zipWithIndex
    val filtered = tokensI.filter {
      case (t, i) => {
        t match {
          case Token.Comma() => {
            // If there are only commas, comments, line feeds or spaces starting from this position
            // until right paren, strip comma from tokens stream
            val retained= tokens.drop(i + 1).takeWhile(!_.isInstanceOf[Token.RightParen])
            !retained.forall(
              _ match {
                case Token.Comma() | Token.Comment(_) | Token.LF() | Token.Space() => true
                case _ => false
              })
          }
          case _ => true
        }
      }
    } map(_._1.syntax)
    filtered.mkString
  }

  def check(original: String, expected: String): Unit = {
    val obtained = stripTrailingCommas(original.tokenize.get)
    assert(obtained.trim == expected.trim)
  }

  check(
    """|function(
       | arg1,
       | arg2,
       |)""".stripMargin,
    """|function(
       | arg1,
       | arg2
       |)""".stripMargin)

  check(
    """|function(
       |  arg1,
       |// arg2,
       |)""".stripMargin,
    """|function(
       |  arg1
       |// arg2,
       |)""".stripMargin)
}
