package teach.meskills.timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import teach.meskills.timetable.holidays.HolidaysEntity

class RecyclerAdapter : RecyclerView.Adapter<AudioViewHolder>(){

    var holidays: List<HolidaysEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        return AudioViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val item = holidays[position]
        holder.input(item)
    }

    override fun getItemCount() = holidays.size
}

class AudioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val holidaysName = view.findViewById<TextView>(R.id.name)
    val descriptions = view.findViewById<TextView>(R.id.artist)


    fun input(holiday: HolidaysEntity) {
        holidaysName.text = holiday.holidaysName
        descriptions.text = holiday.descriptions
    }
}