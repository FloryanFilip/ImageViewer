package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ImageViewer {
	public static void main(String [] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable () {
		public void run() {
			JFrame frame = new JFrame("ImageViewer");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageViewerPanel panel = new ImageViewerPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		
		JMenu jMenu = new JMenu("File...");
		jMenu.setBackground(new Color(0, 0, 153));
		jMenu.setOpaque(true);
		jMenu.setPreferredSize(new Dimension(1000,100));
		JMenuItem firstItem = new JMenuItem("Browse...");
	
		jMenu.add(firstItem);
		
		
		
		
		frame.setMinimumSize(new Dimension(1000,500));
		frame.setVisible(true);
		frame.pack();
		}
	});
}
}
