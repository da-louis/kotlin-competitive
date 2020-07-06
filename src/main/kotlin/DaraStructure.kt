@file:Suppress("unused")

/**
 * TODO add doc
 * TODO add test
 */
private class UnionFind(initSize: Int) {
    private val parent = IntArray(initSize) { it }
    private val size = LongArray(initSize) { 1L }

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

    fun size(x: Int): Long = size[root(x)]
}

/**
 * TODO add doc
 * TODO add test
 */
private class SegmentTree(init: LongArray, val identityElement: Long, val monad: (Long, Long) -> Long) {
    val indices: Int
    val nodes: LongArray

    init {
        var indices = 1
        while (indices < init.size) indices = indices shl 1
        val nodes = LongArray(2 * indices - 1) { identityElement }
        for (i in init.indices) nodes[i + indices - 1] = init[i]
        for (i in indices - 2 downTo 0) nodes[i] = monad(nodes[2 * i + 1], nodes[2 * i + 2])
        this.indices = indices
        this.nodes = nodes
    }

    operator fun set(x: Int, value: Long) {
        var index = x + indices - 1
        nodes[index] = value
        while (index > 0) {
            index = (index - 1) / 2
            nodes[index] = monad(nodes[2 * index + 1], nodes[2 * index + 2])
        }
    }

    operator fun get(range: IntRange) = get(range.first, range.last + 1)

    fun get(a: Int, b: Int, k: Int = 0, l: Int = 0, r: Int = indices): Long {
        if (r <= a || b <= l) return identityElement
        if (a <= l && r <= b) return nodes[k]
        return monad(
            get(a, b, 2 * k + 1, l, (l + r) / 2),
            get(a, b, 2 * k + 2, (l + r) / 2, r)
        )
    }
}
