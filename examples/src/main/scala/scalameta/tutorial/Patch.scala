package scalameta.tutorial

import scala.meta._

case class Patch(from: Token, to: Token, replace: String) {
  def inside(t: Token): Boolean = t.start >= from.start && t.end <= to.end
  val tok = replace.tokenize.get.tokens.toSeq

  def runOn(v: Seq[Token]): Seq[Token] =
    v.flatMap {
      case `from` => tok
      case x if inside(x) => Nil
      case x => Seq(x)
    }
}

object Patch {
  def run(input: Seq[Token], patches: Seq[Patch]): String =
    patches.foldLeft(input)((i, p) => p.runOn(i))
      .map(_.syntax)
      .mkString
}
