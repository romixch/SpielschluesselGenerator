package ch.romix.spielschluessel

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

data class TimeSlot(val slot: Int, val games: List<GameSlot>)

data class Day(val slots: List<TimeSlot>)

data class Schedule(val days: List<Day>)