package teach.meskills.timetable.date

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class BaseRepository(private val dRef: DatabaseReference) {

    val liveData = MutableLiveData<List<DateItem>>()

    fun geEventForDate(year: Int, month: Int, day: Int): LiveData<List<DateItem>> {
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<DateItem>()
                for (s in snapshot.children) {
                    val item = s.getValue(DateItem::class.java)
                    if (item != null) {
                        if (
                            item.dateYear == year &&
                            item.dateMonth == month &&
                            item.dateDay == day
                        ) {
//
                            item.id = s.key
                            item.let { list.add(item) }
                        }

                        list = list.distinctBy { item ->
                            DateItemUniqueSelector(
                                item.userName,
                                item.userText,
                                item.dateYear,
                                item.dateMonth,
                                item.dateDay
                            )
                        } as ArrayList<DateItem>
                    }
                }
                liveData.value = list
                Log.d("resp baseItem d2", liveData.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return liveData
    }
}

data class DateItemUniqueSelector(
    var userName: String? = null,
    var userText: String? = null,
    var dateYear: Int = 0,
    var dateMonth: Int = 0,
    var dateDay: Int = 0
)