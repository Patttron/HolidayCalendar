package teach.meskills.timetable.holidays

import android.util.Log
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitContentRepository : ContentRepository {

    private val mediaService: MediaService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://calendarific.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mediaService = retrofit.create(MediaService::class.java)
    }

    override suspend fun downloadHolidays(): List<HolidaysEntity> {

        return try {
            val holidayMap = mediaService.loadMedia(API_KEY).response.holidays.map {
                HolidaysEntity(
                    metaCode = it.code,
                    holidaysName = it.name,
                    descriptions = it.description,
                    dateIso = it.date.iso
                )
            }.orEmpty()
            Log.d("holidayMap", holidayMap.toString())
            holidayMap
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    companion object {
        const val API_KEY = "f0aaea10bdb084d8bde54336569043896b5dac67"
    }
}