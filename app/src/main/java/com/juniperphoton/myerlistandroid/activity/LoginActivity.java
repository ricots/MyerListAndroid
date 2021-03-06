package com.juniperphoton.myerlistandroid.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juniperphoton.myerlistandroid.R;
import com.juniperphoton.myerlistandroid.presenter.LoginPresenter;
import com.juniperphoton.myerlistandroid.util.Params;
import com.juniperphoton.myerlistandroid.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings("UnusedDeclaration")
public class LoginActivity extends BaseActivity implements LoginView {

    private boolean DEBUG = true;

    @BindView(R.id.login_email)
    EditText mEmailView;

    @BindView(R.id.login_pwd)
    EditText mPasswordView;

    @BindView(R.id.login_second_pwd)
    EditText mSecondPasswordView;

    @BindView(R.id.login_second_pwd_layout)
    TextInputLayout mSecondPasswordLayout;

    @BindView(R.id.login_title)
    TextView mTitle;

    @BindView(R.id.login_btn)
    Button mButton;

    private LoginPresenter mPresenter;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        int loginMode = intent.getIntExtra(Params.LOGIN_MODE, -1);
        switch (loginMode) {
            case Params.LoginMode.LOGIN:
                mTitle.setText("LOGIN");
                mSecondPasswordLayout.setVisibility(View.GONE);
                mButton.setText("Login");
                break;
            case Params.LoginMode.REGISTER:
                mTitle.setText("REGISTER");
                mSecondPasswordLayout.setVisibility(View.VISIBLE);
                mButton.setText("Register");
                break;
        }
        if (DEBUG) {
            mEmailView.setText("dengweichao@hotmail.com");
            mPasswordView.setText("test");
        }
        mPresenter = new LoginPresenter(this, loginMode);
    }

    @Override
    public String getEmail() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public String getSecondPassword() {
        return mSecondPasswordView.getText().toString();
    }

    @OnClick(R.id.login_btn)
    void onClickButton() {
        mDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle("Loading...");
        mDialog.show();
        mPresenter.login();
    }

    @Override
    public void afterLogin(boolean ok) {
        mDialog.dismiss();
        if (ok) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
