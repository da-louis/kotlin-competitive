import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class SearchKtTest {

    @Test
    fun listBinarySearch() {
        val list = listOf(1, 1, 2, 3, 3, 3)

        list.run {
            assertThat(lowerBound(0)).isEqualTo(0)
            assertThat(lowerBound(1)).isEqualTo(0)
            assertThat(lowerBound(2)).isEqualTo(2)
            assertThat(lowerBound(3)).isEqualTo(3)
            assertThat(lowerBound(4)).isEqualTo(6)

            assertThat(upperBound(0)).isEqualTo(0)
            assertThat(upperBound(1)).isEqualTo(2)
            assertThat(upperBound(2)).isEqualTo(3)
            assertThat(upperBound(3)).isEqualTo(6)
            assertThat(upperBound(4)).isEqualTo(6)
        }
    }

    @Test
    fun arrayBinarySearch() {
        val array = arrayOf(1, 1, 2, 3, 3, 3)

        array.run {
            assertThat(lowerBound(0)).isEqualTo(0)
            assertThat(lowerBound(1)).isEqualTo(0)
            assertThat(lowerBound(2)).isEqualTo(2)
            assertThat(lowerBound(3)).isEqualTo(3)
            assertThat(lowerBound(4)).isEqualTo(6)

            assertThat(upperBound(0)).isEqualTo(0)
            assertThat(upperBound(1)).isEqualTo(2)
            assertThat(upperBound(2)).isEqualTo(3)
            assertThat(upperBound(3)).isEqualTo(6)
            assertThat(upperBound(4)).isEqualTo(6)
        }
    }

    @Test
    fun intArrayBinarySearch() {
        val intArray = intArrayOf(1, 1, 2, 3, 3, 3)

        intArray.run {
            assertThat(lowerBound(0)).isEqualTo(0)
            assertThat(lowerBound(1)).isEqualTo(0)
            assertThat(lowerBound(2)).isEqualTo(2)
            assertThat(lowerBound(3)).isEqualTo(3)
            assertThat(lowerBound(4)).isEqualTo(6)

            assertThat(upperBound(0)).isEqualTo(0)
            assertThat(upperBound(1)).isEqualTo(2)
            assertThat(upperBound(2)).isEqualTo(3)
            assertThat(upperBound(3)).isEqualTo(6)
            assertThat(upperBound(4)).isEqualTo(6)
        }
    }

    @Test
    fun longArrayBinarySearch() {
        val longArray = longArrayOf(1, 1, 2, 3, 3, 3)

        longArray.run {
            assertThat(lowerBound(0)).isEqualTo(0)
            assertThat(lowerBound(1)).isEqualTo(0)
            assertThat(lowerBound(2)).isEqualTo(2)
            assertThat(lowerBound(3)).isEqualTo(3)
            assertThat(lowerBound(4)).isEqualTo(6)

            assertThat(upperBound(0)).isEqualTo(0)
            assertThat(upperBound(1)).isEqualTo(2)
            assertThat(upperBound(2)).isEqualTo(3)
            assertThat(upperBound(3)).isEqualTo(6)
            assertThat(upperBound(4)).isEqualTo(6)
        }
    }

    @Test
    fun searchAllPatterns() {
        listOf(
            arrayOf(false),
            arrayOf(true)
        ).let { expected ->
            val actual = mutableListOf<Array<Boolean>>()
            searchAllPatterns(arrayOf(false, true), 1) { actual.add(it) }
            assertThat(actual).containsExactlyElementsOf(expected)
        }

        listOf(
            arrayOf(false, false),
            arrayOf(false, true),
            arrayOf(true, false),
            arrayOf(true, true)
        ).let { expected ->
            val actual = mutableListOf<Array<Boolean>>()
            searchAllPatterns(arrayOf(false, true), 2) { actual.add(it) }
            assertThat(actual).containsExactlyElementsOf(expected)
        }

        listOf(
            arrayOf(0, 0),
            arrayOf(0, 1),
            arrayOf(1, 0),
            arrayOf(1, 1)
        ).let { expected ->
            val actual = mutableListOf<Array<Int>>()
            searchAllPatterns(arrayOf(0, 1), 2) { actual.add(it) }
            assertThat(actual).containsExactlyElementsOf(expected)
        }
    }
}
