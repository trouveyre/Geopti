package geopti

import kotlin.math.*

/**
 * The superclass of all Vectors.
 * @see Vector2D
 * @see Vector3D
 * @see Quaternion
 */
interface Vector<V: Vector<V>>: Vectorial<V> {

    //PROPERTIES
    /**
     * The norm of this Vector.
     */
    var norm: Double
    /**
     * The norm of this Vector squared.
     */
    var norm2: Double
    /**
     * Checks if all the components are equal to 0.
     */
    val isNull: Boolean


    //METHODS
    /**
     * Makes a copy of this vector.
     */
    fun copy(): V
    /**
     * Computes the scalar product of this vector and the one given.
     */
    infix fun dot(other: V): Double
    operator fun minus(other: V): V
    operator fun plus(other: V): V
    operator fun div(number: Double): V
    operator fun times(number: Double): V
    operator fun unaryMinus(): V
    operator fun unaryPlus(): V
    /**
     * Returns the sum of all the components.
     */
    fun sum(): Double

    override fun toString(): String
    /**
     * Converts this Vector to a Vector2D.
     * @see Vector2D
     */
    fun toVector2D(): Vector2D
    /**
     * Converts this Vector to a Vector3D.
     * @see Vector3D
     */
    fun toVector3D(): Vector3D
    /**
     * Converts this Vector to a Quaternion.
     * @see Quaternion
     */
    fun toQuaternion(): Quaternion
}



/**
 * Represents 2-dimensions mutable vectors.
 */
class Vector2D(
    /**
     * The first dimension component of this vector.
     */
    var x: Double,
    /**
     * The second dimension component of this vector.
     */
    var y: Double
): Vector<Vector2D> {


    //PROPERTIES
    override val descriptor: Vector2D
        get() = this

    override var norm: Double
        get() = hypot(x, y)
        set(value) {
            val norm = norm
            if (norm != 0.0) {
                x *= value / norm
                y *= value / norm
            }
        }
    override var norm2: Double
        get() = x * x + y * y
        set(value) { norm = sqrt(value) }
    var radius: Double
        get() = norm
        set(value) { norm = value }
    var azimuth: Double
        get() = atan2(y, x)
        set(value) {
            x = radius * cos(value)
            y = radius * sin(value)
        }
    override val isNull: Boolean
        get() = x == 0.0 && y == 0.0


    //METHODS
    operator fun component1() = x
    operator fun component2() = y

    override fun copy() = Vector2D(x, y)

    override infix fun dot(other: Vector2D) = x * other.x + y * other.y
    override operator fun minus(other: Vector2D) = Vector2D(
            x - other.x,
            y - other.y
        )
    override operator fun plus(other: Vector2D) = Vector2D(
            x + other.x,
            y + other.y
        )
    override operator fun unaryMinus() = Vector2D(-x, -y)
    override operator fun unaryPlus() = Vector2D(x, y)

    override operator fun div(number: Double) = Vector2D(
        x / number,
        y / number
    )
    override operator fun times(number: Double) = Vector2D(
        x * number,
        y * number
    )

    override fun sum() = x + y

    override fun toString() = "($x, $y)"

    override fun toVector2D() = copy()
    override fun toVector3D() = Vector3D(x, y, 0.0)
    override fun toQuaternion() = Quaternion(0.0, toVector3D())
}

/**
 * Represents 3-dimensions mutable vectors.
 */
class Vector3D(
    /**
     * The first dimension component of this vector.
     */
    var x: Double,
    /**
     * The second dimension component of this vector.
     */
    var y: Double,
    /**
     * The third dimension component of this vector.
     */
    var z: Double
): Vector<Vector3D> {

    //PROPERTIES
    override val descriptor: Vector3D
        get() = this

    /**
     * The norm of this vector.
     */
    override var norm: Double
        get() = hypot(x, hypot(y, z))
        set(value) {
            val norm = norm
            if (norm != 0.0) {
                x *= value / norm
                y *= value / norm
                z *= value / norm
            }
        }
    override var norm2: Double
        get() = x * x + y * y + z * z
        set(value) { norm = sqrt(value) }
    override val isNull: Boolean
        get() = x == 0.0 && y == 0.0 && z == 0.0


    //METHODS
    operator fun component1() = x
    operator fun component2() = y
    operator fun component3() = z

    override fun copy() = Vector3D(x, y, z)

    /**
     * Computes the vector product of this vector and the given vector.
     */
    infix fun cross(vector: Vector3D) = Vector3D(
        y * vector.z - z * vector.y,
        z * vector.x - x * vector.z,
        x * vector.y - y * vector.x
    )
    override infix fun dot(other: Vector3D) = x * other.x + y * other.y + z * other.z
    override operator fun minus(other: Vector3D) = Vector3D(
        x - other.x,
        y - other.y,
        z - other.z
    )
    override operator fun plus(other: Vector3D) = Vector3D(
        x + other.x,
        y + other.y,
        z + other.z
    )
    override operator fun unaryMinus() = Vector3D(-x, -y, -z)
    override operator fun unaryPlus() = Vector3D(x, y, z)

    override operator fun div(number: Double) = Vector3D(
        x / number,
        y / number,
        z / number
    )
    override operator fun times(number: Double) = Vector3D(
        x * number,
        y * number,
        z * number
    )

    override fun sum() = x + y + z

    override fun toString() = "($x, $y, $z)"

    override fun toVector2D() = Vector2D(x, y)
    override fun toVector3D() = copy()
    override fun toQuaternion() = Quaternion(0.0, toVector3D())
}



