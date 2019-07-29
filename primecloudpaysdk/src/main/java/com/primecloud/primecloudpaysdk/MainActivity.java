package com.primecloud.primecloudpaysdk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.primecloud.primecloudpaysdk.version1_0.CreateOrderBean;
import com.primecloud.primecloudpaysdk.version1_0.PayManagement;
import com.primecloud.primecloudpaysdk.version1_0.PayParameter;
import com.primecloud.primecloudpaysdk.version1_0.PayResult;
import com.primecloud.primecloudpaysdk.version2_0.ConfigParameter;
import com.primecloud.primecloudpaysdk.version2_0.PaySDK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private SimpleDateFormat format;
    private PayManagement m;

    private String url;



    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    Log.i("sss", "resultInfo========="+resultInfo.toString()+"   payResult====="+payResult.toString());
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付失败" + payResult.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

        ;
    };
    private PaySDK paySDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = ((EditText)findViewById(R.id.et)).getText().toString().trim();
        if (TextUtils.isEmpty(url)) {
            url = "http://10.10.19.1:8080/HFH-webapi/epay/createorder";
//            url = "http://v2.5.issac.top/api/order/createOrderFromV1_3";
        }

        Log.i("sss","url:"+url);


        m = new PayManagement(this, mHandler, url, addHead());


        paySDK = PaySDK.getPaySDKInstance(this);
        ConfigParameter configParameter = new ConfigParameter(url);
//        configParameter.setHeadMap(addHead());
        configParameter.setCallBack(new TestDealCallBack(this));
        paySDK.registerRequestCofig(configParameter);

        applyPermission();
    }

    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;

    private void applyPermission() {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            Toast.makeText(MainActivity.this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }


    public void start(View v) {
        Log.i("sss", "start");
        format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        m.sendPay("Android-" + format.format(new Date()), "1", "0", "1", "888888", "999999", "teacher", "user");

        format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        PayParameter payParameter = new PayParameter();
        payParameter.setProductId("67");
        payParameter.setOrderType("9");

        payParameter.setLoginId("211434fe855283488518919c7679ef32");
        payParameter.setType("1");//赠课类型，默认1
        payParameter.setUserName("13693348305");
        payParameter.setUserId("2003");
        payParameter.setNum("1");
        payParameter.setPayType("1");

        m.sendPay(payParameter);

    }

    public void alipay(View v) {
        Log.i("sss", "start");
        format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        PayParameter payParameter = new PayParameter();
        payParameter.setProductId("10");
        payParameter.setOrderType("3");

        payParameter.setLoginId("C0:EE:FB:D2:70:68");
        payParameter.setType("1");//赠课类型，默认1
        payParameter.setUserName("18810476496");
        payParameter.setUserId("3338");
        payParameter.setNum("2");
        payParameter.setPayType("1");

        m.sendPay(payParameter);

    }


    public void newAli(View view) {
        CreateOrderBean createOrderBean = new CreateOrderBean();
        createOrderBean.setUserId("858");
        createOrderBean.setUserName("aaa");
        createOrderBean.setEventId("1");
        createOrderBean.setGroupId("1");
        createOrderBean.setPlatform("2");
        createOrderBean.setOrderType("6");
        createOrderBean.setPayType("0");
        paySDK.getOrderInfo(createOrderBean, PaySDK.PayMethod.AL_PAY);


    }

    public void newWx(View view) {

        PayParameter payParameter = new PayParameter();
        payParameter.setProductId("67");
        payParameter.setOrderType("9");

        payParameter.setLoginId("211434fe855283488518919c7679ef32");
        payParameter.setType("1");//赠课类型，默认1
        payParameter.setUserName("13693348305");
        payParameter.setUserId("2003");
        payParameter.setNum("1");
        payParameter.setPayType("1");
//        CreateOrderBean createOrderBean = new CreateOrderBean();
//        createOrderBean.setUserId("858");
//        createOrderBean.setUserName("aaa");
//        createOrderBean.setEventId("1");
//        createOrderBean.setGroupId("1");
//        createOrderBean.setPlatform("2");
//        createOrderBean.setOrderType("6");
//        createOrderBean.setPayType("1");
        paySDK.getOrderInfo(payParameter, PaySDK.PayMethod.WX_PAY);

    }

    /**
     * 添加头信息
     */
    public static Map<String, String> addHead() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("yd-app-version", "v1.0.5");
        map.put("yd-user-id", "169");
        map.put("yd-system-info", "MX5");
        map.put("yd-system-version", "android:5.1");
        map.put("yd-token", "ae60faf161830f3227301ab18e0c0cd0");
        return map;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("sss", "权限已申请");
            } else {
                Log.i("sss", "权限已拒绝");
            }
        }else if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        Log.i("sss", "权限未申请");
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
