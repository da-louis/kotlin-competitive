/**
 * base of binarySearch/lowerBound/upperBound
 */
inline fun <T : Comparable<T>> binarySearchBase(size: Int, check: (T) -> Boolean, getValue: (Int) -> T): Int {
    var ng = -1
    var ok = size

    while (ok - ng > 1) {
        val mid = (ok + ng) / 2
        if (check(getValue(mid))) ok = mid else ng = mid
    }
    return ok
}

/**
 * list must be sorted.
 * find element's index, fulfil `check`'s condition.
 *
 * note: these methods will shadowing default `binarySearch`.
 *  (i think default `binarySearch` is not useful for competitive-programming, so no problems at all.)
 */
inline fun <T : Comparable<T>> List<T>.binarySearch(check: (T) -> Boolean) = binarySearchBase(size, check) { this[it] }
inline fun <T : Comparable<T>> Array<T>.binarySearch(check: (T) -> Boolean) = binarySearchBase(size, check) { this[it] }
inline fun IntArray.binarySearch(check: (Int) -> Boolean) = binarySearchBase(size, check) { this[it] }
inline fun LongArray.binarySearch(check: (Long) -> Boolean) = binarySearchBase(size, check) { this[it] }

/**
 * list must be sorted.
 * find element's index, greater than or equals key.
 */
fun <T : Comparable<T>> List<T>.lowerBound(key: T) = binarySearch { it >= key }
fun <T : Comparable<T>> Array<T>.lowerBound(key: T) = binarySearch { it >= key }
fun IntArray.lowerBound(key: Int) = binarySearch { it >= key }
fun LongArray.lowerBound(key: Long) = binarySearch { it >= key }

/**
 * list must be sorted.
 * find element's index, greater than key.
 */
fun <T : Comparable<T>> List<T>.upperBound(key: T) = binarySearch { it > key }
fun <T : Comparable<T>> Array<T>.upperBound(key: T) = binarySearch { it > key }
fun IntArray.upperBound(key: Int) = binarySearch { it > key }
fun LongArray.upperBound(key: Long) = binarySearch { it > key }

/**
 * simplify search-all-pattern (a.k.a. bit-whole-search)
 */
@Suppress("unused", "SameParameterValue")
inline fun <reified T> searchAllPatterns(patterns: Array<T>, length: Int, action: (Array<T>) -> Unit) {
    val patternSize = patterns.size
    check(length < 40) { "length:$length is too large! (must be TLE)" }
    val pow = (1L..length).fold(1L) { acc: Long, _: Long -> Math.multiplyExact(acc, patternSize.toLong()) }
    check(pow <= Int.MAX_VALUE) { "pow:$pow is too large! (must be TLE)" }
    val allPatterns = pow.toInt()
    for (i in 0 until allPatterns) {
        val array = Array(length) { patterns[0] }
        var div = 1
        for (j in 0 until length) {
            array[length - j - 1] = patterns[(i / div) % patternSize]
            div *= patternSize
        }
        action(array)
    }
}

/**
 * TODO add doc
 * TODO sequenceにしたら早くなる？？
 */
fun <T> permutations(source: List<T>): List<List<T>> {
    val n = source.size
    if (n == 1) return listOf(source)
    val result = mutableListOf<List<T>>()
    val copied = source.toMutableList()

    fun MutableList<T>.swap(x: Int, y: Int) {
        val temp = this[x]
        this[x] = this[y]
        this[y] = temp
    }

    fun genPerm(currSource: MutableList<T>, currentIndex: Int) {
        if (currentIndex == n) result.add(currSource.toList())
        for (i in currentIndex until n) {
            currSource.swap(currentIndex, i)
            genPerm(currSource, currentIndex + 1)
            currSource.swap(currentIndex, i)
        }
    }

    genPerm(copied, 0)

    return result
}

/**
 * generate rotated 4 graph clockwise. (0,3,6,9)
 * TODO add test
 *
 * @param graph grid graph
 * @return [rotated graph, height, width]
 */
private inline fun <reified T> generateRotatedGraph(graph: Array<Array<T>>): Array<Triple<Array<Array<T>>, Int, Int>> {
    val height = graph.size
    val width = graph.first().size
    val defaultValue = graph.first().first()

    val graphs = Array(4) { Triple(emptyArray<Array<T>>(), 0, 0) }
    graphs[0] = Triple(Array(height) { Array(width) { defaultValue } }, height, width)
    graphs[1] = Triple(Array(width) { Array(height) { defaultValue } }, width, height)
    graphs[2] = Triple(Array(height) { Array(width) { defaultValue } }, height, width)
    graphs[3] = Triple(Array(width) { Array(height) { defaultValue } }, width, height)

    for (y in 0 until height) for (x in 0 until width) {
        graph[y][x].let {
            graphs[0].first[y][x] = it
            graphs[1].first[x][width - y - 1] = it
            graphs[2].first[height - y - 1][width - x - 1] = it
            graphs[3].first[height - x - 1][y] = it
        }
    }

    return graphs
}

/**
 * generate rotated grid-graph clockwise. (90 degree)
 * TODO add test
 *
 * @param graph grid-graph
 * @return rotated grid-graph
 */
private inline fun <reified T> rotateRight(graph: Array<Array<T>>): Array<Array<T>> {
    val h = graph.size
    val w = graph.first().size
    val defaultValue = graph.first().first()
    val rotated = Array(w) { Array(h) { defaultValue } }
    for (y in 0 until h) for (x in 0 until w) rotated[x][h - y - 1] = graph[y][x]
    return rotated
}
