package com.firstapp.calculatrice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Helper extends SQLiteOpenHelper {
    public static final String DBname="users.db";
    public static final String TAB_members="Membres";

    public DB_Helper(@Nullable Context context) {
        super(context, DBname, null,1);
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
                TAB_members +"(column1_type column1_name, column2_type column2_name, ...);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
