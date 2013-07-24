package tv.pps.tj.ppsdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import tv.pps.tj.ppsdemo.R;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-23
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
public class FragmentLeftMenu extends Fragment {

    private ListView mMenuList;
    private LeftMenuAdapter mMenuListAdapter;
    private AdapterView.OnItemClickListener mMenuItemListener;
    private int mCurrentSelectedIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentSelectedIndex = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.left_menu_frame, container, false);
        mMenuList = (ListView) rootView.findViewById(R.id.menu_list);
        mMenuListAdapter = new LeftMenuAdapter();
        mMenuList.setAdapter(mMenuListAdapter);
        mMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentSelectedIndex = i;
                mMenuListAdapter.notifyDataSetChanged();

                // 通知activity
                if (mMenuItemListener != null)
                    mMenuItemListener.onItemClick(adapterView, view, i, l);
            }
        });
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ActivityMain) {
            ActivityMain hostActivity = (ActivityMain) activity;
            mMenuItemListener = hostActivity.getMenuItemClickListener();
        }
    }

    /**
     * 菜单栏列表的adapter
     */
    private class LeftMenuAdapter extends BaseAdapter {

        private int[] iconResourceId;

        private LeftMenuAdapter() {
            iconResourceId = new int[] {R.drawable.ic_left_home, R.drawable.ic_left_search,
                    R.drawable.ic_left_ipd, R.drawable.ic_left_vip, R.drawable.ic_left_fav_his,
                    R.drawable.ic_left_download, R.drawable.ic_game,
                    R.drawable.ic_left_book, R.drawable.ic_left_setting};
        }

        @Override
        public int getCount() {
            return iconResourceId.length;
        }

        @Override
        public Object getItem(int i) {
            return iconResourceId[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        private class ViewHolder {
            public ImageView mTipView;
            public ImageView mIconView;
        }
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.left_menu_item, null);
                holder = new ViewHolder();
                holder.mTipView = (ImageView) convertView.findViewById(R.id.tip);
                holder.mIconView = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mIconView.setImageResource(iconResourceId[i]);
            if (i == mCurrentSelectedIndex)
                holder.mTipView.setVisibility(View.VISIBLE);
            else
                holder.mTipView.setVisibility(View.GONE);

            return convertView;
        }
    }
}
