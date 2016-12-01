package ch.romix.spielschluessel

import org.jgap.FitnessFunction
import org.jgap.Gene
import org.jgap.IChromosome

/**
 * This is the fitness function. It calculates the fitness of a Schedule. The higher the score
 * the fitter this schedule is.
 */
class BestPlanFitnessFunction(val rows: Int, val cols: Int, val numberOfTeams: Int) : FitnessFunction() {

    val correctGameCount: Int

    init {
        correctGameCount = (numberOfTeams-1)*numberOfTeams / 2
    }

    override fun evaluate(subject: IChromosome): Double {
        val genes = subject.genes
        val gameDiversity = calculateGameDiversity(genes)
        val rightCountOfGames = optimizeForRightCountOfGames(genes)
        val schedule = subject.toSchedule(this)
        val pauseFromGameToGame = optimizeForOnePauseFromGameToGame(schedule)

        val fitness = 100.0 + gameDiversity + rightCountOfGames + pauseFromGameToGame
        if (fitness < 0.0) {
            return 0.0
        } else {
            return fitness
        }
    }

    private fun calculateGameDiversity(genes: Array<Gene>): Double {
        val countDistinctGames = genes.countDistinctGames()
        return 200.0 * countDistinctGames.toDouble() / correctGameCount.toDouble()
    }

    private fun optimizeForRightCountOfGames(genes: Array<Gene>): Double {
        val gamesCount = genes.map { (it as GameGene).getGameSlot() }.filter { it.game != null }.count()
        val diff = Math.abs(gamesCount - correctGameCount)
        return 200.0 - diff
    }

    private fun optimizeForOnePauseFromGameToGame(schedule: Schedule): Double {
        var result = 0.0
        for (day in schedule.days) {
            for (team in 1..numberOfTeams) {
                val pauses = day.getPausesForTeam(team)
                for (pause in pauses) {
                    if (pause < 0) {
                        result -= 20
                    } else if (pause == 0) {
                        result -= 10
                    } else {
                        result += 2 - pause
                    }
                }
            }
        }
        return result
    }
}