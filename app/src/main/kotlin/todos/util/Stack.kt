package todos.util

/**
 * Created by connor on 7/23/17.
 */
class Stack<E> : Collection<E> {

    override val size: Int
        get() = stack.size

    val stack = mutableListOf<E>()

    fun peek(): E {
        return stack[stack.size - 1]
    }

    fun pop(): E {
        return stack.removeAt(stack.size - 1)
    }

    fun push(e: E) {
        stack.add(e)
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun contains(element: E): Boolean {
        return stack.contains(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return stack.containsAll(elements)
    }

    override fun iterator(): Iterator<E> {
        return stack.iterator()
    }

}