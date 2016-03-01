package com.movie.movieticketbooking.net;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.util.Log;
/**
 * Responsible For connecting to the server
 * @author rmalgi
 *
 */
public class JsonRequestCommand {
	
	private static final String TAG = JsonRequestCommand.class.getSimpleName();
	
	private HttpGet get;
	private HttpClient client;
	private boolean canceled = false;
	
	public void cancel() {
		canceled = true;
		if (get != null)
			get.abort();
	}
	
	
	public InputStream requestStream(String url) throws IOException {
		canceled = false;
		HttpGet get = createGet(url);
		HttpClient client = createClient();
		HttpResponse response;
		try {
			response = client.execute(get);
			return response.getEntity().getContent();
		} catch (IOException e) {
			if (!canceled) {
				Log.e(TAG, "IOException", e);
			}
			throw e;
		}
	}
	
	protected HttpGet createGet(String fullUrl) {
		get = new HttpGet(fullUrl);
		get.setHeader("Content-Type", "application/xml");
		return get;
	}
	
	protected HttpClient createClient() {
		client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		return client;
	}
	
	final protected Document streamToXml(InputStream stream) throws SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(stream);
			return doc;
		} catch (ParserConfigurationException e) {
			throw new SAXException(e.getMessage());
		}
	}
	
}