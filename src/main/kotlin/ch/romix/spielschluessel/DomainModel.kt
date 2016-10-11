package ch.romix.spielschluessel

import java.util.*

/**
 * Created by roman on 06.10.16.
 */

data class Game(val teamA: Int, val teamB: Int)

data class GameSlot(val game: Game?) : Comparable<GameSlot> {
    override fun compareTo(other: GameSlot): Int {
        if (other.game == null && game == null) {
            return 0;
        } else if (other.game == null) {
            return 1;
        } else if (game == null) {
            return -1
        } else {
            return game.teamA.compareTo(other.game.teamA) +
                    game.teamB.compareTo(other.game.teamB)
        }
    }
}

data class TimeSlot(val slot: Int, val games: List<GameSlot>) {
    fun teams() : IntArray {
        return games.map { it.game }.filter { it != null }.flatMap { intArrayOf(it!!.teamA, it!!.teamB).asIterable() }.toIntArray()
    }
}

data class Day(val slots: List<TimeSlot>) {
    fun getPausesForTeam(team: Int): List<Int> {
        val pauses = ArrayList<Int>()
        var currentPause = -1
        var started = false
        for (slot in slots) {
            for (slotTeam in slot.teams()) {
                if (slotTeam == team) {
                    if (started) {
                        pauses.add(currentPause)
                    } else {
                        started = true
                    }
                    currentPause = -1
                }
            }
            if (started) {
                currentPause++
            }
        }
        return pauses
    }
}

data class Schedule(val days: List<Day>)