@file:Suppress("unused")

/**
 * idk this class is useful or not :/
 * just practice for custom-setter
 */
class MutableAnswer<T : Number>(value: T, private val action: (T, T) -> T) {
    var value = value
        set(v) {
            field = action(field, v)
        }

    override fun toString() = this.value.toString()
    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MutableAnswer<*>
        return value == other.value
    }

    companion object {
        fun <T : Number> mutableAnswerOf(value: T, action: (T, T) -> T) = MutableAnswer(value, action)
    }
}

/**
 * same usage as `IntArray.scan`, but it will faster than that.
 * TODO add test
 */
fun IntArray.scanWithIntArray(initial: Int, operation: (acc: Int, Int) -> Int): IntArray {
    val accumulator = IntArray(this.size + 1)
    accumulator[0] = initial
    for (i in this.indices) accumulator[i + 1] = operation(accumulator[i], this[i])
    return accumulator
}

/**
 * same usage as `LongArray.scan`, but it will faster than that.
 * TODO add test
 */
fun LongArray.scanWithLongArray(initial: Long, operation: (acc: Long, Long) -> Long): LongArray {
    val accumulator = LongArray(this.size + 1)
    accumulator[0] = initial
    for (i in this.indices) accumulator[i + 1] = operation(accumulator[i], this[i])
    return accumulator
}

/**
 * same usage as `IntArray.scanReduce`, but it will faster than that.
 * TODO add test
 */
fun IntArray.scanReduceWithIntArray(operation: (acc: Int, Int) -> Int): IntArray {
    val accumulator = IntArray(this.size)
    accumulator[0] = this[0]
    for (i in 1..this.lastIndex) accumulator[i] = operation(accumulator[i - 1], this[i])
    return accumulator
}

/**
 * same usage as `LongArray.scanReduce`, but it will faster than that.
 * TODO add test
 */
fun LongArray.scanReduceWithLongArray(operation: (acc: Long, Long) -> Long): LongArray {
    val accumulator = LongArray(this.size)
    accumulator[0] = this[0]
    for (i in 1..this.lastIndex) accumulator[i] = operation(accumulator[i - 1], this[i])
    return accumulator
}

fun IntArray.swap(a: Int, b: Int) {
    val temp = this[a]
    this[a] = this[b]
    this[b] = temp
}

fun LongArray.swap(a: Int, b: Int) {
    val temp = this[a]
    this[a] = this[b]
    this[b] = temp
}

fun CharArray.swap(a: Int, b: Int) {
    val temp = this[a]
    this[a] = this[b]
    this[b] = temp
}

fun IntArray.changeMinOf(i: Int, v: Int) = run { this[i] = kotlin.math.min(this[i], v) }
fun IntArray.changeMaxOf(i: Int, v: Int) = run { this[i] = kotlin.math.max(this[i], v) }

fun LongArray.changeMinOf(i: Int, v: Long) = run { this[i] = kotlin.math.min(this[i], v) }
fun LongArray.changeMaxOf(i: Int, v: Long) = run { this[i] = kotlin.math.max(this[i], v) }
