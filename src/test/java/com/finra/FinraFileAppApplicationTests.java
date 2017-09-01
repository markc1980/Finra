package com.finra;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FinraFileAppApplicationTests {

	private String fileToUpload = null;// update to some file location

	@Test
	public void upload()
			throws ClientProtocolException, IOException, JSONException {
		//SEE THE PROPERTY data.storage.location IN application.properties where file will be stored
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/uploadFile");

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		JSONObject jo = new JSONObject();
		jo.put("owner", "Mark Chin");
		jo.put("permissions", "R");

		builder.addTextBody("fileMetaData",jo.toString(), ContentType.APPLICATION_JSON);
		//builder.addBinaryBody("file", new File("/Users/markchin/Desktop/b1request.xml"),
		builder.addBinaryBody("file", new File(fileToUpload),
				ContentType.APPLICATION_OCTET_STREAM, "uploadedFile.txt");


		ProgressHttpEntityWrapper.ProgressCallback progressCallback = new ProgressHttpEntityWrapper.ProgressCallback() {

			@Override
			public void progress(float progress) {
				//Use the progress
				System.out.printf(" Progress  = "+progress);
			}

		};


		HttpEntity multipart = builder.build();
		httpPost.setEntity(new ProgressHttpEntityWrapper(multipart, progressCallback));

		CloseableHttpResponse response = client.execute(httpPost);
		client.close();
	}

	/**
	 * Test assumes file in location /data/text.tx
	 *
     */
	@Test
	public void downloadFile() throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8080/downloadFile/ID123");
		CloseableHttpResponse response = client.execute(httpGet);
		InputStream is  = response.getEntity().getContent();
		String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		System.out.println("Download = "+text);
	}

	@Test
	public void downloadMetaData() throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8080/downloadFileMetaData/ID123");
		CloseableHttpResponse response = client.execute(httpGet);
		InputStream is  = response.getEntity().getContent();
		String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		System.out.println(" ************ Download MetaData = "+text);

	}

	@Test
	public void searchFile() throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8080/searchFiles/file/owner/markc/startDate/2017-08-11/endDate/2017-11-11?page=0&size=2");
		CloseableHttpResponse response = client.execute(httpGet);
		InputStream is  = response.getEntity().getContent();
		String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		System.out.println(" ************ Search Results = "+text);

	}

//-----------------------------------------------------------------------
	static class ProgressHttpEntityWrapper extends HttpEntityWrapper {

		private final ProgressCallback progressCallback;

		public interface ProgressCallback {
			public void progress(float progress);
		}

		public ProgressHttpEntityWrapper(final HttpEntity entity, final ProgressCallback progressCallback) {
			super(entity);
			this.progressCallback = progressCallback;
		}

		@Override
		public void writeTo(final OutputStream out) throws IOException {
			this.wrappedEntity.writeTo(out instanceof ProgressFilterOutputStream ? out : new ProgressFilterOutputStream(out, this.progressCallback, getContentLength()));
		}

		static class ProgressFilterOutputStream extends FilterOutputStream {

			private final ProgressCallback progressCallback;
			private long transferred;
			private long totalBytes;

			ProgressFilterOutputStream(final OutputStream out, final ProgressCallback progressCallback, final long totalBytes) {
				super(out);
				this.progressCallback = progressCallback;
				this.transferred = 0;
				this.totalBytes = totalBytes;
			}

			@Override
			public void write(final byte[] b, final int off, final int len) throws IOException {
				//super.write(byte b[], int off, int len) calls write(int b)
				out.write(b, off, len);
				this.transferred += len;
				this.progressCallback.progress(getCurrentProgress());
			}

			@Override
			public void write(final int b) throws IOException {
				out.write(b);
				this.transferred++;
				this.progressCallback.progress(getCurrentProgress());
			}

			private float getCurrentProgress() {
				return ((float) this.transferred / this.totalBytes) * 100;
			}

		}
	}

}
