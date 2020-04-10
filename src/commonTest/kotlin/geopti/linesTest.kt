package geopti

import kotlin.test.Test
import kotlin.test.assertEquals

interface LineTest {

    fun nearestPointTest()
}

class RayLineTest: LineTest {

    @Test
    override fun nearestPointTest() {
        assertEquals(
            p(0.0, 0.0, 0.0),
            (p(0.0, 0.0, 0.0) xl_ p(1.0, 2.3, -4.01)).nearestPoint(p(0.0, 0.0, 0.0)),
            "nearest point from the origin of ray should be the origin"
        )
//        assertEquals(
//            p(1.0, 2.3, -4.01),
//            (p(0.0, 0.0, 0.0) xl_ p(1.0, 2.3, -4.01)).nearestPoint(p(1.0, 2.3, -4.01)),
//            "nearest point from ray's b point should be b"
//        )
        assertEquals(
            p(0.0, 0.0, 0.0),
            (p(0.0, 0.0, 0.0) xl_ p(1.0, 2.3, -4.01)).nearestPoint(p(-1.0, -86.0, 5.56)),
            "nearest point from behind the ray's origin should be the origin"
        )
//        assertEquals(
//            p(3.0, 6.9, -12.03),
//            (p(0.0, 0.0, 0.0) xl_ p(1.0, 2.3, -4.01)).nearestPoint(p(3.0, 6.9, -12.03)),
//            "nearest point from a point of ray should be that point"
//        )
        assertEquals(
            p(3.9, 0.0, 0.0),
            (p(0.0, 0.0, 0.0) xl_ p(1.0, 0.0, 0.0)).nearestPoint(p(3.9, 6.9, -12.03)),
            "nearest point from a point out of ray should be the point of ray and the line perpendicular to ray passing by that point"
        )
    }
}