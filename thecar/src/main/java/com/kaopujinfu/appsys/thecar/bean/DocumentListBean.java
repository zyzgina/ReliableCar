package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

import java.util.List;

/**
 * 文档收录列表
 * Created by Doris on 2017/5/22.
 */
public class DocumentListBean {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 2
     * items : [{"id":1,"rfidId":"00000119","vinNo":"WP1AA2959GLB01895","regTime":"2017-05-19 10:43:06.0","regDate":"2017-05-19","dlrName":"上海申协日产专卖店","status":"待入柜"},{"id":2,"rfidId":"00000123","vinNo":"11111111111111111","regTime":"2017-05-19 10:54:52.0","regDate":"2017-05-19","dlrName":"上海申协日产专卖店","status":"待入柜"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<ItemsBean> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 1
         * rfidId : 00000119
         * vinNo : WP1AA2959GLB01895
         * regTime : 2017-05-19 10:43:06.0
         * regDate : 2017-05-19
         * dlrName : 上海申协日产专卖店
         * status : 待入柜
         */

        private int id;
        private String rfidId;
        private String vinNo;
        private String regTime;
        private String regDate;
        private String dlrName;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRfidId() {
            return rfidId;
        }

        public void setRfidId(String rfidId) {
            this.rfidId = rfidId;
        }

        public String getVinNo() {
            return vinNo;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getDlrName() {
            return dlrName;
        }

        public void setDlrName(String dlrName) {
            this.dlrName = dlrName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    /**
     * 文档收录解析
     * @param result
     * @return
     * */
    public static DocumentListBean getDocumentList(String result) {
        DocumentListBean resultBean = null;
        try {
            resultBean = new Gson().fromJson(result, new TypeToken<DocumentListBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            LogTxt.getInstance().writeLog("文档收录解析出错");
            LogUtils.debug("文档收录解析出错");
        }
        return resultBean;
    }
}
