package todos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import com.polydevops.todos.R
import kotlinx.android.synthetic.main.editable_toolbar.*
import kotlinx.android.synthetic.main.editable_toolbar.view.*
import todos.todos.TodosFragment
import todos.util.OnBackPressed
import todos.util.view.IEditableToolbar

class MainActivity : AppCompatActivity(), IEditableToolbar {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val router = TodosApplication.router

        router.containerId = R.id.fragment_container
        router.goToFragment(TodosFragment.TAG, supportFragmentManager, null, false)
    }

    override fun onBackPressed() {
        val currentFragment = TodosApplication.router.getCurrentFragment()
        if (currentFragment is OnBackPressed) {
            (currentFragment as OnBackPressed).onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showToolbarBackNav(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun showEditable(visible: Boolean) {
        if (visible) {
            edit_toolbar.visibility = View.VISIBLE
        } else {
            edit_toolbar.visibility = View.GONE
        }
    }

    override fun setEditableText(text: String) {
        toolbar.edit_toolbar.setText(text)
    }

    override fun setEditableListener(afterTextChangeListener: ((newText: String) -> Unit)?, beforeTextChangeListener: ((text: String, start: Int, count: Int, after: Int) -> Unit)?, onTextChangeListener: ((s: String, start: Int, before: Int, count: Int) -> Unit)?) {
        toolbar.edit_toolbar.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                afterTextChangeListener?.let { it(p0.toString()) }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                beforeTextChangeListener?.let { it(p0.toString(), p1, p2, p3) }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChangeListener?.let { it(p0.toString(), p1, p2, p3) }
            }

        })
    }

    override fun setFocusChangeListener(onFocusChangeListener: (view: View,focused: Boolean) -> Unit) {
        toolbar.edit_toolbar.setOnFocusChangeListener(onFocusChangeListener)
    }

    override fun setToolbarTitle(titleId: Int) {
        supportActionBar?.setTitle(titleId)
    }

    override fun setToolbarTitle(title: CharSequence) {
        supportActionBar?.title = title
    }

}
