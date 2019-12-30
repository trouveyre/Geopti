package geopti

typealias Point<D> = Vector<D>
typealias Point2D = Vector2D
typealias Point3D = Vector3D

interface PointSet<D: Sized> {

    //METHODS
    operator fun contains(point: Point<D>): Boolean
    /**
     * Returns the nearest point of this PointSet to the point given.
     */
    fun nearestPoint(from: Point<D>): Point<D>
}