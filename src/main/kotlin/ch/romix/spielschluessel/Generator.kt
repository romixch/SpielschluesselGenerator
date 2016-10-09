package ch.romix.spielschluessel

/**
 * Created by roman on 24.09.16.
 */

class Generator(private val slotCount: Int) {
    private var n = 0
    private val slots: IntArray

    init {
        slots = IntArray(slotCount)
        calc()
    }

    fun get(column: Int) : Int {
        return slots[column]
    }

    fun proceed() {
        n++
        calc()
    }

    private fun calc() {
        for(c in 0..slotCount - 1) {
            var m = (n / factorial(slotCount - c)) + 1
            for (d in 0..slotCount - 1) {
                if (!alreadyUsedNumberUntilColumn(m, c)) {
                    break
                } else {
                    m = (m + 1).mod(slotCount + 1)
                }
            }
            slots[c] = m
        }
    }

    private fun alreadyUsedNumberUntilColumn(number: Int, column: Int): Boolean {
        for (i in 0..column - 1) {
            if (slots[i] == number)
                return true
        }
        return false
    }

    private fun factorial(x: Int): Int {
        var result = 1
        for (f in 1..x) {
            result *= f
        }
        return result
    }
}