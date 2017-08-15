package todos.todoItems

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.polydevops.todos.R


class SaveOrExitDialog : DialogFragment() {

    companion object {
        val TAG = SaveOrExitDialog::class.java.simpleName

        lateinit var listener: Listener

        fun newInstance(listener: Listener): SaveOrExitDialog {
            this.listener = listener
            return SaveOrExitDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setTitle(R.string.not_saved)
                .setMessage(R.string.todos_not_saved_msg)
                .setPositiveButton(R.string.save, { dialogInterface, i ->
                    this.dismiss()
                    listener.onSaveClicked() })
                .setNegativeButton(R.string.exit, {dialogInterface, i ->
                    this.dismiss()
                    listener.onExitClicked() })
                .create()
    }

    interface Listener {
        fun onSaveClicked()
        fun onExitClicked()
    }
}