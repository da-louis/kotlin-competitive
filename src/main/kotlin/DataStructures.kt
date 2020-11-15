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

    fun unite(x: Int, y: Int) {
        val xr = root(x)
        val yr = root(y)
        if (xr == yr) return

        if (size[xr] >= size[yr]) {
            size[xr] += size[yr]
            parent[yr] = xr
        } else {
            size[yr] += size[xr]
            parent[xr] = yr
        }
    }

    fun isSame(x: Int, y: Int): Boolean = root(x) == root(y)

    fun unite(pair: Pair<Int, Int>): Unit = unite(pair.first, pair.second)
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

    fun unite(x: Int, y: Int) {
        val xr = root(x)
        val yr = root(y)
        if (xr == yr) return

        if (getSize(datum[xr]) >= getSize(datum[yr])) {
            datum[xr] = merge(datum[xr], datum[yr])
            parent[yr] = xr
        } else {
            datum[yr] = merge(datum[yr], datum[xr])
            parent[xr] = yr
        }
    }

    fun isSame(x: Int, y: Int): Boolean = root(x) == root(y)

    fun unite(pair: Pair<Int, Int>): Unit = unite(pair.first, pair.second)
    fun isSame(pair: Pair<Int, Int>): Boolean = isSame(pair.first, pair.second)

    fun size(x: Int): Long = getSize(datum[root(x)])
}

/**
 * TODO add doc
 * TODO add test
 */
@Suppress("unused")
private class SegmentTree(private val n: Int, private val identity: Long, private val merge: (Long, Long) -> Long) {
    private val node: LongArray = LongArray(n shl 1) { identity }

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
    operator fun get(index: Int) = internalGet(index, index + 1)

    private fun internalGet(leftIndex: Int, rightIndex: Int): Long {
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
