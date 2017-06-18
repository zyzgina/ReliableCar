package com.kaopujinfu.appsys.customlayoutlibrary.menu;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ContextMenuItem;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义上下文菜单
 * Created by zuoliji on 2017/3/30.
 */

public class SHContextMenu {

    private Context mContext;
    private List<ContextMenuItem> itemList;
    private PopupWindow popupWindow;
    private View contentView;
    private ListView mLvMenuList;
    private MenuAdapter menuAdapter;
    private OnItemSelectListener onItemSelectListener;

    public interface OnItemSelectListener{
        void onItemSelect(int position);
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
    }

    public SHContextMenu(Context mContext){
        this.mContext = mContext;
        itemList = new ArrayList<>();
        initPopWindow();
    }

    /**
     * 初始化popwindow菜单
     */
    private void initPopWindow(){
        contentView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_menu, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        backgroundAlpha((float) 0.4);
        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });


        mLvMenuList = (ListView) contentView.findViewById(R.id.lv_menu);
        menuAdapter = new MenuAdapter();
        mLvMenuList.setAdapter(menuAdapter);
        mLvMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemSelectListener != null){
                    if(itemList.get(position).getTag() > 0){
                        onItemSelectListener.onItemSelect(itemList.get(position).getTag());
                    }else{
                        onItemSelectListener.onItemSelect(position);
                    }
                }
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams lp =((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
    /**
     * 设置菜单列表数据源
     * @param itemList
     */
    public void setItemList(List<ContextMenuItem> itemList){
        this.itemList = itemList;
        menuAdapter.notifyDataSetChanged();
    }

    public void showMenu(){
        if (popupWindow == null)
            return;
        // 状态栏的高度
        Rect frame = new Rect();
        if(mContext instanceof ActivityGroup) {
            ActivityGroup activity = (ActivityGroup) mContext;
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, mContext.getResources().getDisplayMetrics());
            if (activity.getActionBar() != null){
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP| Gravity.RIGHT, 0,activity.getActionBar().getHeight()+ frame.top - dp);
            } else {
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP| Gravity.RIGHT, 0, CommonUtil.dip2px(mContext, 48) + frame.top - dp);
            }
        }
        else{
            Activity activity = (Activity)mContext;
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, mContext.getResources().getDisplayMetrics());
            if (activity.getActionBar() != null){
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP| Gravity.RIGHT, 0,activity.getActionBar().getHeight()+ frame.top - dp);
            } else {
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP| Gravity.RIGHT, 0, CommonUtil.dip2px(mContext, 50) + frame.top - dp);
            }
        }
    }

    /**
     * 上下文菜单列表适配器
     */
    class MenuAdapter extends BaseAdapter {

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return itemList == null ? 0 : itemList.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.popmenu_item, null);
                viewHolder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.iv_popmenu= (ImageView) convertView.findViewById(R.id.iv_popmenu);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mTvTitle.setText(itemList.get(position).getTitle());
            if(itemList.get(position).getImgDrawable()!=null) {
                viewHolder.iv_popmenu.setVisibility(View.VISIBLE);
                Drawable drawable=itemList.get(position).getImgDrawable();
                viewHolder.iv_popmenu.setImageDrawable(drawable);
                viewHolder.mTvTitle.setGravity(Gravity.LEFT);
            }
            else{
                viewHolder.iv_popmenu.setVisibility(View.GONE);
            }
            return convertView;
        }

        class ViewHolder{
            TextView mTvTitle;
            ImageView iv_popmenu;
        }
    }
}
