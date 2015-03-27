package com.herokuapp.ezhao.penciled.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.herokuapp.ezhao.penciled.R;
import com.herokuapp.ezhao.penciled.adapters.PencilListAdapter;
import com.herokuapp.ezhao.penciled.helpers.Helper;
import com.herokuapp.ezhao.penciled.models.PencilPerson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PencilListFragment extends Fragment {
    public interface OnAddPencilListener {
        public void switchToAddPencil();
    }
    private OnAddPencilListener listener;
    @InjectView(R.id.lvPencilList)
    ListView lvPencilList;
    PencilListAdapter pencilListAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnAddPencilListener) {
            listener = (OnAddPencilListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement PencilListFragment.OnAddPencilListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pencil_list, container, false);
        ButterKnife.inject(this, view);

        ArrayList<PencilPerson> pencilPeopleList = new ArrayList<>();
        pencilListAdapter = new PencilListAdapter((Context) listener, pencilPeopleList);
        lvPencilList.setAdapter(pencilListAdapter);

        ParseQuery<PencilPerson> query = ParseQuery.getQuery(PencilPerson.class);
        if (Helper.isNetworkAvailable((Context) listener)) {
            query.findInBackground(new FindCallback<PencilPerson>() {
                @Override
                public void done(List<PencilPerson> pencilPeople, ParseException e) {
                    try {
                        ParseObject.pinAll(pencilPeople);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    pencilListAdapter.addAll(pencilPeople);
                }
            });
        } else {
            query.fromLocalDatastore();
            query.findInBackground(new FindCallback<PencilPerson>() {
                @Override
                public void done(List<PencilPerson> pencilPeople, ParseException e) {
                    pencilListAdapter.addAll(pencilPeople);
                }
            });
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pencil_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_pencil) {
            listener.switchToAddPencil();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
