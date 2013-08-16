package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import tv.pps.tj.ppsdemo.R;

/**
 * 应用首页，显示各个频道。
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-24
 * Time: 下午3:54
 * To change this template use File | Settings | File Templates.
 */
public class FragmentHome extends Fragment {

    private ImageView mMenuBtn;
    private TextView mFragmentNameView;

    private View.OnClickListener mMenuBtnListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_frame, container, false);
        mMenuBtn = (ImageView) rootView.findViewById(R.id.menu_btn);
        mFragmentNameView = (TextView) rootView.findViewById(R.id.title_txt);

        mMenuBtn.setOnClickListener(mMenuBtnListener);  // 设置菜单按钮的监听

        String fragmentName = getArguments().getString("name");// 传进来的参数
        mFragmentNameView.setText(fragmentName);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ActivityMain) {
            ActivityMain hostActivity = (ActivityMain) activity;
            mMenuBtnListener = hostActivity.getMenuBtnClickListener();
        }
    }
}
