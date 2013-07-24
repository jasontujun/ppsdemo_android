package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import tv.pps.tj.ppsdemo.R;

/**
 * 显示某频道下各个节目的界面.
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-17
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
public class FragmentChannel extends Fragment {

    private ImageView mMenuBtn;
    private ImageView mModeBtn;
    private View.OnClickListener mMenuBtnListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.channel_frame, container, false);
        mMenuBtn = (ImageView) rootView.findViewById(R.id.menu_btn);
        mModeBtn = (ImageView) rootView.findViewById(R.id.mode_btn);
        mMenuBtn.setOnClickListener(mMenuBtnListener);  // 设置菜单按钮的监听
        mModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT).show();
            }
        });

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
