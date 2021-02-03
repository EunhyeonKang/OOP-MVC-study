package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.OrderDto;
import singleton.Singleton;

public class HistoryView extends JFrame implements ActionListener {

	Singleton s;
	private JLabel title;
	private String columnNames[] = {
			"Espresso Beverages", "주문날짜", "사이즈" ,"잔", "총액"
	};
	
	private Object rowData[][];
	private DefaultTableModel model;	// 테이블의 넓이, 폭 등을 설정하기 위해 필요함
	private JTable jtable;
	private JScrollPane jscrPane;
	private ArrayList<OrderDto> list;	// 모든 주문 내역을 담을 리스트  
	private JButton back;
	
	public HistoryView() {
		super("Order History");
		
		JPanel frame = new JPanel();
		frame.setBounds(0, 0, 640, 100);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setLayout(null);
		add(frame);
		
		/*title = new JLabel("주 문 내 역");
		title.setBounds(0, 10, 640, 30);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font(null, Font.BOLD, 30));
		frame.add(title);
		*/
		s = Singleton.getInstance();
		this.list = s.ordCtrl.getAllOrder(s.loginId);
		rowData = new Object[list.size()][5];
		// list에서 테이블로 데이터를 삽입하기 위한 처리
		for (int i = 0; i < list.size(); i++) {
			OrderDto dto = list.get(i);
			rowData[i][0] = dto.getMenuName(); 	// 메뉴 이름
			rowData[i][1] = dto.getoDate();		// 주문날짜
			rowData[i][2] = dto.getCupSize();	// 사이즈 
			rowData[i][3] = dto.getCups(); 		// 잔수
			rowData[i][4] = dto.getTotalPrice();// 총액
		}
		
		// Table 관련 
		// 1. 테이블 폭을 설정하기 위한 Model
		model = new DefaultTableModel(columnNames, 0) {	// (폭,높이)
			@Override
			public boolean isCellEditable(int row, int column) {  //수정, 입력 불가
				return false;
			}
		};
		
		model.setDataVector(rowData, columnNames); // (실제데이터:2차원배열, 범주)
		
		// Jtable
		jtable = new JTable(model);

		
		// column의 폭을 설정
		jtable.getColumnModel().getColumn(0).setMaxWidth(200);// 메뉴 이름
		jtable.getColumnModel().getColumn(1).setMaxWidth(150);// 주문날짜
		jtable.getColumnModel().getColumn(2).setMaxWidth(80);// 사이즈
		jtable.getColumnModel().getColumn(3).setMaxWidth(80);// 잔수
		jtable.getColumnModel().getColumn(4).setMaxWidth(100);// 총액
		
		
		// 테이블 크기와 위치 설정
		jscrPane = new JScrollPane(jtable);
		jscrPane.setBounds(10, 50, 600, 80);
		frame.add(jscrPane);
		
		// 뒤로가기 버튼
		back = new JButton("뒤로가기");
		back.setBounds(265, 310, 100, 40);
		back.addActionListener(this);
		frame.add(back);
		
		
		
		setVisible(true);
		setBounds(350, 200, 640, 100);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn == back) {
			Singleton s = Singleton.getInstance();
			s.ordCtrl.orderView();
			dispose();
		}
	}

}
