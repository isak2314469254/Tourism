package com.trytry.lasttry.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

//GZIP 工具类,用于压缩和解压缩文件
public class CompressionUtil {
    public static byte[] compress(String data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(data.getBytes(StandardCharsets.UTF_8));
        }
        return bos.toByteArray();
    }

    public static String decompress(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try (GZIPInputStream gzip = new GZIPInputStream(bis);
             InputStreamReader reader = new InputStreamReader(gzip, StandardCharsets.UTF_8);
             BufferedReader in = new BufferedReader(reader)) {
            return in.lines().collect(Collectors.joining("\n"));
        }
    }
}
