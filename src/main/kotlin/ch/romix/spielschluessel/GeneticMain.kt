package ch.romix.spielschluessel

import org.jgap.Chromosome
import org.jgap.Genotype
import org.jgap.IChromosome
import org.jgap.impl.ChromosomePool
import org.jgap.impl.DefaultConfiguration
import java.util.*
import javax.naming.ConfigurationException

/**
 * Created by roman on 06.10.16.
 */

fun main(args: Array<String>) {
    val bestPlanFitnessFunction = BestPlanFitnessFunction(rows = 18, cols = 3, numberOfTeams = 9)

    val gameSlots = ArrayList<GameSlot>()
    for (a in 1..bestPlanFitnessFunction.numberOfTeams) {
        for (b in a..bestPlanFitnessFunction.numberOfTeams) {
            if (a != b) {
                val game = Game(a, b)
                gameSlots.add(GameSlot(game))
            }
        }
    }

    fun addEmptyGameSlots(gameSlots: ArrayList<GameSlot>, plan: BestPlanFitnessFunction) {
        val totalSlots = plan.rows * plan.cols
        val minimumSlots = gameSlots.size
        if (totalSlots < minimumSlots) {
            throw RuntimeException("Configuration is shitty. You need at least ${minimumSlots} game slots but have only ${totalSlots}. (${plan.rows} rows and ${plan.cols} cols)")
        }
        for (i in minimumSlots..totalSlots - 1) {
            gameSlots.add(GameSlot(null))
        }
    }

    addEmptyGameSlots(gameSlots, bestPlanFitnessFunction)

    val conf = DefaultConfiguration()
    conf.fitnessFunction = bestPlanFitnessFunction

    fun newRandomGameGene(it: GameSlot): GameGene {
        val gg = GameGene(gameSlots, conf)
        gg.setToRandomValue(conf.randomGenerator)
        return gg
    }

    val genes: List<GameGene> = gameSlots.map(::newRandomGameGene).toList()

    val chromosome = Chromosome(conf, genes.toTypedArray())
    conf.sampleChromosome = chromosome
    conf.populationSize = 300
    val population = Genotype.randomInitialGenotype(conf)

    for (i in 1..1000) {
        population.evolve()
        if (i % 10 == 0) {
            population.fittestChromosome.printToConsole(bestPlanFitnessFunction)
        }
    }
    population.fittestChromosome.printToConsole(bestPlanFitnessFunction)
}

fun IChromosome.printToConsole(fitnessFunction: BestPlanFitnessFunction) {
    println("Fitness: ${this.fitnessValue}, ${this.genes.countDistinctGames()}")
    println(this.toSchedule(fitnessFunction))
}