@file:Suppress("unused")

/**
 * idk this class is useful or not :/
 * just practice for custom-setter
 */
class MutableAnswer<T>(value: T, private val action: (T, T) -> T) {
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
        fun <T> mutableAnswerOf(value: T, action: (T, T) -> T) = MutableAnswer(value, action)
    }
}

/**
 * same usage as `IntArray.scan`, but it will faster than that.
 */
inline fun IntArray.scanWithIntArray(initial: Int, operation: (acc: Int, Int) -> Int): IntArray {
    val accumulator = IntArray(this.size + 1).apply { this[0] = initial }
    for (i in this.indices) accumulator[i + 1] = operation(accumulator[i], this[i])
    return accumulator
}

/**
 * same usage as `LongArray.scan`, but it will faster than that.
 */
inline fun LongArray.scanWithLongArray(initial: Long, operation: (acc: Long, Long) -> Long): LongArray {
    val accumulator = LongArray(this.size + 1).apply { this[0] = initial }
    for (i in this.indices) accumulator[i + 1] = operation(accumulator[i], this[i])
    return accumulator
}

/**
 * same usage as `IntArray.scanReduce`, but it will faster than that.
 */
inline fun IntArray.scanReduceWithIntArray(operation: (acc: Int, Int) -> Int): IntArray {
    val accumulator = IntArray(this.size).apply { this[0] = this@scanReduceWithIntArray[0] }
    for (i in 1..this.lastIndex) accumulator[i] = operation(accumulator[i - 1], this[i])
    return accumulator
}

/**
 * same usage as `LongArray.scanReduce`, but it will faster than that.
 */
inline fun LongArray.scanReduceWithLongArray(operation: (acc: Long, Long) -> Long): LongArray {
    val accumulator = LongArray(this.size).apply { this[0] = this@scanReduceWithLongArray[0] }
    for (i in 1..this.lastIndex) accumulator[i] = operation(accumulator[i - 1], this[i])
    return accumulator
}

fun IntArray.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun LongArray.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun CharArray.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun <T> Array<T>.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }
fun <T> MutableList<T>.swap(a: Int, b: Int) = run { val temp = this[a]; this[a] = this[b]; this[b] = temp }

fun IntArray.changeMinOf(i: Int, v: Int) = run { this[i] = kotlin.math.min(this[i], v) }
fun IntArray.changeMaxOf(i: Int, v: Int) = run { this[i] = kotlin.math.max(this[i], v) }
fun LongArray.changeMinOf(i: Int, v: Long) = run { this[i] = kotlin.math.min(this[i], v) }
fun LongArray.changeMaxOf(i: Int, v: Long) = run { this[i] = kotlin.math.max(this[i], v) }

enum class YesNo {
    Yes, No;

    companion object {
        @JvmStatic
        fun fromValue(b: Boolean): String = (if (b) Yes else No).toString()
    }
}

enum class YES_NO {
    YES, NO;

    companion object {
        @JvmStatic
        fun fromValue(b: Boolean): String = (if (b) YES else NO).toString()
    }
}

/**
 * `[this].indices()` is sugar syntax of `0 until [this]`.
 */
fun Int.indices() = 0 until this

/**
 * `[index] in [this]` is sugar syntax of `index in 0 until [this]`.
 */
operator fun Int.contains(index: Int) = index in this.indices()

/**
 * `[this].closedMap { transform }` is sugar syntax of `(0 until [this]).map{ transform(it) }`.
 */
inline fun <R> Int.indicesMap(transform: (Int) -> R) = this.indices().map(transform)

/**
 * `[this].closedMap(x) { transform }` is sugar syntax of
 * `(0 until [this]).map{ y1-> (0 until [x]).map { x1-> transform(y1,x1) } }`.
 */
inline fun <R> Int.indicesMap(x: Int, transform: (Int, Int) -> R) =
    this.indicesMap { y1 -> x.indicesMap { x1 -> transform(y1, x1) } }

/**
 * e.g.: `10 e 9`
 */
infix fun Int.e(p: Int) = this.toBigInteger().pow(p).toInt()
