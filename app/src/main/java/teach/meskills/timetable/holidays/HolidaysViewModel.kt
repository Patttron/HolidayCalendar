package teach.meskills.timetable.holidays

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import teach.meskills.timetable.holidays.ContentRepository
import teach.meskills.timetable.holidays.HolidaysEntity
import java.lang.Exception

class HolidaysViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    val holidays = MutableLiveData<List<HolidaysEntity>>()
    val scope = CoroutineScope(Dispatchers.Main)

    fun loadingData() {
        try {
            scope.launch {
                val response = withContext(Dispatchers.IO) {
                    contentRepository.downloadHolidays()
                }
                holidays.value = response
                Log.d("response", response.toString())
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}