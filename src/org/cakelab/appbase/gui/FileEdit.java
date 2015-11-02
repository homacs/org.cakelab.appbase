package org.cakelab.appbase.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.cakelab.appbase.log.Log;


public class FileEdit extends JPanel implements ActionListener {
	
	public interface FolderVerifier {

		boolean verify(FileEdit folderEdit);

	}

	private static final long serialVersionUID = 1149303417732884685L;

	private JTextField folder;
	private JButton button;
	final JFileChooser fc = new JFileChooser();

	private String applicationName;

	private int selectionMode;
	private String previousFile;


	public static FileEdit create(String applicationName, Icon folderIcon) {
		FileEdit filefield = new FileEdit(applicationName, folderIcon);
		return filefield;
	}
	
	private FileEdit(String applicationName, Icon folderIcon) {
		this.applicationName = applicationName;
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);

		setLayout(layout);
		
		
		folder = new JTextField();
		
		folder.addActionListener(this);
		
		add(folder);
		button = new JButton();
		try {
			button = GUIUtils.createIconButton(folderIcon);
		} catch (IOException e) {
			button = new JButton();
			button.setText("..");
			button.setFocusable(true);
		}
		button.setToolTipText("Select a folder");
		add(button);

		button.addActionListener(this);
		
		

		layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(folder).addComponent(button));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(folder).addComponent(button));
		
		
	}
	
	
	
	
	@Override
	public void setToolTipText(String text) {
		folder.setToolTipText(text);
		super.setToolTipText(text);
	}

	public void init(String initialFolder, final FolderVerifier verifier, boolean files) {
		folder.setText(initialFolder);
		previousFile = initialFolder;
		if (!files) {
			selectionMode = JFileChooser.DIRECTORIES_ONLY;
		} else {
			selectionMode = JFileChooser.FILES_ONLY;
		}
		InputVerifier inputVerifier = new InputVerifier(){
			@Override
			public boolean verify(JComponent input) {
				try {
					boolean result = true;
					if (!folder.getText().equals(previousFile)) {
						result = verifier.verify(FileEdit.this);
						setInvalid(!result);
						previousFile = folder.getText();
					}
					return result;
				} catch (Throwable t) {
					return false;
				}
			}
		};
		
		this.setInputVerifier(inputVerifier);
		folder.setInputVerifier(inputVerifier);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button)) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					fc.setDialogTitle(applicationName + " - Select a folder");
					fc.setCurrentDirectory(new File(folder.getText()));
					fc.setFileHidingEnabled(false);
					fc.setFileSelectionMode(selectionMode);
					int result = fc.showOpenDialog(FileEdit.this);
					switch(result) {
					case JFileChooser.APPROVE_OPTION:
						setSelectedFile(fc.getSelectedFile().getAbsolutePath());
						folder.getInputVerifier().verify(folder);
						

						break;
					case JFileChooser.ERROR_OPTION:
						Log.warn("Folder selection dialog returned error code.");
					case JFileChooser.CANCEL_OPTION:
						break;
						
					}
				}
				
			});
		} else if (e.getSource().equals(folder)) {
			getInputVerifier().verify(this);
		}
	}

	private void setInvalid(boolean invalid) {
		if (invalid) {
			folder.setForeground(Color.red);
		} else {
			folder.setForeground(Color.black);
		}
	}

	public File getSelectedFile() {
		return new File(folder.getText());
	}

	public void setEditable(boolean b) {
		folder.setEditable(b);
		button.setEnabled(b);
	}

	public void setSelectedFile(String selectedFile) {
		if (selectedFile != null && !selectedFile.equals(folder.getText())) {
			folder.setText(selectedFile);
		}
	}


}
