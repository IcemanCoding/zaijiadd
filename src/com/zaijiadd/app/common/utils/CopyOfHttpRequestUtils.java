package com.zaijiadd.app.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * 请求php端修改库存等  示例代码
 * @author wanglei
 *
 */
public class CopyOfHttpRequestUtils {

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendUTF8Post( String url, String method, String param,
			String zjtoken ) {
		// System.err.println("sendPost param:" + param);
		System.out.println( url + method + param );

		url = "http://121.43.149.246/v1/store/7/store_goods/batch_add.action";

		StringBuffer sb = new StringBuffer();
		sb.append( "goods=[{'id':'1','category_id': '0','name': 'xxx','spec': '100g','pic': 'http://www.baidu.com','purchase_price': '10','stock': '10'}]" );
		sb.append( "&rstr=aaaaaa&timestamp=20150101&sign=b7561f3b7029d1632d9a88710c2fd9ea" );

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			try {
				System.setProperty( "sun.net.http.retryPost", "false" );
			} catch ( Exception e ) {
			}
			URL realUrl = new URL( url );
			// 打开和URL之间的连接

			String host = "api-test-sup.zaijiadd.com";
			Proxy proxy = new Proxy( Proxy.Type.HTTP, new InetSocketAddress(
					InetAddress.getByAddress( host, new byte[] { ( byte ) 121,
							( byte ) 43, ( byte ) 149, ( byte ) 246 } ), 80 ) );
			HttpURLConnection conn = ( HttpURLConnection ) realUrl
					.openConnection( proxy );
			conn.setConnectTimeout( 60000 );
			conn.setReadTimeout( 60000 );

			// 设置通用的请求属性
			conn.setRequestProperty( "accept", "*/*" );
			conn.setRequestProperty( "connection", "Keep-Alive" );
			conn.setRequestProperty( "user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)" );
			conn.setRequestProperty( "Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8" );
			// conn.setRequestProperty( "Host", "api-test-sup.zaijiadd.com" );

			// 发送POST请求必须设置如下两行
			conn.setDoOutput( true );
			conn.setDoInput( true );
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter( new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8" ) );
			// 发送请求参数
			out.print( sb.toString() );

			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader( new InputStreamReader(
					conn.getInputStream(), "UTF-8" ) );
			String line;
			while ( ( line = in.readLine() ) != null ) {

				result += line;

			}
		} catch ( Exception e ) {
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if ( out != null ) {
					out.close();
				}
				if ( in != null ) {
					in.close();
				}
			} catch ( IOException ex ) {
			}
		}
		return result;

	}

	public static String getResponseText( String queryUrl, String host,
			String ip, String params ) {
		// queryUrl，完整的url，host和ip需要绑定的host和ip
		
		System.out.println( "inputs: url:" + queryUrl + ";  host: " + host + "; ip : " + ip + "; params : " + params );
		
		BufferedReader in = null;
		InputStream is = null;
		String result = "";
		BufferedReader br = null;

		StringBuffer res = new StringBuffer();

		try {

			HttpURLConnection httpUrlConn = null;

			URL url = new URL( queryUrl );

			if ( ip != null ) {

				String str[] = ip.split( "\\." );

				byte[] b = new byte[ str.length ];

				for ( int i = 0, len = str.length; i < len; i++ ) {

					b[i] = ( byte ) ( Integer.parseInt( str[i], 10 ) );

				}

				Proxy proxy = new Proxy( Proxy.Type.HTTP,

				new InetSocketAddress( InetAddress.getByAddress( b ), 80 ) ); // b是绑定的ip，生成proxy代理对象，因为http底层是socket实现，

				httpUrlConn = ( HttpURLConnection ) url.openConnection( proxy );

			} else {

				httpUrlConn = ( HttpURLConnection ) url

				.openConnection();

			}

//			httpUrlConn.setRequestMethod( "POST" );
			httpUrlConn.setRequestProperty( "accept", "*/*" );
			httpUrlConn.setRequestProperty( "connection", "Keep-Alive" );
			httpUrlConn.setRequestProperty( "user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)" );
			httpUrlConn.setRequestProperty( "Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8" );
			httpUrlConn.setDoOutput( true );
			httpUrlConn.setDoInput( true );

			httpUrlConn.setConnectTimeout( 2000 );

			httpUrlConn.setReadTimeout( 2000 );

//			httpUrlConn.setDefaultUseCaches( false );
//
//			httpUrlConn.setUseCaches( false );
			PrintWriter out = null;
			out = new PrintWriter( new OutputStreamWriter(
					httpUrlConn.getOutputStream(), "UTF-8" ) );
			// 发送请求参数
			out.print( params.toString() );
			out.flush();

			in = new BufferedReader( new InputStreamReader(
					httpUrlConn.getInputStream(), "UTF-8" ) );
			String line;
			
			while ( ( line = in.readLine() ) != null ) {

				result += line;

			}
			
		} catch ( Exception e ) {
			System.out.println( " -      --");
		}
		return result;
	}

	public static void main( String[] args ) {

		StringBuffer sb = new StringBuffer();
		sb.append( "goods=[{'id':'6532','category_id':'2','name':'xxx','spec':'100g','pic':'http://www.baidu.com','purchase_price':'10','stock':'10'}]" );
		sb.append( "&rstr=aaaaaa&timestamp=20150101&sign=b7561f3b7029d1632d9a88710c2fd9ea" );
		String s = getResponseText( "http://api-test-sup.zaijiadd.com/v1/store/7/store_goods/batch_add.action", "api-test-sup.zaijiadd.com", "121.43.149.246", sb.toString() );
		System.out.println( s );
//		String res = sendUTF8Post( "", "", "", "" );
//		System.out.println( res );

	}

}