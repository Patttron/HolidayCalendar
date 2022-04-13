package teach.meskills.timetable.holidays

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import teach.meskills.timetable.date.DateItem

interface ContentRepository {
    suspend fun downloadHolidays(): List<HolidaysEntity>
    suspend fun holidaysToCalendar(): List<DateItem>
}