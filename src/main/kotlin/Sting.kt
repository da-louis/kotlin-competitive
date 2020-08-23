import kotlin.math.max

fun getLcsLength(s: String, t: String): Int {
    val lcs = Array(s.length + 1) { IntArray(t.length + 1) }

    for (i in s.indices) for (j in t.indices) {
        lcs[i + 1][j + 1] = if (s[i] == t[j]) lcs[i][j] + 1 else max(lcs[i + 1][j], lcs[i][j + 1])
    }

    return lcs.last().last()
}

fun runLengthEncode(s: String): List<Pair<Char, Int>> {
    if (s.isEmpty()) return emptyList()

    val result = mutableListOf<Pair<Char, Int>>()
    var beforeChar = s.first()
    var length = 1
    fun addToResult() = result.add(beforeChar to length)

    for (c in s.drop(1)) {
        if (c == beforeChar) length++ else {
            addToResult()
            beforeChar = c
            length = 1
        }
    }
    addToResult()

    return result
}

fun runLengthDecode(runLengthEncoded: List<Pair<Char, Int>>): String {
    val result = StringBuilder()
    for ((char, count) in runLengthEncoded) {
        result.append(char.toString().repeat(count))
    }
    return result.toString()
}
