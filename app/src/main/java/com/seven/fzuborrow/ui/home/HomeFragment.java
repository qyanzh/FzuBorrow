package com.seven.fzuborrow.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.seven.fzuborrow.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    GoodsAdapter goodsAdapter = new GoodsAdapter();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView;
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(goodsAdapter);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int[] lastVisibles = ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPositions(null);
                    int lastVisible = Math.max(lastVisibles[0], lastVisibles[1]);
                    if ( lastVisible + 1 == recyclerView.getAdapter().getItemCount()) {
                        Log.d("TAG", "到底了");
                    }
                }
            }

        });
        subscribeUi();
        return root;
    }

    private void subscribeUi() {
        homeViewModel.getGoods().observe(this, goods -> {
            goodsAdapter.submitList(goods);
        });
    }

    private static final String TAG = "HomeFragment";
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}