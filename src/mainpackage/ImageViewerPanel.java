package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class ImageViewerPanel extends JPanel implements ActionListener {
	private JFileChooser chooser = new JFileChooser(".");
	File selectedFile;
	JLabel imageLabel;
	static ImageIcon image;

	// konfiguracja Layoutu
	BorderLayout buttonConstraints = new BorderLayout();

	public ImageViewerPanel() {
		super();
		setLayout(new BorderLayout());
		initializer();
	}

	private void initializer() {

		// konfiguracja paska Menu
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.setBackground(new Color(0, 200, 50));
		jMenuBar.setOpaque(true);
		jMenuBar.setPreferredSize(new Dimension(1000, 100));
		JMenu jMenu = new JMenu("File...");
		jMenuBar.add(jMenu);
		JMenuItem firstItem = new JMenuItem("Browse...");
		firstItem.addActionListener(this);
		jMenu.add(firstItem);

		// Konfigutacja przycisków
		JButton NextButton = new JButton("Next >");

		NextButton.addActionListener(this);
		JButton PreviousButton = new JButton("< Prev");
		PreviousButton.addActionListener(this);

		// dodanie przycisków do panelu
		add(NextButton, buttonConstraints.LINE_END);
		add(PreviousButton, buttonConstraints.LINE_START);
		add(jMenuBar, buttonConstraints.PAGE_START);
//		 add(imageLabel, buttonConstraints.CENTER);
	}

	class ImageComponent extends JComponent {
		private Image image;

		public ImageComponent(String path) throws IOException {

			File image2 = new File(path);
			image = ImageIO.read(image2);

		}

		public void paintComponent(Graphics g) {
			if (image == null)
				return;
			int imageWidth = image.getWidth(this);
			int imageHeight = image.getHeight(this);

			g.drawImage(image, 50, 50, this);

			for (int i = 0; i * imageWidth <= getWidth(); i++)
				for (int j = 0; j * imageHeight <= getHeight(); j++)
					if (i + j > 0)
						g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);

		}
	}
	public void displayerOfImages(ImageIcon image) {
		JLabel imageLabel = new JLabel(image);	
		add(imageLabel, buttonConstraints.CENTER);
		
	}
	public static ArrayList<String> getAllImages(File directory, boolean descendIntoSubDirectories) throws IOException {
		ArrayList<String> resultList = new ArrayList<String>(256);
		File[] f = directory.listFiles();
		for (File file : f) {
			if (file != null && file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png") && !file.getName().startsWith("tn_")) {
				resultList.add(file.getCanonicalPath());
			}
			if (descendIntoSubDirectories && file.isDirectory()) {
				ArrayList<String> tmp = getAllImages(file, true);
				if (tmp != null) {
					resultList.addAll(tmp);
				}
			}
		}
		// zamieniam ArrayListe na Stringa
		String listString []  = resultList.toArray(new String[resultList.size()]);
		if (resultList.size() > 0) {
			System.out.println(resultList.get(0));
			image = new ImageIcon(resultList.get(0));	
			return resultList;
		} else {
			System.out.println(resultList);
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Browse...")) {
			chooser.setControlButtonsAreShown(false);
			chooser.setFileFilter(new FolderFilter());
			chooser.setControlButtonsAreShown(true);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				System.out.println(selectedFile);
				
				System.out.println(selectedFile.getName().toLowerCase());
				
				try {
					getAllImages(selectedFile, true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				imageLabel = new JLabel(image);
				add(imageLabel, buttonConstraints.CENTER);
			}

		}
	}

	private static class FolderFilter extends javax.swing.filechooser.FileFilter {

		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}

		@Override
		public String getDescription() {
			return "We only take directories";
		}

	}

}
