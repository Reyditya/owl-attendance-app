package id.owl.com.owlliveattendanceapp.views.attendance

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import id.owl.com.owlliveattendanceapp.R
import id.owl.com.owlliveattendanceapp.databinding.ActivityAttendanceBinding
import id.owl.com.owlliveattendanceapp.views.fragment.attendance.AttendanceFragment
import id.owl.com.owlliveattendanceapp.views.fragment.history.HistoryFragment
import id.owl.com.owlliveattendanceapp.views.fragment.profile.ProfileFragment

class AttendanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAttendanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setFlags()
    }

    private fun setFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun init() {
        binding.bnAttendance.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_history -> {
                    openFragment(HistoryFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_attendance -> {
                    openFragment(AttendanceFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    openFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
        openDefaultFragment()
    }

    private fun openDefaultFragment() {
        binding.bnAttendance.selectedItemId = R.id.action_attendance
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_attendance, fragment)
            .addToBackStack(null)
            .commit()
    }
}