fun Number.toInfinite(): Infinite {
    return when(this) {
        is Byte, is Short, is Int, is Long -> when(val number = this.toLong()) {
            0L -> Infinite.ZERO
            1L -> Infinite.ONE
            -1L -> Infinite.NEGATIVE_ONE
            else -> Infinite(number)
        }
        else -> when(val number = this.toDouble()) {
            0.0 -> Infinite.ZERO
            1.0 -> Infinite.ONE
            -1.0 -> Infinite.NEGATIVE_ONE
            Double.NaN -> Infinite.NaN
            Double.POSITIVE_INFINITY -> Infinite.POSITIVE_INFINITY
            Double.NEGATIVE_INFINITY -> Infinite.NEGATIVE_INFINITY
            else -> {
                val bits = number.toBits()
                val mantissa = bits and 0xFFFFFFFFFFFFF or 0x10000000000000
                val exponent = (bits ushr 52 and 0b11111111111) - 1023
                Infinite(TODO())
            }
        }
    }
}

/**
 * A class representing immutable limitless numbers.
 */
class Infinite private constructor(
    private val integerPart: String,
    private val mantissa: String = "0",
    private val exponent: String = "0"
): Number() {

    constructor(
        integerPart: Long,
        mantissa: Long = 0
    ): this(integerPart.toString(), mantissa.toString())

    //PROPERTIES
    val sign: Infinite
        get() = if (integerPart.first() == '-') NEGATIVE_ONE else ONE

    //METHODS
    override fun toByte(): Byte {
        TODO()
    }

    override fun toChar(): Char {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toDouble(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toFloat(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toInt(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toLong(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toShort(): Short {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString() = if (
        integerPart == NaN_REPRESENTATION ||
        integerPart == POSITIVE_INFINITY_REPRESENTATION ||
        integerPart == NEGATIVE_INFINITY_REPRESENTATION
    )
        integerPart
    else
        "${integerPart}.${mantissa}e${exponent}"

    companion object {
        private const val NaN_REPRESENTATION = "NaN"
        private const val POSITIVE_INFINITY_REPRESENTATION = "+infinity"
        private const val NEGATIVE_INFINITY_REPRESENTATION = "-infinity"

        val ZERO: Infinite by lazy { Infinite(0) }
        val ONE: Infinite by lazy { Infinite(1) }
        val NEGATIVE_ONE: Infinite by lazy { Infinite(-1) }
        val NaN: Infinite by lazy { Infinite(NaN_REPRESENTATION) }
        val POSITIVE_INFINITY: Infinite by lazy { Infinite(POSITIVE_INFINITY_REPRESENTATION) }
        val NEGATIVE_INFINITY: Infinite by lazy { Infinite(NEGATIVE_INFINITY_REPRESENTATION) }
    }
}