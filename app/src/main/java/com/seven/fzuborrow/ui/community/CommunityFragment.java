package com.seven.fzuborrow.ui.community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.seven.fzuborrow.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private CommunityViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(CommunityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_community, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new TieziAdapter());
        List<Object> list = new ArrayList<>();
        list.add("");
        list.add("1");
        list.add("2");
        list.add("3");
        ((TieziAdapter)recyclerView.getAdapter()).submitList(list);
        return root;
    }

    private static final String TAG = "DashboardFragment";
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}