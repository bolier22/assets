package com.wanma.eichong.assets.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterfaceTestTools {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceTestTools.class);

	static HashMap mapVariable = new HashMap();// 将变量所对应的json数据暂存 key为变量
												// value为json数据
	static List listVariableKey = new ArrayList();// 将变量放入到list中

	static String txtMessage = "";
	
//	public static void main(String[] args) {
//		File file=new File("D:\\soft\\profileSoft\\recordingFile\\smart.call\\test001.txt");
//		getIt(file.getName());
//	}

	public static List<String> getIt(String file) {
		// String fp = "E:/test001.txt";
		List<String> msg=new ArrayList<>();
		try {
			InputStream inputStream = new FileInputStream(file);
			ArrayList list = readTxtInputStreamToPutList(inputStream);
			for (int i = 0; i < list.size(); i++) {
				// System.out.println(list.get(i));
				requestSendAndGetResult(list.get(i).toString());
				msg.add(txtMessage);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	public static void requestSendAndGetResult(String jsonStr) {
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		try {
			String name = jsonObject.getString("name");
			String type = jsonObject.getString("type");
			String url = jsonObject.getString("url");
			String jsonObjectRequestStr = jsonObject.getJSONObject("request").toString();// 获取请求报文
																							// 将请求json对象转换称请求json字符串
			System.out.println("\r\n" + "------------------------分割线----------------------------");
			if ("post".equals(type.toLowerCase())) {// 判断请求方式 GET 或 POST
				String rgex = "\\$(.*?)\\$"; // 正则表达式取出 两个$$ 中间的内容
				List<String> fetchRequest = getSubUtilList(jsonObjectRequestStr, rgex);// 取出一段报文中所有的
																						// $$
																						// 中间变量
				jsonObjectRequestStr = jsonObjectRequestStr.replaceAll("\\$", "");
				for (int k = 0; k < fetchRequest.size(); k++) {// 将取出的变量循环遍历
					String fetchStr = fetchRequest.get(k);//
					String fetchVal = mapVariable.get(fetchStr).toString();// 根据变量
																			// 从map中取出对应的变量的value
					jsonObjectRequestStr = jsonObjectRequestStr.replace(fetchStr, fetchVal);// 将请求报文中$$部分内容换成
																							// map中取出的变量的value
				}
				jsonObjectRequestStr = jsonObjectRequestStr.replace("\"[", "[").replace("]\"", "]").replace("\\", "");// 去除请求json字符串中的"字符（如果变量是list取值时会多出"字符）
				String responseStr = HttpRequest.sendPost(url, jsonObjectRequestStr.replaceAll("\\s*", ""));// 去除请求json字符串的空格
																											// 并发起
																											// post请求
																											// 并获取返回字符串
				JSONObject jsonObjectResponse = JSON.parseObject(responseStr);// 将返回字符串
																				// 转换称
																				// json对象
				String status = jsonObjectResponse.getString("status");// 取出返回数据状态
				String prex = name.split("\\.")[0];// 取出name的序号
				System.out.println("接口名：" + name + "\r\n请求方式：" + type + "\r\n请求地址：" + url + "\r\n请求数据：" + jsonObjectRequestStr
						+ "\r\n返回数据：" + responseStr);
				txtMessage = "接口名：" + name + "\r\n请求方式：" + type + "\r\n请求地址：" + url + "\r\n请求数据：" + jsonObjectRequestStr
						+ "\r\n返回数据：" + responseStr;
				// System.out.println("接口名：" + name + "\r\n请求方式：" + type +
				// "\r\n请求地址：" + url + "\r\n请求数据：" + jsonObjectRequestStr
				// + "\r\n返回数据：" + responseStr);

				if ("0".equals(status)) {// 非0为失败
					txtMessage = txtMessage + "\r\n测试通过";
					 System.out.println("测试通过");
					for (int y = 0; y < listVariableKey.size(); y++) {
						String variableKey = listVariableKey.get(y).toString();// 从遍历map中获取出变量的字符串
						if (variableKey.startsWith(prex)) {
							if (!mapVariable.containsKey(variableKey)) {
								String[] variableKeyS = variableKey.split("\\.");// String
																					// s13
																					// =
																					// "$callingList[3].studentList[1].studentObj.age$";
								JSONObject jsonObjectNext = jsonObjectResponse;
								if (variableKeyS.length > 1) {
									for (String variableKeyOne : variableKeyS) {
										jsonObjectNext = fetchJsonObjectByKey(jsonObjectNext, variableKeyOne);
										if (jsonObjectNext instanceof JSONObject) {
											if (jsonObjectNext.get(variableKeyOne) != null) {
												mapVariable.put(variableKey, jsonObjectNext.get(variableKeyOne));// 从返回的json对象中
																													// 根据key获取对应的值
																													// 并将提取出的返回值
																													// 放入到map中
											}
										}
									}
								} else {
									if (!jsonObjectNext.isEmpty()) {
										mapVariable.put(variableKey, jsonObjectNext.get(variableKey.split("\\_")[1]));// 从返回的json对象中
																														// 根据key获取对应的值
																														// 并将提取出的返回值
																														// 放入到map中
									}
								}
							}
						}
					}
				} else {
					String errorMsg = jsonObjectResponse.getString("errorMsg");
					txtMessage = txtMessage + "测试不通过：" + errorMsg + "\r\n错误信息：" + errorMsg;
					 System.out.println("测试不通过：" + errorMsg + "\r\n错误信息：" +
					 errorMsg);
				}
			} else {// Get
				 System.out.println("暂时不支持Get方式请求：");
				txtMessage = txtMessage + "暂时不支持Get方式请求：";
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtMessage+="\r\n";
	}

	/**
	 * 根据listVariableKey将变量取出返回的json对象中的内容
	 * 
	 * @param jsonObjectResponse
	 * @param variableKeyOne
	 * @return
	 */
	public static JSONObject fetchJsonObjectByKey(JSONObject jsonObjectResponse, String variableKeyOne) {
		JSONObject jsonObject = new JSONObject();
		if (variableKeyOne.contains("List[")) {// list对象
			String rgex = "\\*?(.*?)\\["; // 取出[左边的内容
			String keyListStr = getSubUtilObject(variableKeyOne, rgex);
			String[] keyListStrS = keyListStr.split("_");
			if (keyListStrS.length > 1) {
				keyListStr = keyListStrS[1];
			}
			rgex = "\\[(.*?)\\]"; // 取出[]中间的数字
			int keyIndex = Integer.parseInt(getSubUtilObject(variableKeyOne, rgex));
			jsonObject = (JSONObject) jsonObjectResponse.getJSONArray(keyListStr).get(keyIndex);
		} else if (variableKeyOne.endsWith("Obj")) {// 单个对象
			jsonObject = jsonObjectResponse.getJSONObject(variableKeyOne);
		} else {
			jsonObject = jsonObjectResponse;
		}
		return jsonObject;
	}

	/**
	 * 读取txt文件的内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static ArrayList readTxtInputStreamToPutList(InputStream inputStream) {
		logger.info("---------InterfaceTestTools--------------readTxtInputStreamToPutList------------------");
		// 每一块#go标记json数据存放一起
		StringBuilder jsonStrGo = new StringBuilder();
		ArrayList listJsonStr = new ArrayList();// 将读取到的文本中的json字符串分多个 GO分割
												// 放入到list中
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));// 读取文件数据
			String lineStr = null;// 定义每行字符串
			int index = 0;// 以index为数组的下标
			while ((lineStr = br.readLine()) != null) {// 使用readLine方法，一次读一行
				logger.info("---------InterfaceTestTools--------------readTxtInputStreamToPutList------------------");
				if ("#go".equals(lineStr.trim())) {// 根据#go分割每一块儿报文
					listJsonStr.add(index, jsonStrGo);// 将#go模块放入到list中
					jsonStrGo = new StringBuilder();// 将字符串清空
					index++;// list下标计数+1
				} else if (lineStr.startsWith("$")) {// 判断变量
					String rgex = "\\$(.*?)\\$";
					listVariableKey.add(getSubUtilObject(lineStr, rgex));// 将变量提取放入到list中
				} else {
					jsonStrGo.append(System.lineSeparator() + lineStr);// 加入一个换行符
																		// 并继续将数据放入到同一个可变字符串
				}
			}
			br.close();// 关闭数据流
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listJsonStr;// 将放入到list中的json数组返回
	}

	/**
	 * 读取txt文件的内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static ArrayList txt2String(String filePath) {
		// 每一块#go标记json数据存放一起
		StringBuilder jsonStrGo = new StringBuilder();
		ArrayList listJsonStr = new ArrayList();// 将读取到的文本中的json字符串分多个 GO分割
												// 放入到list中
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));// 读取文件数据
			String lineStr = null;// 定义每行字符串
			int index = 0;// 以index为数组的下标
			while ((lineStr = br.readLine()) != null) {// 使用readLine方法，一次读一行
				logger.info("---------InterfaceTestTools--------------txt2String------------------");
				if ("#go".equals(lineStr.trim())) {// 根据#go分割每一块儿报文
					listJsonStr.add(index, jsonStrGo);// 将#go模块放入到list中
					jsonStrGo = new StringBuilder();// 将字符串清空
					index++;// list下标计数+1
				} else if (lineStr.startsWith("$")) {// 判断变量
					String rgex = "\\$(.*?)\\$";
					listVariableKey.add(getSubUtilObject(lineStr, rgex));// 将变量提取放入到list中
				} else {
					jsonStrGo.append(System.lineSeparator() + lineStr);// 加入一个换行符
																		// 并继续将数据放入到同一个可变字符串
				}
			}
			br.close();// 关闭数据流
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listJsonStr;// 将放入到list中的json数组返回
	}

	/**
	 * 正则表达式匹配两个指定字符串中间的内容
	 * 
	 * @param soap
	 * @return
	 */
	public static List<String> getSubUtilList(String soap, String rgex) { // String
																			// rgex
																			// =
																			// "\\$(.*?)\\$";
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile(rgex);// 匹配的模式
		Matcher m = pattern.matcher(soap);
		while (m.find()) {
			logger.info("---------InterfaceTestTools--------------getSubUtilList------------------");
			int i = 1;
			list.add(m.group(i));
			i++;
		}
		return list;
	}

	/**
	 * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
	 * 
	 * @param soap
	 * @param rgex
	 * @return
	 */
	public static String getSubUtilObject(String soap, String rgex) { // String
																		// rgex
																		// =
																		// "\\$(.*?)\\$";
		Pattern pattern = Pattern.compile(rgex);// 匹配的模式
		Matcher m = pattern.matcher(soap);
		while (m.find()) {
			logger.info("---------InterfaceTestTools--------------getSubUtilObject------------------");
			return m.group(1);
		}
		return "";
	}
}
