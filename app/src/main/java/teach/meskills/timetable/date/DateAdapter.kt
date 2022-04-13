package teach.meskills.timetable.date

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import teach.meskills.timetable.AUTH
import teach.meskills.timetable.R

class DateAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    var listener: RecyclerViewClickListener? = null

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
        val item = dateItems[position]
        holder.bind(item)
        holder.options.setOnClickListener {
            listener?.onRecyclerViewClickListener(it, item)
        }
    }

    override fun getItemCount() = dateItems.size
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var userText = view.findViewById<TextView>(R.id.user_text)
    var userName = view.findViewById<TextView>(R.id.user_name)
    val options = view.findViewById<ImageView>(R.id.option_icon)

    fun bind(item: DateItem) {
        userName.text = item.userName
        userText.text = item.userText
    }
}

