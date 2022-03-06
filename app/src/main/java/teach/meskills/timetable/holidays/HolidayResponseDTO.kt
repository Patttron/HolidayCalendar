package teach.meskills.timetable

import com.google.gson.annotations.SerializedName

class HolidayResponseDTO(
//    @SerializedName("meta")
//    val meta: Meta,
    @SerializedName("response")
    val response: Response
)

data class Response(
    @SerializedName("holidays")
    val holidays: List<Holiday>,
)
data class Meta(
    @SerializedName("code")
    val code: Int
)

data class Holiday(
    @SerializedName("code")
    val code: Int,
    @SerializedName("date")
    val date: Date,
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: List<String>
)

data class Date(
    @SerializedName("datetime")
    val datetime: Datetime,
    @SerializedName("iso")
    val iso: String
)

data class Datetime(
    @SerializedName("day")
    val day: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("year")
    val year: Int
)
