package eeggui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class eegGUI extends JFrame {

	protected Container contentPane;
	private JPanel pane;
	private JButton go;
	
	private JFileChooser fileSelect;
	private DataMarking marker;
	
	public eegGUI(){
		super("Kinematic Analysis");
		setSize(400, 300);
		setLayout(new BorderLayout());
		
		// initialize components
		contentPane = getContentPane();
		
		fileSelect = new JFileChooser();
		fileSelect.setFileFilter(new EEGDataFilter());
		fileSelect.setMultiSelectionEnabled(true);
		
		marker = new DataMarking();
		go = new JButton("ANALYZE!");
		
		pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(new JPanel());
		pane.add(go);
		pane.add(new JPanel());
		
		// make listeners
		go.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (fileSelect.showOpenDialog(eegGUI.this) == JFileChooser.APPROVE_OPTION) {
					File[] files = fileSelect.getSelectedFiles();
					String[] fileNames = new String[files.length];
					for (int i = 0; i < files.length; i++) 
						fileNames[i] = files[i].getAbsolutePath();
					marker.markData(fileNames);
				}
			}
		});
		
		// add components to the frame
		contentPane.add(pane, BorderLayout.CENTER);
		
		setMinimumSize(new Dimension(400, 300));
		setSize(400, 300);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

}
