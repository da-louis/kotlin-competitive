@file:Suppress("unused")

fun sample() = SimpleFastIO().exec {
    val n = readLine()
    val list = readLine().split(' ').map { it.toInt() }

    println(n)
    println(list, System.lineSeparator())
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused", "HasPlatformType")
class SimpleFastIO {
    private val br: java.io.BufferedReader = System.`in`.bufferedReader()
    private val sb: StringBuilder = StringBuilder()
    fun readLine(): String = br.readLine()!!
    fun println(): Unit = run { sb.appendln() }
    fun print(any: Any): Unit = run { sb.append(any) }
    fun println(any: Any): Unit = run { sb.appendln(any) }
    fun <T> print(any: Iterable<T>, separator: String) = run { sb.append(any.joinToString(separator)) }
    fun <T> println(any: Iterable<T>, separator: String) = run { sb.appendln(any.joinToString(separator)) }
    fun exec(action: SimpleFastIO.() -> Unit) = run {
        Thread(null, { action() }, "solve", 128 * 1024 * 1024).apply { start() }.join()
    }.run { kotlin.io.print(sb) }
}
