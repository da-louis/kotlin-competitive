data class Vertex(val x: Long, val y: Long)
data class Edge(val vertex1: Vertex, val vertex2: Vertex)

fun Edge.isIntersectWith(other: Edge) = isIntersected(this.vertex1, this.vertex2, other.vertex1, other.vertex2)

fun isIntersected(a: Vertex, b: Vertex, c: Vertex, d: Vertex): Boolean {
    val (ax, ay) = a
    val (bx, by) = b
    val (cx, cy) = c
    val (dx, dy) = d
    val s1 = (ax - bx) * (cy - ay) - (ay - by) * (cx - ax)
    val t1 = (ax - bx) * (dy - ay) - (ay - by) * (dx - ax)
    val s2 = (cx - dx) * (ay - cy) - (cy - dy) * (ax - cx)
    val t2 = (cx - dx) * (by - cy) - (cy - dy) * (bx - cx)
    return s1 * t1 < 0 && s2 * t2 < 0
}
