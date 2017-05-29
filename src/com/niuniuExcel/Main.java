package com.niuniuExcel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Future;

public class Main {
    public static int count = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        do {
            astronVote();
        } while (true);
    }

    private static void astronVoteUsingProxy() throws IOException {
        Document doc = Jsoup.connect("http://free-proxy-list.net/").get();
        Elements proxys = doc.select("tbody tr");
        for (int i = 0; i < proxys.size(); i++) {
            String ip = proxys.get(i).select("td").get(0).text();
            int port = Integer.parseInt(proxys.get(i).select("td").get(1).text());
            String scheme = proxys.get(i).select("td").get(6).text();
            HttpHost host;
            if (scheme.equals("yes")) {
                host = new HttpHost(ip, port, "https");
            } else {
                host = new HttpHost(ip, port);
            }
            astronVote(host);
        }
    }

    private static void astronVote() throws IOException {
        Connection.Response res = Jsoup.connect("http://www.stepon.top/misc/voicevote/index" +
                ".php?from=groupmessage&isappinstalled=0")
                .method(Connection.Method.GET)
                .execute();
        String phpsessid = res.cookie("PHPSESSID");

        //   Document doc = Jsoup.connect("http://www.stepon.top/misc/voicevote/index
        // .php?from=groupmessage&isappinstalled=0").get();


        Document doc = res.parse();


        String script = doc.select("script").get(4).toString();
        String token = script.substring(script.lastIndexOf("'token'") + 9, script.lastIndexOf("'token'") + 49);


        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://www.stepon.top/misc/voicevote/api.php?op=vote");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();
        request.setConfig(requestConfig);
        StringEntity params = new StringEntity("vote-id=3025&token=" + token,
                ContentType
                        .APPLICATION_FORM_URLENCODED);
        request.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; SM-G928X Build/LMY47X) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36");
        request.addHeader("Origin", "http://www.stepon.top");
        request.addHeader("Host", "www.stepon.top");
        request.addHeader("Referer", "http://www.stepon.top/misc/voicevote/index" +
                ".php?from=groupmessage&isappinstalled=0");
        request.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Cookie", "PHPSESSID=" + phpsessid + "; Hm_lvt_9045f321c9885a5d2142d00c75d836e2=1495892588;");
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        request.setEntity(params);

        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void astronVote(HttpHost host) throws IOException {
        //   Document doc = Jsoup.connect("http://www.stepon.top/misc/voicevote/index
        // .php?from=groupmessage&isappinstalled=0").get();
        Connection.Response res = Jsoup.connect("http://www.stepon.top/misc/voicevote/index" +
                ".php?from=groupmessage&isappinstalled=0")
                .method(Connection.Method.GET)
                .execute();
        String phpsessid = res.cookie("PHPSESSID");
        System.out.println(phpsessid);
        Document doc = res.parse();


        String script = doc.select("script").get(4).toString();
        String token = script.substring(script.lastIndexOf("'token'") + 9, script.lastIndexOf("'token'") + 49);


        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://www.stepon.top/misc/voicevote/api.php?op=vote");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).setProxy
                (host).build();
        request.setConfig(requestConfig);
        StringEntity params = new StringEntity("vote-id=3025&token=" + token,
                ContentType.APPLICATION_FORM_URLENCODED);
        request.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; SM-G928X Build/LMY47X) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36");
        request.addHeader("Origin", "http://www.stepon.top");
        request.addHeader("Host", "www.stepon.top");
        request.addHeader("Referer", "http://www.stepon.top/misc/voicevote/index" +
                ".php?from=groupmessage&isappinstalled=0");
        request.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Cookie", "PHPSESSID=" + phpsessid + ";");
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        request.setEntity(params);

        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            if (responseString.substring(8, 9).equals("1")) {
                count++;
                System.out.println("success: " + count);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getProxyList(String proxySiteUrl) throws IOException, InterruptedException {
//        Document doc = Jsoup.connect("https://www.us-proxy.org/").get();
        Document doc = Jsoup.connect(proxySiteUrl).get();
        Elements proxys = doc.select("tbody tr");

        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
        httpClient.start();


        HttpPost request = new HttpPost("http://www.stepon.top/misc/voicevote/api.php?op=vote");


        request.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; SM-G928X Build/LMY47X) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36");
        request.addHeader("Origin", "http://www.stepon.top");
        request.addHeader("Host", "www.stepon.top");
        request.addHeader("Referer", "http://www.stepon.top/misc/voicevote/index" +
                ".php?from=groupmessage&isappinstalled=0");
        request.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.addHeader("Connection", "keep-alive");

        request.addHeader("X-Requested-With", "XMLHttpRequest");


        GetThread[] threads = new GetThread[proxys.size()];


        for (int i = 0; i < threads.length; i++) {
            String ip = proxys.get(i).select("td").get(0).text();
            int port = Integer.parseInt(proxys.get(i).select("td").get(1).text());
            String scheme = proxys.get(i).select("td").get(6).text();
            HttpHost host;
            if (scheme.equals("yes")) {
                host = new HttpHost(ip, port, "https");
            } else {
                host = new HttpHost(ip, port);
            }
            threads[i] = new GetThread(httpClient, request, host);
        }

        for (GetThread thread : threads) {
            thread.start();
            Thread.sleep(2000);
        }
        for (GetThread thread : threads) {
            thread.join();
        }
        httpClient.close();
    }

    static class GetThread extends Thread {
        private CloseableHttpAsyncClient httpClient;
        private HttpPost request;
        private HttpHost host;

        public GetThread(CloseableHttpAsyncClient httpClient, HttpPost request, HttpHost host) {
            this.httpClient = httpClient;
            this.request = request;
            this.host = host;
        }

        @Override
        public void run() {
            try {
                //start thread
                System.out.println("Thread start with " + host.getHostName() + " : " + host.getPort());
                long pre = System.currentTimeMillis();


                //get phpsessid and token
                Connection.Response res = Jsoup.connect("http://www.stepon.top/misc/voicevote/index" +
                        ".php?from=groupmessage&isappinstalled=0")
                        .method(Connection.Method.GET)
                        .execute();
                String phpsessid = res.cookie("PHPSESSID");
                System.out.println(phpsessid);
                Document doc = res.parse();

                String script = doc.select("script").get(4).toString();
                String token = script.substring(script.lastIndexOf("'token'") + 9, script.lastIndexOf("'token'") + 49);

                //set phpsessid and token
                StringEntity params = new StringEntity("vote-id=3025&token=" + token,
                        ContentType.APPLICATION_FORM_URLENCODED);
                request.setEntity(params);
                request.addHeader("Cookie", "PHPSESSID=" + phpsessid + ";");

                //set host and time out
                RequestConfig requestConfig = RequestConfig.custom()
                        .setProxy(host)
                        .setConnectionRequestTimeout(300000)
                        .setConnectTimeout(300000)
                        .setSocketTimeout(300000)
                        .build();
                request.setConfig(requestConfig);

                //fire multi thread call
                Future<HttpResponse> future = httpClient.execute(request, null);
                HttpResponse response = future.get();
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                if (responseString.substring(8, 9).equals("1")) {
                    System.out.println("Success! time used: " + (System.currentTimeMillis() - pre) / 1000 + "s");
                    count++;
                } else {
                    System.out.println(response.toString());
                }
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
                return;
            }
        }
    }

    private static void niuniuExcel() {
        final int[] DATA = {4432420, 4825034, 1228732, 799085, 376068, 430000, 170000, 1831000, 300000, 265800,
                493675, 124900, 603590, 115385, 239316, 188034, 235897, 1837607, 185470, 288889, 461453, 173000,
                10256410, 777778, 179487, 210342, 13317948, 445000};
        item[] items = new item[8];
        items[0] = new item(265800, "a", "1");
        items[1] = new item(124900, "a", "2");
        items[2] = new item(603590, "a", "3");
        items[3] = new item(235897, "a", "4");
        items[4] = new item(10256410, "a", "5");
        items[5] = new item(210342, "a", "6");
        items[6] = new item(13317948, "a", "7");
        items[7] = new item(4432420, "a", "8");


//        final int[] DATA1 = {1, 3, 4, 5, 6, 2, 7, 8, 9, 10, 11, 13,
//                14, 15};

        sumClass get = new sumClass();
        get.TARGET_SUM = 25014887;
        get.populateSubset(items, 0, items.length);
        System.out.print("finish");
    }

    public static void printList(List<item> itemList) throws IOException {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        String filename = desktopPath + "\\proxy.txt";
        OutputStream out = new FileOutputStream(filename);
        OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        for (item p : itemList) {
            writer.write("价值: " + p.getValue() + ", 描述: " + p.getDisc() + ",行数: " + p.getColumn() + "\r\n");
        }
        writer.close();
    }

    public static void fakeXforwardCall() {
        int voteTime = 200;

        HttpClient client = HttpClients.custom().build();
        HttpPost request = new HttpPost("http://yst.fudan.edu.cn/hongtan/vote/api/user/votes/");
        StringEntity params = new StringEntity("[\"bba6be23-6be4-4201-ab5b-ba1fd94683da\"]", ContentType
                .APPLICATION_JSON);
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like " +
                "Gecko) Chrome/56.0.2924.87 Safari/537.36");
        request.addHeader("Origin", "http://yst.fudan.edu.cn");
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept", "*/*");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.setEntity(params);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(6000).setConnectTimeout
                (6000).setSocketTimeout(6000).build();
        request.setConfig(requestConfig);

        //设置ip
        for (int i = 100; i < voteTime; i++) {
            request.addHeader("X-Forwarded-For", "10.132.140." + i);
            try {
                long pre = System.currentTimeMillis();
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("time used: " + (System.currentTimeMillis() - pre) / 1000 + "s");
                }
                System.out.println(response.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        }


    }
}
