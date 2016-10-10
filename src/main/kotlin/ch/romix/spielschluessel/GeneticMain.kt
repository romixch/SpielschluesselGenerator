package ch.romix.spielschluessel

import org.jgap.Chromosome
import org.jgap.Genotype
import org.jgap.impl.ChromosomePool
import org.jgap.impl.DefaultConfiguration
import java.util.*

/**
 * Created by roman on 06.10.16.
 */

fun main(args: Array<String>) {
    val conf = DefaultConfiguration()
    conf.fitnessFunction = BestPlanFitnessFunction()

    val gameSlots = ArrayList<GameSlot>()
    val numberOfTeams = 5
    for (a in 1..numberOfTeams){
        for (b in 1..numberOfTeams) {
            if (a != b) {
                val game = Game(a, b)
                gameSlots.add(GameSlot(game))
            }
        }
    }

    fun transform (it:GameSlot): GameGene {
        val gg = GameGene(gameSlots, conf)
        gg.setToRandomValue(conf.randomGenerator)
        return gg
    }

    val genes: List<GameGene> = gameSlots.map { transform(it) }.toList()

    val chromosome = Chromosome(conf, genes.toTypedArray())
    conf.sampleChromosome = chromosome
    conf.populationSize = 100
    val population = Genotype.randomInitialGenotype(conf)

    for (i in 1..30) {
        population.evolve()
        println(population.fittestChromosome)
    }

}