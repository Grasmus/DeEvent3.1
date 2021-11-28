package de.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.ArrayList
import androidx.recyclerview.widget.LinearLayoutManager

class ChatActivity : AppCompatActivity() {

    lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        val database = Firebase.database("https://deevent3-1-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRef = database.getReference("message")
        bSend.setOnClickListener {
            myRef.child(myRef.push().key ?: "blabla").setValue(User("User", edMessage.text.toString()))
            edMessage.text.clear()
        }
        onChangeListener(myRef)
        initRcView()

        imageView3.setOnClickListener {
            val intent = Intent(this, main_page::class.java)
            startActivity(intent)
        }

    }
    private fun initRcView() {
        adapter = UserAdapter()
        rcView.layoutManager = LinearLayoutManager(this@ChatActivity)
        rcView.adapter = adapter
    }
    private fun onChangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()
                for (s in snapshot.children) {
                    val user = s.getValue(User::class.java)
                    if (user != null) list.add(user)
                }
                adapter.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}