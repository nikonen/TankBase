package control;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class Help {

	public Image loadPic(String path) {
		BufferedImage img;
		java.net.URL imgURL = getClass().getClassLoader().getResource(path);
		if (imgURL != null) {
			
			return new ImageIcon(imgURL).getImage();
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public URL lataaUrl(String polku) {

		URL url;

		url = this.getClass().getClassLoader().getResource(polku);

		if (url != null) {
			return url;
		} else {
			return null;
		}

	}

}
