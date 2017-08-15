package todos.http

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*
import todos.model.TodoItem
import todos.model.Todos
import todos.model.to.IdTO

const val HEADER_AUTH = "Authorization"

interface RetrofitTodosApi {

    @GET("/todos")
    fun getTodos(@Header(HEADER_AUTH) token: String): Observable<ServiceResponse<List<Todos>>>

    @POST("/todos")
    fun createTodos(@Header(HEADER_AUTH) token: String, @Body todos: Todos): Observable<ServiceResponse<IdTO>>

    @PUT("/todos/{id}")
    fun updateTodos(@Header(HEADER_AUTH) token: String, @Path("id") id: String, @Body todos: Todos): Observable<Response<Void>>

    @DELETE("/todos/{id}")
    fun deleteTodos(@Header(HEADER_AUTH) token: String, @Path("id") id: String): Observable<Response<Void>>

    @POST("/todo/{id}")
    fun createTodoItem(@Header(HEADER_AUTH) token: String, @Path("id") id: String, @Body todoItem: TodoItem): Observable<ServiceResponse<IdTO>>

    @PUT("/todo/{id}")
    fun updateTodoItem(@Header(HEADER_AUTH) token: String, @Path("id") id: String, @Body todoItem: TodoItem): Observable<Response<Void>>

    @DELETE("/todos/{id}")
    fun deleteTodoItem(@Header(HEADER_AUTH) token: String, @Path("id") id: String): Observable<Response<Void>>
}