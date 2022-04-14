package teach.meskills.timetable.holidays

import com.google.gson.annotations.SerializedName

data class JsonResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: Response
) {
    data class Meta(
        @SerializedName("code")
        val code: Int
    )

    data class Response(
        @SerializedName("holidays")
        val holidays: List<Holiday>
    ) {
        data class Holiday(
            @SerializedName("date")
            val date: Date,
            @SerializedName("description")
            val description: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: List<String>
        ) {
            data class Date(
                @SerializedName("datetime")
                val datetime: Datetime,
                @SerializedName("iso")
                val iso: String
            ) {
                data class Datetime(
                    @SerializedName("day")
                    val day: Int,
                    @SerializedName("month")
                    val month: Int,
                    @SerializedName("year")
                    val year: Int
                )
            }
        }
    }
}