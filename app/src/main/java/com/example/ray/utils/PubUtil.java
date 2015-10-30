package com.example.ray.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * @discribtion:系统工具类
 */
public class PubUtil {
	private static PubUtil instance = new PubUtil();


	private PubUtil() {
	}

	public static PubUtil getInstance() {
		return instance;
	}


	/**
	 * 检查网络连接状况
	 *
	 * @return true为有连接
	 */
	public boolean checkNetwork(Context context) {
		boolean bRet = false;
		ConnectivityManager connectivityManager = null;
		NetworkInfo networkInfo = null;
		try {
			// 实例化ConnectivityManager
			connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			// 获得当前网络信息
			networkInfo = connectivityManager.getActiveNetworkInfo();
			// 判断是否连接
			if (null != networkInfo && networkInfo.isConnected()) {
				bRet = true;
				return bRet;
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			return bRet;
		}
	}

}