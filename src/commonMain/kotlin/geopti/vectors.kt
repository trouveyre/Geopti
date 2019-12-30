package geopti

import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

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
 * Fast way to make a Vector.
 * @param x The first dimension component.
 * @param y The second dimension component.
 * @param z The third dimension component.
 * @param w The fourth dimension component. As a Quaternion, this is its real part.
 * @see Vector
 * @see Vector4D
 * @see Quaternion
 */
fun v(x: Double, y: Double, z: Double, w: Double) = Vector4D(x, y, z, w)

/**
 * Represents immutable vectors with a length D.
 * The implementations of this are mutable.
 * @see Vector2D
 * @see Vector3D
 * @see Vector4D
 */
interface Vector<D: Sized>: Comparable<Vector<D>> {

    //PROPERTIES
    /**
     * The first dimension component of this vector.
     */
    val x: Double
    /**
     * The second dimension component of this vector.
     */
    val y: Double
    /**
     * The third dimension component of this vector.
     */
    val z: Double
    /**
     * The fourth dimension component of this vector.
     * As a Quaternion, this is its real part.
     */
    val w: Double
    /**
     * The norm of this vector.
     */
    val norm: Double

    //METHODS
    operator fun component1(): Double = x
    operator fun component2(): Double = y
    operator fun component3(): Double = z
    operator fun component4(): Double = w

    /**
     * Makes a copy of this vector.
     */
    fun copy(): Vector<D>

    /**
     * Computes the scalar product of this vector and the given vector.
     */
    infix fun dot(vector: Vector<D>): Double
    /**
     * Returns the projection of this Vector onto 'vector' or null if vector.norm == 0.0
     */
    operator fun get(vector: Vector<D>): Vector<D>?
    operator fun minus(vector: Vector<D>): Vector<D>
    operator fun plus(vector: Vector<D>): Vector<D>
    operator fun times(vector: Vector<D>): Vector<D>
    operator fun unaryMinus(): Vector<D>
    operator fun unaryPlus(): Vector<D>
    operator fun div(number: Double): Vector<D>
    operator fun times(number: Double): Vector<D>
    infix fun normedTo(number: Double): Vector<D>
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
     * Converts this Vector to a Vector4D.
     * @see Vector4D
     */
    fun toVector4D(): Vector4D
}

/**
 * Represents 2-dimensions mutable vectors.
 */
