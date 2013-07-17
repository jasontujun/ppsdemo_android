package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.logic.SystemMgr;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-17
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
public class ActivityMain extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化系统相关组件
        SystemMgr.getInstance().initSystem(getApplicationContext());

        // ui
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
