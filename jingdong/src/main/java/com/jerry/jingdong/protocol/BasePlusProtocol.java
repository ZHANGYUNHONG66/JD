package com.jerry.jingdong.protocol;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.utils.FileUtils;
import com.jerry.jingdong.utils.IOUtils;
import com.jerry.jingdong.utils.MD5Util;
import com.jerry.jingdong.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public abstract class BasePlusProtocol<T> {

    /**
     * 加载数据,先内存,然后磁盘,最后网络
     *
     * @return
     * @throws Exception
     * @params 参数列表, 没有可以传 null
     */
    public T loadData(HttpRequest.HttpMethod method, HashMap<String, String> params, String header) throws Exception {
        String key = generateKey(params);
        // 1.先内存-->返回
        MyApplication app = (MyApplication) UIUtils.getContext();
        Map<String, String> protocolMap = app.getProtocolMap();
        String memJsonString = protocolMap.get(key);
        if (!TextUtils.isEmpty(memJsonString)) {
            //LogUtils.i("###从内存加载数据-->" + key);
            // 解析返回即可
            return parseJsonString(memJsonString);
        }

        // 2.再磁盘-->存内存,返回
        T t = loadDataFromLocal(key);
        if (t != null) {
            // LogUtils.i("###磁盘加载数据-->" + getCacheFile(key).getAbsolutePath());
            return t;
        }

        // 3.最后网络-->存内存,存磁盘,返回

        t = loadDataFromNet(method, params, header);

        return t;
    }

    /**
     * 从本地加载数据
     */
    private T loadDataFromLocal(String key) {

        File cacheFile = getCacheFile(key);

        if (cacheFile.exists()) {
            BufferedReader reader = null;
            // 读取缓存的生成时间/插入时间

            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                // 读取插入时间

                // 如何组织具体内容和插入时间
                String insertTime = reader.readLine();
                Long insertTime_ = Long.parseLong(insertTime);

                // 当前时间-插入时间和过期时间做判断
                if (System.currentTimeMillis() - insertTime_ < MyConstants.PROTOCOLTIMEOUT) {

                    // 读取缓存内容
                    String diskJsonString = reader.readLine();
                    if (!TextUtils.isEmpty(diskJsonString)) {

                        // 存内存
                        MyApplication app = (MyApplication) UIUtils.getContext();
                        app.getProtocolMap().put(key, diskJsonString);

                        // 返回
                        return parseJsonString(diskJsonString);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    /**
     * 得到缓存的文件
     *
     * @param key
     * @return
     */
    @NonNull
    private File getCacheFile(String key) {
        String dir = FileUtils.getDir("json");// sdcard/Android/data/包目录/json目录
        String name = key;// home.0 home.20
        return new File(dir, name);
    }

    /**
     * 缓存的唯一索引
     *
     * @return
     * @params 参数的键值对
     */
    private String generateKey(HashMap<String, String> params) {
        StringBuffer buf = new StringBuffer();
        if (params == null)
            return MD5Util.md5(MyConstants.URL.BASEURL + getInterfaceKey() + "?" + "");
        for (HashMap.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            buf.append(key + "=" + value);
        }

        String keyString = MyConstants.URL.BASEURL + getInterfaceKey() + "?" + buf.toString();
        return MD5Util.md5(keyString);
    }


    /**
     * 从网络加载数据
     */
    private T loadDataFromNet(HttpRequest.HttpMethod method, HashMap<String, String> params, String header) throws IOException {
        /*=============== 1.得到网络请求回来的jsonString ===============*/

        HttpUtils httpUtils = new HttpUtils();
        ResponseStream responseStream = null;
        String result = null;
        RequestParams rparams = null;
        String postHead = null;
        String postValue = null;
        try {

            if (params != null && params.size() != 0) {
                rparams = new RequestParams();
                for (HashMap.Entry<String, String> entry : params.entrySet()) {

                    String key = entry.getKey();
                    String value = entry.getValue();

                    if (key.equals("userid")){
                        postHead = key;
                        postValue = entry.getValue();
                        continue;
                    }
                    rparams.addQueryStringParameter(key, value);
                }
            }
            String url = MyConstants.URL.BASEURL + getInterfaceKey();
            if (params == null) {
                if (method == HttpRequest.HttpMethod.GET && header == null) {
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url);
                } else if (method == HttpRequest.HttpMethod.POST && header == null) {
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.POST, url);
                } else if (method == HttpRequest.HttpMethod.GET && header != null) {
                    rparams = new RequestParams();
                    rparams.addHeader("userid", header);
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, rparams);
                }else if(method == HttpRequest.HttpMethod.POST && header != null){
                    rparams = new RequestParams();
                    rparams.addHeader("userid", header);
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.POST, url, rparams);
                }
            } else {
                if (method == HttpRequest.HttpMethod.GET&& header == null) {
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, rparams);

                } else if (method == HttpRequest.HttpMethod.POST&& header == null) {
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.POST, url, rparams);
                }else if(method == HttpRequest.HttpMethod.GET&& header != null){

                    rparams.addHeader("userid", header);
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, rparams);
                }else  if(method == HttpRequest.HttpMethod.POST&& header != null){

                    rparams.addHeader("userid", header);

                    rparams.addHeader(postHead,postValue);
                    responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.POST, url, rparams);
                }
            }

            result = responseStream.readString();
            //Log.d("andy", result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        MyApplication app = (MyApplication) UIUtils.getContext();
        String key = generateKey(params);
        app.getProtocolMap().put(key, result);

        // 存本地
        File cacheFile = getCacheFile(key);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(cacheFile));
            // 缓存的生成时间
            writer.write(System.currentTimeMillis() + "");
            // 换行
            writer.newLine();
            // 缓存的具体内容
            writer.write(result);
        } finally {
            IOUtils.close(writer);
        }

        //LogUtils.i("resultJsonString:" + resultJsonString);

		/*=============== 2.接续网络请求回来的数据 ===============*/
        // 解析json
        return parseJsonString(result);
    }

    /**
     * @return
     * @des 返回协议的关键字
     * @des 必须的
     */
    public abstract String getInterfaceKey();

    /**
     * @param resultJsonString
     * @return
     * @des 具体解析请求回来数据
     * @des 必须的
     */
    public abstract T parseJsonString(String resultJsonString);
}
