package eeggui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class eegPanel extends JPanel {

	private JComboBox elec;
	private JList analysis;
	private JButton go;
	private eegListener formListener;
	private String[] electrodes = {"FP1","AF7","AF3","F1", "F3", "F5", "F7", "FT7","FC5","FC3","FC1",
			"C1","C3","C5","T7","TP7","CP5","CP3","CP1","P1","P3","P5","P7","P9","PO7","PO3","O1","Iz",
			"Oz","POz","Pz","CPz","FPz","FP2","AF8","AF4","AFz","Fz","F2","F4","F6","F8","FT8","FC6",
			"FC4","FC2","FCz","Cz","C2","C4","C6","T8","TP8","CP6","CP4","CP2","P2","P4","P6","P8",
			"P10","PO8","PO4", "O2"};

	public eegPanel(int width) {
		Dimension dim = getPreferredSize();
		dim.width = width/2;
		setPreferredSize(dim);

		// elements
		go = new JButton("OK");
		elec = new JComboBox();
		analysis = new JList();
		
		// set up the elements
		DefaultComboBoxModel elecModel = new DefaultComboBoxModel();
		for (int i = 0; i < electrodes.length; i++){
			elecModel.addElement(electrodes[i]);
		}
		
		DefaultListModel anaModel = new DefaultListModel();
		anaModel.addElement("FFT");
		anaModel.addElement("Wavelet");

		elec.setModel(elecModel);
		analysis.setModel(anaModel);
		analysis.setPreferredSize(new Dimension(100, 40));
		analysis.setBorder(BorderFactory.createEtchedBorder());
		analysis.setSelectedIndex(0);
		

		// add listeners
		go.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				eegEvent ev = new eegEvent(this, (String) elec.getSelectedItem(), (String) analysis.getSelectedValue());
				if (formListener != null) formListener.formEventOccurred(ev);
			}

		});

		layoutComponents();

	}

	public void layoutComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.NONE;		// take up all space or not


		// ------------------------ FIRST ROW ------------------------------ //
		gc.gridy = 0;

		gc.weightx = 1;			// controls how much space one cell takes up relative to others
		gc.weighty = 0.2;
		gc.weightx = 0;
		gc.anchor = GridBagConstraints.LINE_END;		// LINE_END means end of line (right justify)
		gc.insets = new Insets(0, 0, 0, 5);				// top, left, bottom, right
		add(new JLabel("Electrodes"), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;		// LINE_START means start of line (left justify)
		gc.insets = new Insets(0, 0, 0, 0);
		add(elec, gc);

		// ------------------------ NEXT ROW ------------------------------ //
		gc.gridy++;				// increment gridy --> forces to go row by row but
		//						don't have to set every time

		gc.weighty = 0.2;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Analyses"), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(analysis, gc);

		// ------------------------ NEXT ROW ------------------------------ //

		//submit box
		gc.gridy++;

		gc.weighty = 2.0;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(go, gc);
	}

	public void setFormListener(eegListener listener){
		this.formListener = listener;
	}
}
