package com.example.demo;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ParseWord {

	static String[] special_character_arr = new String[] { "", "\r", "\n" };

	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		ActiveXComponent word = new ActiveXComponent("word.Application");
		Dispatch wordObject = (Dispatch) word.getObject();
		Dispatch.put((Dispatch) wordObject, "Visible", new Variant(false));
		Dispatch documents = word.getProperty("Documents").toDispatch();
		Dispatch document = Dispatch.call(documents, "Open", "C://a.doc").toDispatch();
		Dispatch wordContent = Dispatch.get(document, "Content").toDispatch();
		Dispatch paragraphs = Dispatch.get(wordContent, "Paragraphs").toDispatch();

		int paragraphCount = Dispatch.get(paragraphs, "Count").getInt();// 总行数
		
		for (int i = 1; i <= paragraphCount; i++) {
			Dispatch paragraph = Dispatch.call(paragraphs, "Item", new Variant(i)).toDispatch();
			Dispatch paragraphRange = Dispatch.get(paragraph, "Range").toDispatch();
			System.out.println(Dispatch.get(paragraph, "Range").toString());
			
			String paragraphContent = Dispatch.get(paragraphRange, "Text").toString();

			// 去掉特殊的字符
			for (String c : special_character_arr) {
				if (paragraphContent.contains(c)) {
					paragraphContent = paragraphContent.replaceAll(c, "");
				}

			}
			Dispatch imgDispatch = Dispatch.get(paragraphRange, "InlineShapes").toDispatch();// 图片
			int imgCount = Dispatch.get(imgDispatch, "Count").getInt();

			// System.out.println("第" + i + "行图片总数" + imgCount);
			System.out.println("第" + i + "行的内容为：[" + paragraphContent + "]" + (imgCount>0?(", 图片的数量为："+imgCount):""));// 打印每行内容

			for (int j = 1; j < imgCount + 1; j++) {
				Dispatch shape = Dispatch.call(imgDispatch, "Item", new Variant(1)).toDispatch();
				Dispatch imageRange = Dispatch.get(shape, "Range").toDispatch();
				Dispatch.call(imageRange, "Copy");
				Dispatch.call(imageRange, "Paste");
			}
		}

		Dispatch.call(document, "SaveAs", new Variant("C://b.doc"));
		Dispatch.call(document, "Close", new Variant(true));
		Dispatch.call(word, "Quit");
		long time2 = System.currentTimeMillis();
		long time3 = (time2 - time1);
		System.out.println("解析word消耗的时间为： " + time3 + " ms");

	}

}
