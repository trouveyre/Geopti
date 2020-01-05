package geopti

import kotlin.math.abs

/**
 * Represents lines with points P.
 */
interface Line<P: Point<P>, L: Line<P, L>>: PointSet<P, L>, Vectorial<P> {

    //PROPERTIES
    override var descriptor: P


    //METHODS
    /**
     * Returns the nearest point to the parameter on this Line.
     * If this.a == this.b, returns the point given.
     */
    override fun nearestPoint(to: P): P
}

/**
 * The point resulted by shifting 'pivotalPoint' by 'descriptor'.
 */
var <P: Point<P>, L: Line<P, L>> L.b: P
    get() = pivotalPoint + descriptor
    set(value) { descriptor = value - pivotalPoint }



/**
 * Represents segments with points P.
 */
class SegmentLine<P: Point<P>>(
    override var pivotalPoint: P,
    b: P
): Line<P, SegmentLine<P>> {

    //PROPERTIES
    override var descriptor = b - pivotalPoint


    //METHODS
    override fun copy() = SegmentLine(pivotalPoint, b)

    override fun contains(point: P): Boolean {
        val relativePoint = point - pivotalPoint
        val dotProduct = relativePoint dot descriptor
        return abs(dotProduct) == relativePoint.norm * descriptor.norm && dotProduct in 0.0..(descriptor.norm2)
    }
    override fun nearestPoint(to: P): P {
        val projection = (to - pivotalPoint)[descriptor]
        return if (projection?.norm ?: .0 !in 0.0..descriptor.norm)
            pivotalPoint
        else
            projection?.plus(pivotalPoint) ?: pivotalPoint
    }
}



/**
 * Represents ray with points P.
 */
class RayLine<P: Point<P>>(
    var origin: P,
    b: P
): Line<P, RayLine<P>> {

    //PROPERTIES
    override var pivotalPoint: P
        get() = origin
        set(value) { origin = value }
    override var descriptor = b - pivotalPoint


    //METHODS
    override fun copy() = RayLine(origin, b)

    override fun contains(point: P): Boolean {
        val relativePoint = point - pivotalPoint
        val dotProduct = relativePoint dot descriptor
        return abs(dotProduct) == relativePoint.norm * descriptor.norm && dotProduct >= 0.0
    }
    override fun nearestPoint(to: P): P {
        val relativePoint = to - pivotalPoint
        return if (descriptor dot relativePoint < 0.0)
            pivotalPoint
        else
            relativePoint[descriptor]?.plus(pivotalPoint) ?: pivotalPoint
    }
}



/**
 * Represents straight lines with points of dimension D.
 */
class StraightLine<P: Point<P>>(
    override var pivotalPoint: P,
    b: P
): Line<P, StraightLine<P>> {

    //PROPERTIES
    override var descriptor = b - pivotalPoint


    //METHODS
    override fun copy() = StraightLine(pivotalPoint, b)

    override fun contains(point: P): Boolean {
        return (point - pivotalPoint) isCollinearWith descriptor
    }
    override fun nearestPoint(to: P): P {
        return (to - pivotalPoint)[descriptor]?.plus(pivotalPoint) ?: pivotalPoint
    }
}



/**
 * Fast way to create a SegmentLine.
 * @see SegmentLine
 */
infix fun <P: Point<P>> P.xlx(point: P) = SegmentLine(this, point)

/**
 * Fast way to create a RayLine with the l-value as its origin.
 * @see RayLine
 */
infix fun <P: Point<P>> P.xl_(point: P) = RayLine(this, point)

/**
 * Fast way to create a RayLine with the r-value as its origin.
 * @see RayLine
 */
infix fun <P: Point<P>> P._lx(point: P) = RayLine(point, this)

/**
 * Fast way to create a StraightLine.
 * @see StraightLine
 */
infix fun <P: Point<P>> P._l_(point: P) = StraightLine(this, point)