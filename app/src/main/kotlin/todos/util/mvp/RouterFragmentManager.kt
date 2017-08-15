package todos.util.mvp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import java.util.*

class RouterFragmentManager {

    val fragments = Stack<Fragment>()

    fun next(containerId: Int, fragment: Fragment, fragmentManager: FragmentManager, addToBackStack: Boolean) {
        fragments.push(fragment)

        val fragmentTransaction = fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)

        if (addToBackStack) fragmentTransaction.addToBackStack(fragment.javaClass.toString())

        fragmentTransaction.commit()
    }

    fun prev(fragmentManager: FragmentManager) {
        fragments.pop()
        fragmentManager.popBackStack()
    }

    fun getCurrentFragment(): Fragment? {
        return if (fragments.size > 0) fragments.peek() else null
    }


}