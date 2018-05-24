package nio;


public class NioClient {
	public static void main(String[] arg) {
		 new Thread(new NioClientTread("127.0.0.1", 10001)).start();
	}

}
