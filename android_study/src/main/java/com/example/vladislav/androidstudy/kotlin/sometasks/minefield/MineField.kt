package com.example.vladislav.androidstudy.kotlin.sometasks.minefield

/**
 * http://svgimnazia1.grodno.by/sinica/Book_ABC/Book_ABC_pascal/olimp_resh/olimp_resh48.htm
 *
 * NOT YET COMPLETE
 */
class MineField {

    private val mineField: Array<Array<MineCell>> = Array(50) { Array(50) { MineCell(0, 0, " ") } }
    private val stepsMap: LinkedHashMap<Int, MutableMap<Int, Int>> = linkedMapOf()

    fun initializeMineField() {
        for (x in mineField.indices) {
            mineField[x] = Array(mineField[0].size) { MineCell(0, 0, " ") }
            for (y in mineField[0].indices) {
                mineField[x][y] = MineCell(x, y, " ")
                // println(mineField[x][y])
            }
        }
    }

    fun printField() {
        for (x in mineField.indices) {
            for (y in mineField[0].indices) {
                print("${mineField[y][x].direction} ")
            }
            println()
        }
    }

    fun makeRobotPath(turnsNumber: Int, stepsNumber: Int) {
        var direction: String = getInitialDirection()
        var x = mineField.size / 2
        var y = mineField[0].size / 2
        mineField[x][y].direction = START_POSITION
        stepsMap[x] = mutableMapOf()
        stepsMap[x]?.put(y,0)
        val turns = (1 until turnsNumber).random()
        var steps :Int
        for (turn in 0 until turns) {
            steps = (1 until stepsNumber).random()
            print("#$turn: steps=$steps $direction" )
            for (step in 0 until steps) {
                when (direction) {
                    DIRECTION_RIGHT -> x++
                    DIRECTION_UP -> y--
                    DIRECTION_LEFT -> x--
                    DIRECTION_DOWN -> y++
                }
                if (x == mineField.size) {
                    x = 0
                }
                if (x == -1) {
                    x = mineField.size - 1
                }
                if (y == mineField[0].size) {
                    y = 0
                }
                if (y == -1) {
                    y = mineField[0].size - 1
                }
                mineField[x][y].direction = direction
                if (stepsMap[x] == null) {
                    stepsMap[x] = mutableMapOf()
                }
                if (stepsMap[x]?.get(y) == null) {
                    stepsMap[x]?.put(y, 0)
                }
                stepsMap[x]?.put(y, stepsMap[x]?.get(y)?.plus(1)!!)
                if (stepsMap[x]?.get(y)?.compareTo(2)!!>=0) {
                    print("  x=$x, y=$y; ")
                }
            }
            direction = getDirection(direction)
            println()
        }
        mineField[x][y].direction = FINISH_POSITION
        // println(stepsMap)
    }

    private fun getInitialDirection():String {
        return when ((0 until 4).random()) {
            0 -> DIRECTION_DOWN
            1 -> DIRECTION_UP
            2 -> DIRECTION_LEFT
            3 -> DIRECTION_RIGHT
            else -> START_POSITION
        }
    }

    private fun getDirection(direction: String): String {
        when (direction) {
            DIRECTION_RIGHT -> {
                when((0 until 1).random()) {
                    0 -> return DIRECTION_UP
                    1 -> return DIRECTION_DOWN
                }
            }
            DIRECTION_UP -> {
                when((0 until 1).random()) {
                    0 -> return DIRECTION_LEFT
                    1 -> return DIRECTION_RIGHT
                }
            }
            DIRECTION_LEFT -> {
                when((0 until 1).random()) {
                    0 -> return DIRECTION_DOWN
                    1 -> return DIRECTION_UP
                }
            }
            DIRECTION_DOWN -> {
                when((0 until 1).random()) {
                    0 -> return DIRECTION_RIGHT
                    1 -> return DIRECTION_LEFT
                }
            }
        }
        return START_POSITION
    }

    data class MineCell(val x: Int, val y: Int, var direction: String)

    companion object {
        private const val DIRECTION_UP = "^"
        private const val DIRECTION_DOWN = "v"
        private const val DIRECTION_LEFT = "<"
        private const val DIRECTION_RIGHT = ">"
        private const val START_POSITION = "•"
        private const val FINISH_POSITION = "o"
    }
}