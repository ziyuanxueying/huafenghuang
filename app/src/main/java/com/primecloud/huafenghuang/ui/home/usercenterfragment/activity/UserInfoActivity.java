package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.application.AppConfig;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.UIHelper;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info.InfoContract;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info.InfoModel;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info.InfoPresenter;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info.UserHead;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SimpleBackPage;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.CityBean;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.GetJsonDataUtil;
import com.primecloud.huafenghuang.utils.PictureUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.CircleImageView;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;
import com.primecloud.library.baselibrary.log.XLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BasePresenterActivity<InfoPresenter, InfoModel> implements InfoContract.View, TimePickerView.OnTimeSelectListener {


    @BindView(R.id.info_head)
    CircleImageView infoHead;
    @BindView(R.id.head_info)
    RelativeLayout headInfo;
    @BindView(R.id.sb_info_nick)
    SettingBar sbInfoNick;
    @BindView(R.id.sb_info_birth)
    SettingBar sbInfoBirth;
    @BindView(R.id.sb_info_phone)
    SettingBar sbInfoPhone;
    @BindView(R.id.sb_info_in)
    SettingBar sbInfoIn;
    @BindView(R.id.sb_info_add)
    SettingBar sbInfoAdd;

    OptionsPickerView pvOptions;//地区弹框
    private TimePickerView timePic;
    ;//出生年月提弹框


    //处理省市二级联动
    private ArrayList<CityBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    //    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
//    private ArrayList<String> bankNameList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private String pId = "";
    private String cId ="";
    private int provinceId = 0;//选择器下标
    private int cityId = 0;//选择器下标
    private int proId =0 ;//数据库的下标
    private int citId =0;//数据库的下标

    private String birthDay = "2019-5-10";
    private boolean[] type = new boolean[]{true, true, true, false, false, false};//显示类型 默认全部显示

    //判断地址数据是否获取成功
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LOAD_DATA://解析数据
                    if (thread == null) {
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //子线程里面解析数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS://解析成功
                    sbInfoAdd.setRightText(pId + "-" + cId);
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED://解析失败
                    break;
            }
        }
    };
    List<LocalMedia> List = null;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.fragment_info);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.text_info_title));
        timePic = new TimePickerView.Builder(UserInfoActivity.this, this).setType(type).build();
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (null != userInfo) {
            //初始化地区信息--省市
            mHandler.sendEmptyMessage(MSG_LOAD_DATA);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .dontAnimate();//图片加载失败后，显示的图片

            Glide.with(this)
                    .load(userInfo.getPic()) //图片地址
                    .apply(options)
                    .into(infoHead);
            sbInfoNick.setRightText(userInfo.getUsername());
            if (StringUtils.notBlank(userInfo.getBirthday())) {
                birthDay = userInfo.getBirthday();
            }

            sbInfoBirth.setRightText(StringUtils.notBlank(userInfo.getBirthday()) ? userInfo.getBirthday() : "");
            sbInfoPhone.setRightText(StringUtils.notBlank(userInfo.getPhone()) ? userInfo.getPhone() : "");
            sbInfoIn.setRightText(StringUtils.notBlank(userInfo.getRecommenderName()) ? userInfo.getRecommenderName() : "");
            citId = userInfo.getCityId();
            proId = userInfo.getProvinceId();

        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.head_info, R.id.sb_info_nick, R.id.sb_info_birth, R.id.sb_info_add})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.head_info:
                DialogUtils.choosePicDialog(UserInfoActivity.this, headOnclick);
                break;
            case R.id.sb_info_nick:

                UIHelper.showSimpleBack(UserInfoActivity.this, SimpleBackPage.Change_Nick);
                break;
            case R.id.sb_info_birth:
                if (StringUtils.notBlank(birthDay)) {
                    java.util.Calendar calendar = Utils.StringToCalendar(birthDay);
                    if (calendar != null) {
                        timePic.setDate(calendar);
                    }

                }
                timePic.show();
                break;
            case R.id.sb_info_add:
                ShowPickerView();
                break;
            default:
                break;
        }
    }

    private void initJsonData() {//解析数据

        String CityData = new GetJsonDataUtil().getJson(UserInfoActivity.this, "result.json");//获取assets目录下的json文件数据
        ArrayList<CityBean> jsonBean = parseData(CityData);//用Gson 转成实体
        options1Items = jsonBean;


        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            if(String.valueOf(proId).equals(jsonBean.get(i).getProvinceID())){
                pId = jsonBean.get(i).getProvinceName();
            }
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            for (int c = 0; c < jsonBean.get(i).getCitys().size(); c++) {
                if(String.valueOf(citId).equals(jsonBean.get(i).getCitys().get(c).getCityID())){
                    cId = jsonBean.get(i).getCitys().get(c).getCityName();
                }
                String CityName = jsonBean.get(i).getCitys().get(c).getCityName();
                CityList.add(CityName);//添加城市
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<CityBean> parseData(String result) {//Gson 解析
        ArrayList<CityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private void ShowPickerView() {// 弹出地址选择器

        pvOptions = new OptionsPickerView.Builder(UserInfoActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                cityId = options1;
                provinceId = options2;
                pId = options1Items.get(cityId).getProvinceID();
                cId = options1Items.get(cityId).getCitys().get(provinceId).getCityID();

                setAddress(cId,pId);


            }
        })

                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();


        pvOptions.setPicker(options1Items, options2Items);//二级选择器（市区）
        pvOptions.show();
    }


    private View.OnClickListener headOnclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_camera:
                    PictureUtils.takeCameraPicture(UserInfoActivity.this, true);
                    break;
                case R.id.btn_photo:
                    PictureUtils.takeGallryPicture(UserInfoActivity.this, 1, null, true);
                    break;
                case R.id.dialog_pic_cancel:
                    break;
            }
            DialogUtils.dismiss();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:// 相册
                    // 图片、视频、音频选择结果回调
                    dealPic(PictureSelector.obtainMultipleResult(data));
                    break;
                case PictureConfig.REQUEST_CAMERA:// 拍照
                    dealPic(PictureSelector.obtainMultipleResult(data));
                    break;
            }

        }
    }

    private void dealPic(List<LocalMedia> selectList) {
        if (selectList != null) {
            List = selectList;
            mPresenter.upLoadHead(MyApplication.getInstance().getUserInfo().getId(), selectList);
        }


    }

    @Override
    public void onTimeSelect(Date date, View v) {
        String strTime = Utils.dateToString(date, "yyyyMMdd");//用户选择的日期
        String string = Utils.dateToString(date,"yyyy-MM-dd");
        birthDay = strTime;
        setBirthDay(birthDay,string);

    }

    @Override
    public void successHead(UserHead userInfo) {
        Glide.with(this)
                .load(userInfo.getData().getPic()) //图片地址
                .into(infoHead);
    }

    @Override
    public void onFHead(String message) {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(2);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changePage(String str) {
        sbInfoNick.setRightText(str);
    }



    private  void setAddress(String cityd,String provincId){
        if(null!=MyApplication.getInstance().getUserInfo() && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            FengHuangApi.updateUserAddress(MyApplication.getInstance().getUserInfo().getId(), cityd, provincId, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    String province = options1Items.get(cityId).getPickerViewText();
                    String city = options2Items.get(cityId).get(provinceId);
                    sbInfoAdd.setRightText(province + "-" + city);
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }
    }

    private void setBirthDay(String birthday,String bir){
        if(null!=MyApplication.getInstance().getUserInfo() && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())){
            FengHuangApi.updateUserBirthday(MyApplication.getInstance().getUserInfo().getId(), birthday, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    sbInfoBirth.setRightText(bir);
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }

    }
}
