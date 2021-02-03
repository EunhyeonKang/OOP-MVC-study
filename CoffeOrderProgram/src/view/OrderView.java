package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dto.MenuDto;
import dto.OrderDto;
import singleton.Singleton;

public class OrderView extends JFrame implements ActionListener {
	
	JComboBox<String> menuChoice;
	
	JPanel option[];
	JLabel opLabel[];
	JRadioButton opButton[][];
	JRadioButton shortS, tall, grande;
	JRadioButton vanila, caramel, hazelnut;
	
	ButtonGroup groupRd[];
	JCheckBox addOption[];

	JLabel cupsLab;
	JTextField cupTxt;
	
	JButton showMenu, orderMenu;
	JButton showBasket,ordHistory;
	boolean openMenu=false;
	
	Singleton s;
	
	
	public OrderView() {
		super("Order");
		
		s = Singleton.getInstance();
		
		// 메인 프레임
		JPanel frame = new JPanel();
		frame.setBounds(0, 0, 660, 400);
		frame.setBackground(Color.GRAY);
		frame.setLayout(null);
		add(frame);
		
		JLabel title = new JLabel("COFFEE ORDER");
		title.setBounds(0, 10, 660, 30);
		title.setFont(new Font(null, Font.BOLD, 20));
		title.setHorizontalAlignment(JLabel.CENTER);
		frame.add(title);
		
		
		
		
		// 메뉴보기 버튼 
		showMenu = new JButton("메뉴보기");
		showMenu.setBounds(510, 50, 100, 20);
		showMenu.addActionListener(this);
		frame.add(showMenu);
		
		// 메뉴 콤보박스
		int menuNum = s.ordCtrl.getMenu().size();
		String menustr[]= new String[menuNum+1];
		ArrayList<MenuDto> list = s.ordCtrl.getMenu();
		menustr[0] = "*메뉴를 선택하세요*";
		for (int i = 1; i < menustr.length; i++) {
			menustr[i] = list.get(i-1).getName();
		}
		
		menuChoice = new JComboBox<String>(menustr);
		menuChoice.setBounds(30, 50,460, 20);
		frame.add(menuChoice);
		
		
		
		
		// 옵션 패널
		option = new JPanel[3];
		opLabel = new JLabel[3];
		
		String opStr[] = {" 사 이 즈", " 시 럽", " 기 타"};
		for (int i = 0; i < option.length; i++) {
			option[i] = new JPanel();
			option[i].setBounds(30+(i*200),100, 180, 200);
			option[i].setBackground(Color.white);
			option[i].setLayout(new GridLayout(5, 0 ));
			frame.add(option[i]);
			
			opLabel[i] = new JLabel(opStr[i]);
			opLabel[i].setFont(new Font(null, Font.BOLD, 20));
			option[i].add(opLabel[i]);
		}
		
		String opbtnStr[][] = {
				{"short", "Tall", "Grande"},
				{"바닐라", "카라멜", "헤이즐넛", "없음"}
		};
		addOption = new JCheckBox[2];
		addOption[0] = new JCheckBox("샷 추가");
		addOption[0].setBackground(Color.white);
		addOption[1] = new JCheckBox("휘핑크림");
		addOption[1].setBackground(Color.white);
		
		groupRd = new ButtonGroup[2];
		opButton = new JRadioButton[2][3];
		// 라디오 버튼 추가하기
		for (int i = 0; i < 2; i++) {
			groupRd[i] = new ButtonGroup();
			if(i==1) opButton[i] = new JRadioButton[4];
			for (int j = 0; j < opButton[i].length; j++) {
				opButton[i][j] = new JRadioButton(opbtnStr[i][j]);
				opButton[i][j].setSelected(true);
				option[i].add(opButton[i][j]);
				opButton[i][j].setBackground(Color.white);
				groupRd[i].add(opButton[i][j]);
			}
			
		}
		
		
		// 기타 추가사항 (체크박스)
		option[2].add(addOption[0]);
		option[2].add(addOption[1]);
		
		
		
		// 잔 수 
		cupTxt = new JTextField("1");
		cupTxt.setHorizontalAlignment(JTextField.CENTER);
		cupTxt.setEditable(false);
		cupTxt.setBounds(350, 320, 35, 30);
		frame.add(cupTxt);
		cupsLab = new JLabel("잔");
		cupsLab.setBounds(390, 320, 30, 30);
		frame.add(cupsLab);
		
		// + 
		JButton plus = new JButton("+");
		plus.setBounds(250, 320, 40, 30);
		plus.setFont(new Font("null", Font.BOLD,10));		
		frame.add(plus);
		plus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a = Integer.parseInt(cupTxt.getText());
				a++;
				cupTxt.setText(a+"");
			}
		});
		// - 
		JButton minus = new JButton("-");
		minus.setBounds(300, 320, 40, 30);
		minus.setFont(new Font("null", Font.BOLD,12));		
		frame.add(minus);
		minus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			if(!cupTxt.getText().equals("0")) {
					int a = Integer.parseInt(cupTxt.getText());
					a--;
					cupTxt.setText(a+"");
			}}}
		);
		
		
		
		showBasket = new JButton("장바구니");
		showBasket.setBounds(430, 320, 100, 30);
		showBasket.addActionListener(this);
		frame.add(showBasket);
		
		
		orderMenu = new JButton("추가");
		orderMenu.setBounds(540, 320, 70, 30);
		orderMenu.addActionListener(this);
		frame.add(orderMenu);
		
		// 로그아웃
		JButton back = new JButton("뒤로 가기");
		back.setBounds(30, 320, 120, 30);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton nowbtn = (JButton)e.getSource();
				if(nowbtn == back) {
					dispose();
					s.memCtrl.firstView();
				}
			}
			
		});
		frame.add(back);
		
		
		setVisible(true);
		setBounds(200, 200, 650, 420);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
		
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton)e.getSource();
		if( btn == showMenu) {
			if(openMenu) { // 창 닫기
				s.pv.dispose();
				openMenu = false;
				showMenu.setText("메뉴보기");
			}
			else {	// 메뉴 보기 
				openMenu = true;
				s.priceView(openMenu);
				showMenu.setText("창닫기");
				
			}
		}
		// 장바구니에 추가하기
		if( btn == orderMenu) {
			if(menuChoice.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "메뉴를 선택해주세요!");
				return;
			}
			int menuNum = menuChoice.getSelectedIndex();
			String menuName = (String)menuChoice.getSelectedItem();
			String id = s.loginId;
			String cupSize = "";
			String syrup = "";
			int shot=0;
			int whip=0;
			int cups = Integer.parseInt(cupTxt.getText());
			int price = s.ordCtrl.getMenu().get(menuNum-1).getPrice();
			System.out.println("cupTxt.getText():"+cupTxt.getText());
			System.out.println("cups: "+cups);
			for (int i = 0; i < opButton[0].length; i++) {
				if(opButton[0][i].isSelected()) {
					cupSize = opButton[0][i].getText();
					if(cupSize.equals("Tall")) {
			    		price += 500;
			    	}
			    	else if(cupSize.equals("Grande")) {
			    		price += 1000;
			    	}
				}
			}
			
			for (int i = 0; i < opButton[1].length; i++) {
				if(opButton[1][i].isSelected()) {
					syrup = opButton[1][i].getText();
				}
			}
			
			// 샷추가 여부 
			if(addOption[0].isSelected()) {
				shot++;
			}
			// 휘핑크림 추가?
			if(addOption[1].isSelected()) {
				whip++;
			}
			int total = price * cups;
			
			
			String str = " 주문한 메뉴 : " + menuChoice.getSelectedItem() + "\n"
					+" 사이즈 : " + cupSize + "\n";
			str += " 시럽 : " + syrup+"\n"
					+ " 추가 샷 : " + shot + "\n" + " 휘핑크림 추가 : " + whip + "\n"+
					 " 총 : " + cups + " 잔\n"
					+ " Total : " + total;
		int result = JOptionPane.showConfirmDialog(null, str);
		if(result == JOptionPane.YES_NO_OPTION) {
			OrderDto dto = new OrderDto();
			dto.setSequence(s.bucketList.size()+1);
			dto.setId(id);
			dto.setMenuName(menuName);
			dto.setCupSize(cupSize);
			dto.setSyrup(syrup);
			dto.setShot(shot);
			dto.setWhip(whip);
			dto.setCups(cups);
			dto.setTotalPrice(total);
			dto.setoDate("");
			
			boolean b = s.ordCtrl.addBucket(dto);
			if(b) JOptionPane.showMessageDialog(null, "메뉴 추가가 완료되었습니다!");
			else JOptionPane.showMessageDialog(null, "실패!");
			// 초기화부분 
			menuChoice.setSelectedIndex(0);
			addOption[0].setSelected(false);			
			addOption[1].setSelected(false);			
			cupTxt.setText("1");
			opButton[0][0].setSelected(true);
			opButton[1][0].setSelected(true);
			s.priceView(false);
			}
		}
	
		if(btn == showBasket) {
			// 장바구니 보기
			if(openMenu) s.pv.dispose();
			
			s.ordCtrl.bucketView();
			dispose();
		}

}}
