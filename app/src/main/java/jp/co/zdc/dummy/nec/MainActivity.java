package jp.co.zdc.dummy.nec;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQ_LOGIN = 1, REQ_REALTIME = 2, REQ_LOGOUT = 3;
    private EditText mCompIdEdt, mLoginIdEdt, mPasswordEdt, mFacilityEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mCompIdEdt = (EditText)findViewById(R.id.compID);
        mLoginIdEdt = (EditText)findViewById(R.id.loginID);
        mPasswordEdt = (EditText)findViewById(R.id.pass);
        mFacilityEdt = (EditText)findViewById(R.id.facilityID);

        addClickListener(R.id.loginBtn);
        addClickListener(R.id.logout);
        addClickListener(R.id.realtime);
    }

    private void addClickListener(int res){
        findViewById(res).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                login();
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.realtime:
                realTime();
                break;
        }
    }

    private Intent buildDataIntent(String action){
        Intent i = new Intent(action);
        i.putExtra(Const.EXTRA_COMPANY_ID, mCompIdEdt.getText().toString());
        i.putExtra(Const.EXTRA_LOGIN_ID, mLoginIdEdt.getText().toString());
        i.putExtra(Const.EXTRA_PASSWORD, mPasswordEdt.getText().toString());
        int facilityId = 0;
        try{
            facilityId = Integer.parseInt(mFacilityEdt.getText().toString());
        }catch (NumberFormatException e){
            //ignore
        }
        i.putExtra(Const.EXTRA_FACILITY_ID, facilityId);
        return i;
    }

    private void login(){
        Intent i = buildDataIntent(Const.INTENT_ACTION_LOGIN);
        startActivityForResultSafety(i, REQ_LOGIN);
    }

    private void logout(){
        Intent i = new Intent(Const.INTENT_ACTION_LOGOUT);
        startActivityForResultSafety(i, REQ_LOGOUT);
    }

    private void realTime(){
        Intent i = buildDataIntent(Const.INTENT_ACTION_MAP);
        startActivityForResultSafety(i, REQ_REALTIME);
    }

    private void startActivityForResultSafety(Intent i, int reqCode){
        try{
            startActivityForResult(i, reqCode);
        }catch (ActivityNotFoundException e){
            Toast.makeText(this, "Failed to start Indoor App", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            showResult(data);
        }
    }

    private void showResult(Intent data){
        new AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(String.format("Action: %s, Code: %s", data.getAction(), data.getIntExtra("code", 0)))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
