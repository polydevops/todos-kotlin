package todos

import android.support.v4.app.Fragment
import todos.todoItems.TodoItemsFragment
import todos.todos.TodosFragment
import todos.util.mvp.RouterMap

/**
 * Created by connor on 7/25/17.
 */
class TodosRouterMap : RouterMap<String> {

    override fun getFragment(tag: String): Fragment? {
        when (tag) {
            TodosFragment.TAG -> return TodosFragment.newInstance()
            TodoItemsFragment.TAG -> return TodoItemsFragment.newInstance()
        }

        return null




    }

}