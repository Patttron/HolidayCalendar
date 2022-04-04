package teach.meskills.timetable.date

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DateViewModel : ViewModel() {

//    private val dbAuthors = FirebaseDatabase.getInstance().getReference("message")
private val myRef =   Firebase.database.getReference("message")
//    private val _result = MutableLiveData<Exception?>()
//    val result: LiveData<Exception?>
//        get() = _result

    val dateItemLiveData = MutableLiveData<List<DateItem>>()

    fun onChangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<DateItem>()
                for(s in snapshot.children){
                    val item = s.getValue(DateItem::class.java)
                    if(item != null)
//                        list.add(item)

                    item.id = s.key
                    item?.let { list.add(item) }

                }
                dateItemLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun deleteItem(dateItem: DateItem) {
        myRef.child(dateItem.id!!).removeValue()
//        setValue(null)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    _result.value = null
//                } else {
//                    _result.value = it.exception
//                }
//            }
    }
}



