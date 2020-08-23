import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class StingKtTest {

    @Test
    fun getLcsLength() {
        assertThat(getLcsLength("a", "b")).isEqualTo(0)
        assertThat(getLcsLength("a", "a")).isEqualTo(1)
        assertThat(getLcsLength("a", "ab")).isEqualTo(1)
        assertThat(getLcsLength("aa", "ab")).isEqualTo(1)
        assertThat(getLcsLength("aa", "aab")).isEqualTo(2)
        assertThat(getLcsLength("ab", "aab")).isEqualTo(2)
    }

    @Test
    fun runLengthEncode() {
        assertThat(runLengthEncode("")).isEqualTo(emptyList<Pair<Char, Int>>())
        assertThat(runLengthEncode("a")).isEqualTo(listOf('a' to 1))
        assertThat(runLengthEncode("abc")).isEqualTo(listOf('a' to 1, 'b' to 1, 'c' to 1))
        assertThat(runLengthEncode("aabc")).isEqualTo(listOf('a' to 2, 'b' to 1, 'c' to 1))
        assertThat(runLengthEncode("abbc")).isEqualTo(listOf('a' to 1, 'b' to 2, 'c' to 1))
        assertThat(runLengthEncode("abcc")).isEqualTo(listOf('a' to 1, 'b' to 1, 'c' to 2))
    }

    @Test
    fun runLengthDecode() {
        assertThat(runLengthDecode(emptyList())).isEqualTo("")
        assertThat(runLengthDecode(listOf('a' to 1))).isEqualTo("a")
        assertThat(runLengthDecode(listOf('a' to 1, 'b' to 1, 'c' to 1))).isEqualTo("abc")
        assertThat(runLengthDecode(listOf('a' to 2, 'b' to 1, 'c' to 1))).isEqualTo("aabc")
        assertThat(runLengthDecode(listOf('a' to 1, 'b' to 2, 'c' to 1))).isEqualTo("abbc")
        assertThat(runLengthDecode(listOf('a' to 1, 'b' to 1, 'c' to 2))).isEqualTo("abcc")
    }
}
