/**
 * idk this class is useful or not :/
 * just practice for custom-setter
 */
class MutableAnswer<T : Number>(value: T, private val action: (T, T) -> T) {
    var value = value
        set(v) {
            field = action(field, v)
        }

    override fun toString() = this.value.toString()
    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MutableAnswer<*>
        return value == other.value
    }

    companion object {
        fun <T : Number> mutableAnswerOf(value: T, action: (T, T) -> T) = MutableAnswer(value, action)
    }
}
