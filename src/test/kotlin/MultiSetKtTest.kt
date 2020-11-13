import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class MultiSetKtTest {
    @Test
    fun multiSet() {
        val ms = MultiSet<Int>()
        assertThat(ms).isEqualTo(MultiSet<Int>())

        ms.add(1)
        assertThat(ms).isEqualTo(MultiSet(1))
        assertThat(ms.count(1)).isEqualTo(1)
        assertThat(ms.count(2)).isEqualTo(0)

        ms.add(2)
        assertThat(ms).isEqualTo(MultiSet(1, 2))
        assertThat(ms.count(1)).isEqualTo(1)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.add(1)
        assertThat(ms).isEqualTo(MultiSet(1, 1, 2))
        assertThat(ms.count(1)).isEqualTo(2)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.remove(1)
        assertThat(ms).isEqualTo(MultiSet(1, 2))
        assertThat(ms.count(1)).isEqualTo(1)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.remove(1)
        assertThat(ms).isEqualTo(MultiSet(2))
        assertThat(ms.count(1)).isEqualTo(0)
        assertThat(ms.count(2)).isEqualTo(1)

        ms.remove(1)
        assertThat(ms).isEqualTo(MultiSet(2))
        assertThat(ms.count(1)).isEqualTo(0)
        assertThat(ms.count(2)).isEqualTo(1)
    }

    @Test
    fun sortedMultiSet() {
        val sms = SortedMultiSet<Int>()
        assertThat(sms).isEqualTo(SortedMultiSet<Int>())
        assertThat(sms.count(1)).isEqualTo(0)
        assertThat(sms.count(2)).isEqualTo(0)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isNull()
        assertThat(sms.max()).isNull()

        sms.add(1)
        assertThat(sms).isEqualTo(SortedMultiSet(1))
        assertThat(sms.count(1)).isEqualTo(1)
        assertThat(sms.count(2)).isEqualTo(0)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(1)

        sms.add(2)
        assertThat(sms).isEqualTo(SortedMultiSet(1, 2))
        assertThat(sms.count(1)).isEqualTo(1)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)

        sms.add(1)
        assertThat(sms).isEqualTo(SortedMultiSet(1, 1, 2))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)

        sms.add(3)
        assertThat(sms).isEqualTo(SortedMultiSet(1, 1, 2, 3))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(1)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(3)

        sms.remove(3)
        assertThat(sms).isEqualTo(SortedMultiSet(1, 1, 2))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)

        sms.remove(3)
        assertThat(sms).isEqualTo(SortedMultiSet(1, 1, 2))
        assertThat(sms.count(1)).isEqualTo(2)
        assertThat(sms.count(2)).isEqualTo(1)
        assertThat(sms.count(3)).isEqualTo(0)
        assertThat(sms.min()).isEqualTo(1)
        assertThat(sms.max()).isEqualTo(2)
    }
}
