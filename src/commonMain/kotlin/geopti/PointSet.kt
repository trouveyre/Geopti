package geopti

interface PointSet<D: Dimensions> {

    //METHODS
    operator fun contains(point: Point<D>): Boolean
}