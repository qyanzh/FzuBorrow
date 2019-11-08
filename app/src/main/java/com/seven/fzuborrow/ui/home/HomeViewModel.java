package com.seven.fzuborrow.ui.home;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seven.fzuborrow.Constants;
import com.seven.fzuborrow.data.Good;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Good>> mGoods = new MutableLiveData<>();

    {
        getGoodsFromServer(Constants.GOOD_TYPE_ROOM);
    }

    private static final String TAG = "HomeViewModel";
    @SuppressLint("CheckResult")
    public void getGoodsFromServer(String type) {
        Api.get().findAllGood(User.getLoggedInUser().getToken(), type, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findAllGoodsResponse -> {
                    for (Good good : findAllGoodsResponse.getGoodList()) {
                        Log.d(TAG, "getGoodsFromServer: "+good.getName());
                    }
                    mGoods.setValue(findAllGoodsResponse.getGoodList());
                });
    }

    public LiveData<List<Good>> getGoods() {
        return mGoods;
    }
}