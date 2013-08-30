package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.xengine.android.media.graphics.XScreen;
import com.xengine.android.media.image.processor.XImageProcessor;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.data.model.ProgramDetail;
import tv.pps.tj.ppsdemo.data.model.ThirdPart;
import tv.pps.tj.ppsdemo.engine.MyImageViewRemoteLoader;
import tv.pps.tj.ppsdemo.engine.ScreenHolder;
import tv.pps.tj.ppsdemo.logic.ProgramMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示具体节目的界面
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-23
 * Time: 下午6:13
 * To change this template use File | Settings | File Templates.
 */
public class FragmentProgram extends Fragment {
    private ImageView mMenuBtn;
    private View.OnClickListener mMenuBtnListener;
    private TextView mProgramNameView;
    private ImageView mProgramImage;
    private TextView mProgramAreaView;
    private TextView mProgramTypeView;
    private LinearLayout mProgramDirectorFrame;
    private LinearLayout mProgramActorFrame;// 演员栏
    private TextView mProgramScore;
    private View mPlayBtn;
    private View mDownloadBtn;
    private ImageView mDownloadBtnIcon;
    private TextView mDownloadBtnTxt;
    private View mSrcBtn;// 播放来源按钮
    private ImageView mSrcIconView;
    private ImageView mSrcBtnTip;
    private GridView mSrcGridView;
    private TextView mProgramBriefView;// 简介栏
    private View mTabLeftBtn, mTabRightBtn;
    private ViewPager mEpisodeTabFrame;// 剧集标签栏
    private GridView mEpisodeGridView;// 剧集表格
    private View mFavoriteBtn, mShareBtn, mCommentBtn;
    private EpisodeTabViewPagerAdapter mTabAdapter;
    private AdapterEpisode mPpsAdapter;
    private List<AdapterEpisode> mThirdPartAdapters;

    // loading提示
    private View mAllLoadingFrame;
    private ProgressBar mAllLoadingProgressBar;
    private TextView mAllLoadingTextView;

    private String mProgramId;// 节目的id
    private String mProgramName;// 节目的名称
    private ProgramDetail mProgramDetail;
    private int mSelectedSrcIndex;// 选择的来源

