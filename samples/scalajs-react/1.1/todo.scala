import fiddle.Fiddle, Fiddle.println
import scalajs.js

@js.annotation.JSExportTopLevel("ScalaFiddle")
object ScalaFiddle {
  // $FiddleStart
  import japgolly.scalajs.react._, vdom.html_<^._
  
  val TodoList = ScalaFnComponent[List[String]]{ props =>
      def createItem(itemText: String) = <.li(itemText)
      <.ul(props map createItem: _*)
    }
  
  case class State(items: List[String], text: String)
  
  class Backend($: BackendScope[Unit, State]) {
    def onChange(e: ReactEventFromInput) = {
      val newValue = e.target.value
      $.modState(_.copy(text = newValue))
    }
  
    def handleSubmit(e: ReactEventFromInput) =
      e.preventDefaultCB >>
      $.modState(s => State(s.items :+ s.text, ""))
  
    def render(state: State) =
      <.div(
        <.h3("TODO"),
        TodoList(state.items),
        <.form(^.onSubmit ==> handleSubmit,
          <.input(^.onChange ==> onChange, ^.value := state.text),
          <.button("Add #", state.items.length + 1)
        )
      )
  }
  
  val TodoApp = ScalaComponent.builder[Unit]("TodoApp")
    .initialState(State(Nil, ""))
    .renderBackend[Backend]
    .build
  
  TodoApp().renderIntoDOM(Fiddle.panel)
  // $FiddleEnd
}