package todos.http

import io.reactivex.Observable
import retrofit2.Response
import todos.model.TodoItem
import todos.model.Todos
import todos.model.to.IdTO

interface ITodosApi {

    fun getTodos(token: String): Observable<ServiceResponse<List<Todos>>>

    fun createTodos(token: String, todos: Todos): Observable<ServiceResponse<IdTO>>

    fun updateTodos(token: String, id: String, todos: Todos): Observable<Response<Void>>

    fun deleteTodos(token: String, id: String): Observable<Response<Void>>

    fun createTodoItem(token: String, todosId: String, todoItem: TodoItem): Observable<ServiceResponse<IdTO>>

    fun updateTodoItem(token: String, id: String, todoItem: TodoItem): Observable<Response<Void>>

    fun deleteTodoItem(token: String, id: String): Observable<Response<Void>>

}