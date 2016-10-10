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

