package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {


    EditText messageTyped;
    Button sendBtn;
    Button receiveBtn;
    ListView theList;
    ArrayList<Message> messagesList = new ArrayList<>();
    MyOwnAdapter adapter;
    SQLiteDatabase db;
    Cursor results;

    public void sendMessage(View view) {

        String text = messageTyped.getText().toString();

        if (!text.equals("")) {

            ContentValues newRowValue = new ContentValues();

            //put string message in the message column
            newRowValue.put(DatabaseOpener.COL_MESSAGE, text);

            //  newRowValue.put(DatabaseOpener.COL_ID, message.getId());
            newRowValue.put(DatabaseOpener.COL_ISSEND, true);

            long newId = db.insert(DatabaseOpener.TABLE_NAME, null, newRowValue);

            Message message = new Message(newId, text, true);

            messagesList.add(message);
            theList.setAdapter(adapter);
            messageTyped.setText("");

        }

        }//end

        public void receiveMessage(View view) {

            String text = messageTyped.getText().toString();

            if (!text.equals("")) {

                ContentValues newRowValue = new ContentValues();

                //put string message in the message column
                newRowValue.put(DatabaseOpener.COL_MESSAGE, text);

                //  newRowValue.put(DatabaseOpener.COL_ID, message.getId());
                newRowValue.put(DatabaseOpener.COL_ISSEND, false);

                long newId = db.insert(DatabaseOpener.TABLE_NAME, null, newRowValue);

                Message message = new Message(newId, text, false);

                messagesList.add(message);
                adapter.notifyDataSetChanged();
                messageTyped.setText("");


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


        //Database
        DatabaseOpener dbOpener = new DatabaseOpener(this);
        db = dbOpener.getWritableDatabase();


        //Query results from database
        String [] columns = {DatabaseOpener.COL_ID, DatabaseOpener.COL_MESSAGE, DatabaseOpener.COL_ISSEND};
        results = db.query(DatabaseOpener.TABLE_NAME, columns,
                null, null, null, null, null);


        //Find column indices
        int idColIndex = results.getColumnIndex(DatabaseOpener.COL_ID);
        int messageColIndex = results.getColumnIndex(DatabaseOpener.COL_MESSAGE);
        int isSendIndex = results.getColumnIndex(DatabaseOpener.COL_ISSEND);


        //iterate over the results, return true if there is a next item:
        while(results.moveToNext()){

            long id = results.getLong(idColIndex);
            String message = results.getString(messageColIndex);
            int isSend = results.getInt(isSendIndex);

            messagesList.add(new Message(id, message, isSend==1 ));

        }

        //Create adapter and send to list
        adapter = new MyOwnAdapter();
        theList.setAdapter(adapter);

        printCursor();

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


    public void printCursor() {

        Log.e("Count:", results.getCount() + "");
        Log.e("Database version:", db.getVersion() + "");
        Log.e("Number of columns:", results.getColumnCount() + "");
        Log.e("Name of the columns:", results.getColumnNames().toString());
        Log.e("Number of results", results.getCount() + "");
        Log.e("Row:", "");

        results.moveToFirst();

        for (int i = 0; i < results.getCount(); i++) {
            while (!results.isAfterLast()) {

                long id = results.getLong(2);
                boolean isSent = results.getInt(0) > 0;
                String message = results.getString(1);

                Log.e("id", id + "");
                Log.e("isSent", isSent + "");
                Log.e("message", message + "");

                results.moveToNext();

            }
        }

    }

}