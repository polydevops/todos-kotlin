package todos.todos


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polydevops.todos.R
import kotlinx.android.synthetic.main.fragment_todos.*
import todos.TodosApplication
import todos.auth.AuthInteractor
import todos.util.view.IToolbar
import todos.util.view.RecyclerItemClickListener

class TodosFragment : Fragment(), TodosContract.View {

    private lateinit var presenter: TodosContract.Presenter

    private lateinit var toolbar: IToolbar

    companion object {

        val TAG: String = TodosFragment::class.java.simpleName

        fun newInstance(): TodosFragment {
            val fragment = TodosFragment()
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        toolbar = context as IToolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TodosPresenter(TodosApplication.router, TodosInteractor(AuthInteractor(context)))
        presenter.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        recycler_todo_topics.layoutManager = LinearLayoutManager(context)
        recycler_todo_topics.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler_todo_topics.addOnItemTouchListener(RecyclerItemClickListener(context, { _, position: Int ->
            presenter.onTodosItemClicked((recycler_todo_topics.adapter as TodosAdapter).todos[position])
        }))

        fab_new_todo_topic.setOnClickListener {
            presenter.onCreateTodosButtonClicked()
        }

    }

    override fun setTodos(adapter: TodosAdapter) {
        recycler_todo_topics.adapter = adapter
    }

    override fun toolbar(): IToolbar {
        return toolbar
    }
}
