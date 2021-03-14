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
private class Mint(value: Long) {
    constructor(other: Mint) : this(other.value)

    val value = value.safeMod()
    operator fun unaryPlus() = this
    operator fun unaryMinus() = Mint(MOD - this.value)
    operator fun inc() = this.plus(1)
    operator fun dec() = this.minus(1)
    operator fun plus(other: Long) = Mint(this.value + other % MOD)
    operator fun plus(other: Mint) = this.plus(other.value)
    operator fun minus(other: Long) = Mint(this.value - other % MOD)
    operator fun minus(other: Mint) = this.minus(other.value)
    operator fun times(other: Long) = Mint(this.value * other % MOD)
    operator fun times(other: Mint) = this.times(other.value)
    operator fun div(other: Long) = this.times(other.invert()) // todo verify!!!!
    operator fun div(other: Mint) = this.div(other.value)
    fun invert() = Mint(this.value.invert())
    fun mod(mod: Long) = Mint(this.value % mod)
    fun pow(other: Long) = Mint(powMod(this.value, other))
    fun pow(other: Mint) = this.pow(other.value)
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

        private fun Long.safeMod() = (this % MOD).let { if (it < 0) it + MOD else it }
        private val modIsPrime = MOD.toBigInteger().isProbablePrime(100)

        private fun Long.invert(): Long {
            return if (modIsPrime) powMod(this, MOD - 2L)
            else {
                val eg = invGcd(this)
                assert(eg.first == 1L)
                return eg.second
            }
        }

        private fun powMod(n: Long, p: Long): Long {
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

        private fun invGcd(a: Long): Pair<Long, Long> {
            val am = a.safeMod()
            if (a == 0L) return MOD to 0L
            var s = MOD
            var t = am
            var m0 = 0L
            var m1 = 1L
            while (t != 0L) {
                val u = s / t
                s -= t * u
                m0 -= m1 * u

                var temp = s
                s = t
                t = temp
                temp = m0
                m0 = m1
                m1 = temp
            }
            if (m0 < 0) m0 += MOD / s
            return s to m0
        }
    }
}

@Suppress("unused")
private class StaticMint(value: Long) {
    val value = value.safeMod(mod)
    operator fun unaryPlus() = this
    operator fun unaryMinus() = StaticMint(mod - this.value)
    operator fun inc() = this.plus(1)
    operator fun dec() = this.minus(1)
    operator fun plus(other: Long) = StaticMint(this.value + other % mod)
    operator fun plus(other: StaticMint) = this.plus(other.value)
    operator fun minus(other: Long) = StaticMint(this.value - other % mod)
    operator fun minus(other: StaticMint) = this.minus(other.value)
    operator fun times(other: Long) = StaticMint(this.value * other % mod)
    operator fun times(other: StaticMint) = this.times(other.value)
    operator fun div(other: Long) = this.times(other.invert())
    operator fun div(other: StaticMint) = this.div(other.value)
    fun invert() = StaticMint(this.value.invert())

    fun mod(mod: Long) = StaticMint(this.value % mod)
    fun pow(other: Long) = StaticMint(modPow(this.value, other))
    fun pow(other: Mint) = this.pow(other.value)
    fun toLong() = this.value
    override fun toString() = this.value.toString()
    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as StaticMint
        return value == other.value
    }

    companion object {
        var mod = 1_000_000_007L
            set(value) {
                modIsPrime = value.toBigInteger().isProbablePrime(100)
                field = value
            }
        private var modIsPrime = true

        val ZERO get() = StaticMint(0)
        val ONE get() = StaticMint(1)
        val TWO get() = StaticMint(2)
        val TEN get() = StaticMint(10)

        private fun Long.safeMod(m: Long) = (this % m).let { if (it < 0) it + m else it }
        private fun Long.invert(): Long {
            return if (modIsPrime) modPow(this, mod - 2L)
            else {
                val eg = invGcd(this, mod)
                assert(eg.first == 1L)
                return eg.second
            }
        }

        private fun modPow(n: Long, p: Long): Long {
            var x = n % mod
            var y = p
            var result = 1L
            while (y > 0) {
                if (y % 2 == 1L) result = (result * x) % mod
                y = y shr 1
                x = (x * x) % mod
            }
            return result
        }

        private fun invGcd(a: Long, b: Long): Pair<Long, Long> {
            val am = a.safeMod(b)
            if (a == 0L) return b to 0L
            var s = b
            var t = am
            var m0 = 0L
            var m1 = 1L
            while (t != 0L) {
                val u = s / t
                s -= t * u
                m0 -= m1 * u

                var temp = s
                s = t
                t = temp
                temp = m0
                m0 = m1
                m1 = temp
            }
            if (m0 < 0) m0 += b / s
            return s to m0
        }
    }
}

