package todos.model

import android.os.Parcel
import android.os.Parcelable

data class Todos(var _id: String, var name: String, var todoItems: MutableList<TodoItem>) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(TodoItem))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeTypedList(todoItems)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Todos> {
        override fun createFromParcel(parcel: Parcel): Todos {
            return Todos(parcel)
        }

        override fun newArray(size: Int): Array<Todos?> {
            return arrayOfNulls(size)
        }
    }

    fun updateTodoItem(updatedTodoItem: TodoItem) {
        todoItems
                .asSequence()
                .filter { it._id == updatedTodoItem._id }
                .forEach {
                    it.apply {
                        todo = updatedTodoItem.todo
                        isDone = updatedTodoItem.isDone
                    }
                }
    }

}

