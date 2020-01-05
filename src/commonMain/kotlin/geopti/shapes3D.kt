package geopti

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.sign


/**
 *
 */
interface Shape3D<S: Shape3D<S>>: Shape<Point3D, S> {

    //PROPERTIES
    var depth: Double
    var height: Double
    var width: Double
    var orientation: Vector3D
}



/**
 *
 */
class Ball(
    var radius: Double,
    center: Point3D = Point3D(0.0, 0.0, 0.0),
    override var orientation: Vector3D = v(0.0, 0.0, 0.0)
): Shape3D<Ball> {

    //PROPERTIES
    override var pivotalPoint: Point3D = center

    override var size: Double
        get() = radius * 2
        set(value) { radius = value / 2}
    override var depth: Double
        get() = size
        set(value) { size = value }
    override var height: Double
        get() = size
        set(value) { size = value }
    override var width: Double
        get() = size
        set(value) { size = value }

    //METHODS
    override fun copy() = Ball(radius, center, orientation)

    override fun contains(point: Point3D) = (point - center).norm <= radius
    override fun nearestPoint(to: Point3D): Point3D {
        return if (to in this)
            to.copy()
        else
            center + ((to - center) normedTo radius)
    }
}



/**
 *
 */
open class Cuboid(
    width: Double,
    height: Double,
    depth: Double,
    center: Point3D = v(0.0, 0.0, 0.0),
    override var orientation: Vector3D = v(0.0, 0.0, 0.0)
): Shape3D<Cuboid> {

    //PROPERTIES
    override var pivotalPoint: Point3D = center

    open var hDepth: Double = depth / 2
    open var hHeight: Double = height / 2
    open var hWidth: Double = width / 2

    final override var size: Double
        get() = hypot(width, hypot(height, depth))
        set(value) {
            val (x, y, z) = v(hWidth, hHeight, hDepth).apply { norm = value / 2 }
            hWidth = x
            hHeight = y
            hDepth = z
        }
    final override var depth: Double
        get() = hDepth * 2
        set(value) { hDepth = value / 2 }
    final override var height: Double
        get() = hHeight * 2
        set(value) { hHeight = value / 2 }
    final override var width: Double
        get() = hWidth * 2
        set(value) { hWidth = value / 2 }


    //METHODS
    override fun copy() = Cuboid(width, height, depth, center, orientation)

    final override fun contains(point: Point3D): Boolean {
        val relativePoint = (point - center).rotate(-orientation)
        return abs(relativePoint.x) == hWidth &&
                abs(relativePoint.y) in 0.0..(hHeight) &&
                abs(relativePoint.z) in 0.0..(hDepth) ||
                abs(relativePoint.x) in 0.0..(hWidth) &&
                abs(relativePoint.y) == hHeight &&
                abs(relativePoint.z) in 0.0..(hDepth) ||
                abs(relativePoint.x) in 0.0..(hWidth) &&
                abs(relativePoint.y) in 0.0..(hHeight) &&
                abs(relativePoint.z) == hDepth
    }
    final override fun nearestPoint(to: Point3D): Point3D {
        val relativePoint = (to - center).rotate(-orientation)
        val result = v(0.0, 0.0, 0.0)
        result.x = relativePoint[v(1.0, 0.0, 0.0)]?.x?.coerceIn(-hWidth, hWidth) ?: 0.0
        result.y = relativePoint[v(0.0, 1.0, 0.0)]?.y?.coerceIn(-hHeight, hHeight) ?: 0.0
        result.z = relativePoint[v(0.0, 0.0, 1.0)]?.z?.coerceIn(-hDepth, hDepth) ?: 0.0
        return result.rotate(orientation) + center
    }
}



/**
 *
 */
class Cube(
    sideSize: Double,
    center: Point3D = v(0.0, 0.0, 0.0),
    orientation: Vector3D = v(0.0, 0.0, 0.0)
): Cuboid(sideSize, sideSize, sideSize, center, orientation) {

    //PROPERTIES
    var hSideSize: Double = sideSize / 2
    var sideSize: Double
        get() = hSideSize * 2
        set(value) { hSideSize = value / 2 }
    override var hDepth: Double
        get() = hSideSize
        set(value) { hSideSize = value }
    override var hHeight: Double
        get() = hSideSize
        set(value) { hSideSize = value }
    override var hWidth: Double
        get() = hSideSize
        set(value) { hSideSize = value }

    //PROPERTIES
    fun toCuboid() = Cuboid(sideSize, sideSize, sideSize, center, orientation)
}



/**
 *
 */
