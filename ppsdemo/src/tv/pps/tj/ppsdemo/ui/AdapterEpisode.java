package tv.pps.tj.ppsdemo.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.data.model.Episode;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-14
 * Time: 下午6:36
 * To change this template use File | Settings | File Templates.
 */
public class AdapterEpisode extends BaseAdapter {
    private Context mContext;

    private Map<String, WrapperData> mAllTypeEpisodes;
    private String mSelectedTab;// 选中的Tab

    private class WrapperData {
        public List<Episode> episodes;// 剧集数据
        public boolean isAscending;// 是否升序排列

        private WrapperData(List<Episode> episodes, boolean ascending) {
            this.episodes = episodes;
            isAscending = ascending;
        }
    }

    public AdapterEpisode(Context context,Map<String, List<Episode>> allTypeEpisodes) {
        mContext = context;

        mAllTypeEpisodes = new HashMap<String, WrapperData>();
        for (Map.Entry<String, List<Episode>> entry: allTypeEpisodes.entrySet()) {
            String tag = entry.getKey();
            List<Episode> episodes = entry.getValue();
            mAllTypeEpisodes.put(tag, new WrapperData
                    (new ArrayList<Episode>(episodes), true));
        }

        mSelectedTab = mAllTypeEpisodes.keySet().iterator().next();
    }

    public String[] getTabList() {
        Set<String> keys = mAllTypeEpisodes.keySet();
        String[] result = new String[keys.size()];
        keys.toArray(result);
        return result;
    }

    public String getSelectedTab() {
        return mSelectedTab;
    }

    public void setSelectedTab(String mSelectedTab) {
        this.mSelectedTab = mSelectedTab;
        notifyDataSetChanged();
    }

    public boolean isAscending() {
        WrapperData wrapperData = mAllTypeEpisodes.get(mSelectedTab);
        return wrapperData.isAscending;
    }

    public void setAscending(boolean isAscending) {
        WrapperData wrapperData = mAllTypeEpisodes.get(mSelectedTab);
        if (wrapperData.isAscending == isAscending)
            return;
        wrapperData.isAscending = isAscending;
        Collections.reverse(wrapperData.episodes);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        WrapperData wrapperData = mAllTypeEpisodes.get(mSelectedTab);
        if (wrapperData == null || wrapperData.episodes == null)
            return 0;
        else
            return wrapperData.episodes.size();
    }

    @Override
    public Object getItem(int i) {
        WrapperData wrapperData = mAllTypeEpisodes.get(mSelectedTab);
        if (wrapperData == null || wrapperData.episodes == null)
            return null;
        else
            return wrapperData.episodes.get(i);
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
        if ("预告花絮".equals(mSelectedTab)) {
            // 如果是预告花絮，则左对齐
            holder.episodeNameView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else {
            // 如果是国语高清或国语普通，则居中
            holder.episodeNameView.setGravity(Gravity.CENTER);
        }
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
