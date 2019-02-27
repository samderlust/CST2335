package com.samderlust.androidlabs

import android.content.Context
import android.opengl.Visibility
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ChatAdapter(val c: Context, val chatList: ArrayList<Message>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.message_row, parent,false)
        val chatText = view.findViewById(R.id.messageText) as TextView
        val girlPic = view.findViewById<ImageView>(R.id.girlPic)
        val boyPic = view.findViewById<ImageView>(R.id.boyPic)

        val message = chatList.get(position)

        chatText.text = message.text
        if (!message.isSend) {
            boyPic.visibility = View.INVISIBLE
            chatText.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }
        else{
            girlPic.visibility = View.INVISIBLE
            chatText.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return chatList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return chatList.size
    }

}