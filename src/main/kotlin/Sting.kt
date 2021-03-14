import kotlin.math.max

// TODO DP配列を返すほうが使い勝手良さそう、復元も別メソッドで込にする？
fun getLcsLength(s: String, t: String): Int {
    val lcs = Array(s.length + 1) { IntArray(t.length + 1) }

    for (i in s.indices) for (j in t.indices) {
        lcs[i + 1][j + 1] = if (s[i] == t[j]) lcs[i][j] + 1 else max(lcs[i + 1][j], lcs[i][j + 1])
    }

    return lcs.last().last()
}

fun <T> runLengthEncode(list: List<T>): List<Pair<T, Int>> {
    if (list.isEmpty()) return emptyList()
    val result = mutableListOf<Pair<T, Int>>()
    var last = list.first()
    var length = 0
    fun addToResult() = result.add(last to length)
    for ((i, t) in list.withIndex()) {
        if (t != last) {
            addToResult()
            length = 1
        } else length++
        last = t
        if (i == list.lastIndex) addToResult()
    }
    return result
}

fun runLengthEncode(s: String): List<Pair<Char, Int>> = runLengthEncode(s.toList())

fun runLengthDecode(runLengthEncoded: List<Pair<Char, Int>>): String {
    val result = StringBuilder()
    for ((char, count) in runLengthEncoded) {
        result.append(char.toString().repeat(count))
    }
    return result.toString()
}
