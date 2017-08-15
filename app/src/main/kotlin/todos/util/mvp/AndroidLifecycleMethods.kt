package todos.util.mvp

import android.os.Bundle

/**
 * Created by connor on 7/22/17.
 */
interface AndroidLifecycleMethods<V> {

    fun onCreate(savedInstanceState: Bundle?)
    fun onViewCreated(view: V)

}