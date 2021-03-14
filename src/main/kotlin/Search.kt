import java.util.*

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
 */
@Suppress("unused", "SameParameterValue")
inline fun <reified T> searchAllPatterns(patterns: Array<T>, length: Int, action: (Array<T>) -> Unit) {
    val patternSize = patterns.size
    check(length < 40) { "length:$length is too large! (must be TLE)" }
    val pow = (1L..length).fold(1L) { acc: Long, _: Long -> Math.multiplyExact(acc, patternSize.toLong()) }
    check(pow <= Int.MAX_VALUE) { "pow:$pow is too large! (must be TLE)" }
    val allPatterns = pow.toInt()
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

/**
 * TODO add doc
 * TODO sequenceにしたら早くなる？？
 * TODO 辞書順になってない
 */
fun <T> permutations(source: List<T>): List<List<T>> {
    val n = source.size
    if (n == 1) return listOf(source)
    val result = mutableListOf<List<T>>()
    val copied = source.toMutableList()

    fun MutableList<T>.swap(x: Int, y: Int) {
        val temp = this[x]
        this[x] = this[y]
        this[y] = temp
    }

    fun genPerm(currSource: MutableList<T>, currentIndex: Int) {
        if (currentIndex == n) result.add(currSource.toList())
        for (i in currentIndex until n) {
            currSource.swap(currentIndex, i)
            genPerm(currSource, currentIndex + 1)
            currSource.swap(currentIndex, i)
        }
    }

    genPerm(copied, 0)

    return result
}

/**
 * generate rotated 4 graph clockwise. (0,3,6,9)
 * TODO add test
 *
 * @param graph grid graph
 * @return [rotated graph, height, width]
 */
private inline fun <reified T> generateRotatedGraph(graph: Array<Array<T>>): Array<Triple<Array<Array<T>>, Int, Int>> {
    val height = graph.size
    val width = graph.first().size
    val defaultValue = graph.first().first()

    val graphs = Array(4) { Triple(emptyArray<Array<T>>(), 0, 0) }
    graphs[0] = Triple(Array(height) { Array(width) { defaultValue } }, height, width)
    graphs[1] = Triple(Array(width) { Array(height) { defaultValue } }, width, height)
    graphs[2] = Triple(Array(height) { Array(width) { defaultValue } }, height, width)
    graphs[3] = Triple(Array(width) { Array(height) { defaultValue } }, width, height)

    for (y in 0 until height) for (x in 0 until width) {
        graph[y][x].let {
            graphs[0].first[y][x] = it
            graphs[1].first[x][width - y - 1] = it
            graphs[2].first[height - y - 1][width - x - 1] = it
            graphs[3].first[height - x - 1][y] = it
        }
    }

    return graphs
}

/**
 * generate rotated grid-graph clockwise. (90 degree)
 * TODO add test
 *
 * @param graph grid-graph
 * @return rotated grid-graph
 */
private inline fun <reified T> rotateRight(graph: Array<Array<T>>): Array<Array<T>> {
    val h = graph.size
    val w = graph.first().size
    val defaultValue = graph.first().first()
    val rotated = Array(w) { Array(h) { defaultValue } }
    for (y in 0 until h) for (x in 0 until w) rotated[x][h - y - 1] = graph[y][x]
    return rotated
}

private fun rotateRight(graph: Array<IntArray>): Array<IntArray> {
    val h = graph.size
    val w = graph.first().size
    val rotated = Array(w) { IntArray(h) }
    for (y in 0 until h) for (x in 0 until w) rotated[x][h - y - 1] = graph[y][x]
    return rotated
}

private fun rotateRight(graph: Array<BooleanArray>): Array<BooleanArray> {
    val h = graph.size
    val w = graph.first().size
    val rotated = Array(w) { BooleanArray(h) }
    for (y in 0 until h) for (x in 0 until w) rotated[x][h - y - 1] = graph[y][x]
    return rotated
}

@Suppress("SameParameterValue")
private fun <T> bitWholeSearch(length: Int, e: T, action: (Int, T) -> T): List<T> {
    val result = mutableListOf<T>()
    fun dfs(i: Int, accumulative: T) {
        if (i == length) result.add(accumulative)
        else {
            dfs(i + 1, accumulative)
            dfs(i + 1, action(i, accumulative))
        }
    }
    dfs(0, e)
    return result
}

fun actionWithNext(y: Int, x: Int, h: Int, w: Int, action: (Int, Int) -> Unit) {
    val dy = intArrayOf(1, 0, -1, 0)
    val dx = intArrayOf(0, 1, 0, -1)

    repeat(dy.size) {
        val ny = y + dy[it]
        val nx = x + dx[it]
        if (ny in 0 until h && nx in 0 until w) action(ny, nx)

    }
}

fun addAroundWall(grid: Array<CharArray>, wall: Char): Triple<Int, Int, Array<CharArray>> {
    val result = Array(grid.size + 2) { CharArray(grid.first().size + 2) { wall } }
    for (i in grid.indices) for (j in grid.first().indices) {
        result[i + 1][j + 1] = grid[i][j]
    }
    return Triple(result.size, result.first().size, result)
}

private fun addAroundWall(grid: Array<IntArray>, wall: Int): Triple<Int, Int, Array<IntArray>> {
    val result = Array(grid.size + 2) { IntArray(grid.first().size + 2) { wall } }
    for (i in grid.indices) for (j in grid.first().indices) {
        result[i + 1][j + 1] = grid[i][j]
    }
    return Triple(result.size, result.first().size, result)
}


fun isBipartite() {
    TODO()
    // https://atcoder.jp/contests/agc039/submissions/16287411
}

data class Point(val y: Int, val x: Int)

// TODO 「次を取得」を抽象化すればgrid以外にも汎用化できそう
fun gridDfs(grid: Array<IntArray>, start: Point, height: Int, width: Int, action: () -> Unit) {
    val visited = Array(height) { BooleanArray(width) }

    fun innerDfs() {
        TODO()
    }
}

fun gridBfs() {
    TODO()
}

//private data class Edge(val first: Int, val second: Int, val weight: Int)
//private data class Vertex(val index: Int, val neighbor: MutableSet<Int> = mutableSetOf())
//
//private fun dijkstra(graph: Array<Vertex>, start: Int): IntArray {
//    val distances = IntArray(graph.size) { inf }.apply { set(start, 0) }
//    val queue = PriorityQueue<Vertex> { o1, o2 -> o1.cost.compareTo(o2.cost) }
//        .apply { add(Vertex(start, 0)) }
//
//    while (queue.isNotEmpty()) {
//        val (curr, dist) = queue.remove()
//        if (dist > distances[curr]) continue
//
//        for ((_, next, edgeDist) in graph[curr].edges) {
//            val totalDist = dist + edgeDist
//
//            if (totalDist < distances[next]) {
//                distances[next] = totalDist
//                queue.add(Vertex(next, totalDist))
//            }
//        }
//    }
//    return distances
//}

private data class Edge(val a: Int, val b: Int, val weight: Long = 1)
private data class Vertex(val index: Int, val cost: Long = 0, val edges: MutableSet<Edge> = mutableSetOf())

private const val INF = Long.MAX_VALUE / 10

/**
 * note: require graph construct cost.
 */
private fun dijkstra(edges: Array<Edge>, n: Int, start: Int): LongArray {
    val graph = Array(n) { Vertex(it) }
    edges.forEach { (s, t, d) ->
        graph[s].edges.add(Edge(s, t, d))
        //        graph[t].edges.add(Edge(t, s, d))
    }
    return dijkstra(graph, start)
}

private fun dijkstra(graph: Array<Vertex>, start: Int): LongArray {
    val distances = LongArray(graph.size) { INF }.apply { this[start] = 0 }
    val queue = PriorityQueue<Vertex> { o1, o2 -> o1.cost.compareTo(o2.cost) }
        .apply { add(Vertex(start, 0)) }

    while (queue.isNotEmpty()) {
        val (curr, dist) = queue.remove()
        if (dist > distances[curr]) continue

        for ((_, next, edgeDist) in graph[curr].edges) {
            val totalDist = dist + edgeDist

            if (totalDist < distances[next]) {
                distances[next] = totalDist
                queue.add(Vertex(next, totalDist))
            }
        }
    }
    return distances
}

private fun kruskal(n: Int, edges: Array<Edge>): Pair<List<Edge>, Long> {
    class UnionFind(size: LongArray) {
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

    val uf = UnionFind(n)
    val mst = mutableListOf<Edge>()
    var weightSum = 0L

    for (edge in edges.sortedBy { it.weight }) {
        if (uf.unite(edge.a, edge.b)) {
            mst.add(edge)
            weightSum += edge.weight
        }
    }
    return mst to weightSum
}

private fun bellmanFord(v: Int, edges: Array<Edge>, start: Int): LongArray? {
    val distances = LongArray(v) { INF }.apply { this[start] = 0 }
    var count = 0
    var updated = true
    while (updated) {
        updated = false
        count++
        for ((s, t, d) in edges) {
            if (distances[s] == INF) continue
            val nextDist = distances[s] + d
            if (nextDist >= distances[t]) continue
            if (count == v) return null
            updated = true
            distances[t] = nextDist
        }
    }
    return distances
}

@OptIn(ExperimentalStdlibApi::class)
private fun filterEdgesReachableToGoal(v: Int, edges: Array<Edge>, goal: Int, directGraph: Boolean): Array<Edge> {
    val revGraph = Array(v) { mutableListOf<Int>() }
    for ((a, b) in edges) {
        revGraph[b].add(a)
        if (!directGraph) revGraph[a].add(b)
    }
    val visited = BooleanArray(v).apply { this[goal] = true }
    val queue = kotlin.collections.ArrayDeque(listOf(goal))
    while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()
        for (next in revGraph[curr]) if (!visited[next]) {
            visited[next] = true
            queue.addLast(next)
        }
    }
    return edges.filter { visited[it.a] && visited[it.b] }.toTypedArray()
}
