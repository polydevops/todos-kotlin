package todos.todos

import android.os.Bundle
import android.util.Log
import com.polydevops.todos.R
import todos.model.Todos
import todos.todoItems.TodoItemsFragment
import todos.todoItems.TodoItemsPresenter
import todos.util.mvp.Router
import todos.util.mvp.rx.RxPresenter

class TodosPresenter(val router: Router<String>, val interactor: TodosContract.Interactor) : RxPresenter(), TodosContract.Presenter {

    val TAG: String = TodosPresenter::class.java.simpleName

    lateinit var view: TodosContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO
    }

    override fun onViewCreated(view: TodosContract.View) {
        this.view = view
        view.toolbar().setToolbarTitle(R.string.todos)

        fetchTodos()
    }

    override fun onCreateTodosButtonClicked() {
        router.goToFragment(TodoItemsFragment.TAG, view.getFragmentManager(), null, true)
    }

    override fun onTodosItemClicked(todos: Todos) {
        val data = Bundle()
        data.putParcelable(TodoItemsPresenter.ARG_TODOS, todos)

        router.goToFragment(TodoItemsFragment.TAG, view.getFragmentManager(), data, true)
    }

    override fun handleError(error: Throwable) {
        super.handleError(error)
        Log.d(TAG, error.message)
    }

    private fun fetchTodos() {
        compositeDisposable.add(
                interactor.getTodos()
                        .subscribe(
                                { todos: List<Todos> ->
                                    val adapter = TodosAdapter(todos)
                                    view.setTodos(adapter)
                                },
                                { error: Throwable -> this.handleError(error) })
        )
    }

}