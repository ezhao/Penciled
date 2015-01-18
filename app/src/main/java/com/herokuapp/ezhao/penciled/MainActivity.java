package com.herokuapp.ezhao.penciled;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

    private ListView lvContactsTest;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Emily", "It has begun");

        // Fetch views
        lvContactsTest = (ListView) findViewById(R.id.lvContactsTest);

        // Create Cursor
        String[] mProjection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        String mSelectionClause = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?";
        String[] mSelectionArgs = {"1"};
        String mSortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC";
        mCursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                mProjection,
                mSelectionClause,
                mSelectionArgs,
                mSortOrder
        );
        Log.i("Emily", "mCursor count " + mCursor.getCount());

        // Create CursorAdapter
        int[] mWordListItems = {
                R.id.tvContactName,
                R.id.ivContactImage,
                R.id.tvEmailAddress
        };
        String[] mContactCols = {
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts._ID
        };
        CursorAdapter mCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.contact_list_item,
                mCursor,
                mContactCols,
                mWordListItems,
                0
        );

        // Inflate
        lvContactsTest.setAdapter(mCursorAdapter);

        String mDataQuery = ContactsContract.Data.CONTACT_ID + "=?" +
                              " AND " +
                              ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE + "'";
        String contactId = "1";
        Cursor mDataContentResolver = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[] {ContactsContract.Data._ID,
                              ContactsContract.CommonDataKinds.Email.ADDRESS,
                              ContactsContract.CommonDataKinds.Email.TYPE
                              },
                mDataQuery,
                new String[] {String.valueOf(contactId)},
                null
        );
        Log.i("Emily", DatabaseUtils.dumpCursorToString(mDataContentResolver));

        Log.i("Emily", "End");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mCursor.close();
    }
}
