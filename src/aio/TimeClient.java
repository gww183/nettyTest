package aio;

import aio.client.AsyncTimeClientHandler;

public class TimeClient {

	public static void main(String[] args) {
		new Thread(new AsyncTimeClientHandler(8080)).start(); 
	}

}
