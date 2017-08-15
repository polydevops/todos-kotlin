package todos.util.view


interface IToolbar {

    fun showToolbarBackNav(visible: Boolean)
    fun setToolbarTitle(titleId: Int)
    fun setToolbarTitle(title: CharSequence)

}