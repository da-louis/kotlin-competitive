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
    fun yesNo() {
        assertThat(YesNo.asCamelCase(true)).isEqualTo("Yes")
        assertThat(YesNo.asCamelCase(false)).isEqualTo("No")
        assertThat(YesNo.asUpperCase(true)).isEqualTo("YES")
        assertThat(YesNo.asUpperCase(false)).isEqualTo("NO")
    }
}
