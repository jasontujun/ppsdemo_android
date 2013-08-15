package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.xengine.android.media.graphics.XScreen;
import com.xengine.android.media.image.processor.XImageProcessor;
import com.xengine.android.utils.XStringUtil;
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
    private View mTabNormal, mTabHq, mTabTrailer;
    private ImageView mTabTipNormal, mTabTipHq, mTabTipTrailer;
    private GridView mEpisodeFrame;// 剧集标签
    private View mFavoriteBtn, mShareBtn, mCommentBtn;
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
        mTabNormal = rootView.findViewById(R.id.type_tab_normal);
        mTabHq = rootView.findViewById(R.id.type_tab_hq);
        mTabTrailer = rootView.findViewById(R.id.type_tab_trailer);
        mTabTipNormal = (ImageView) rootView.findViewById(R.id.tab_order_normal);
        mTabTipHq = (ImageView) rootView.findViewById(R.id.tab_order_hq);
        mTabTipTrailer = (ImageView) rootView.findViewById(R.id.tab_order_trailer);
        mEpisodeFrame = (GridView) rootView.findViewById(R.id.episode_frame);
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
        mProgramAreaView.setText(mProgramDetail.getRegion());
        mProgramTypeView.setText(mProgramDetail.getType());
        addPeopleItemToLinearFrame(mProgramDetail.getDirector(), mProgramDirectorFrame);
        String[] actors = mProgramDetail.getActor();
        for (String actor : actors)
            addPeopleItemToLinearFrame(actor, mProgramActorFrame);
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
            mThirdPartAdapters = new ArrayList<AdapterEpisode>();

            for (ThirdPart thirdPart : mProgramDetail.getThirdPartList())
                mThirdPartAdapters.add(
                        new AdapterEpisode(getActivity(),
                        thirdPart.getThirdPartNormalEpisodes(),
                        thirdPart.getThirdPartHqEpisodes(),
                        thirdPart.getThirdPartTrailerEpisodes()));
        }
        mPpsAdapter = new AdapterEpisode(getActivity(),
                mProgramDetail.getPpsNormalEpisodes(),
                mProgramDetail.getPpsHqEpisodes(),
                mProgramDetail.getPpsTrailerEpisodes());

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
        if (mSelectedSrcIndex == -1) {
            // 下载按钮
            mDownloadBtn.setClickable(true);
            mDownloadBtnIcon.setImageResource(R.drawable.details_download_imageview);
            mDownloadBtnTxt.setTextColor(getActivity().getResources().getColor(R.color.black));
            // 初始化剧集
            adapter = mPpsAdapter;
            mEpisodeFrame.setAdapter(adapter);
        } else {
            // 下载按钮
            mDownloadBtn.setClickable(false);
            mDownloadBtnIcon.setImageResource(R.drawable.details_download_gray_imageview);
            mDownloadBtnTxt.setTextColor(getActivity().getResources().getColor(R.color.gray));
            // 初始化剧集
            adapter = mThirdPartAdapters.get(mSelectedSrcIndex);
            mEpisodeFrame.setAdapter(adapter);
        }
        // TODO 初始化标签栏
        // tab有无
        if (adapter.getNormalEpisodeList() != null)
            mTabNormal.setVisibility(View.VISIBLE);
        else
            mTabNormal.setVisibility(View.GONE);
        if (adapter.getHqEpisodeList() != null)
            mTabHq.setVisibility(View.VISIBLE);
        else
            mTabHq.setVisibility(View.GONE);
        if (adapter.getTrailerEpisodeList() != null)
            mTabTrailer.setVisibility(View.VISIBLE);
        else
            mTabTrailer.setVisibility(View.GONE);
        // tab颜色
        mTabNormal.setBackgroundResource(R.drawable.tab_3l_gray);
        mTabHq.setBackgroundResource(R.drawable.tab_3l_gray);
        mTabTrailer.setBackgroundResource(R.drawable.tab_3l_gray);
        mTabTipNormal.setVisibility(View.GONE);
        mTabTipHq.setVisibility(View.GONE);
        mTabTipTrailer.setVisibility(View.GONE);
        final int selectedTab = adapter.getSelectedTab();
        switch (selectedTab) {
            case 0:
                mTabNormal.setBackgroundResource(R.drawable.tab_3l_white);
                mTabTipNormal.setVisibility(View.VISIBLE);
                if (adapter.isNormalAscending())
                    mTabTipNormal.setImageResource(R.drawable.ic_details_arrow_up);
                else
                    mTabTipNormal.setImageResource(R.drawable.ic_details_arrow_down);
                break;
            case 1:
                mTabHq.setBackgroundResource(R.drawable.tab_3l_white);
                mTabTipHq.setVisibility(View.VISIBLE);
                if (adapter.isHqAscending())
                    mTabTipHq.setImageResource(R.drawable.ic_details_arrow_up);
                else
                    mTabTipHq.setImageResource(R.drawable.ic_details_arrow_down);
                break;
            case 2:
                mTabTrailer.setBackgroundResource(R.drawable.tab_3l_white);
                mTabTipTrailer.setVisibility(View.VISIBLE);
                if (adapter.isTrailerAscending())
                    mTabTipTrailer.setImageResource(R.drawable.ic_details_arrow_up);
                else
                    mTabTipTrailer.setImageResource(R.drawable.ic_details_arrow_down);
                break;
        }
        // tab监听
        mTabNormal.setOnClickListener(new TabClickListener(0, adapter));
        mTabHq.setOnClickListener(new TabClickListener(1, adapter));
        mTabTrailer.setOnClickListener(new TabClickListener(2, adapter));
    }

    private void addPeopleItemToLinearFrame(String people, LinearLayout frame) {
        XScreen screen = ScreenHolder.getInstance();
        TextView peopleView = new TextView(getActivity());
        peopleView.setTextSize(14);// sp
        peopleView.setTextColor(getActivity().getResources().getColor(R.color.dark_blue));
        peopleView.setBackgroundResource(R.drawable.bg_transparent_black);
        if (!XStringUtil.isNullOrEmpty(people)) {
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
     * 资源类别tab的点击监听
     */
    private class TabClickListener implements View.OnClickListener {
        private int mSelfIndex;
        AdapterEpisode mAdapter;

        private TabClickListener(int selfIndex, AdapterEpisode adapter) {
            mSelfIndex = selfIndex;
            mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            if (mAdapter.getSelectedTab() != mSelfIndex) {
                mAdapter.setSelectedTab(mSelfIndex);
            } else {
                switch (mSelfIndex) {
                    case 0:
                        mAdapter.setNormalAscending(!mAdapter.isNormalAscending());
                        break;
                    case 1:
                        mAdapter.setHqAscending(!mAdapter.isHqAscending());
                        break;
                    case 2:
                        mAdapter.setTrailerAscending(!mAdapter.isTrailerAscending());
                        break;
                }
            }
            refreshEpisode();
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
                convertView = View.inflate(mContext, R.layout.program_thirdpart_item, null);
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
                        // TODO 切换到当前资源下剧集列表
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
                        // TODO 切换到当前资源下剧集列表
                        mSelectedSrcIndex = -1;
                        refreshEpisode();
                    }
                });
            }

            return convertView;
        }
    }
}
