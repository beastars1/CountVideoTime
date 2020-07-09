package com.lzq;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author beastars
 */
public class VideoTime {
    private static ArrayList<File> videos = new ArrayList<File>();
    private static final String[] FORMAT = {"avi", "mp4", "flv"}; // 视频后缀

    /**
     * 统计视频
     * @param path 本地路径
     */
    private void getVideos(String path) {
        File disk = new File(path);
        File[] files = disk.listFiles();
        List<String> list = new ArrayList<String>(Arrays.asList(FORMAT)); // 封装成集合

        if (files == null) {
            System.out.println("空目录！");
            return;
        }

        for (File file : files) {
            String name = file.getName();
            if (file.isDirectory()) {
                getVideos(file.getAbsolutePath());
            } else if (file.isFile()) {
                String suffix = name.substring(name.lastIndexOf(".") + 1);
                if (list.contains(suffix))
                    videos.add(file);
            } else {
                System.out.println(name + " 出现错误！");
            }
        }
    }

    /**
     * 计算视频时间
     *
     * @param path 本地路径
     */
    public void getVideoTime(String path) {
        getVideos(path);

        Encoder encoder = new Encoder();
        long time = 0;

        try {
            for (File video : videos) {
                MultimediaInfo info = encoder.getInfo(video);
                time += info.getDuration() / 1000;
            }
        } catch (EncoderException e) {
            e.printStackTrace();
        }

        int hour = (int) (time / 3600);
        int minute = (int) (time % 3600) / 60;
        int second = (int) (time - hour * 3600 - minute * 60);

        System.out.println("共有" + videos.size() + "个视频");
        System.out.println("总视频时长为：" + hour + "时" + minute + "分" + second + "秒");
    }
}
