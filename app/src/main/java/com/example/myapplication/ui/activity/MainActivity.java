package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AppComponent;
import com.example.myapplication.R;
import com.example.myapplication.contacts.MainActivityContact;
import com.example.myapplication.dagger.component.DaggerMainactivityComponent;
import com.example.myapplication.dagger.module.MainActivityModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainActivityContact.View {
    private static final String TAG = "RETROFIT";
    @BindView(R.id.et_content1)
    EditText etContent1;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @Inject
    MainActivityContact.Presenter presenter;


    @Override
    public void setUpComponent(AppComponent appComponent) {
        DaggerMainactivityComponent
                .builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }


    @Override
    public void signInSuccess(String s) {
//        ToastUtis.showToast(this, "登录成功token:" + s);
        tvResult.setText(s);
    }


        @OnClick(R.id.btn_start)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                presenter.calculate(2,3);
                break;
        }
    }
}
