package com.herokuapp.ezhao.penciled.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.herokuapp.ezhao.penciled.R;
import com.herokuapp.ezhao.penciled.models.PencilPerson;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PencilListAdapter extends ArrayAdapter<PencilPerson>{

    public static class ViewHolder {
        @InjectView(R.id.tvContactName) TextView tvContactName;
        @InjectView(R.id.tvEmailAddress) TextView tvEmailAddress;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public PencilListAdapter(Context context, ArrayList<PencilPerson> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        PencilPerson pencilPerson = getItem(position);
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.tvContactName.setText(pencilPerson.getName());
        viewHolder.tvEmailAddress.setText(pencilPerson.getEmail());
        return convertView;
    }
}