open class CuboidFrame(    //TODO Is that a good name ? Noooo.
    width: Double,
    height: Double,
    depth: Double,
    center: Point3D = v(0.0, 0.0, 0.0),
    override var orientation: Vector3D = v(0.0, 0.0, 0.0)
): Shape3D<CuboidFrame> {

    //PROPERTIES
    override var pivotalPoint: Point3D = center

    open var hDepth: Double = depth / 2
    open var hHeight: Double = height / 2
    open var hWidth: Double = width / 2

    final override var size: Double
        get() = hypot(width, hypot(height, depth))
        set(value) {
            val (x, y, z) = v(hWidth, hHeight, hDepth).apply { norm = value / 2 }
            hWidth = x
            hHeight = y
            hDepth = z
        }
    final override var depth: Double
        get() = hDepth * 2
        set(value) { hDepth = value / 2 }
    final override var height: Double
        get() = hHeight * 2
        set(value) { hHeight = value / 2 }
    final override var width: Double
        get() = hWidth * 2
        set(value) { hWidth = value / 2 }


    //METHODS
    override fun copy() = CuboidFrame(width, height, depth, center, orientation)

    final override fun contains(point: Point3D): Boolean {
        val relativePoint = (point - center).rotate(-orientation)
        return abs(relativePoint.x) == hWidth &&
                abs(relativePoint.y) in 0.0..(hHeight) &&
                abs(relativePoint.z) in 0.0..(hDepth) ||
                abs(relativePoint.x) in 0.0..(hWidth) &&
                abs(relativePoint.y) == hHeight &&
                abs(relativePoint.z) in 0.0..(hDepth) ||
                abs(relativePoint.x) in 0.0..(hWidth) &&
                abs(relativePoint.y) in 0.0..(hHeight) &&
                abs(relativePoint.z) == hDepth
    }
    final override fun nearestPoint(to: Point3D): Point3D {
        val relativePoint = (to - center).rotate(-orientation)
        val result = v(0.0, 0.0, 0.0)
        val x = relativePoint[v(1.0, 0.0, 0.0)]?.x?.coerceIn(-hWidth, hWidth) ?: 0.0
        val y = relativePoint[v(0.0, 1.0, 0.0)]?.y?.coerceIn(-hHeight, hHeight) ?: 0.0
        val z = relativePoint[v(0.0, 0.0, 1.0)]?.z?.coerceIn(-hDepth, hDepth) ?: 0.0
        result.x =
            if (hWidth - abs(x) <= hHeight - abs(y) && hWidth - abs(x) <= hDepth - abs(z))
                hWidth * sign(x)
            else
                x
        result.y =
            if (hHeight - abs(y) < hWidth - abs(x) && hHeight - abs(y) <= hDepth - abs(z))
                hHeight * sign(y)
            else
                y
        result.z =
            if (hDepth - abs(z) < hWidth - abs(x) && hDepth - abs(z) < hHeight - abs(y))
                hDepth * sign(z)
            else
                z
        return result.rotate(orientation) + center
    }
}


/**
 *
 */
class CubeFrame(
    sideSize: Double,
    center: Point3D = v(0.0, 0.0, 0.0),
    orientation: Vector3D = v(0.0, 0.0, 0.0)
): CuboidFrame(sideSize, sideSize, sideSize, center, orientation) {

    //PROPERTIES
    var hSideSize: Double = sideSize / 2
    var sideSize: Double
        get() = hSideSize * 2
        set(value) { hSideSize = value / 2 }
    override var hDepth: Double
        get() = hSideSize
        set(value) { hSideSize = value }
    override var hHeight: Double
        get() = hSideSize
        set(value) { hSideSize = value }
    override var hWidth: Double
        get() = hSideSize
        set(value) { hSideSize = value }

    //PROPERTIES
    fun toCuboidFrame() = CuboidFrame(sideSize, sideSize, sideSize, center, orientation)
}



/**
 *
 */
class Sphere(
    var radius: Double,
    center: Point3D = Point3D(0.0, 0.0, 0.0),
    override var orientation: Vector3D = v(0.0, 0.0, 0.0)
): Shape3D<Sphere> {

    //PROPERTIES
    override var pivotalPoint: Point3D = center

    override var size: Double
        get() = radius * 2
        set(value) { radius = value / 2}
    override var depth: Double
        get() = size
        set(value) { size = value }
    override var height: Double
        get() = size
        set(value) { size = value }
    override var width: Double
        get() = size
        set(value) { size = value }

    //METHODS
    override fun copy() = Sphere(radius, center, orientation)

    override fun contains(point: Point3D) = (point - center).norm == radius
    override fun nearestPoint(to: Point3D): Point3D {
        return center + ((to - center) normedTo radius)
    }
}
