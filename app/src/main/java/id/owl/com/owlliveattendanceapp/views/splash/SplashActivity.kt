package id.owl.com.owlliveattendanceapp.views.splash

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import id.owl.com.owlliveattendanceapp.R
import id.owl.com.owlliveattendanceapp.views.login.LoginActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splash()
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

    private fun splash() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity<LoginActivity>()
            finishAffinity()
        }, 2000)
    }
}