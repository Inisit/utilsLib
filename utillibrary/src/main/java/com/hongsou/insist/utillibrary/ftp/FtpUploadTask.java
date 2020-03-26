package com.hongsou.insist.utillibrary.ftp;

import android.os.Handler;
import android.util.Log;
import com.hongsou.insist.utillibrary.ThreadPoolUtils;

import java.io.File;


/**
 * @author lpc
 *         <p>
 * @copyright 鸿搜网络公司 版权所有
 * <p>
 * @date 2018/7/30
 * <p>
 * @desc 上传本地文件（夹）到ftp
 */

public class FtpUploadTask {
    //ftp工具类
    private FtpHelper ftpHelper;
    //回调
    private FtpNetCallBack callBack;
    //ftp文件夹目录
    private String ftpFolder;
    //本地文件夹路径
    private String localFilePath;

    private Runnable mRunnable ;
    private Handler mHandler = new Handler();

    public FtpUploadTask(final FtpHelper ftpHelper, final FtpNetCallBack callBack, final String localFilePath, final String ftpFolder) {
        this.ftpHelper = ftpHelper;
        this.callBack = callBack;
        this.ftpFolder = ftpFolder;
        this.localFilePath = localFilePath;

        upload(ftpHelper, callBack, localFilePath, ftpFolder);
    }

    public FtpUploadTask(final FtpHelper ftpHelper, final FtpNetCallBack callBack, final String localFilePath, String fileName, final String ftpFolder) {
        this.ftpHelper = ftpHelper;
        this.callBack = callBack;
        this.ftpFolder = ftpFolder;
        this.localFilePath = localFilePath;

        upload(ftpHelper, callBack, localFilePath, fileName, ftpFolder);
    }

    private void upload(final FtpHelper ftpHelper, final FtpNetCallBack callBack, final String localFilePath, final String ftpFolder) {
        if (mRunnable == null){
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    boolean result = false;
                    try {
                        if (ftpHelper != null){
                            ftpHelper.openConnect();
                        }
                        if (ftpHelper != null && ftpHelper.isConnect()) {
                            //判断本地文件是否是文件夹
                            File localFile = new File(localFilePath);
                            if (localFile.exists() && localFile.isDirectory()) {
                                //上传文件夹
                                int count = ftpHelper.uploadFolder(localFilePath, ftpFolder);
                                if (count > 0) {
                                    //上传数量大于0时
                                    result = true;
                                }
                            } else if (localFile.exists() && localFile.isFile()) {
                                //上传文件
                                result = ftpHelper.uploadFile(localFilePath, ftpFolder);
                                ftpHelper.closeConnect();
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Exception", "run: " + e.toString());
                        e.printStackTrace();
                        callBack.uploadFinish(false);
                    }
                    callBack.uploadFinish(result);
                }
            };
        }
        ThreadPoolUtils.execute(mRunnable);
    }

    private void upload(final FtpHelper ftpHelper, final FtpNetCallBack callBack, final String localFilePath, final String fileName, final String ftpFolder) {
        if (mRunnable == null){
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    boolean result = false;
                    try {
                        if (ftpHelper != null){
                            ftpHelper.openConnect();
                        }
                        if (ftpHelper != null && ftpHelper.isConnect()) {
                            //判断本地文件是否是文件夹
                            File localFile = new File(localFilePath);
                            if (localFile.exists() && localFile.isDirectory()) {
                                //上传文件夹
                                int count = ftpHelper.uploadFolder(localFilePath, ftpFolder);
                                if (count > 0) {
                                    //上传数量大于0时
                                    result = true;
                                }
                            } else if (localFile.exists() && localFile.isFile()) {
                                //上传文件
                                result = ftpHelper.uploadFile(localFilePath, fileName, ftpFolder);
                                ftpHelper.closeConnect();
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Exception", "run: " + e.toString());
                        e.printStackTrace();
                        callBack.uploadFinish(false);
                    }
                    callBack.uploadFinish(result);
                }
            };
        }
        ThreadPoolUtils.execute(mRunnable);
    }
}
