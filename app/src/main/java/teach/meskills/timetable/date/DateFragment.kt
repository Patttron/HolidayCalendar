package teach.meskills.timetable.date

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import teach.meskills.timetable.*
import teach.meskills.timetable.databinding.DateFragmentBinding

class DateFragment : BaseFragment(), RecyclerViewClickListener {
    lateinit var auth: FirebaseAuth
    lateinit var adapter: DateAdapter
    private lateinit var binding: DateFragmentBinding
    private val viewModel by viewModel<DateViewModel>()
    val myRef = Firebase.database.getReference("message")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        adapter = DateAdapter()
        adapter.listener = this

        binding.save.setOnClickListener {
            if (binding.editTextMessage.text.isNotEmpty()) {
                myRef.child(myRef.push().key ?: "some text")
                    .setValue(
                        DateItem(
                            auth.currentUser?.displayName,
                            binding.editTextMessage.text.toString(),
                            auth.currentUser?.uid,
                            arguments
                        )
                    )
                binding.editTextMessage.text.clear()
            } else {
                showToast("Enter some text")
            }
        }

        onChangeListener(myRef)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.dateRecycler.adapter = adapter
        binding.dateRecycler.layoutManager = layoutManager

//        viewModel.dateItemLiveData.observe(viewLifecycleOwner) {
//            adapter.dateItems = it
//        }
    }

    fun onChangeListener(dRef: DatabaseReference) {
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<DateItem>()
                for (s in snapshot.children) {
                    val item = s.getValue(DateItem::class.java)
                    if (item != null) {
                        item.date = arguments
                        item?.id = s.key
                        item?.let { list.add(item) }
                    }
                }
                adapter.dateItems = list
//                dateItemLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onRecyclerViewClickListener(view: View, dateItem: DateItem) {
        val options = arrayOf<CharSequence>(
            "Delete",
            "Cancel"
        )
        val builder = android.app.AlertDialog.Builder(view.context)
        builder.setTitle("Delete Content")
        builder.setItems(
            options
        ) { _, which ->
            if (which == 0) {
                viewModel.deleteItem(dateItem)
            }
        }
        builder.show()
    }

    companion object {

        fun newInstance(year: Int, month: Int, day: Int) = DateFragment().apply {
            arguments = Bundle().apply {
                putInt("year", year)
                putInt("month", month)
                putInt("day", day)
            }
        }
    }
}


// fun newInstance(date: Long) = DateFragment().apply {
//            arguments = bundleOf("date" to date)
//        arguments = Bundle().apply {