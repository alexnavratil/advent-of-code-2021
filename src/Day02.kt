const val CMD_FORWARD = "forward"
const val CMD_UP = "up"
const val CMD_DOWN = "down"

fun main() {
    fun part1(input: List<String>): Int {
        val commands = input.map {
            val splitted = it.split(" ")
            Pair(splitted[0], splitted[1].toInt())
        }

        val horizontalPos = commands.filter { it.first == CMD_FORWARD }.sumOf { it.second }
        val depth = commands
            .filter { it.first == CMD_DOWN }.
            sumOf { it.second }
            .minus(
                commands
                    .filter { it.first == CMD_UP }
                    .sumOf { it.second }
            )

        return horizontalPos * depth
    }

    fun part2(input: List<String>): Int {
        val commands = input.map {
            val splitted = it.split(" ")
            Pair(splitted[0], splitted[1].toInt())
        }

        val state = commands.fold(SubmarineState(0, 0, 0)) { acc, next ->
            val cmd = next.first
            val param = next.second

            when (cmd) {
                CMD_DOWN -> acc.copy(aim = acc.aim + param)
                CMD_UP -> acc.copy(aim = acc.aim - param)
                CMD_FORWARD -> acc.copy(
                    horizontalPos = acc.horizontalPos + param,
                    depth = acc.depth + acc.aim * param
                )
                else -> throw IllegalStateException("unsupported command \"$cmd\"")
            }
        }

        return state.horizontalPos * state.depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

data class SubmarineState(
    val horizontalPos: Int = 0,
    val depth: Int = 0,
    val aim: Int = 0
)