@Suppress("unused")
private class DynamicMint(value: Long, val mod: Long) {
    private var modIsPrime = mod.toBigInteger().isProbablePrime(100)
    val value = value.safeMod(mod)
    operator fun unaryPlus() = this
    operator fun unaryMinus() = DynamicMint(mod - this.value, mod)
    operator fun inc() = this.plus(1)
    operator fun dec() = this.minus(1)
    operator fun plus(other: Long) = DynamicMint(this.value + other % mod, mod)
    operator fun plus(other: DynamicMint) = this.plus(other.value)
    operator fun minus(other: Long) = DynamicMint(this.value - other % mod, mod)
    operator fun minus(other: DynamicMint) = this.minus(other.value)
    operator fun times(other: Long) = DynamicMint(this.value * other % mod, mod)
    operator fun times(other: DynamicMint) = this.times(other.value)
    operator fun div(other: Long) = this.times(other.invert(mod))
    operator fun div(other: DynamicMint) = this.div(other.value)
    fun invert() = DynamicMint(this.value.invert(mod), mod)
    fun mod(mod: Long) = DynamicMint(this.value % mod, mod)
    fun pow(other: Long) = DynamicMint(modPow(this.value, other, mod), mod)
    fun pow(other: Mint) = this.pow(other.value)
    override fun toString() = this.value.toString()
    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DynamicMint
        return value == other.value
    }

    private fun Long.invert(mod: Long): Long {
        return if (modIsPrime) modPow(this, mod - 2L, mod)
        else {
            val eg = invGcd(this, mod)
            assert(eg.first == 1L)
            return eg.second
        }
    }

    companion object {
        private fun Long.safeMod(m: Long) = (this % m).let { if (it < 0) it + m else it }
        private fun modPow(n: Long, p: Long, m: Long): Long {
            var x = n % m
            var y = p
            var result = 1L
            while (y > 0) {
                if (y % 2 == 1L) result = (result * x) % m
                y = y shr 1
                x = (x * x) % m
            }
            return result
        }

        private fun invGcd(a: Long, b: Long): Pair<Long, Long> {
            val am = a.safeMod(b)
            if (a == 0L) return b to 0L
            var s = b
            var t = am
            var m0 = 0L
            var m1 = 1L
            while (t != 0L) {
                val u = s / t
                s -= t * u
                m0 -= m1 * u

                var temp = s
                s = t
                t = temp
                temp = m0
                m0 = m1
                m1 = temp
            }
            if (m0 < 0) m0 += b / s
            return s to m0
        }
    }
}

private infix fun Long.powMod(p: Long): Long {
    var x = this % MOD
    var y = p
    var result = 1L
    while (y > 0) {
        if (y % 2 == 1L) result = (result * x) % MOD
        y = y shr 1
        x = (x * x) % MOD
    }
    return result
}

private fun Long.powMod(p: Long, m: Int): Long {
    var x = this % m
    var y = p
    var result = 1L
    while (y > 0) {
        if (y % 2 == 1L) result = (result * x) % m
        y = y shr 1
        x = (x * x) % m
    }
    return result
}

// todo verify
private class ModLongArray {
    private val values: LongArray

    constructor(values: LongArray) {
        this.values = LongArray(values.size) { values[it].safeMod() }
    }

    constructor(size: Int) {
        this.values = LongArray(size)
    }

    operator fun get(i: Int): Long = values[i]
    operator fun set(i: Int, value: Long): Unit = run { this.values[i] = value.safeMod() }
    operator fun set(i: Int, value: Int): Unit = run { this[i] = value.toLong() }
    operator fun iterator(): LongIterator = values.iterator()
    fun indices(): IntRange = values.indices
    val size: Int get() = this.values.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ModLongArray) return false
        if (!values.contentEquals(other.values)) return false
        if (size != other.size) return false
        return true
    }

    override fun hashCode(): Int {
        var result = values.contentHashCode()
        result = 31 * result + size
        return result
    }

    override fun toString(): String = "ModArray(values=${values.contentToString()})"

    private fun Long.safeMod() = (this % MOD).let { if (it < 0) it + MOD else it }
    private val modIsPrime = MOD.toBigInteger().isProbablePrime(100)

    private fun Long.invert(): Long {
        return if (modIsPrime) powMod(this, MOD - 2L)
        else {
            val eg = invGcd(this)
            assert(eg.first == 1L)
            return eg.second
        }
    }

    private fun powMod(n: Long, p: Long): Long {
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

    private fun invGcd(a: Long): Pair<Long, Long> {
        val am = a.safeMod()
        if (a == 0L) return MOD.toLong() to 0L
        var s = MOD.toLong()
        var t = am
        var m0 = 0L
        var m1 = 1L
        while (t != 0L) {
            val u = s / t
            s -= t * u
            m0 -= m1 * u

            var temp = s
            s = t
            t = temp
            temp = m0
            m0 = m1
            m1 = temp
        }
        if (m0 < 0) m0 += MOD / s
        return s to m0
    }
}
