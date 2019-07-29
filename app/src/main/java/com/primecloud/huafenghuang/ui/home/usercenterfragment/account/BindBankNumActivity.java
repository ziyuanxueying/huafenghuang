package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.OptionsPickerView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankCardBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.TipsFillBar;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BindBankNumActivity extends BasePresenterActivity<AccountPresenter, AccountModel> implements AccountContract.View {
    @BindView(R.id.act_bindbank_banknum)
    TipsFillBar actBindbankBanknum;
    @BindView(R.id.act_bindbank_name)
    TipsFillBar actBindbankName;
    @BindView(R.id.act_bindbank_bankname)
    TipsFillBar actBindbankBankname;
    @BindView(R.id.act_bindbank_next)
    Button actBindbankNext;


    private List<BankListBean.DataBean> bankList;
    private OptionsPickerView pvOptions;

    private String bankId;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_bindbanknum);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent("绑定银行卡");
        bankList = new ArrayList<>();
        mPresenter.getBankList();

        OptionsPickerView.Builder builder = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if(bankList.size() > 0){
                    actBindbankBankname.setTipEtText(bankList.get(options1).getBankName());
                    bankId = bankList.get(options1).getBankCode();
                }
            }
        });
        pvOptions = new OptionsPickerView(builder);



    }

    @Override
    protected void initEvent() {
        actBindbankBankname.getTipEt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.setPicker(bankList);
                pvOptions.show();
            }
        });
    }


    @OnClick(R.id.act_bindbank_next)
    public void onViewClicked() {

        String cardNum = actBindbankBanknum.getTipEtText();
        if (TextUtils.isEmpty(cardNum)) {
            ToastUtils.showToast(this, "请输入卡号");
            return;
        }

        if(cardNum.length() <12){
            ToastUtils.showToast(this, "请输入正确的银行卡号");
            return;
        }

        String name = actBindbankName.getTipEtText();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(this, "请输入持卡人姓名");
            return;
        }
        String bankName = actBindbankBankname.getTipEtText();
        if (TextUtils.isEmpty(bankName)) {
            ToastUtils.showToast(this, "请选择银行卡");
            return;
        }
        Intent intent = new Intent(this, BindBankNumNextActivity.class);
        intent.putExtra("cardNum", cardNum);
        intent.putExtra("name", name);
        intent.putExtra("bankName", bankName);
        intent.putExtra("bankId", bankId);
        startActivity(intent);
    }

    @Override
    public void showMyAccountData(AccountBean.DataBean accountBean) {

    }

    @Override
    public void bindBankCard() {

    }

    @Override
    public void showBankList(List<BankListBean.DataBean> banks) {

        if (banks != null) {
            bankList.addAll(banks);
        }
    }

    @Override
    public void showUserBankcardList(List<BankCardBean> bankCardLists) {

    }

    @Override
    public void unBindBankCard() {

    }

    @Override
    public void bingWxResult() {

    }

    @Override
    public void showBindMethodResult(CatchoutChannelBean catchoutChannelBean) {

    }

    @Override
    public void UnbindingWechat() {

    }

    @Override
    public void catchoutResult() {

    }

    @Override
    public void setDefaultResult() {

    }

}