class Vector2D(
    override var x: Double,
    override var y: Double
): Vector<TwoDimensions> {

    //PROPERTIES
    override val z = 0.0
    override val w = 0.0
    override var norm: Double
        get() = hypot(x, y)
        set(value) {
            val norm = norm
            if (norm != 0.0) {
                x *= value / norm
                y *= value / norm
            }
        }

    //METHODS
    override fun compareTo(other: Vector<TwoDimensions>): Int {
        return ceil((this - other).norm).toInt()
    }
    override fun copy() = Vector2D(x, y)

    override infix fun dot(vector: Vector<TwoDimensions>) = x * vector.x + y * vector.y
    override fun get(vector: Vector<TwoDimensions>): Vector<TwoDimensions>? {
        return if (vector.norm == 0.0)
            null
        else {
            vector.toVector2D().apply {
                norm = this dot (vector / vector.norm)
            }
        }
    }
    override operator fun minus(vector: Vector<TwoDimensions>) =
        Vector2D(
            x - vector.x,
            y - vector.y
        )
    override operator fun plus(vector: Vector<TwoDimensions>) =
        Vector2D(
            x + vector.x,
            y + vector.y
        )
    override fun times(vector: Vector<TwoDimensions>) = Vector2D(
        (vector * x).sum(),
        (vector * y).sum()
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
    override fun normedTo(number: Double) = toVector2D().apply { norm = number }

    /**
     * Rotates this vector of the given angle.
     * @param of the angle of rotation
     */
    fun rotate(of: Double) = Vector2D(
        cos(of) * x - sin(of) * y,
        sin(of) * x + cos(of) * y
    )

    override fun sum() = x + y

    override fun toString() = "($x, $y)"

    override fun toVector2D() = copy()
    override fun toVector3D() = Vector3D(x, y, 0.0)
    override fun toVector4D() = Vector4D(x, y, 0.0, 0.0)
}

/**
 * Represents 3-dimensions mutable vectors.
 */
class Vector3D(
    override var x: Double,
    override var y: Double,
    override var z: Double
): Vector<ThreeDimensions> {

    //PROPERTIES
    override val w = 0.0
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

    //METHODS
    override fun compareTo(other: Vector<ThreeDimensions>): Int {
        return ceil((this - other).norm).toInt()
    }
    override fun copy() = Vector3D(x, y, z)

    /**
     * Computes the vector product of this vector and the given vector.
     */
    infix fun x(vector: Vector3D) = Vector3D(
        y * vector.z - z * vector.y,
        z * vector.x - x * vector.z,
        x * vector.y - y * vector.x
    )
    override infix fun dot(vector: Vector<ThreeDimensions>) = x * vector.x + y * vector.y + z * vector.z
    override fun get(vector: Vector<ThreeDimensions>): Vector<ThreeDimensions>? {
        return if (vector.norm == 0.0)
            null
        else {
            vector.toVector3D().apply {
                norm = this dot (vector / vector.norm)
            }
        }
    }
    override operator fun minus(vector: Vector<ThreeDimensions>) =
        Vector3D(
            x - vector.x,
            y - vector.y,
            z - vector.z
        )
    override operator fun plus(vector: Vector<ThreeDimensions>) =
        Vector3D(
            x + vector.x,
            y + vector.y,
            z + vector.z
        )
    override fun times(vector: Vector<ThreeDimensions>) = Vector3D(
        (vector * x).sum(),
        (vector * y).sum(),
        (vector * z).sum()
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
    override fun normedTo(number: Double) = toVector3D().apply { norm = number }

    fun rotate(of: Double, around: Vector3D): Vector3D {    //TODO verify
        if (around.norm != 1.0)
            around.norm = 1.0
        val sinBy2 = sin(of / 2)
        val q = Quaternion(
            cos(of / 2),
            sinBy2 * around.x,
            sinBy2 * around.y,
            sinBy2 * around.z
        )
        return (q * this.toVector4D() * q.inv()).toVector3D()
    }
    /**
     * Rotates this vector of the given angle around the X axis.
     * @param of the angle of rotation
     */
    fun rotateAroundX(of: Double) = Vector3D(
        cos(of) * x - sin(of) * y,
        sin(of) * x + cos(of) * y,
        z
    )
    /**
     * Rotates this vector of the given angle around the Y axis.
     * @param of the angle of rotation
     */
    fun rotateAroundY(of: Double) = Vector3D(
        cos(of) * x + sin(of) * z,
        y,
        cos(of) * z - sin(of) * x
    )
    /**
     * Rotates this vector of the given angle around the Z axis.
     * @param of the angle of rotation
     */
    fun rotateAroundZ(of: Double) = Vector3D(
        x,
        cos(of) * y - sin(of) * z,
        sin(of) * y + cos(of) * z
    )

    override fun sum() = x + y + z

    override fun toString() = "($x, $y, $z)"

    override fun toVector2D() = Vector2D(x, y)
    override fun toVector3D() = copy()
    override fun toVector4D() = Vector4D(x, y, z, 0.0)
}

/**
 * Represents 4-dimensions mutable vectors and quaternions.
 */
typealias Quaternion = Vector4D //TODO Is that really good as typealias ?
class Vector4D(
    override var x: Double,
    override var y: Double,
    override var z: Double,
    override var w: Double
): Vector<FourDimensions> {

    //PROPERTIES
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

    //METHODS
    override fun compareTo(other: Vector<FourDimensions>): Int {
        return ceil((this - other).norm).toInt()
    }
    override fun copy() = Vector4D(x, y, z, w)

    /**
     * Returns the conjugate of this as Quaternion.
     * @see Quaternion
     */
    fun conjugate() = Vector4D(-x, -y, -z, w)
    /**
     * Returns the quaternion q which match: this * q = 1.0.
     */
    /*TODO see for override*/ fun inv() = conjugate() / norm
    override infix fun dot(vector: Vector<FourDimensions>) = x * vector.x + y * vector.y + z * vector.z + w * vector.w
    override fun get(vector: Vector<FourDimensions>): Vector<FourDimensions>? {
        return if (vector.norm == 0.0)
            null
        else {
            vector.toVector4D().apply {
                norm = this dot (vector / vector.norm)
            }
        }
    }
    override operator fun minus(vector: Vector<FourDimensions>) =
        Vector4D(
            x - vector.x,
            y - vector.y,
            z - vector.z,
            w - vector.w
        )
    override operator fun plus(vector: Vector<FourDimensions>) =
        Vector4D(
            x + vector.x,
            y - vector.y,
            z - vector.z,
            w + vector.w
        )
    override operator fun unaryMinus() = Vector4D(-x, -y, -z, -w)
    override operator fun unaryPlus() = Vector4D(x, y, z, w)
    override operator fun times(vector: Vector<FourDimensions>) =
        Vector4D(
            (vector * x).sum(),
            (vector * y).sum(),
            (vector * z).sum(),
            (vector * w).sum()
        )

    override operator fun div(number: Double) = Vector4D(
        x / number,
        y / number,
        z / number,
        w / number
    )
    override operator fun times(number: Double) = Vector4D(
        x * number,
        y * number,
        z * number,
        w * number
    )
    override fun normedTo(number: Double) = toVector4D().apply { norm = number }

    override fun sum() = x + y + z + w

    override fun toString() = "($x, $y, $z, $w)"

    override fun toVector2D() = Vector2D(x, y)
    override fun toVector3D() = Vector3D(x, y, z)
    override fun toVector4D() = copy()
}

operator fun <D: Sized> Vector<D>.get(vararg axis: Vector<D>): Vector<D>? {
    return axis.map { this[it] }.reduce { acc, vector ->
        if (vector === null) null else acc?.plus(vector)
    }
}