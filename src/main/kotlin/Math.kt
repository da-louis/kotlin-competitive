import java.util.*
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sqrt

/**
 * O(log(min(a,b)))
 */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/**
 * O(log(min(a,b)))
 */
fun lcm(a: Long, b: Long): Long {
    tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    return a / gcd(a, b) * b
}

/**
 * O(sqrt(max))
 */
fun primes(max: Long): LongArray {
    val sqrt = ceil(sqrt(max.toDouble())).toLong()
    val primes = mutableListOf<Long>()

    var target = (2..max).toList()

    while (target.isNotEmpty() && target.first() <= sqrt) {
        val prime = target.first()
        primes.add(prime)
        target = target.filterNot { it % prime == 0L }
    }

    primes.addAll(target)

    return primes.toLongArray()
}

/**
 * O(NloglogN)
 */
fun primeFactorization(n: Long): Map<Long, Long> {
    val factors = mutableMapOf<Long, Long>()
    var temp = n
    var i = 2L
    while (i * i <= temp) {
        while (temp % i == 0L) {
            factors[i] = (factors[i] ?: 0) + 1
            temp /= i
        }
        i++
    }
    if (temp > 1) factors[temp] = 1
    return factors
}

fun isPrime(n: Long): Boolean {
    if (n < 2) return false
    for (i in 2..sqrt(n.toDouble()).toLong()) if (n % i == 0L) return false
    return true
}

/**
 * O(repeat)
 */
fun isPrimeML(n: Long, repeat: Int = 1000): Boolean {
    fun modPow(n: Long, p: Long, m: Long): Long {
        var x = n
        var y = p
        var result = 1L

        x %= m
        while (y > 0) {
            if (y % 2 == 1L)
                result = (result * x) % m
            y = y shr 1
            x = (x * x) % m
        }

        return result
    }

    fun millerTest(n: Long, d: Long): Boolean {

        val a = 2 + (abs(Random().nextLong()) % (n - 2))
        var x = modPow(a, d, n)
        var d1 = d

        if (x == 1L || x == n - 1) return true

        while (d1 != n - 1) {
            x = (x * x) % n
            d1 *= 2
            if (x == 1L) return false
            if (x == n - 1) return true
        }
        return false
    }

    if (n <= 1L || n == 4L) return false
    if (n <= 3L) return true

    var d = n - 1
    while (d % 2 == 0L) d /= 2

    for (i in 0 until repeat) {
        if (!millerTest(n, d)) return false
    }
    return true
}

/**
 * O(p)
 */
// TODO refactor method name?
fun Int.simplePowExact(p: Int): Int {
    var x = 1
    for (i in 0 until p) x = Math.multiplyExact(x, this)
    return x
}

/**
 * O(p)
 */
// TODO refactor method name?
fun Long.simplePowExact(p: Long): Long {
    var x = 1L
    for (i in 0 until p) x = Math.multiplyExact(x, this)
    return x
}

/**
 * O(log(p))
 */
fun Int.powExact(p: Long): Int {
    var x = java.math.BigInteger.valueOf(this.toLong())
    var y = p
    var result = java.math.BigInteger.valueOf(1L)
    val cap = java.math.BigInteger.valueOf(Int.MAX_VALUE.toLong())
    while (y > 0) {
        if (y % 2 == 1L) result = result.multiply(x)
        y = y shr 1
        x = x.multiply(x)
        if (result > cap) throw ArithmeticException("int overflow")
    }
    return result.toInt()
}

/**
 * O(log(p))
 */
fun Long.powExact(p: Long): Long {
    var x = java.math.BigInteger.valueOf(this)
    var y = p
    var result = java.math.BigInteger.valueOf(1L)
    val cap = java.math.BigInteger.valueOf(Long.MAX_VALUE)
    while (y > 0) {
        if (y % 2 == 1L) result = result.multiply(x)
        y = y shr 1
        x = x.multiply(x)
        if (result > cap) throw ArithmeticException("long overflow")
    }
    return result.toLong()
}
