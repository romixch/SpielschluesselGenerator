package ch.romix.spielschluessel

import org.jgap.IChromosome
import org.jgap.FitnessFunction
import org.jgap.Gene
import java.util.*

class BestPlanFitnessFunction : FitnessFunction {

    constructor()

    override fun evaluate(subject: IChromosome): Double {
        var fitness: Double = 0.0
        val genes = subject.genes
        fitness += calculateGameDiversity(genes)
        return fitness
    }

    private fun calculateGameDiversity(genes: Array<Gene>): Double {
        val distinctGames = HashSet<Game?>()
        genes.forEach { g -> distinctGames.add((g as GameGene).getGameSlot().game) }
        return distinctGames.size.toDouble() / genes.size.toDouble()
    }

}