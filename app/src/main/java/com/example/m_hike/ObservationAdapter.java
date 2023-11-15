package com.example.m_hike;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ObservationAdapter extends BaseAdapter {

    final List<ObservationModel> listObservation;

    ObservationAdapter(List<ObservationModel> listObservation) {
        this.listObservation = listObservation;
    }

    @Override
    public int getCount() {
        return listObservation.size();
    }

    @Override
    public Object getItem(int position) {
        return listObservation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listObservation.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewObservation;
        if (convertView == null) {
            viewObservation = View.inflate(parent.getContext(), R.layout.listobservation, null);
        } else viewObservation = convertView;

        ObservationModel observationModel = (ObservationModel) getItem(position);
        ((TextView) viewObservation.findViewById(R.id.idObservation)).setText(String.format("ID = %d", observationModel.id));
        ((TextView) viewObservation.findViewById(R.id.nameObservation)).setText(String.format("Observation Name : %s", observationModel.observation));
        ((TextView) viewObservation.findViewById(R.id.dateTimeObservation)).setText(String.format("date %s", observationModel.dateTime));

        return viewObservation;
    }
}
