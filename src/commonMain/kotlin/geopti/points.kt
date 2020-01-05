package geopti

typealias Point<V> = Vector<V>
typealias Point2D = Vector2D
typealias Point3D = Vector3D

interface PointSet<P: Point<P>, S: PointSet<P, S>> {

    //PROPERTIES
    /**
     * TODO
     */
    var pivotalPoint: P


    //METHODS
    fun copy(): S

    operator fun contains(point: P): Boolean
    /**
     * Returns the nearest point of this PointSet from the point given.
     */
    fun nearestPoint(to: P): P
}


/**
 * TODO
 */
fun <V: Vector<V>, S: PointSet<V, S>> S.translate(by: V): S {
    return this.apply { pivotalPoint += by }
}

/**
 * TODO
 */
infix fun <P: Point<P>, S: PointSet<P, S>> S.translatedTo(point: P): S {
    return this.apply { pivotalPoint = point }
}


/**
 * Fast way to make a Point.
 */
fun p(x: Double, y: Double): Point2D = v(x, y)
/**
 * Fast way to make a Point.
 */
fun p(x: Double, y: Double, z: Double): Point3D = v(x, y, z)