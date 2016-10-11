package ch.romix.spielschluessel

import org.jgap.IChromosome
import org.jgap.FitnessFunction
import org.jgap.Gene
import java.awt.List
import java.util.*

class BestPlanFitnessFunction(val rows: Int, val cols: Int) : FitnessFunction() {

    override fun evaluate(subject: IChromosome): Double {
        var fitness: Double = 0.0
        val genes = subject.genes
        fitness += calculateGameDiversity(genes)
        val schedule = buildSchedule(genes)
        fitness += optimizeForOnePauseFromGameToGame(schedule)
        return fitness
    }

    private fun calculateGameDiversity(genes: Array<Gene>): Double {
        val distinctGames = HashSet<Game?>()
        genes.forEach { g -> distinctGames.add((g as GameGene).getGameSlot().game) }
        return 10.0 * distinctGames.size.toDouble() / genes.size.toDouble()
    }

    private fun buildSchedule(genes: Array<Gene>): Schedule {
        var gameIndex = 0
        val timeSlots = ArrayList<TimeSlot>()
        for (row in 0..rows - 1) {
            val gameSlots = ArrayList<GameSlot>()
            for (col in 0..cols - 1) {
                val gameGene = genes[gameIndex] as GameGene
                gameSlots.add(gameGene.getGameSlot())
                gameIndex++
            }
            val timeSlot = TimeSlot(row, gameSlots)
            timeSlots.add(timeSlot)
        }
        val day = Day(timeSlots)
        return Schedule(listOf(day))
    }

    private fun optimizeForOnePauseFromGameToGame(schedule: Schedule): Double {

        return 0.0
    }
}