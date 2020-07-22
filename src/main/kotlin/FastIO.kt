@file:Suppress("unused")

fun sample() = SimpleFastIO().exec {
    val n = readLine()

    println(n)
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused", "HasPlatformType", "MemberVisibilityCanBePrivate")
class SimpleFastIO(private val separator: String = "\n") {
    private val br: java.io.BufferedReader = System.`in`.bufferedReader()
    private val sb: StringBuilder = StringBuilder()
    fun readLine(): String = br.readLine()!!
    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = print(any.toString() + separator)
    fun exec(action: SimpleFastIO.() -> Unit) = run {
        Thread(null, { action() }, "solve", 128 * 1024 * 1024).apply { start() }.join()
    }.run { kotlin.io.print(sb) }
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused", "HasPlatformType", "MemberVisibilityCanBePrivate")
class SimpleFastIOWithToken(private val separator: String = "\n") {
    private val br: java.io.BufferedReader = System.`in`.bufferedReader()
    private var st: java.util.StringTokenizer = java.util.StringTokenizer("")
    private val sb: StringBuilder = StringBuilder()
    private fun prepareNext() = run { if (!st.hasMoreTokens()) st = java.util.StringTokenizer(br.readLine()) }

    fun readString(): String = run { prepareNext() }.run { st.nextToken() }
    fun readStringList(size: Int): List<String> = List(size) { readString() }
    fun readInt(): Int = Integer.valueOf(readString())
    fun readIntList(size: Int): List<Int> = List(size) { readInt() }
    fun readLong(): Long = java.lang.Long.valueOf(readString())
    fun readLongList(size: Int): List<Long> = List(size) { readLong() }

    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = print(any.toString() + separator)

    fun exec(action: SimpleFastIOWithToken.() -> Unit) = run {
        Thread(null, { action() }, "solve", 128 * 1024 * 1024).apply { start() }.join()
    }.run { kotlin.io.print(sb) }

    fun readLine(): Nothing = error("readLine is disabled.")
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused", "ClassName", "SpellCheckingInspection", "ConvertToStringTemplate", "MemberVisibilityCanBePrivate")
class FastIO(private val separator: String = "\n") {
    private val input = System.`in`
    private val buffer = ByteArray(1024)
    private var pointer = 0
    private var bufferLength = 0
    private val sb = StringBuilder()
    private fun Byte.isPrintable() = this in 33..126
    private fun Byte.isNumeric() = this in '0'.toByte()..'9'.toByte()
    private fun Byte.toNumVal() =
        if (this.isNumeric()) this - '0'.toByte() else error(this.toString() + " is not numeric")

    private fun hasNextByte(): Boolean {
        return if (pointer < bufferLength) true else {
            pointer = 0
            bufferLength = input.read(buffer)
            bufferLength > 0
        }
    }

    private fun readByte(): Byte = if (hasNextByte()) buffer[pointer++] else -1
    private fun skipUnprintable() = run { while (hasNextByte() && !buffer[pointer].isPrintable()) pointer++ }
    private fun hasNext(): Boolean = run { skipUnprintable() }.run { hasNextByte() }
    private fun hasNextOrError() = if (!hasNext()) error("has no next element.") else Unit

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
        if (!b.isNumeric()) error(b.toString() + " is not numeric.")
        while (true) {
            when {
                b.isNumeric() -> n = n * 10 + b.toNumVal()
                b.toInt() == -1 || !b.isPrintable() -> return if (negative) -n else n
                else -> error("failed to parse. [n=" + n + ", b=" + b + "]")
            }
            b = readByte()
        }
    }

    fun readInt() = readLong()
        .let { if (it in Int.MIN_VALUE..Int.MAX_VALUE) it.toInt() else error(it.toString() + " is not in range of Int.") }

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

    fun readStringList(size: Int) = readList(size) { readString() }
    fun readIntList(size: Int) = readList(size) { readInt() }
    fun readLongList(size: Int) = readList(size) { readLong() }
    fun readDoubleList(size: Int) = readList(size) { readDouble() }
    fun readIntArray(size: Int) = IntArray(size) { readInt() }
    fun readLongArray(size: Int) = LongArray(size) { readLong() }
    fun readDoubleArray(size: Int) = DoubleArray(size) { readDouble() }
    inline fun <reified T> readArray(size: Int, init: () -> T) = Array(size) { init() }
    inline fun <T> readList(size: Int, init: () -> T) = List(size) { init() }

    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = print(any.toString() + separator)

    fun exec(code: FastIO.() -> Unit) = run {
        Thread(null, { code() }, "solve", 128 * 1024 * 1024).apply { start() }.join()
    }.run { kotlin.io.print(sb) }

    fun readLine(): Nothing = error("readLine is disabled.")
}
