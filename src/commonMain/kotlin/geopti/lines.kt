package geopti

import kotlin.math.abs

/**
 * Fast way to create a SegmentLine.
 * @see SegmentLine
 */
operator fun <D: Sized> Point<D>.rangeTo(point: Point<D>) = SegmentLine(this, point)
/**
 * Fast way to create a RayLine with the l-value as its origin.
 * @see RayLine
 */
infix fun <D: Sized> Point<D>.xl_(point: Point<D>) = RayLine(this, point)
/**
 * Fast way to create a RayLine with the r-value as its origin.
 * @see RayLine
 */
infix fun <D: Sized> Point<D>._lx(point: Point<D>) = RayLine(point, this)
/**
 * Fast way to create a StraightLine.
 * @see StraightLine
 */
infix fun <D: Sized> Point<D>._l_(point: Point<D>) = StraightLine(this, point)

/* TODO if the previous functions do not work
/**
 * Fast way to create a 2-dimensioned geopti.SegmentLine.
 * @see geopti.SegmentLine
 */
infix fun geopti.Point2D.x2x(point: geopti.Point2D) = geopti.SegmentLine(this, point)
/**
 * Fast way to create a 2-dimensioned geopti.RayLine with the l-value as its origin.
 * @see geopti.RayLine
 */
infix fun geopti.Point2D.x2_(point: geopti.Point2D) = geopti.RayLine(this, point)
/**
 * Fast way to create a 2-dimensioned geopti.RayLine with the r-value as its origin.
 * @see geopti.RayLine
 */
infix fun geopti.Point2D._2x(point: geopti.Point2D) = geopti.RayLine(point, this)
/**
 * Fast way to create a 2-dimensioned geopti.StraightLine.
 * @see geopti.StraightLine
 */
infix fun geopti.Point2D._2_(point: geopti.Point2D) = geopti.StraightLine(this, point)
/**
 * Fast way to create a 3-dimensioned geopti.SegmentLine.
 * @see geopti.SegmentLine
 */
infix fun geopti.Point3D.x3x(point: geopti.Point3D) = geopti.SegmentLine(this, point)
/**
 * Fast way to create a 3-dimensioned geopti.RayLine with the l-value as its origin.
 * @see geopti.RayLine
 */
infix fun geopti.Point3D.x3_(point: geopti.Point3D) = geopti.RayLine(this, point)
/**
 * Fast way to create a 3-dimensioned geopti.RayLine with the r-value as its origin.
 * @see geopti.RayLine
 */
infix fun geopti.Point3D._3x(point: geopti.Point3D) = geopti.RayLine(point, this)
/**
 * Fast way to create a 3-dimensioned geopti.StraightLine.
 * @see geopti.StraightLine
 */
infix fun geopti.Point3D._3_(point: geopti.Point3D) = geopti.StraightLine(this, point)
*/

/**
 * Represents lines with points of dimension D.
 */
interface Line<D: Sized>: PointSet<D> {

    //PROPERTIES
    var a: Point<D>
    var b: Point<D>

    //METHODS
    /**
     * Returns the nearest point to the parameter on this Line.
     * If this.a == this.b, returns the point given.
     */
    override fun nearestPoint(from: Point<D>): Point<D>
}

abstract class AbstractLine<D: Sized>: Line<D> {

    //PROPERTIES
    protected abstract var vector: Vector<D>
    override var b: Point<D>
        get() = a + vector
        set(value) { vector = value - a }

    //METHODS
    override fun nearestPoint(from: Point<D>): Point<D> {
        return (from - a)[vector]?.plus(a) ?: from
    }
}

/**
 * Represents segments with points of dimension D.
 */
class SegmentLine<D: Sized>(
    override var a: Point<D>,
    b: Point<D>
): AbstractLine<D>(), ClosedRange<Point<D>> {

    //PROPERTIES
    override var vector = b - a
    override val start: Point<D>
        get() = a
    override val endInclusive: Point<D>
        get() = b

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
class RayLine<D: Sized>(
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
class StraightLine<D: Sized>(
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