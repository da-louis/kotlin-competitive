import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DataStructuresTest {
    @Test
    fun cumulative() {
        val array = longArrayOf(1, 2, 3, 4, 5)

        val cumulativeSum = LongCumulative(array, LongGroup.SUM)
        assertThat(cumulativeSum[0..0]).isEqualTo(1L)
        assertThat(cumulativeSum[0..1]).isEqualTo(3L)
        assertThat(cumulativeSum[0..4]).isEqualTo(15L)
        assertThat(cumulativeSum[2..3]).isEqualTo(7L)
        assertThat(cumulativeSum[4..4]).isEqualTo(5L)

        val cumulativeMultiply = LongCumulative(array, LongGroup.MULTIPLY)
        assertThat(cumulativeMultiply[0..0]).isEqualTo(1L)
        assertThat(cumulativeMultiply[0..1]).isEqualTo(2L)
        assertThat(cumulativeMultiply[0..4]).isEqualTo(120L)
        assertThat(cumulativeMultiply[1..1]).isEqualTo(2L)
        assertThat(cumulativeMultiply[1..4]).isEqualTo(120L)
        assertThat(cumulativeMultiply[3..4]).isEqualTo(20L)
        assertThat(cumulativeMultiply[4..4]).isEqualTo(5L)
    }
}
