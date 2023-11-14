package com.example.m_hike;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MHikeListAdapter extends BaseAdapter {

    final List<MHike> listMHike;

    MHikeListAdapter(List<MHike> listMHike) {
        this.listMHike = listMHike;
    }

    @Override
    public int getCount() {
        return listMHike.size();
    }

    @Override
    public Object getItem(int position) {
        return listMHike.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listMHike.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewMHike;
        if (convertView == null) {
            viewMHike = View.inflate(parent.getContext(), R.layout.listhikem, null);
        } else viewMHike = convertView;

        MHike mHike = (MHike) getItem(position);
        ((TextView) viewMHike.findViewById(R.id.idHikeManager)).setText(String.format("ID = %d", mHike.id));
        ((TextView) viewMHike.findViewById(R.id.NameHikeManager)).setText(String.format("Hike Name : %s", mHike.name));
        ((TextView) viewMHike.findViewById(R.id.DateOfHike)).setText(String.format("date %s", mHike.dateOfHike));

        return viewMHike;
    }
}
