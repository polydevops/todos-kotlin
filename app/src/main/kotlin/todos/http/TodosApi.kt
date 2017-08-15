package todos.http

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import todos.model.TodoItem
import todos.model.Todos
import todos.model.to.IdTO

class TodosApi : ITodosApi {

    private val TAG = TodosApi::class.java.simpleName

    private val host = "http://polydevops.com:3000"

    private val api: RetrofitTodosApi = Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RetrofitTodosApi::class.java)

    override fun getTodos(token: String): Observable<ServiceResponse<List<Todos>>> {
        return api.getTodos(token)
                .doOnError(ErrorHandler())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createTodos(token: String, todos: Todos): Observable<ServiceResponse<IdTO>> {
        return api.createTodos(token, todos)
                .doOnError(ErrorHandler())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateTodos(token: String, id: String, todos: Todos): Observable<Response<Void>> {
        return api.updateTodos(token, id, todos)
                .doOnError(ErrorHandler())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteTodos(token: String, id: String): Observable<Response<Void>> {
        return api.deleteTodos(token, id)
                .doOnError(ErrorHandler())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createTodoItem(token: String, todosId: String, todoItem: TodoItem): Observable<ServiceResponse<IdTO>> {
        return api.createTodoItem(token, todosId, todoItem)
                .doOnError(ErrorHandler())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateTodoItem(token: String, id: String, todoItem: TodoItem): Observable<Response<Void>> {
        return api.updateTodoItem(token, id, todoItem)
                .doOnError(ErrorHandler())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteTodoItem(token: String, id: String): Observable<Response<Void>> {
        return api.deleteTodoItem(token, id)
                .doOnError(ErrorHandler())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    class ErrorHandler : Consumer<Throwable> {

        override fun accept(error: Throwable) {
            if (error is HttpException) {
                val errorResponse = String(error.response().errorBody()!!.bytes())
                Log.d("TodosApi", errorResponse)
                val serviceResponse = Gson().fromJson(errorResponse, ServiceResponse::class.java)

                serviceResponse.errors?.let {
                    if (serviceResponse.errors?.size == 1) {
                        throw serviceResponse.errors?.get(0) as ServiceError
                    } else {
                        throw ServiceErrors(serviceResponse.errors)
                    }
                }

            } else {
                throw error
            }
        }

    }

    class ServiceResponseMapper<T>(val missingReturnValue: T) : Function<ServiceResponse<T>, T> {

        override fun apply(t: ServiceResponse<T>): T {
            val data = t.data?.let {
                t.data
            }

            if (data != null) {
                return data
            } else {
                return missingReturnValue
            }

        }

    }

}