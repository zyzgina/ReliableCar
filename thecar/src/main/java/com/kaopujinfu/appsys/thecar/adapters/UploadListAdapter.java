package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.thecar.LoginActivity;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.upload.UploadListActivity;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 上传列表适配器
 * Created by Doris on 2017/5/16.
 */
public class UploadListAdapter extends BaseAdapter {

    private Context mContext;
    private FinalBitmap finalBitmap;
    //    private List<File> files;
    private List<UploadBean> lists;
    private String uploadPath;
    private List<View> views;
    private Handler handler;
    private FinalDb db;
    private File file;

    public UploadListAdapter(Context context, String uploadPath, Handler handler) {
        mContext = context;
        finalBitmap = FinalBitmap.create(mContext);
        this.uploadPath = uploadPath;
        file = new File(this.uploadPath);
        views = new ArrayList<>();
        this.handler = handler;
        db = FinalDb.create(mContext, IBase.BASE_DATE, true);
//        db.deleteAll(UploadBean.class);
//        db.deleteByWhere(UploadBean.class, "label=\"外观全景图\"");
        initData();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public UploadBean getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UploadListHold hold;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_uploadlist, null);
            hold = new UploadListHold(view);
            view.setTag(hold);
        } else {
            hold = (UploadListHold) view.getTag();
        }
        UploadBean uploadBean = lists.get(i);
        File file = new File(uploadBean.getLoactionPath());
        if (file.exists()) {
            String lastName = file.getName();
            lastName = lastName.substring(lastName.indexOf("."));
            if (".mp4".equalsIgnoreCase(lastName)) {
                getVideoImage(file, hold.icon);
            } else {
                finalBitmap.display(hold.icon, "file://" + file.getAbsolutePath());
            }
            hold.name.setText(file.getName());
            hold.size.setText(FileUtils.getSize(file));
            hold.progressBar.setProgress(0);
            hold.progress.setText("0%");
            view.setTag(R.id.uploadListLayout_item, i);
            views.add(view);
        }
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        views.clear();
        super.notifyDataSetChanged();
    }

    private void initData() {
        List<UploadBean> uploadBeens = db.findAll(UploadBean.class);
        for (UploadBean uploadBean : uploadBeens) {
            LogUtils.debug(uploadBean.toString());
        }
        //降序排列
        Collections.sort(uploadBeens, new Comparator<UploadBean>() {
            @Override
            public int compare(UploadBean o1, UploadBean o2) {
                if (o1.getId() > o2.getId())
                    return -1;
                else if (o1.getId() < o2.getId())
                    return 1;
                else
                    return 0;
            }
        });
        lists = uploadBeens;
    }

    class UploadListHold {
        ImageView icon; // 图片
        TextView name; // 名称
        ProgressBar progressBar; // 进度条
        TextView progress; // 进度
        TextView size; // 大小

        public UploadListHold(View view) {
            icon = (ImageView) view.findViewById(R.id.uploadIcon_item);
            name = (TextView) view.findViewById(R.id.uploadName_item);
            progressBar = (ProgressBar) view.findViewById(R.id.uploadProgressBar_item);
            progress = (TextView) view.findViewById(R.id.uploadProgress_item);
            size = (TextView) view.findViewById(R.id.uploadSize_item);
        }
    }

    public void uploadFile(UploadManager uploadManager) {
        String token = getToken();
        if (GeneralUtils.isEmpty(token)) {
            return;
        }
        for (int i = 0; i < lists.size(); i++) {
            final UploadBean uploadBean = lists.get(i);
//            LogUtils.debug("===" + uploadBean.toString());
            final File file = new File(uploadBean.getLoactionPath());
            LogUtils.debug("pathNmae==" + uploadBean.getQny_key());
            uploadManager.put(file, uploadBean.getQny_key(), token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, final JSONObject res) {
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if (info.isOK()) {
                        LogUtils.debug("Upload Success");
                        lists.remove(0);
                        LogUtils.debug(lists.size() + "_size_" + views.remove(0));
                        if (lists.size() == 0) {
                            handler.sendEmptyMessage(IBase.CONSTANT_ONE);
                        }
                        notifyDataSetChanged();
                        HttpBank.getIntence(mContext).uploadSuccess(uploadBean.getLabel(),
                                uploadBean.getVinCode(), uploadBean.getQny_key(),
                                uploadBean.getFilename(), uploadBean.getFilesize(), new CallBack() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        LogUtils.debug("上传" + o.toString());
                                        Result result = Result.getMcJson(o.toString());
                                        if (result.isSuccess() && lists.size() == 0) {
                                            IBaseMethod.showToast(mContext, "上传完成", IBase.RETAIL_ONE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(int errorNo, String strMsg) {
                                        IBaseMethod.showToast(mContext, "上传出错", IBase.RETAIL_ZERO);
                                    }
                                });
                        db.deleteByWhere(UploadBean.class, "loactionPath=\"" + uploadBean.getLoactionPath() + "\"");
                    } else {
                        LogUtils.debug("Upload Fail " + file.exists());
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        int i = 1;
                        if (file.exists()) {
                            for (View view : views) {
                                if (view != null) {
                                    //将视图对象中缓存的ViewHolder对象取出，并使用该对象对控件进行更新
                                    UploadListHold viewHolder = (UploadListHold) view.getTag();
                                    if (!flag) {
                                        if (i == views.size()) {
                                            flag = true;
                                            IBaseMethod.showToast(mContext, "连接失败", IBase.RETAIL_TWO);
                                        }
                                        viewHolder.progress.setText("上传失败");
                                        i++;
                                    } else {
                                        viewHolder.progress.setText("已暂停");
                                    }
                                }
                            }
                            handler.sendEmptyMessage(IBase.CONSTANT_TWO);
                        } else {
                            lists.remove(0);
                            if (lists.size() == 0) {
                                handler.sendEmptyMessage(IBase.CONSTANT_ONE);
                            }
                            notifyDataSetChanged();
                            db.deleteByWhere(UploadBean.class, "loactionPath=\"" + uploadBean.getLoactionPath() + "\"");
                            LogUtils.debug("文件不存在:" + lists.size() + "_size_" + views.remove(0));

                        }

                    }
//                    LogUtils.debug(key + ",\r\n " + info + ",\r\n " + res);
                }
            }, new UploadOptions(null, null, false,
                    new UpProgressHandler() {
                        public void progress(String key, double percent) {
                            View view = null;
                            /* 匹配视图对象 */
                            if (views.size() > 0) {
                                view = views.get(0);
                                if (view != null) {
                                    //将视图对象中缓存的ViewHolder对象取出，并使用该对象对控件进行更新
                                    UploadListHold viewHolder = (UploadListHold) view.getTag();
                                    percent = percent * 100;
                                    viewHolder.progressBar.setProgress((int) percent);
                                    viewHolder.progress.setText((int) percent + "%");
                                }
                            }
                            LogUtils.debug(key + ": " + percent);
                        }
                    }, new UpCancellationSignal() {
                @Override
                public boolean isCancelled() {
                    return flag;
                }
            }));
        }
    }

    private String getToken() {
        final StringBuffer stringBuffer = new StringBuffer();
        Long currentTime = System.currentTimeMillis();
        Long uploadTime = (Long) SPUtils.get(mContext, "currentUploadTime", currentTime);
        if (currentTime - uploadTime == 0 || currentTime - uploadTime >= 24 * 60 * 60 * 1000) {
            // 重新获取
            IBaseMethod.showToast(mContext, "token无效,请重新登录", IBase.RETAIL_TWO);
            SPUtils.remove(mContext, "login_user_password");
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            ((UploadListActivity) mContext).finish();
        } else {
            // 在24小时之内无需重新获取
            LogTxt.getInstance().writeLog("从SP获取Token");
            stringBuffer.append((String) SPUtils.get(mContext, "uploadToken", ""));
        }
        return stringBuffer.toString();
    }

    private void getVideoImage(File file, ImageView imageView) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(file.getAbsolutePath());
        Bitmap bitmap = media.getFrameAtTime();
        imageView.setImageBitmap(bitmap);
    }

    private boolean flag = false;

    public void setUpload(boolean flag) {
        this.flag = flag;
    }

    public void delFile() {
        FileUtils.delFolder(file.getAbsolutePath());
    }

    public void delSql() {
        db.deleteAll(UploadBean.class);
    }

}
