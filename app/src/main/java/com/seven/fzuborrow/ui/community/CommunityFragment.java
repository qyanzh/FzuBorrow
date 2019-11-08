package com.seven.fzuborrow.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.seven.fzuborrow.R;
import com.seven.fzuborrow.ui.community.publish.PublishDiscussActivity;

public class CommunityFragment extends Fragment {

    private CommunityViewModel discViewModel;

    private DiscAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discViewModel =
                ViewModelProviders.of(this).get(CommunityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_community, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new DiscAdapter(disc -> {
           //TODO:帖子详情
        });
        recyclerView.setAdapter(adapter);
        root.findViewById(R.id.bt_send_tiezi).setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), PublishDiscussActivity.class);
            startActivity(intent);
        });
        subscribeUi();
        return root;
    }

    private void subscribeUi() {

        discViewModel.getDiscs().observe(this,discs -> {
            adapter.submitList(discs);
        });
    }

    private static final String TAG = "DashboardFragment";
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}