@file:Suppress("unused")

import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * idk this class is useful or not :/
 * just practice for custom-setter
 */
class MutableAnswer<T>(private var v: T, private val function: (T, T) -> T = { _, t -> t }) {
    val value get() = this.v

    fun update(value: T): Boolean {
        val v = this.v
        this.v = function(this.v, value)
        return this.v != v
    }

    // todo update とどっちがいいかな
    operator fun invoke(value: T): Boolean {
        val v = this.v
        this.v = function(this.v, value)
        return this.v != v
    }

    override fun toString() = this.v.toString()
    override fun hashCode() = v.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MutableAnswer<*>
        return v == other.v
    }
}

/**
 * todo range set 同士のマージもできるようにする？
 */
@ExperimentalStdlibApi
private class RangeSet(mergeAdjacentRanges: Boolean) {
    val ranges: ArrayDeque<LongRange> = ArrayDeque<LongRange>()
        .apply { add(-INF..-INF); add(INF..INF) }
    private val adjustForMerge: Int = if (mergeAdjacentRanges) 1 else 0
    var maxLength = 0L

    /**
     * 点を挿入
     * @return 増やした点の数？
     */
    fun add(x: Long): Long = add(x..x)

    /**
     * 範囲を挿入
     * @return 増やした点の数？
     */
    fun add(range: LongRange): Long {
        checkRange(range)
        var (l, r) = range
        var i = ranges.lowerBound(range) - 1
        if (ranges[i].first <= l && l <= ranges[i].last + adjustForMerge) {
            l = min(l, ranges[i].first)
            r = max(r, ranges[i].last)
            ranges.removeAt(i)
        } else i++
        while (true) {
            if (ranges[i].first !in l..r + adjustForMerge) break
            r = max(r, ranges[i].last)
            ranges.removeAt(i)
        }
        maxLength = max(maxLength, r - l + 1)
        ranges.add(i, l..r)
        return 0 // todo
    }

    /**
     * 点を削除
     * @return 消した点の数？
     */
    fun remove(x: Long): Long = remove(x..x)

    /**
     * 範囲を削除
     * @return 消した点の数？
     */
    fun remove(range: LongRange): Long {
        checkRange(range)
        TODO()
    }

    /**
     * 点が含まれているかの判定
     */
    operator fun contains(x: Long) = contains(x..x)

    /**
     * 指定の範囲が全部含まれているかの判定
     * todo 一部含まれているかとか、 intersect なやつもあってもいいかも？
     */
    operator fun contains(range: LongRange): Boolean {
        // todo containBy を呼び出せばいいのでは？
        checkRange(range)
        TODO()
    }

    /**
     * 指定の点を含んでいる範囲を返す　
     * 含まれていない場合は null を返す
     */
    fun containBy(x: Long): LongRange? = containBy(x..x)

    /**
     * 指定の範囲を含んでいる範囲を返す　
     * 含まれていない場合は null を返す
     */
    fun containBy(range: LongRange): LongRange? {
        checkRange(range)
        TODO()
    }

    /**
     * Mex: 最小除外数(Minimum excludant) を返す
     */
    fun mex(x: Long = 0): Long {
        TODO()
    }

    /**
     * 区間に含まれる点の数を返す
     * todo デカいとオーバーフローするけどどうしよう
     */
    fun count(): Long {
        var sum = -2L
        for (range in ranges) sum += range.last - range.first + 1
        return sum
    }

    /**
     * 範囲の数
     */
    val size: Int get() = ranges.size - 2

    operator fun plusAssign(x: Long): Unit = run { add(x) }
    operator fun plusAssign(range: LongRange): Unit = run { add(range) }
    operator fun minusAssign(x: Long): Unit = run { remove(x) }
    operator fun minusAssign(range: LongRange): Unit = run { remove(range) }

    override fun toString(): String = "RangeSet(ranges=${ranges.drop(1).dropLast(1)})"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RangeSet) return false
        if (ranges != other.ranges) return false
        return true
    }

    override fun hashCode(): Int = ranges.hashCode()

    companion object {
        private const val INF = Long.MAX_VALUE / 10 * 9
        private operator fun LongRange.contains(other: LongRange): Boolean = this.first in other && this.last in other
        private operator fun LongRange.component1(): Long = this.first
        private operator fun LongRange.component2(): Long = this.last
        private fun checkRange(value: Long): Unit = check(value in -INF + 1 until INF)
        private fun checkRange(range: LongRange): Unit = check(range.first <= INF && range.last <= INF)
        private fun ArrayDeque<LongRange>.lowerBound(range: LongRange): Int {
            var ng = -1
            var ok = size
            while (abs(ok - ng) > 1) {
                val mid = (ok + ng) / 2
                if (this[mid].first >= range.first) ok = mid else ng = mid
            }
            return ok
        }
    }
}

enum class YesNo {
    Yes, No;

    companion object {
        @JvmStatic
        fun fromValue(b: Boolean): String = (if (b) Yes else No).toString()
    }
}

enum class YES_NO {
    YES, NO;

    companion object {
        @JvmStatic
        fun fromValue(b: Boolean): String = (if (b) YES else NO).toString()
    }
}

