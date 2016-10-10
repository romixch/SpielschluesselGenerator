package ch.romix.ivk.spielschluessel

import ch.romix.spielschluessel.Generator
import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.core.Is.*
import java.math.BigInteger

/**
 * Created by roman on 24.09.16.
 */

class GeneratorTest {

    @Test
    fun testVeryBasic() {
        val gen = Generator(slotCount = 5)
        assertThat(gen).matches(1,2,3,4,5)
    }

    @Test
    fun testAfterOneProceeding() {
        val gen = Generator(5)
        gen.proceed()
        assertThat(gen).matches(1,2,3,5,4)
    }

    @Test
    fun testLastRow() {
        val gen = Generator(5)
        println("Generate for " + gen.getPossibilities() + " slots")
        var p = gen.getPossibilities().toLong() - 1
        for (i in 1..p) {
            gen.proceed()
        }
        assertThat(gen).matches(5,4,3,2,1)
    }

    @Test
    fun testGenerateAllPossibilitiesFor10Slots() {
        val gen = Generator(10)
        println("Generate for " + gen.getPossibilities() + " slots")
        var p = gen.getPossibilities().toLong() - 1
        for (i in 1..p) {
            gen.proceed()
        }
    }

    @Test
    fun testGenerateAllPossibilitiesFor45Slots() {
        val gen = Generator(45)
        println("Generate for " + gen.getPossibilities() + " slots")
        var p = gen.getPossibilities().toLong() - 1
        for (i in 1..p) {
            gen.proceed()
        }
    }

    private fun assertThat(gen: Generator): Matcher {
        return Matcher(gen)
    }

    private class Matcher(private val gen: Generator) {
        fun matches(vararg match: Int) {
            for (i in 0..match.lastIndex) {
                assertThat(gen.get(i), `is`(match[i]))
            }
        }
    }
}