package teach.meskills.timetable.holidays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import teach.meskills.timetable.R

class RecyclerAdapter : RecyclerView.Adapter<AudioViewHolder>(){

    var holidays: List<HolidaysEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        return AudioViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holiday_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val item = holidays[position]
        holder.input(item)
    }

    override fun getItemCount() = holidays.size
}

class AudioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val holidaysName = view.findViewById<TextView>(R.id.holidays_name)
    private val descriptions = view.findViewById<TextView>(R.id.holidays_description)
    private val date = view.findViewById<TextView>(R.id.date)
    

    fun input(holiday: HolidaysEntity) {
        holidaysName.text = holiday.holidaysName
        descriptions.text = holiday.descriptions
        date.text = holiday.dateIso
    }
}