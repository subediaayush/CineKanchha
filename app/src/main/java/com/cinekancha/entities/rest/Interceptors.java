package com.cinekancha.entities.rest;

import android.content.Context;
import android.util.Log;

import com.cinekancha.utils.Connectivity;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by aayushsubedi on 8/26/17.
 */

public class Interceptors {
	
	/*
	/**
	 * Interceptor to cache data and maintain it for a minute.
	 * <p>
	 * If the same network request is sent within a minute,
	 * the response is retrieved from cache.
	 */
	public static class ResponseCacheInterceptor implements Interceptor {
		@Override
		public okhttp3.Response intercept(Chain chain) throws IOException {
			okhttp3.Response originalResponse = chain.proceed(chain.request());
			return originalResponse.newBuilder()
					.removeHeader("Pragma")
					.header("Cache-Control", "public, max-age=" + 100)
					.build();
		}
	}
	
	/**
	 * Interceptor to cache data and maintain it for four weeks.
	 * <p>
	 * If the device is offline, stale (at most four weeks old)
	 * response is fetched from the cache.
	 */
	public static class OfflineResponseCacheInterceptor implements Interceptor {
		private final Context mContext;
		
		public OfflineResponseCacheInterceptor(Context context) {
			this.mContext = context;
		}
		
		@Override
		public okhttp3.Response intercept(Chain chain) throws IOException {
			Request request = chain.request();
			if (!Connectivity.isConnected(mContext)) {
				long maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
				request = request.newBuilder()
						.removeHeader("Pragma")
						.header("Cache-Control",
								"public, only-if-cached, max-stale=" + maxStale)
						.build();
			}
			return chain.proceed(request);
		}
	}
	
	
	/**
	 * An OkHttp interceptor that logs requests as curl shell commands. They can then
	 * be copied, pasted and executed inside a terminal environment. This might be
	 * useful for troubleshooting client/server API interaction during development,
	 * making it easy to isolate and share requests made by the app. <p> Warning: The
	 * logs generated by this interceptor have the potential to leak sensitive
	 * information. It should only be used in a controlled manner or in a
	 * non-production environment.
	 */
	public static class CurlLoggingInterceptor implements Interceptor {
		
		private static final Charset UTF8 = Charset.forName("UTF-8");
		
		private String curlOptions;
		
		/**
		 * Set any additional curl command options (see 'curl --help').
		 */
		public void setCurlOptions(String curlOptions) {
			this.curlOptions = curlOptions;
		}
		
		@Override
		public okhttp3.Response intercept(Chain chain) throws IOException {
			Request request = chain.request();
			
			boolean compressed = false;
			
			String curlCmd = "curl";
			if (curlOptions != null) {
				curlCmd += " " + curlOptions;
			}
			curlCmd += " -X " + request.method();
			
			Headers headers = request.headers();
			for (int i = 0, count = headers.size(); i < count; i++) {
				String name = headers.name(i);
				String value = headers.value(i);
				if ("Accept-Encoding".equalsIgnoreCase(name) && "gzip".equalsIgnoreCase(value)) {
					compressed = true;
				}
				curlCmd += " -H " + "\"" + name + ": " + value + "\"";
			}
			
			RequestBody requestBody = request.body();
			if (requestBody != null) {
				Buffer buffer = new Buffer();
				requestBody.writeTo(buffer);
				Charset charset = UTF8;
				MediaType contentType = requestBody.contentType();
				if (contentType != null) {
					charset = contentType.charset(UTF8);
				}
				// try to keep to a single line and use a subshell to preserve any line breaks
				curlCmd += " --data '" + buffer.readString(charset).replace("\n", "\\n") + "'";
			}
			
			curlCmd += ((compressed) ? " --compressed " : " ") + request.url();
			
			Log.i("HTTPInterceptor", curlCmd);
			
			return chain.proceed(request);
		}
		
	}
}
