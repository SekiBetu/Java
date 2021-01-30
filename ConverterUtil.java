/**
 *
 */
package jp.util;

import java.util.ArrayList;

/**
 * @author PC User
 *
 */
public class ConverterUtil {

	/**
	 *
	 * 漢数字を半角数字に変換します。
	 *
	 * @param chinaNum
	 *            変換したい漢数字
	 * @return long 変換した値
	 */
	public static String chinaNumToArabicNum(String chinaNum) {

		long retNum = 0;

		int maxSize = chinaNum.length();

		String calValue = new String(chinaNum);
		int count = 0;
		for (int i = 0; i < maxSize; i++) {

			if (getNum(chinaNum.substring(i, i + 1)) != -1) {

				calValue = chinaNum.substring(count, maxSize);
				break;
			} else {
				count++;
			}
		}

		String spritChinaNum[] = new String[3];

		ArrayList<String> list = new ArrayList<>();

		list.add("百");
		list.add("十");

		for (int i = 0; i < 2; i++) {
			if (calValue.indexOf(list.get(i)) > -1) {

				spritChinaNum[i] = calValue.substring(0,
						calValue.indexOf(list.get(i)) + 1);
				if (spritChinaNum[i].equals(list.get(i))) {
					spritChinaNum[i] = "一" + spritChinaNum[i];
				}
				calValue = calValue.substring(calValue.indexOf(list.get(i)) + 1, calValue.length());
			} else {
				spritChinaNum[i] = "零";
			}
		}

		spritChinaNum[2] = calValue;

		long hundredsPlaceArray[] = { 0, 0 };
		long tensPlaceArray[] = { 0, 0 };

		for (String num : spritChinaNum) {

			if (num.matches(".*" + list.get(0) + ".*")) {
				for (int i = 0; i < num.length(); i++) {
					// hundredsPlaceArray.add(getNum(num.substring(i,i+1)));
					hundredsPlaceArray[i] = getNum(num.substring(i, i + 1));

				}
			}

			if (num.matches(".*" + list.get(1) + ".*")) {
				for (int i = 0; i < num.length(); i++) {
					// tensPlaceArray.add(getNum(num.substring(i,i+1)));
					tensPlaceArray[i] = getNum(num.substring(i, i + 1));
				}
			}
		}

		long hundredsPlace = 0;
		long tensPlace = 0;
		long onesPlace = 0;

		hundredsPlace = Math.abs(hundredsPlaceArray[0] * hundredsPlaceArray[1]);
		tensPlace = Math.abs(tensPlaceArray[0] * tensPlaceArray[1]);

		for (int i = 0; i < calValue.length(); i++) {
			if (i == 0) {
				if (calValue.length() >= 3) {
					onesPlace += getNum(calValue.substring(i, i + 1)) * 100;
				} else if (calValue.length() == 2) {
					onesPlace += getNum(calValue.substring(i, i + 1)) * 10;
				} else {
					onesPlace += getNum(calValue);
				}
			} else if (i == 1) {
				if (calValue.length() >= 3) {
					onesPlace += getNum(calValue.substring(i, i + 1)) * 10;
				} else {
					onesPlace += getNum(calValue);
				}
			} else if (i == 2) {
				onesPlace += getNum(calValue);
			}
		}
		// 百の位、十の位と一の位を加算
		retNum = hundredsPlace + tensPlace + onesPlace;

		//半角数字を全角数字にする。
		String ret =  convertMultiByteNum(retNum);


		return ret;

	}

	/**
	 * 半角数字を全角数字にする。
	 * @param inNum
	 * @return
	 */
	public static String convertMultiByteNum(long inNum) {

		String strInNum = Long.toString(inNum);
		 StringBuffer sb = new StringBuffer(strInNum);
	    for (int i = 0; i < sb.length(); i++) {
	        char c = sb.charAt(i);
	        if (c >= '0' && c <= '9') {
	            sb.setCharAt(i, (char) (c - '0' + '０'));
	          }
	        }

		return sb.toString();
	}

	/**
	 * 漢数字を半角数字にする詳細
	 * @param strNum
	 * @return
	 */
	private static long getNum(String strNum) {

		long num = 0L;
		// 漢数字を数値化する。
		for (int i = 0; i < strNum.length(); i++) {
			char cBuf = strNum.charAt(i);
			switch (cBuf) {
			case '零':
			case '〇':
				num = 0;
				break;
			case '壱':
			case '一':
				num = 1;
				break;
			case '弐':
			case '二':
				num = 2;
				break;
			case '参':
			case '三':
				num = 3;
				break;
			case '四':
				num = 4;
				break;
			case '五':
				num = 5;
				break;
			case '六':
				num = 6;
				break;
			case '七':
				num = 7;
				break;
			case '八':
				num = 8;
				break;
			case '九':
				num = 9;
				break;
			case '十':
				num = 10;
				break;

			case '百':
				num = 100;
				break;
			default:
				num = -1;
				break;
			}
		}
		return num;

	}

	/**
	 * 文字の全角と半角を判定して半角であれば正を返す
	 *
	 * @param targetString
	 * @return　boolean
	 */
	public static boolean isSingleByte(String targetString) {
		// 取得した文字サイズの計算
		int strSize = targetString.length();
		int byteSize = targetString.getBytes().length;

		// 文字数とバイト数が同じ→半角
		if (strSize == byteSize) {
			return true;
		}
		return false;
	}

}




