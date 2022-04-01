package teach.meskills.timetable.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WorkTimetableApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WorkTimetableApplication)
            modules(contentRepository, holidayViewModel, dateItemViewModel)
        }
    }
}