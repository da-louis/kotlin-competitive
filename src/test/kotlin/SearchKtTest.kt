import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class SearchKtTest {

    @Test
    fun list() {
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
    fun array() {
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
    fun intArray() {
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
    fun longArray() {
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

}
