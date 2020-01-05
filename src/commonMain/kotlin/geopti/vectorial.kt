package geopti

import kotlin.math.cos
import kotlin.math.sin

/**
 * TODO
 */
interface Vectorial<V: Vector<V>> { //TODO typo ?

    //PROPERTIES
    /**
     * A Vector representing this object.
     */
    val descriptor: V
}


/**
 * Rotates this Vectorial of the given angle then returns it.
 * @param by the angle of rotation
 */
fun <R: Vectorial<Vector2D>> R.rotate(by: Double): R = this.apply {
    descriptor.apply {
        x = cos(by) * x - sin(by) * y
        y = sin(by) * x + cos(by) * y
    }
}

/**
 * Rotates this Vectorial by the Quaternion given then returns this Vector.
 */
fun <R: Vectorial<Vector3D>> R.rotate(by: Quaternion): R {  //TODO 'by' must be unitary ?
    val q = by * descriptor.toQuaternion() * by.inv()
    descriptor.apply {
        x = q.x
        y = q.y
        z = q.z
    }
    return this
}
/**
 * Rotates this Vectorial by angle given around the axis provided then returns this Vector.
 */
fun <R: Vectorial<Vector3D>> R.rotate(by: Double, around: Vector3D): R {
    if (around.norm != 1.0)
        around.norm = 1.0
    return rotate(Quaternion(cos(by / 2), around * sin(by / 2)))
}
/**
 * Rotates this Vectorial by the Vector3D given then returns this Vector.
 */
fun <R: Vectorial<Vector3D>> R.rotate(by: Vector3D): R = rotate(by.norm, by)
/**
 * Rotates this Vectorial of the given angle around the X axis then returns it.
 * @param angle the angle of rotation
 */
fun <R: Vectorial<Vector3D>> R.rotateAroundX(angle: Double): R = this.apply {
    descriptor.apply {
        x = cos(angle) * x - sin(angle) * y
        y = sin(angle) * x + cos(angle) * y
    }
}
/**
 * Rotates this Vectorial of the given angle around the Y axis then returns it.
 * @param angle the angle of rotation
 */
fun <R: Vectorial<Vector3D>> R.rotateAroundY(angle: Double): R = this.apply {
    descriptor.apply {
        x = cos(angle) * x + sin(angle) * z
        z = cos(angle) * z - sin(angle) * x
    }
}
/**
 * Rotates this Vectorial of the given angle around the Z axis then returns it.
 * @param angle the angle of rotation
 */
fun <R: Vectorial<Vector3D>> R.rotateAroundZ(angle: Double): R = this.apply {
    descriptor.apply {
        y = cos(angle) * y - sin(angle) * z
        z = sin(angle) * y + cos(angle) * z
    }
}