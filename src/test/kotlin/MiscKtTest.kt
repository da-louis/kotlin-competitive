import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class MiscKtTest {
    @Test
    fun mutableAnswer() {
        val ma = MutableAnswer(1, Math::max)
        assertThat(ma.value).isEqualTo(1)

        assertThat(ma(2)).isTrue()
        assertThat(ma.value).isEqualTo(2)

        assertThat(ma(1)).isFalse()
        assertThat(ma.value).isEqualTo(2)

        assertThat(ma(3)).isTrue()
        assertThat(ma.value).isEqualTo(3)
    }

    @Test
    fun yesNoCamel() {
        assertThat(YesNo.fromValue(true)).isEqualTo("Yes")
        assertThat(YesNo.fromValue(false)).isEqualTo("No")
    }

    @Test
    fun yesNoUpper() {
        assertThat(YES_NO.fromValue(true)).isEqualTo("YES")
        assertThat(YES_NO.fromValue(false)).isEqualTo("NO")
    }

    @Test
    fun scan() {
        assertThat(intArrayOf(1, 2, 3, 4).scanArray(0, Int::plus)).isEqualTo(intArrayOf(0, 1, 3, 6, 10))
        assertThat(intArrayOf(1, 2, 3, 4).scanArray(1, Int::times)).isEqualTo(intArrayOf(1, 1, 2, 6, 24))

        assertThat(intArrayOf(1, 2, 3, 4).scanReduceArray(Int::plus)).isEqualTo(intArrayOf(1, 3, 6, 10))
        assertThat(intArrayOf(1, 2, 3, 4).scanReduceArray(Int::times)).isEqualTo(intArrayOf(1, 2, 6, 24))

        assertThat(longArrayOf(1, 2, 3, 4).scanArray(0, Long::plus)).isEqualTo(longArrayOf(0, 1, 3, 6, 10))
        assertThat(longArrayOf(1, 2, 3, 4).scanArray(1, Long::times)).isEqualTo(longArrayOf(1, 1, 2, 6, 24))

        assertThat(longArrayOf(1, 2, 3, 4).scanReduceArray(Long::plus)).isEqualTo(longArrayOf(1, 3, 6, 10))
        assertThat(longArrayOf(1, 2, 3, 4).scanReduceArray(Long::times)).isEqualTo(longArrayOf(1, 2, 6, 24))
    }
}
