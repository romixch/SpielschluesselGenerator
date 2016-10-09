package ch.romix.spielschluessel

import java.util.*

/**
 * Created by roman on 18.09.16.
 */

fun main(args: Array<String>) {
    val games = generateGames(teams = 5)
    val schedule = generateSchedule(days = 1, timeSlotsPerDay = 9, gamesPerTimeSlot = 2)
    println(schedule)
    val state = GenerationState(5, schedule, games)
    val s1 = generateSchedule(1, 9, 2)
    state.generate(s1)
    state.proceed()
}

fun  generateGames(teams: Int): List<Game> {
    val games = ArrayList<Game>()
    for (teamA in 1..(teams - 1)) {
        for (teamB in (teamA + 1)..teams) {
            games.add(Game(teamA, teamB))
        }
    }
    return games
}

fun generateSchedule(days: Int, timeSlotsPerDay: Int, gamesPerTimeSlot: Int): Schedule {
    var dayList = ArrayList<Day>()
    for (day in 1..days) {
        val timeSlotList = ArrayList<TimeSlot>()
        for (timeSlot in 1..timeSlotsPerDay) {
            var gameSlotList = ArrayList<GameSlot>()
            for (gameSlot in 1..gamesPerTimeSlot) {
                gameSlotList.add(GameSlot(null))
            }
            timeSlotList.add(TimeSlot(timeSlot, gameSlotList))
        }
        dayList.add(Day(timeSlotList))
    }
    return Schedule(dayList)
}

class GenerationState {
    private val teamsCount: Int
    private val gameSlotsCount: Int
    private val games : List<Game>
    private val emptySlotsCount: Int
    private val slotPossibilityCount: Int
    private val slotStates: IntArray

    constructor(teamsCount: Int, schedule: Schedule, games: List<Game>) {
        this.teamsCount = teamsCount
        this.gameSlotsCount = countGameSlots(schedule)
        this.games = games
        this.emptySlotsCount = gameSlotsCount - games.size
        slotPossibilityCount = games.size + emptySlotsCount
        this.slotStates = IntArray(slotPossibilityCount)
        for (i in 0..slotStates.lastIndex) {
            slotStates[i] = i
        }
    }

    private fun countGameSlots(schedule: Schedule): Int {
        return schedule.days.flatMap { d -> d.slots }
        .flatMap { s -> s.games }
        .map { 1 }
        .sum()
    }

    public fun generate(schedule: Schedule) {
        var i = 0
        for (day in schedule.days) {
            for (slot in day.slots) {
                for (gameSlot in slot.games) {
                    //gameSlot.game = games[i]
                    i++
                }
            }
        }
    }

    public fun proceed() {

    }


}