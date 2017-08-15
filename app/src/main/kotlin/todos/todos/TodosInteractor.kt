package todos.todos

import io.reactivex.Observable
import todos.auth.AuthInteractor
import todos.http.TodosApi
import todos.model.Todos

class TodosInteractor(private val authInteractor: AuthInteractor) : TodosContract.Interactor {

    val todosApi: TodosApi = TodosApi()


    override fun getTodos(): Observable<List<Todos>> {
        return todosApi.getTodos(authInteractor.getToken())
                .map(TodosApi.ServiceResponseMapper((listOf<Todos>())))

    }

}