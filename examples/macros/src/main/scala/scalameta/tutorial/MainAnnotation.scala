package scalameta.tutorial

import scala.meta._

class Main extends scala.annotation.StaticAnnotation {

  inline def apply(body: Any): Any = meta {
    body match {
      case q"object $name {..$stats }" =>
        val main = q"def main(args: Array[String]): Unit = {..$stats}"
        q"object $name { $main }"
      case _ =>
        abort("@main must annotate an object")
    }
  }

}
