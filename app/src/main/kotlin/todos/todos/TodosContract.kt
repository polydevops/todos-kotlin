package todos.todos

import io.reactivex.Observable
import todos.model.Todos
import todos.util.mvp.AndroidLifecycleMethods
import todos.util.mvp.Router
import todos.util.view.IToolbar

interface TodosContract {

    interface View : Router.FragmentHelper {

        fun setTodos(adapter: TodosAdapter)
        fun toolbar(): IToolbar
    }

    interface Presenter: AndroidLifecycleMethods<View> {
        fun onCreateTodosButtonClicked()
        fun onTodosItemClicked(todos: Todos)
    }

    interface Interactor {
        fun getTodos(): Observable<List<Todos>>
    }
}