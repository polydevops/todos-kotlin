package todos.util.view

import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Listener for RecyclerView.onItemTouch() method - specifically listens for clicks.
 *
 * Attribution: http://sapandiwakar.in/recycler-view-item-click-handler/
 */
class RecyclerItemClickListener(context: Context, val onClickListener: (itemView: View, position: Int) -> Unit) : RecyclerView.OnItemTouchListener {

    val gestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, ClickGestureDetector())

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val selectedView = rv.findChildViewUnder(e.x, e.y)
        if (selectedView != null && gestureDetector.onTouchEvent(e)) {
            onClickListener(selectedView, rv.getChildAdapterPosition(selectedView))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ClickGestureDetector : GestureDetector.OnGestureListener {

        override fun onDown(p0: MotionEvent?): Boolean {
            return false
        }

        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            return false
        }

        override fun onShowPress(p0: MotionEvent?) {
            // no impl
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            // no impl
            return false
        }

        override fun onLongPress(p0: MotionEvent?) {
            // no impl

        }

    }

}