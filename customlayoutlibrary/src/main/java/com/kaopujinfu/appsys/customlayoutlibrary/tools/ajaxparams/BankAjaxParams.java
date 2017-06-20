package com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams;

import android.content.Context;

import com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils.AjaxParams;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;

/**
 * 库融参数
 * Created by 左丽姬 on 2017/5/19.
 */

public class BankAjaxParams {
    private Context mContext;

    public BankAjaxParams(Context context) {
        mContext = context;

    }


    /**
     * 登录接口参数
     *
     * @param name 用户名
     * @param pwd  密码
     * @param code 手机验证码
     */
    public AjaxParams login(String name, String pwd, String code) {
        AjaxParams params = new AjaxParams();
        params.put("action", "LOGIN");
        params.put("user_id", name);
        params.put("password", pwd);
        if (!GeneralUtils.isEmpty(code)) {
            params.put("mobile_code", code);
        }
        return params;
    }

    /**
     * 获取个人信息
     *
     * @param action
     */
    public AjaxParams getAction(String action) {
        AjaxParams params = BANKUserIDSID();
        params.put("action", action);
        return params;
    }


    /**
     * 监管器绑定-》获取小圆盘列表
     */
    public AjaxParams ajaxBindingLists() {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", IBaseUrl.ACTION_BANK_BINDING_LISTS);
        return params;
    }

    /**
     * 获取Token
     *
     * @return
     */
    public AjaxParams getTokenAjax() {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", "QINIU_TOKEN");
        return params;
    }

