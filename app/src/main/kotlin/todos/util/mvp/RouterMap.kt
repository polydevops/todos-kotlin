package todos.util.mvp

import android.support.v4.app.Fragment

/**
 * Created by connor on 7/22/17.
 */
interface RouterMap<T> {
    fun getFragment(tag: T): Fragment?
}