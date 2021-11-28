package de.event

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.rcView
import kotlinx.android.synthetic.main.activity_main_page.*
import java.util.ArrayList

class main_page : AppCompatActivity() {
    lateinit var adapter: EventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        val database = Firebase.firestore
        val docRef = database.collection("events")
        docRef.get()
            .addOnSuccessListener { result ->
                val list = ArrayList<Event>()
                 for (document in result) {
                     val event = Event(document.get("place").toString(), document.get("date").toString(), document.get("name").toString())
                     if (event != null) list.add(event)
                    }
                adapter.submitList(list)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        //onChangeListener(docRef)
        initRcView()
        goToChat.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initRcView() {
        adapter = EventAdapter()
        rcView.layoutManager = LinearLayoutManager(this@main_page)
        rcView.adapter = adapter
    }
   /* private fun onChangeListener(docRef: DatabaseReference){
        docRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Event>()
                for (s in snapshot.children) {
                    val event = s.getValue(Event::class.java)
                    if (event != null) list.add(event)
                }
                adapter.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
*/
}