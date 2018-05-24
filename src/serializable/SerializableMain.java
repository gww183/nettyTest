package serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializableMain {

	public static void main(String[] args) {
		UserInfo userInfo = new UserInfo();
		userInfo.buildUserId(1000).buildUserName("hello netty");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(userInfo);
			os.flush();
			os.close();
			byte[] b = bos.toByteArray();
			System.out.println(b.length);
			System.out.println(userInfo.codeC().length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