/**
 * `n.indices()` is sugar syntax of `0 until n`.
 */
val Int.indices: IntRange get() = 0 until this

/**
 * `[index] in [this]` is sugar syntax of `index in 0 until [this]`.
 */
operator fun Int.contains(index: Int): Boolean = index in this.indices

/**
 * `[this].mapIndices { transform }` is sugar syntax of `(0 until [this]).map{ transform(it) }`.
 */
inline fun <R> Int.mapIndices(transform: (Int) -> R): List<R> = this.indices.map(transform)

/**
 * `[this].mapIndices(x) { transform }` is sugar syntax of
 * `(0 until [this]).map{ y1-> (0 until [x]).map { x1-> transform(y1,x1) } }`.
 */
inline fun <R> Int.mapIndices(x: Int, transform: (Int, Int) -> R): List<List<R>> =
    this.mapIndices { y1 -> x.mapIndices { x1 -> transform(y1, x1) } }

/**
 * `[this].mapIndices(x) { transform }` is sugar syntax of
 * `(0 until [this]).map{ y1-> (0 until [x]).map { x1-> transform(y1,x1) } }`.
 */
inline fun <R> Int.flatMapIndices(x: Int, transform: (Int, Int) -> R): List<R> =
    this.indices.flatMap { y1 -> x.mapIndices { x1 -> transform(y1, x1) } }

/**
 * `[this].forEachIndices { transform }` is sugar syntax of `(0 until [this]).map{ transform(it) }`.
 */
inline fun Int.forEachIndices(transform: (Int) -> Unit): Unit = this.indices.forEach(transform)

/**
 * `[this].forEachIndices(x) { transform }` is sugar syntax of
 * `(0 until [this]).map{ y1-> (0 until [x]).map { x1-> transform(y1,x1) } }`.
 */
inline fun Int.forEachIndices(x: Int, transform: (Int, Int) -> Unit): Unit =
    this.forEachIndices { y1 -> x.forEachIndices { x1 -> transform(y1, x1) } }


/**
 * [base]進数表記のリストに変換する、負数でも使えるのかなこれ
 */
private fun Long.toNumbersWithBase(base: Long): List<Long> {
    val result = mutableListOf<Long>()
    var remain = this
    while (remain != 0L) {
        result.add(remain % base)
        remain /= base
    }
    if (result.isEmpty()) result.add(0)
    return result.reversed()
}

/**
 * 数値と見立てて比較する, O(size)
 */
private operator fun <E : Comparable<E>> List<E>.compareTo(other: List<E>): Int {
    this.size.compareTo(other.size).takeIf { it != 0 }?.let { return it }
    for ((a, b) in this.zip(other)) a.compareTo(b).takeIf { it != 0 }?.let { return it }
    return 0
}

/**
 * get characters numeric value.
 * e.g.: '0' to 0
 */
fun Char.toNumVal(): Int = this - '0'

//fun Char.isLowerCase() = this in 'a'..'z'
//
//fun Char.isUpperCase() = this in 'A'..'Z'

fun String.toPoweredLong(p: Int): Long {
    check(this.isNotBlank())
    if (this[0] == '-') return -this.drop(1).toPoweredLong(p)
    var mulI = 1L
    repeat(p) { mulI *= 10 }
    val i = this.indexOf('.').takeIf { it != -1 } ?: return this.toLong() * mulI
    var mulD = 1L
    repeat(p - this.length + i + 1) { mulD *= 10 }
    return this.slice(0 until i).toLong() * mulI + this.slice(i + 1..this.lastIndex).toLong() * mulD
}

fun String.toBigIntegerWithBase(base: java.math.BigInteger): java.math.BigInteger {
    var sum = java.math.BigInteger.ZERO
    var bs = java.math.BigInteger.ONE
    for (c in this.reversed()) {
        sum += bs * (java.lang.Character.getNumericValue(c)).toBigInteger()
        bs *= base
    }
    return sum
}

fun String.isSubsequenceOf(other: String): Boolean {
    if (this.isEmpty()) return true
    var i = 0
    for (o in other) {
        if (o == this[i]) i++
        if (i == this.lastIndex + 1) return true
    }
    return false
}

/**
 * make triple. e.g.:`1 to 2 to 3`
 */
infix fun <A, B, C> Pair<A, B>.to(c: C): Triple<A, B, C> = Triple(this.first, this.second, c)

fun YesNo(b: Boolean): String = if (b) Yes else No
val Yes = "Yes"
val No = "No"
fun YES_NO(b: Boolean): String = if (b) YES else NO
val YES = "YES"
val NO = "NO"

/**
 * same usage as `IntArray.scan`, but it will faster than that.
 */
inline fun IntArray.scanArray(initial: Int, operation: (acc: Int, Int) -> Int): IntArray {
    val accumulator = IntArray(this.size + 1).apply { this[0] = initial }
    for (i in this.indices) accumulator[i + 1] = operation(accumulator[i], this[i])
    return accumulator
}

/**
 * same usage as `LongArray.scan`, but it will faster than that.
 */
