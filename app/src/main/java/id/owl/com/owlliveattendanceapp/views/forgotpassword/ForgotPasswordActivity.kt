package id.owl.com.owlliveattendanceapp.views.forgotpassword

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import com.google.gson.Gson
import id.owl.com.owlliveattendanceapp.R
import id.owl.com.owlliveattendanceapp.databinding.ActivityForgotPasswordBinding
import id.owl.com.owlliveattendanceapp.dialog.MyDialog
import id.owl.com.owlliveattendanceapp.model.ForgotPasswordResponse
import id.owl.com.owlliveattendanceapp.networking.ApiClient
import id.owl.com.owlliveattendanceapp.networking.ApiServices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
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

    private fun onClick() {
        binding.apply {
            back.setOnClickListener {
                finish()
            }

            cvForgotPassword.setOnClickListener {
                val email = binding.edtEmailForgotPassword.text.toString()
                if (isFormValid(email)) {
                    forgotPassToServer(email)
                }
            }
        }
    }

    private fun forgotPassToServer(email: String) {
        val forgotPasswordRequest = ForgotPasswordRequest(email = email)
        val forgotPasswordRequestString = Gson().toJson(forgotPasswordRequest)

        MyDialog.showProgressDialog(this)
        ApiServices.getAttendanceServices()
            .forgotPasswordRequest(forgotPasswordRequestString)
            .enqueue(object : Callback<ForgotPasswordResponse>{
                override fun onResponse(
                    call: Call<ForgotPasswordResponse>,
                    response: Response<ForgotPasswordResponse>
                ) {
                    MyDialog.hideDialog()
                    if (response.isSuccessful){
                        val message = response.body()?.message
                        MyDialog.dynamicDialog(
                            this@ForgotPasswordActivity,
                            getString(R.string.success),
                            message.toString()
                        )
                        Handler(Looper.getMainLooper()).postDelayed({
                            MyDialog.hideDialog()
                            finish()
                        },2000)
                    }else {
                        val errorConverter: Converter<ResponseBody, ForgotPasswordResponse> =
                            ApiClient
                                .getClient()
                                .responseBodyConverter(
                                    ForgotPasswordResponse::class.java,
                                    arrayOfNulls<Annotation>(0)
                                )
                        var errorResponse: ForgotPasswordResponse?
                        try {
                            response.errorBody()?.let {
                                errorResponse = errorConverter.convert(it)
                                MyDialog.dynamicDialog(
                                    this@ForgotPasswordActivity, "Failed to Send E-mail, please enter correct E-mail",
                                    errorResponse?.message.toString()
                                )
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.e(TAG, "Error: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                    MyDialog.hideDialog()
                    Log.e(TAG, "Error: ${t.message}")
                }

            })
    }

    private fun isFormValid(email: String): Boolean {
        if (email.isEmpty()) {
            binding.edtEmailForgotPassword.error = getString(R.string.please_field_your_email)
            binding.edtEmailForgotPassword.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmailForgotPassword.error = getString(R.string.please_use_valid_email)
            binding.edtEmailForgotPassword.requestFocus()
        } else {
            binding.edtEmailForgotPassword.error = null
            return true
        }

        return false
    }

    companion object{
        private val TAG = ForgotPasswordActivity::class.java.simpleName
    }

    private fun init() {

    }
}