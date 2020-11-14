@file:Suppress("unused")

fun main() = Messiah().exec {
    val n = readInt()

    println(n)

    debug("test")

    debug {
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
    fun readLine(): String = br.readLine()!!
    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = run { sb.append(any.toString() + separator) }
    fun flush() = run { kotlin.io.println(sb); sb.clear() }
    fun debug(any: Any): Unit =
        run { if ("ENABLE_DEBUG_MODE_FOR_COMPETITIVE_PROGRAMING" in System.getenv()) System.err.println(any) }

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
    private val enableDebugMode = "ENABLE_DEBUG_MODE_FOR_COMPETITIVE_PROGRAMING" in System.getenv()

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

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("MemberVisibilityCanBePrivate", "FunctionName", "PropertyName")
private class Messiah(private val separator: String = System.lineSeparator()) {
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
    private val enableDebugMode = "ENABLE_DEBUG_MODE_FOR_COMPETITIVE_PROGRAMING" in System.getenv()

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
    fun readChars2D(h: Int): Array<CharArray> = Array(h) { readString().toCharArray() }
    fun readLong(size: Int): LongArray = LongArray(size) { readLong() }
    fun readLong2D(h: Int, w: Int): Array<LongArray> = Array(h) { readLong(w) }
    fun readInt(size: Int): IntArray = IntArray(size) { readInt() }
    fun readInt2D(h: Int, w: Int): Array<IntArray> = Array(h) { readInt(w) }
    fun readIntAsIndex(size: Int): IntArray = IntArray(size) { readIntAsIndex() }
    fun readIntAsIndex2D(h: Int, w: Int): Array<IntArray> = Array(h) { readIntAsIndex(w) }
    fun readDouble(size: Int): DoubleArray = DoubleArray(size) { readDouble() }
    fun readDouble2D(h: Int, w: Int): Array<DoubleArray> = Array(h) { readDouble(w) }

    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any.toString()) }
    fun println(any: Any): Unit = run { sb.append(any.toString() + separator) }
    fun flush() = run { kotlin.io.println(sb); sb.clear() }
    fun debug(any: Any): Unit = run { if (enableDebugMode) System.err.println(any) }
    fun debug(action: () -> Unit): Unit = run { if (enableDebugMode) action() }

    fun exec(action: Messiah.() -> Unit) {
        var t: Throwable? = null
        Thread(null, { action() }, "solve", 128 * 1024 * 1024)
            .apply { setUncaughtExceptionHandler { _, t1 -> t = t1 } }
            .apply { start() }.join()
        t?.let { throw it }
        kotlin.io.print(sb)
    }

    fun readLine(): Nothing = error("readLine is disabled.")

    //////////////////////////////////////////////////
    // Misc
    //////////////////////////////////////////////////
    /**
     * `[this].indices()` is sugar syntax of `0 until [this]`.
     */
    fun Int.indices(): IntRange = 0 until this

    /**
     * `[index] in [this]` is sugar syntax of `index in 0 until [this]`.
     */
    operator fun Int.contains(index: Int): Boolean = index in this.indices()

    /**
     * make triple. e.g.:`1 to 2 to 3`
     */
    fun <A, B, C> Pair<A, B>.to(c: C): Triple<A, B, C> = Triple(this.first, this.second, c)

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

    fun IntArray.changeMinOf(i: Int, v: Int): Unit = run { this[i] = kotlin.math.min(this[i], v) }
    fun IntArray.changeMaxOf(i: Int, v: Int): Unit = run { this[i] = kotlin.math.max(this[i], v) }
    fun LongArray.changeMinOf(i: Int, v: Long): Unit = run { this[i] = kotlin.math.min(this[i], v) }
    fun LongArray.changeMaxOf(i: Int, v: Long): Unit = run { this[i] = kotlin.math.max(this[i], v) }
}
