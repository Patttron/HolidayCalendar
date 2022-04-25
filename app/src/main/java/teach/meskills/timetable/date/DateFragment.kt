package teach.meskills.timetable.date

import android.app.*
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import teach.meskills.timetable.*
import teach.meskills.timetable.R
import teach.meskills.timetable.databinding.DateFragmentBinding
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class DateFragment : BaseFragment(), RecyclerViewClickListener {

    lateinit var adapter: DateAdapter
    private lateinit var binding: DateFragmentBinding
    private val dateViewModel by viewModel<DateViewModel>()
    var year = 0
    var month = 0
    var day = 0
    lateinit var nodeKey: String
    private var nodeFlag = false


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
        nodeKey = ROOT_REFERENCE.push().key.toString()
        adapter = DateAdapter()
        adapter.listener = this
        year = requireArguments().getInt(YEAR_KEY)
        month = requireArguments().getInt(MONTH_KEY) + 1
        day = requireArguments().getInt(DAY_KEY)
        val actionBar: ActionBar? = (activity as MainActivity?)!!.supportActionBar
        actionBar?.title = "$day-$month-$year"
        binding.save.setOnClickListener {
            if (binding.editTextMessage.text.isNotEmpty()) {
                if (!nodeFlag) {
                    nodeKey = ROOT_REFERENCE.push().key.toString()
                }
                ROOT_REFERENCE.child(nodeKey).setValue(
                    DateItem(
                        AUTH.currentUser?.displayName,
                        binding.editTextMessage.text.toString(),
                        AUTH.currentUser?.uid,
                        nodeKey, year, month, day
                    )
                )
                binding.editTextMessage.text.clear()
                nodeFlag = false
            } else {
                showToast(getString(R.string.enter_text))
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.dateRecycler.adapter = adapter
        binding.dateRecycler.layoutManager = layoutManager
        dateViewModel.getEventsForDate(year, month, day)
        dateViewModel.dateItemLiveData.observe(viewLifecycleOwner) {
            adapter.dateItems = it
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRecyclerViewClickListener(view: View, dateItem: DateItem) {
        val options = arrayOf<CharSequence>(
            "Edit",
            "Delete",
            "Add a notice",
            "Cancel"
        )
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(getString(R.string.options))
        builder.setItems(
            options
        ) { _, which ->
            when (which) {
                0 -> {
                    binding.editTextMessage.setText(
                        dateItem.userText
                    )
                    nodeFlag = true
                    nodeKey = dateItem.id.toString()
                }
                1 -> {
                    dateViewModel.deleteItem(dateItem)
                }
                2 -> {
                    nodeKey = dateItem.id.toString()
                    notification()
                }
            }
        }
        builder.show()
    }

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notice)
            val descriptionText = getString(R.string.description_notice)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(NotificationWorker.CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun createNotification(context: Context): Notification {
        val pIntent = PendingIntent.getActivity(
            context,
            NotificationWorker.CONTENT_REQUEST_CODE,
            MainActivity.startDateFragment(year, month, day),
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, NotificationWorker.CHANNEL_ID)
            .setContentTitle(getString(R.string.attention))
            .setContentText(getString(R.string.you_have_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_baseline_notifications)
            .setAutoCancel(true)
            .setContentIntent(pIntent)
            .build()
        createChannel(context)
        return notification
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun notification() {
        val beginTime = LocalDateTime.of(year, month, day, 8, 30)
        val endTime = LocalDateTime.of(year, month, day, 10, 0)
        val timeInterval = ChronoUnit.MILLIS.between(beginTime, endTime)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()
        val myWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .addTag(nodeKey)
            .setInitialDelay(timeInterval, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
    }

    companion object {
        private const val YEAR_KEY = "year"
        private const val MONTH_KEY = "month"
        private const val DAY_KEY = "day"

        fun newInstance(year: Int, month: Int, day: Int) = DateFragment().apply {
            arguments = Bundle().apply {
                putInt(YEAR_KEY, year)
                putInt(MONTH_KEY, month)
                putInt(DAY_KEY, day)
            }
        }
    }
}
