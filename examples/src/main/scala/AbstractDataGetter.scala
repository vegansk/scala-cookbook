/**
  * The problem is that we have some type and we need to
  * to create getter that can return something that can be
  * translated to string
  */

object AbstractDataGetter extends App {

  type Getter[T,R] = Function1[T,R];

  case class Column[T,R](
    name: String,
    getter: Getter[T,R]
  )

  case class Row[T] (
    value: T
  )

  case class Props[T](
    columns: Seq[Column[T,_]],
    rows: Seq[Row[T]]
  )

  val mapGetter = (k: String) => (m: Map[String, String]) => m(k)

  val mapProps = Props[Map[String,String]](
    columns = (
      Column[Map[String,String],String]("ID", mapGetter("ID")) ::
        Column[Map[String,String],String]("NAME", mapGetter("NAME")) ::
        Nil
    ),
    rows = (
      Row(Map("ID" -> "1", "NAME" -> "Test")) ::
        Nil
    )
  )

  case class Data(id: Int, value: String)

  val dataProps = Props[Data](
    columns = (
      Column[Data,String]("ID", _.id.toString) ::
        Column[Data,String]("VALUE", _.value) ::
        Nil
    ),
    rows = List(Row(Data(1, "A")), Row(Data(2, "B")))
  )

  def printRows[T](p: Props[T]) = {
    p.rows.foreach(row => {
                     p.columns.foreach(col => {
                                         print(s"${col.name} = ${col.getter(row.value)}  ")
                                       })
                     println()
                   })
  }

  printRows(mapProps)
  printRows(dataProps)

}
