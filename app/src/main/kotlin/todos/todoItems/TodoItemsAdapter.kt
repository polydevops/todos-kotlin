package todos.todoItems

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.polydevops.todos.R
import todos.model.TodoItem

class TodoItemsAdapter(val todoItems: List<TodoItem>, val listener: TodoItemListener): RecyclerView.Adapter<TodoItemsAdapter.TodoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TodoItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_todo, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = todoItems[position]
        holder.todo.text = todoItem.todo
        holder.todo.setOnClickListener { listener.onTodoItemClicked(todoItem) }

        holder.todoCheckbox.setChecked(todoItem.isDone)
        holder.todoCheckbox.setOnCheckedChangeListener { _, checked -> listener.onTodoItemCheckboxToggled(todoItem, checked) }
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    inner class TodoItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val todo: TextView = itemView.findViewById(R.id.text_todo)
        val todoCheckbox: CheckBox = itemView.findViewById(R.id.chkbx_todo)
    }

    interface TodoItemListener {

        fun onTodoItemClicked(todoItem: TodoItem)
        fun onTodoItemCheckboxToggled(todoItem: TodoItem, checked: Boolean)
    }
}
