package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import Services.GrupaService;
import Services.SubgrupaService;
import entity.Grupa;
import entity.Subgrupa;

public class StudentCtrlPanel extends JPanel {
	private JTextField txtNumeStudent;
	private JComboBox<String> cbGrupa;
	private JComboBox<String> cbSubgrupa;
	List<Grupa> allFromGrupa;
	List<Subgrupa> allFromSubgrupa;

	/**
	 * Create the panel.
	 */
	public StudentCtrlPanel() {
		setLayout(null);
		
		setSize(734, 119);
		
		JPanel panel = new JPanel();
		panel.setBounds(64, 49, 605, 20);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNumeStudent = new JLabel("Nume student:");
		lblNumeStudent.setBounds(0, 3, 71, 14);
		panel.add(lblNumeStudent);
		
		txtNumeStudent = new JTextField();
		txtNumeStudent.setBounds(81, 0, 192, 20);
		panel.add(txtNumeStudent);
		txtNumeStudent.setColumns(10);
		JLabel lblGrupa = new JLabel("Grupa:");
		lblGrupa.setBounds(283, 3, 33, 14);
		panel.add(lblGrupa);
		
		
		cbGrupa = new JComboBox<String>();
		cbGrupa.setBounds(326, 0, 102, 20);
		panel.add(cbGrupa);
		
		cbGrupa.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				//JComboBox<String> cb = (JComboBox)e.getSource();
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cbGrupa.getSelectedIndex() == -1 || cbGrupa.getSelectedIndex() == 0) {
						cbSubgrupa.removeAllItems();
						
						cbSubgrupa.addItem("");
						for (Object subgrupa : allFromSubgrupa.toArray())
							cbSubgrupa.addItem(subgrupa.toString());
						
						cbSubgrupa.setSelectedIndex(-1);
						return;
					}
					cbSubgrupa.removeAllItems();
					List<Subgrupa> list = SubgrupaService.getAllSubGrupeByGrupa(GrupaService.getGrupaByNume((String)e.getItem()));
					cbSubgrupa.addItem("");
					for (Subgrupa sg : list) {
						cbSubgrupa.addItem(sg.getNume());
					}
					cbSubgrupa.setSelectedIndex(-1);
				}
				
			}
			
		});
		
		JLabel lblSubgrupa = new JLabel("Subgrupa:");
		lblSubgrupa.setBounds(438, 3, 55, 14);
		panel.add(lblSubgrupa);
		
		cbSubgrupa = new JComboBox<String>();
		cbSubgrupa.setBounds(503, 0, 102, 20);
		panel.add(cbSubgrupa);
		allFromGrupa = GrupaService.getAllFromGrupa();
		allFromSubgrupa = SubgrupaService.getAllFromSubgrupa();
		
		loadCombos();

	}
	
	public void loadCombos() {
		//loadAllFromGrupa();
		//init combo boxes with info from db.
		//all of them.
		cbGrupa.addItem("");
		cbSubgrupa.addItem("");
		
		for (Object grupa : allFromGrupa.toArray())
			cbGrupa.addItem(grupa.toString());
		
		for (Object subgrupa : allFromSubgrupa.toArray())
			cbSubgrupa.addItem(subgrupa.toString());
		
	}
	
	public void setFields(String numeStudent, String grupa, String subgrupa) {
		txtNumeStudent.setText(numeStudent);
		
		cbGrupa.setSelectedItem(grupa);
		cbSubgrupa.setSelectedItem(subgrupa);
		
	}
	
	public String getNumeStudent() {
		return txtNumeStudent.getText();
	}
	
	public String getSelectedGrupa() {
		return (String)cbGrupa.getSelectedItem();
	}
	
	public String getSelectedSubgrupa() {
		return (String)cbSubgrupa.getSelectedItem();
	}

	public Object[] getFields(boolean allowNull) {
		if(!allowNull) {
			if (txtNumeStudent.getText().length() == 0 || cbGrupa.getSelectedIndex() == -1 || cbSubgrupa.getSelectedIndex() == -1)
				return null;
		}
		
		Object result[] = { txtNumeStudent.getText(), cbGrupa.getSelectedItem(), cbSubgrupa.getSelectedItem() };
		
		return result;
	}
	
	public void resetFields() {
		txtNumeStudent.setText("");
		cbGrupa.setSelectedIndex(-1);
		cbSubgrupa.setSelectedIndex(-1);
	}
}
