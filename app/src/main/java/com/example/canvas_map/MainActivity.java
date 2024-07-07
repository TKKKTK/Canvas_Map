package com.example.canvas_map;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button select_btn;
    private Button select_fragment_btn;
    private List<Fragment> fragments;
    private int currentFragmentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        createFragments();
        select_btn = findViewById(R.id.slect_view);
        select_btn.setOnClickListener(view -> {
            AvpFragment avpFragment = (AvpFragment) fragments.get(0);
            avpFragment.selectFloor();
        });
        select_fragment_btn = findViewById(R.id.slect_fragment);
        select_fragment_btn.setOnClickListener(view -> {
            changeFragment();
        });
    }

    private void createFragments(){
        fragments = new ArrayList<>();
        AvpFragment avpFragment = new AvpFragment();
        SdFragment sdFragment = new SdFragment();
        fragments.add(avpFragment);
        fragments.add(sdFragment);
    }

    private void changeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.map_fragment,fragments.get(currentFragmentIndex));
        transaction.commit();
        currentFragmentIndex++;
        currentFragmentIndex = currentFragmentIndex == fragments.size() ? 0 : currentFragmentIndex;
    }
}