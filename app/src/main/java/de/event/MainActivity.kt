package de.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = Firebase.auth

        fun login(email: String, pass: String) {
            auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        password.text.clear()
                        login.text.clear()
                        val intent = Intent(this, main_page::class.java)
                        startActivity(intent)
                        Log.e("Task Message", "Good!")
                    } else {
                        Toast.makeText(this, "There is no such user!", Toast.LENGTH_SHORT).show()
                        Log.e("Task Message", "Failed..." + task)
                        password.text.clear()
                        login.text.clear()

                    }
                }
        }


        sign_in.setOnClickListener()
        {
            val login_var = login.text.toString()
            val password_var = password.text.toString()
            if(login_var.isNotEmpty() && password_var.isNotEmpty())  login(login_var, password_var)
            else
            {
                Toast.makeText(this, "Fields can`t be empty", Toast.LENGTH_SHORT).show()
            }

            //val intent = Intent(this, ChatActivity::class.java)
            //startActivity(intent)
        }

        sign_up.setOnClickListener{
             val intent = Intent(this, User_Registration::class.java)
            startActivity(intent)

        }

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //Toast.makeText(this, currentUser.toString(), Toast.LENGTH_SHORT).show()
        if(currentUser != null){
            //updateUI(currentUser)
        }
    }

    fun updateUI(currentUser: FirebaseUser)
    {
        if(currentUser != null)
        {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(this, "No any user", Toast.LENGTH_SHORT).show()
        }
    }
}