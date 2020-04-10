package geopti

import kotlin.math.hypot
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

interface VectorTest {

    fun normTest()

    fun norm2Test()

//    fun isNullTest()

    fun dotTest()

//    fun minusTest()

//    fun plusTest()

//    fun divTest()

//    fun timesTest()

//    fun unaryMinusTest()

//    fun unaryPlusTest()

//    fun sumTest()

//    fun toVector2DTest()

//    fun toVector3DTest()

//    fun toQuaternionTest()
}

class Vector3DTest: VectorTest {

    @Test
    override fun normTest() {
        assertEquals(
            0.0,
            v(0.0, 0.0, 0.0).norm,
            "norm of null vector should be 0.0"
        )
        assertEquals(
            1.0,
            v(1.0, 0.0, 0.0).norm,
            "norm of x-unit vector should be 1.0"
        )
        assertEquals(
            1.0,
            v(0.0, 1.0, 0.0).norm,
            "norm of y-unit vector should be 1.0"
        )
        assertEquals(
            1.0,
            v(0.0, 0.0, 1.0).norm,
            "norm of z-unit vector should be 1.0"
        )
        assertEquals(
            1.0,
            v(-1.0, 0.0, 0.0).norm,
            "norm of negative x-unit vector should be 1.0"
        )
        assertEquals(
            hypot(1.0, 1.0),
            v(1.0, 1.0, 0.0).norm,
            "norm of v(1.0, 1.0, 0.0) should be hypot(1.0, 1.0)"
        )
        assertEquals(
            v(1.0, 6.0, 2.2).norm,
            v(-1.0, -6.0, -2.2).norm,
            "norm of v should be norm of -v"
        )
        assertEquals(
            3.0,
            v(1.0, 2.0, 2.0).norm,
            "norm of v(1.0, 2.0, 2.0) should be 3.0"
        )
        assertEquals(
            v(10.0 / 3, 20.0 / 3, 20.0 / 3),
            v(1.0, 2.0, 2.0).apply { norm = 10.0 },
            "bad setting of v's norm"
        )
        assertEquals(
            v(0.0, 0.0, 0.0),
            v(1.0, 2.0, 2.0).apply { norm = 0.0 },
            "bad setting of v's norm to 0.0"
        )
    }

    @Ignore
    @Test
    override fun norm2Test() {
        TODO("Not yet implemented")
    }

    @Test
    override fun dotTest() {
        assertEquals(
            0.0,
            v(0.0, 0.0, 0.0) dot v(0.0, 8.2, -4.1),
            "dot product with null left operand should be 0.0"
        )
        assertEquals(
            0.0,
            v(3.987, 778.2, -104.1) dot v(0.0, 0.0, 0.0),
            "dot product with null right operand should be 0.0"
        )
        assertEquals(
            0.0,
            v(0.0, 0.0, 0.0) dot v(0.0, 0.0, 0.0),
            "dot product with null operands should be 0.0"
        )
        assertEquals(
            10.0,
            v(2.0, 0.0, 0.0) dot v(5.0, 0.0, 0.0),
            "dot product of collinear vectors should be ||u|| * ||v||"
        )
        assertEquals(
            0.0,
            v(2.0, 0.0, 0.0) dot v(0.0, 5.0, 0.0),
            "dot product of perpendicular vectors should be 0.0"
        )
        assertEquals(
            v(4.1, 0.7, -2.5) dot v(5.2, -8.3, 0.4),
            v(5.2, -8.3, 0.4) dot v(4.1, 0.7, -2.5),
            "dot product should be commutative"
        )
        assertEquals(
            8.609,
            v(1.0, 0.0, 0.0) dot v(8.609, 0.0, -5.2),
            "dot product with x-unit left operand should be x value of right operand"
        )
        assertEquals(
            0.0,
            v(0.0, 1.0, 0.0) dot v(8.609, 0.0, -5.2),
            "dot product with y-unit left operand should be y value of right operand"
        )
        assertEquals(
            -5.2,
            v(0.0, 0.0, 1.0) dot v(8.609, 0.0, -5.2),
            "dot product with z-unit left operand should be z value of right operand"
        )
    }

    @Ignore
    @Test
    fun crossTest() {
        TODO()
    }

    @Test
    fun getTest() {
        assertEquals(
            v(0.0, 0.0, -0.0),
            v(0.0, 0.0, 0.0)[v(0.8, 78.048, -9.2)],
            "projection of null vector should be null vector"
        )
        assertEquals(
            v(0.0, 0.0, 0.0),
            v(4.1, 0.896, 8.0)[v(0.0, 0.0, 0.0)],
            "projection on null vector should be null vector"
        )
        assertEquals(
            v(4.1, -8.3, 0.78),
            v(4.1, -8.3, 0.78)[v(4.1, -8.3, 0.78)],
            "projection of a vector on itself should be that vector"
        )
        assertEquals(
            v(4.1, 0.0, 0.0),
            v(4.1, -8.3, 0.78)[v(1.0, 0.0, 0.0)],
            "projection on x-unit vector should be x"
        )
        assertEquals(
            v(-0.0, -8.3, -0.0),
            v(4.1, -8.3, 0.78)[v(0.0, 1.0, 0.0)],
            "projection on y-unit vector should be y"
        )
        assertEquals(
            v(0.0, 0.0, 0.78),
            v(4.1, -8.3, 0.78)[v(0.0, 0.0, 1.0)],
            "projection on z-unit vector should be z"
        )
    }
}