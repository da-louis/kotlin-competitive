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
        assertThat(primes(1)).isEqualTo(listOf<Long>())
        assertThat(primes(2)).isEqualTo(listOf(2L))
        assertThat(primes(10)).isEqualTo(listOf(2L, 3L, 5L, 7L))
        assertThat(primes(20)).isEqualTo(listOf(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L))
    }

    @Test
    fun primeFactorization() {
        assertThat(primeFactorization(1)).isEqualTo(mapOf<Long, Long>())
        assertThat(primeFactorization(7)).isEqualTo(mapOf(7L to 1L))
        assertThat(primeFactorization(10)).isEqualTo(mapOf(2L to 1L, 5L to 1L))
        assertThat(primeFactorization(12)).isEqualTo(mapOf(2L to 2L, 3L to 1L))
    }

    @Test
    fun isPrime() {
        assertThat(isPrime(1)).isFalse()
        assertThat(isPrime(2)).isTrue()
        assertThat(isPrime(3)).isTrue()
        assertThat(isPrime(4)).isFalse()

        assertThat(isPrime(10)).isFalse()
        assertThat(isPrime(11)).isTrue()
        assertThat(isPrime(12)).isFalse()
        assertThat(isPrime(13)).isTrue()
        // assertThat(isPrime(57)).isTrue()
    }

    @Test
    fun isPrimeML() {
        assertThat(isPrimeML(1)).isFalse()
        assertThat(isPrimeML(2)).isTrue()
        assertThat(isPrimeML(3)).isTrue()
        assertThat(isPrimeML(4)).isFalse()

        assertThat(isPrimeML(10)).isFalse()
        assertThat(isPrimeML(11)).isTrue()
        assertThat(isPrimeML(12)).isFalse()
        assertThat(isPrimeML(13)).isTrue()
        // assertThat(isPrimeML(57)).isTrue()
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

    @Test
    fun sieve() {
        Sieve(13).run {
            assertThat(isPrime(1)).isFalse()
            assertThat(isPrime(2)).isTrue()
            assertThat(isPrime(3)).isTrue()
            assertThat(isPrime(4)).isFalse()

            assertThat(isPrime(10)).isFalse()
            assertThat(isPrime(11)).isTrue()
            assertThat(isPrime(12)).isFalse()
            assertThat(isPrime(13)).isTrue()
            // assertThat(isPrime(57)).isTrue()

            assertThat(primeFactorization(1)).isEqualTo(emptyMap<Long, Long>())
            assertThat(primeFactorization(7)).isEqualTo(mapOf(7 to 1))
            assertThat(primeFactorization(10)).isEqualTo(mapOf(2 to 1, 5 to 1))
            assertThat(primeFactorization(12)).isEqualTo(mapOf(2 to 2, 3 to 1))

            assertThat(primeFactorization(1L)).isEqualTo(emptyMap<Long, Int>())
            assertThat(primeFactorization(7L)).isEqualTo(mapOf(7L to 1))
            assertThat(primeFactorization(10L)).isEqualTo(mapOf(2L to 1, 5L to 1))
            assertThat(primeFactorization(12L)).isEqualTo(mapOf(2L to 2, 3L to 1))
        }
    }
}
