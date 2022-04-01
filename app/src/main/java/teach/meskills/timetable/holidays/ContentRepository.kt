package teach.meskills.timetable.holidays

interface ContentRepository {
  suspend  fun downloadHolidays(): List<HolidaysEntity>
}