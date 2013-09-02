package tv.pps.tj.ppsdemo.ui;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.xengine.android.media.graphics.XScreen;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.engine.ScreenHolder;
import tv.pps.tj.ppsdemo.logic.SystemMgr;
import tv.pps.tj.ppsdemo.ui.receiver.NetworkReceiver;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-17
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
public class ActivityMain extends FragmentActivity {

    private static final int PRESS_BACK_INTERVAL = 1500; // back按键间隔，单位：毫秒
    private long lastBackTime;// 上一次back键的时间

    private ViewPager mDragMenu;// 可拖动图层
    private PagerAdapter mDragMenuAdapter;
    private Fragment mLeftMenuFragment;// 左边菜单栏
    private FragmentContainer mMiddleContainerFragment;// 中间主界面

    private boolean mShowingMenu;// 是否显示左边栏

    private BroadcastReceiver mNetworkReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化系统相关组件
        SystemMgr.getInstance().initEngine(getApplicationContext());
        SystemMgr.getInstance().initSystem(getApplicationContext());

        // ui
        setContentView(R.layout.base_activity_frame);
        mDragMenu = (ViewPager) findViewById(R.id.drag_frame);

        mLeftMenuFragment = new FragmentLeftMenu();
        mMiddleContainerFragment = new FragmentContainer();
        mDragMenuAdapter = new DragMenuAdapter(getSupportFragmentManager());
        mDragMenu.setAdapter(mDragMenuAdapter);
        mDragMenu.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mShowingMenu = (position == 0);
            }
        });
        mShowingMenu = true;// 默认为弹出菜单按钮
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mNetworkReceiver == null)
            mNetworkReceiver = new NetworkReceiver();

        // 注册对网络状况的监听
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 取消对网络状况的监听
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    return false;
                } else if (!mShowingMenu) {
                    mDragMenu.setCurrentItem(0);
                    return true;
                } else {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastBackTime <= PRESS_BACK_INTERVAL) {
                        SystemMgr.getInstance().clearSystem();
                        finish();
                    } else {
                        lastBackTime = currentTime;
                        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            case KeyEvent.KEYCODE_MENU:
                if (!mShowingMenu)
                    mDragMenu.setCurrentItem(0);
                else
                    mDragMenu.setCurrentItem(1);
                return true;
        }
        return false;
    }

    /**
     * 获取对每个fragment子页面的菜单按钮监听
     * @return
     */
    public View.OnClickListener getMenuBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShowingMenu)
                    mDragMenu.setCurrentItem(1);
                else
                    mDragMenu.setCurrentItem(0);
            }
        };
    }

    public AdapterView.OnItemClickListener getMenuItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 切换成相应的Fragment界面
                mMiddleContainerFragment.selectMenu(i);
                // 收起侧边栏
                if (mShowingMenu)
                    mDragMenu.setCurrentItem(1);
            }
        };
    }


    /**
     * 当前主题页面下添加子页面
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        mMiddleContainerFragment.addFragment(fragment);
    }


    /**
     * 滑动式菜单栏的adapter
     */
    private class DragMenuAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_PAGES = 2;

        private float mLeftMenuWidthProportion;// 左边栏宽度比例

        public DragMenuAdapter(FragmentManager fm) {
            super(fm);
            // 计算左边栏的宽度比例
            XScreen screen = ScreenHolder.getInstance();
            float sWidthPx = screen.getScreenWidth();// 单位：像素
            float menuWidthPx = screen.dp2px(50);
            mLeftMenuWidthProportion = menuWidthPx / sWidthPx;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return mLeftMenuFragment;
            else
                return mMiddleContainerFragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public float getPageWidth(int position) {
            if (position == 0)
                return mLeftMenuWidthProportion;
            else
                return 1.f;
        }
    }
}
