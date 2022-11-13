package id.owl.com.owlliveattendanceapp.views.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.owl.com.owlliveattendanceapp.databinding.ActivityChangePasswordBinding
import org.jetbrains.anko.startActivity

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick() {
        binding.apply {
            back.setOnClickListener {
                finish()
            }
        }
    }
}