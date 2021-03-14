import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class SearchKtTest {

    // TODO refactor test
    private val list = listOf(1, 1, 2, 3, 3, 3)

    private val lowerBoundParamAndExpected = listOf(
        0 to 0,
        1 to 0,
        2 to 2,
        3 to 3,
        4 to 6
    )

    private val upperBoundParamAndExpected = listOf(
        0 to 0,
        1 to 2,
        2 to 3,
        3 to 6,
        4 to 6
    )

    @Test
    fun listBinarySearch() {
        val list = list.toList()

        lowerBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(list.lowerBound(param)).isEqualTo(expected)
        }

        upperBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(list.upperBound(param)).isEqualTo(expected)
        }
    }

    @Test
    fun arrayBinarySearch() {
        val array = list.toTypedArray()

        lowerBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(array.lowerBound(param)).isEqualTo(expected)
        }

        upperBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(array.upperBound(param)).isEqualTo(expected)
        }
    }

    @Test
    fun intArrayBinarySearch() {
        val intArray = list.toIntArray()

        lowerBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(intArray.lowerBound(param)).isEqualTo(expected)
        }

        upperBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(intArray.upperBound(param)).isEqualTo(expected)
        }
    }

    @Test
    fun longArrayBinarySearch() {
        val longArray = list.map { it.toLong() }.toLongArray()

        lowerBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(longArray.lowerBound(param.toLong())).isEqualTo(expected)
        }

        upperBoundParamAndExpected.forEach { (param, expected) ->
            assertThat(longArray.upperBound(param.toLong())).isEqualTo(expected)
        }
    }

    @Test
    fun searchAllPatterns() {
        listOf(
            arrayOf(false),
            arrayOf(true)
        ).let { expected ->
            var count = 0
            searchAllPatterns(arrayOf(false, true), 1) { actual ->
                assertThat(actual).isEqualTo(expected[count++])
            }
            assertThat(count).isEqualTo(expected.size)
        }

        listOf(
            arrayOf(false, false),
            arrayOf(false, true),
            arrayOf(true, false),
            arrayOf(true, true)
        ).let { expected ->
            var count = 0
            searchAllPatterns(arrayOf(false, true), 2) { actual ->
                assertThat(actual).isEqualTo(expected[count++])
            }
            assertThat(count).isEqualTo(expected.size)
        }

        listOf(
            arrayOf(0, 0),
            arrayOf(0, 1),
            arrayOf(1, 0),
            arrayOf(1, 1)
        ).let { expected ->
            var count = 0
            searchAllPatterns(arrayOf(0, 1), 2) { actual ->
                assertThat(actual).isEqualTo(expected[count++])
            }
            assertThat(count).isEqualTo(expected.size)
        }
    }

    @Test
    fun permutations() {
        assertThat(permutations(listOf(1, 2, 3))).isEqualTo(
            listOf(
                listOf(1, 2, 3),
                listOf(1, 3, 2),
                listOf(2, 1, 3),
                listOf(2, 3, 1),
                listOf(3, 2, 1),
                listOf(3, 1, 2)
            )
        )
    }

    @Test
    fun addAroundWall() {
        val grid = arrayOf(
            "..S".toCharArray(),
            ".#.".toCharArray(),
            "G..".toCharArray()
        )

        val (h, w, newGrid) = addAroundWall(grid, '#')

        assertThat(h).isEqualTo(grid.size + 2)
        assertThat(w).isEqualTo(grid.first().size + 2)

        assertThat(newGrid).isEqualTo(
            arrayOf(
                "#####".toCharArray(),
                "#..S#".toCharArray(),
                "#.#.#".toCharArray(),
                "#G..#".toCharArray(),
                "#####".toCharArray()
            )
        )
    }
}
