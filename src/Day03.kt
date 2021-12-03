fun main() {
    fun part1(input: List<String>): Int {
        val positionCount = input.first().length

        val gammaBits = (0 until positionCount)
            .mapNotNull { idx ->
                input
                    .map { binNum -> binNum[idx] }
                    .groupingBy { it }
                    .eachCount()
                    .maxByOrNull { it.value }
                    ?.key?.toString()
            }
            .reduce { acc, next -> acc + next }

        val epsilonBits = gammaBits.map { if (it.toString() == "1") "0" else "1" }.reduce { acc, next -> acc + next }

        val gamma = gammaBits.toInt(2)
        val epsilon = epsilonBits.toInt(2)

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val oxygenGeneratorRatingBits = input.evaluateNumber({ countZero, countOne ->
            if (countZero > countOne) {
                0
            } else {
                1
            }
        })

        val co2ScrubberRatingBits = input.evaluateNumber({ countZero, countOne ->
            if (countZero <= countOne ) {
                0
            } else {
                1
            }
        })

        val oxygenGeneratorRating = oxygenGeneratorRatingBits.toInt(2)
        val co2ScrubberRating = co2ScrubberRatingBits.toInt(2)

        return oxygenGeneratorRating * co2ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 23 * 10)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun List<String>.evaluateNumber(bitCriteria: (Int, Int) -> Int, startIdx: Int = 0): String {
    if (this.size == 1) {
        return this.first()
    }

    val bitCounts = this
        .map { binNum -> binNum[startIdx]}
        .groupingBy { it }
        .eachCount()

    val takeBit = bitCriteria(bitCounts['0'] ?: 0, bitCounts['1'] ?: 0).toString()[0]

    val remainingBinNums = this.filter { it[startIdx] == takeBit }
    return remainingBinNums.evaluateNumber(bitCriteria, startIdx + 1)
}