package teach.meskills.timetable

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import teach.meskills.timetable.databinding.CalendarFragmentBinding
import teach.meskills.timetable.date.DateFragment
import teach.meskills.timetable.holidays.HolidaysFragment

class CalendarFragment: Fragment() {

    lateinit var binding: CalendarFragmentBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalendarFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        setHasOptionsMenu(true)
        binding.holidays.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, HolidaysFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, DateFragment.newInstance(year, month, dayOfMonth))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.log_out) {
            auth.signOut()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, SignInFragment.newInstance())
                .commit()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }
}