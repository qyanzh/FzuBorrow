package com.seven.fzuborrow.ui.home;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seven.fzuborrow.Constants;
import com.seven.fzuborrow.data.Good;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Good>> mGoods = new MutableLiveData<>();

    {
        getGoodsFromServer(Constants.GOOD_TYPE_GOOD);
    }

    private static final String TAG = "HomeViewModel";

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

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
                },e->  {
                    if(type.equals(Constants.GOOD_TYPE_GOOD)) {
                        Toast.makeText(getApplication(), "没有空闲的物品", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "没有空闲的活动室", Toast.LENGTH_SHORT).show();
                    }
                    mGoods.setValue(new ArrayList<>());
                });
    }

    public LiveData<List<Good>> getGoods() {
        return mGoods;
    }
}