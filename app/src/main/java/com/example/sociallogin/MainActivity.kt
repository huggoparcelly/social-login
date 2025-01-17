package com.example.sociallogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val emailField: TextInputEditText by lazy { findViewById(R.id.emailEditField) }
    private val emailFieldLayout: TextInputLayout by lazy { findViewById(R.id.emailTextField) }
    private val passwordField: TextInputEditText by lazy { findViewById(R.id.passwordEditField) }
    private val passwordFieldLayout: TextInputLayout by lazy { findViewById(R.id.passwordTextField) }
    private val loginButton: Button by lazy { findViewById(R.id.loginButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textWatcher = getTextWatcher()
        emailField.addTextChangedListener(textWatcher)
        passwordField.addTextChangedListener(textWatcher)

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
}