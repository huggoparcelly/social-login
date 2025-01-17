package com.example.sociallogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val emailField: TextInputEditText by lazy { findViewById(R.id.emailEditField) }
    private val emailFieldLayout: TextInputLayout by lazy { findViewById(R.id.emailTextField) }
    private val passwordField: TextInputEditText by lazy { findViewById(R.id.passwordEditField) }
    private val passwordFieldLayout: TextInputLayout by lazy { findViewById(R.id.passwordTextField) }
    private val loginButton: Button by lazy { findViewById(R.id.loginButton) }
    private val contextView: View by lazy { findViewById(R.id.main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textWatcher = getTextWatcher()
        emailField.addTextChangedListener(textWatcher)
        passwordField.addTextChangedListener(textWatcher)
        login()

    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isEmailNotEmpty = emailField.text?.isNotEmpty() == true && Patterns.EMAIL_ADDRESS.matcher(emailField.text.toString()).matches()
                val isPasswordNotEmpty = passwordField.text?.isNotEmpty() == true

                resetFields()

                loginButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty

                if(loginButton.isEnabled) {
                    loginButton.setBackgroundColor(getColor(R.color.brown))
                } else {
                    loginButton.setBackgroundColor(getColor(R.color.brown_light))
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        }

    }

    private fun resetFields() {
        emailFieldLayout.isErrorEnabled = false
        emailFieldLayout.setStartIconTintList(getColorStateList(R.color.default_color))

        passwordFieldLayout.isErrorEnabled = false
        passwordFieldLayout.setStartIconTintList(getColorStateList(R.color.default_color))
    }

    private fun login() {
        loginButton.setOnClickListener {
            if( checkEmail() && checkPassword() ) {
                Snackbar.make(contextView, R.string.login_succeeded, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.green_light))
                    .setTextColor(getColor(R.color.black))
                    .show()
            }
        }
    }

    private fun checkEmail(): Boolean {
        val email = emailField.text.toString().trim()
        val isValidEmail = isValidEmail(email)
        if(!isValidEmail) {
            emailFieldLayout.setStartIconTintList(getColorStateList(R.color.red))
            emailFieldLayout.error = getString(R.string.email_warning)
        }
        return isValidEmail
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkPassword(): Boolean {
        val password = passwordField.text.toString().trim()
        val isValidPassword = isValidPassword(password)
        if(!isValidPassword) {
            passwordFieldLayout.setStartIconTintList(getColorStateList(R.color.red))
            passwordFieldLayout.error = getString(R.string.password)
        }
        return isValidPassword
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }
}


