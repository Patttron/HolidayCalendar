package teach.meskills.timetable.date

import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DateViewModel : ViewModel() {

private val myRef =   Firebase.database.getReference("message")

    fun deleteItem(dateItem: DateItem) {
        myRef.child(dateItem.id!!).removeValue()
    }
}



