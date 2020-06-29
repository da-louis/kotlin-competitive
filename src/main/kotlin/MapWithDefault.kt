fun <K : Any, V : Any> mapWithDefaultOf(default: (key: K) -> V): MapWithDefault<K, V> =
    MapWithDefault(default = default)

fun <K : Any, V : Any> mapWithDefaultOf(vararg pairs: Pair<K, V>, default: (key: K) -> V) =
    if (pairs.isNotEmpty()) MapWithDefault(HashMap<K, V>().apply { putAll(pairs) }, default)
    else mapWithDefaultOf(default = default)

fun <K : Any, V : Any> mutableMapWithDefaultOf(default: (key: K) -> V) =
    MutableMapWithDefault(default = default)

fun <K : Any, V : Any> mutableMapWithDefaultOf(
    vararg pairs: Pair<K, V>,
    default: (key: K) -> V
): MutableMapWithDefault<K, V> =
    if (pairs.isNotEmpty()) MutableMapWithDefault(HashMap<K, V>().apply { putAll(pairs) }, default)
    else mutableMapWithDefaultOf(default = default)

abstract class AbstractMapWithDefault<K : Any, V : Any>(
    protected open val map: Map<K, V> = emptyMap(),
    private val default: (key: K) -> V
) :
    Map<K, V> {
    override val entries get() = map.entries
    override val keys get() = map.keys
    override val size get() = map.size
    override val values get() = map.values

    override fun containsKey(key: K) = map.containsKey(key)
    override fun containsValue(value: V) = map.containsValue(value)
    override fun get(key: K): V = map.getOrElse(key) { default(key) }
    override fun isEmpty() = map.isEmpty()
    override fun toString() = "AbstractMapWithDefault(map=$map)"
    override fun hashCode() = map.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractMapWithDefault<*, *>) return false
        return map == other.map
    }
}

class MapWithDefault<K : Any, V : Any>(map: Map<K, V> = emptyMap(), default: (key: K) -> V) :
    AbstractMapWithDefault<K, V>(map, default)

class MutableMapWithDefault<K : Any, V : Any>(
    override val map: MutableMap<K, V> = mutableMapOf(),
    default: (key: K) -> V
) :
    AbstractMapWithDefault<K, V>(map, default), MutableMap<K, V> {
    override val entries get() = map.entries
    override val keys get() = map.keys
    override val values get() = map.values
    override fun clear() = map.clear()
    override fun put(key: K, value: V) = map.put(key, value)
    override fun putAll(from: Map<out K, V>) = map.putAll(from)
    override fun remove(key: K) = map.remove(key)
}
