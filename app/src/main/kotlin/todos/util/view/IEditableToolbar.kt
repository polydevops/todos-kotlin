package todos.util.view

import android.view.View

val emptyAfterTextChangeListener = { newText: String -> }
val emptyBeforeTextChangeListener = { text: String, start: Int, count: Int, after: Int -> }
val emptyOnTextChangeListener = { s: String, start: Int, before: Int, count: Int -> }

interface IEditableToolbar : IToolbar {

    fun showEditable(visible: Boolean)

    fun setEditableText(text: String)

    fun setEditableListener(
            afterTextChangeListener: ((newText: String) -> Unit)?,
            beforeTextChangeListener: ((text: String, start: Int, count: Int, after: Int) -> Unit)?,
            onTextChangeListener: ((s: String, start: Int, before: Int, count: Int) -> Unit)?
    )

    fun setFocusChangeListener(
            onFocusChangeListener: (view: View, focused: Boolean) -> Unit
    )
}