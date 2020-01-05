package geopti

interface Shape<P: Point<P>, S: Shape<P, S>>: PointSet<P, S> {

    //PROPERTIES
    /**
     * The diameter of the smallest hypersphere containing all the points of this Shape and sharing the same center.
     * When changed, this property keeps the ratio of the sizes.
     */
    var size: Double
}

/**
 * The average location of all the points contained by this Shape.
 * This is also the pivotalPoint.
 */
var <P: Point<P>, S: Shape<P, S>> S.center: P
    get() = pivotalPoint
    set(value) { pivotalPoint = value }