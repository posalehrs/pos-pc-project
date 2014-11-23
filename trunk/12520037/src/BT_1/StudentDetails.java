package BT_1;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class StudentDetails {

	protected Shell shlStudentDetails;
	private Text txt_name;
	private Text txt_age;
	private Text txt_address;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StudentDetails window = new StudentDetails();
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
		shlStudentDetails.open();
		shlStudentDetails.layout();
		while (!shlStudentDetails.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlStudentDetails = new Shell();
		shlStudentDetails.setSize(380, 557);
		shlStudentDetails.setText("Student Details");
		
		Label label_1 = new Label(shlStudentDetails, SWT.NONE);
		label_1.setText("Name");
		label_1.setBounds(24, 75, 55, 15);
		
		txt_name = new Text(shlStudentDetails, SWT.BORDER);
		txt_name.setBounds(118, 69, 164, 21);
		
		txt_age = new Text(shlStudentDetails, SWT.BORDER);
		txt_age.setBounds(118, 106, 164, 21);
		
		Label label_2 = new Label(shlStudentDetails, SWT.NONE);
		label_2.setText("Age");
		label_2.setBounds(24, 112, 55, 15);
		
		Label label_3 = new Label(shlStudentDetails, SWT.NONE);
		label_3.setText("Address");
		label_3.setBounds(24, 149, 55, 15);
		
		txt_address = new Text(shlStudentDetails, SWT.BORDER | SWT.MULTI);
		txt_address.setBounds(118, 143, 206, 21);
		
		Group group = new Group(shlStudentDetails, SWT.NONE);
		group.setText("Gender");
		group.setBounds(24, 189, 258, 51);
		
		final Button rdb_male = new Button(group, SWT.RADIO);
		rdb_male.setText("Male");
		rdb_male.setBounds(10, 20, 90, 16);
		
		final Button rdb_female = new Button(group, SWT.RADIO);
		rdb_female.setText("Female");
		rdb_female.setBounds(161, 20, 90, 16);
		
		Label label_4 = new Label(shlStudentDetails, SWT.NONE);
		label_4.setText("Course");
		label_4.setBounds(39, 256, 55, 15);
		
		Combo cmb_course = new Combo(shlStudentDetails, SWT.NONE);
		cmb_course.setItems(new String[] {"Web Application Developer", "Database Administrator", "Network Administrator", "Windows Application Developer"});
		cmb_course.setBounds(118, 253, 164, 23);
		cmb_course.select(0);
		
		Label label_5 = new Label(shlStudentDetails, SWT.NONE);
		label_5.setText("Time slot");
		label_5.setBounds(39, 295, 73, 15);
		
		Group group_1 = new Group(shlStudentDetails, SWT.NONE);
		group_1.setText("Facilities");
		group_1.setBounds(24, 393, 258, 75);
		
		final Button chb_library = new Button(group_1, SWT.CHECK);
		chb_library.setText("Library");
		chb_library.setBounds(20, 23, 93, 16);
		
		final Button chb_computerDrome = new Button(group_1, SWT.CHECK);
		chb_computerDrome.setText("Computer Drome");
		chb_computerDrome.setBounds(20, 49, 114, 16);
		
		List list_timeSlot = new List(shlStudentDetails, SWT.BORDER);
		list_timeSlot.setItems(new String[] {"7:00 \u2013 9:00", "9:00 \u2013 11:00", "11:00 \u2013 1:00", "1:00 \u2013 3:00", "3:00 \u2013 5:00"});
		list_timeSlot.setBounds(117, 291, 165, 96);
		
		Button btn_ok = new Button(shlStudentDetails, SWT.NONE);
		btn_ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shlStudentDetails,SWT.ERROR | SWT.OK);
				if (txt_address.getText().isEmpty()||txt_age.getText().isEmpty()) {
					messageBox.setMessage("One textbox have empty!");
					messageBox.setText("Error");
					messageBox.open();
				}else if(!rdb_female.getSelection()&&!rdb_male.getSelection()){
					messageBox.setMessage("One checkbox is not check!");
					messageBox.setText("Error");
					messageBox.open();
				}else {
					messageBox = new MessageBox(shlStudentDetails,SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setMessage("Info success!");
					messageBox.setText("Info");
					messageBox.open();
				}
				
			}
		});
		btn_ok.setBounds(92, 486, 75, 25);
		btn_ok.setText("OK");
		
		Button btn_close = new Button(shlStudentDetails, SWT.NONE);
		btn_close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox =new MessageBox(shlStudentDetails, SWT.ICON_QUESTION|SWT.OK|SWT.CANCEL);
				messageBox.setMessage("Do you want exit");
				messageBox.setText("Question");
				if(messageBox.open()==SWT.OK) System.exit(0);
				else return;
			}
		});
		btn_close.setBounds(194, 486, 75, 25);
		btn_close.setText("Close");
		
		Label lblStudentDetails = new Label(shlStudentDetails, SWT.NONE);
		lblStudentDetails.setText("Student Details");
		lblStudentDetails.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.NORMAL));
		lblStudentDetails.setBounds(92, 10, 190, 36);

	}
}
