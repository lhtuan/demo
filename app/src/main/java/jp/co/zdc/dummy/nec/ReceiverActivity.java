package jp.co.zdc.dummy.nec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by tuan.lh on 3/23/2018.
 */

public class ReceiverActivity extends Activity{
    private TextView action, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiver_activity);

        action = (TextView)findViewById(R.id.action);
        code = (TextView)findViewById(R.id.code);

        Intent i = getIntent();
        action.setText(i.getAction());
        code.setText(i.getIntExtra(Const.EXTRA_CODE, 0));
    }
}
