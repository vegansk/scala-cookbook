package scalameta.tutorial

import scala.meta._

object NonFatal extends App {

  def rewrite(s: String): Option[String] = {
    s.parse[Source] match {
      case Parsed.Success(t) =>
        val patches = t.collect {
          case c @ p"case $v: Throwable => $b" =>
            val pat = c.asInstanceOf[Case].pat
            Patch(pat.tokens.head, pat.tokens.last, s"NonFatal(${v.syntax})")
        }
        Some(Patch.run(t.tokens, patches))
      case _ => None
    }
  }

  def check(f: String, t: String): Unit = {
    val r = rewrite(f)
    assert(r.map(_.toString == t).getOrElse(false))
  }

  check(
    """|object a {
       |  x match {
       |    case bar: Throwable => println(bar)
       |  }
       |}
       |""".stripMargin,
    """|object a {
       |  x match {
       |    case NonFatal(bar) => println(bar)
       |  }
       |}
       |""".stripMargin
  )

  check(
    """|
       |object a {
       |  // comment
       |  try danger()
       |
       |
       |  catch {
       |    case e :      Throwable => // comment
       |      println(e)
       |  }
       |} """.stripMargin,
    """|
       |object a {
       |  // comment
       |  try danger()
       |
       |
       |  catch {
       |    case NonFatal(e) => // comment
       |      println(e)
       |  }
       |} """.stripMargin
  )

}
