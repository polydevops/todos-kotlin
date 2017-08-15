package todos.util.mvp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

class Router<T>(var routerMap: RouterMap<T>, var containerId: Int) {

    val routerFragmentManager: RouterFragmentManager = RouterFragmentManager()

    fun goToActivity(clazz: Class<*>, context: Context, data: Bundle?) {
        val intent = Intent(context, clazz)
        if (data != null) intent.putExtras(data)
        context.startActivity(intent)
    }

    /*
    fun goToFragment(fragmentTag: T, fragmentManager: FragmentManager, data: Bundle?) {
        val fragment = routerMap.getFragment(fragmentTag)
        if (fragment != null) {
            if (data != null) fragment.arguments = data
            routerFragmentManager.next(containerId, fragment, fragmentManager, true)
        }
    }
    */

    fun goToFragment(fragmentTag: T, fragmentManager: FragmentManager, data: Bundle?, addToBackStack: Boolean) {
        val fragment = routerMap.getFragment(fragmentTag)
        if (fragment != null) {
            data?.let { fragment.arguments = data }
            routerFragmentManager.next(containerId, fragment, fragmentManager, addToBackStack)
        }
    }

    fun goBack(fragmentManager: FragmentManager) {
        routerFragmentManager.prev(fragmentManager)
    }

    fun getCurrentFragment(): Fragment? {
        return routerFragmentManager.getCurrentFragment()
    }

    interface FragmentHelper {
        fun getFragmentManager(): FragmentManager

        fun getIntent(): Intent {
            return Intent()
        }
    }

    interface PresenterHelper {
        fun setData(data: Bundle?)
    }




}