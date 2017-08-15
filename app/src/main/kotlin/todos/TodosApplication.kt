package todos

import android.app.Application
import com.polydevops.todos.R
import todos.util.mvp.Router

class TodosApplication : Application() {

    companion object {
        val router: Router<String> = Router(TodosRouterMap(), R.id.fragment_container)
    }


}