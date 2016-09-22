import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImgSaver {
    public static boolean saveImg(String name, String path) {
        URL url;
        try {
            url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            File file = new File("images/" + name + ".jpg");
            if (!file.exists()) file.createNewFile();
            ImageIO.write(image, "jpg", file);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
