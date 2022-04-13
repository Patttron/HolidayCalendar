package teach.meskills.timetable.date

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import teach.meskills.timetable.ROOT_REFERENCE

class DateRepository(private val dRef: DatabaseReference) {

    var nodeKey = ROOT_REFERENCE.push().key.toString()
    val liveData = MutableLiveData<List<DateItem>>()

    fun geEventForDate(year: Int, month: Int, day: Int): LiveData<List<DateItem>> {
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               val list = ArrayList<DateItem>()
                for (s in snapshot.children) {
                    val item = s.getValue(DateItem::class.java)
                    if (item != null) {
                        if (item.dateYear == year &&
                            item.dateMonth == month +1 &&
                            item.dateDay == day
                        ) {

                            item.id = s.key
                            item.let { list.add(item) }
                        }
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