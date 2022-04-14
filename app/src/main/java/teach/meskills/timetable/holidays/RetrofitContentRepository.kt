package teach.meskills.timetable.holidays

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import teach.meskills.timetable.AUTH
import teach.meskills.timetable.ROOT_REFERENCE
import teach.meskills.timetable.date.DateItem

class RetrofitContentRepository : ContentRepository {

    private val holidayService: HolidayService


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://calendarific.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        holidayService = retrofit.create(HolidayService::class.java)
    }

    override suspend fun downloadHolidays(): List<HolidaysEntity> {

        return try {
            val holidayMap = holidayService.loadMedia().response.holidays.map {
                HolidaysEntity(
                    metaCode = it.code,
                    holidaysName = it.name,
                    descriptions = it.description,
                    holidayDateDay = it.date.datetime.day,
                    holidayDateMonth = it.date.datetime.month,
                    holidayDateYear = it.date.datetime.year
                )
            }
            Log.d("holidayMap", holidayMap.toString())
            holidayMap
        } catch (e: java.lang.Exception) {
            Log.d("holiday", e.printStackTrace().toString())
            return emptyList()
        }
    }

    override suspend fun holidaysToCalendar(): List<DateItem> {
        return try {
            var nodeKey = ""
            val holidayMap2 = holidayService.loadMedia().response.holidays.map {
                nodeKey = ROOT_REFERENCE.push().key.toString()

                DateItem(
                    it.name, it.description,
                    AUTH.currentUser?.uid,
                    nodeKey,
                    dateYear = it.date.datetime.year,
                    dateMonth = it.date.datetime.month,
                    dateDay = it.date.datetime.day
                )
            }
            for (i in holidayMap2){
                ROOT_REFERENCE.child(i.key.toString()).setValue(i)
            }
            Log.d("responHoliday1", holidayMap2.toString())
            holidayMap2
        } catch (e: java.lang.Exception) {
            Log.d("holiday", e.printStackTrace().toString())
            return emptyList()
        }
    }


    companion object {
        const val API_KEY = "f0aaea10bdb084d8bde54336569043896b5dac67"
    }
}