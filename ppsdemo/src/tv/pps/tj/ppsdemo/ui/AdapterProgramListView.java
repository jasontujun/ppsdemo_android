package tv.pps.tj.ppsdemo.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.data.model.ProgramBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-17
 * Time: 上午9:19
 * To change this template use File | Settings | File Templates.
 */
public class AdapterProgramListView extends BaseAdapter {

    private Context mContext;
    private List<ProgramBase> mProgramBases;

    public AdapterProgramListView(Context context) {
        mContext = context;
        mProgramBases = new ArrayList<ProgramBase>();
    }



    @Override
    public int getCount() {
        return mProgramBases.size();
    }

    @Override
    public Object getItem(int i) {
        return mProgramBases.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    private class ViewHolder {
        public View frame;
        public ImageView programImageView;
        public ImageView programRankView;
        public TextView programNameView;
        public TextView programTypeView;
        public TextView onlineNumberView;
        public TextView programScoreView;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.program_listview_item, null);
            holder = new ViewHolder();
            holder.frame = convertView.findViewById(R.id.item_frame);
            holder.programImageView = (ImageView) convertView.findViewById(R.id.program_img);
            holder.programRankView = (ImageView) convertView.findViewById(R.id.program_rank);
            holder.programNameView = (TextView) convertView.findViewById(R.id.program_name);
            holder.programTypeView = (TextView) convertView.findViewById(R.id.program_type);
            holder.onlineNumberView = (TextView) convertView.findViewById(R.id.online_number);
            holder.programScoreView = (TextView) convertView.findViewById(R.id.program_score);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProgramBase programBase = (ProgramBase) getItem(i);
        switch (i) {
            case 0:
                holder.programRankView.setVisibility(View.VISIBLE);
                holder.programRankView.setImageResource(R.drawable.ic_ss_number1);
                break;
            case 1:
                holder.programRankView.setVisibility(View.VISIBLE);
                holder.programRankView.setImageResource(R.drawable.ic_ss_number2);
                break;
            case 2:
                holder.programRankView.setVisibility(View.VISIBLE);
                holder.programRankView.setImageResource(R.drawable.ic_ss_number3);
                break;
            default:
                holder.programRankView.setVisibility(View.GONE);
                break;
        }

        return convertView;
    }
}
