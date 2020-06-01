import java.io.File;

public class test {

	public static void main(String[] args) {
		File f = new File("test.txt");
		
		

		System.out.println(System.getProperty("user.dir"));
		System.out.println("絕對路徑 : " + f.getAbsolutePath());
		System.out.println("路徑 : " + f.toPath());
		System.out.println("URI : " + f.toURI());
		System.out.println("URI + ASCII : " + f.toURI().toASCIIString());

	}

}