inline fun LongArray.scanArray(initial: Long, operation: (acc: Long, Long) -> Long): LongArray {
    val accumulator = LongArray(this.size + 1).apply { this[0] = initial }
    for (i in this.indices) accumulator[i + 1] = operation(accumulator[i], this[i])
    return accumulator
}

/**
 * same usage as `IntArray.scanReduce`, but it will faster than that.
 */
inline fun IntArray.scanReduceArray(operation: (acc: Int, Int) -> Int): IntArray {
    val accumulator = IntArray(this.size).apply { this[0] = this@scanReduceArray[0] }
    for (i in 1..this.lastIndex) accumulator[i] = operation(accumulator[i - 1], this[i])
    return accumulator
}

/**
 * same usage as `LongArray.scanReduce`, but it will faster than that.
 */
inline fun LongArray.scanReduceArray(operation: (acc: Long, Long) -> Long): LongArray {
    val accumulator = LongArray(this.size).apply { this[0] = this@scanReduceArray[0] }
    for (i in 1..this.lastIndex) accumulator[i] = operation(accumulator[i - 1], this[i])
    return accumulator
}

fun IntArray.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun LongArray.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun CharArray.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun <T> Array<T>.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun <T> MutableList<T>.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun Array<IntArray>.swap(a: Int, b: Int, c: Int, d: Int): Unit =
    run { val temp = this[a][b]; this[a][b] = this[c][d]; this[c][d] = temp }

fun Array<LongArray>.swap(a: Int, b: Int, c: Int, d: Int): Unit =
    run { val temp = this[a][b]; this[a][b] = this[c][d]; this[c][d] = temp }

fun Array<CharArray>.swap(a: Int, b: Int, c: Int, d: Int): Unit =
    run { val temp = this[a][b]; this[a][b] = this[c][d]; this[c][d] = temp }

fun IntArray.changeMinOf(i: Int, v: Int): Boolean = run { if (this[i] > v) run { this[i] = v; true } else false }
fun IntArray.changeMaxOf(i: Int, v: Int): Boolean = run { if (this[i] < v) run { this[i] = v; true } else false }
fun LongArray.changeMinOf(i: Int, v: Long): Boolean = run { if (this[i] > v) run { this[i] = v; true } else false }
fun LongArray.changeMaxOf(i: Int, v: Long): Boolean = run { if (this[i] < v) run { this[i] = v; true } else false }
fun Array<IntArray>.changeMinOf(i: Int, j: Int, v: Int): Boolean = this[i].changeMinOf(j, v)
fun Array<IntArray>.changeMaxOf(i: Int, j: Int, v: Int): Boolean = this[i].changeMaxOf(j, v)
fun Array<LongArray>.changeMinOf(i: Int, j: Int, v: Long): Boolean = this[i].changeMinOf(j, v)
fun Array<LongArray>.changeMaxOf(i: Int, j: Int, v: Long): Boolean = this[i].changeMaxOf(j, v)
fun Array<Array<IntArray>>.changeMinOf(i: Int, j: Int, k: Int, v: Int): Boolean = this[i].changeMinOf(j, k, v)
fun Array<Array<IntArray>>.changeMaxOf(i: Int, j: Int, k: Int, v: Int): Boolean = this[i].changeMaxOf(j, k, v)
fun Array<Array<LongArray>>.changeMinOf(i: Int, j: Int, k: Int, v: Long): Boolean = this[i].changeMinOf(j, k, v)
fun Array<Array<LongArray>>.changeMaxOf(i: Int, j: Int, k: Int, v: Long): Boolean = this[i].changeMaxOf(j, k, v)

/**
 * merge two sorted LongArray.
 * O(dist.size + source.size) でソート済みの配列2つをソート済みでマージする
 */
fun mergeSortedArray(dist: LongArray, source: LongArray): LongArray {
    val result = LongArray(dist.size + source.size)
    var di = 0
    var si = 0
    while (di in dist.indices || si in source.indices) {
        result[di + si] = when {
            di !in dist.indices -> source[si++]
            si !in source.indices -> dist[di++]
            dist[di] < source[si] -> dist[di++]
            else -> source[si++]
        }
    }
    return result
}

/**
 * merge sorted LongArray.
 * O(dist.size+1) で dist(ソート済み配列)と「distの各要素+value」の要素をソート済みでマージする
 */
fun mergeSortedArray(dist: LongArray, value: Long): LongArray {
    return mergeSortedArray(dist, LongArray(dist.size) { dist[it] + value })
}

private fun compress(array: IntArray, values: IntArray = array.toList().distinct().sorted().toIntArray()): IntArray {
    return IntArray(array.size) { java.util.Arrays.binarySearch(values, array[it]) }
}

private fun compress(array: LongArray, values: LongArray = array.toList().distinct().sorted().toLongArray()): IntArray {
    return IntArray(array.size) { java.util.Arrays.binarySearch(values, array[it]) }
}

private inline fun <reified T> compress(
    array: Array<T>,
    values: Array<T> = array.toList().distinct().toTypedArray()
): IntArray {
    return IntArray(array.size) { java.util.Arrays.binarySearch(values, array[it]) }
}
