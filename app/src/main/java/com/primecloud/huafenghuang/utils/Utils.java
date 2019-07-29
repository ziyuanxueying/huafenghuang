package com.primecloud.huafenghuang.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.store.MethodsCompat;
import com.primecloud.huafenghuang.ui.home.HomeActivity;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.library.baselibrary.utils.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.content.Context.DOWNLOAD_SERVICE;

public class Utils {

    public static void setSwipeRefreshLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
    }

    /*
     * 设置tabLayout的下划线长度和间距
     * */
    public static void reflex(final TabLayout tabLayout, final int leftmagin, final int rightmagin) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        mTextView.setMaxLines(1);
                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = leftmagin;
                        params.rightMargin = rightmagin;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * @判断 是否含有sim卡
     */
    public static boolean readSIMCard(Context context) {
//        TelephonyManager manager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);// 取得相关系统服务
//        String imsi = manager.getSubscriberId(); // 取出IMSI
//        if (imsi == null || imsi.length() <= 0) {
//            return false;
//        } else {
//            return true;
//        }

        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        return result;

    }

    // 年月日转Date
    public static Calendar StringToCalendar(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = null;
        try {
            Date date = sdf.parse(dateStr);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return calendar;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * 计算缓存的大小
     */
    public static String caculateCacheSize(Context context) {
        long fileSize = 0;
        String cacheSize = "0KB";
        try {
            File filesDir = MyApplication.getInstance().getFilesDir();
            File cacheDir = MyApplication.getInstance().getCacheDir();

            fileSize += FileUtils.getDirSize(filesDir);
            fileSize += FileUtils.getDirSize(cacheDir);
            // 2.2版本才有将应用缓存转移到sd卡的功能
            if (MyApplication.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
                File externalCacheDir = MethodsCompat.getExternalCacheDir(context);
                fileSize += FileUtils.getDirSize(externalCacheDir);
                fileSize += FileUtils.getDirSize(new File(PathUtils.getRootPath()));
            }
            if (fileSize > 0) {
                cacheSize = FileUtils.formatFileSize(fileSize);
                return cacheSize;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheSize;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载图片
     */
    public static void loadImage(String imgUrl, final Context context) {

        //由当前时间组成图片路径
        String timeSring = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgPath = Environment.getExternalStorageDirectory() + "/HuaFengHuang";
        File f = new File(imgPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        imgPath = imgPath + "/img_" + timeSring + ".jpg";
        //使用 xutils.HttpUtils 工具利用图片uri网络地址下载图片
        HttpUtils httpUtils = new HttpUtils();
        /*
         * 图片url网络地址
         * URL是WWW上的资源定位器，所以你只有把图片上传到网上以后才有URL。
         * */


        if (TextUtils.isEmpty(imgUrl)) {
            ToastUtils.showToast(context, "图片地址有误,下载失败");
        }

//        Utils.showLog("tempPath:" + imgPath);
        //执行下载，传入网络图片路径和保存位置
        httpUtils.download(imgUrl, imgPath, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {


                File file = new File(responseInfo.result.getPath());
                Utils.scannerMedia(file, context);
////                MediaScanner mediaScanner = new MediaScanner(ShowImageActivity.this);
////                String[] filePaths = new String[]{file.getAbsolutePath()};
////                String[] mimeTypes = new String[]{MimeTypeMap.getSingleton().getMimeTypeFromExtension("image/jpeg")};
////                mediaScanner.scanFiles(filePaths, mimeTypes);
//
//                if(file.exists()){
//                    file.delete();
//                }
                Toast.makeText(context, context.getResources().getString(R.string.save_image2album), Toast.LENGTH_SHORT).show();
//                try {
//                    String str = LoadImgUtils.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), "图片");
//                    Toast.makeText(context, context.getResources().getString(R.string.save_image2album), Toast.LENGTH_SHORT).show();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }


//                ScannerMediaUtils.savePic(context, file);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                ToastUtils.showToast(context, "保存失败" + msg);
            }
        });

    }

    /**
     * 刷新媒体库
     *
     * @param file
     * @param context
     */
    public static void scannerMedia(File file, Context context) {
        try {
            String imageUri = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), "图片: " + file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            Toast.makeText(context, context.getResources().getString(R.string.save_image2album), Toast.LENGTH_SHORT).show();
        } else {
            //4.4开始不允许发送"Intent.ACTION_MEDIA_MOUNTED"广播, 否则会出现: Permission Denial: not allowed to send broadcast android.intent.action.MEDIA_MOUNTED from pid=15410, uid=10135
            Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            context.sendBroadcast(intent);
            Toast.makeText(context, context.getResources().getString(R.string.save_image2album), Toast.LENGTH_SHORT).show();
        }
    }

    public static void getUserInfo(Context context, String userId, int toMain) {
        FengHuangApi.getUserInformation(userId, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                UserInfo userInfo = JSON.parseObject(body.getData(), UserInfo.class);
                userInfo.setToken(MyApplication.getInstance().getToken());
                MyApplication.doLogin(context, userInfo);
//                XLog.i("hhh", "getUserInfo====="+userInfo.toString());

                if (toMain == 1) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    EventBus.getDefault().post(3);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });

    }

    /*
     * 登录
     * */
    public static boolean isLogin(Activity activity) {
        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {

            return true;
        } else {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }

        return false;
    }

    public static RequestBody getJsonRequestBody(String body) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, body);
        return requestBody;
    }

    // 获取文本的body
    public static RequestBody getTextRequestBody(String body) {
        return RequestBody.create(MediaType.parse("form-data"), body);
    }

    // 获取图片的requestBody
    public static RequestBody getFileRequestBody(File file) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file);
    }

    /**
     * 用逗号拼接字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 剪切到剪切板
     *
     * @param message
     * @param context
     */
    public static void copyToClipBoard(String message, Context context) {
        //获取剪贴版
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//创建ClipData对象
//第一个参数只是一个标记，随便传入。
//第二个参数是要复制到剪贴版的内容
        ClipData clip = ClipData.newPlainText("simple text", message);
//传入clipdata对象.
        clipboard.setPrimaryClip(clip);

        ToastUtils.showToast(context, "成功复制到剪切板");

    }

    public static void downLoadVideo(Context context, String path) {
        if(TextUtils.isEmpty(path)){
            ToastUtils.showToast(context, "下载地址损坏");
            return;
        }

        //获取到下载得到的图片，进行本地保存
        File pictureFolder = Environment.getExternalStorageDirectory();
        //第二个参数为你想要保存的目录名称
        File appDir = new File(pictureFolder, "HuaFengHuang");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
        String fileName = System.currentTimeMillis()+".mp4";
        request.setDestinationInExternalPublicDir("HuaFengHuang", fileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //start 一些非必要的设置
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setVisibleInDownloadsUi(true);
        request.setTitle(fileName);
       //end 一些非必要的设置

        DownloadVideoReceiver downloadVideoReceiver = new DownloadVideoReceiver(appDir.getAbsolutePath()+"/"+fileName);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadVideoReceiver, filter);


        long downloadId = downloadManager.enqueue(request);


    }

    /**
     * 格式化银行卡号
     *
     * @param cardNumber
     * @return
     */
    public static String cardNumFormat(String cardNumber) {

        if (TextUtils.isEmpty(cardNumber))
            return "";
        int len = cardNumber.length();
        int end = len - (len % 4 == 0 ? 4 : len % 4);
        String string = cardNumber.substring(4, end).replaceAll("(.)", "*");
        String splitStr = cardNumber.substring(0, 4) + string + cardNumber.substring(end);
        splitStr = splitStr.replaceAll("(.{4})", "$0 ");
        return splitStr;
    }


    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * 屏蔽表情
     *
     * @param editText
     */
    public static void setEmojiFilter(EditText editText) {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {

                    return "";
                }
                return null;
            }
        };

        editText.setFilters(new InputFilter[]{emojiFilter});
    }


    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public static String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }


}
