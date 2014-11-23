package BT_2;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class QuanLySinhVien {

	protected Shell shlQuanLySinh;
	private Text txt_maSinhVien;
	private Text txt_tenSinhVien;
	private Text txt_ngaySinh;
	private Table tbl_sinhVien;
	
	Button btnThm;
	Button btn_luu;
	Button btn_xoa;
	Button btn_File;
	Button btn_csdl;
	Button btn_thoat;
	Button btn_moFile;
	Button btn_luuFile;
	Button btn_huy;
	Button btn_huyThem;
	Button btn_sua;

	TableItem item;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			QuanLySinhVien window = new QuanLySinhVien();
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
		shlQuanLySinh.open();
		shlQuanLySinh.layout();
		while (!shlQuanLySinh.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlQuanLySinh = new Shell();
		shlQuanLySinh.setSize(528, 417);
		shlQuanLySinh.setText("Qu\u1EA3n L\u00FD Sinh Vi\u00EAn");
		shlQuanLySinh.setLayout(null);
		
		Group grpThngTinSinh = new Group(shlQuanLySinh, SWT.NONE);
		grpThngTinSinh.setBounds(11, 10, 491, 182);
		grpThngTinSinh.setText("Th\u00F4ng tin sinh vi\u00EAn");
		
		Label lblNewLabel = new Label(grpThngTinSinh, SWT.NONE);
		lblNewLabel.setBounds(39, 32, 83, 15);
		lblNewLabel.setText("M\u00E3 sinh vi\u00EAn");
		
		Label lblTnSinhVin = new Label(grpThngTinSinh, SWT.NONE);
		lblTnSinhVin.setText("T\u00EAn sinh vi\u00EAn");
		lblTnSinhVin.setBounds(39, 69, 84, 15);
		
		Label lblNgySinh = new Label(grpThngTinSinh, SWT.NONE);
		lblNgySinh.setText("Ng\u00E0y sinh");
		lblNgySinh.setBounds(39, 107, 84, 15);
		
		txt_maSinhVien = new Text(grpThngTinSinh, SWT.BORDER);
		txt_maSinhVien.setBounds(139, 29, 264, 21);
		
		txt_tenSinhVien = new Text(grpThngTinSinh, SWT.BORDER);
		txt_tenSinhVien.setBounds(139, 66, 264, 21);
		
		txt_ngaySinh = new Text(grpThngTinSinh, SWT.BORDER);
		txt_ngaySinh.setBounds(139, 104, 255, 21);
		
		btnThm = new Button(grpThngTinSinh, SWT.NONE);
		btnThm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				clickThem();
			}
		});
		btnThm.setBounds(86, 147, 75, 25);
		btnThm.setText("Th\u00EAm");
		
		btn_luu = new Button(grpThngTinSinh, SWT.NONE);
		btn_luu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txt_maSinhVien.getText().isEmpty()&& txt_ngaySinh.getText().isEmpty()&& txt_tenSinhVien.getText().isEmpty()) {
					MessageBox messageBox = new MessageBox(shlQuanLySinh, SWT.ERROR| SWT.OK);
					messageBox.setMessage("Mã sinh viên,tên sinh viên,ngày sinh không được bỏ trống!!!\nVui lòng điền đầy đủ thông tin");
					messageBox.setText("Error");
					messageBox.open();
					return;
				}
				if(!testTrungMaSinhVien()) return;
				if(!testNgaySinh(txt_ngaySinh.getText())) return;
				item = new TableItem(tbl_sinhVien, SWT.NONE);
				item.setText(new String[] {""+txt_maSinhVien.getText(),""+txt_tenSinhVien.getText(),""+txt_ngaySinh.getText()});
				initButton();
			}
		});
		btn_luu.setBounds(165, 147, 75, 25);
		btn_luu.setText("L\u01B0u");
		
		btn_sua = new Button(grpThngTinSinh, SWT.NONE);
		btn_sua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnThm.setVisible(false);
				btn_huyThem.setVisible(true);
				
				TableItem[] items = tbl_sinhVien.getSelection();
				if(items.length==0) {
					MessageBox messageBox = new MessageBox(shlQuanLySinh, SWT.ERROR| SWT.OK);
					messageBox.setMessage("Chọn một sinh viên trong danh dách sinh viên");
					messageBox.setText("Error");
					messageBox.open();
					return;
				}
				
				if(!testNgaySinh(txt_ngaySinh.getText())) return;
				items[0].setText(new String[] {""+txt_maSinhVien.getText(),""+txt_tenSinhVien.getText(),""+txt_ngaySinh.getText()});
				
			}
		});
		btn_sua.setBounds(247, 147, 75, 25);
		btn_sua.setText("S\u1EEDa");
		
		btn_xoa = new Button(grpThngTinSinh, SWT.NONE);
		btn_xoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				TableItem[] items = tbl_sinhVien.getSelection();
				if(items.length==0) {
					MessageBox messageBox = new MessageBox(shlQuanLySinh, SWT.ERROR| SWT.OK);
					messageBox.setMessage("Chọn một sinh viên trong danh dách sinh viên");
					messageBox.setText("Error");
					messageBox.open();
					return;
				}
				MessageBox messageBox = new MessageBox(shlQuanLySinh, SWT.ICON_QUESTION| SWT.OK|SWT.CANCEL);
				messageBox.setMessage("Có chắc muốn xóa sinh viên "+txt_tenSinhVien.getText()+" không?");
				messageBox.setText("Question");
				if(messageBox.open()==SWT.CANCEL) return;
				int indexRemo=tbl_sinhVien.getSelectionIndex();
				tbl_sinhVien.remove(indexRemo);
				
				txt_maSinhVien.setText("");
				txt_ngaySinh.setText("");
				txt_tenSinhVien.setText("");
			}
		});
		btn_xoa.setBounds(328, 147, 75, 25);
		btn_xoa.setText("X\u00F3a");
		
		Label lblmmddyy = new Label(grpThngTinSinh, SWT.NONE);
		lblmmddyy.setBounds(400, 107, 82, 15);
		lblmmddyy.setText("(MM/dd/yyyy)");
		
		btn_huyThem = new Button(grpThngTinSinh, SWT.NONE);
		btn_huyThem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initButton();
				txt_maSinhVien.setText("");
				txt_ngaySinh.setText("");
				txt_tenSinhVien.setText("");
			}
		});
		btn_huyThem.setBounds(86, 147, 75, 25);
		btn_huyThem.setText("Hủy");
		
		Group grpAnhSchSinh = new Group(shlQuanLySinh, SWT.NONE);
		grpAnhSchSinh.setText("Danh s\u00E1ch sinh vi\u00EAn");
		grpAnhSchSinh.setBounds(11, 196, 493, 129);
		
		tbl_sinhVien = new Table(grpAnhSchSinh, SWT.BORDER | SWT.FULL_SELECTION);
		tbl_sinhVien.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items=tbl_sinhVien.getSelection();
				txt_maSinhVien.setText(items[0].getText(0));
				txt_tenSinhVien.setText(items[0].getText(1));
				txt_ngaySinh.setText(items[0].getText(2));
			}
		});
		tbl_sinhVien.setBounds(10, 23, 473, 96);
		tbl_sinhVien.setHeaderVisible(true);
		tbl_sinhVien.setLinesVisible(true);
		
		
		TableColumn tblCl_maSinhVien = new TableColumn(tbl_sinhVien, SWT.NONE);
		tblCl_maSinhVien.setWidth(156);
		tblCl_maSinhVien.setText("M\u00E3 sinh vi\u00EAn");
		
		TableColumn tblCl_tenSinhVien = new TableColumn(tbl_sinhVien, SWT.NONE);
		tblCl_tenSinhVien.setWidth(156);
		tblCl_tenSinhVien.setText("T\u00EAn sinh vi\u00EAn");
		
		TableColumn tblCl_ngaySinh = new TableColumn(tbl_sinhVien, SWT.NONE);
		tblCl_ngaySinh.setWidth(156);
		tblCl_ngaySinh.setText("Ng\u00E0y sinh");

		
		btn_File = new Button(shlQuanLySinh, SWT.NONE);
		btn_File.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clickFile();
			}
		});
		btn_File.setBounds(21, 341, 75, 25);
		btn_File.setText("File");
		
		btn_csdl = new Button(shlQuanLySinh, SWT.NONE);
		btn_csdl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btn_csdl.setBounds(108, 341, 75, 25);
		btn_csdl.setText("CSDL");
		
		btn_thoat = new Button(shlQuanLySinh, SWT.NONE);
		btn_thoat.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox =new MessageBox(shlQuanLySinh, SWT.ICON_QUESTION|SWT.OK|SWT.CANCEL);
				messageBox.setMessage("Do you want exit");
				messageBox.setText("Question");
				if(messageBox.open()==SWT.OK) System.exit(0);
			}
		});
		btn_thoat.setBounds(415, 341, 75, 25);
		btn_thoat.setText("Tho\u00E1t");
		
		btn_moFile = new Button(shlQuanLySinh, SWT.NONE);
		btn_moFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shlQuanLySinh, SWT.OPEN);
				fileDialog.setText("Open");
				String[] filter={"*.txt"};
				fileDialog.setFilterExtensions(filter);
				String selectString=fileDialog.open();
				System.out.println(selectString);
				
				
			}
		});
		btn_moFile.setBounds(21, 341, 75, 25);
		btn_moFile.setText("M\u1EDF file");
		
		btn_luuFile = new Button(shlQuanLySinh, SWT.NONE);
		btn_luuFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shlQuanLySinh, SWT.SAVE);
				fileDialog.setText("Save");
				String[] filter={"*.txt"};
				fileDialog.setFilterExtensions(filter);
				String selectString=fileDialog.open();
				System.out.println(selectString);
				
				
			}
		});
		btn_luuFile.setBounds(102, 341, 75, 25);
		btn_luuFile.setText("L\u01B0u file");
		
		btn_huy = new Button(shlQuanLySinh, SWT.NONE);
		btn_huy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_moFile.setVisible(false);
				btn_huy.setVisible(false);
				btn_luuFile.setVisible(false);
				
				btn_File.setVisible(true);
				btn_csdl.setVisible(true);
			}
		});
		btn_huy.setBounds(199, 341, 75, 25);
		btn_huy.setText("H\u1EE7y");

		initButton();
	}
	private void clickFile(){
		btn_moFile.setVisible(true);
		btn_huy.setVisible(true);
		btn_luuFile.setVisible(true);
		
		btn_File.setVisible(false);
		btn_csdl.setVisible(false);
	}
	private void initButton(){
		btn_moFile.setVisible(false);
		btn_huy.setVisible(false);
		btn_luuFile.setVisible(false);
		btn_huyThem.setVisible(false);
		btn_luu.setEnabled(false);
		btnThm.setVisible(true);
		btn_xoa.setEnabled(true);
		btn_sua.setEnabled(true);
	}
	private void clickThem(){
		btnThm.setVisible(false);
		btn_huyThem.setVisible(true);
		btn_luu.setEnabled(true);
		btn_xoa.setEnabled(false);
		btn_sua.setEnabled(false);
		
	}
	private boolean testNgaySinh(String ngaySinh){
		try{
		int date=Integer.parseInt(ngaySinh.substring(3, 5));
		int month=Integer.parseInt(ngaySinh.substring(0, 2));
		if(1<=date&&date<=31&&1<=month&&month<=12)return true;
		else return false;
		}catch(Exception e){
			MessageBox messageBox = new MessageBox(shlQuanLySinh, SWT.ERROR| SWT.OK);
			messageBox.setMessage("Ngày sinh không đúng định dạng!!!\nVí dụ: 12/11/2011");
			messageBox.setText("Error");
			messageBox.open();
			return false;
		}
	}
	private boolean testTrungMaSinhVien(){
		TableItem[] items=tbl_sinhVien.getItems();
		for (TableItem tableItem : items) {
			if(tableItem.getText(0).equals(txt_maSinhVien.getText())){
				MessageBox messageBox = new MessageBox(shlQuanLySinh, SWT.ERROR| SWT.OK);
				messageBox.setMessage("Mã sinh viên "+tableItem.getText(0)+" đã tồn tại\nVui lòng nhập mã sinh viên khác");
				messageBox.setText("Error");
				messageBox.open();
				return false;
			}
		}
		return true;
	}
}
