@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class AbstractMultiSet<K>(protected open val map: MutableMap<K, Long>) {
    fun add(k: K, v: Long = 1) = map.inc(k, v)
    fun remove(k: K, v: Long = 1) = map.dec(k, v)
    fun removeAll(k: K) = map.remove(k)
    fun clear() = map.clear()
    fun contains(k: K) = map.contains(k)
    fun count(k: K) = map[k] ?: 0
    fun isEmpty() = map.isEmpty()
    fun isNotEmpty() = !isEmpty()

    protected fun <K> MutableMap<K, Long>.inc(k: K, v: Long) = merge(k, v, Long::plus)
    protected fun <K> MutableMap<K, Long>.dec(k: K, v: Long) =
        if ((this[k] ?: 0) <= v) remove(k) else merge(k, v, Long::minus)

    abstract override fun toString(): String
    abstract override fun hashCode(): Int
    abstract override fun equals(other: Any?): Boolean
}

class MultiSet<K>(override val map: MutableMap<K, Long> = mutableMapOf()) : AbstractMultiSet<K>(map) {
    override fun toString() = "MultiSet(map=$map)"
    override fun hashCode() = map.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MultiSet<*>
        return map == other.map
    }
}

fun <K> multiSetOf() = MultiSet<K>()
fun <K> multiSetOf(iterable: Iterable<K>) = multiSetOf(iterable.groupBy { it })
fun <K> multiSetOf(vararg args: K) = multiSetOf(args.toList())
fun <K> multiSetOf(map: Map<K, List<K>>) = MultiSet(map.mapValues { it.value.size.toLong() }.toMutableMap())
//fun <K> sortedMultiSetOf(map: Map<K, Long>) = MultiSet(map.toMutableMap())

class SortedMultiSet<K : Comparable<K>>(override val map: java.util.SortedMap<K, Long> = sortedMapOf()) :
    AbstractMultiSet<K>(map) {
    fun min() = if (map.isNotEmpty()) map.firstKey() else null
    fun max() = if (map.isNotEmpty()) map.lastKey() else null

    override fun hashCode() = map.hashCode()
    override fun toString() = "SortedMultiSet(map=$map)"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SortedMultiSet<*>
        return map == other.map
    }
}

fun <K : Comparable<K>> sortedMultiSetOf() = SortedMultiSet<K>()
fun <K : Comparable<K>> sortedMultiSetOf(vararg args: K) = sortedMultiSetOf(args.toList())
fun <K : Comparable<K>> sortedMultiSetOf(iterable: Iterable<K>) = sortedMultiSetOf(iterable.groupBy { it })
fun <K : Comparable<K>> sortedMultiSetOf(map: Map<K, List<K>>) =
    SortedMultiSet(map.mapValues { it.value.size.toLong() }.toSortedMap())
//fun <K : Comparable<K>> sortedMultiSetOf(map: Map<K, Long>) = SortedMultiSet(map.toSortedMap())
