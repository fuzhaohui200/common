package org.shine.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.*;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpClientUtil {
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	
	public static String doUpload(String url, Map<String, String> params, Map<String, String> headers, String fileParam, List<File> files) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000); // 连接时间
		HttpConnectionParams.setSoTimeout(httpParams, 300000); // 传输超时时间
		HttpConnectionParams.setSocketBufferSize(httpParams, 81920);
		HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(httpParams, "UTF-8");
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, Charset.forName("UTF-8"));
		StringBuilder urlStr = new StringBuilder(url);
		if(params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for(Entry<String, String> entry : entrySet) {
				urlStr.append("&");
				urlStr.append(entry.getKey());
				urlStr.append("=");
				urlStr.append(entry.getValue());
			}
		}
		HttpPost post = new HttpPost(urlStr.toString());
		MultipartEntity multipartEntity = new MultipartEntity();
		for(File file : files) {
			FileBody cbFileBody = new FileBody(file);
			multipartEntity.addPart(fileParam, cbFileBody);
		}
		post.setEntity(multipartEntity);
		HttpResponse response = null;
		String content = null;
		try {
			response = httpClient.execute(post);
			content = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			logger.error("请求协议异常");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return content;
	}
	
	public static String doPost(String url, Map<String, String> params, Map<String, String>headers) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000); // 连接时间
		HttpConnectionParams.setSoTimeout(httpParams, 300000); // 传输超时时间
		HttpConnectionParams.setSocketBufferSize(httpParams, 81920);
		HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(httpParams, "UTF-8");
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, Charset.forName("UTF-8"));
        HttpResponse resp;
		try {
			HttpPost postRequest = new HttpPost(url);
			if(headers != null) {
		        Set<Entry<String, String>> entrySet = headers.entrySet();
		        for(Entry<String, String> entry : entrySet) {
		        	  postRequest.setHeader(entry.getKey(), entry.getValue());
		        }
			}
			
			if(params != null) {
				Set<Entry<String, String>> paramSet = params.entrySet();
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
		        for(Entry<String, String> paramEntry : paramSet) {
		        	 nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));  
		        }
		        postRequest.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
			}
			resp = httpClient.execute(postRequest);
			if (resp.getStatusLine().getStatusCode() == 200) {
	            BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
	            String output;
	            StringBuilder content = new StringBuilder();
	            while ((output = br.readLine()) != null) {
	                content.append(output);
	            }
	           return content.toString();
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
        return null;
	}
	
	public static String doPostBigParam(String url, Map<String, String> params, Map<String, String>headers) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000); // 连接时间
		HttpConnectionParams.setSoTimeout(httpParams, 300000); // 传输超时时间
		HttpConnectionParams.setSocketBufferSize(httpParams, 81920);
		HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(httpParams, "UTF-8");
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, Charset.forName("UTF-8"));
        HttpResponse resp;
		try {
			HttpPost postRequest = new HttpPost(url);
			if(headers != null) {
		        Set<Entry<String, String>> entrySet = headers.entrySet();
		        for(Entry<String, String> entry : entrySet) {
		        	  postRequest.setHeader(entry.getKey(), entry.getValue());
		        }
			}
			if(params != null) {
				Set<Entry<String, String>> paramSet = params.entrySet();
				 MultipartEntity multipartEntity = new MultipartEntity();
		        for(Entry<String, String> paramEntry : paramSet) {
		        	  ContentBody contentBody = new StringBody(paramEntry.getValue());
				        multipartEntity.addPart(paramEntry.getKey(), contentBody);
		        }
		        postRequest.setEntity(multipartEntity);  
			}
			resp = httpClient.execute(postRequest);
			if (resp.getStatusLine().getStatusCode() == 200) {
	            BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
	            String output;
	            StringBuilder content = new StringBuilder();
	            while ((output = br.readLine()) != null) {
	                content.append(output);
	            }
	           return content.toString();
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
        return null;
	}
	
	public static String doGet(String url, Map<String, String>params, Map<String, String> headers) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000); // 连接时间
		HttpConnectionParams.setSoTimeout(httpParams, 300000); // 传输超时时间
		HttpConnectionParams.setSocketBufferSize(httpParams, 81920);
		HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(httpParams, "UTF-8");
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, Charset.forName("UTF-8"));
        HttpResponse resp;
		try {
			StringBuilder urlStr = new StringBuilder(url);
			if(params != null) {
				Set<Entry<String, String>> paramEntrySet = params.entrySet();
				for(Entry<String, String> paramEntry : paramEntrySet) {
					urlStr.append("&");
					urlStr.append(paramEntry.getKey());
					urlStr.append("=");
					urlStr.append(paramEntry.getValue());
				}
			}
			HttpGet getRequest = new HttpGet(urlStr.toString());
			if(headers != null) {
		        Set<Entry<String, String>> entrySet = headers.entrySet();
		        for(Entry<String, String> entry : entrySet) {
		        	  getRequest.setHeader(entry.getKey(), entry.getValue());
		        }
			}
			resp = httpClient.execute(getRequest);
			if (resp.getStatusLine().getStatusCode() == 200) {
	            BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
	            String output;
	            StringBuilder content = new StringBuilder();
	            while ((output = br.readLine()) != null) {
	                content.append(output);
	            }
	           return content.toString();
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
        return null;
	}
}