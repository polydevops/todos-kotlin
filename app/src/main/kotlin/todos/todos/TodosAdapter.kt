package todos.todos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.polydevops.todos.R
import todos.model.Todos

class TodosAdapter(val todos: List<Todos>): RecyclerView.Adapter<TodosAdapter.TodosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TodosViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_todos, parent, false)
        return TodosViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodosViewHolder?, position: Int) {
        val todosObj = todos[position]
        holder?.topic?.text = todosObj.name
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    class TodosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val topic: TextView = itemView.findViewById(R.id.todosTopic)
    }
}