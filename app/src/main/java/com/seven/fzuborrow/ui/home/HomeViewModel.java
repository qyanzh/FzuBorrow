package com.seven.fzuborrow.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seven.fzuborrow.data.Good;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Good>> mGoods;

    public HomeViewModel() {
        mGoods = new MutableLiveData<>();
        mGoods.setValue(getGoodsRandomly());
    }

    private List<Good> getGoodsRandomly() {
        List<Good> goods = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            goods.add(new Good(random.nextLong(), "物品" + i, "这是一个物品简介，长一点"));
        }
        return goods;
    }

    public LiveData<List<Good>> getGoods() {
        return mGoods;
    }
}