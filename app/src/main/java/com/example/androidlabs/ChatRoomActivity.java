package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {


    EditText messageTyped;
    Button sendBtn;
    Button receiveBtn;
    ListView theList;
    ArrayList<Message> messagesList = new ArrayList<>();
    MyOwnAdapter adapter;
    public static int ACTIVITY_VIEW_MESSAGE = 33;
    int positionClicked;

    SQLiteDatabase db;



    public void sendMessage(View view) {

        String text = messageTyped.getText().toString();

        if (!text.equals("")) {

            Message message = new Message(text, true);
            messagesList.add(message);
            theList.setAdapter(adapter);
            messageTyped.setText("");

            ContentValues newRowValue = new ContentValues();

            //put string message in the message column
            newRowValue.put(DatabaseOpener.COL_MESSAGE, message.getText());
            newRowValue.put(DatabaseOpener.COL_ID, message.getId());
            newRowValue.put(DatabaseOpener.COL_ISSEND, message.isSend());

            long newId = db.insert(DatabaseOpener.TABLE_NAME, null, newRowValue);
        }

        }//end

        public void receiveMessage(View view) {

            String text = messageTyped.getText().toString();

            if (!text.equals("")) {
                Message message = new Message(text, false);
                messagesList.add(message);
                theList.setAdapter(adapter);
                messageTyped.setText("");
                //add message to database and get new ID
                ContentValues newRowValue = new ContentValues();

                //put string message in the message column
                newRowValue.put(DatabaseOpener.COL_MESSAGE, message.getText());
                newRowValue.put(DatabaseOpener.COL_ID, message.getId());
                newRowValue.put(DatabaseOpener.COL_ISSEND, message.isSend());

                long newId = db.insert(DatabaseOpener.TABLE_NAME, null, newRowValue);
            }

        }//end

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        //Fields
        messageTyped = findViewById(R.id.messageTyped);
        theList = findViewById(R.id.theList);
        sendBtn = findViewById(R.id.sendBtn);
        receiveBtn = findViewById(R.id.receiveBtn);
        ImageView sendPic = findViewById(R.id.sendPic);
        TextView messageRow = findViewById(R.id.messageShow);
        ImageView receivePic = findViewById(R.id.receivePic);

        //Database
        DatabaseOpener dbOpener = new DatabaseOpener(this);
        db = dbOpener.getWritableDatabase();


        //Query results from database
        String [] columns = {DatabaseOpener.COL_ID, DatabaseOpener.COL_MESSAGE, DatabaseOpener.COL_ISSEND};
        Cursor results = db.query(DatabaseOpener.TABLE_NAME, columns,
                null, null, null, null, null);


        //Find column indices
        int idColIndex = results.getColumnIndex(DatabaseOpener.COL_ID);
        int messageColIndex = results.getColumnIndex(DatabaseOpener.COL_MESSAGE);
        int isSendIndex = results.getColumnIndex(DatabaseOpener.COL_ISSEND);


        //maybe if send btn then isSend true.
        //iterate over the results, return true if there is a next item:
        while(results.moveToNext()){

            long id = results.getLong(idColIndex);
            String message = results.getString(messageColIndex);
            int isSend = results.getInt(isSendIndex);

            messagesList.add(new Message(id, message, isSend==1 ));

        }
        Log.i("size",messagesList.size()+"");


        //Create adapter and send to list
        adapter = new MyOwnAdapter();
        theList.setAdapter(adapter);


        //listen for send button clicked
     /*   sendBtn.setOnClickListener(click -> {


            String message = messageTyped.getText().toString();

            //add message to database and get new ID
            ContentValues newRowValues = new ContentValues();

            //put string message in the message column
            newRowValues.put(DatabaseOpener.COL_MESSAGE, message);
            long newId = db.insert(DatabaseOpener.TABLE_NAME, null, newRowValues);

            //Create new message with new ID
            Message newMessage = new Message(newId, message, true);

            //add new message to list
            messagesList.add(newMessage);

            //update the listView
            adapter.notifyDataSetChanged();

            //clear the message field
            messageTyped.setText("");

            //show notification
            Toast.makeText(this, "sendBtn!", Toast.LENGTH_SHORT).show();

        });

        //listen for receive button clicked
        receiveBtn.setOnClickListener(click -> {

            String message = messageTyped.getText().toString();


            //Create new message with new ID
            Message newMessage = new Message(newId, message, false);

            //add new message to list
            messagesList.add(newMessage);

            //update the listView
            adapter.notifyDataSetChanged();

            //clear the message field
            messageTyped.setText("");

            String text = messageTyped.getText().toString();

            //add message to database and get new ID
            ContentValues newRowValue = new ContentValues();

            //put string message in the message column
            newRowValue.put(DatabaseOpener.COL_MESSAGE, message);
            newRowValue.put(DatabaseOpener.COL_ID, newMessage.getId());
            newRowValue.put(DatabaseOpener.COL_ISSEND, newMessage.isSend());

            long newId = db.insert(DatabaseOpener.TABLE_NAME, null, newRowValue);



            //show notification
            Toast.makeText(this, "receiveBtn!", Toast.LENGTH_SHORT).show();

        });*/


    }//End onCreate

    protected class MyOwnAdapter extends BaseAdapter {



        @Override
        public int getCount() {

            return messagesList.size();

        }

        @Override
        public Message getItem(int position){

            return messagesList.get(position);

        }

        @Override
        public long getItemId(int position) {

            return position;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.single_row, parent, false);

            Message thisMessageRow = getItem(position);
            ImageView sendPic = newView.findViewById(R.id.sendPic);
            TextView messageRow = newView.findViewById(R.id.messageShow);
            ImageView receivePic = newView.findViewById(R.id.receivePic);

            messageRow.setText(thisMessageRow.getText());

            if (thisMessageRow.isSend() == true) {
                receivePic.setVisibility(View.INVISIBLE);
                messageRow.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
            else sendPic.setVisibility(View.INVISIBLE);

            return newView;

        }

    }//End MyOwnAdapter


    public void printCursor(Cursor c){

    }

    public void saveMessage(){

    }

}