package todos.todoItems

import io.reactivex.Observable
import retrofit2.Response
import todos.auth.AuthInteractor
import todos.http.TodosApi
import todos.model.TodoItem
import todos.model.Todos
import todos.model.to.IdTO


class TodoItemsInteractor(private val authInteractor: AuthInteractor) : TodoItemsContract.Interactor {

    val todosApi = TodosApi()

    override fun saveTodos(todos: Todos): Observable<String> {

        return todosApi.createTodos(authInteractor.getToken(), todos)
                .map(TodosApi.ServiceResponseMapper(IdTO("")))
                .map{ it._id }
    }

    override fun updateTodos(todos: Todos): Observable<Response<Void>> {
        return todosApi.updateTodos(authInteractor.getToken(), todos._id, todos)
    }

    override fun addTodoItem(todoItem: TodoItem, todosId: String): Observable<String> {
        return todosApi.createTodoItem(authInteractor.getToken(), todosId, todoItem)
                .map(TodosApi.ServiceResponseMapper(IdTO("")))
                .map{ it._id }
    }

    override fun deleteTodos(todosId: String): Observable<Response<Void>> {
        return todosApi.deleteTodos(authInteractor.getToken(), todosId)
    }

    override fun updateTodoItem(todoItem: TodoItem, todosId: String): Observable<Response<Void>> {
        return todosApi.updateTodoItem(authInteractor.getToken(), todosId, todoItem)
    }
}