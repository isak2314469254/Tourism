package com.trytry.lasttry.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    // 此处为新的压缩、解压方法
    // 统计文本单字词频，取前N个词生成字典
    public static Map<String, String> generateDictionary(String text, int topN) {
        Map<String, Integer> freqMap = new HashMap<>();

        // 这里简单按单字统计，如果你想统计多字词，可以改成分词处理
        for (int i = 0; i < text.length(); i++) {
            String ch = text.substring(i, i + 1);
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        // 按频率降序排序，取前topN个词
        List<Map.Entry<String, Integer>> sortedList = freqMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .limit(topN)
                .collect(Collectors.toList());

        // 为词语生成编码，格式为 #1, #2, ...
        Map<String, String> dictionary = new LinkedHashMap<>();
        int code = 1;
        for (Map.Entry<String, Integer> entry : sortedList) {
            dictionary.put(entry.getKey(), "#" + code++);
        }

        return dictionary;
    }

    // 压缩文本，替换词为编码
    public static String compress_new(String text, Map<String, String> dictionary) {
        // 按字典中词的长度排序，防止替换时短词先被替换
        List<String> words = new ArrayList<>(dictionary.keySet());
        words.sort((a, b) -> b.length() - a.length());

        String compressed = text;
        for (String word : words) {
            String code = dictionary.get(word);
            compressed = compressed.replace(word, code);
        }
        return compressed;
    }

    // 解压文本，替换编码为原词
    public static String decompress_new(String compressedText, Map<String, String> dictionary) {
        // 反转字典（编码->词）
        Map<String, String> reverseDict = dictionary.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        // 按编码长度排序，避免部分编码被提前替换
        List<String> codes = new ArrayList<>(reverseDict.keySet());
        codes.sort((a, b) -> b.length() - a.length());

        String decompressed = compressedText;
        for (String code : codes) {
            String word = reverseDict.get(code);
            decompressed = decompressed.replace(code, word);
        }
        return decompressed;
    }

}
