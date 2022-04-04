package teach.meskills.timetable.date

import android.os.Bundle

data class DateItem(
    val userName: String? = null,
    var userText: String? = null,
    var id: String? = null,
    var date: Bundle? = null
) {
//    override fun equals(other: Any?): Boolean {
//        return if (other is DateItem) {
//            other.id == id
//        } else false
//    }
//
//    override fun hashCode(): Int {
//        var result = id?.hashCode() ?: 0
//        result = 31 * result + (userName?.hashCode() ?: 0)
//        return result
//    }
}