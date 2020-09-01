package com.ytfu.yuntaifawu.utils;

import android.content.Context;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;

import org.json.JSONException;
import org.json.JSONObject;

import me.jessyan.autosize.utils.LogUtils;

public class MiIdHelper implements IIdentifierListener {

    interface OnMiIdCallback {
        void onSupport(IdSupplier idSupplier);

        void onUnSupport(IdSupplier idSupplier);
    }

    private OnMiIdCallback callback;

    public MiIdHelper(OnMiIdCallback callback) {
        this.callback = callback;
    }


    public void getDeviceIds(Context context) {
        int code = MdidSdkHelper.InitSdk(context, true, this);
        LogUtils.e("code ===== " + code);
    }

    @Override
    public void OnSupport(boolean b, IdSupplier idSupplier) {

        if (b) {
            if (null != idSupplier) {
                callback.onSupport(idSupplier);
            } else {
                callback.onUnSupport(null);
            }
        } else {
            callback.onUnSupport(idSupplier);
        }
    }


    private String descriptionCode(int code) {
        switch (code) {
            case ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT:
                return "DEVICE_NOSUPPORT";
            case ErrorCode.INIT_ERROR_LOAD_CONFIGFILE:
                return "LOAD_CONFIGFILE";
            case ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT:
                return "MANUFACTURER_NOSUPPORT";
            case ErrorCode.INIT_ERROR_RESULT_DELAY:
                return "RESULT_DELAY";
            case ErrorCode.INIT_HELPER_CALL_ERROR:
                return "HELPER_CALL_ERROR";
            default:
                return "SUCCESS";
        }
    }
}
