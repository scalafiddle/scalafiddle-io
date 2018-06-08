import fiddle.Fiddle, Fiddle.println
import scalajs.js

@js.annotation.JSExportTopLevel("ScalaFiddle")
object ScalaFiddle {
  // $FiddleStart
  import slinky.core._
  
  import slinky.web.html._
  import slinky.web.ReactDOM
  
  import scala.scalajs.js
  import scala.scalajs.js.annotation.ScalaJSDefined
  
  import scala.scalajs.js.Date
  import org.scalajs.dom.raw.{Event, HTMLInputElement}
  
  case class TodoItem(text: String, id: Long)
  
  object TodoList extends StatelessComponentWrapper {
    case class Props(items: Seq[TodoItem])

    @ScalaJSDefined
    class Def(jsProps: js.Object) extends Definition(jsProps) {
      override def render() = {
        ul(props.items.map { item =>
          li(key := item.id.toString)(item.text)
        })
      }
    }
  }

  object TodoApp extends ComponentWrapper {
    type Props = Unit
    case class State(items: Seq[TodoItem], text: String)

    @ScalaJSDefined
    class Def(jsProps: js.Object) extends Definition(jsProps) {
      override def initialState = State(Seq.empty, "")

      def handleChange(e: Event): Unit = {
        val eventValue = e.target.asInstanceOf[HTMLInputElement].value
        setState(_.copy(text = eventValue))
      }

      def handleSubmit(e: Event): Unit = {
        e.preventDefault()

        if (state.text.nonEmpty) {
          val newItem = TodoItem(
            text = state.text,
            id = Date.now().toLong
          )

          setState(prevState => {
            State(
              items = prevState.items :+ newItem,
              text = ""
            )
          })
        }
      }

      override def render() = {
        div(
          h3("TODO"),
          TodoList(TodoList.Props(items = state.items)),
          form(onSubmit := handleSubmit _)(
            input(onChange := handleChange _, value := state.text),
            button(s"Add #${state.items.size + 1}")
          )
        )
      }
    }
  }

  ReactDOM.render(TodoApp(), Fiddle.panel)
  // $FiddleEnd
}
