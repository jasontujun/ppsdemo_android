package tv.pps.tj.ppsdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tv.pps.tj.ppsdemo.R;

/**
 * 应用首页，显示各个频道。
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-24
 * Time: 下午3:54
 * To change this template use File | Settings | File Templates.
 */
public class FragmentContainer extends Fragment {

    private int mSelectedMenuIndex;// 当前选中的左边标签

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.base_container_frame, container, false);

        mSelectedMenuIndex = -1;
        selectMenu(0);// 预先加载0
        return rootView;
    }

    /**
     * 左边栏切换主题页面
     * @param index
     */
    public void selectMenu(int index) {
        if (index != mSelectedMenuIndex) {
            mSelectedMenuIndex = index;

            if (getFragmentManager().getBackStackEntryCount() > 0)
                getFragmentManager().popBackStack();

            Fragment fragment = null;
            Bundle args = null;
            switch (mSelectedMenuIndex) {
                case 0:
                    fragment = new FragmentChannel();
                    args = new Bundle();
                    args.putInt("mode", FragmentChannel.MODE_LISTVIEW);
                    args.putString("name", "内地剧场");
                    fragment.setArguments(args);
                    break;
                case 1:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "搜索");
                    fragment.setArguments(args);
                    break;
                case 2:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "爱频道");
                    fragment.setArguments(args);
                    break;
                case 3:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "会员");
                    fragment.setArguments(args);
                    break;
                case 4:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "我的视频");
                    fragment.setArguments(args);
                    break;
                case 5:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "离线频道");
                    fragment.setArguments(args);
                    break;
                case 6:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "游戏中心");
                    fragment.setArguments(args);
                    break;
                case 7:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "阅读");
                    fragment.setArguments(args);
                    break;
                case 8:
                    fragment = new FragmentHome();
                    args = new Bundle();
                    args.putString("name", "设置");
                    fragment.setArguments(args);
                    break;
                default:
            }
            if (fragment != null)
                getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    /**
     * 当前主题页面下添加页面
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
