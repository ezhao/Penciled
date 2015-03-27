package com.herokuapp.ezhao.penciled;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.herokuapp.ezhao.penciled.fragments.AddPencilFragment;
import com.herokuapp.ezhao.penciled.fragments.PencilListFragment;


public class MainActivity extends ActionBarActivity implements PencilListFragment.OnAddPencilListener, AddPencilFragment.OnPencilAddedListener {
    private FragmentManager fragmentManager;
    private final String ADD_PENCIL_TAG = "Add Pencil";
    private PencilListFragment pencilListFragment;
    private AddPencilFragment addPencilFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Emily", "It has begun");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (pencilListFragment == null) {
            pencilListFragment = new PencilListFragment();
        }

        ft.replace(R.id.flFragmentPlaceholder, pencilListFragment);
        ft.commit();
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
    public void switchToAddPencil() {
        Log.i("EMILY", "switchToAddPencil called");
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (addPencilFragment == null) {
            addPencilFragment = new AddPencilFragment();
        }

        if (addPencilFragment.isAdded()) {
            ft.show(addPencilFragment);
        } else {
            ft.add(R.id.flFragmentPlaceholder, addPencilFragment);
            ft.addToBackStack(ADD_PENCIL_TAG);
        }

        if (pencilListFragment.isAdded()) {
            ft.hide(pencilListFragment);
        }
        ft.commit();
    }

    @Override
    public void closeAddPencilFragment() {
        Log.i("EMILY", "closeAddPencilFragment called");
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (pencilListFragment.isAdded()) {
            ft.show(pencilListFragment);
        } else {
            ft.add(R.id.flFragmentPlaceholder, pencilListFragment);
        }

        if (addPencilFragment.isAdded()) {
            fragmentManager.popBackStack(ADD_PENCIL_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        ft.commit();
    }
}
