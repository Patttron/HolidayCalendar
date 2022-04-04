package teach.meskills.timetable.holidays

import retrofit2.http.GET
import teach.meskills.timetable.holidays.RetrofitContentRepository.Companion.API_KEY

interface HolidayService {

    @GET("api/v2/holidays?&api_key=$API_KEY&country=by&year=2022")
    suspend fun loadMedia(): HolidayResponseDTO
}
