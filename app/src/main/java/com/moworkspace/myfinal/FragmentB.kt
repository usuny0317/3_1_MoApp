package com.moworkspace.myfinal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.moworkspace.myfinal.R
import kotlinx.android.synthetic.main.fragment_b.*
import kotlinx.android.synthetic.main.fragment_b.view.*
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var setday=""

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentB.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentB : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val adapter=commentAdapter()
    val databaseRef = FirebaseDatabase.getInstance().reference

    val cal=Calendar.getInstance()
    val year=cal.get(Calendar.YEAR)
    val month=cal.get(Calendar.MONTH)+1
    val day= cal.get(Calendar.DAY_OF_MONTH)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)

        if (setday==""){ setday = "0"+month.toString()+day.toString() }

        var today= ""
        if(yea==0){today=year.toString()+setday}
        else{today=yea.toString()+ setday}
        view.showday.setText(today)
        view.showday.setOnClickListener {
            Toast.makeText(context, "설정한 날짜는 "+ today +"입니다.", Toast.LENGTH_SHORT).show()
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter=adapter

        saveButton.setOnClickListener {
            val author = input1.text.toString()
            val contents=input2.text.toString()
            saveComment(author, contents)
        }

        databaseRef.orderByKey().limitToFirst(10).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                loadCommentList(snapshot)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("test", "loadItem:onCancelled: ${error.toException()}")
            }
        })
    }

    fun loadCommentList(dataSnapshot: DataSnapshot){
        val collectionIterator= dataSnapshot!!.children.iterator()
        if(collectionIterator.hasNext()){
            adapter.items.clear()
            val comments = collectionIterator.next()
            val itemsIterator=comments.children.iterator()
            while(itemsIterator.hasNext()){
                val currentItem = itemsIterator.next()
                val map = currentItem.value as HashMap<String, Any>
                val objectId=map["objectId"].toString()
                val author= map["author"].toString()
                var commentTime= Date(map["timestamp"]as Long).toString()
                val contents = map["contents"] as String

                val dat= setday
                if (author==dat){ adapter.items.add(comment(objectId, author, commentTime, contents))}

            }
            adapter.notifyDataSetChanged()
        }
    }
    fun saveComment(author:String, contents: String){
        var key : String? = databaseRef.child("comments").push().getKey()
        val comment= comment(key!!, author, "", contents)
        val commentValues: HashMap<String, Any> = comment.toMap()
        commentValues["timestamp"]= ServerValue.TIMESTAMP
        val childUpdates: MutableMap<String, Any> = HashMap()
        childUpdates["/comments/$key"]=commentValues
        databaseRef.updateChildren(childUpdates)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentB.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentB().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}