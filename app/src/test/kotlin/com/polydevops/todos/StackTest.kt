package com.polydevops.todos

import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Test
import todos.util.Stack

/**
 * Created by connor on 7/24/17.
 */
class StackTest {

    private lateinit var stack: Stack<Int>

    @Before
    fun setup() {
        stack = Stack()
    }

    @Test
    fun `should return a size of 0`() {
        stack.size shouldBe 0
    }

    @Test
    fun `#peek() - should return 1 and retain a size of 1`() {
        stack.push(1)

        stack.peek() shouldBe 1
        stack.size shouldBe 1
    }

    @Test
    fun`#peek() - should return 2 and retain a size of 2`() {
        stack.push(1)
        stack.push(2)

        stack.peek() shouldBe 2
        stack.size shouldBe 2
    }

    @Test
    fun `#pop() - should return 1 and have a size of 0`() {
        stack.push(1)

        stack.pop() shouldBe 1
        stack.size shouldBe 0
    }

    @Test
    fun `#pop() - should return 2 and have a size of 0`() {
        stack.push(1)
        stack.push(2)

        stack.pop() shouldBe 2
        stack.size shouldBe 1
    }

    @Test
    fun `#push() - should add 1 to the stack and have a size of 1`() {
        stack.push(1)

        stack.size shouldBe 1
    }

    @Test
    fun `#push() - should add 1 and 2 to the stack and have a size of 2`() {
        stack.push(1)
        stack.push(2)

        stack.size shouldBe 2
    }

    @Test
    fun `#isEmpty() - should return true for an empty stack`() {
        stack.isEmpty() shouldBe true
    }

    @Test
    fun `#isEmpty() - should return false for a stack with at least 1 element`() {
        stack.push(1)

        stack.isEmpty() shouldBe false
    }

    @Test
    fun `#contains() - should return false for an empty stack`() {
        stack.contains(1) shouldBe false
    }

    @Test
    fun `#contains() - should return false for a stack not contain the Int value 1`() {
        stack.push(2)

        stack.contains(1) shouldBe false
    }

    @Test
    fun `#contains - should return true for a stack containing the Int value 1`() {
        stack.push(1)

        stack.contains(1) shouldBe true
    }

}