package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BingBankCardList;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

public class AccountModel implements AccountContract.Model{

    @Override
    public Observable<Response<AccountBean>> getMyAccountFirstPage(String userId) {
        return NetWorks.getInstance()
                .getApi()
                .getMyAccountFirstPage(userId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> sendBankSmsCode(String phone) {
        return NetWorks.getInstance()
                .getApi()
                .sendBankSmsCode(phone)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> userBindingBankcard(String userId, String cardNumber, String cardName, String bankId, String smscode, String phone) {
        return NetWorks.getInstance()
                .getApi()
                .userBindingBankcard(userId, cardNumber, cardName, bankId, smscode, phone)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<BankListBean>> getBankList() {
        return NetWorks.getInstance()
                .getApi()
                .getBankList()
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<BingBankCardList>> getUserBankcardList(String userId) {
        return NetWorks.getInstance()
                .getApi()
                .getUserBankcardList(userId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> userUnbindingBankcard(String userId, String cardId) {
        return NetWorks.getInstance()
                .getApi()
                .userUnbindingBankcard(userId, cardId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> userBindingWechat(String userId, String openId) {
        return NetWorks.getInstance()
                .getApi()
                .userBindingWechat(userId, openId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<CatchoutChannelBean>> getCatchoutChannel(String userId) {
        return NetWorks.getInstance()
                .getApi()
                .getCatchoutChannel(userId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> userUnbindingWechat(String userId) {
        return NetWorks.getInstance()
                .getApi()
                .userUnbindingWechat(userId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> catchout(String userId, int type, int amount, int bankcardId) {
        return  NetWorks.getInstance()
                .getApi()
                .catchout(userId, type, amount, bankcardId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> setDefaultBankcard(String userId, String cardId) {
        RequestBody uId = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),userId);
        RequestBody cId = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),cardId);
        return NetWorks.getInstance()
                .getApi()
                .setDefaultBankcard(uId, cId)
                .compose(RxSchedulerHelper.applySchedulers());
    }


}