    private LoadAllTask mLoadAllTask;// 加载数据的异步线程

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgramId = getArguments().getString("id");// 传进来的参数
        mProgramName = getArguments().getString("name");// 传进来的参数

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.program_frame, container, false);
        mMenuBtn = (ImageView) rootView.findViewById(R.id.menu_btn);
        mProgramNameView = (TextView) rootView.findViewById(R.id.title_txt);
        mProgramImage = (ImageView) rootView.findViewById(R.id.program_img);
        mProgramAreaView = (TextView) rootView.findViewById(R.id.program_area);
        mProgramTypeView = (TextView) rootView.findViewById(R.id.program_type);
        mProgramDirectorFrame = (LinearLayout) rootView.findViewById(R.id.program_director_frame);
        mProgramActorFrame = (LinearLayout) rootView.findViewById(R.id.program_actor_frame);
        mProgramScore = (TextView) rootView.findViewById(R.id.program_score);
        mPlayBtn = rootView.findViewById(R.id.play_btn);
        mDownloadBtn = rootView.findViewById(R.id.download_btn);
        mDownloadBtnIcon = (ImageView) rootView.findViewById(R.id.download_btn_icon);
        mDownloadBtnTxt = (TextView) rootView.findViewById(R.id.download_btn_txt);
        mSrcBtn = rootView.findViewById(R.id.src_btn);
        mSrcIconView = (ImageView) rootView.findViewById(R.id.src_icon);
        mSrcBtnTip = (ImageView) rootView.findViewById(R.id.src_tip);
        mSrcGridView = (GridView) rootView.findViewById(R.id.src_gridview);
        mProgramBriefView = (TextView) rootView.findViewById(R.id.program_brief);
        mTabLeftBtn = rootView.findViewById(R.id.type_left_btn);
        mTabRightBtn = rootView.findViewById(R.id.type_right_btn);
        mEpisodeTabFrame = (ViewPager) rootView.findViewById(R.id.episode_tab_frame);
        mEpisodeGridView = (GridView) rootView.findViewById(R.id.episode_frame);
        mFavoriteBtn = rootView.findViewById(R.id.favour_btn);
        mShareBtn = rootView.findViewById(R.id.share_btn);
        mCommentBtn = rootView.findViewById(R.id.comment_btn);
        mAllLoadingFrame = rootView.findViewById(R.id.all_loading_frame);
        mAllLoadingProgressBar = (ProgressBar) rootView.findViewById(R.id.all_loading_progressbar);
        mAllLoadingTextView = (TextView) rootView.findViewById(R.id.all_loading_txt);

        mMenuBtn.setOnClickListener(mMenuBtnListener);  // 设置菜单按钮的监听
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 播放
                Toast.makeText(getActivity(), "播放 " + mProgramName, Toast.LENGTH_SHORT).show();
            }
        });
        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 下载
                Toast.makeText(getActivity(), "下载 " + mProgramName, Toast.LENGTH_SHORT).show();
            }
        });
        mSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSrcGridView.getVisibility() == View.VISIBLE) {
                    mSrcGridView.setVisibility(View.GONE);
                    mSrcBtnTip.setImageResource(R.drawable.ic_third_zhankai);
                } else {
                    mSrcGridView.setVisibility(View.VISIBLE);
                    mSrcBtnTip.setImageResource(R.drawable.ic_third_shouqi);
                }
            }
        });
        mTabLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEpisodeTabFrame.getCurrentItem() > 0)
                    mEpisodeTabFrame.setCurrentItem(mEpisodeTabFrame.getCurrentItem() - 1);
            }
        });
        mTabRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEpisodeTabFrame.getCurrentItem() < mTabAdapter.getCount() - 1)
                    mEpisodeTabFrame.setCurrentItem(mEpisodeTabFrame.getCurrentItem() + 1);
            }
        });
        mFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 收藏
            }
        });
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 分享
            }
        });
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 评论
            }
        });

        mProgramNameView.setText(mProgramName);

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

        if (mLoadAllTask != null) {
            mLoadAllTask.cancel(true);
            mLoadAllTask = null;
        }
        mLoadAllTask = new LoadAllTask(mProgramId);
        mLoadAllTask.execute(null);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mLoadAllTask != null) {
            mLoadAllTask.cancel(true);
            mLoadAllTask = null;
        }
    }

    /**
     * 刷新节目的信息（整个界面）
     */
    private void refreshInfo() {
        if (mProgramDetail == null)
            return;

        // 节目海报
        MyImageViewRemoteLoader.getInstance().asyncLoadBitmap(getActivity(),
                mProgramDetail.getPosterUrl(), mProgramImage,
                XImageProcessor.ImageSize.SCREEN, null);
        // 信息
        if (TextUtils.isEmpty(mProgramDetail.getRegion()))
            mProgramAreaView.setText("暂无");
        else
            mProgramAreaView.setText(mProgramDetail.getRegion());
        if (TextUtils.isEmpty(mProgramDetail.getType()))
            mProgramTypeView.setText("暂无");
        else
            mProgramTypeView.setText(mProgramDetail.getType());
        addPeopleItemToLinearFrame(mProgramDetail.getDirector(), mProgramDirectorFrame);
        String[] actors = mProgramDetail.getActor();
        if (actors != null) {
            for (String actor : actors)
                addPeopleItemToLinearFrame(actor, mProgramActorFrame);
        } else {
            addPeopleItemToLinearFrame(null, mProgramActorFrame);
        }
        mProgramScore.setText("" + mProgramDetail.getVote());
        mProgramBriefView.setText(mProgramDetail.getIntroduction());
        // 初始化播放来源
        if (mProgramDetail.getThirdPartList() == null) {
            mSrcBtn.setVisibility(View.GONE);
            mSrcGridView.setVisibility(View.GONE);
            mSelectedSrcIndex = -1;
        } else {
            mSrcBtn.setVisibility(View.VISIBLE);
            mSrcGridView.setVisibility(View.GONE);
            mSrcGridView.setAdapter(new SourceAdapter(getActivity(),
                    mProgramDetail.getThirdPartList()));
            mSelectedSrcIndex = 0;
            // 初始化第三方来源的adpater
            if (mThirdPartAdapters == null) {
                mThirdPartAdapters = new ArrayList<AdapterEpisode>();
                for (ThirdPart thirdPart : mProgramDetail.getThirdPartList())
                    mThirdPartAdapters.add(
                            new AdapterEpisode(getActivity(),
                                    thirdPart.getThirdPartEpisodes()));
            }
        }
        // 初始化pps来源的初始化
        if (mPpsAdapter == null)
            mPpsAdapter = new AdapterEpisode(getActivity(),
                    mProgramDetail.getPpsEpisodes());

        // 初始化剧集
        refreshEpisode();
    }

    /**
     * 刷新剧集的信息（局部界面）
     */
    private void refreshEpisode() {
        if (mProgramDetail == null)
            return;

        AdapterEpisode adapter = null;
        if (mSelectedSrcIndex == -1) {// PPS来源
            // 来源按钮
            mSrcIconView.setImageResource(R.drawable.ic_third_pps);
            // 下载按钮
            mDownloadBtn.setClickable(true);
            mDownloadBtnIcon.setImageResource(R.drawable.details_download_imageview);
            mDownloadBtnTxt.setTextColor(getActivity().getResources().getColor(R.color.black));
            // 初始化剧集
            adapter = mPpsAdapter;
            mEpisodeGridView.setAdapter(adapter);
        } else {// 第三方来源
            // 来源按钮
            String thirdPartImage = mProgramDetail.getThirdPartList().
                    get(mSelectedSrcIndex).getThirdPartImage();
            MyImageViewRemoteLoader.getInstance().asyncLoadBitmap(getActivity(),
                    thirdPartImage, mSrcIconView,
                    XImageProcessor.ImageSize.SMALL, null);
            // 下载按钮
            mDownloadBtn.setClickable(false);
            mDownloadBtnIcon.setImageResource(R.drawable.details_download_gray_imageview);
            mDownloadBtnTxt.setTextColor(getActivity().getResources().getColor(R.color.gray));
            // 初始化剧集
            adapter = mThirdPartAdapters.get(mSelectedSrcIndex);
            mEpisodeGridView.setAdapter(adapter);
        }
        // 根据内容字数，设置GridView的列数
        int itemLength = adapter.getItemLength();
        if (itemLength <= 2)
            mEpisodeGridView.setNumColumns(5);
        else if (itemLength <= 5)
            mEpisodeGridView.setNumColumns(4);
        else
            mEpisodeGridView.setNumColumns(1);
        // 初始化标签栏
        mTabAdapter = new EpisodeTabViewPagerAdapter(getActivity(), adapter);
        mEpisodeTabFrame.setAdapter(mTabAdapter);
        mEpisodeTabFrame.setCurrentItem(mTabAdapter.getSelectedIndex());
    }

    /**
     * 添加人名到演员栏或导演栏
     * @param people
     * @param frame
     */
    private void addPeopleItemToLinearFrame(String people, LinearLayout frame) {
        XScreen screen = ScreenHolder.getInstance();
        TextView peopleView = new TextView(getActivity());
        peopleView.setTextSize(14);// sp
        peopleView.setTextColor(getActivity().getResources().getColor(R.color.dark_blue));
        peopleView.setBackgroundResource(R.drawable.bg_transparent_black);
        if (!TextUtils.isEmpty(people)) {
            peopleView.setText(people);
            peopleView.setOnClickListener(new PeopleClickListener(people));// 添加监听
        } else {
            peopleView.setText("暂无");
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                screen.dp2px(30));
        params.gravity = Gravity.CENTER_VERTICAL;
        peopleView.setLayoutParams(params);
        frame.addView(peopleView);
    }

    /**
     * 人名TextView的点击监听
     */
    private class PeopleClickListener implements View.OnClickListener {
        private String people;

        private PeopleClickListener(String people) {
            this.people = people;
        }

        @Override
        public void onClick(View view) {
            // TODO 跳转到搜索界面
            Toast.makeText(getActivity(), "点击了“" + people + "”，跳转到搜索界面",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 加载列表数据的asyncTask
     */
    private class LoadAllTask extends AsyncTask<Void, Void, ProgramDetail> {

        private String mProgramId;

        private LoadAllTask(String programId) {
            mProgramId = programId;
        }

        @Override
        protected void onPreExecute() {
            mAllLoadingProgressBar.setIndeterminate(true);
            mAllLoadingTextView.setText("正在加载...");
            mAllLoadingProgressBar.setVisibility(View.VISIBLE);
            mAllLoadingFrame.setVisibility(View.VISIBLE);
        }
        @Override
        protected ProgramDetail doInBackground(Void... voids) {
            return ProgramMgr.getInstance().getProgramDetail(getActivity(), mProgramId);
        }
        @Override
        protected void onPostExecute(ProgramDetail result) {
            if (result != null) {
                // 更新界面
                mProgramDetail = result;
                refreshInfo();
                mAllLoadingFrame.setVisibility(View.GONE);
            } else {
                mAllLoadingProgressBar.setVisibility(View.GONE);
                mAllLoadingTextView.setText("网络异常，无法加载内容...");
            }
        }
        @Override
        protected void onCancelled(){
            mAllLoadingProgressBar.setVisibility(View.GONE);
            mAllLoadingTextView.setText("网络异常，无法加载内容...");
        }
    }

    /**
     * 资源选择GridView的adapter
     */
    private class SourceAdapter extends BaseAdapter {
        private Context mContext;
        private List<ThirdPart> mThirdPartList;

        private SourceAdapter(Context context, List<ThirdPart> thirdPartList) {
            mContext = context;
            mThirdPartList = thirdPartList;
        }

        @Override
        public int getCount() {
            return mThirdPartList.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            if (i == mThirdPartList.size())
                return null;
            else
                return mThirdPartList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        private class ViewHolder {
            public ImageView srcIconView;
            public TextView srcNameView;
        }
        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.program_src_item, null);
                holder = new ViewHolder();
                holder.srcIconView = (ImageView) convertView.findViewById(R.id.thirdpart_icon);
                holder.srcNameView = (TextView) convertView.findViewById(R.id.thirdpart_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (getItem(i) != null) {
                final ThirdPart thirdPart = (ThirdPart) getItem(i);
                holder.srcNameView.setText(thirdPart.getThirdPartTitle());
                MyImageViewRemoteLoader.getInstance().asyncLoadBitmap(mContext,
                        thirdPart.getThirdPartImage(), holder.srcIconView,
                        XImageProcessor.ImageSize.SMALL, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyImageViewRemoteLoader.getInstance().asyncLoadBitmap(mContext,
                                thirdPart.getThirdPartImage(), mSrcIconView,
                                XImageProcessor.ImageSize.SMALL, null);
                        mSrcBtn.performClick();// 收起
                        // 切换到当前资源下剧集列表
                        mSelectedSrcIndex = i;
                        refreshEpisode();
                    }
                });
            } else {
                holder.srcIconView.setImageResource(R.drawable.ic_third_pps);
                holder.srcNameView.setText("PPS");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSrcIconView.setImageResource(R.drawable.ic_third_pps);
                        mSrcBtn.performClick();// 收起
                        // 切换到当前资源下剧集列表
                        mSelectedSrcIndex = -1;
                        refreshEpisode();
                    }
                });
            }

            return convertView;
        }
    }

    /**
     * 剧集标签的adapter
     */
    private class EpisodeTabViewPagerAdapter extends PagerAdapter {
        private Context mContext;
        private AdapterEpisode mEpisodeAdapter;
        private String[] mTabs;
        private View[] mViews;
        private class ViewHolder {
            public TextView tabTextView1;
            public TextView tabTextView2;
            public ImageView tabTipView;
        }

        private EpisodeTabViewPagerAdapter(Context context,
                                           AdapterEpisode episodeAdapter) {
            mContext = context;
            mEpisodeAdapter = episodeAdapter;
            mTabs = mEpisodeAdapter.getTabList();
            mViews = new View[mTabs.length];
        }

        public int getSelectedIndex() {
            String selectedTab = mEpisodeAdapter.getSelectedTab();
            for (int i = 0; i<mTabs.length; i++) {
                if (selectedTab.equals(mTabs[i]))
                    return i;
            }
            return -1;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return mTabs.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            View view = mViews[position];
            if (view != null)
                container.removeView(view);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View convertView = mViews[position];
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.episode_type_item, null);
                holder = new ViewHolder();
                holder.tabTextView1 = (TextView) convertView.findViewById(R.id.tab_txt1);
                holder.tabTextView2 = (TextView) convertView.findViewById(R.id.tab_txt2);
                holder.tabTipView = (ImageView) convertView.findViewById(R.id.tab_tip);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String tab = mTabs[position];
            String txt1 = tab.substring(0, 2);
            String txt2 = tab.substring(2, 4);
            holder.tabTextView1.setText(txt1);
            holder.tabTextView2.setText(txt2);

            if ("高清".equals(txt2))
                holder.tabTextView2.setTextColor(mContext.getResources().getColor(R.color.dark_blue));
            else
                holder.tabTextView2.setTextColor(mContext.getResources().getColor(R.color.black));

            String selectedTab = mEpisodeAdapter.getSelectedTab();
            if (tab.equals(selectedTab)) {
                holder.tabTipView.setVisibility(View.VISIBLE);
                if (mEpisodeAdapter.isAscending())
                    holder.tabTipView.setImageResource(R.drawable.ic_details_arrow_up);
                else
                    holder.tabTipView.setImageResource(R.drawable.ic_details_arrow_down);
                convertView.setBackgroundResource(R.drawable.tab_3l_white);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEpisodeAdapter.setAscending(!mEpisodeAdapter.isAscending());
                        refreshEpisode();
                    }
                });
            } else {
                holder.tabTipView.setVisibility(View.GONE);
                convertView.setBackgroundResource(R.drawable.tab_3l_gray);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEpisodeAdapter.setSelectedTab(tab);
                        refreshEpisode();
                    }
                });
            }

            container.addView(convertView);
            return convertView;
        }

        @Override
        public float getPageWidth(int position) {
            XScreen screen = ScreenHolder.getInstance();
            float sWidthPx = mEpisodeTabFrame.getWidth();
            float menuWidthPx = screen.dp2px(65);
            float proportion = menuWidthPx / sWidthPx;
            return proportion;
        }
    }
}
