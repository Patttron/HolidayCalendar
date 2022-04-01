package teach.meskills.timetable.date

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import teach.meskills.timetable.*
import teach.meskills.timetable.R
import teach.meskills.timetable.databinding.DateFragmentBinding
import teach.meskills.timetable.holidays.HolidaysViewModel

class DateFragment : BaseFragment(), RecyclerViewClickListener {
    lateinit var auth: FirebaseAuth
    lateinit var adapter: DateAdapter
    private lateinit var binding: DateFragmentBinding
    private val viewModel by viewModel<DateViewModel>()
    val myRef = Firebase.database.getReference("message")
//    private val cast: CustomObj by lazy { arguments?.getSerializable("key") as CustomObj}

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
//            myRef.child(auth.currentUser?.uid.toString())
                    .setValue(
                        DateItem(
                            auth.currentUser?.displayName,
                            binding.editTextMessage.text.toString()
                        )
                    )
                binding.editTextMessage.text.clear()
            } else {
                showToast("Enter some text")
            }
        }

       viewModel.onChangeListener(myRef)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.dateRecycler.adapter = adapter
        binding.dateRecycler.layoutManager = layoutManager

        viewModel.dateItemLiveData.observe(viewLifecycleOwner){
            adapter.dateItems = it
        }
    }


//    fun onChangeListener(dRef: DatabaseReference) {
//        dRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = ArrayList<DateItem>()
//                for (s in snapshot.children) {
//                    val item = s.getValue(DateItem::class.java)
//                    if (item != null)
//                        list.add(item)
//                }
//                adapter.dateItems = list
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })
//    }

    companion object {
        fun newInstance() = DateFragment()
//        fun newInstance(date: Long) = DateFragment().apply {
//            arguments = bundleOf("date" to date)
//        arguments = Bundle().apply {
//            putSeriala()
//            putString()
    }

    override fun onRecyclerViewClickListener(view: View, dateItem: DateItem) {

        view.findViewById<ImageView>(R.id.delete_icon).setOnClickListener {

            myRef.removeValue()
        }

//        when (view.id) {
//            R.id.delete_icon -> {
//                EditAuthorDialogFragment(author).show(childFragmentManager, "")
//            }
//            R.id.button_delete -> {
//                AlertDialog.Builder(requireContext()).also {
//                    it.setTitle(getString(R.string.delete_confirmation))
//                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
//                        viewModel.deleteAuthor(author)
//                    }
//                }.create().show()
//            }
//        }
    }
}
