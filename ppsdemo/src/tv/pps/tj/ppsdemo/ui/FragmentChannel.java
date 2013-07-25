package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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
    private View.OnClickListener mMenuBtnListener;
    private TextView mChannelNameView;
    private ImageView  mModeBtn;
    private Button mFilterBtn;
    private EditText mSearchInput;
    private Button mClearInputBtn;
    private TextView mTabView1, mTabView2, mTabView3, mTabView4;
    private ImageView mTabTip;
    private FrameLayout mContentFrame;

    private boolean mGridViewMode = false;// 是否是网格模式
    private int mSelectedTabIndex;// 选中的标签页码
    private Handler mHandler = new Handler();// A handler object, used for deferring UI operations.

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

            if (mGridViewMode)
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.content_list_grid_frame, new FragmentChannelGridView())
                        .commit();
            else
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.content_list_grid_frame, new FragmentChannelListView())
                        .commit();

        }
    }

    private void refreshModeBtn() {
        if (mGridViewMode)
            mModeBtn.setImageResource(R.drawable.icon_top_list);
        else
            mModeBtn.setImageResource(R.drawable.icon_top_grid);
    }

    /**
     * 利用翻转效果，切换列表或网格模式
     */
    private void flipCard() {
        if (mGridViewMode) {
            mGridViewMode = false;
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.content_list_grid_frame, new FragmentChannelListView())
                    .addToBackStack(null)
                    .commit();
        } else {
            mGridViewMode = true;
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.content_list_grid_frame, new FragmentChannelGridView())
                    .addToBackStack(null)
                    .commit();
        }

        // Defer an invalidation of the options menu (on modern devices, the action bar). This
        // can't be done immediately because the transaction may not yet be committed. Commits
        // are asynchronous in that they are posted to the main thread's message loop.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                refreshModeBtn();
            }
        });
    }

    /**
     * 列表模式的界面(正面)
     */
    class FragmentChannelListView extends Fragment {

        private ListView mProgramListView;

        public FragmentChannelListView() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.channel_frame_program_listview, container, false);
            mProgramListView = (ListView) rootView.findViewById(R.id.program_listview);
            return rootView;
        }
    }


    /**
     * 网格模式的界面(背面)
     */
    class FragmentChannelGridView extends Fragment {

        private GridView mProgramGridView;

        public FragmentChannelGridView() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.channel_frame_program_gridview, container, false);
            mProgramGridView = (GridView) rootView.findViewById(R.id.program_gridview);
            return rootView;
        }

    }
}
