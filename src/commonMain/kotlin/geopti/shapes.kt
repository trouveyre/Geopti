package geopti

interface Shape<D: Dimensions>: PointSet<D> {

    //PROPERTIES
    var center: Point<D>
    var size: Double
    //TODO orientation
}

interface Shape2D: Shape<TwoDimensions> {

    //PROPERTIES
    var height: Double
    var width: Double
}

interface Shape3D: Shape<ThreeDimensions> {

    //PROPERTIES
    var depth: Double
    var height: Double
    var width: Double
}

class Circle(
    var radius: Double,
    override var center: Point<TwoDimensions> = geopti.Point2D(
        0.0,
        0.0
    )
): Shape2D {

    //PROPERTIES
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
    override fun contains(point: Point<TwoDimensions>) = (point - center).norm <= radius
}

class Sphere(
    var radius: Double,
    override var center: Point<ThreeDimensions> = geopti.Point3D(
        0.0,
        0.0,
        0.0
    )
): Shape3D {

    //PROPERTIES
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
    override fun contains(point: Point<ThreeDimensions>) = (point - center).norm <= radius
}
