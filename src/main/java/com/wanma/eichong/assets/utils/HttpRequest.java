package com.wanma.eichong.assets.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
	
	public static String sessionId = null;
	public static void main(String[] args) {

		//图片上传
		//readMsg();
//		postTest();
System.out.println("--------");
		
		String urlPath = "http://192.168.1.147:1235/toPinyin";
		String strJson = "{"+
				"\"text\":\"行业  银行 行不行\""+
		"}";
		
		String urlDownloadPdfHtml=sendPost(urlPath, strJson.replaceAll("\\s*", ""));//.replaceAll(">\\s*<", "><")
System.out.println(urlDownloadPdfHtml);
	}
	
	public static void postTest(){
		String downloadPdfStrPic = "{"
	    +"    \"rasa_nlu_data\": {"
	    +"        \"common_examples\": ["
	    +"            {"
	    +"                \"intentSeq\": 1,"
+"	                    \"intent\": \"意图1\","
+"	                    \"corpusSeq\": 1,"
+"	                    \"text\": \"你说\","
+"	                    \"entities\": []"
+"	                },"
+"	             "
+"	                {"
+"	                    \"intentSeq\": 4,"
+"	                    \"intent\": \"意向4\","
+"	                    \"corpusSeq\": 7,"
+"	                    \"text\": \"你在哪里\","
+"	                   \"entities\": []"
+"	                }"
+"	            ]"
+"	        }"
+"	    }";
		
		String urlDownloadPdf = "http://192.168.1.249:5000/train?project=金融业&model=金融";
		
		String urlDownloadPdfHtml=sendPost(urlDownloadPdf, downloadPdfStrPic.replaceAll("\\s*", ""));//.replaceAll(">\\s*<", "><")
System.out.println(urlDownloadPdfHtml);
		
		
	}
	
	
	// 判断文件夹是否存在
     public static void judeDirExists(File file) {
         if (file.exists()) {
             if (file.isDirectory()) {
                 System.out.println("dir exists");
             } else {
                 System.out.println("the same name file exists, can not create dir");
             }
         } else {
             System.out.println("dir not exists, create it ...");
             file.mkdir();
         }
     }
     
	public static void readMsg(){
		String fileName = "D:/234.wav";
		byte[] bb = readPic(fileName);
		String ss = bytesToHexString(bb).replaceAll("\\s*", "");
		System.out.println(ss);

		String downloadPdfStrPic = "{                                   "+
				"    \"tempSeq\": 100,                                  "+
				"    \"detailSeq\": 10,                                 "+
				"    \"calledStatus\": 0,                               "+
				"    \"callTime\": \"4512154651\",                      "+
				"    \"taskSeq\": 30,                                   "+
				"    \"mercSeq\": 130,                                   "+
				"    \"taskRemark\": \"sdfasfsdf\",                     "+
				"    \"fileStr\": \""+ss+"\",                           "+
				"    \"callLogList\": [                                 "+
				"        {                                              "+
				"            \"voiceSeq\": 102,                         "+
				"            \"callLogSeq\": 130,                       "+
				"            \"detailSeq\": 1340,                       "+
				"            \"callText\": \"存放识别后的语音字符串\",   "+
				"            \"callFilePath\": \"\",                    "+
				"            \"createTime\": \"创建时间的毫秒\",         "+
				"            \"callRemark\": 100,                       "+
				"            \"callParsingTime\": 100,                  "+
				"            \"callType\": 100,                         "+
				"            \"mercName\": 100,                         "+
				"            \"mercSeq\": 100,                          "+
				"            \"agentName\": 100,                        "+
				"            \"agentSeq\": 100,                         "+
				"            \"callingTel\": 100,                       "+
				"            \"calledTel\": 100,                        "+
				"            \"resultLevel\": 100,                      "+
				"            \"fileStr\": \""+ss+"\"    "+
				"        },                                           "+
				"        {                                            "+
				"            \"voiceSeq\": 100,                         "+
				"            \"callLogSeq\": 100,                       "+
				"            \"detailSeq\": 100,                        "+
				"            \"callText\": 100,                         "+
				"            \"callFilePath\": 100,                     "+
				"            \"createTime\": 100,                       "+
				"            \"callRemark\": 100,                       "+
				"            \"callParsingTime\": 100,                  "+
				"            \"callType\": 100,                         "+
				"            \"mercName\": 100,                         "+
				"            \"mercSeq\": 100,                          "+
				"            \"agentName\": 100,                        "+
				"            \"agentSeq\": 100,                         "+
				"            \"callingTel\": 100,                       "+
				"            \"calledTel\": 100,                        "+
				"            \"resultLevel\": 100,                      "+
				"            \"fileStr\": \""+ss+"\"    "+
				"        }                                            "+
				"    ]}                                               ";
		
		String urlDownloadPdf = "http://localhost:8080/user/json/data";

		String urlDownloadPdfHtml=sendPost(urlDownloadPdf, downloadPdfStrPic.replaceAll("\\s*", ""));//.replaceAll(">\\s*<", "><")
//		System.out.println("====="+urlDownloadPdfHtml);
	}
	
	public static void base64ToIobb(byte[] sbb) throws IOException {
//        String fileName = "d:/123.png"; //生成的新文件
//        FileOutputStream out = null;
//        try {
//            // 解码，然后将字节转换为文件
//            ByteArrayInputStream in = new ByteArrayInputStream(sbb);
//            byte[] buffer = new byte[1024];
//            out = new FileOutputStream(fileName);
//            int bytesum = 0;
//            int byteread = 0;
//            while ((byteread = in.read(buffer)) != -1) {
//                bytesum += byteread;
//                out.write(buffer, 0, byteread); //文件写操作
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }finally{
//        	out.flush();
//        	out.close();
//        }
    }
	
	
	public static void base64ToIo(String strBase64) throws IOException {
//        String string = strBase64;
//        String fileName = "d:/gril2.jpg"; //生成的新文件
//        FileOutputStream out = null;
//        try {
//            // 解码，然后将字节转换为文件
//            byte[] bytes = new BASE64Decoder().decodeBuffer(string);   //将字符串转换为byte数组
//            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
//            byte[] buffer = new byte[1024];
//            out = new FileOutputStream(fileName);
//            int bytesum = 0;
//            int byteread = 0;
//            while ((byteread = in.read(buffer)) != -1) {
//                bytesum += byteread;
//                out.write(buffer, 0, byteread); //文件写操作
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }finally{
//        	out.flush();
//        	out.close();
//        }
    }
	
	/**
	 * 将图片读取转换为字节数组
	 * @param picPath
	 * @return
	 */
	public static byte[] readPic(String picPath) {
		File file = new File(picPath); // 实例化File类的对象
		InputStream input = null;
		byte b[] = new byte[(int) file.length()];// 定义一个用于接收字符串的数组
		try {
			input = new FileInputStream(file);
			input.read(b);// 读取
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	
	public String invokeHttpClient(String requestJson,String urlStr) throws Exception {
		CloseableHttpClient httpclient = null;
		String response = null;
		//1. 取 Httpclient 实例，该实例是个单例
		//用配置文件里给定的自定义依赖项和配置创建一个 HttpClient实例
		httpclient = HttpClients.custom().build();

		//2. 创建一个 HttpPost 实例，使用从服务器上取得的服务参数以及传入参数  url 地址由esb提供
		HttpPost httppost = new HttpPost(urlStr);//("http://10.15.22.124:8001/0100010001/CompulsoryInsurancePremiumCalculationService/V1");

		//3. 将 requestXML 放入httppost 的 Entity 中
		HttpEntity reqEntity = EntityBuilder.create()
				.setContentType(ContentType.APPLICATION_JSON)
				.setContentEncoding("UTF-8")
				.setText(requestJson)
				.build();
		httppost.setEntity(reqEntity);
		
		// ------------------------------------------------------------------------------
		//4. 发送请求
		try {
			response = httpclient.execute(httppost, new ResponseHandler<String>(){
					//处理响应
					@Override
					public String handleResponse(final HttpResponse response)
							throws ClientProtocolException, IOException {
						String responseJson = null;
						HttpEntity entity = null;
						int status = response.getStatusLine().getStatusCode();
						if (status >= 200 && status < 300) {
								entity = response.getEntity();
								if (entity != null) {
									String sentity = EntityUtils.toString(entity, "UTF-8");
									if(sentity==null || sentity.trim().length()==0) return null;
									responseJson = sentity;
								} else {
									responseJson = response.getStatusLine().getReasonPhrase() + "  返回的结果为空！";
								}
						} else {
								responseJson = response.getStatusLine().getReasonPhrase();
						}
						System.out.println(responseJson);
						return responseJson;
					}
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//5. 释放连接
			httppost.releaseConnection();
			httpclient.close();
		}
		// ------------------------------------------------------------------------------
		return response;
	}
	
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");  
			
			if(sessionId!=null){
				conn.setRequestProperty("Cookie", sessionId);
			}
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			String cookieValue=conn.getHeaderField("Set-Cookie");
			if(cookieValue!=null){
		        System.out.println("cookie value:"+cookieValue);
		        sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
			}
			
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * byte[]数组转十六进制字符串
	 * @param byteSrc
	 * @return
	 */
	public static String bytesToHexString(byte[] byteSrc){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (byteSrc == null || byteSrc.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < byteSrc.length; i++) {  
	        int v = byteSrc[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	 
	
	/** 
	 * 十六进制字符串转byte[]
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	/** 
	 * char转byte
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
}