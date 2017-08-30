package com.finra;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;


public class FinraFileAppApplicationTests {

	@Test
	public void contextLoads() {

	}

	@Test
	public void whenSendMultipartRequestUsingHttpClient_thenCorrect()
			throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/uploadFile");

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("username", "John");
		builder.addTextBody("password", "pass");
		builder.addBinaryBody("file", new File("/Users/markchin/Movies/insanity/Shaun T - Insanity workout/01 - Fit Test.m4v"),
				ContentType.APPLICATION_OCTET_STREAM, "file.ext");

		HttpEntity multipart = builder.build();
		httpPost.setEntity(multipart);

		CloseableHttpResponse response = client.execute(httpPost);
		client.close();
	}

}
