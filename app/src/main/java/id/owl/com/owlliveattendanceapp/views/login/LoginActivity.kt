package id.owl.com.owlliveattendanceapp.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.owl.com.owlliveattendanceapp.R
import id.owl.com.owlliveattendanceapp.databinding.ActivityLoginBinding
import id.owl.com.owlliveattendanceapp.views.attendance.AttendanceActivity
import id.owl.com.owlliveattendanceapp.views.forgotpassword.ForgotPasswordActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick() {
        binding.apply {
            cvLogin.setOnClickListener {
                startActivity<AttendanceActivity>()
            }
            txt_forgot_password.setOnClickListener {
                startActivity<ForgotPasswordActivity>()
            }
        }
    }
}