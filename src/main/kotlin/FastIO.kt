@file:Suppress("unused")

fun main() = Messiah(123).exec {
    val n = readInt()

    println(n)

    debugln("test")

    debugln {
        var a = 1
        while (true) {
            a++
            System.out.println(a)
            kotlin.io.println(a)
        }
    }
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused", "HasPlatformType", "MemberVisibilityCanBePrivate", "ClassName")
class SimpleFastIO(private val separator: String = System.lineSeparator()) {
    private val br: java.io.BufferedReader = System.`in`.bufferedReader()
    private val sb: StringBuilder = StringBuilder()
    private val enableDebugMode =
        runCatching { "ENABLE_DEBUG_MODE_FOR_COMPETITIVE_PROGRAMING" in System.getenv() }.getOrNull() ?: false

    fun readLine(): String = br.readLine()!!
    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = run { sb.append(any.toString() + separator) }
    fun flush() = run { kotlin.io.println(sb); sb.clear() }
    fun debug(any: Any): Unit = run { if (enableDebugMode) System.err.println(any) }

    fun exec(action: SimpleFastIO.() -> Unit) {
        var t: Throwable? = null
        Thread(null, { action() }, "solve", 128 * 1024 * 1024)
            .apply { setUncaughtExceptionHandler { _, t1 -> t = t1 } }
            .apply { start() }.join()
        t?.let { throw it }
        kotlin.io.print(sb)
    }
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused", "HasPlatformType", "MemberVisibilityCanBePrivate", "ClassName")
class SimpleFastIOWithToken(private val separator: String = System.lineSeparator()) {
    private val br: java.io.BufferedReader = System.`in`.bufferedReader()
    private var st: java.util.StringTokenizer = java.util.StringTokenizer("")
    private val sb: StringBuilder = StringBuilder()
    private fun prepareNext() = run { if (!st.hasMoreTokens()) st = java.util.StringTokenizer(br.readLine()) }
    private val enableDebugMode =
        runCatching { "ENABLE_DEBUG_MODE_FOR_COMPETITIVE_PROGRAMING" in System.getenv() }.getOrNull() ?: false

    fun readString(): String = run { prepareNext() }.run { st.nextToken() }
    fun readInt(): Int = Integer.valueOf(readString())
    fun readLong(): Long = java.lang.Long.valueOf(readString())

    fun readString(size: Int): Array<String> = Array(size) { readString() }
    fun readLong(size: Int): LongArray = LongArray(size) { readLong() }
    fun readInt(size: Int): IntArray = IntArray(size) { readInt() }

    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = run { sb.append(any.toString() + separator) }
    fun flush() = run { kotlin.io.println(sb); sb.clear() }
    fun debug(any: Any): Unit = run { if (enableDebugMode) System.err.println(any) }
    fun debug(action: () -> Unit): Unit = run { if (enableDebugMode) action() }

    fun exec(action: SimpleFastIOWithToken.() -> Unit) {
        var t: Throwable? = null
        Thread(null, { action() }, "solve", 128 * 1024 * 1024)
            .apply { setUncaughtExceptionHandler { _, t1 -> t = t1 } }
            .apply { start() }.join()
        t?.let { throw it }
        kotlin.io.print(sb)
    }

    fun readLine(): Nothing = error("readLine is disabled.")
}

private val localEnv =
    runCatching { "ENABLE_DEBUG_MODE_FOR_COMPETITIVE_PROGRAMING" in System.getenv() }.getOrNull() ?: false
private const val MOD = 1_000_000_007L

/**
 * TODO add doc
 * TODO add test
 * TODO TLE 検知してそれ用の値を吐く機能を作りたい（でもギリギリでも通ることもあるし有効/無効切り返したい）
 */
@Suppress("MemberVisibilityCanBePrivate", "FunctionName", "PropertyName")
private class Messiah(private val mod: Int) {
    //////////////////////////////////////////////////
    // IO
    //////////////////////////////////////////////////
    private val input = System.`in`
    private val buffer = ByteArray(1024)
    private var pointer = 0
    private var bufferLength = 0
    private val sb = StringBuilder()
    private fun Byte.isPrintable() = this in 33..126
    private fun Byte.isNumeric() = this in '0'.toByte()..'9'.toByte()
    private fun Byte.toNumVal() = if (isNumeric()) this - '0'.toByte() else error("$this is not numeric")
    private val lineSeparator: String = System.lineSeparator()

    private fun hasNextByte(): Boolean {
        return if (pointer < bufferLength) true else {
            pointer = 0
            bufferLength = input.read(buffer)
            bufferLength > 0
        }
    }

    private fun readByte(): Byte = if (hasNextByte()) buffer[pointer++] else -1
    private fun skipUnprintable() = run { while (hasNextByte() && !buffer[pointer].isPrintable()) pointer++ }
    private fun hasNext(): Boolean = run { skipUnprintable(); hasNextByte() }
    private fun hasNextOrError() = run { if (!hasNext()) error("has no next element.") }

    fun readString(): String {
        hasNextOrError()
        val sb = StringBuilder()
        var b = readByte()
        while (b.isPrintable()) {
            sb.appendCodePoint(b.toInt())
            b = readByte()
        }
        return sb.toString()
    }

    fun readLong(): Long {
        hasNextOrError()
        var n = 0L
        var negative = false
        var b = readByte()
        if (b == '-'.toByte()) {
            negative = true
            b = readByte()
        }
        if (!b.isNumeric()) error("$b is not numeric.")
        while (true) {
            when {
                b.isNumeric() -> n = n * 10 + b.toNumVal()
                b.toInt() == -1 || !b.isPrintable() -> return if (negative) -n else n
                else -> error("failed to parse. [n=$n, b=$b]")
            }
            b = readByte()
        }
    }

    fun readInt() = readLong()
        .let { if (it in Int.MIN_VALUE..Int.MAX_VALUE) it.toInt() else error("$it is not in range of Int.") }

    fun readIntAsIndex() = readInt().dec()

    fun readDouble(): Double {
        hasNextOrError()
        var n = 0.0
        var div = 1.0
        var negative = false
        var b = readByte()
        if (b == '-'.toByte()) {
            negative = true
            b = readByte()
        }
        do n = n * 10 + b.toNumVal()
        while (run { b = readByte() }.run { b.isNumeric() })
        if (b == '.'.toByte()) {
            while (run { b = readByte() }.run { b.isNumeric() })
                n += b.toNumVal() / (run { div *= 10 }.run { div })
        }
        return if (negative) -n else n
    }

    fun readString(size: Int): Array<String> = Array(size) { readString() }

    @Suppress("UNUSED_PARAMETER")
    fun readChars(h: Int, w: Int): Array<CharArray> = Array(h) { readString().toCharArray() }
    fun readLong(size: Int): LongArray = LongArray(size) { readLong() }
    fun readLong(h: Int, w: Int): Array<LongArray> = Array(h) { readLong(w) }
    fun readInt(size: Int): IntArray = IntArray(size) { readInt() }

    // todo これ系全体的に追加？
    fun readInt(size: Int, op: (Int) -> Int): IntArray = IntArray(size) { op(readInt()) }
    fun readInt(h: Int, w: Int): Array<IntArray> = Array(h) { readInt(w) }
    fun readIntAsIndex(size: Int): IntArray = IntArray(size) { readIntAsIndex() }
    fun readIntAsIndex(h: Int, w: Int): Array<IntArray> = Array(h) { readIntAsIndex(w) }
    fun readDouble(size: Int): DoubleArray = DoubleArray(size) { readDouble() }
    fun readDouble(h: Int, w: Int): Array<DoubleArray> = Array(h) { readDouble(w) }
    fun readBooleanWithSeparator(size: Int, trueElement: String): BooleanArray =
        BooleanArray(size) { readString() == trueElement }

    fun readBooleanWithSeparator(h: Int, w: Int, trueElement: String): Array<BooleanArray> =
        Array(h) { readBooleanWithSeparator(w, trueElement) }

    @Suppress("UNUSED_PARAMETER")
    fun readBooleanWithoutSeparator(size: Int, trueElement: Char): BooleanArray =
        readString().map { it == trueElement }.toBooleanArray()

    fun readBooleanWithoutSeparator(h: Int, w: Int, trueElement: Char): Array<BooleanArray> =
        Array(h) { readBooleanWithoutSeparator(w, trueElement) }

    fun println(): Unit = run { sb.append(lineSeparator) }
    fun print(any: Any): Unit = run { sb.append(any.toString()) }
    fun println(any: Any): Unit = run { sb.append(any.toString() + lineSeparator) }
    fun flush() = run { kotlin.io.println(sb); sb.clear() }
    fun debug(any: Any): Unit = run { if (localEnv) System.err.print(any) }
    fun debugln(any: Any): Unit = run { if (localEnv) System.err.println(any) }
    fun debugln(): Unit = run { if (localEnv) System.err.println() }
    fun debugExec(action: () -> Unit): Unit = run { if (localEnv) action() }

    fun exec(action: Messiah.() -> Unit) {
        var t: Throwable? = null
        Thread(null, { action() }, "solve", 128 * 1024 * 1024)
            .apply { setUncaughtExceptionHandler { _, t1 -> t = t1 } }
            .apply { start() }.join()
        t?.let { throw it }
        kotlin.io.print(sb)
    }

    // TODO lineで読めるようにできる？ separatorの場合分けとか必要そうだけど欲しいかも
    fun readLine(): Nothing = error("readLine is disabled.")

    //////////////////////////////////////////////////
    // MOD
    //////////////////////////////////////////////////
    private val modIsPrime: Boolean = MOD.toBigInteger().isProbablePrime(100)

    fun Long.invertMod(): Long = if (modIsPrime) this.powMod(MOD - 2L) else invGcd(this).second
    fun Long.safeMod(): Long = (this % MOD).let { if (it < 0) it + MOD else it }
    fun Long.plusMod(other: Long): Long = this.plus(other).safeMod()
    fun Long.minusMod(other: Long): Long = this.minus(other).safeMod()
    fun Long.timesMod(other: Long): Long = this.times(other).safeMod()
    fun Long.divMod(other: Long): Long = this.times(other.invertMod()).safeMod()

    fun Long.powMod(p: Long): Long {
        var x = this % MOD
        var y = p
        var result = 1L
        while (y > 0) {
            if (y % 2 == 1L) result = (result * x) % MOD
            y = y shr 1
            x = (x * x) % MOD
        }
        return result
    }

    fun Long.powMod(p: Long, m: Int): Long {
        var x = this % m
        var y = p
        var result = 1L
        while (y > 0) {
            if (y % 2 == 1L) result = (result * x) % m
            y = y shr 1
            x = (x * x) % m
        }
        return result
    }

    fun invGcd(a: Long): Pair<Long, Long> {
        val am = a.safeMod()
        if (a == 0L) return MOD.toLong() to 0L
        var s = MOD.toLong()
        var t = am
        var m0 = 0L
        var m1 = 1L
        while (t != 0L) {
            val u = s / t
            s -= t * u
            m0 -= m1 * u

            var temp = s
            s = t
            t = temp
            temp = m0
            m0 = m1
            m1 = temp
        }
        if (m0 < 0) m0 += MOD / s
        return s to m0
    }

    //////////////////////////////////////////////////
    // Misc
    //////////////////////////////////////////////////
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
     * [base]進数表記のリストに変換する、負数でも使えるのかなこれ
     */
    fun Long.toNumbersWithBase(base: Long): List<Long> {
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
    operator fun <E : Comparable<E>> List<E>.compareTo(other: List<E>): Int {
        this.size.compareTo(other.size).takeIf { it != 0 }?.let { return it }
        for ((a, b) in this.zip(other)) a.compareTo(b).takeIf { it != 0 }?.let { return it }
        return 0
    }

    /**
     * get characters numeric value.
     * e.g.: '0' to 0
     */
    fun Char.toNumericValue(): Int = this - '0'

    fun Char.caseToIndexedValue(): Int = this - 'a'

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

    fun IntArray.swap(a: Int, b: Int): Unit = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
    fun LongArray.swap(a: Int, b: Int): Unit = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
    fun CharArray.swap(a: Int, b: Int): Unit = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
    fun <T> Array<T>.swap(a: Int, b: Int): Unit = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
    fun <T> MutableList<T>.swap(a: Int, b: Int): Unit = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
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
}
