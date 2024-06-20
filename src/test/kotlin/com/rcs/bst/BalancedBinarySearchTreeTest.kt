package com.rcs.bst

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BalancedBinarySearchTreeTest {

    @Test
    fun `test simple add`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()
        bst.add(0, "zero")

        // Act
        val contains = bst.contains(0)
        val value = bst.get(0)
        val height = bst.height

        // Assert
        assertThat(contains).isTrue()
        assertThat(value).isEqualTo("zero")
        assertThat(height).isEqualTo(1)
    }

    @Test
    fun `test simple update`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()
        bst.add(0, "zero")

        // Act
        val previousValue = bst.add(0, "null")

        // Assert
        assertThat(previousValue).isEqualTo("zero")
        val currentValue = bst.get(0)
        assertThat(currentValue).isEqualTo("null")
    }

    @Test
    fun `test simple remove`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()
        bst.add(0, "zero")

        // Act
        bst.remove(0)

        // Assert
        assertThat(bst.height).isEqualTo(0)
        assertThat(bst.contains(0)).isFalse()
        assertThat(bst.get(0)).isNull()
    }

    @Test
    fun `test remove root`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()
        bst.add(0, "zero")
        bst.add(-1, "minus one")
        bst.add(1, "one")

        // Act
        bst.remove(0)

        // Assert
        assertThat(bst.height).isEqualTo(2)
        assertThat(bst.contains(0)).isFalse()
        assertThat(bst.get(0)).isNull()
        assertThat(bst.root!!.key).isEqualTo(-1)
        assertThat(bst.root!!.left).isNull()
        assertThat(bst.root!!.right!!.key).isEqualTo(1)
    }

    @Test
    fun `test remove left leaf`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()
        bst.add(0, "zero")
        bst.add(-1, "minus one")
        bst.add(1, "one")

        // Act
        bst.remove(-1)

        // Assert
        assertThat(bst.height).isEqualTo(2)
        assertThat(bst.contains(-1)).isFalse()
        assertThat(bst.get(-1)).isNull()
        assertThat(bst.root!!.key).isEqualTo(0)
        assertThat(bst.root!!.left).isNull()
        assertThat(bst.root!!.right!!.key).isEqualTo(1)
    }

    @Test
    fun `test remove right leaf`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()
        bst.add(0, "zero")
        bst.add(-1, "minus one")
        bst.add(1, "one")

        // Act
        bst.remove(1)

        // Assert
        assertThat(bst.height).isEqualTo(2)
        assertThat(bst.contains(1)).isFalse()
        assertThat(bst.get(1)).isNull()
        assertThat(bst.root!!.key).isEqualTo(0)
        assertThat(bst.root!!.right).isNull()
        assertThat(bst.root!!.left!!.key).isEqualTo(-1)
    }

    @Test
    fun `test get recursive`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()
        bst.add(0, "zero")
        bst.add(-1, "minus one")
        bst.add(1, "one")

        // Act
        val value = bst.get(-1)

        // Assert
        assertThat(value).isEqualTo("minus one")
    }

    @Test
    fun `test add left-skewed balances`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()

        // Act
        bst.add(0, "zero")
        bst.add(-1, "minus one")
        bst.add(-2, "minus two")

        // Assert
        assertThat(bst.root!!.key).isEqualTo(-1)
        assertThat(bst.root!!.value).isEqualTo("minus one")
        assertThat(bst.root!!.parent).isNull()

        assertThat(bst.root!!.left!!.key).isEqualTo(-2)
        assertThat(bst.root!!.left!!.value).isEqualTo("minus two")
        assertThat(bst.root!!.left!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.left!!.left).isNull()
        assertThat(bst.root!!.left!!.right).isNull()

        assertThat(bst.root!!.right!!.key).isEqualTo(0)
        assertThat(bst.root!!.right!!.value).isEqualTo("zero")
        assertThat(bst.root!!.right!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.right!!.left).isNull()
        assertThat(bst.root!!.right!!.right).isNull()
    }

    @Test
    fun `test add right-skewed balances`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()

        // Act
        bst.add(0, "zero")
        bst.add(1, "one")
        bst.add(2, "two")

        // Assert
        assertThat(bst.root!!.key).isEqualTo(1)
        assertThat(bst.root!!.value).isEqualTo("one")
        assertThat(bst.root!!.parent).isNull()

        assertThat(bst.root!!.left!!.key).isEqualTo(0)
        assertThat(bst.root!!.left!!.value).isEqualTo("zero")
        assertThat(bst.root!!.left!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.left!!.left).isNull()
        assertThat(bst.root!!.left!!.right).isNull()

        assertThat(bst.root!!.right!!.key).isEqualTo(2)
        assertThat(bst.root!!.right!!.value).isEqualTo("two")
        assertThat(bst.root!!.right!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.right!!.left).isNull()
        assertThat(bst.root!!.right!!.right).isNull()
    }

    @Test
    fun `test left-right rotation`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()

        // Act
        bst.add(30, "thirty")
        bst.add(20, "twenty")
        bst.add(25, "twenty five")

        // Assert
        assertThat(bst.root!!.key).isEqualTo(25)
        assertThat(bst.root!!.value).isEqualTo("twenty five")
        assertThat(bst.root!!.parent).isNull()

        assertThat(bst.root!!.left!!.key).isEqualTo(20)
        assertThat(bst.root!!.left!!.value).isEqualTo("twenty")
        assertThat(bst.root!!.left!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.left!!.left).isNull()
        assertThat(bst.root!!.left!!.right).isNull()

        assertThat(bst.root!!.right!!.key).isEqualTo(30)
        assertThat(bst.root!!.right!!.value).isEqualTo("thirty")
        assertThat(bst.root!!.right!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.right!!.left).isNull()
        assertThat(bst.root!!.right!!.right).isNull()
    }

    @Test
    fun `test right-left rotation`() {
        // Arrange
        val bst = BalancedBinarySearchTree<Int, String>()

        // Act
        bst.add(30, "thirty")
        bst.add(40, "forty")
        bst.add(35, "thirty five")

        // Assert
        assertThat(bst.root!!.key).isEqualTo(35)
        assertThat(bst.root!!.value).isEqualTo("thirty five")
        assertThat(bst.root!!.parent).isNull()

        assertThat(bst.root!!.left!!.key).isEqualTo(30)
        assertThat(bst.root!!.left!!.value).isEqualTo("thirty")
        assertThat(bst.root!!.left!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.left!!.left).isNull()
        assertThat(bst.root!!.left!!.right).isNull()

        assertThat(bst.root!!.right!!.key).isEqualTo(40)
        assertThat(bst.root!!.right!!.value).isEqualTo("forty")
        assertThat(bst.root!!.right!!.parent).isEqualTo(bst.root)
        assertThat(bst.root!!.right!!.left).isNull()
        assertThat(bst.root!!.right!!.right).isNull()
    }
}