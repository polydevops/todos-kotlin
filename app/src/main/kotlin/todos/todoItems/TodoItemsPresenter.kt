package todos.todoItems

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuInflater
import com.polydevops.todos.R
import todos.model.TodoItem
import todos.model.Todos
import todos.util.mvp.Router
import todos.util.mvp.rx.RxPresenter


class TodoItemsPresenter(val router: Router<String>, val interactor: TodoItemsContract.Interactor) : RxPresenter(), TodoItemsContract.Presenter {

    lateinit var todos: Todos

    var isCreateMode: Boolean = true
    var isExiting: Boolean = false

    companion object {
        val ARG_TODOS = "todos"
    }

    val TAG = TodoItemsPresenter::class.java.simpleName

    lateinit var view: TodoItemsContract.View

    override fun setData(data: Bundle?) {
        todos = data?.getParcelable(ARG_TODOS) ?: Todos("", "", mutableListOf())
        isCreateMode = todos._id.isEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO
    }

    override fun onViewCreated(view: TodoItemsContract.View) {
        this.view = view

        view.toolbar().showToolbarBackNav(true)
        view.toolbar().showEditable(true)

        view.setTodoTitle(todos.name)
        view.setTodoItems(todos.todoItems)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (isCreateMode) {
            inflater?.inflate(R.menu.create_todos, menu)
        } else {
            inflater?.inflate(R.menu.edit_todos, menu)
        }
    }

    override fun updateTodosTitle(title: String) {
        todos.name = title
        if (isCreateMode && validate(todos)) {
            view.enableSaveAction(true)
        } else if (isCreateMode && title.isEmpty()) {
            view.enableSaveAction(false)
        }
    }

    override fun onDoneEditingTodosTitle() {
        if (!isExiting && !isCreateMode && validate(todos)) {
            compositeDisposable.add(interactor.updateTodos(todos)
                    .subscribe(
                            { response ->
                                if (response.code() != 204) {
                                    view.showSnackBar(R.string.failed_to_update_todos, Snackbar.LENGTH_SHORT)
                                }
                            }
                    ))
        }
    }

    override fun onTodoItemClicked(todoItem: TodoItem) {
        // TODO
    }


    override fun onAddTodoItemButtonClicked(todoItem: String) {
        if (isCreateMode && todoItem.isNotEmpty()) {
            val todoItemObj = TodoItem(todoItem)
            todos.todoItems.add(todoItemObj)
            view.clearAddTodoItem()
        } else if (todoItem.isNotEmpty()) {
            val todoItemObj = TodoItem(todoItem)
            todos.todoItems.add(todoItemObj)
            view.clearAddTodoItem()
            compositeDisposable.add(interactor.addTodoItem(todoItemObj, todos._id)
                    .subscribe(
                            { _id: String -> todoItemObj._id = _id },
                            { error: Throwable -> handleError(error) }))
        }

        view.setTodoItems(todos.todoItems)
    }

    override fun onTodoItemCheckboxToggled(todoItem: TodoItem, checked: Boolean) {
        todoItem.isDone = checked
        todos.updateTodoItem(todoItem)
        compositeDisposable.add(interactor.updateTodoItem(todoItem, todos._id)
                .subscribe(
                        { response ->
                            if (response.code() != 204) {
                                view.showSnackBar(R.string.failed_to_update_todo_item, Snackbar.LENGTH_SHORT)
                            }
                        }
                ))
    }

    override fun onSaveTodosActionClicked() {
        compositeDisposable.add(interactor.saveTodos(todos)
                .subscribe(
                        { _id: String -> todos._id = _id },
                        { error: Throwable -> handleError(error) },
                        { goBack() }))

    }

    override fun onDeleteTodosActionClicked() {
        compositeDisposable.add(interactor.deleteTodos(todos._id)
                .subscribe(
                        { response ->
                            if (response.code() == 204) {
                                goBack()
                            } else {
                                view.showSnackBar(R.string.failed_to_delete_todos, Snackbar.LENGTH_SHORT)
                            }
                        }
                ))
    }

    override fun onBackPressed() {
        if (isCreateMode) {
            view.displaySaveOrExitDialog(this)
        } else {
            compositeDisposable.add(interactor.updateTodos(todos)
                    .subscribe(
                            { response ->
                                if (response.code() == 204) {
                                    goBack()
                                } else {
                                    view.showSnackBar(R.string.failed_to_save_todos, Snackbar.LENGTH_SHORT)
                                }
                            }
                    ))
        }
    }

    override fun onSaveClicked() {
        compositeDisposable.add(interactor.saveTodos(todos)
                .subscribe(
                        { _id: String -> todos._id = _id },
                        { error: Throwable -> handleError(error) },
                        { goBack() }))
    }

    override fun onExitClicked() {
        goBack()
    }

    private fun validate(todos: Todos): Boolean {
        return todos.name.isNotEmpty()
    }

    private fun goBack() {
        isExiting = true

        view.toolbar().showEditable(false)
        view.toolbar().showToolbarBackNav(false)
        router.goBack(view.getFragmentManager())
    }

}