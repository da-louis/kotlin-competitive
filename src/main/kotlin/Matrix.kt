// todo 2x2 しか対応してない
private data class Matrix(
    val a: Array<IntArray> = arrayOf(intArrayOf(1, 0), intArrayOf(0, 1)),
    val b: LongArray = longArrayOf(0, 0)
) {
    operator fun times(x: Matrix): Matrix {
        val res = Matrix(x.a * a, Matrix(x.a) * b)
        for (i in 0..1) res.b[i] += x.b[i]
        return res
    }

    private operator fun Array<IntArray>.times(x: Array<IntArray>): Array<IntArray> {
        val res = arrayOf(intArrayOf(0, 0), intArrayOf(0, 0))
        for (i in 0..1) for (j in 0..1) for (k in 0..1) {
            res[i][j] += this[i][k] * x[k][j]
        }
        return res
    }

    operator fun times(x: LongArray): LongArray = b.copyOf()
        .apply { for (i in 0..1) for (j in 0..1) this[i] += a[i][j] * x[j] }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix) return false
        if (!a.contentDeepEquals(other.a)) return false
        if (!b.contentEquals(other.b)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = a.contentDeepHashCode()
        result = 31 * result + b.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "Matrix(a=${a.contentToString()}, b=${b.contentToString()})"
    }
}
