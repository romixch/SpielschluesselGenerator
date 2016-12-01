package ch.romix.spielschluessel

import org.jgap.*
import java.util.*

/**
 * Created by roman on 06.10.16.
 */

class GameGene(val gameSlots: ArrayList<GameSlot>, conf: Configuration): BaseGene(conf) {

    constructor(cloneTemplate: GameGene) : this(cloneTemplate.gameSlots, cloneTemplate.configuration) {
    }

    private var value: GameSlot = GameSlot(null)

    override fun newGeneInternal(): Gene = GameGene(gameSlots, configuration)

    override fun setAllele(allele: Any?) {
        if (allele is GameSlot) {
            value = allele
        }
    }

    fun getGameSlot(): GameSlot = value

    override fun getInternalValue(): Any? = value

    override fun compareTo(other: Any?): Int {
        if (other is GameGene) {
            return value.compareTo(other.getGameSlot());
        } else {
            return 1;
        }
    }

    override fun setToRandomValue(randomGenerator: RandomGenerator) {
        val i = randomGenerator.nextInt(gameSlots.size)
        this.allele = gameSlots[i]
    }

    override fun applyMutation(index: Int, a_percentage: Double) {
        allele = gameSlots[configuration.randomGenerator.nextInt(gameSlots.size)]
    }

    // no persistence
    override fun setValueFromPersistentRepresentation(a_representation: String?): Unit = throw UnsupportedOperationException()
    override fun getPersistentRepresentation(): String? = throw UnsupportedOperationException()
}

fun IChromosome.toSchedule(bestPlanFitnessFunction: BestPlanFitnessFunction): Schedule {
    var gameIndex = 0
    val timeSlots = ArrayList<TimeSlot>()
    for (row in 0..bestPlanFitnessFunction.rows - 1) {
        val gameSlots = ArrayList<GameSlot>()
        for (col in 0..bestPlanFitnessFunction.cols - 1) {
            val gameGene = this.genes[gameIndex] as GameGene
            gameSlots.add(gameGene.getGameSlot())
            gameIndex++
        }
        val timeSlot = TimeSlot(row, gameSlots)
        timeSlots.add(timeSlot)
    }
    val day = Day(timeSlots)
    return Schedule(listOf(day))
}

fun Array<Gene>.countDistinctGames() : Int {
    val distinctGames = HashSet<Game?>()
    this.forEach { g -> distinctGames.add((g as GameGene).getGameSlot().game) }
    return distinctGames.size
}