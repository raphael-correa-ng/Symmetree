package com.rcs.bst

import org.example.com.rcs.bst.BstNode

class BstUtils {

    companion object {

        val LESS = -1
        val EQUAL = 0
        val GREATER = 1

        fun <K, V> leftMost(node: BstNode<K, V>?): BstNode<K, V>? {
            var leftMost = node
            while (leftMost?.left != null) {
                leftMost = leftMost.left!!
            }
            return leftMost
        }

        fun <K, V> rightMost(node: BstNode<K, V>?): BstNode<K, V>? {
            var rightMost = node
            while (rightMost?.right != null) {
                rightMost = rightMost.right!!
            }
            return rightMost
        }

        fun <K, V> predecessor(node: BstNode<K, V>): BstNode<K, V>? {
            return node.left?.let {
                rightMost(it)
            }
        }

        fun <K, V> successor(node: BstNode<K, V>): BstNode<K, V>? {
            return node.right?.let {
                leftMost(it)
            }
        }

        fun <K: Comparable<K>, V> findStart(startInclusive: K, node: BstNode<K, V>?): BstNode<K, V>? {
            return node?.let {
                when (startInclusive.compareTo(it.key)) {
                    LESS -> {
                        if (it.left == null || (it.left!!.key < startInclusive && it.left!!.right == null)) {
                            it
                        } else {
                            findStart(startInclusive, it.left)
                        }
                    }
                    EQUAL -> it
                    GREATER -> {
                        if (it.right == null || (it.right!!.key < startInclusive && it.right!!.left == null)) {
                            it
                        } else {
                            findStart(startInclusive, it.right)
                        }
                    }
                    else -> throw AssertionError()
                }
            }
        }

        fun <K, V> replace(node: BstNode<K, V>, replacement: BstNode<K, V>) {
            node.key = replacement.key
            node.value = replacement.value
            if (replacement.isLeaf()) {
                when (replacement) {
                    replacement.parent?.left -> replacement.parent!!.left = null
                    replacement.parent?.right -> replacement.parent!!.right = null
                }
            } else {
                replace(replacement, (predecessor(replacement) ?: successor(replacement))!!)
            }
        }
    }
}