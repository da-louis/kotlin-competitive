@file:Suppress("unused")

/**
 * TODO add doc
 * TODO add test
 */
private class UnionFind(size: LongArray) {
    constructor(initSize: Int) : this(LongArray(initSize) { 1L })

    private val parent: IntArray = IntArray(size.size) { it }
    private val size: LongArray = size.copyOf()

    fun root(x: Int): Int {
        if (x == parent[x]) return x

        val xr = root(parent[x])
        parent[x] = xr
        return xr
    }

    fun unite(x: Int, y: Int): Boolean {
        val xr = root(x)
        val yr = root(y)
        if (xr == yr) return false
        if (size[xr] < size[yr]) return unite(yr, xr)
        size[xr] += size[yr]
        parent[yr] = xr
        return true
    }

    fun isSame(x: Int, y: Int): Boolean = root(x) == root(y)

    fun unite(pair: Pair<Int, Int>): Boolean = unite(pair.first, pair.second)
    fun isSame(pair: Pair<Int, Int>): Boolean = isSame(pair.first, pair.second)

    fun size(x: Int): Long = size[root(x)]
}

private class AbstractUnionFind<T>(
    initArray: Array<T>,
    private val getSize: (T) -> Long,
    private val merge: (T, T) -> T
) {
    //    constructor(initSize: Int,default:T) : this(Array(initSize) {default })

    private val parent: IntArray = IntArray(initArray.size) { it }
    private val datum: Array<T> = initArray.copyOf()

    fun root(x: Int): Int {
        if (x == parent[x]) return x
        val xr = root(parent[x])
        parent[x] = xr
        return xr
    }

    fun rootData(x: Int): T = datum[root(x)]

    fun unite(x: Int, y: Int): Boolean {
        val xr = root(x)
        val yr = root(y)
        if (xr == yr) return false
        if (getSize(datum[xr]) < getSize(datum[yr])) return unite(yr, xr)
        datum[xr] = merge(datum[xr], datum[yr])
        parent[yr] = xr
        return true
    }

    fun isSame(x: Int, y: Int): Boolean = root(x) == root(y)

    fun unite(pair: Pair<Int, Int>): Boolean = unite(pair.first, pair.second)
    fun isSame(pair: Pair<Int, Int>): Boolean = isSame(pair.first, pair.second)

    fun size(x: Int): Long = getSize(datum[root(x)])
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused")
private class SegmentTree(private val n: Int, private val identity: Long, private val merge: (Long, Long) -> Long) {
    val node: LongArray = LongArray(n shl 1) { identity }

    constructor(initArray: LongArray, identity: Long, merge: (Long, Long) -> Long) :
            this(initArray.size, identity, merge) {
        for (i in 0 until n) this.node[i + n] = initArray[i]
        for (i in n - 1 downTo 1) this.node[i] = merge(this.node[i shl 1], this.node[i shl 1 or 1])
    }

    operator fun set(index: Int, x: Long) {
        var i = index + n
        node[i] = x
        while (i > 1) {
            i = i shr 1
            node[i] = merge(node[i shl 1], node[i shl 1 or 1])
        }
    }

    operator fun get(range: IntRange) = internalGet(range.first, range.last + 1)
    operator fun get(index: Int) = merge(node[index + n], identity)
    fun getRawValue(index: Int) = node[index + n]

    private fun internalGet(leftIndex: Int, rightIndex: Int): Long {
        check(leftIndex <= rightIndex)
        var result = identity
        var l = leftIndex + n
        var r = rightIndex + n
        while (l < r) {
            if (l and 1 == 1) result = merge(result, node[l++])
            if (r and 1 == 1) result = merge(result, node[--r])
            l = l shr 1; r = r shr 1
        }
        return result
    }
}

@Suppress("unused")
private class AbstractSegmentTree<T>(private val n: Int, private val identity: T, private val merge: (T, T) -> T) {
    @Suppress("UNCHECKED_CAST")
    private val node: Array<T> = Array(n shl 1) { identity as Any } as Array<T>

    constructor(initArray: Array<T>, identity: T, merge: (T, T) -> T) :
            this(initArray.size, identity, merge) {
        for (i in 0 until n) this.node[i + n] = initArray[i]
        for (i in n - 1 downTo 1) this.node[i] = merge(this.node[i shl 1], this.node[i shl 1 or 1])
    }

    operator fun set(index: Int, x: T) {
        var i = index + n
        node[i] = x
        while (i > 1) {
            i = i shr 1
            node[i] = merge(node[i shl 1], node[i shl 1 or 1])
        }
    }

    operator fun get(range: IntRange) = internalGet(range.first, range.last + 1)
    operator fun get(index: Int) = internalGet(index, index + 1)

    private fun internalGet(leftIndex: Int, rightIndex: Int): T {
        var result = identity
        var l = leftIndex + n
        var r = rightIndex + n
        while (l < r) {
            if (l and 1 == 1) result = merge(result, node[l++])
            if (r and 1 == 1) result = merge(result, node[--r])
            l = l shr 1; r = r shr 1
        }
        return result
    }
}

// TODO 普通の継承にしてモノイドとしても扱えるようにしたほうが良さそう？
enum class LongGroup(
    val identity: Long,
    val operation: (Long, Long) -> Long,
    val inverseOperation: (Long, Long) -> Long
) {
    SUM(0L, Long::plus, Long::minus),
    MULTIPLY(1L, Long::times, Long::div),
    XOR(0L, Long::xor, Long::xor);
    //    AND(a, Long::and, Long::xor), ??
    //    GCD(0L,::gcd,), ??
    //    private tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
}

/**
 * simplify cumulative.
 * require O(size(array)) to construct.
 */
class LongCumulative(array: LongArray, private val longGroup: LongGroup) {
    private val cumulative: LongArray = LongArray(array.size + 1) { longGroup.identity }

    init {
        for (i in array.indices) cumulative[i + 1] = longGroup.operation(cumulative[i], array[i])
    }

    /**
     * O(cost of inverseOperation)
     */
    operator fun get(range: IntRange): Long =
        longGroup.inverseOperation(cumulative[range.last + 1], cumulative[range.first])
}

// todo 2d の累積和も欲しいかも
