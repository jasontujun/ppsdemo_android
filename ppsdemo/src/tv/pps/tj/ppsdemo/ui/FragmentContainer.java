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
            switch (mSelectedMenuIndex) {
                case 0:
                    Fragment channel = new FragmentChannel();
                    Bundle args = new Bundle();
                    args.putInt("mode", FragmentChannel.MODE_LISTVIEW);
                    args.putString("name", "内地剧场");
                    channel.setArguments(args);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, channel)
                            .commit();
                    break;
                default:
            }
        }
    }

    /**
     * 当前主题页面下添加页面
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
