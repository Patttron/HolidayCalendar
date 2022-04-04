package teach.meskills.timetable.date

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import teach.meskills.timetable.R


class DateAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    var listener: RecyclerViewClickListener? = null
    val database = Firebase.database
    val myRef = database.getReference("message")
    lateinit var auth: FirebaseAuth

    var dateItems: List<DateItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.date_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        auth = Firebase.auth
        val item = dateItems[position]
        holder.userName.text = auth.currentUser?.displayName.toString()
        holder.userText.text = item.userText
        holder.delete.setOnClickListener {
            listener?.onRecyclerViewClickListener(it, item)
        }
    }

    override fun getItemCount() = dateItems.size
}

//private fun delete(position: Int) {
//    // creating a variable for our Database
//    // Reference for Firebase.
////    val dbref = FirebaseDatabase.getInstance().reference.child("message")
//    val dbref = Firebase.database.getReference("message")
//    // we are use add listerner
//    // for event listener method
//    // which is called with query.
//    val query: Query = dbref.child(position.toString())
//    query.addListenerForSingleValueEvent(object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            // remove the value at reference
//            dataSnapshot.ref.removeValue()
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {}
//    })
//}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var userText = view.findViewById<TextView>(R.id.user_text)
    var userName = view.findViewById<TextView>(R.id.user_name)
    val delete = view.findViewById<ImageView>(R.id.delete_icon)

//    fun bind(item: DateItem) {
//        userName.text = auth.currentUser?.displayName.toString()
//        userText.text = item.userText
//    }
}

