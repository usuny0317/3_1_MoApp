package com.moworkspace.myfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment.view.*

class commentAdapter : RecyclerView.Adapter<commentAdapter.ViewHolder>(){
    var items= ArrayList<comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): commentAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment,parent,false )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: commentAdapter.ViewHolder, position: Int) {
        var item= items[position]
        holder.setItem(item)
    }

    override fun getItemCount()=items.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun setItem(item: comment){
            itemView.idTextView.text=item.author
            itemView.idTextView2.text=item.time
            itemView.contentsTextView.text=item.contents
        }
    }
}