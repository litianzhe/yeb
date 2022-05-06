package com.xxxx.server.util;


import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @className: FastDFSUtils工具类
 * @copyright: HTD
 * @description: FastDFS
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/22
 * @version: 1.0
 */
public class FastDFSUtils {

	private static Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

	/**
	 * 0.
	 * 初始化客户端
	 * ClientGlobal.init(path); 读取dfs配置文件并初始化对应属性
	 */
	static {
		try {
			String filePath = new ClassPathResource( "fdfs_client.conf" ).getFile().getAbsolutePath();
			ClientGlobal.init( filePath );
		} catch (Exception e) {
			logger.error("初始化DFS失败->{}",e.getMessage());
		}
	}

	/**
	 * 3
	 * 上传文件
	 * @param file
	 * @return
	 */
	public static String[] upload(MultipartFile file){
		String filename = file.getOriginalFilename();
		logger.info( "文件名:{}", filename );
		StorageClient storageClient = null;
		String[] upLoadRes = null;
		try {
			// 获取storage客户端
			storageClient = getStorageClient();
			// 上传
			upLoadRes = storageClient.upload_file( file.getBytes(), filename.substring(
					filename.lastIndexOf( "." )+ 1 ), null );
		} catch (Exception e) {
			logger.error( "上传文件失败->{}",e.getMessage() );
		}
		if ( null == upLoadRes ){
			logger.error( "上传文件失败->{}",storageClient.getErrorCode() );
		}
		return upLoadRes;
	}


	/**
	 * 4
	 * 获取文件信息
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 */
	public static FileInfo getFileInfo(String groupName, String remoteFileName){
		StorageClient storageClient = null;
		try {
			storageClient = getStorageClient();
			return storageClient.get_file_info( groupName, remoteFileName );
		} catch (Exception e){
			logger.error( "文件信息获取失败->{}",e.getMessage() );
		}
		return null;
	}

	/**
	 * 5
	 * 文件下载
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 */
	public static InputStream download(String groupName, String remoteFileName){
		StorageClient storageClient = null;
		try {
			storageClient = getStorageClient();
			byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
			InputStream inputStream = new ByteArrayInputStream(fileByte);
			return inputStream;
		} catch (Exception e) {
			logger.error("文件下载失败->{}",e.getMessage());
		}
		return  null;
	}

	/**
	 * 6
	 * 文件删除
	 * @param groupName
	 * @param remoteFileName
	 */
	public static void removeFile(String groupName,String remoteFileName){
		StorageClient storageClient = null;
		try {
			storageClient = getStorageClient();
			storageClient.delete_file(groupName, remoteFileName);
		} catch (Exception e) {
			logger.error("文件删除失败->{}",e.getMessage());
		}
	}



	/**
	 * 2.
	 * 获取storage客户端
	 * @return
	 * @throws IOException
	 */
	private static StorageClient getStorageClient() throws IOException {
		TrackerServer trackerServer = getTrackerServer();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		return storageClient;
	}

	/**
	 * 1.
	 * 生成tracker服务器
	 * @return
	 * @throws IOException
	 */
	private static TrackerServer getTrackerServer() throws IOException {
		TrackerClient trackerClient = new TrackerClient(  );
		TrackerServer trackerServer = trackerClient.getConnection();
		return trackerServer;
	}


	/**
	 * 7
	 * 获取文件路径
	 * @return
	 */
	public static String getTrackerUrl(){
		TrackerClient trackerClient = new TrackerClient();
		StorageServer storageServer = null;
		try {
			TrackerServer trackerServer = getTrackerServer();
			storageServer = trackerClient.getStoreStorage(trackerServer);
		} catch (IOException e) {
			logger.error("文件路径获取失败->{}",e.getMessage());
		}
		return "http://"+ Objects.requireNonNull(storageServer).getInetSocketAddress().getHostString()+":8888/";
	}



}
