package com.jerry.jingdong.protocol;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.utils.FileUtils;
import com.jerry.jingdong.utils.IOUtils;
import com.jerry.jingdong.utils.MD5Util;
import com.jerry.jingdong.utils.UIUtils;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @param <T>
 * @des 根据接口文档的参数来获取数据，详细看{@link HomeProtocal}
 */
public abstract class BaseProtocol<T> {
    /**
     * 加载数据,先内存,然后磁盘,最后网络
     *
     * @return
     * @throws Exception
     * @params 参数列表, 没有可以传 null
     */
    public T loadData(HttpMethod method, HashMap<String, String> params, String header) throws Exception {
        String key = generateKey(params);
        // 1.先内存-->返回
        MyApplication app = (MyApplication) UIUtils.getContext();
        Map<String, String> protocolMap = app.getProtocolMap();
        String memJsonString = protocolMap.get(key);
        if (!TextUtils.isEmpty(memJsonString)) {
            // 解析返回即可
            return parseJsonString(memJsonString);
        }

        // 2.再磁盘-->存内存,返回
        T t = loadDataFromLocal(key);
        if (t != null) {
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
     * @des 缓存唯一命中，获取指定的值
     * @param params
     * @return
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
     * @des 根据URL，拿到网络指定的内容
     * @param method
     * @param params
     * @param header
     * @return
     * @throws IOException
     */
    private T loadDataFromNet(HttpMethod method, HashMap<String, String> params, String header) throws IOException {

        String result = null; // 返回请求的数据
        RequestParams rparams = null;
        String postHead = null; //请求头
        String postValue = null;//请求体
        try {

            if (params != null && params.size() != 0) {
                rparams = new RequestParams();
                for (HashMap.Entry<String, String> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equals("userid")) {
                        postHead = key;
                        postValue = entry.getValue();
                        continue;
                    }
                    rparams.addQueryStringParameter(key, value);//添加参数头
                }
            }

            String url = MyConstants.URL.BASEURL + getInterfaceKey();
            rparams = new RequestParams(url);

            if (params == null) { // 无参
                if (header == null && method == HttpMethod.GET) {// 第一次GET请求

                    result = x.http().getSync(rparams, String.class);//请求网络并直接获取json数据

                } else if (method == HttpMethod.POST && header == null) {// 第一次POST请求
                    result = x.http().postSync(rparams, String.class);
                } else if (method == HttpMethod.GET && header != null) {
                    rparams.addHeader("userid", header);
                    result = x.http().postSync(rparams, String.class);
                } else if (method == HttpMethod.POST && header != null) {
                    rparams.addHeader("userid", header);
                    result = x.http().postSync(rparams, String.class);
                }
            } else { // 有参
                if (method == HttpMethod.GET && header == null) {
                    result = x.http().getSync(rparams, String.class);
                } else if (method == HttpMethod.POST && header == null) {
                    result = x.http().postSync(rparams, String.class);
                } else if (method == HttpMethod.GET && header != null) {
                    rparams.addHeader("userid", header);
                    result = x.http().getSync(rparams, String.class);
                } else if (method == HttpMethod.POST && header != null) {
                    rparams.addHeader("userid", header);
                    rparams.addHeader(postHead, postValue);//加入请求头
                    result = x.http().postSync(rparams, String.class);
                }
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
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

		/* =============== 2.接续网络请求回来的数据 =============== */
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
