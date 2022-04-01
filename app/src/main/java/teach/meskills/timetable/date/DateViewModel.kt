package teach.meskills.timetable.date

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class DateViewModel : ViewModel() {

    val dateItemLiveData = MutableLiveData<List<DateItem>>()

    fun onChangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<DateItem>()
                for(s in snapshot.children){
                    val item = s.getValue(DateItem::class.java)
                    if(item != null)
                        list.add(item)
                }
                dateItemLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}


