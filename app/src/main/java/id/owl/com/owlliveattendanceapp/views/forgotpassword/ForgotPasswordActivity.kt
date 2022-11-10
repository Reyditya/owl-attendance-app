package id.owl.com.owlliveattendanceapp.views.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.owl.com.owlliveattendanceapp.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()


    }

    private fun init(){

    }
}