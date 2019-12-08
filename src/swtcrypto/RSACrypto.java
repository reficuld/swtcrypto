package swtcrypto;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class RSACrypto {

	protected Shell shell;
	private Text txtP;
	private Text txtQ;
	private Text txtPublic;
	private Text txtData;
	private int privKey, nn;
	private boolean flag = true;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RSACrypto window = new RSACrypto();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		shell.setSize(628, 302);
		
		Label lblP = new Label(shell, SWT.NONE);
		lblP.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblP.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		lblP.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblP.setAlignment(SWT.RIGHT);
		lblP.setBounds(71, 76, 55, 15);
		lblP.setText("P:");
		
		Label lblQ = new Label(shell, SWT.NONE);
		lblQ.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblQ.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		lblQ.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblQ.setAlignment(SWT.RIGHT);
		lblQ.setBounds(71, 109, 55, 15);
		lblQ.setText("Q:");
		
		Label lblPublicKey = new Label(shell, SWT.NONE);
		lblPublicKey.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblPublicKey.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		lblPublicKey.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblPublicKey.setAlignment(SWT.RIGHT);
		lblPublicKey.setBounds(0, 147, 126, 15);
		lblPublicKey.setText("Public key:");
		
		Label lblRsa = new Label(shell, SWT.NONE);
		lblRsa.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblRsa.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblRsa.setAlignment(SWT.CENTER);
		lblRsa.setFont(SWTResourceManager.getFont("Courier New", 30, SWT.BOLD));
		lblRsa.setBounds(265, 10, 109, 38);
		lblRsa.setText("RSA");
		
		Label lblData = new Label(shell, SWT.NONE);
		lblData.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblData.setText("Data:");
		lblData.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblData.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		lblData.setAlignment(SWT.RIGHT);
		lblData.setBounds(341, 76, 67, 15);
		
		txtP = new Text(shell, SWT.BORDER);
		txtP.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		txtP.setBounds(144, 73, 140, 21);
		
		txtQ = new Text(shell, SWT.BORDER);
		txtQ.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		txtQ.setBounds(144, 109, 140, 21);
		
		txtPublic = new Text(shell, SWT.BORDER);
		txtPublic.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		txtPublic.setBounds(144, 144, 140, 21);
		
		txtData = new Text(shell, SWT.BORDER);
		txtData.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				flag = true;
			}
		});
		txtData.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		txtData.setBounds(432, 73, 138, 21);
		
		Button btnCrypt = new Button(shell, SWT.NONE);
		btnCrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RSAAlgorithm rsa = new RSAAlgorithm(Integer.parseInt(txtP.getText()),
						Integer.parseInt(txtQ.getText()), Integer.parseInt(txtPublic.getText()));
				
				MessageBox msg = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
				
				int crypted = rsa.Crypt(Integer.parseInt(txtData.getText()));
				msg.setText("Crypting is complete!");
				msg.setMessage("Input data: " + Integer.parseInt(txtData.getText())
				+"\nPublic key: "+ Integer.parseInt(txtPublic.getText())
				+"\nCrypted data: "+ Integer.toString(crypted)
				+"\nDo you want to save data on file?");
				int action = msg.open();
				
				if(action == SWT.YES) {
					FileDialog dialog = new FileDialog(shell, SWT.SAVE);
					dialog.open();
					File fl = new File(dialog.getFilterPath() + File.separator + dialog.getFileName());
					try {
						FileWriter flwr = new FileWriter(fl);
						BufferedWriter bw = new BufferedWriter(flwr);
		                bw.write(Integer.toString(crypted));
		                bw.newLine();
		                bw.write(Integer.toString(rsa.privateKey));
		                bw.newLine();
		                bw.write(Integer.toString(rsa.n));
		                bw.newLine();
		                bw.flush();
		                bw.close();
					}
					catch (IOException e1) {
						MessageBox err = new MessageBox(shell, SWT.ABORT | SWT.ICON_ERROR);
						err.setMessage("Error occured while writing to file!" +e1);
		            }
				}
				
					
			}
		});
		btnCrypt.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btnCrypt.setFont(SWTResourceManager.getFont("Courier New", 18, SWT.BOLD));
		btnCrypt.setBounds(144, 196, 140, 38);
		btnCrypt.setText("Crypt");
		
		Button btnDecrypt = new Button(shell, SWT.NONE);
		btnDecrypt.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btnDecrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(flag==false)
				{
					RSAAlgorithm rs = new RSAAlgorithm();
					int dec = rs.Decrypt(Integer.parseInt(txtData.getText()), privKey, nn);
					
					MessageBox msg2 = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
					msg2.setText("Decrypting is complete!");
					msg2.setMessage("Input data: " + Integer.parseInt(txtData.getText())
					+"\nPrivate key: "+ privKey
					+"\nDecrypted data: "+ Integer.toString(dec));
					msg2.open();
				}
				else {
				RSAAlgorithm rsa = new RSAAlgorithm(Integer.parseInt(txtP.getText()),
						Integer.parseInt(txtQ.getText()), Integer.parseInt(txtPublic.getText()));
						
				MessageBox msg = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
				int decrypted = rsa.Decrypt(Integer.parseInt(txtData.getText()));
				msg.setText("Decrypting is complete!");
				msg.setMessage("Input data: " + Integer.parseInt(txtData.getText())
				+"\nPrivate key: "+ rsa.privateKey
				+"\nDecrypted data: "+ Integer.toString(decrypted));
				msg.open();
				}
			}
		});
		btnDecrypt.setFont(SWTResourceManager.getFont("Courier New", 18, SWT.BOLD));
		btnDecrypt.setText("Decrypt");
		btnDecrypt.setBounds(432, 196, 138, 38);
		
		Button btnLoadFile = new Button(shell, SWT.NONE);
		btnLoadFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.open();
				File fl = new File(dialog.getFilterPath() + File.separator + dialog.getFileName());
				try {
					FileReader flr = new FileReader(fl);
					BufferedReader br = new BufferedReader(flr);
					txtData.setText(br.readLine());
					privKey = Integer.parseInt(br.readLine());
					nn = Integer.parseInt(br.readLine());
					flag = false;
					br.close();
					
				}
				catch (IOException e1) {
					MessageBox err = new MessageBox(shell, SWT.ABORT | SWT.ICON_ERROR);
					err.setMessage("Error occured while reading from file!" +e1);
	            }
			}
		});
		btnLoadFile.setText("Load File");
		btnLoadFile.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btnLoadFile.setFont(SWTResourceManager.getFont("Courier New", 18, SWT.BOLD));
		btnLoadFile.setBounds(432, 124, 138, 38);

	}
}
