package tv.pps.tj.ppsdemo.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.data.model.Episode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-14
 * Time: 下午6:36
 * To change this template use File | Settings | File Templates.
 */
public class AdapterEpisode extends BaseAdapter {
    private Context mContext;
    private int mSelectedTab;// 选择
    private boolean isNormalAscending, isHqAscending, isTrailerAscending;
    private List<Episode> mNormalEpisodeList;// 国语普通
    private List<Episode> mHqEpisodeList;
    private List<Episode> mTrailerEpisodeList;

    public AdapterEpisode(Context context,
                          List<Episode> normalEpisodeList,
                          List<Episode> hqEpisodeList,
                          List<Episode> trailerEpisodeList) {
        mContext = context;
        if (normalEpisodeList != null) {
            mNormalEpisodeList = new ArrayList<Episode>();
            mNormalEpisodeList.addAll(normalEpisodeList);
        }
        if (hqEpisodeList != null) {
            mHqEpisodeList = new ArrayList<Episode>();
            mHqEpisodeList.addAll(hqEpisodeList);
        }
        if (trailerEpisodeList != null) {
            mTrailerEpisodeList = new ArrayList<Episode>();
            mTrailerEpisodeList.addAll(trailerEpisodeList);
        }
        isNormalAscending = true;
        isHqAscending = true;
        isTrailerAscending = true;

        // 初始化tab标签号
        if (normalEpisodeList != null) {
            mSelectedTab = 0;
        } else if (hqEpisodeList != null) {
            mSelectedTab = 1;
        } else {
            mSelectedTab = 2;
        }
    }

    public int getSelectedTab() {
        return mSelectedTab;
    }

    public void setSelectedTab(int mSelectedTab) {
        this.mSelectedTab = mSelectedTab;
        notifyDataSetChanged();
    }

    public boolean isNormalAscending() {
        return isNormalAscending;
    }

    public void setNormalAscending(boolean normalAscending) {
        if (isNormalAscending == normalAscending)
            return;
        isNormalAscending = normalAscending;
        Collections.reverse(mNormalEpisodeList);
        notifyDataSetChanged();
    }

    public boolean isHqAscending() {
        return isHqAscending;
    }

    public void setHqAscending(boolean hqAscending) {
        if (isHqAscending == hqAscending)
            return;
        isHqAscending = hqAscending;
        Collections.reverse(mHqEpisodeList);
        notifyDataSetChanged();
    }

    public boolean isTrailerAscending() {
        return isTrailerAscending;
    }

    public void setTrailerAscending(boolean trailerAscending) {
        if (isTrailerAscending == trailerAscending)
            return;
        isTrailerAscending = trailerAscending;
        Collections.reverse(mTrailerEpisodeList);
        notifyDataSetChanged();
    }

    public List<Episode> getNormalEpisodeList() {
        return mNormalEpisodeList;
    }

    public List<Episode> getHqEpisodeList() {
        return mHqEpisodeList;
    }

    public List<Episode> getTrailerEpisodeList() {
        return mTrailerEpisodeList;
    }

    @Override
    public int getCount() {
        switch (mSelectedTab) {
            case 0:
                if (mNormalEpisodeList != null)
                    return mNormalEpisodeList.size();
                break;
            case 1:
                if (mHqEpisodeList != null)
                    return mHqEpisodeList.size();
                break;
            case 2:
                if (mTrailerEpisodeList != null)
                    return mTrailerEpisodeList.size();
                break;
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        switch (mSelectedTab) {
            case 0:
                if (mNormalEpisodeList != null)
                    return mNormalEpisodeList.get(i);
                break;
            case 1:
                if (mHqEpisodeList != null)
                    return mHqEpisodeList.get(i);
                break;
            case 2:
                if (mTrailerEpisodeList != null)
                    return mTrailerEpisodeList.get(i);
                break;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        public ImageView episodeTypeView;
        public TextView episodeNameView;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.episode_item, null);
            holder = new ViewHolder();
            holder.episodeNameView = (TextView) convertView.findViewById(R.id.episode_name);
            holder.episodeTypeView = (ImageView) convertView.findViewById(R.id.episode_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (getItem(i) == null)
            return null;

        final Episode episode = (Episode) getItem(i);
        if ("rm".equals(episode.getFormat())) {
            holder.episodeTypeView.setVisibility(View.VISIBLE);
            holder.episodeTypeView.setImageResource(R.drawable.ic_ss_rm);
        } else if ("wmv".equals(episode.getFormat())) {
            holder.episodeTypeView.setVisibility(View.VISIBLE);
            holder.episodeTypeView.setImageResource(R.drawable.ic_ss_wmv);
        } else {
            holder.episodeTypeView.setVisibility(View.GONE);
        }
        holder.episodeNameView.setText(episode.getName());
        holder.episodeNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 播放对应剧集
                Toast.makeText(mContext, "点击" + episode.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
