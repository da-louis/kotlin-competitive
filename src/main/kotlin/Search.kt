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
 * needs [powExact]
 */
@Suppress("unused", "SameParameterValue")
inline fun <reified T> searchAllPatterns(patterns: Array<T>, length: Int, action: (Array<T>) -> Unit) {
    val patternSize = patterns.size
    val allPatterns = patternSize.powExact(length.toLong())
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
