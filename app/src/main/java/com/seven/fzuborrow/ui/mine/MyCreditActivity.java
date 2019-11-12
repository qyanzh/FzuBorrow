package com.seven.fzuborrow.ui.mine;

import androidx.appcompat.app.AppCompatActivity;
import com.seven.fzuborrow.R;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MyCreditActivity extends AppCompatActivity implements View.OnClickListener{
    private DashboardView3 mDashboardView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cg_activity_my_credit);
        mDashboardView3 = (DashboardView3) findViewById(R.id.dashboard_view_3);
        mDashboardView3.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.dashboard_view_3:
                mDashboardView3.setCreditValue(new Random().nextInt(950 - 350) + 350);

                break;
        }
    }
}
