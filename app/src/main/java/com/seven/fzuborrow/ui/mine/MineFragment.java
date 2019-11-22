package com.seven.fzuborrow.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.ui.login.LoginActivity;
import com.seven.fzuborrow.ui.login.UserInfoActivity;

public class MineFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cg_fragment_mine, container, false);
        getbind(root);
        return root;
    }

    private void getbind(View root) {
        root.findViewById(R.id.layout_info).setOnClickListener(v -> startActivity(new Intent(getContext(), UserInfoActivity.class)));
        ((TextView) root.findViewById(R.id.mine_name)).setText(User.getLoggedInUser().getUsername());
        ((TextView) root.findViewById(R.id.mine_number)).setText("学号:" + User.getLoggedInUser().getSchoolid());
        ImageView avatar = root.findViewById(R.id.image_mine_head);
        Glide.with(this).load(User.getLoggedInUser().getImgurl()).into(avatar);
        root.findViewById(R.id.Li_mine_credit).setOnClickListener(this);
        root.findViewById(R.id.Li_mine_manager).setOnClickListener(this);
        root.findViewById(R.id.Li_mine_borrow).setOnClickListener(this);
        root.findViewById(R.id.mine_setting).setOnClickListener(this);
        root.findViewById(R.id.Li_relogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.Li_mine_borrow:
                intent = new Intent(getContext(), MyBorrowActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.Li_mine_manager:
                intent = new Intent(getContext(), MineManagerActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.Li_mine_credit:
                Toast.makeText(getContext(), "信用度", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), MyCreditActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.mine_setting:
                Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Li_relogin:
                User.setLoggedInUser(null);
                startActivity(new Intent(getContext(), LoginActivity.class).putExtra("relogin",true));
                getActivity().finish();
                break;
        }
    }
}