    /**
     * 上传七牛云成功后通知服务器
     *
     * @param bizType
     * @param bizId
     * @param storeKey
     * @param fileName
     * @param fileSize
     * @return
     */
    public AjaxParams getUploadSuccess(String bizType, String bizId, String storeKey, String fileName, String fileSize) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", "UPLOAD");
        params.put("bizType", bizType);
        params.put("bizId", bizId);
        params.put("storeKey", storeKey);
        params.put("fileName", fileName);
        params.put("fileSize", fileSize);
        return params;
    }

    /**
     * 文档绑定列表
     *
     * @return
     */
    public AjaxParams ajaxDocumentList() {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", "DOC_LIST");
        return params;
    }

    /**
     * 注册一个待入柜文档
     *
     * @param vinNo  vin码
     * @param rfidId 标签编号
     * @return
     */
    public AjaxParams ajaxNewDocument(String vinNo, String rfidId) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", "REG");
        params.put("vinNo", vinNo);
        params.put("rfidId", rfidId);
        return params;
    }

    /**
     * 监管器绑定-》小圆盘的绑定
     */
    public AjaxParams ajaxBindingAdd(String vinNo, String devCode) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", IBaseUrl.ACTION_BANK_BINDING);
        params.put("vinNo", vinNo);
        params.put("devCode", devCode);
        return params;
    }

    /**
     * 获取经销商列表
     *
     * @return
     */
    public AjaxParams ajaxDlrs(String... action) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", action[0]); // 默认FIND_SHORT
        if (action.length == 2) {
            params.put("keyword", action[1]);
        }
        if (action.length > 2) {
            params.put("lngAmap", action[1]);
            params.put("latAmap", action[2]);
        }
        return params;
    }


    /**
     * 盘库-任务列表
     */
    public AjaxParams ajaxTaskList() {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", IBaseUrl.MY_TASK_LIST);
        return params;
    }

    /**
     * 盘库 --任务所含车辆清单
     */
    public AjaxParams ajaxTaskItem(String taskCode) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", IBaseUrl.ITEM_IN_TASK);
        params.put("taskCode", taskCode);
        return params;
    }

    /**
     * 新建一个盘库任务
     */
    public AjaxParams ajaxAddTask(String userCompanyCode, String checkUser, String checkCompany) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", IBaseUrl.NEW_TASK);
        params.put("userCompanyCode", userCompanyCode);
        params.put("checkUser", checkUser);
        params.put("checkCompany", checkCompany);
        return params;
    }


    /**
     * 删除盘库任务
     */
    public AjaxParams ajaxDelTask(String taskCode) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", IBaseUrl.DEL_TASK);
        params.put("taskCode", taskCode);
        return params;
    }

    /**
     * 盘库--提交预审
     */
    public AjaxParams ajaxSubmitTask(String taskCode, String json) {
        AjaxParams params = BANKUserIDSIDCompany();
        params.put("action", IBaseUrl.SUBMIT_TASK);
        params.put("json", json);
        params.put("taskCode", taskCode);
        return params;
    }

    /**
     * 新建车辆
     */
    public AjaxParams ajaxVinBrand(String vinCode) {
        AjaxParams params = BANKUserIDSID();
        params.put("action", IBaseUrl.ACTION_VIN_INFO);
        params.put("vin", vinCode);
        return params;
    }

    /**
     * 查询车辆品牌列表
     * 查询车辆子品牌列表
     * 查询车辆型号列表
     *
     * @param keyword    模糊查询
     * @param brandId    品牌ID
     * @param subBrandId 子品牌ID
     */
    public AjaxParams getCat(String keyword, String brandId, String subBrandId) {
        AjaxParams params = BANKUserIDSID();
        if (GeneralUtils.isEmpty(brandId) && GeneralUtils.isEmpty(subBrandId))
            params.put("action", IBaseUrl.ACTION_LIST_BRAND);
        else if (GeneralUtils.isEmpty(subBrandId)) {
            params.put("action", IBaseUrl.ACTION_LIST_SUB_BRAND);
            params.put("brandId", brandId);
        } else {
            params.put("action", IBaseUrl.ACTION_LIST_MODEL);
            params.put("brandId", brandId);
            params.put("subBrandId", subBrandId);
        }
        params.put("keyword", keyword);
        return params;
    }

    public AjaxParams ajaxCommitNewCar(String... strings) {
        AjaxParams ajaxParams = BANKUserIDSID();
        ajaxParams.put("action", "NEW");
        if (strings.length > 0) {
            ajaxParams.put("dlr", strings[0]);
        }
        if (strings.length > 1) {
            ajaxParams.put("vinNo", strings[1]);
        }
        if (strings.length > 2) {
            ajaxParams.put("usedCar", strings[2]);
        }
        if (strings.length > 3) {
            ajaxParams.put("carBrand", strings[3]);
        }
        if (strings.length > 4) {
            ajaxParams.put("carSubBrand", strings[4]);
        }
        if (strings.length > 5) {
            ajaxParams.put("carModel", strings[5]);
        }
        if (strings.length > 6) {
            ajaxParams.put("carPrice", strings[6]);
        }
        if (strings.length > 7) {
            ajaxParams.put("carTotalKM", strings[7]);
        }
        if (strings.length > 8) {
            ajaxParams.put("carRegDate", strings[8]);
        }

        return ajaxParams;
    }

    /**
     * 车辆列表
     */
    public AjaxParams ajaxNewCarList() {
        AjaxParams params = BANKUserIDSID();
        params.put("action", IBaseUrl.ACTION_APP_LIST);
        return params;
    }

    /**
     * 车辆绑标签
     *
     * @param strs 0,vin码  1标签
     * @return AjaxParams
     */
    public AjaxParams ajaxAddLable(String... strs) {
        AjaxParams params = BANKUserIDSID();
        params.put("action", IBaseUrl.ACTION_BINDRFID);
        params.put("vinNo", strs[0]);
        params.put("rfidId", strs[1]);
        return params;
    }
    /**
     * 车辆绑标签
     */
    public AjaxParams ajaxLableList() {
        AjaxParams params = BANKUserIDSID();
        params.put("action", IBaseUrl.BIND_RFID_LIST);
        return params;
    }

    /**
     * 设置登录用户，所属session ，公司code参数
     *
     * @return AjaxParams
     */
    public AjaxParams BANKUserIDSIDCompany() {
        AjaxParams params = BANKUserIDSID();
        params.put("companyCode", IBase.COMPANY_CODE);//用户所属公司CODE
        return params;
    }

    /**
     * 设置登录用户，所属session
     *
     * @return AjaxParams
     */
    public AjaxParams BANKUserIDSID() {
        AjaxParams params = new AjaxParams();
        IBaseMethod.setBaseInfo(mContext);
        params.put("user_id", IBase.USERID);//登录的用户ID
        params.put("s_id", IBase.SESSIONID);//登录用户所属sessionID
        return params;
    }
}
