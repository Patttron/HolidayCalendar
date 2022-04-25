package teach.meskills.timetable.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import teach.meskills.timetable.ROOT_REFERENCE
import teach.meskills.timetable.date.BaseRepository
import teach.meskills.timetable.date.DateViewModel
import teach.meskills.timetable.holidays.ContentRepository
import teach.meskills.timetable.holidays.HolidaysViewModel
import teach.meskills.timetable.holidays.RetrofitContentRepository

val contentRepository = module {
    single<ContentRepository> { RetrofitContentRepository() }
}
val contentDate = module {
    single<BaseRepository> { BaseRepository(ROOT_REFERENCE) }
}

val holidayViewModel = module {
    viewModel {
        HolidaysViewModel(contentRepository = get())
    }
}

val dateItemViewModel = module {
    viewModel {
        DateViewModel(contentRepository = get(), contentBase = get())
    }
}