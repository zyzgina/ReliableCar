package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 盘库任务列表
 * Created Author: Gina
 * Created Date: 2017/6/1.
 */

public class TaskListBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 1
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<TaskListItem> items;

    public static class TaskListItem implements Serializable{
        /**
         * id : 3
         * taskCode : 170601-648271
         * userId : U161152257
         * userName : 陈浪
         * afc : 17015353
         * afcShortName : 百财车贷
         * afcLongName : 百财车贷
         * afcProvince : 上海
         * afcCity : 上海市
         * afcRegion : 黄浦区
         * sv : 17010141
         * svShortName : 靠谱金服
         * svLongName : 北京靠谱金服
         * svProvince : 北京
         * svCity : 北京市
         * svRegion : 朝阳区
         * sp : 17016531
         * spShortName : 真格数码
         * spLongName : 上海真格数码科技
         * spProvince : 上海
         * spCity : 上海市
         * spRegion : 长宁区
         * dlr : 17017440
         * dlrShortName : 上海申协日产
         * dlrLongName : 上海申协日产专卖店
         * dlrProvince : 上海
         * dlrCity : 上海市
         * dlrRegion : 奉贤区
         * carCount : 2
         * checkCount : 0
         * vinCount : 0
         * rfidCount : 0
         * allowVin : 0
         * allowRfid : 0
         * creationTime : 2017-06-01 16:57:09
         * startTime :
         * finishTime :
         */

        private String id;
        private String taskState;
        private String taskCode;
        private String userId;
        private String userName;
        private String afc;
        private String afcShortName;
        private String afcLongName;
        private String afcProvince;
        private String afcCity;
        private String afcRegion;
        private String sv;
        private String svShortName;
        private String svLongName;
        private String svProvince;
        private String svCity;
        private String svRegion;
        private String sp;
        private String spShortName;
        private String spLongName;
        private String spProvince;
        private String spCity;
        private String spRegion;
        private String dlr;
        private String dlrShortName;
        private String dlrLongName;
        private String dlrProvince;
        private String dlrCity;
        private String dlrRegion;
        private int carCount;
        private int checkCount;
        private int vinCount;
        private int rfidCount;
        private int allowVin;
        private int allowRfid;
        private String creationTime;
        private String startTime;
        private String finishTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTaskState() {
            return taskState;
        }

        public void setTaskState(String taskState) {
            this.taskState = taskState;
        }

        public String getTaskCode() {
            return taskCode;
        }

        public void setTaskCode(String taskCode) {
            this.taskCode = taskCode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAfc() {
            return afc;
        }

        public void setAfc(String afc) {
            this.afc = afc;
        }

        public String getAfcShortName() {
            return afcShortName;
        }

        public void setAfcShortName(String afcShortName) {
            this.afcShortName = afcShortName;
        }

        public String getAfcLongName() {
            return afcLongName;
        }

        public void setAfcLongName(String afcLongName) {
            this.afcLongName = afcLongName;
        }

        public String getAfcProvince() {
            return afcProvince;
        }

        public void setAfcProvince(String afcProvince) {
            this.afcProvince = afcProvince;
        }

        public String getAfcCity() {
            return afcCity;
        }

        public void setAfcCity(String afcCity) {
            this.afcCity = afcCity;
        }

        public String getAfcRegion() {
            return afcRegion;
        }

        public void setAfcRegion(String afcRegion) {
            this.afcRegion = afcRegion;
        }

        public String getSv() {
            return sv;
        }

        public void setSv(String sv) {
            this.sv = sv;
        }

        public String getSvShortName() {
            return svShortName;
        }

        public void setSvShortName(String svShortName) {
            this.svShortName = svShortName;
        }

        public String getSvLongName() {
            return svLongName;
        }

        public void setSvLongName(String svLongName) {
            this.svLongName = svLongName;
        }

        public String getSvProvince() {
            return svProvince;
        }

        public void setSvProvince(String svProvince) {
            this.svProvince = svProvince;
        }

        public String getSvCity() {
            return svCity;
        }

        public void setSvCity(String svCity) {
            this.svCity = svCity;
        }

        public String getSvRegion() {
            return svRegion;
        }

        public void setSvRegion(String svRegion) {
            this.svRegion = svRegion;
        }

        public String getSp() {
            return sp;
        }

        public void setSp(String sp) {
            this.sp = sp;
        }

        public String getSpShortName() {
            return spShortName;
        }

        public void setSpShortName(String spShortName) {
            this.spShortName = spShortName;
        }

        public String getSpLongName() {
            return spLongName;
        }

        public void setSpLongName(String spLongName) {
            this.spLongName = spLongName;
        }

        public String getSpProvince() {
            return spProvince;
        }

        public void setSpProvince(String spProvince) {
            this.spProvince = spProvince;
        }

        public String getSpCity() {
            return spCity;
        }

        public void setSpCity(String spCity) {
            this.spCity = spCity;
        }

        public String getSpRegion() {
            return spRegion;
        }

        public void setSpRegion(String spRegion) {
            this.spRegion = spRegion;
        }

        public String getDlr() {
            return dlr;
        }

        public void setDlr(String dlr) {
            this.dlr = dlr;
        }

        public String getDlrShortName() {
            return dlrShortName;
        }

        public void setDlrShortName(String dlrShortName) {
            this.dlrShortName = dlrShortName;
        }

        public String getDlrLongName() {
            return dlrLongName;
        }

        public void setDlrLongName(String dlrLongName) {
            this.dlrLongName = dlrLongName;
        }

        public String getDlrProvince() {
            return dlrProvince;
        }

        public void setDlrProvince(String dlrProvince) {
            this.dlrProvince = dlrProvince;
        }

        public String getDlrCity() {
            return dlrCity;
        }

        public void setDlrCity(String dlrCity) {
            this.dlrCity = dlrCity;
        }

        public String getDlrRegion() {
            return dlrRegion;
        }

        public void setDlrRegion(String dlrRegion) {
            this.dlrRegion = dlrRegion;
        }

        public int getCarCount() {
            return carCount;
        }

        public void setCarCount(int carCount) {
            this.carCount = carCount;
        }

        public int getCheckCount() {
            return checkCount;
        }

        public void setCheckCount(int checkCount) {
            this.checkCount = checkCount;
        }

        public int getVinCount() {
            return vinCount;
        }

        public void setVinCount(int vinCount) {
            this.vinCount = vinCount;
        }

        public int getRfidCount() {
            return rfidCount;
        }

        public void setRfidCount(int rfidCount) {
            this.rfidCount = rfidCount;
        }

        public int getAllowVin() {
            return allowVin;
        }

        public void setAllowVin(int allowVin) {
            this.allowVin = allowVin;
        }

        public int getAllowRfid() {
            return allowRfid;
        }

        public void setAllowRfid(int allowRfid) {
            this.allowRfid = allowRfid;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        @Override
        public String toString() {
            return "TaskListItem{" +
                    "id='" + id + '\'' +
                    ", taskState='" + taskState + '\'' +
                    ", taskCode='" + taskCode + '\'' +
                    ", userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", afc='" + afc + '\'' +
                    ", afcShortName='" + afcShortName + '\'' +
                    ", afcLongName='" + afcLongName + '\'' +
                    ", afcProvince='" + afcProvince + '\'' +
                    ", afcCity='" + afcCity + '\'' +
                    ", afcRegion='" + afcRegion + '\'' +
                    ", sv='" + sv + '\'' +
                    ", svShortName='" + svShortName + '\'' +
                    ", svLongName='" + svLongName + '\'' +
                    ", svProvince='" + svProvince + '\'' +
                    ", svCity='" + svCity + '\'' +
                    ", svRegion='" + svRegion + '\'' +
                    ", sp='" + sp + '\'' +
                    ", spShortName='" + spShortName + '\'' +
                    ", spLongName='" + spLongName + '\'' +
                    ", spProvince='" + spProvince + '\'' +
                    ", spCity='" + spCity + '\'' +
                    ", spRegion='" + spRegion + '\'' +
                    ", dlr='" + dlr + '\'' +
                    ", dlrShortName='" + dlrShortName + '\'' +
                    ", dlrLongName='" + dlrLongName + '\'' +
                    ", dlrProvince='" + dlrProvince + '\'' +
                    ", dlrCity='" + dlrCity + '\'' +
                    ", dlrRegion='" + dlrRegion + '\'' +
                    ", carCount=" + carCount +
                    ", checkCount=" + checkCount +
                    ", vinCount=" + vinCount +
                    ", rfidCount=" + rfidCount +
                    ", allowVin=" + allowVin +
                    ", allowRfid=" + allowRfid +
                    ", creationTime='" + creationTime + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", finishTime='" + finishTime + '\'' +
                    '}';
        }
    }

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

    public List<TaskListItem> getItems() {
        return items;
    }

    public void setItems(List<TaskListItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "TaskListBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }

    public static TaskListBean getTaskListBean(String result){
        TaskListBean bean=null;
        try{
            bean=new Gson().fromJson(result,new TypeToken<TaskListBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
