package ch.romix.spielschluessel

import java.util.*

/**
 * This file contains all the domain model.
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

    override fun toString(): String {
        if (game == null) {
            return "    :    |"
        } else {
            return " %2s : %2s |".format(game.teamA, game.teamB)
        }
    }
}

data class TimeSlot(val slot: Int, val games: List<GameSlot>) {
    fun teams() : IntArray {
        return games.map { it.game }.filter { it != null }.flatMap { intArrayOf(it!!.teamA, it!!.teamB).asIterable() }.toIntArray()
    }

    override fun toString(): String {
        val sb = StringBuilder("%4s : |".format(slot))
        games.forEach { sb.append(it) }
        return sb.toString()
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

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("----------------------------\n")
        slots.forEach { sb.append("$it\n") }
        sb.append("----------------------------\n")
        return sb.toString()
    }
}

data class Schedule(val days: List<Day>) {
    override fun toString(): String {
        val sb = StringBuilder()
        days.forEach { d ->
            run {
                sb.append("Day: \n $d")
            }
        }
        return sb.toString()
    }
}