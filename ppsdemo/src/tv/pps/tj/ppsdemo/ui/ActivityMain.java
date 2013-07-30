package tv.pps.tj.ppsdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import com.xengine.android.media.graphics.XAndroidScreen;
import com.xengine.android.media.graphics.XScreen;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.logic.SystemMgr;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-17
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
public class ActivityMain extends FragmentActivity {

    private ViewPager mDragMenu;// 可拖动图层
    private PagerAdapter mDragMenuAdapter;

    private boolean mShowingMenu;// 是否显示左边栏

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化系统相关组件
        SystemMgr.getInstance().initSystem(getApplicationContext());

        // ui
        setContentView(R.layout.base_activity_frame);
        mDragMenu = (ViewPager) findViewById(R.id.drag_frame);
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
                // TODO 切换成相应的Fragment界面
                if (mShowingMenu)
                    mDragMenu.setCurrentItem(1);
            }
        };
    }


    /**
     * 滑动式菜单栏的adapter
     */
    private class DragMenuAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_PAGES = 2;

        private Fragment mLeftMenuFragment;
        private Fragment mMiddleContentFragment;

        private float mLeftMenuWidthProportion;

        public DragMenuAdapter(FragmentManager fm) {
            super(fm);
            mLeftMenuFragment = new FragmentLeftMenu();
            mMiddleContentFragment = new FragmentChannel();
            Bundle args = new Bundle();
            args.putInt("mode", FragmentChannel.MODE_LISTVIEW);
            mMiddleContentFragment.setArguments(args);

            // 计算左边栏的宽度比例
            XScreen screen = new XAndroidScreen(ActivityMain.this);
            float sWidthPx = screen.getScreenWidth();// 单位：像素
            float menuWidthPx = screen.dp2px(50);
            mLeftMenuWidthProportion = menuWidthPx / sWidthPx;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return mLeftMenuFragment;
            else
                return mMiddleContentFragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        public float getPageWidth(int position) {
            if (position == 0)
                return mLeftMenuWidthProportion;
            else
                return 1.f;
        }
    }
}
