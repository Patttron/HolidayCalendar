package teach.meskills.timetable.holidays

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import teach.meskills.timetable.BaseFragment
import teach.meskills.timetable.databinding.HolidaysFragmentBinding

class HolidaysFragment : BaseFragment() {

    private lateinit var binding: HolidaysFragmentBinding
    private val viewModel by viewModel<HolidaysViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HolidaysFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecyclerAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.holidaysRecycler.adapter = adapter
        binding.holidaysRecycler.layoutManager = layoutManager
        viewModel.loadingData()
        viewModel.holidaysLiveData.observe(viewLifecycleOwner) {
            adapter.holidays = it
        }
    }

    companion object {
        fun newInstance() = HolidaysFragment()
    }
}