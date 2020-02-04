package com.seven.fzuborrow.ui.community;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seven.fzuborrow.data.Disc;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommunityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Disc>> discs = new MutableLiveData<>();

     {
         refreshDisc();
     }

    @SuppressLint("CheckResult")
    public void refreshDisc() {
        Api.get().findAllDisc(User.getLoggedInUser().getToken(),"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findAllDiscResponse -> {
                    Collections.sort(findAllDiscResponse.getDiscList());
                    discs.setValue(findAllDiscResponse.getDiscList());
                },e-> e.printStackTrace());
    }

    public CommunityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Disc>> getDiscs() {
        return discs;
    }
}