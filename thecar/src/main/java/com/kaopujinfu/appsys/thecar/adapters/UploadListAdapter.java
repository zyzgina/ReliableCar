package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Message;
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

import static com.kaopujinfu.appsys.thecar.R.id.num;

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
                hold.uVideo.setVisibility(View.VISIBLE);
            } else {
                finalBitmap.display(hold.icon, "file://" + file.getAbsolutePath());
                hold.uVideo.setVisibility(View.GONE);
            }
            hold.name.setText(uploadBean.getFilename());
            hold.size.setText(FileUtils.getSize(file));
            hold.progressBar.setProgress(0);
            if (status == -1 || uploadBean.process == 0 || status == 1 && i != 0 || status == 2 && i != 0) {
                hold.progress.setText("等待上传...");
            }
            LogUtils.debug("===进度显示===" + uploadBean.toString());
            if (uploadBean.process != 0) {
                hold.progressBar.setProgress(uploadBean.process);
                hold.progress.setText(uploadBean.processText);
            }
            if (status == 1 && i == 0) {
                hold.progress.setText("已暂停");
            }
            if (status == 2 && i == 0) {
                hold.progress.setText("连接失败");
            }
            view.setTag(R.id.uploadListLayout_item, i);
            views.add(view);
        }
        LogTxt.getInstance().writeLog("获取views。size=" + lists.size() + "    " + views.size());
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        views.clear();
        super.notifyDataSetChanged();
    }

    private void initData() {
        List<UploadBean> uploadBeens = db.findAllByWhere(UploadBean.class, "userid=\"" + IBase.USERID + "\"");
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
        Message message = new Message();
        message.what = IBase.CONSTANT_THREE;
        message.obj = lists.size();
        handler.sendMessage(message);
    }

    class UploadListHold {
        ImageView icon, uVideo; // 图片
        TextView name; // 名称
        ProgressBar progressBar; // 进度条
        TextView progress; // 进度
        TextView size; // 大小

        public UploadListHold(View view) {
            icon = (ImageView) view.findViewById(R.id.uploadIcon_item);
            uVideo = (ImageView) view.findViewById(R.id.uploadVideo_item);
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
        if (lists.size() > 0) {
            final UploadBean uploadBean = lists.get(0);
            uploadGoQuniu(uploadManager, uploadBean, token);
        } else {
            handler.sendEmptyMessage(IBase.CONSTANT_ONE);
        }
//        for (int i = 0; i < lists.size(); i++) {
//            final UploadBean uploadBean = lists.get(i);
//            uploadGoQuniu(uploadManager, uploadBean, token);
//        }
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
        if (flag) {
            status = 1;
        } else {
            status = 0;
        }
    }

    public void delFile() {
        FileUtils.delFolder(file.getAbsolutePath());
    }

    public void delSql() {
        db.deleteAll(UploadBean.class);
    }

    private void uploadDate(UploadBean uploadBean) {
        LogUtils.debug("进入了上传服务器：" + uploadBean.getId());
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
                }, uploadBean.getLongitude(), uploadBean.getLatitude());
    }

    private int status = -1;

    private void uploadGoQuniu(final UploadManager uploadManager, final UploadBean uploadBean, final String token) {
        final File files = new File(uploadBean.getLoactionPath());
        LogUtils.debug("===" + uploadBean.toString());
        uploadManager.put(files, uploadBean.getQny_key(), token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, final JSONObject res) {
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                if (info.isOK()) {
                    LogUtils.debug("Upload Success");
                    uploadDate(uploadBean);
                    LogTxt.getInstance().writeLog("输出上传的文件下标与大小：" + lists.size() + "   " + views.size());
                    lists.remove(0);
                    if (views.size() > 0)
                        views.remove(0);
                    LogUtils.debug(lists.size() + "_size_" + views.size());
                    Message message = new Message();
                    message.what = IBase.CONSTANT_THREE;
                    message.obj = lists.size();
                    handler.sendMessage(message);
                    if (lists.size() == 0) {
                        handler.sendEmptyMessage(IBase.CONSTANT_ONE);
                    } else {
                        LogUtils.debug("上传数字：" + num + "  总数据:" + lists.size());
                        if (lists.size() > 0) {
                            UploadBean uBean = lists.get(0);
                            uploadGoQuniu(uploadManager, uBean, token);
                        }
                    }
                    notifyDataSetChanged();
                    db.deleteByWhere(UploadBean.class, "loactionPath=\"" + uploadBean.getLoactionPath() + "\"");
                } else {
                    LogUtils.debug("Upload Fail " + files.exists());
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    if (files.exists()) {
                        if (!flag) {
                            status = 2;
                            flag = true;
                            IBaseMethod.showToast(mContext, "连接失败", IBase.RETAIL_TWO);
                        }
                        notifyDataSetChanged();
                        handler.sendEmptyMessage(IBase.CONSTANT_TWO);
                    } else {
                        lists.remove(0);
                        if (lists.size() == 0) {
                            handler.sendEmptyMessage(IBase.CONSTANT_ONE);
                        } else {
                            if (lists.size() > 0) {
                                UploadBean uBean = lists.get(0);
                                uploadGoQuniu(uploadManager, uBean, token);
                            }
                        }
                        notifyDataSetChanged();
                        db.deleteByWhere(UploadBean.class, "loactionPath=\"" + uploadBean.getLoactionPath() + "\"");
                        if (views.size() > 0)
                            views.remove(0);
                        LogUtils.debug("文件不存在:" + lists.size() + "_size_" + views.size());
                    }
                }
//                    LogUtils.debug(key + ",\r\n " + info + ",\r\n " + res);
            }
        }, new UploadOptions(null, null, true,
                new UpProgressHandler() {
                    public void progress(String key, double percent) {
                        View view = null;
                            /* 匹配视图对象 */
                        if (views.size() > 0) {
                            view = views.get(0);
                            if (view != null) {
                                //将视图对象中缓存的ViewHolder对象取出，并使用该对象对控件进行更新
                                int size = (int) (files.length() * percent);
                                percent = percent * 100;
                                if (percent >= 100) {
                                    percent = 99;
                                }
//                                LogUtils.debug(key + "上传进度:" + percent);
//                                viewHolder.progress.setText(("当前进度:" + (int) percent) + "%");
                                String sp = FileUtils.getSize(size);
                                uploadBean.process = (int) percent;
                                uploadBean.processText = sp;
                                notifyDataSetChanged();
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
