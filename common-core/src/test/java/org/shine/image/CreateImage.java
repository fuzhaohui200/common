package org.shine.image;

import java.io.IOException;

import org.shine.common.images.GenerateImage;

public class CreateImage {
	public static void main(String[] args) {
		String imagePath = CreateImage.class.getResource("/images").getPath();
		try {
			GenerateImage.generateImage(imagePath, imagePath +"/doc.png", "欢迎使用有道去笔记中摇滚乐要魂牵梦萦魂牵梦萦魂牵梦萦.doc", "2012/11/07 18:57, 30.50KB", "D:/image1.png");
			GenerateImage.generateImage(imagePath, imagePath + "/ppt.png", "欢迎使用有道去笔记.doc", "2012/11/07 18:57, 30.50KB", "D:/image2.png");
			GenerateImage.generateImage(imagePath, imagePath + "/excel.png", "欢迎使用有道去笔记.doc", "2012/11/07 18:57, 30.50KB", "D:/image3.png");
			GenerateImage.generateImage(imagePath, imagePath + "/txt.png", "欢迎使用有道去笔记.doc", "2012/11/07 18:57, 30.50KB", "D:/image4.png");
			GenerateImage.generateImage(imagePath, imagePath + "/pdf.png", "欢迎使用有道去笔记.doc", "2012/11/07 18:57, 30.50KB", "D:/image5.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*public static void generateImage(String imageName, String title,
			String fileSize, String targetFile) {

		try {
			int width = 220;
			int height = 55;

			File file = new File(targetFile);

			File backgroudImage = new File("images/attachment.png");
			Image bgImage = ImageIO.read(backgroudImage);

			File _file = new File(imageName);
			Image src = ImageIO.read(_file);

			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			g2.setBackground(Color.WHITE);
			g2.clearRect(0, 0, width, height);
			g2.setPaint(Color.BLUE);
			Font defaultFont = new Font("宋体", Font.PLAIN, 12);
			g2.setFont(defaultFont);

			g2.drawImage(bgImage, 0, 0, 220, 55, null);
			g2.drawImage(src, 14, 10, 26, 31, null);

			g2.drawString(title, 50, 20);
			g2.drawString(fileSize, 50, 40);

			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}