package geopti

import kotlin.math.abs
import kotlin.math.hypot


/**
 *
 */
interface Shape2D<S: Shape2D<S>>: Shape<Point2D, S> {

    //PROPERTIES
    var height: Double
    var width: Double
    var orientation: Double
}



/**
 *
 */
class Circle(
    var radius: Double,
    center: Point2D = v(0.0, 0.0),
    override var orientation: Double = 0.0
): Shape2D<Circle> {

    //PROPERTIES
    override var pivotalPoint: Point2D = center

    override var size: Double
        get() = radius * 2
        set(value) { radius = value / 2}
    override var height: Double
        get() = size
        set(value) { size = value }
    override var width: Double
        get() = size
        set(value) { size = value }

    //METHODS
    override fun copy() = Circle(radius, center, orientation)

    override fun contains(point: Point2D) = (point - center).norm <= radius
    override fun nearestPoint(to: Point2D): Point2D {
        return if (to in this)
            to.copy()
        else
            center + ((to - center) normedTo radius)
    }
}



/**
 *
 */
open class Rectangle(
    width: Double,
    height: Double,
    center: Point2D = v(0.0, 0.0),
    override var orientation: Double = 0.0
): Shape2D<Rectangle> {

    //PROPERTIES
    override var pivotalPoint: Point2D = center

    open var hWidth: Double = width / 2
    open var hHeight: Double = height / 2

    final override var size: Double
        get() = hypot(hWidth, hHeight)
        set(value) {
            val (x, y) = v(hWidth, hHeight).apply { norm = value }
            hWidth = x
            hHeight = y
        }
    final override var width: Double
        get() = hWidth * 2
        set(value) { hWidth = value / 2 }
    final override var height: Double
        get() = hHeight * 2
        set(value) { hHeight = value / 2 }


    //METHODS
    override fun copy() = Rectangle(width, height, center, orientation)

    final override fun contains(point: Point2D): Boolean {
        val relativePoint = (point - center).rotate(-orientation)
        return abs(relativePoint.x) <= hWidth && abs(relativePoint.y) <= hHeight
    }
    final override fun nearestPoint(to: Point2D): Point2D {
        val relativePoint = (to - center).rotate(-orientation)
        val result = v(0.0, 0.0)
        result.x += relativePoint[v(1.0, 0.0)]?.x?.coerceIn(-hWidth, hWidth) ?: 0.0
        result.y += relativePoint[v(0.0, 1.0)]?.y?.coerceIn(-hHeight, hHeight) ?: 0.0
        return result.rotate(orientation) + center
    }
}


/**
 *
 */
class Square(
    sideSize: Double,
    center: Point2D = v(0.0, 0.0),
    orientation: Double = 0.0
): Rectangle(sideSize, sideSize, center, orientation) {

    //PROPERTIES
    var hSideSize: Double = sideSize / 2
    var sideSize: Double
        get() = hSideSize * 2
        set(value) { hSideSize = value / 2 }
    override var hHeight: Double
        get() = hSideSize
        set(value) { hSideSize = value }
    override var hWidth: Double
        get() = hSideSize
        set(value) { hSideSize = value }


    //METHODS
    fun toRectangle() = Rectangle(sideSize, sideSize, center, orientation)
}