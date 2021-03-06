package com.yixun.pettyloan.model;

import android.text.TextUtils;

import com.yixun.pettyloan.model.http.ApiException;
import com.yixun.pettyloan.rx.base.BaseView;
import com.yixun.pettyloan.utils.LogUtils;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by zongkaili on 2017/8/29.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ApiException) {
            mView.showErrorMsg(e.toString());
        } else if (e instanceof HttpException) {
//            if (!TextUtils.isEmpty(e.getMessage()))
//                mView.showErrorMsg(e.getMessage());
//            else
//                mView.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            if (!TextUtils.isEmpty(e.getMessage()))
                mView.showErrorMsg(e.getMessage());
            else
                mView.showErrorMsg("未知错误ヽ(≧Д≦)ノ");
            LogUtils.logd(e.toString());
        }
        if (isShowErrorState) {
            mView.stateError(e);
        }
    }
}
