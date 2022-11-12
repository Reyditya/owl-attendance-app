package id.owl.com.owlliveattendanceapp.views.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.owl.com.owlliveattendanceapp.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}