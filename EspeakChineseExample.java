package org.wj.prajumsook.demo;

import java.io.*;
import java.util.Base64;

public class EspeakChineseExample {

    public static void main(String[] args) throws Exception {
        String file = genEspeakingWavFile("C C E M 6 7 0 0 2 3");
        String base64 = fileToBase64(file);
        decodeBase64(base64);
    }

    private static String genEspeakingWavFile(String text){

        final int speed = 50; // 語速設置 (可以調整為 80 到 450，數值越高，語速越快)
        final int pitch = 99; // 音高設置 (50 是默認值，範圍是 0 到 99，值越高音越尖)

        // 音檔輸出路徑
        String outputPath = "output.wav";

        // 使用中文語音，並設置語速和音高，輸出為音檔
        String cmd = "espeak -v zh -s " + speed + " -p " + pitch + " -w " + outputPath + " \"" + text + "\""; //中文語音

        try {
            // 執行 eSpeak 命令生成音檔
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor(); // 等待命令完成

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return outputPath;
    }
    private static String fileToBase64(String outputPath) throws Exception {
        return convertToBase64(outputPath);
    }

    private static String convertToBase64(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        fileInputStream.read(fileBytes);
        fileInputStream.close();

        // 使用 Base64 編碼
        return Base64.getEncoder().encodeToString(fileBytes);
    }
    private static void decodeBase64(String base64String){
        // 解碼 Base64 字串
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // 將解碼後的 byte 陣列寫入到檔案
        String outputFilePath = "D:\\Project\\demo\\src\\main\\java\\org\\wj\\prajumsook\\demo\\output.wav"; // 輸出的檔案名稱
        try (FileOutputStream fos = new FileOutputStream(new File(outputFilePath))) {
            fos.write(decodedBytes);
            System.out.println("解碼並寫入檔案成功：" + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}