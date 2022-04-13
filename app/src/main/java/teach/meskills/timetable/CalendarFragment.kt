package teach.meskills.timetable

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import teach.meskills.timetable.databinding.CalendarFragmentBinding
import teach.meskills.timetable.date.DateFragment
import teach.meskills.timetable.holidays.HolidaysFragment
import kotlin.system.exitProcess

class CalendarFragment : Fragment() {

    lateinit var binding: CalendarFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalendarFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val actionBar: ActionBar? = (activity as MainActivity?)!!.supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

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
        when (item.itemId) {
            R.id.log_out -> {
                AUTH.signOut()
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, SignInFragment.newInstance())
                    .commit()
            }
            R.id.date -> {item.isVisible = false}
            android.R.id.home -> {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.attention))
                    setMessage(getString(R.string.exit_question))
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        exitProcess(0)
                    }

                    setNegativeButton(getString(R.string.no)) { _, _ ->
                        Toast.makeText(
                            requireContext(), getString(R.string.thank_you),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    setCancelable(true)
                }.create().show()
            }
        }
            return super.onOptionsItemSelected(item)
        }

        companion object {
            fun newInstance() = CalendarFragment()
        }
    }