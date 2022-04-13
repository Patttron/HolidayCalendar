package teach.meskills.timetable.date

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import org.koin.core.component.KoinComponent
import androidx.work.*
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) : KoinComponent,
    Worker(context, params) {

    private val dateFragment = DateFragment()

    override fun doWork(): Result {
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(
                NOTIFICATION_ID, dateFragment.createNotification(applicationContext)
            )
        }
        return Result.success()
    }

    companion object {
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "channel_id"
        const val CONTENT_REQUEST_CODE = 11
        const val TAG = "worker"
    }
}