package todos.todoItems


import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.polydevops.todos.R
import kotlinx.android.synthetic.main.fragment_todo_items.*
import todos.TodosApplication
import todos.auth.AuthInteractor
import todos.model.TodoItem
import todos.util.OnBackPressed
import todos.util.view.IEditableToolbar


class TodoItemsFragment : Fragment(), TodoItemsContract.View, OnBackPressed {

    private lateinit var presenter: TodoItemsContract.Presenter
    private lateinit var toolbar: IEditableToolbar

    private var saveOrExitDialog: SaveOrExitDialog? = null
    private var menu: Menu? = null

    companion object {

        val TAG = TodoItemsFragment::class.java.simpleName

        fun newInstance(): TodoItemsFragment {
            val fragment = TodoItemsFragment()
            return fragment
        }
    }

    override fun toolbar(): IEditableToolbar {
        return toolbar
    }

    override fun showSnackBar(stringResId: Int, ms: Int) {
        Snackbar.make(this.view!!, stringResId, ms)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        toolbar = context as IEditableToolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TodoItemsPresenter(TodosApplication.router, TodoItemsInteractor(AuthInteractor(context)))
        presenter.onCreate(savedInstanceState)
        presenter.setData(arguments)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo_items, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.onViewCreated(this)

        recycler_todo_items.layoutManager = LinearLayoutManager(context)
        setFieldWatchers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu

        presenter.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_save -> {
                presenter.onSaveTodosActionClicked()
                return true
            }
            R.id.action_delete -> {
                presenter.onDeleteTodosActionClicked()
                return true
            }
            else -> return false
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setTodoTitle(title: String) {
        toolbar.setEditableText(title)
    }

    override fun setTodoItems(todoItems: List<TodoItem>) {
        recycler_todo_items.adapter = TodoItemsAdapter(todoItems, this)
    }

    override fun displaySaveOrExitDialog(listener: SaveOrExitDialog.Listener) {
        if (saveOrExitDialog == null) {
            this.saveOrExitDialog = SaveOrExitDialog.newInstance(listener)
            this.saveOrExitDialog?.show(childFragmentManager, SaveOrExitDialog.TAG)
        }
    }

    override fun clearAddTodoItem() {
        edit_todo.setText("")
    }

    override fun enableSaveAction(enabled: Boolean) {
        menu?.findItem(R.id.action_save)?.isEnabled = enabled
    }

    override fun onTodoItemClicked(todoItem: TodoItem) {
        presenter.onTodoItemClicked(todoItem)
    }

    override fun onTodoItemCheckboxToggled(todoItem: TodoItem, checked: Boolean) {
        presenter.onTodoItemCheckboxToggled(todoItem, checked)
    }

    private fun setFieldWatchers() {

        toolbar.setEditableListener({ newTitle -> presenter.updateTodosTitle(newTitle) }, null, null)

        toolbar.setFocusChangeListener { view, focused -> if (!focused) presenter.onDoneEditingTodosTitle() }

        btn_add_todo.setOnClickListener {
            val todoItem = edit_todo.editableText.toString()
            presenter.onAddTodoItemButtonClicked(todoItem)
        }
    }




}
