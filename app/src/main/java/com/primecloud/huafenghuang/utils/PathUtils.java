package com.primecloud.huafenghuang.utils;


import android.os.Environment;

import com.primecloud.library.baselibrary.log.XLog;

import java.io.File;

/**
 * 文件路径建立
 **/
public class PathUtils {
	// 存储目录
	private static String StorePath = null;

	// 根目录
	private static final String STPS_DIRECTORY = "/FengHuang";

	// 图片存储目录
	private static String DIR_IMAGES = null;

	// 音频存储目录
	private static String DIR_RECORD = null;

	// 缓存存储目录
	private static String DIR_CACHE = null;

	// 下载存储目录
	private static String DIR_DOWNLOAD = null;

	// 日志记录存储目录
	private static String DIR_LOGS = null;

	static {
		StorePath = getRootPath();
		DIR_IMAGES = StorePath + "/YDimages/";
		DIR_RECORD = StorePath + "/YDrecord/";
		DIR_CACHE = StorePath + "/YDcache/";
		DIR_DOWNLOAD = StorePath + "/YDdownload/";
		DIR_LOGS = StorePath + "/YDlogs/";
	}

	/**
	 * 判断手机是否安装SDCard
	 *
	 * @return
	 */
	public static boolean isSDCardMounted() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取手机存储根目录
	 *
	 * @return
	 */
	public static String getStoreRootPath() {
		if (isSDCardMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			return Environment.getDataDirectory().getAbsolutePath();
		}
	}

	/**
	 * 获取根目录
	 *
	 * @return
	 */
	public static String getRootPath() {
		StorePath = getStoreRootPath() + STPS_DIRECTORY;
		return StorePath;
	}

	/**
	 * 创建文件存储目录
	 */
	public static void createFilePath() {

		File file = new File(DIR_IMAGES);
		if (!file.exists())
			file.mkdirs();

		// 创建音频存储目录
		file = new File(DIR_RECORD);
		if (!file.exists())
			file.mkdirs();

		// 创建缓存存储目录
		file = new File(DIR_CACHE);
		if (!file.exists())
			file.mkdirs();

		// 创建文件下载存储目录
		file = new File(DIR_DOWNLOAD);
		if (!file.exists())
			file.mkdirs();
		XLog.i("sss","file========"+file.getAbsolutePath());
		// 创建日志存储目录
		file = new File(DIR_LOGS);
		if (!file.exists())
			file.mkdirs();
	}

	/**
	 * 获取图片目录
	 *
	 * @return
	 */
	public static String getImagesPath() {
		File file = new File(DIR_IMAGES);
		if (!file.exists())
			file.mkdirs();
		return file.getAbsolutePath() + File.separator;
	}

	/**
	 * 获取音频目录
	 *
	 * @return
	 */
	public static String getRecordPath() {
		File file = new File(DIR_RECORD);
		if (!file.exists())
			file.mkdirs();
		return file.getAbsolutePath() + File.separator;
	}

	/**
	 * 获取缓存目录
	 *
	 * @return
	 */
	public static String getCachePath() {
		File file = new File(DIR_CACHE);
		if (!file.exists())
			file.mkdirs();
		return file.getAbsolutePath() + File.separator;
	}

	/**
	 * 获取下载目录
	 *
	 * @return
	 */
	public static String getDownloadPath() {
		File file = new File(DIR_DOWNLOAD);
		if (!file.exists())
			file.mkdirs();
		return file.getAbsolutePath() + File.separator;
	}

	/**
	 * 获取记录日志目录
	 *
	 * @return
	 */
	public static String getLogPath() {
		File file = new File(DIR_LOGS);
		if (!file.exists())
			file.mkdirs();
		return file.getAbsolutePath() + File.separator;
	}


}
