package ch.romix.spielschluessel

import org.jgap.Chromosome
import org.jgap.Genotype
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

    val genes = gameSlots.map { g -> GameGene(gameSlots, conf) }.toList()

    val planChromosome = PlanChromosome(conf, genes.toTypedArray())
    conf.sampleChromosome = planChromosome

    conf.populationSize = 100
    val population = Genotype.randomInitialGenotype(conf)

    for (i in 1..10) {
        population.evolve()
    }

    val bestPlanSoFar = population.fittestChromosome
    println(bestPlanSoFar)
}