@file:Suppress("unused")

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
fun primes(max: Long): List<Long> {
    val sqrt = ceil(sqrt(max.toDouble())).toLong()
    val primes = mutableListOf<Long>()
    var target = (2..max).toList()

    while (target.isNotEmpty() && target.first() <= sqrt) {
        val prime = target.first()
        primes.add(prime)
        target = target.filterNot { it % prime == 0L }
    }

    primes.addAll(target)

    return primes
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
    repeat(p) { x = Math.multiplyExact(x, this) }
    return x
}

/**
 * O(p)
 */
// TODO refactor method name?
fun Long.simplePowExact(p: Int): Long {
    var x = 1L
    repeat(p) { x = Math.multiplyExact(x, this) }
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

/**
 * TODO add test
 * TODO add doc
 */
tailrec fun Long.divCount(x: Long, count: Int = 0): Int {
    return when {
        x <= 1L -> error("$x is unexpected value.")
        this % x == 0L -> (this@divCount / x).divCount(x, count + 1)
        else -> count
    }
}

fun sigma(to: Long): Long = sigma(0, to)

fun sigma(from: Long, to: Long): Long {
    check(0 <= to && from in 0..to)
    return (to + 1) * to / 2 - (from - 1) * from / 2
}

/**
 * O(n * log(log(n))) to construct
 */
@Suppress("unused")
class Sieve(private val max: Int) {
    private val divisibleBy: IntArray = IntArray(max + 1)
    private val primes: MutableSet<Int> = mutableSetOf()
    private val maxSquare = max.toLong() * max

    init {
        divisibleBy[0] = -1; divisibleBy[1] = -1
        for (i in 2..max) {
            if (divisibleBy[i] != 0) continue
            primes.add(i)
            divisibleBy[i] = i
            for (j in i.toLong() * i..max step i.toLong()) {
                if (divisibleBy[j.toInt()] == 0) divisibleBy[j.toInt()] = i
            }
        }
    }

    /**
     * O(1)
     */
    fun isPrime(n: Int) = n > 1 && divisibleBy[n] == n

    /**
     * O(log(n))
     */
    private fun primeFactorization(n: Int): Map<Int, Int> {
        var curr = n
        val factors = mutableMapOf<Int, Int>()
        while (curr > 1) {
            factors.merge(divisibleBy[curr], 1, Int::plus)
            curr /= divisibleBy[curr]
        }
        return factors
    }

    /**
     * O(?)
     */
    private fun primeFactorization(n: Long): Map<Long, Int> {
        var curr = n
        val factors = mutableMapOf<Long, Int>()
        for (prime in primes) {
            var divCount = 0
            while (curr % prime == 0L) {
                curr /= prime; divCount++
            }
            if (divCount > 0) factors[prime.toLong()] = divCount
        }
        if (curr > 1) factors[curr] = 1
        return factors
    }

    /**
     * O(?)
     */
    @Suppress("UNCHECKED_CAST")
    fun <K : Number> primeFactorization(n: K): Map<K, Int> {
        return when {
            n is Int && n <= max -> primeFactorization(n) as Map<K, Int>
            n is Long && n <= maxSquare -> primeFactorization(n) as Map<K, Int>
            else -> throw IllegalArgumentException("n=$n is not integer or too large. must be smaller than max*max=$maxSquare")
        }
    }
}
