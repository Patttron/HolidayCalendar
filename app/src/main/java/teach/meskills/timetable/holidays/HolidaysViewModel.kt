package teach.meskills.timetable.holidays

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.lang.Exception

class HolidaysViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    val holidaysLiveData = MutableLiveData<List<HolidaysEntity>>()
    private val scope = CoroutineScope(Dispatchers.Main)

    fun loadingData() {
        try {
            scope.launch {
                val response = withContext(Dispatchers.IO) {
                    contentRepository.downloadHolidays()
                }
                holidaysLiveData.value = response
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