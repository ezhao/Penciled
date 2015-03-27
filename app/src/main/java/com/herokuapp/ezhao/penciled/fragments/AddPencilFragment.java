package com.herokuapp.ezhao.penciled.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.herokuapp.ezhao.penciled.R;
import com.herokuapp.ezhao.penciled.models.PencilPerson;
import com.parse.ParseException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddPencilFragment extends Fragment {
    public interface OnPencilAddedListener {
        public void closeAddPencilFragment();
    }
    private OnPencilAddedListener listener;

    @InjectView(R.id.btnAddPencil)
    Button btnAddPencil;

    @InjectView(R.id.etContactName)
    EditText etContactName;

    @InjectView(R.id.etEmailAddress)
    EditText etEmailAddress;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnPencilAddedListener) {
            listener = (OnPencilAddedListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement AddPencilFragment.OnPencilAddedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_pencil, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.btnAddPencil)
    public void submitPencil() {
        PencilPerson pencilPerson = new PencilPerson(etContactName.getText().toString(), etEmailAddress.getText().toString());
        try {
            pencilPerson.pin();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pencilPerson.saveEventually();
        listener.closeAddPencilFragment();
    }

}
