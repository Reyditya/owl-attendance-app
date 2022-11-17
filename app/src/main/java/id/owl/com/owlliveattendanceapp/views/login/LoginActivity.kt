package id.owl.com.owlliveattendanceapp.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import id.owl.com.owlliveattendanceapp.R
import id.owl.com.owlliveattendanceapp.databinding.ActivityLoginBinding
import id.owl.com.owlliveattendanceapp.dialog.MyDialog
import id.owl.com.owlliveattendanceapp.hawkstorage.HawkStorage
import id.owl.com.owlliveattendanceapp.model.LoginResponse
import id.owl.com.owlliveattendanceapp.networking.ApiClient
import id.owl.com.owlliveattendanceapp.networking.ApiServices
import id.owl.com.owlliveattendanceapp.views.attendance.AttendanceActivity
import id.owl.com.owlliveattendanceapp.views.forgotpassword.ForgotPasswordActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.jetbrains.anko.startActivity
import retrofit2.*
import java.io.IOException

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
                val email = binding.edtEmailLogin.text.toString()
                val password = binding.edtPasswordLogin.text.toString()
                if (isFormValid(email, password)) {
                    loginToServer(email, password)
                }

            }
            txt_forgot_password.setOnClickListener {
                startActivity<ForgotPasswordActivity>()
            }
        }
    }

    private fun loginToServer(email: String, password: String) {
        val requestLogin = LoginRequest(email = email, password = password, deviceName = "mobile")
        val requestLoginString = Gson().toJson(requestLogin)
        MyDialog.showProgressDialog(this)

        ApiServices.getAttendanceServices()
            .loginRequest(requestLoginString)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    MyDialog.hideDialog()
                    if (response.isSuccessful) {
                        val user = response.body()?.user
                        val token = response.body()?.meta?.token
                        if (user != null && token != null) {
                            HawkStorage.instance(this@LoginActivity).setUser(user)
                            HawkStorage.instance(this@LoginActivity).setToken(token)
                            goToAttendanceActivity()
                        }
                    } else {
                        val errorConverter: Converter<ResponseBody, LoginResponse> =
                            ApiClient
                                .getClient()
                                .responseBodyConverter(
                                    LoginResponse::class.java,
                                    arrayOfNulls<Annotation>(0)
                                )
                        var errorResponse: LoginResponse?
                        try {
                            response.errorBody()?.let {
                                errorResponse = errorConverter.convert(it)
                                MyDialog.dynamicDialog(
                                    this@LoginActivity, "Failed to Login, enter correct Email and Password",
                                errorResponse?.message.toString()
                                )
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.e(TAG, "Error: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    MyDialog.hideDialog()
                    Log.e(TAG, "Error: ${t.message}")
                }

            })

    }

    private fun goToAttendanceActivity() {
        startActivity<AttendanceActivity>()
        finishAffinity()
    }

    private fun isFormValid(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.edtEmailLogin.error = getString(R.string.please_field_your_email)
            binding.edtEmailLogin.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmailLogin.error = getString(R.string.please_use_valid_email)
            binding.edtEmailLogin.requestFocus()
        } else if (password.isEmpty()) {
            binding.edtEmailLogin.error = null
            binding.edtPasswordLogin.error = getString(R.string.please_field_your_password)
            binding.edtPasswordLogin.requestFocus()
        } else {
            binding.edtEmailLogin.error = null
            binding.edtPasswordLogin.error = null
            return true
        }
        return false
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }
}