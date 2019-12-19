package geopti

fun Number.toInfinite(): Infinite {
    return when(this) {
        is Infinite -> this.copy()
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
            else -> TODO()
        }
    }
}

/**
 * A class representing immutable limitless numbers.
 */
class Infinite private constructor(
    private val integerPart: String,
    private val mantissa: String = "0",
    private val exponent: Infinite? = null
): Number() {

    constructor(
        integerPart: Long,
        mantissa: Long = 0
    ): this(integerPart.toString(), mantissa.toString())


    //PROPERTIES
    val sign: Infinite
        get() = if (integerPart.first() == '-') NEGATIVE_ONE else ONE


    //METHODS
    fun copy() = Infinite(integerPart, mantissa, exponent)

    operator fun div(number: Infinite): Infinite {
        var iPart = ""
        var m = ""
        var e: Infinite? = null
        TODO()
        return Infinite(iPart, m, e)
    }
    operator fun minus(number: Infinite): Infinite {
        var iPart = ""
        var m = ""
        var e: Infinite? = null
        TODO()
        return Infinite(iPart, m, e)
    }
    operator fun plus(number: Infinite): Infinite {
        var iPart = ""
        var m = ""
        var e: Infinite? = null
        TODO()
        return Infinite(iPart, m, e)
    }
    operator fun rem(number: Infinite): Infinite {
        var iPart = ""
        var m = ""
        var e: Infinite? = null
        TODO()
        return Infinite(iPart, m, e)
    }
    operator fun times(number: Infinite): Infinite {
        var iPart = ""
        var m = ""
        var e: Infinite? = null
        TODO()
        return Infinite(iPart, m, e)
    }
    operator fun unaryMinus() = Infinite(
        integerPart.substringAfter('-', "-$integerPart"),
        mantissa,
        exponent
    )
    operator fun unaryPlus() = copy()

    //TODO
    override fun toByte() = (integerPart.toLong()).toByte()
    override fun toChar() = (integerPart.toLong()).toChar()
    override fun toDouble() = "$integerPart.$mantissa".toDouble()
    override fun toFloat() = ("$integerPart.$mantissa".toDouble()).toFloat()
    override fun toInt() = (integerPart.toLong()).toInt()
    override fun toLong() = (integerPart.toLong()).toLong()
    override fun toShort() = (integerPart.toLong()).toShort()

    override fun toString() = if (
        integerPart == NaN_REPRESENTATION ||
        integerPart == POSITIVE_INFINITY_REPRESENTATION ||
        integerPart == NEGATIVE_INFINITY_REPRESENTATION
    )
        integerPart
    else
        "${integerPart}.${mantissa}${if (exponent == null) "" else "e$exponent"}"


    companion object {
        private const val NaN_REPRESENTATION = "NaN"
        private const val POSITIVE_INFINITY_REPRESENTATION = "+infinity"
        private const val NEGATIVE_INFINITY_REPRESENTATION = "-infinity"

        val ZERO: Infinite by lazy { Infinite(0) }
        val ONE: Infinite by lazy { Infinite(1) }
        val NEGATIVE_ONE: Infinite by lazy { Infinite(-1) }
        val NaN: Infinite by lazy { Infinite(NaN_REPRESENTATION) }
        val POSITIVE_INFINITY: Infinite by lazy {
            Infinite(
                POSITIVE_INFINITY_REPRESENTATION
            )
        }
        val NEGATIVE_INFINITY: Infinite by lazy {
            Infinite(
                NEGATIVE_INFINITY_REPRESENTATION
            )
        }
    }
}