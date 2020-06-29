import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class MultiSetKtTest {
    @Test
    fun multiSet() {
        val ms = multiSetOf<Int>()
        assertThat(ms).isEqualTo(multiSetOf<Int>())

        ms.add(1)
        assertThat(ms).isEqualTo(multiSetOf(1))
        assertThat(ms.count(1)).isEqualTo(1)
        assertThat(ms.count(2)).isEqualTo(0)

        ms.add(2)
        assertThat(ms).isEqualTo(multiSetOf(1, 2))
        assertThat(ms.count(1)).isEqualTo(1)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.add(1)
        assertThat(ms).isEqualTo(multiSetOf(1, 1, 2))
        assertThat(ms.count(1)).isEqualTo(2)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.remove(1)
        assertThat(ms).isEqualTo(multiSetOf(1, 2))
        assertThat(ms.count(1)).isEqualTo(1)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.remove(1)
        assertThat(ms).isEqualTo(multiSetOf(2))
        assertThat(ms.count(1)).isEqualTo(0)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.remove(1)
        assertThat(ms).isEqualTo(multiSetOf(2))
        assertThat(ms.count(1)).isEqualTo(0)
        assertThat(ms.count(2)).isEqualTo(1)
    }

    @Test
    fun sortedMultiSet() {
        val sms = sortedMultiSetOf<Int>()
        assertThat(sms).isEqualTo(sortedMultiSetOf<Int>())
        assertThat(sms.count(1)).isEqualTo(0)
        assertThat(sms.count(2)).isEqualTo(0)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isNull()
        assertThat(sms.max()).isNull()

        sms.add(1)
        assertThat(sms).isEqualTo(sortedMultiSetOf(1))
        assertThat(sms.count(1)).isEqualTo(1)
        assertThat(sms.count(2)).isEqualTo(0)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(1)

        sms.add(2)
        assertThat(sms).isEqualTo(sortedMultiSetOf(1, 2))
        assertThat(sms.count(1)).isEqualTo(1)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)

        sms.add(1)
        assertThat(sms).isEqualTo(sortedMultiSetOf(1, 1, 2))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)

        sms.add(3)
        assertThat(sms).isEqualTo(sortedMultiSetOf(1, 1, 2, 3))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(1)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(3)

        sms.remove(3)
        assertThat(sms).isEqualTo(sortedMultiSetOf(1, 1, 2))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)

        sms.remove(3)
        assertThat(sms).isEqualTo(sortedMultiSetOf(1, 1, 2))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)
    }
}
