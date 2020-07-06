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
    fun readLine() = br.readLine()!!
    fun println() = sb.appendln()
    fun print(any: Any) = sb.append(any)
    fun println(any: Any) = sb.appendln(any)
    fun <T> print(any: Iterable<T>, separator: String) = sb.append(any.joinToString(separator))
    fun <T> println(any: Iterable<T>, separator: String) = sb.appendln(any.joinToString(separator))
    fun exec(action: SimpleFastIO.() -> Unit) = run { action() }.run { kotlin.io.println(sb) }
}
