package todos.todoItems

import android.view.Menu
import android.view.MenuInflater
import io.reactivex.Observable
import retrofit2.Response
import todos.model.TodoItem
import todos.model.Todos
import todos.util.mvp.AndroidLifecycleMethods
import todos.util.mvp.Router
import todos.util.view.IEditableToolbar


interface TodoItemsContract {

    interface View : Router.FragmentHelper, TodoItemsAdapter.TodoItemListener {
        fun setTodoTitle(title: String)
        fun setTodoItems(todoItems: List<TodoItem>)
        fun displaySaveOrExitDialog(listener: SaveOrExitDialog.Listener)
        fun clearAddTodoItem()

        fun enableSaveAction(enabled: Boolean)
        fun toolbar(): IEditableToolbar
        fun showSnackBar(stringResId: Int, ms: Int)
    }

    interface Presenter : AndroidLifecycleMethods<View>, Router.PresenterHelper, SaveOrExitDialog.Listener {
        fun onAddTodoItemButtonClicked(todoItem: String)
        fun onTodoItemClicked(todoItem: TodoItem)
        fun onTodoItemCheckboxToggled(todoItem: TodoItem, checked: Boolean)

        // create
        fun onSaveTodosActionClicked()

        // edit
        fun onDeleteTodosActionClicked()

        fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?)
        fun onBackPressed()

        fun updateTodosTitle(title: String)
        fun onDoneEditingTodosTitle()
    }

    interface Interactor {
        fun saveTodos(todos: Todos): Observable<String>
        fun updateTodos(todos: Todos): Observable<Response<Void>>
        fun deleteTodos(todosId: String): Observable<Response<Void>>
        fun addTodoItem(todoItem: TodoItem, todosId: String): Observable<String>
        fun updateTodoItem(todoItem: TodoItem, todosId: String): Observable<Response<Void>>
    }
}