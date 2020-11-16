import MutableAnswer.Companion.mutableAnswerOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class MiscKtTest {
    @Test
    fun mutableAnswer() {
        val ma = mutableAnswerOf(1, Math::max)
        assertThat(ma.value).isEqualTo(1)

        ma.value = 2
        assertThat(ma.value).isEqualTo(2)

        ma.value = 1
        assertThat(ma.value).isEqualTo(2)

        ma.value = 3
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
        assertThat(intArrayOf(1, 2, 3, 4).scanWithIntArray(0, Int::plus)).isEqualTo(intArrayOf(0, 1, 3, 6, 10))
        assertThat(intArrayOf(1, 2, 3, 4).scanWithIntArray(1, Int::times)).isEqualTo(intArrayOf(1, 1, 2, 6, 24))

        assertThat(intArrayOf(1, 2, 3, 4).scanReduceWithIntArray(Int::plus)).isEqualTo(intArrayOf(1, 3, 6, 10))
        assertThat(intArrayOf(1, 2, 3, 4).scanReduceWithIntArray(Int::times)).isEqualTo(intArrayOf(1, 2, 6, 24))

        assertThat(longArrayOf(1, 2, 3, 4).scanWithLongArray(0, Long::plus)).isEqualTo(longArrayOf(0, 1, 3, 6, 10))
        assertThat(longArrayOf(1, 2, 3, 4).scanWithLongArray(1, Long::times)).isEqualTo(longArrayOf(1, 1, 2, 6, 24))

        assertThat(longArrayOf(1, 2, 3, 4).scanReduceWithLongArray(Long::plus)).isEqualTo(longArrayOf(1, 3, 6, 10))
        assertThat(longArrayOf(1, 2, 3, 4).scanReduceWithLongArray(Long::times)).isEqualTo(longArrayOf(1, 2, 6, 24))
    }
}
