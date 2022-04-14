package teach.meskills.timetable


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import teach.meskills.timetable.date.DateFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, SignInFragment())
                .commit()
        }
        if (intent.action == ACTION) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    DateFragment.newInstance(
                        intent.getIntExtra(YEAR_KEY, 0),
                        intent.getIntExtra(MONTH_KEY, 0),
                        intent.getIntExtra(DAY_KEY, 0)
                    ))
                        .commit()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.attention))
            setMessage(getString(R.string.exit_question))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                super.onBackPressed()
            }
            setNegativeButton(getString(R.string.no)) { _, _ ->
                Toast.makeText(
                    this@MainActivity, getString(R.string.thank_you),
                    Toast.LENGTH_SHORT
                ).show()
            }
            setCancelable(true)
        }.create().show()

    }

    companion object {
        private const val ACTION = "teach.meskills.timetable.startDateFragment"
        private const val YEAR_KEY = "year"
        private const val MONTH_KEY = "month"
        private const val DAY_KEY = "day"

        fun startDateFragment(year: Int, month: Int, day: Int): Intent {
            return Intent(ACTION).putExtra(YEAR_KEY, year)
                .putExtra(MONTH_KEY, month).putExtra(DAY_KEY, day)
        }
    }
}