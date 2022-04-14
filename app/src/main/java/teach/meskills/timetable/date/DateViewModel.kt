package teach.meskills.timetable.date

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import teach.meskills.timetable.ROOT_REFERENCE
import teach.meskills.timetable.holidays.ContentRepository
import java.lang.Exception

class DateViewModel(
    private val contentRepository: ContentRepository,
    private val contentDate: DateRepository
) : ViewModel() {

    val holidaysToDateLiveData = MutableLiveData<List<DateItem>>()
    private val scope = CoroutineScope(Dispatchers.Main)
    var dateItemLiveData = MutableLiveData<List<DateItem>>()
    var holidayDownloadFlag = MutableLiveData<Boolean>()


    fun deleteItem(dateItem: DateItem) {
        ROOT_REFERENCE.child(dateItem.id!!).removeValue()
    }

    fun getHolidays() {

        try {
            if (holidayDownloadFlag.value == null || holidayDownloadFlag.value == false) {
                scope.launch {
                    val response = withContext(Dispatchers.IO) {
                        contentRepository.holidaysToCalendar()
                    }
                    holidaysToDateLiveData.value = response
                    holidayDownloadFlag.value = true
                    Log.d("resp holidays live d1", response.toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getEventsForDate(year: Int, month: Int, day: Int) {
        dateItemLiveData = contentDate.geEventForDate(year, month, day) as MutableLiveData<List<DateItem>>
        Log.d("resp dateItem d2", dateItemLiveData.value.toString())
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}