/**
 * Represents quaternions.
 */
class Quaternion(
    var realPart: Double,
    var imaginaryPart: Vector3D
): Vector<Quaternion> {

    //PROPERTIES
    override val descriptor: Quaternion
        get() = this

    var w: Double
        get() = realPart
        set(value) { realPart = value }
    var x: Double
        get() = imaginaryPart.x
        set(value) { imaginaryPart.x = value }
    var y: Double
        get() = imaginaryPart.y
        set(value) { imaginaryPart.y = value }
    var z: Double
        get() = imaginaryPart.z
        set(value) { imaginaryPart.z = value }
    /**
     * The norm of this vector.
     */
    override var norm: Double
        get() = hypot(x, hypot(y, hypot(z, w)))
        set(value) {
            val norm = norm
            if (norm != 0.0) {
                x *= value / norm
                y *= value / norm
                z *= value / norm
                w *= value / norm
            }
        }
    override var norm2: Double
        get() = w * w + x * x + y * y + z * z
        set(value) { norm = sqrt(value) }
    override val isNull: Boolean
        get() = realPart == 0.0 && imaginaryPart.isNull


    //METHODS
    operator fun component1() = realPart
    operator fun component2() = imaginaryPart

    override fun copy() = Quaternion(realPart, imaginaryPart)

    /**
     * Returns the conjugate of this as Quaternion.
     * @see Quaternion
     */
    fun conjugate() = Quaternion(realPart, -imaginaryPart)
    /**
     * Returns the quaternion q which match: this * q = 1.0.
     */
    fun inv() = conjugate() / (w * w + x * x + y * y + z * z)

    override infix fun dot(other: Quaternion) = x * other.x + y * other.y + z * other.z + w * other.w
    override operator fun minus(other: Quaternion) = Quaternion(
        realPart - other.realPart,
        imaginaryPart - other.imaginaryPart
    )
    override operator fun plus(other: Quaternion) = Quaternion(
        realPart - other.realPart,
        imaginaryPart - other.imaginaryPart
    )
    override operator fun unaryMinus() = Quaternion(-realPart, -imaginaryPart)
    override operator fun unaryPlus() = Quaternion(realPart, imaginaryPart)
    operator fun times(other: Quaternion) = Quaternion(
        realPart * other.realPart - (imaginaryPart dot other.imaginaryPart),
        w * other.imaginaryPart + other.w * imaginaryPart + (imaginaryPart cross other.imaginaryPart)
    )

    override operator fun div(number: Double) = Quaternion(
        realPart / number,
        imaginaryPart / number
    )
    override operator fun times(number: Double) = Quaternion(
        realPart / number,
        imaginaryPart / number
    )

    override fun sum() = x + y + z + w

    override fun toString() = "($x, $y, $z, $w)"

    override fun toVector2D() = Vector2D(x, y)
    override fun toVector3D() = Vector3D(x, y, z)
    override fun toQuaternion() = copy()
}



/**
 * Fast way to make a Vector.
 * @param x The first dimension component.
 * @param y The second dimension component.
 * @see Vector
 * @see Vector2D
 */
fun v(x: Double, y: Double) = Vector2D(x, y)

/**
 * Fast way to make a Vector.
 * @param x The first dimension component.
 * @param y The second dimension component.
 * @param z The third dimension component.
 * @see Vector
 * @see Vector3D
 */
fun v(x: Double, y: Double, z: Double) = Vector3D(x, y, z)

/**
 * Returns the projection of this Vector onto 'vector' or null if 'vector' is the vector null.
 */
operator fun <V: Vector<V>> V.get(on: V): V? {
    return if (on.isNull)
        null
    else
        on.copy().apply { norm = this dot (on / on.norm) }
}
/**
 * Returns the projection of this Vector onto the plane of the Vectors given or null if the plane is not valid.
 */
operator fun <V: Vector<V>> V.get(plane: Pair<V, V>): V? {
    return if (plane.first dot plane.second == 0.0) {
        val onJ = this[plane.second]
        if (onJ === null)
            null
        else
            this[plane.first]?.plus(onJ)
    }
    else null
}

/**
 * Checks if the two Vectors are collinear.
 * Two Vectors v1 and v2 are considered collinear if |v1 . v2| = |v1| * |v2|.
 */
infix fun <V: Vector<V>> V.isCollinearWith(other: V): Boolean = abs(this dot other) == norm * other.norm

/**
 * Sets this Vector's norm to 'number' then returns it.
 */
infix fun <V: Vector<V>> V.normedTo(number: Double) = this.apply { norm = number }

operator fun <V: Vector<V>> Number.times(vector: V): V = vector * this.toDouble()