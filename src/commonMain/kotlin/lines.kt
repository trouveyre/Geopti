import kotlin.math.abs

typealias Point<D> = Vector<D>
typealias Point2D = Vector2D
typealias Point3D = Vector3D

/**
 * Fast way to create a SegmentLine.
 * @see SegmentLine
 */
infix fun <D: Dimensions> Point<D>.xlx(point: Point<D>) = SegmentLine(this, point)
/**
 * Fast way to create a RayLine with the l-value as its origin.
 * @see RayLine
 */
infix fun <D: Dimensions> Point<D>.xl_(point: Point<D>) = RayLine(this, point)
/**
 * Fast way to create a RayLine with the r-value as its origin.
 * @see RayLine
 */
infix fun <D: Dimensions> Point<D>._lx(point: Point<D>) = RayLine(point, this)
/**
 * Fast way to create a StraightLine.
 * @see StraightLine
 */
infix fun <D: Dimensions> Point<D>._l_(point: Point<D>) = StraightLine(this, point)

/* TODO if the previous functions do not work
/**
 * Fast way to create a 2-dimensioned SegmentLine.
 * @see SegmentLine
 */
infix fun Point2D.x2x(point: Point2D) = SegmentLine(this, point)
/**
 * Fast way to create a 2-dimensioned RayLine with the l-value as its origin.
 * @see RayLine
 */
infix fun Point2D.x2_(point: Point2D) = RayLine(this, point)
/**
 * Fast way to create a 2-dimensioned RayLine with the r-value as its origin.
 * @see RayLine
 */
infix fun Point2D._2x(point: Point2D) = RayLine(point, this)
/**
 * Fast way to create a 2-dimensioned StraightLine.
 * @see StraightLine
 */
infix fun Point2D._2_(point: Point2D) = StraightLine(this, point)
/**
 * Fast way to create a 3-dimensioned SegmentLine.
 * @see SegmentLine
 */
infix fun Point3D.x3x(point: Point3D) = SegmentLine(this, point)
/**
 * Fast way to create a 3-dimensioned RayLine with the l-value as its origin.
 * @see RayLine
 */
infix fun Point3D.x3_(point: Point3D) = RayLine(this, point)
/**
 * Fast way to create a 3-dimensioned RayLine with the r-value as its origin.
 * @see RayLine
 */
infix fun Point3D._3x(point: Point3D) = RayLine(point, this)
/**
 * Fast way to create a 3-dimensioned StraightLine.
 * @see StraightLine
 */
infix fun Point3D._3_(point: Point3D) = StraightLine(this, point)
*/

/**
 * Represents lines with points of dimension D.
 */
interface Line<D: Dimensions> {

    //PROPERTIES
    var a: Point<D>
    var b: Point<D>

    //METHODS
    operator fun contains(point: Point<D>): Boolean
}

abstract class AbstractLine<D: Dimensions>: Line<D> {

    //PROPERTIES
    protected abstract var vector: Vector<D>
    override var b: Point<D>
        get() = a + vector
        set(value) { vector = value - a }
}

/**
 * Represents segments with points of dimension D.
 */
class SegmentLine<D: Dimensions>(
    override var a: Point<D>,
    b: Point<D>
): AbstractLine<D>() {

    //PROPERTIES
    override var vector = b - a

    //METHODS
    override fun contains(point: Point<D>): Boolean {
        return if (point.x in a.x..b.x && point.y in a.y..b.y) {
            val relativePoint = point - a
            relativePoint dot vector == relativePoint.norm * vector.norm
        }
        else false
    }
}

/**
 * Represents ray with points of dimension D.
 */
class RayLine<D: Dimensions>(
    var origin: Point<D>,
    b: Point<D>
): AbstractLine<D>() {

    //PROPERTIES
    override var a: Point<D>
        get() = origin
        set(value) { origin = value }
    override var vector = b - a

    //METHODS
    override fun contains(point: Point<D>): Boolean {
        val relativePoint = point - a
        return relativePoint dot vector == relativePoint.norm * vector.norm
    }
}

/**
 * Represents straight lines with points of dimension D.
 */
class StraightLine<D: Dimensions>(
    override var a: Point<D>,
    b: Point<D>
): AbstractLine<D>() {

    //PROPERTIES
    override var vector = b - a

    //METHODS
    override fun contains(point: Point<D>): Boolean {
        val relativePoint = point - a
        return abs(relativePoint dot vector) == relativePoint.norm * vector.norm
    }
}