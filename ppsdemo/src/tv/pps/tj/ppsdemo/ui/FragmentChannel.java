package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.xengine.android.utils.XStringUtil;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.ui.animation.Rotate3dAnimationHelper;

/**
 * 显示某频道下各个节目的界面.
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-17
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
public class FragmentChannel extends Fragment {

    public static final int MODE_LISTVIEW = 0;// 列表模式
    public static final int MODE_GRIDVIEW = 1;// 网格模式

    private ImageView mMenuBtn;
    private View.OnClickListener mMenuBtnListener;
    private TextView mChannelNameView;
    private ImageView  mModeBtn;
    private Button mFilterBtn;
    private EditText mSearchInput;
    private Button mClearInputBtn;
    private TextView mTabView1, mTabView2, mTabView3, mTabView4;
    private ImageView mTabTip;
    private LinearLayout mFilterFrame;// 过滤栏
    private RelativeLayout mProgramContentFrame;
    private ListView mProgramListView;// 节目的列表界面
    private GridView mProgramGridView;// 节目的网格界面
    private View mAllLoadingFrame;// 整个节目加载提示 \
    private ProgressBar mAllLoadingProgressBar;
    private TextView mAllLoadingTextView;
    private View mListLoadingFrame;// 列表加载提示

    private Rotate3dAnimationHelper mRotate3dAnimationHelper;
    private boolean mModeChangeAnimation = false;// 是否正在播放切换动画

    private int mCurrentMode;//当前显示模式
    private int mSelectedTabIndex;// 选中的标签页码
    private String mFilterType, mFilterYear, mFilterFirstLetter;

    private LoadAllTask mLoadAllTask;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.channel_frame, container, false);
        mMenuBtn = (ImageView) rootView.findViewById(R.id.menu_btn);
        mModeBtn = (ImageView) rootView.findViewById(R.id.mode_btn);
        mChannelNameView = (TextView) rootView.findViewById(R.id.title_txt);
        mFilterBtn = (Button) rootView.findViewById(R.id.filter_btn);
        mSearchInput = (EditText) rootView.findViewById(R.id.search_input);
        mClearInputBtn = (Button) rootView.findViewById(R.id.clear_input_btn);
        mTabView1 = (TextView) rootView.findViewById(R.id.tab_txt1);
        mTabView2 = (TextView) rootView.findViewById(R.id.tab_txt2);
        mTabView3 = (TextView) rootView.findViewById(R.id.tab_txt3);
        mTabView4 = (TextView) rootView.findViewById(R.id.tab_txt4);
        mTabTip = (ImageView) rootView.findViewById(R.id.tab_tip);
        mFilterFrame = (LinearLayout) rootView.findViewById(R.id.filter_frame);
        mProgramContentFrame = (RelativeLayout) rootView.findViewById(R.id.content_list_grid_frame);
        mProgramListView = (ListView) rootView.findViewById(R.id.program_listview);
        mProgramGridView = (GridView) rootView.findViewById(R.id.program_gridview);
        mAllLoadingFrame = rootView.findViewById(R.id.all_loading_frame);
        mAllLoadingProgressBar = (ProgressBar) rootView.findViewById(R.id.all_loading_progressbar);
        mAllLoadingTextView = (TextView) rootView.findViewById(R.id.all_loading_txt);
        mListLoadingFrame = rootView.findViewById(R.id.list_loading_frame);


        // 初始化监听
        mMenuBtn.setOnClickListener(mMenuBtnListener);  // 设置菜单按钮的监听
        mModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 如果当前正在播放翻转动画，则忽略用户这次点击
                if (mModeChangeAnimation)
                    return;

                mModeChangeAnimation = true;
                mRotate3dAnimationHelper.rotate3d(mCurrentMode == MODE_LISTVIEW);
            }
        });
        mFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 弹出过滤对话框
            }
        });
        mClearInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchInput.setText("");
            }
        });

        // 初始化搜索栏
        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s = mSearchInput.getText().toString();
                if (!XStringUtil.isNullOrEmpty(s)) {
                    mClearInputBtn.setVisibility(View.VISIBLE);
                    // TODO 根据输入字符串获取最新的列表
                } else {
                    mClearInputBtn.setVisibility(View.GONE);
                }
            }
        });
        mClearInputBtn.setVisibility(View.GONE);

        // 初始化3d翻转动画
        mRotate3dAnimationHelper = new Rotate3dAnimationHelper
                (mProgramContentFrame, mProgramListView, mProgramGridView);
        mRotate3dAnimationHelper.setListener(new Rotate3dAnimationHelper.Rotate3dAnimationListener() {
            @Override
            public void rotateStart(boolean rotateToBack) {
            }
            @Override
            public void rotateFinish(boolean rotateToBack) {
                if (rotateToBack) {
                    mCurrentMode = MODE_GRIDVIEW;
                    mModeBtn.setImageResource(R.drawable.icon_top_grid);
                } else {
                    mCurrentMode = MODE_LISTVIEW;
                    mModeBtn.setImageResource(R.drawable.icon_top_list);
                }

                mModeChangeAnimation = false;
            }
        });

        // 初始化显示模式(上次用户是列表模式还是网格模式)
        mModeChangeAnimation = false;
        String currentChannelName = getArguments().getString("name");
        mCurrentMode = getArguments().getInt("mode");// TODO 传进来的参数
        if (mCurrentMode == MODE_LISTVIEW) {
            mProgramListView.setVisibility(View.VISIBLE);
            mProgramGridView.setVisibility(View.GONE);
        } else {
            mProgramListView.setVisibility(View.GONE);
            mProgramGridView.setVisibility(View.VISIBLE);
        }

        // 初始化标签
        mSelectedTabIndex = 0;

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

    @Override
    public void onStart() {
        super.onStart();

        refreshFilterFrame();

        if (mLoadAllTask != null) {
            mLoadAllTask.cancel(true);
            mLoadAllTask = null;
        }
        mLoadAllTask = new LoadAllTask();
        mLoadAllTask.execute(null);
    }


    /**
     * 刷新过滤栏
     */
    private void refreshFilterFrame() {
        if (XStringUtil.isNullOrEmpty(mFilterType) &&
                XStringUtil.isNullOrEmpty(mFilterYear) &&
                XStringUtil.isNullOrEmpty(mFilterFirstLetter))
            mFilterFrame.setVisibility(View.GONE);
        else {
            mFilterFrame.removeAllViews();
            if (!XStringUtil.isNullOrEmpty(mFilterType))
                addItemToFilterFrame(0, mFilterType);
            if (!XStringUtil.isNullOrEmpty(mFilterYear))
                addItemToFilterFrame(1, mFilterYear);
            if (!XStringUtil.isNullOrEmpty(mFilterFirstLetter))
                addItemToFilterFrame(2, mFilterFirstLetter);
            mFilterFrame.setVisibility(View.VISIBLE);
        }
    }

    private void addItemToFilterFrame(final int type, String filterStr) {
        View rootView = View.inflate(getActivity(), R.layout.filter_item, null);
        TextView filterTxt = (TextView) rootView.findViewById(R.id.item_txt);
        ImageView closeBtn = (ImageView) rootView.findViewById(R.id.close_btn);
        filterTxt.setText(filterStr);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 删除filter item
                switch (type) {
                    case 0:
                        mFilterType = null;
                        break;
                    case 1:
                        mFilterYear = null;
                        break;
                    case 2:
                        mFilterFirstLetter = null;
                        break;
                }
                refreshFilterFrame();
            }
        });
    }

    private class LoadAllTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            mAllLoadingProgressBar.setIndeterminate(true);
            mAllLoadingTextView.setText("正在加载...");
            mAllLoadingFrame.setVisibility(View.VISIBLE);
            mModeBtn.setVisibility(View.GONE);
            mFilterBtn.setVisibility(View.GONE);
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mAllLoadingFrame.setVisibility(View.GONE);
                mModeBtn.setVisibility(View.VISIBLE);
                mFilterBtn.setVisibility(View.VISIBLE);
                // TODO 更新界面
            } else {
                mAllLoadingProgressBar.setVisibility(View.GONE);
                mAllLoadingTextView.setText("网络异常，无法加载内容...");
            }
        }
    }
}
