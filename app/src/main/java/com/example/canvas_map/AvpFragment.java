package com.example.canvas_map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AvpFragment extends Fragment {
    private MapView mapView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avp_fragment,container,false);
        mapView = view.findViewById(R.id.map_view);
        return view;
    }

    public void selectFloor(){
        mapView.selectView();
    }
}
