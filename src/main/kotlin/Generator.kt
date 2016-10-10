package ch.romix.ivk.spielschluessel

import java.math.BigInteger
import java.util.*

/**
 * Created by roman on 24.09.16.
 */

class Generator(private val slotCount: Int) {
    private var curRow = 0L
    private val slots: IntArray

    init {
        slots = IntArray(slotCount)
        calc()
    }

    fun getPossibilities(): BigInteger {
        return factorial(slotCount)
    }

    fun get(column: Int) : Int {
        return slots[column]
    }

    fun proceed() {
        curRow++
        calc()
    }

    override fun toString(): String {
        return slots.joinToString()
    }

    private fun calc() {
        val inventory = ArrayList<Int>()
        for (i in 1..slotCount) {
            inventory.add(i)
        }
        for(c in 0..slotCount - 1) {
            val remainingSlots = inventory.size - 1
            val changeInterval = factorial(remainingSlots)
            var inventoryIndex: BigInteger
            inventoryIndex = BigInteger.valueOf(curRow).div(changeInterval)
            inventoryIndex = inventoryIndex.mod(BigInteger.valueOf(inventory.size.toLong()))
            slots[c] = inventory[inventoryIndex.toInt()]
            inventory.removeAt(inventoryIndex.toInt())
        }
    }

    private fun factorial(x: Int): BigInteger {
        var result = BigInteger.ONE
        for (f in 1L..x) {
            result = result.multiply(BigInteger.valueOf(f))
        }
        return result
    }
}