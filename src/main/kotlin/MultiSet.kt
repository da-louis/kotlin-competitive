@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class AbstractMultiSet<K>(map: Map<K, Long>) {
    protected open val map: MutableMap<K, Long> = map.toMutableMap()

    fun add(k: K, v: Long = 1) = map.inc(k, v)
    fun remove(k: K, v: Long = 1) = map.dec(k, v)
    fun removeAll(k: K) = map.remove(k)
    fun clear() = map.clear()
    fun contains(k: K) = map.contains(k)
    fun count(k: K) = map[k] ?: 0
    fun isEmpty() = map.isEmpty()
    fun isNotEmpty() = !isEmpty()
    fun distinct() = map.keys.toSet()
    fun toList() =
        map.flatMap { (k, v) -> if (v <= Int.MAX_VALUE) List(v.toInt()) { k } else error("$v is too large.") }

    protected fun <K> MutableMap<K, Long>.inc(k: K, v: Long) = merge(k, v, Long::plus)
    protected fun <K> MutableMap<K, Long>.dec(k: K, v: Long) =
        if ((this[k] ?: 0) <= v) remove(k) else merge(k, v, Long::minus)

    abstract override fun toString(): String
    abstract override fun hashCode(): Int
    abstract override fun equals(other: Any?): Boolean
}

class MultiSet<K>(map: Map<K, Long> = mapOf()) : AbstractMultiSet<K>(map) {
    //        constructor(map: Map<K, List<K>>) : this(map.mapValues { it.value.size.toLong() }.toMutableMap())
    constructor(iterable: Iterable<K>) : this(iterable.groupingBy { it }.eachCount().mapValues { it.value.toLong() })
    constructor(vararg args: K) : this(args.toList())

    override fun toString() = "MultiSet(map=$map)"
    override fun hashCode() = map.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MultiSet<*>
        return map == other.map
    }
}

@Suppress("unused")
class SortedMultiSet<K : Comparable<K>>(override val map: java.util.TreeMap<K, Long> = java.util.TreeMap()) :
    AbstractMultiSet<K>(map) {
    //    constructor(map: Map<K, List<K>>) : this(TreeMap(map.mapValues { it.value.size.toLong() }))
    constructor(map: Map<K, Long>) : this(java.util.TreeMap(map))
    constructor(iterable: Iterable<K>) : this(iterable.groupingBy { it }.eachCount().mapValues { it.value.toLong() })
    constructor(vararg args: K) : this(args.toList())

    fun min() = if (map.isNotEmpty()) map.firstKey() else null
    fun max() = if (map.isNotEmpty()) map.lastKey() else null
    fun ceiling(key: K): K? = map.ceilingKey(key)
    fun higher(key: K): K? = map.higherKey(key)
    fun floor(key: K): K? = map.floorKey(key)
    fun lower(key: K): K? = map.lowerKey(key)

    override fun hashCode() = map.hashCode()
    override fun toString() = "SortedMultiSet(map=$map)"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SortedMultiSet<*>
        return map == other.map
    }
}
