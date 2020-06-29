/**
 * base of binarySearch/lowerBound/upperBound
 */
fun <T : Comparable<T>> binarySearchBase(size: Int, check: (T) -> Boolean, getValue: (Int) -> T): Int {
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
 */
fun <T : Comparable<T>> List<T>.binarySearch(check: (T) -> Boolean) = binarySearchBase(size, check) { this[it] }
fun <T : Comparable<T>> Array<T>.binarySearch(check: (T) -> Boolean) = binarySearchBase(size, check) { this[it] }
fun IntArray.binarySearch(check: (Int) -> Boolean) = binarySearchBase(size, check) { this[it] }
fun LongArray.binarySearch(check: (Long) -> Boolean) = binarySearchBase(size, check) { this[it] }

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
