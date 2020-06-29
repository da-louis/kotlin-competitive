import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class MapWithDefaultKtTest {
    @Test
    fun immutableWithEmpty() {
        val mapWithDefault = mapWithDefaultOf<Int, Int> { 0 }

        repeat(1000) { assertThat(mapWithDefault[it]).isEqualTo(0) }
    }

    @Test
    fun immutableWithHasElement() {
        val mapWithDefault = mapWithDefaultOf(1 to 2, 2 to 3) { 0 }

        repeat(1000) {
            assertThat(mapWithDefault[it]).isEqualTo(
                when (it) {
                    1 -> 2
                    2 -> 3
                    else -> 0
                }
            )
        }
    }

    @Test
    fun mutableWithPut() {
        val mutableMapWithDefault = mutableMapWithDefaultOf<Int, Int> { 0 }

        repeat(1000) { assertThat(mutableMapWithDefault[it]).isEqualTo(0) }

        mutableMapWithDefault[1] = 2
        mutableMapWithDefault[2] = 3

        repeat(1000) {
            assertThat(mutableMapWithDefault[it]).isEqualTo(
                when (it) {
                    1 -> 2
                    2 -> 3
                    else -> 0
                }
            )
        }
    }

    @Test
    fun mutableWithRemove() {
        val mutableMapWithDefault = mutableMapWithDefaultOf(1 to 2, 2 to 3) { 0 }

        repeat(1000) {
            assertThat(mutableMapWithDefault[it]).isEqualTo(
                when (it) {
                    1 -> 2
                    2 -> 3
                    else -> 0
                }
            )
        }

        mutableMapWithDefault.remove(1)
        mutableMapWithDefault.remove(2)
        mutableMapWithDefault.remove(3)

        repeat(1000) { assertThat(mutableMapWithDefault[it]).isEqualTo(0) }
    }
}
