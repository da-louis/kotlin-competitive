@file:Suppress("unused")

// in this implementation, treating MOD as const.
private const val MOD = 1_000_000_007L

private fun Long.toMint() = Mint(this)
private fun Int.toMint() = Mint(this.toLong())

/**
 * ModInt. you know, right?
 * TODO add test case
 */
@Suppress("unused")
private class Mint(value: Long = 0L) {
    constructor(other: Mint) : this(other.value)

    private val value = (value % MOD).let { if (it < 0) it + MOD else it }
    operator fun unaryPlus() = this
    operator fun unaryMinus() = Mint(MOD - this.value)
    operator fun inc() = this.plus(1)
    operator fun dec() = this.minus(1)
    operator fun plus(other: Long) = Mint(this.value + other)
    operator fun plus(other: Mint) = this.plus(other.value)
    operator fun minus(other: Long) = Mint(this.value - other)
    operator fun minus(other: Mint) = this.plus(-other)
    operator fun times(other: Long) = Mint(this.value * Mint(other).value)
    operator fun times(other: Mint) = this.times(other.value)
    operator fun div(other: Long) = this.times(modPow(other, MOD - 2))
    operator fun div(other: Mint) = this.div(other.value)
    fun mod(mod: Long) = Mint(this.value % mod)
    fun pow(other: Long) = Mint(modPow(this.value, other))
    fun toLong() = this.value
    override fun toString() = this.value.toString()
    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Mint
        return value == other.value
    }

    companion object {
        val ZERO = Mint(0)
        val ONE = Mint(1)
        val TWO = Mint(2)
        val TEN = Mint(10)

        private fun modPow(n: Long, p: Long): Long {
            var x = n % MOD
            var y = p
            var result = 1L
            while (y > 0) {
                if (y % 2 == 1L) result = (result * x) % MOD
                y = y shr 1
                x = (x * x) % MOD
            }
            return result
        }
    }
}
