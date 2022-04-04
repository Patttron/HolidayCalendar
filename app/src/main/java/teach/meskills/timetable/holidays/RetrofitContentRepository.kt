package teach.meskills.timetable.holidays

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                    dateIso = it.date.iso
                )
            }
            Log.d("holidayMap", holidayMap.toString())
            holidayMap
        } catch (e: java.lang.Exception) {
            Log.d("holiday", e.printStackTrace().toString())
            return emptyList()
        }
    }

    companion object {
        const val API_KEY = "f0aaea10bdb084d8bde54336569043896b5dac67"
    }
}