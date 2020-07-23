import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows


internal class MathKtTest {

    @Test
    fun gcd() {
        assertThat(gcd(1, 7)).isEqualTo(1)
        assertThat(gcd(2, 6)).isEqualTo(2)
        assertThat(gcd(3, 9)).isEqualTo(3)
    }

    @Test
    fun lcm() {
        assertThat(lcm(1, 7)).isEqualTo(7)
        assertThat(lcm(2, 5)).isEqualTo(10)
        assertThat(lcm(6, 8)).isEqualTo(24)
    }

    @Test
    fun primes() {
        assertThat(primes(1)).isEqualTo(longArrayOf())
        assertThat(primes(2)).isEqualTo(longArrayOf(2))
        assertThat(primes(10)).isEqualTo(longArrayOf(2, 3, 5, 7))
        assertThat(primes(20)).isEqualTo(longArrayOf(2, 3, 5, 7, 11, 13, 17, 19))
    }

    @Test
    fun primeFactorization() {
        // TODO wrong expect value?
        assertThat(primeFactorization(1)).isEqualTo(mapOf<Long, Long>())
        assertThat(primeFactorization(10)).isEqualTo(mapOf(2L to 1L, 5L to 1L))
        assertThat(primeFactorization(12)).isEqualTo(mapOf(2L to 2L, 3L to 1L))
    }

    @Test
    fun isPrime() {
        assertThat(isPrime(1)).isFalse()
        assertThat(isPrime(2)).isTrue()
        assertThat(isPrime(3)).isTrue()
        assertThat(isPrime(4)).isFalse()
        // TODO add test case
    }

    @Test
    fun isPrimeML() {
        assertThat(isPrimeML(1)).isFalse()
        assertThat(isPrimeML(2)).isTrue()
        assertThat(isPrimeML(3)).isTrue()
        assertThat(isPrimeML(4)).isFalse()
        // TODO add test case
    }

    @Test
    fun simplePowExact_Int() {
        assertThat(1.simplePowExact(1)).isEqualTo(1)
        assertThat(1.simplePowExact(2)).isEqualTo(1)
        assertThat(1.simplePowExact(10000000)).isEqualTo(1)

        assertThat(2.simplePowExact(1)).isEqualTo(2)
        assertThat(2.simplePowExact(2)).isEqualTo(4)
        assertThat(3.simplePowExact(3)).isEqualTo(27)

        assertThat(2.simplePowExact(30)).isEqualTo(1073741824)
        assertThrows<ArithmeticException>("int overflow") { 2.simplePowExact(31) }
    }

    @Test
    fun simplePowExact_Long() {
        assertThat(1L.simplePowExact(1)).isEqualTo(1)
        assertThat(1L.simplePowExact(2)).isEqualTo(1)
        assertThat(1L.simplePowExact(10000000)).isEqualTo(1)

        assertThat(2L.simplePowExact(1)).isEqualTo(2)
        assertThat(2L.simplePowExact(2)).isEqualTo(4)
        assertThat(3L.simplePowExact(3)).isEqualTo(27)

        assertThat(2L.simplePowExact(62)).isEqualTo(4611686018427387904L)
        assertThrows<ArithmeticException>("long overflow") { 2L.simplePowExact(63) }
    }

    @Test
    fun powExact_Int() {
        assertThat(1.powExact(1)).isEqualTo(1)
        assertThat(1.powExact(2)).isEqualTo(1)
        assertThat(1.powExact(10000000)).isEqualTo(1)

        assertThat(2.powExact(1)).isEqualTo(2)
        assertThat(2.powExact(2)).isEqualTo(4)
        assertThat(3.powExact(3)).isEqualTo(27)

        assertThat(2.powExact(30)).isEqualTo(1073741824)
        assertThrows<ArithmeticException>("int overflow") { 2.powExact(31L) }
    }

    @Test
    fun powExact_Long() {
        assertThat(1L.powExact(1L)).isEqualTo(1)
        assertThat(1L.powExact(2L)).isEqualTo(1)
        assertThat(1L.powExact(10000000L)).isEqualTo(1)

        assertThat(2L.powExact(1L)).isEqualTo(2)
        assertThat(2L.powExact(2L)).isEqualTo(4)
        assertThat(3L.powExact(3L)).isEqualTo(27)

        assertThat(2L.powExact(62L)).isEqualTo(4611686018427387904L)
        assertThrows<ArithmeticException>("long overflow") { 2L.powExact(63L) }
    }
}
