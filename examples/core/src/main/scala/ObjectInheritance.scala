trait Base {

  def say: Unit

  def apply() = this.say

}

object Base extends Base {

  def say = {
    println("Hello from base")
  }

}

object Child extends Base {

  def say = { println("Hello from child") }

}

object ObjectInheritance {

  def main(args: Array[String]) = {
    Base()
    Child()
  }

}
