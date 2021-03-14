@file:Suppress("unused")

import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.sqrt

/**
 * O(log(min(a,b)))
 */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a.absoluteValue else gcd(b, a % b)
fun gcd(vararg values: Long): Long = values.reduce { acc, l -> gcd(acc, l) }

/**
 * O(log(min(a,b)))
 */
fun lcm(a: Long, b: Long): Long = a.absoluteValue / gcd(a, b) * b.absoluteValue

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

@Suppress("SameParameterValue")
fun sigma(from: Long, to: Long): Long {
    if (to < 0 || from !in 0..to) return 0L
    return (to + 1) * to / 2 - (from - 1) * from / 2
}

/**
 * 正の方向へ丸める割り算
 */
fun Int.ceilDiv(b: Int): Int = this.also { check(b != 0) }.run {
    when {
        b < 0 -> (-this).ceilDiv(-b)
        this > 0 -> ((this + b - 1) / b)
        else -> (this / b)
    }
}

/**
 * 負の方向へ丸める割り算
 */
fun Int.floorDiv(b: Int): Int = -(-this).ceilDiv(b)

/**
 * 正の方向へ丸める割り算
 */
fun Long.ceilDiv(b: Long): Long = this.also { check(b != 0L) }.run {
    when {
        b < 0 -> (-this).ceilDiv(-b)
        this > 0 -> ((this + b - 1) / b)
        else -> (this / b)
    }
}

/**
 * 負の方向へ丸める割り算
 */
fun Long.floorDiv(b: Long): Long = -(-this).ceilDiv(b)

/**
 * 0から離れている方向の[b]の倍数へ切り上げる
 */
fun Long.ceilMultipleOf(b: Long): Long = this / b * b + if (this % b == 0L) 0L else b

/**
 * `[a]x + [b]y = gcd(a, b)` となる [a] と [b] の最大公約数と解 x, y を求める
 * @return  <gcd(a, b), x, y>
 */
private fun extensionGcd(a: Long, b: Long): Triple<Long, Long, Long> {
    var ar = a
    var br = b
    var x0 = 1L
    var x1 = 0L
    var y0 = 0L
    var y1 = 1L
    while (br != 0L) {
        val q = ar / br
        val r = ar % br
        val x2 = x0 - q * x1
        val y2 = y0 - q * y1
        run { ar = br; br = r; x0 = x1; x1 = x2; y0 = y1; y1 = y2 }
    }
    return Triple(ar, x0, y0)
}

// TODO なにこれ？
private fun Long.invEuclid(m: Long): Long {
    var a = this
    var b = m
    var u = 1L
    var v = 0L
    while (b > 0) {
        val t = a / b
        a -= t * b
        val temp1 = a
        a = b
        b = temp1

        u -= t * v
        val temp2 = u
        u = v
        v = temp2
    }
    u %= m
    if (u < 0) u += m
    return u
}

/**
 * O(n * log(log(n))) to construct
 */
@Suppress("unused")
class Sieve(private val max: Int) {
    private val divisibleBy: IntArray = IntArray((max + 1).coerceAtLeast(2))
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

/**
 * TODO add test
 * TODO add doc
 */
private fun matrixMultiplyMod(a: Array<LongArray>, b: Array<LongArray>, mod: Int): Array<LongArray> {
    val c = Array(a.size) { LongArray(b.first().size) }
    for (i in a.indices) for (k in b.indices) for (j in b.first().indices) {
        c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % mod
    }
    return c
}

/**
 * TODO add test
 * TODO add doc
 */
private fun matrixPowMod(a: Array<LongArray>, p: Long, mod: Int): Array<LongArray> {
    var b = Array(a.size) { LongArray(a.size) }
    for (i in a.indices) b[i][i] = 1
    var ca = a
    var cp = p
    while (cp > 0) {
        if (cp and 1 == 1L) b = matrixMultiplyMod(b, ca, mod)
        ca = matrixMultiplyMod(ca, ca, mod)
        cp /= 2
    }
    return b
}

/**
 * 最大長方形 O(source.size)
 * source の各値は 0 以上である必要あり
 */
private fun maxAreaInHistogram(source: LongArray): Long {
    val array = source + -1L
    val stack = java.util.Stack<IndexedValue<Long>>()
    var maxArea = 0L

    for ((right, a) in array.withIndex()) {
        if (stack.isEmpty() || stack.peek().value <= a) {
            stack.push(IndexedValue(right, a))
        } else {
            var mostLeft = right
            while (stack.isNotEmpty() && stack.peek().value > a) {
                val (left, value) = stack.pop()
                mostLeft = left
                maxArea = Math.max(maxArea, value * (right - mostLeft))
            }
            stack.push(IndexedValue(mostLeft, a))
        }
    }
    return maxArea
}

private fun slideMinimumElement(array: LongArray, width: Int): Array<IndexedValue<Long>> {
    val deque = java.util.ArrayDeque<IndexedValue<Long>>()
    val result = Array<IndexedValue<Long>>(array.size - width + 1) { IndexedValue(0, 0) }
    for (i in array.indices) {
        while (deque.isNotEmpty() && i - deque.first.index >= width) deque.removeFirst()
        while (deque.isNotEmpty() && deque.last.value > array[i]) deque.removeLast()
        deque.addLast(IndexedValue(i, array[i]))
        (i - width + 1).let { if (it >= 0) result[it] = IndexedValue(it, deque.first.value) }
    }
    return result
}

// todo verify
private fun slideMaximumElement(array: LongArray, width: Int): Array<IndexedValue<Long>> {
    val deque = java.util.ArrayDeque<IndexedValue<Long>>()
    val result = Array<IndexedValue<Long>>(array.size - width + 1) { IndexedValue(0, 0) }
    for (i in array.indices) {
        while (deque.isNotEmpty() && i - deque.first.index >= width) deque.removeFirst()
        while (deque.isNotEmpty() && deque.last.value < array[i]) deque.removeLast()
        deque.addLast(IndexedValue(i, array[i]))
        (i - width + 1).let { if (it >= 0) result[it] = IndexedValue(it, deque.first.value) }
    }
    return result
}

private fun Long.sqrt(): Long {
    var sqrt = kotlin.math.sqrt(this.toDouble()).toLong() - 1
    while ((sqrt + 1) * (sqrt + 1) <= this) sqrt++
    return sqrt
}
