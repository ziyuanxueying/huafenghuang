package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankCardBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BingBankCardList;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

public interface AccountContract {

    interface View extends BaseView {
        void showMyAccountData(AccountBean.DataBean accountBean);

        void bindBankCard();

        void showBankList(List<BankListBean.DataBean> banks);

        void showUserBankcardList(List<BankCardBean> bankCardLists);

        void unBindBankCard();

        void bingWxResult();

        void showBindMethodResult(CatchoutChannelBean catchoutChannelBean);

        void UnbindingWechat();

        void catchoutResult();

        void setDefaultResult();
    }
    interface Model extends BaseModel {

        //获取我的账户页首页数据
        Observable<Response<AccountBean>> getMyAccountFirstPage(String userId);

        /**
         * 发送银行卡短信验证码
         * @param phone
         * @return
         */
        Observable<Response<Object>> sendBankSmsCode(String phone);


        /**
         * 用户绑定银行卡接口
         * @param userId
         * @param cardNumber
         * @param cardName
         * @param bankId
         * @param smscode
         * @return
         */
        Observable<Response<Object>> userBindingBankcard(String userId, String cardNumber, String cardName, String bankId, String smscode, String phone);

        /**
         * 获取银行卡列表
         * @return
         */
        Observable<Response<BankListBean>> getBankList();

        /**
         * 获取用户绑定的所有银行卡
         * @param userId
         * @return
         */

        Observable<Response<BingBankCardList>> getUserBankcardList(String userId);


        /**
         * 用户解绑银行卡接口
         * @param userId
         * @param cardId
         * @return
         */
        Observable<Response<Object>> userUnbindingBankcard(String userId, String cardId);

        /**
         * 用户绑定提现微信接口
         * @param userId
         * @return
         */
        Observable<Response<Object>> userBindingWechat(String userId, String openId);

        /**
         * 获取用户所有已绑定的提现方式
         * @param userId
         * @return
         */
        Observable<Response<CatchoutChannelBean>> getCatchoutChannel(String userId);

        /**
         * 用户解绑提现微信接口
         * @param userId
         * @return
         */
        Observable<Response<Object>> userUnbindingWechat(String userId);


        /**
         * 银行卡提现
         * @param userId
         * @param type 1微信提现 2 银行卡
         * @param amount
         * @param bankcardId
         * @return
         */
        Observable<Response<Object>> catchout(String userId, int type, int amount, int bankcardId);

        /**
         * 设置用户默认银行卡
         * @return
         */
        Observable<Response<Object>> setDefaultBankcard(String userId, String cardId);


    }
    abstract class  Presenter extends BasePresenter<View, Model> {

        public abstract void getMyAccountFirstPage(String userId);
        /**
         * 发送银行卡短信验证码
         * @param phone
         * @return
         */
        public abstract void sendBankSmsCode(String phone);

        public abstract void userBindingBankcard(String userId, String cardNumber, String cardName, String bankId, String smscode, String phone);

        public abstract void getBankList();

        public abstract void  getUserBankcardList(String userId);

        public abstract void  userUnbindingBankcard(String userId, String cardId);

        public abstract void  userBindingWechat(String userId, String openId);

        public abstract void  getCatchoutChannel(String userId);

        public abstract void  userUnbindingWechat(String userId);

        public abstract void  catchout(String userId, int type, int amount, int bankcardId);

        public abstract void  setDefaultBankcard(String userId, String cardId);


    }
}
