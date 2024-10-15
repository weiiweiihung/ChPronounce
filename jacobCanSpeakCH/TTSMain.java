package org.wj.prajumsook.demo.jacob;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class TTSMain {
	static String var = "1";

	public static void main(String[] args) {
		ActiveXComponent voice = new ActiveXComponent("Sapi.SpVoice");
		ActiveXComponent fileStream = new ActiveXComponent("Sapi.SpFileStream");
		Dispatch voiceDispatch = voice.getObject();
		Dispatch fileStreamDispatch = fileStream.getObject();
		String tempWavFilePath = "D:\\Project\\demo\\src\\main\\java\\org\\wj\\prajumsook\\demo\\jacob\\"+var+".mp3"; // 暫時的 WAV 檔案路徑

		try {
			// 設置音量和語速
			voice.setProperty("Volume", new Variant(100));
			voice.setProperty("Rate", new Variant(-1));

			// 使用 Sapi.SpFileStream 將語音輸出到 WAV 檔案
			Dispatch.call(fileStreamDispatch, "Open", tempWavFilePath, new Variant(3)); // 3 = SSFMCreateForWrite

			// 將語音輸出到文件流
			Dispatch.putRef(voiceDispatch, "AudioOutputStream", fileStreamDispatch);

			// 開始生成語音並寫入文件
			Dispatch.call(voiceDispatch, "Speak", new Variant(var));

			// 等待語音合成完成
			// 在此可以加入等待邏輯，例如使用 Thread.sleep()
			Thread.sleep(2000); // 等待 1 秒，確保語音生成完成

			// 關閉文件流
			Dispatch.call(fileStreamDispatch, "Close");

			// 讀取 WAV 檔案並轉換為 Base64
			String base64Audio = convertFileToBase64(tempWavFilePath);
			System.out.println("Base64 Audio: " + base64Audio);

			// 刪除臨時檔案
//			new File(tempWavFilePath).delete();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			voiceDispatch.safeRelease();
			fileStreamDispatch.safeRelease();
			voice.safeRelease();
			fileStream.safeRelease();
		}
	}

	// 將檔案內容轉換為 Base64 編碼
	private static String convertFileToBase64(String filePath) throws IOException {
		File file = new File(filePath);
		byte[] fileContent = new byte[(int) file.length()];

		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			fileInputStream.read(fileContent);
		}

		// 使用 Base64 進行編碼
		return Base64.getEncoder().encodeToString(fileContent);
	}
}



//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//
//public class TTSMain {
//    public static void main(String[] args) {
//        ActiveXComponent activeXComponent = new ActiveXComponent("Sapi.SpVoice");
//		Dispatch dis = activeXComponent.getObject();
//
//		try{
//			activeXComponent.setProperty("Volume", new Variant(100));
//			activeXComponent.setProperty("Rate", new Variant(-1));
//			Dispatch.call(dis,"Speak",new Variant("6 6 6 1 2 3 早安你好總共1000塊"));
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			dis.safeRelease();
//			activeXComponent.safeRelease();
//		}
//    }
//}
