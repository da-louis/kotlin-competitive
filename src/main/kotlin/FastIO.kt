@file:Suppress("unused")

fun sample() = SimpleFastIO().exec {
    val n = readLine()

    println(n)
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

    fun readString(): String = run { prepareNext() }.run { st.nextToken() }
    fun readInt(): Int = Integer.valueOf(readString())
    fun readLong(): Long = java.lang.Long.valueOf(readString())

    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = run { sb.append(any.toString() + separator) }
    fun flush() = run { kotlin.io.println(sb); sb.clear() }

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
@Suppress("unused", "ClassName", "SpellCheckingInspection", "ConvertToStringTemplate", "MemberVisibilityCanBePrivate")
class FastIO(private val separator: String = System.lineSeparator()) {
    private val input = System.`in`
    private val buffer = ByteArray(1024)
    private var pointer = 0
    private var bufferLength = 0
    private val sb = StringBuilder()
    private fun Byte.isPrintable() = this in 33..126
    private fun Byte.isNumeric() = this in '0'.toByte()..'9'.toByte()
    private fun Byte.toNumVal() = if (isNumeric()) this - '0'.toByte() else error("$this is not numeric")

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

    fun println(): Unit = run { sb.append(separator) }
    fun print(any: Any): Unit = run { sb.append(any.toString()) }
    fun println(any: Any): Unit = run { sb.append(any.toString() + separator) }
    fun flush() = run { kotlin.io.println(sb); sb.clear() }

    fun exec(action: FastIO.() -> Unit) {
        var t: Throwable? = null
        Thread(null, { action() }, "solve", 128 * 1024 * 1024)
            .apply { setUncaughtExceptionHandler { _, t1 -> t = t1 } }
            .apply { start() }.join()
        t?.let { throw it }
        kotlin.io.print(sb)
    }

    fun readLine(): Nothing = error("readLine is disabled.")
}
