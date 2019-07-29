package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BingBankCardList;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;

import retrofit2.Response;

public class AccountPresenter extends AccountContract.Presenter {
    @Override
    public void getMyAccountFirstPage(String userId) {
        mRxManager.add(mModel.getMyAccountFirstPage(userId).subscribe(new BaseSubscrible<Response<AccountBean>>() {
            @Override
            public void onSuccess(Response<AccountBean> accountBeanResponse) {
                mView.showMyAccountData(accountBeanResponse.body().getData());
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {
            }
        }));
    }

    @Override
    public void sendBankSmsCode(String phone) {
        mRxManager.add(mModel.sendBankSmsCode(phone).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> stringResponse) {
                mView.onRequestError("发送成功");
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void userBindingBankcard(String userId, String cardNumber, String cardName, String bankId, String smscode, String phone) {
        mRxManager.add(mModel.userBindingBankcard(userId, cardNumber, cardName, bankId, smscode, phone).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> stringResponse) {
                mView.bindBankCard();
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void getBankList() {
        mRxManager.add(mModel.getBankList().subscribe(new BaseSubscrible<Response<BankListBean>>() {
            @Override
            public void onSuccess(Response<BankListBean> bankListBeanResponse) {
                mView.showBankList(bankListBeanResponse.body().getData());
            }

            @Override
            public void onFail(String errorMsg) {

            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void getUserBankcardList(String userId) {
        mRxManager.add(mModel.getUserBankcardList(userId).subscribe(new BaseSubscrible<Response<BingBankCardList>>() {
            @Override
            public void onSuccess(Response<BingBankCardList> bingBankCardListResponse) {
                mView.showUserBankcardList(bingBankCardListResponse.body().getData());
            }

            @Override
            public void onFail(String errorMsg) {

                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void userUnbindingBankcard(String userId, String cardId) {
        mRxManager.add(mModel.userUnbindingBankcard(userId, cardId).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> stringResponse) {
                mView.unBindBankCard();
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void userBindingWechat(String userId, String openId) {
        mRxManager.add(mModel.userBindingWechat(userId, openId).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> objectResponse) {
                mView.bingWxResult();
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }


    @Override
    public void getCatchoutChannel(String userId) {
        mRxManager.add(mModel.getCatchoutChannel(userId).subscribe(new BaseSubscrible<Response<CatchoutChannelBean>>() {
            @Override
            public void onSuccess(Response<CatchoutChannelBean> catchoutChannelBeanResponse) {
                mView.showBindMethodResult(catchoutChannelBeanResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void userUnbindingWechat(String userId) {
        mRxManager.add(mModel.userUnbindingWechat(userId).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> objectResponse) {
                mView.UnbindingWechat();
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void catchout(String userId, int type, int amount, int bankcardId) {
        mRxManager.add(mModel.catchout(userId, type, amount, bankcardId).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> objectResponse) {
                mView.catchoutResult();
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    public void setDefaultBankcard(String userId, String cardId) {
        mRxManager.add(mModel.setDefaultBankcard(userId, cardId).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> objectResponse) {
                mView.setDefaultResult();
            }

            @Override
            public void onFail(String errorMsg) {

                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }


}
