package com.firstapp.calculatrice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Helper extends SQLiteOpenHelper {
    public static final String DBname="users.db";
    public static final String membersTable="Membres";

    public DB_Helper(@Nullable Context context) {

        super(context, DBname, null,2);

    }
/*context: This parameter represents the context in which the database helper is being created
  In Android development, a Context is an object that provides access to application-specific resources, system services, and the application's environment.
 1) Context allows you to access resources such as strings, colors, drawables, and layouts that are part of your Android application.
  *String appName = context.getString(R.string.app_name);
 2)Context can be used to start activities, services, or broadcast intents.
  *Intent intent = new Intent(context, AnotherActivity.class);
    context.startActivity(intent);
 3)When working with databases or reading/writing files, the Context is often required. It provides methods like openOrCreateDatabase() for database access and openFileInput() and openFileOutput() for file I/O
   *SQLiteDatabase database = context.openOrCreateDatabase("mydatabase", Context.MODE_PRIVATE, null);
_______________________________________________________________________________________________________________________________________________________________________________________________________________________________

The CursorFactory is responsible for creating cursor objects, which are essentially result sets obtained from executing queries on the database.
When you're creating a new database, you don't have any existing result sets, so there's no need for a custom CursorFactory.
The default behavior provided by Android's system is sufficient, and setting it to null signals that the default should be used.

________________________________________________________________________________________________________________________________________________________________________________________________________________________________

version parameter set to 1: When creating a new database or making changes to the database schema, you typically start with version 1.
If you later need to make modifications to the database structure, you can increment the version number. Setting the initial version to 1 is common practice.

  */

    // is called when DB is created for the first time. This method is responsible for executing the SQL statements that define the initial structure of DB: creation of tables, initial population of data.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
                // Define the SQL statement to create a table
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " +
                membersTable +"(login TEXT, password TEXT ,nom TEXT,prenom TEXT,statut INTEGER);";
        //statut : if =1 ==> adminsitarteur
        //         if =2 ==> user

        // Execute the SQL statement
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+membersTable);
       onCreate(sqLiteDatabase);
    }

    public void AddUser(String login,String password,String fname,String name,int status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("login", login);
        values.put("password", password);
        values.put("nom", fname);
        values.put("prenom", name);
        values.put("statut", status);
        db.insert(membersTable,"",values);

        db.close();
    }

    public ArrayList<Object> getPasswordByLogin(String login, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(membersTable,new String[]{"password"},"login=?", new String[]{login},null,null,null);
//The Cursor is a pointer to the result set of a query. It points to a specific row in the result set.
        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(null);

        if (cursor.moveToFirst()) {
            /*moveToFirst():
                Moves the cursor to the first row of the result set.
                Returns true if the cursor is not empty (i.e., there is at least one row), and false if the cursor is empty.
                Commonly used when you expect only one result or want to process the first row of a result set.
            */
            int passwordIndex = cursor.getColumnIndex("password");
            /*ATTENTION :
            If the column with the name "password" was not found in the result set(cuz name is
            misspelled or the column does not exist in the query result),cursor.getColumnIndex("password") will return -1.
            which will cause an excpetion so for that we might want to add a test
            */
            if (passwordIndex != -1)
                outputs.add(0,cursor.getString(passwordIndex));
        }
        cursor.close();
        cursor = db.query(membersTable,new String[]{"statut"},"login=?", new String[]{login},null,null,null);
        if (cursor.moveToFirst()) {
            int statutIndex = cursor.getColumnIndex("statut");
            if (statutIndex != -1)
                outputs.add(1, cursor.getString(statutIndex));

        }
        cursor.close();
        db.close();

        return outputs;
    }
}


