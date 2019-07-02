package com.wanma.eichong.assets.utils;

import com.jcraft.jsch.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Random;

public class SftpTooL {

	/**
	 * 录音文件生成文件名规则
	 * @return
	 */
	public static String createRecodingFileName() {
		String FORMAT = "yyyyMMddHHmmss";
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT);
		Random rd = new Random();
		return sf.format(new Date())+(rd.nextInt(999)+1000);
    }
	/**
	 * 1. 第一步:连接sftp服务器,先获取Session
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public Session getSession(String host, int port, String username,String password) {
		Session session = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			session.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(sshConfig);
			session.connect();
			System.out.println("Session connected.");
		} catch (Exception e) {
			e.printStackTrace();
			if (session!= null && session.isConnected()){
				session.disconnect();
			}
		}
		return session;
	}


	/**
	 * 2.第二步: 连接sftp服务器,再获取链接
	 * @return
	 */
	public ChannelSftp getConnect(Session session) {
		ChannelSftp sftp = null;
		try {
			if(session == null){
				System.out.println("Can't Create Connect,Because session is null");
				return sftp;
			}
			Channel channel = session.openChannel("sftp");
			System.out.println("Opening Channel.");
			channel.connect();
			sftp = (ChannelSftp) channel;
			//sftp.setFilenameEncoding("gbk");
			System.out.println("Connected to " + session.getHost()+":"+session.getPort());
		} catch (Exception e) {
			e.printStackTrace();
			if (sftp!= null && sftp.isConnected()){
				sftp.disconnect();
			}
		}

		return sftp;
	}

	/**
	 * 3.第三步:关闭 channel和session
	 * @param channel
	 */
	public void disconnect(Channel channel ,Session session) {
		try {
			if (channel!= null && channel.isConnected()){
				channel.disconnect();
				System.out.println("Disconnected channel");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (session!= null && session.isConnected()){
				session.disconnect();
				System.out.println("Disconnected session");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 上传文件到远端服务器,如果在同一目录下,文件名相同会自动替换
	 * 如果上传一半,网络原因中断,那服务器上会有一半大小的文件,请重新上传.
	 * @param destDirectory
	 *            远端服务器要上传的目录  : /data/temp/test/
	 * @param srcDirectory
	 *            本地要上传的目录 : D:/test/
	 * @param srcFileName
	 *            本地要上传的文件 : upload.txt
	 * @param sftp
	 */
	public void upload(String destDirectory, String srcDirectory, String srcFileName, ChannelSftp sftp) throws Exception{
		try {
			sftp.cd(destDirectory);
			File file = new File(srcDirectory+srcFileName);
			if(!file.exists()){
				throw new Exception(srcDirectory+srcFileName+" is not exists");
			}
			System.out.println("上传本地文件"+srcDirectory+srcFileName+"到远端服务器"+destDirectory+" 开始");
			sftp.put(new FileInputStream(file), file.getName());
			System.out.println("上传本地文件"+srcDirectory+srcFileName+"到远端服务器"+destDirectory+" 结束");
//			sftp.put("D:/application/eclipse64ee/workspace/SFTP/src/com/isoftstone/www/ftp/SFTPTooL.java","/data/temp/test");//将本地目录的文件直接上传到服务器上
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 上传流到远端服务器,如果在同一目录下,文件名相同会自动替换
	 * 如果上传一半,网络原因中断,那服务器上会有一半大小的文件,请重新上传.
	 * @param destDirectory
	 *            远端服务器要上传的目录  : /data/temp/test/
	 * @param  srcStream
	 *            本地要上传的流 : D:/test/
	 * @param srcFileName
	 *            本地指定到远端服务器要生成的文件名 : upload.txt
	 * @param sftp
	 */
	public void upload(String destDirectory, InputStream srcStream, String srcFileName, ChannelSftp sftp) throws Exception{
		try {
			sftp.cd(destDirectory);
			if(srcStream == null){
				throw new Exception("流为空,"+srcFileName+" is not exists");
			}
			System.out.println("上传流"+srcFileName+"到远端服务器"+destDirectory+" 开始");
			sftp.put(srcStream, srcFileName);
			System.out.println("上传流"+srcFileName+"到远端服务器"+destDirectory+" 结束");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void uploadFile(String destDirectory, String strStream, String srcFileName, ChannelSftp sftp) throws Exception{
		try {
			sftp.cd(destDirectory);
			InputStream fileStream= getStreamFromString(strStream);
			if(fileStream == null){
				throw new Exception("流为空,"+srcFileName+" is not exists");
			}
			System.out.println("上传流"+srcFileName+"到远端服务器"+destDirectory+" 开始");
			sftp.put(fileStream, srcFileName);
			System.out.println("上传流"+srcFileName+"到远端服务器"+destDirectory+" 结束");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 在远端服务器上下载文件
	 * 
	 * @param remoteDirectory
	 *            在远端服务器上要下载的目录 :/data/temp/test/
	 * @param remoteFile
	 *            在远端服务器上要下载的文件名 :　download.txt
	 * @param localDirectory
	 *            本地所在文件夹 : D:/test/
	 * @param localFile
	 *            本地将要生成的的文件名 : download.txt
	 * @param sftp 链接
	 */
	public void download(String remoteDirectory, String remoteFile,String localDirectory, String localFile, ChannelSftp sftp)  throws Exception{
		try {
			sftp.cd(remoteDirectory);
			File file = new File(localDirectory);
			if(!file.exists())
				file.mkdirs();
			File saveFile = new File(localDirectory,localFile);
			System.out.println("从远端服务器下载文件"+remoteDirectory+remoteFile+"到本地"+localDirectory+localFile+" 开始");
			sftp.get(remoteFile, new FileOutputStream(saveFile));
			System.out.println("从远端服务器下载文件"+remoteDirectory+remoteFile+"到本地"+localDirectory+localFile+" 结束");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 在远端服务器上删除文件(仅能删除文件,不能删目录)
	 * 
	 * @param directory
	 *            在远端服务器上,要删除文件所在目录 : /data/temp/test/
	 * @param deleteFile
	 *            在远端服务器上,要删除的文件
	 * @param sftp 链接
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp)  throws Exception{
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
//			Vector v = sftp.ls(directory);
//			Enumeration vEnum = v.elements();
//			while(vEnum.hasMoreElements())
//				System.out.print("====:"+vEnum.nextElement());
//			sftp.rmdir(directory);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteDirectory(String directory, String deleteFile, ChannelSftp sftp)  throws Exception{
		try {
//			sftp.cd(directory);
//			sftp.rm(deleteFile);
//			Vector v = sftp.ls(directory);
//			Enumeration vEnum = v.elements();
//			while(vEnum.hasMoreElements())
//				System.out.print("====:"+vEnum.nextElement());
//			sftp.rmdir(directory);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 在远端服务器上的指定文件夹下创建新的目录(多层次)
	 * 
	 * @param directory
	 *            远端服务器上,要创建文件所在目录 : /data/temp/test/
	 * @param folderPath
	 *            远端服务器上,要创建的文件夹名 : ( 可以为多层次,形如  good 或  test2/good/ok )
	 * @param sftp 链接
	 */
	public void mkdir(String directory, String folderPath, ChannelSftp sftp)  throws Exception{
		try {
			sftp.cd(directory);//切换目录,如果目录不存在就会报错
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		String[] folders = folderPath.split("/");
		for(String currentFolder :folders){
			try{
				sftp.ls(currentFolder);//展示目录,如果文件夹不存在就会报错
				sftp.cd(currentFolder);
			}catch(Exception e){
				sftp.mkdir(currentFolder);//即然不存在,就创建该文件夹
				sftp.cd(currentFolder);
				System.out.println(currentFolder+" is no exists, make the dir success");
			}
		}
	}
	
	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

	/**
	* 将一个字符串转化为输入流
	*/
	public static InputStream getStreamFromString(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						hexStringToBytes(sInputString));
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 16进行字符串转成byte[]数组
	 * @param hexString
	 * @return
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
	
	private static byte charToByte(char c) {  
		return (byte) "0123456789ABCDEF".indexOf(c);  
	}

	/**
	 *
	 * @param host 10.9.2.49
	 * @param port 22
	 * @param username apache
	 * @param password Frdr2312dd22
	 * @param file MultipartFile
	 * @param handleType 1:上传 2：删除文件
	 * @param directory /var/www/html/deploy/test/
	 * @param folderPath excel/1000/2
	 * @param fileName 12.xls
	 */
	public static void uploadMultipartFile(String host,int port,String username,String password,MultipartFile file,int handleType,String directory,String folderPath,String fileName){
		SftpTooL sf = new SftpTooL();
		Session session = null;
		ChannelSftp channel = null;
		InputStream stream = null;
		try{
			session = sf.getSession(host, port, username, password);
			channel = sf.getConnect(session);
			if(handleType == 1){
				stream = file.getInputStream();
				sf.mkdir	(directory, folderPath, channel);//建目录
				sf.upload	(directory+folderPath, stream,fileName, channel);//上传
			}else if(handleType == 2){
				sf.delete	(folderPath, fileName, channel);//删除
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sf.disconnect(channel,session);
		}
	}

	//    http://10.9.2.49/deploy/test/
	//    /var/www/html/deploy/test/
	public static void main(String[] args) throws Exception{
		SftpTooL sf = new SftpTooL();
		Session session = null;
		ChannelSftp channel = null;
		InputStream stream = null;
		try{
			String host = "10.9.2.49";//ip
			int port = 22;				//port
			String username = "apache";//user
			String password = "Frdr2312dd22";//password
			session = sf.getSession(host, port, username, password);
			channel = sf.getConnect(session);
			stream = getStreamFromString(streamStr);
//			sf.mkdir	("/var/www/html/deploy/test/", "excel/template/", channel);//建目录
//			sf.upload	("/var/www/html/deploy/test/excel/template/", "D:/excel/12/","jueSuan.xls", channel);//上传
			sf.upload	("/var/www/html/deploy/test/excel/template/", "D:/excel/12/","shiShi-detail.xls", channel);//上传
//			sf.upload	("/var/www/html/deploy/test/excel/template/", "D:/excel/12/","shiShi.xls", channel);//上传

//			sf.upload	("/data/temp/test/", stream,"dd2323d.PNG", channel);//上传
//			sf.download	("/data/temp/test/", "download.txt", "D:/temp/haha/","download.txt", channel);//下载
//			sf.delete	("/var/www/html/deploy/test/excel/1000/2/", "111111.xls", channel);//删除
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sf.disconnect(channel,session);
		}
	}
	//http://10.9.2.49/deploy/test/excel/1000/2
	
	private static String streamStr = "89504e470d0a1a0a0000000d494844520000015b0000009c08020000005e379f0200000006624b474400ff00ff00ffa0bda793000000097048597300000ec400000ec401952b0e1b000016a249444154789ced9db172e33893c71b575f7d4f708f203970a9eeea52f109acb9c091d2cda4504a3673e86c132994224faa68821dea09a47cab5c0e2c3dc1444e2fc205240110e8064059d2ccd8ff5f6d6dd160a30172dc0d8004ff563f7efcf8f7bfff4d44dfbe7dbbbfbf27a2eaf8fffef37f8868f2e5bf0800f069f88f9fdd0100c02f04320200c0828c0000b0646484a922e5fc37dddac2e591886859d4a78a251151d1b637555c5795e5c99826a6d3f7ba0271a6cdbff2c91c97ce2fc3d477ae38ffde6f14b822191961a5496b5a0c697120ad697557174e86b4f98b8868b6a3c382860bdacd8888769a8613d2bafeefb0a8fd4c155159178e37a7ff632f0b1a1f6a3fb43ed1c9cfa598a66daee927c24ad3acf75e2793e6df5daf6ce1b2202aa99cf8c653459bb1fd3d79673e021d79dfaa614c76fc0f5916b43c526f46ab3b3a2ee979516713229aed6830275b75cb0c2039ac749d86a83d16b9e9a66806a27ae4991239b395e591b6d356ad4e7ee298b94c51b49cefd7b60973134c37bc61936d57f253d954c7ee085c2852052d4d13052503cd0cd46e67aac2a9f1f38e9434dbd9df0717f7df344e75138ae6b6d437d0e95278ff418af765842f2ba247ee776b4d4ad17c6f0bbe6f68fca565723fa1d793d2ff6c479b3e13b17f6ce8a09939c84e5339a179bf1e794aa22dd1aa99f5cc7a74b7a2724293b2fe45ece427823b971934b7a23723dd9e43d9a0b8b7859bbe75ceb62bf999ed6831ac2baeb41d81779a867b9a5333711b533f15ccb35d3d37f40a17435a377e16cf5963f87ad431836c6bfbcd383643e9cde8b0a0fd9e4a4d25d188ea2e99e418de7f90e25fef75f0e798fe58d257af74427a45cb22dbcb1d69dda1d15d63bc2ca820dacde8b8a4fd9efaca311ab4aa54c14f4477cdc475f640ea2f9aad88881e9fe9eb8a884ef123317b20d5a779f5c384746adcebbf921a8967f3db8d5036757b335a14b425e206e90e7e6e06f49a32eecdecb56fa734ddf2538316cdefc354d1f24b62d9325cd01dd1966872df2aef7aff01119de15d436f46e30d7de74ecd76f6dff2cb98366da36f6bba79f70275f640fb1722a2de0d0d1776c0f496ac3c77f578b29dd2e081aabe9ce247f66f9c94949ab56ea9efcc4dbc91f93c0ca97f01af9db8bba7e7640a715895feaf4d97c6badc7f50738eb78fb3079acf1336bd190de6f6a1c3b2a0e7853340e53f47d8b6d6c0c7d76664b8a3c1bcf353a8d9033d2ee9f199fe345d39c90f4bd15eab0f6e9c1f9eeb53db69f35ce09586e33a2b1d97ad05578cc04f45b51c3b2e69e43e79ddd31f4e546c06274e10ba52384f22968fedfb20d89b15d37494b617fd44ee3f90f9f1e3c7dbdbdbdbdbdbd3d3d35bc3d3d3d3eaef7f567fffa3b5d613d2e4fc37295b858b83d65a97133d5ce88aa1311eea836e616a19e39a5213699ae83465ab335e1343f7d484b3275db6fd2d86f515e9f7f909619c988b9830e58ba1bda8c9b06922da2eebe7b0b07e1613fb6f346cdcbaff702241bb5513a693a5d37ac295732bdc7f77eff7ca9e2ab9420e73a593b2eecce250f7b08cde7f20a3f05dc367a128e8eb8edebd50031f1bec59fc1c14aa7e628ae53488f2ee770de0b7c0bc9d01200ae60800000b320200c0828c0000b0a43382522a5972465ce75243991d607b1ef779d14b03e0d727ebc9a21b275a6bb624a76edc5e29654eb9c7ef776eccbcb35e752f29e47400800f46222398c88c1cc4d15a7b55a46c221d27fdbbbd650fc22cc6d6f5ba9ad901003e12895583891913c96189613b554aa922b505b8f2100ed7664aefceeddd72f75468e0251ad38a17f6e1bcc0f3c9ae2cd842e97af9f2e3b2b03d0dbf21df4ed96200ae4bd673042f56c30824a2e3b21851a943010cc749a4153774ebdd944ab981ed99b125e17cc10b72cf86f569b673c66f8b74bdb1fb60b74bfb5fff2d8b112d2ef27913009d4867046fdb330931d39bed82df73df49bc216fc61ece41a45aecd81e06798e074a4d3a0cd2f5c6ef03cb76aa36e3c3ea4bcaae9a6514c5b49a65545311675e6127214551606f2238890e738470a690df4cd25e394f257272813160e708529a88847a3cdf9d87f5285c3554738a5d8e72596fb63b2c86fb3d955a97341a51a9f561f1fc6d5bfbd98ceb2f8ecac11e0a21e034b2de3578b3f113a2c51dfce306ec5306b775af4bca79a040eda9810a1e13866ebdeaf14ebe8bde6ce70887a8e9b69a4a1c5ef6b41ea9e6abe5e2f690c80ec3c5aa1108b923fbbd6f6ff630507dd50884941008012791b543499a23749d29507b45c03ef38bfb8c3c08f0cadd85407ccc9716446cf7cec3ddfda4110eb95b354d1e16c349de6441706a3ce9924658358093e8f01c8182676f3993052fcea52aec823f4c1c9ee7e429760172a95940946561df3e1c978feb730b782c8bf6cb0d088480d3482ba6084ff5bd1ffd87eb9cd685e7c473e53514370b8771af6ea4ab9176593f2cd2f546ee837d9710dc1c536b18511f39340e26f5bb8ce1e250954d4add7e51118ac0009045423165fabfff4dc1fc59b7df085036aabd0b48f21631a3e0a1806416fec8d6757f0c2d3b5d1d001f80c493c530243437a266a283819df516310bdbcd3c956990730a800f0cbe7d0400589011000016640400800519010060b99e620a5b2be22aa795137a92733991fd48eec627003e1e17574c311e746abb91f1166e28f6de29c6fbc69a65769235665b8cbcb904e0f7e5e28a29aea50ed4538c8db42b81a2b11de943243b84e55ebb71571ac22ae0e37271c59430f223f1599d0dbf717043d7fba4222cc9a7daa4e51eb8ad787df33ac336c716b2b7c5954ff1ced65f392b5f40c54aaeb4cb4db1eb24e21f800817574c7183bc821dd595338f0803d5cb299e815b12763b9e26d8200fbd859d611b0d910554ec46e383bb03793b1d3d371ba2696423793bed6fc6a6dce4844a5ba12a1eccfb4eae10fc0310e5aa8a29f1f8f1f20e7143aef1a0e47dcd6ccf231da37690e7748f848c937f5b663b5bfa7d3378683e793cbe3e4f1eea0f99ef56e5a0f95be9db6fcf8baf4df99f8d2802d1dd4a379f4bdefde944bee49f01422cc0e14a8a2949dc588aa71e0a623239504b48a92712eac9be75e6b8dc0cee4df4f66e06ebc76573e671bd7fe1a5179b8fa91db67f6dc64cf269fb6780100b70b892624a2794f3d420ec80f951c9df3879d5430fac1fa939b64be74a88c7ef9bc1fdcefe7cb73abc16aa523e192e16c3973c2fcba23f1f94cc35fafe2520c40288e89a8a29f1f13c32207b55a4d94ab8bec85caa98ff27d7176cc7bc74d935537c0f46f0de6c577bdfddbcd06d7372ff72708c1cf983e3b250fdcdf8c02fda42ff1d8110cbe7e2e28a299e1f123e34d6cd2b3d29f584fd794f672a54fbb56878b6abc3ce44a7f4dbe9881eecb3037aad47eeed5f7353693b55fdcd586b417b29b964480121964fc7151453a42ae1b174ca23ec4cc438d262bc7bac259d4958a5e2b018fada266e85f639fbdcd094076f113c6f8cff1008b100872b29a684537acf43b898f7300d491d089724ec7301d62c5e31f97c21f32600f0eb7325c514d658b707db4cb79265a4ab5dcd929e734e01f03b826f1f0100166404008005190100604146000058aea7989289b79b38b34b9966911d44ec3627003e1b17574c09634cb24fee4a3ed9b9f4fad0abee2505bc47009f908b2ba6e8402545ca26d271d2bfdb5bf640dae910ee53486e8b00e063734ec51456ba43f2190ed7dee6e5b0dc3d151a843b9ddd03afbad42e6b2315b25228244b9b1c9d1b94e30780eb7336c51459bac33a89b4e2866ebd9b522937b03d33b6249c2f7841eed9b03ecd76cef86d11a550646913537e186f9cbf0a2b49aa00f013389b628a24dde13a8937a482edc93953777666413f4f0a25226da277cca7c471a5991690360197e7128a298c7447728ea09ca71239b9c018b07304294d44423d9eef4ea7256d5207717f3316be558c0269137079b2f623487304266c8ecb42d96f783d0ff126a8fd79326be6ceffbd1237d4bdae7aab06dd5e5c78d553f9ee3dd45a03e5607efa53834ada846a6913436ff63098f7ab9e8fd693929b8f0090e49c8a2971e90ee38ada2f20dc2628359b60330b9b9e94b31088e7236941c4762f0351dac4e00a289e0f489b80337036c51449ba4305cff6a4562808dd3071789e93a7f4d5a55024699365a19ca78ca3f5b9a547206d02cec379145352d21d5e5de2928bd750dc2c1cc6bdba62572f2f85c2489b78351ce3b8a44a0b489b80cb7325c514b7aee2f626bade2266d47ea6189e62bbea967875dd1f43cb4e5707c007e04a8a296e15dd1e8a436f11b3b0ddcc53990639a700f8c0e0db470080051901006041460000589011000096eb29a6b0b522ae725a39a127399713d98fe46e7c02e0e37171c514e341a7b61b196fe6987d3be845a3d413cf2cb393ac31db62e4cd2500bf2f17574c712d75a09e626ca45d09148ded481f22d9212cf7da8dbbd21056011f978b2ba684911f894fcdfddd4712f4515c1bb7249f6a93967be0b6e2f5cdeb0cdb1c5b2809a2d41f422a5f5825263c23d5a94e4c83bf2c2fda03c07371c51437c82bd8515d39f3883050bd9ce219b82561b7e369820df2d05bd819b6d1104910a52eafee575b5845129ea93e2b6db627b7be265b16235af81bc923f600485c5531251e3f5ede216ec8351e94bcaf99ed79e4eaa81de439dd2321e3841e228228e66be6feedf0b9f9384abc8dc7e51f9b311bd85512597d6997caf63e1062010e57524c49e2c6523cf5501093c9815a424a3d91504ff62d93deec8146752b82804afb361e5e68f012ae27aab906535db067bb02211660b892624a2792a9278c4f363d25170e9e1f6fd5e05e9d347f91d3628aed3733a13f8c5ffc880d6ee3f1f579bf5ed3e25055d8fc518fd587973dad474a29d59fefd7232bdf28d88b40880510d1351553e2e3796440d6c20382f0d835cb593250fb3942727dc1768c4d1c49b6df9e6ffbf571ef8636df6d4a106fe37051cf057ab3316daad8b7322987c570d29e2c70f6dd8110cbe7e2e28a299e1f123e34d6cd2b3d29f584fd89742613f5f38455fab7d4925a6a906e636fb65bd0bc59dc6f5f681c176aec6a2f0121964fc7151453f82adcb174ca23ec4cc438d262bc7bac259d4d58c53dd3dcad84f08cade229a29813c3c521c7be05845880c3951453c229bde741a5f6fc9886a40e844b12e38aed7fbc24ec277bb19d6e0200bf3e57524c618d757bb0cd742b5946badad52ce939e71400bf23f8f61100604146000058901100001664040080e57a8a2999b8cea586323bc0f63ceef3a29706c0afcfc51553c21893ec95fc9717dee95c7a7de855f79202de23804fc8c5155374a092226513e938e9dfed2d7b20ed7408f72924b74500f0b139a762cad1d1fa48fa0c876b6ff37258ee9e0a0dc29dceee81575d6a97b5910abb499bd8bba33cfd92ccfb06c01538a7624a7f33ae22f030de7841c246948b1bbaf56e4aa5dcc0f6ccd89270bee005b967c3fa34db39e3b7e5146913bb09d816c6ef1b0057e69c8a295afe543627c654b03d3967eacece2ce8f2522827489bb0c4ef5b0b489b80cb735ec594fa9734940049ce1194f3542227171803768e20a58948a8c7f35d942c691322aa850cda615c7990ee5b0b489b80cb735ec594fa5bfa7230f766bfc91833b303dd5e1478b8f37fafc40d75afabdeaa41b717175e7539df71644b9b506fb633cdbbca8a4491fbc60069137049cea99862b85b9583cd77c915b55f40b84d506a36c16616363d29672110cf476cb2a30c29944ed2262deeee27cfaf614f22f72d03489b80337036c59465a11c41e1d1ba91d650c1b33da9150a42374c1c2e39a7f4c5a450ba4a9b2c0bbb84382e1fcdfd91ee5b57206d02ce42871d4af169ff6c574e4da80d177a578f9bde486b0ec268d7c10b4ed64cea9e8be73652d7ac56a89d3ebc5abe93e3f2714d4473a5e655c1a4b43305f7564c4add146a2a1a73e7fe48f78de1b82cfaf33d919adee87ba2f5a8b83d7c255a8f1495fa96f6f37ed31b9a941a6aece014aea498e2d655dcde44d75bc48c9cf8f71e3d50306b08534958d7fd31b4ec7475007c00aea498e2566107f0b0501ae723a377626087140a0029f0ed2300c0828c0000b0202300002cc8080000cbf51453d85a115739ad9cd0939ccb89ec9272373e01f0f1b8b8628af190dc6e64bcb9db16c226bc68947ae29965769235665b8cbcb904e0f7e5e28a29aea50ed4538c8db42b81a2b11de943243b84e55ebb71571ac22ae0e37271c5146963628809b6f01b0737745dd8927cdcddd9f1af18bc16591ba9b0fab031fc84291455a9e826c422f991055a00887071c51437c82bd8515d39f3883050bd9ce219b82561b7e369820df2d05bd819b6d190e3b2185119fcf947a2ed74f45cff0dc892ec5f79ef2cc422f821e2055a00887355c59478fc787987b821d77850f2be66b6e7918e513bc873ba4742c6093df4663b361e8fafcf93e61b6af793c7ae422c929f0e408805385c493125891b4bf1d443414c26076a0929f544423dd9b74c7a3783f5631d5dc7e5e37aff124cecb38458627e448116af2b106201962b29a67422997ac2f8649706c98583e7c75b35b85727cd5f24cf69ee5687f1463509d4ff2bf1f9422c929f98400b07845800115d5331253e9e4706642d3c20088f5db39c2503b59f2324d7176cc7d8c491838dd9ddcd0bdd9a38ec2ac422f9b108022d794088e5737171c514cf0f091f1aebe6959e947ac2fe443a93896abf160dcf7675781adba99d0e74156291fc48022d5d8110cba7e3c78f1f6f6f6f6f6f6f4f4f4f6f0d4f4f4fabbfff59fdfd8f1b6952109a11c43e4f1f2ee2712b1d4ba73cc2ce448c232dc6bbc75a92f0e243c27fc960ee8c7bc2bc1438f8ab07fbbaa05dc796b37eb4d65a5b5fed7f0e1fd3e8a47e27325c1caab249a9db1d6a37003e2257524c09a7f49e0795daf3631a923a102e498c2bb6fff192b09fecc576ba0900fcfa5c49318535d6edc136d3ad6419e96a57b3a4e79c5300fc8ee0db470080051901006041460000589011000096eb29a664e23a971acaec00dbf3b8cfabed4100e0d7e4e28a29618c49f64afecb0bef742ebd3ef4aa7b4901ef11c027e4e28a293a504991b289749cf4eff6963d90763a84fb1492db2200f8d89c53318588eacf1fa3dfd5e8e07300e2be5608cbdd53a141b8d3d93df0aa4bedb23652a124792295b38a3200fc529c4d31a562598c68e16fc52521a25cdcd0ad77532ae506b667c69684f3052fc83d1bd6a7d9ce99b82f8254492d91d2943bdf7af08a3200fc529c4d31851afd9fd517d149bc21156c4fce99bab3330bbabc144a44aac47c4fdcbf1d3ebf1e1b9b98a24c0b4898809fc7d91453aab151124a49ce1194f3542227171803768e20a58948a827f39d872455d29b3d50a3541228c7e429ca40c204fc3ccea6987278d9d7a23dfdf97e3df266c5c91833b3036f51e0e1ceffbd1237d4bdae7aab06dd5e5c78d5d97cc72049956cbf192dc4c3f8a57d1bba28ca40c204fc0ccea6986285350e8be1243659a0f60b08b7094acd26d8ccc28eeaca5908c4f3119bec28430a85952ad97e7bbeed370637b4f9ce44fe898288a6b6e925244cc0b9399b628a840a9eed49ad5010ba61e2f03c274fe9ab48a1b85225fd5b7a3930364945994c2061022e4a871d4a39f1bf9daad19a88a8b83d54d3046fa4350761b4ebe005276b2675cfc5731ba96b562bd44e1f5e2dc689b954229a94ba99d9f7660f4e8726a5aee74ab35d3935e5c385dec982e9c765d19fef89d4f446df13ad47c5ede12bd17aa4a8d4b7b49ff7d53c6c1880737025c514b7aee2f626bade2266e4c4bf6b1c9a853fb275dd1f43cb4e5707c007e04a8a296e1576000f0ba5713e327a2707f6f4c89f710a800f0cbe7d0400589011000016640400800519010060414600005890110000166404008005190100604146000058901100001664040080051901006041460000589011000016640400800519010060414600005890110000166404008005190100604146000058901100001664040080e5ff011980df7e5d76b2160000000049454e44ae426082";
}

