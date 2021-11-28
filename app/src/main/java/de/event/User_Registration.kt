package de.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_registration.*

class User_Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
        val database = FirebaseAuth.getInstance()
        fun signIn(email: String, password: String) {
            database.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "Successful registration!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

        }
        sign_up_regform.setOnClickListener()
        {
            val email = editTextTextEmailAddress_regform.text.toString()
            val password = editTextTextPassword_regform.text.toString()
            val password_confirm = editTextTextPassword_regform_confirm.text.toString()
            val name = editTextTextPersonName_regform.text.toString()
            val surname = editTextTextPersonSurname_regform.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty() && password_confirm.isNotEmpty() && name.isNotEmpty() && surname.isNotEmpty())
            {
                signIn(email, password)
            }
        }
    }
}