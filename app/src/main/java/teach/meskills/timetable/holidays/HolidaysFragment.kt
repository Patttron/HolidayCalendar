package teach.meskills.timetable.holidays

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import teach.meskills.timetable.R
import teach.meskills.timetable.RecyclerAdapter
import teach.meskills.timetable.SignInFragment
import teach.meskills.timetable.databinding.MainFragmentBinding

class HolidaysFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding: MainFragmentBinding
    private val contentRepository = ContentRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val viewModel = HolidaysViewModel(contentRepository)
        val adapter = RecyclerAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = layoutManager
        viewModel.loadingData()
        setHasOptionsMenu(true)
        viewModel.holidays.observe(viewLifecycleOwner) {
            adapter.holidays = it
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
        fun newInstance() = HolidaysFragment()
    }
}