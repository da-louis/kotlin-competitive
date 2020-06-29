@file:Suppress("unused")

// in this implementation, treating MOD as const.
private const val MOD = 1_000_000_007L

/**
 * TODO add doc
 * TODO add test
 */
private class BiCoef(max: Int) {
    private val fact = LongArray(max + 1)
    private val fInv = LongArray(max + 1)
    private val inv = LongArray(max + 1)

    init {
        fact[0] = 1
        fact[1] = 1
        fInv[0] = 1
        fInv[1] = 1
        inv[1] = 1
        for (i in 2..max) {
            fact[i] = fact[i - 1] * i % MOD
            inv[i] = MOD - inv[(MOD % i).toInt()] * (MOD / i) % MOD
            fInv[i] = fInv[i - 1] * inv[i] % MOD
        }
    }

    fun fact(n: Int): Long = if (n < 0) 0 else fact[n]
    fun inv(n: Int): Long = if (n < 0) 0 else inv[n]
    fun fInv(n: Int): Long = if (n < 0) 0 else fInv[n]
    fun comb(n: Int, k: Int): Long = nCr(n, k)
    fun nCr(n: Int, k: Int): Long = if (n < k || n < 0 || k < 0) 0 else fact[n] * (fInv[k] * fInv[n - k] % MOD) % MOD
    fun perm(n: Int, k: Int): Long = nPr(n, k)
    fun nPr(n: Int, k: Int): Long = if (n < k || n < 0 || k < 0) 0 else fact[n] * fInv[n - k] % MOD % MOD
}
