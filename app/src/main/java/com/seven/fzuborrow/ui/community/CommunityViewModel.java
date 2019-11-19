package com.seven.fzuborrow.ui.community;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seven.fzuborrow.data.Disc;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommunityViewModel extends ViewModel {

    private MutableLiveData<List<Disc>> discs = new MutableLiveData<>();;

     {
        Api.get().findAllDisc(User.getLoggedInUser().getToken(),"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findAllDiscResponse -> discs.setValue(findAllDiscResponse.getDiscList()));
    }

    public MutableLiveData<List<Disc>> getDiscs() {
        return discs;
    }
}