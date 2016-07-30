package eeggui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


public class TextPanel extends JPanel {
	
	private JTextArea textArea;
	
	public TextPanel(){
		textArea = new JTextArea();
		
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);		
		// without adding anything to N, E, S, W -> text area will take up entire panel
		// making JScrollPane -> scroll bars for if there's more text than in window
		
		Border in = BorderFactory.createRaisedBevelBorder();
		Border out = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(out, in));
	}
	
	public void appendText(String text){
		textArea.append(text);
	}
}
