package ch.romix.spielschluessel

import org.jgap.*
import java.util.*

/**
 * Created by roman on 06.10.16.
 */

class PlanChromosome(conf: Configuration, genes: Array<Gene>): Chromosome(conf, genes) {

    val f: Game
        get() = Game(1,2)

    val gameGenes: Array<GameGene>
        get() = genes as Array<GameGene>

    override fun perform(a_obj: Any?, a_class: Class<*>?, a_params: Any?): Any? {
        return PlanChromosome(configuration, genes)
    }
}

class GameGene(val gameSlots: ArrayList<GameSlot>, conf: Configuration): BaseGene(conf) {

    private var value: GameSlot = GameSlot(null)

    override fun newGeneInternal(): Gene = GameGene(gameSlots, configuration)

    override fun setAllele(allele: Any?) {
        if (allele is GameSlot) {
            value = allele
        }
    }

    fun getGameSlot(): GameSlot = value

    override fun getInternalValue(): Any? = value.game

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
        setAllele(gameSlots[configuration.randomGenerator.nextInt(gameSlots.size)])
    }

    // no persistence
    override fun setValueFromPersistentRepresentation(a_representation: String?): Unit = throw UnsupportedOperationException()
    override fun getPersistentRepresentation(): String? = throw UnsupportedOperationException()
}

class BestPlanFitnessFunction : FitnessFunction {

    constructor()

    override fun evaluate(subject: IChromosome): Double {
        var fitness: Double = 0.0
        if (subject is PlanChromosome) {
            val genes = subject.gameGenes
            fitness += calculateGameDiversity(genes)
        }
        return fitness
    }

    private fun calculateGameDiversity(genes: Array<GameGene>): Double {
        val distinctGames = HashSet<Game?>()
        genes.forEach { g -> distinctGames.add(g.getGameSlot().game) }
        return distinctGames.size as Double / genes.size
    }

}