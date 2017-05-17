package com.niuniuExcel;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Future;

public class Main {
    public static int i = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        getProxyList("http://free-proxy-list.net/");
        getProxyList("http://www.us-proxy.org/");
        System.out.println("All thread finished: " + i);
    }

    private static void getProxyList(String proxySiteUrl) throws IOException, InterruptedException {
//        Document doc = Jsoup.connect("https://www.us-proxy.org/").get();
        Document doc = Jsoup.connect(proxySiteUrl).get();
        Elements proxys = doc.select("tbody tr");

        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
        httpClient.start();

        HttpPost request = new HttpPost("http://yst.fudan.edu.cn/hongtan/vote/api/user/votes/");
        StringEntity params = new StringEntity(
                "[\"19a60805-9d66-48d2-b526-8c7b22ee6e09\"]",
                ContentType.APPLICATION_JSON);
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like " +
                "Gecko) Chrome/56.0.2924.87 Safari/537.36");
        request.addHeader("Origin", "http://yst.fudan.edu.cn");
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept", "*/*");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.setEntity(params);

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
                System.out.println("Thread start with " + host.getHostName() + " : " + host.getPort());
                long pre = System.currentTimeMillis();

                RequestConfig requestConfig = RequestConfig.custom()
                        .setProxy(host)
                        .setConnectionRequestTimeout(200000)
                        .setConnectTimeout(200000)
                        .setSocketTimeout(200000)
                        .build();
                request.setConfig(requestConfig);

                Future<HttpResponse> future = httpClient.execute(request, null);
                HttpResponse response = future.get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("time used: " + (System.currentTimeMillis() - pre) / 1000 + "s");
                    i++;
                }

                System.out.println(response.toString());
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


}
