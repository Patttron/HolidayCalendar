package teach.meskills.timetable.holidays

import retrofit2.http.GET
import retrofit2.http.Path

interface MediaService {

    @GET("api/v2/holidays?&api_key={kkkey}&country=by&year=2022")
    suspend fun loadMedia(@Path("kkkey") param: String): HolidayResponseDTO

}


//"https://calendarific.com/api/v2/holidays?&api_key=$API_KEY&country=by&year=2022"