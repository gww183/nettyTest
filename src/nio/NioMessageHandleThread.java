package nio;

import java.io.IOException;
import java.nio.channels.Selector;

public class NioMessageHandleThread implements Runnable {
	
	private Selector selector;
	public NioMessageHandleThread(Selector selector) {
		this.selector = selector;
	}

	@Override
	public void run() {
			try {
				while(true) {
					int num = this.selector.select();
					System.out.println(Thread.currentThread().getName() + ",检测到客户到连接");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
