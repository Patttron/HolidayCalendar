package teach.meskills.timetable

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

lateinit var ROOT_REFERENCE: DatabaseReference
lateinit var AUTH: FirebaseAuth
const val REFERENCE_PATH = "message"


fun Fragment.showToast(message:String){
    Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show()
}

fun init() {
    ROOT_REFERENCE = Firebase.database.getReference(REFERENCE_PATH)
    AUTH = Firebase.auth
}

