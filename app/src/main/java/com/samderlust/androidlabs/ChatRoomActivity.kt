package com.samderlust.androidlabs

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    lateinit var chatList: ArrayList<Message>
    lateinit var chatAdapter: ChatAdapter
    var messID: Long = 0

    lateinit var dbOpener: MessagesDB
    lateinit var db: SQLiteDatabase
    lateinit var newRow: ContentValues
    lateinit var messageText: EditText

    fun onSend(v: View) {
        if (messageText.text.toString() == "") return

        messID = chatList.size.toLong()

        val message = Message(messID, messageText.text.toString(), true)
        chatList.add(message)

        newRow.put(MessagesDB.TEXT, message.text)
        newRow.put(MessagesDB.SENDER, message.isSend)

        val newId = db.insert(MessagesDB.TABLE_NAME, null, newRow)
//        db.update()

//        chatListView.adapter = chatAdapter
        chatAdapter.notifyDataSetChanged()
        messageText.setText("")
    }

    fun onReceive(v: View) {
        if (messageText.text.toString() == "") return


        messID = chatList.size.toLong()

        val message = Message(messID, messageText.text.toString(), false)
        chatList.add(message)

        newRow.put(MessagesDB.TEXT, message.text)
        newRow.put(MessagesDB.SENDER, message.isSend)

        val newId = db.insert(MessagesDB.TABLE_NAME, null, newRow)

//        chatListView.adapter = chatAdapter
        chatAdapter.notifyDataSetChanged()

        messageText.setText("")
    }

    fun printCursor(c: Cursor) {
        println("### Number of results " + c.count)
        println("### Number of Column: " + c.columnCount)

        for (i in 0 until c.columnCount) {
            println("### Name of Column: " + c.getColumnName(i))
        }
        val textIndex = c.getColumnIndex(MessagesDB.TEXT)
        val idIndex = c.getColumnIndex(MessagesDB.ID)
        val isSendIndex = c.getColumnIndex(MessagesDB.SENDER)

        while (c.moveToNext()) {
            var text = c.getString(textIndex)
            var id = c.getLong(idIndex)
            var isSend = c.getInt(isSendIndex)

            println(
                "ID: " + id
                        + " TEXT: " + text
                        + " isSEND: " + isSend
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        chatList = ArrayList()
        chatAdapter = ChatAdapter(this, chatList)
        messageText = findViewById(R.id.messageText)

        dbOpener = MessagesDB(applicationContext)
        db = dbOpener.writableDatabase
        newRow = ContentValues()


        var result: Cursor = db.rawQuery("SELECT * FROM  " + MessagesDB.TABLE_NAME, null)

        println("### Number of results on Create" + result.count)

        val textIndex = result.getColumnIndex(MessagesDB.TEXT)
        val idIndex = result.getColumnIndex(MessagesDB.ID)
        val isSendIndex = result.getColumnIndex(MessagesDB.SENDER)


        while (result.moveToNext()) {
            var text = result.getString(textIndex)
            var id = result.getLong(idIndex)
            var isSend = result.getInt(isSendIndex)

            chatList.add(Message(id, text, isSend == 1))
        }
        chatAdapter = ChatAdapter(this, chatList)

        chatListView.adapter = chatAdapter
        printCursor(result)

    }

    override fun onPause() {
        super.onPause()
    }
}

class Message(val id: Long, val text: String, val isSend: Boolean) {

}