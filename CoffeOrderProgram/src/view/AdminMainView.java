package view;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dto.MenuDto;
import dto.OrderDto;
import singleton.Singleton;

import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdminMainView extends JFrame {
   
   JFrame frame;
   JTabbedPane tab; 
   
   JMenuBar bar;
   JMenu systemMenu;      
   JMenuItem logoutItem, logoutItem2;
   
   Singleton s;
   MenuPanel a;
   OrderHistoryPanel b;
   public AdminMainView() {
      super("AdminMain");
      s = Singleton.getInstance();
      //메인 프레임

      frame= new JFrame("AdminMain");
      tab = new JTabbedPane();
      
      a= new MenuPanel();
      b=new OrderHistoryPanel();
      
      tab.add("전체 주문내역",b);
      tab.add("월별 매출",new monthTotalPanel());
      tab.add("일별 매출",new dayTotalPanel());
      tab.add("메뉴 조회",a);
      
    /*  tab.addChangeListener(new ChangeListener() {
      
      @Override
      public void stateChanged(ChangeEvent e) {
         // TODO Auto-generated method stub
         if(tab.getSelectedIndex()==0) {         //전체주문내역
            //System.out.println("Index = 0");
         }
         else if(tab.getSelectedIndex()==3) {   //메뉴 조회

         }
      }
   });*/
        
      createMenubar();
      frame.setLocation(300,200);         //창의 위치 결정
      frame.setSize(660,550);
      frame.add(tab);
      frame.setBackground(Color.GRAY);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      
      // 메인 프레임
   }
   private void createMenubar() {
      bar = new JMenuBar();
      systemMenu = new JMenu("System");      
      logoutItem = new JMenuItem("Logout");
      logoutItem2 = new JMenuItem("새로고침");
      systemMenu.add(logoutItem);            //메뉴항목 추가
      systemMenu.add(logoutItem2);         //새로고침 추가
      bar.add(systemMenu);
      frame.setJMenuBar(bar);             //프레임에 메뉴바 설정   
      
      logoutItem.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?","logout",JOptionPane.WARNING_MESSAGE);
            if(result == JOptionPane.YES_OPTION) {
               s.setLoginDto(null);
                s.memCtrl.loginView();
                frame.dispose();
            }         
         }
      });
      
      logoutItem2.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
             if(tab.getSelectedIndex()==3) {
                tab.remove(3);
              a= new MenuPanel();
              tab.add("메뉴 조회",a);
             }     
          }
       });
      
   }
}

class OrderHistoryPanel extends JPanel {
   
   JPanel jp1;
   
   private JTable jtable;
   private JScrollPane jscrPane;    
   private DefaultTableModel model;   // 테이블의 넓이, 폭 등을 설정하기 위해 필요함
   private Object rowData[][];
   private ArrayList<OrderDto> list;
   private String columnNames[] = {"Espresso Beverages", "주문날짜", "사이즈" ,"잔", "총액"};
   Singleton s;    
    
   
   public OrderHistoryPanel() {
      
   //   this.setLayout(new FlowLayout());
      this.setLayout(null);
      jp1=new JPanel();
      jp1.setBounds(0, 0, 660, 500);
      jp1.setBackground(Color.white);
      this.add(jp1);
      
      s = Singleton.getInstance();
      this.list = s.ordCtrl.getAllOrder(s.loginId);
      rowData = new Object[list.size()][5];
      // list에서 테이블로 데이터를 삽입하기 위한 처리
      for (int i = 0; i < list.size(); i++) {
         OrderDto dto = list.get(i);
         rowData[i][0] = dto.getMenuName();    // 메뉴 이름
         rowData[i][1] = dto.getoDate();      // 주문날짜
         rowData[i][2] = dto.getCupSize();   // 사이즈 
         rowData[i][3] = dto.getCups();       // 잔수
         rowData[i][4] = dto.getTotalPrice();// 총액
      }
      
      // Table 관련 
      // 1. 테이블 폭을 설정하기 위한 Model
      model = new DefaultTableModel(columnNames, 0) {   // (폭,높이)
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
        jscrPane.setBounds(10, 50, 660, 500);      
        jp1.add(jscrPane);            //패널에 붙이기
        
   }
}
class monthTotalPanel extends JPanel{

   String year,month;
   String date;
   int result;
   ArrayList<String> yearList;
   ArrayList<String> monthList;
   Singleton s; 
   
   Calendar ci=Calendar.getInstance();
   JLabel jl1= new JLabel();
   JLabel jl2= new JLabel();
   JButton js=new JButton();
   
   private JLabel sumLabel;

   public monthTotalPanel() {
      
      this.setLayout(null);     
      
      JPanel jpm=new JPanel();
      jpm.setLayout(null);
      jpm.setBounds(30, 20, 580, 200);
      jpm.setBackground(Color.white);
      this.add(jpm);
      
      jpm.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY,2)," 월별 매출 "));
      //jpm.setLayout(null);
      jl1=new JLabel("『날짜 선택』");
      jl2=new JLabel("『매출 총 액』 ");
      sumLabel=new JLabel();
      jl1.setFont(new Font("null", Font.BOLD,15));
      jl2.setFont(new Font("null", Font.BOLD,15));
      
      jl1.setBounds(60,50,100,40);
      jpm.add(jl1);
      
      
      
      js=new JButton("매출 조회");
      js.setBounds(350,53,100,30);
     
      JPanel combo=new JPanel();
      
      JComboBox<String> cyear,cmonth,cday;

      int toyear=ci.get(Calendar.YEAR)+1;
      int tomonth=ci.get(Calendar.MONTH)+1;
      //int today=ci.get(Calendar.DAY_OF_MONTH);
   
      // ⵵
      yearList=new ArrayList<String>();
      for(int j=toyear; j>=toyear-10; j--) {
         if(j==toyear) {
            yearList.add("--");
         }else {
            yearList.add(String.valueOf(j));
         }
      }
      cyear= new JComboBox<String>(yearList.toArray(new String[yearList.size()]));
      cyear.setBounds(5, 5, 70, 30);
      cyear.setSelectedItem(String.valueOf(toyear));

     
      
      cyear.addItemListener(new ItemListener() {
          @Override
             public void itemStateChanged(ItemEvent e) {
             
                if(e.getStateChange()==ItemEvent.SELECTED) {
                   year=cyear.getSelectedItem().toString();
                   
                 //  year_2 = Integer.parseInt(year);
                   System.out.println(date);                   
                }
             }
          });

   monthList = new ArrayList<String>();
     
    for(int i = 0; i <= 12; i++){
       if(i==0) {
          monthList.add("--");
        }else {
           monthList.add(addZeroString(i));
        }
       
      //System.out.println(i);
     }  
     cmonth = new JComboBox<String>(monthList.toArray(new String[monthList.size()]));

     cmonth.setBounds(80, 5, 70, 30);
     
     String mcom = tomonth >= 10?String.valueOf(tomonth):"0"+tomonth;
   //  cmonth.setSelectedItem("---");
     
     cmonth.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
           if(e.getStateChange()==ItemEvent.SELECTED) {
              month=cmonth.getSelectedItem().toString();
              
 //             date= date +"-"+month+"%";
              System.out.println(date);
           }
        }
     });

     
      
     combo.add(cyear);
     combo.add(cmonth);
     
    // combo.add(cday); 
     combo.setBackground(Color.white);
     
     jpm.add(combo);
     
     jpm.add(js);
     combo.setBounds(160,50,200,60);
    
     jpm.add(jl2);
     jl2.setBounds(60,130,110,50);
     
     
     js.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            date=year+"-"+month+"%";
            s = Singleton.getInstance();
             result = s.ordCtrl.MonthPrice(date);
             sumLabel.setText(Integer.toString(result)+"원");
             System.out.println(date);
             System.out.println(result);  
                        
         }         
      });    
     sumLabel.setFont(new Font("null", Font.BOLD,15));
     sumLabel.setBounds(200,80,150,150);  
     jpm.add(sumLabel);
  
     


   }
   
   private String addZeroString(int k){
        String value=Integer.toString(k);
        
        if(value.length()==1) {
         value="0"+value;
        }
        else value=value;
        
        return value;
 
   }
}


class dayTotalPanel extends JPanel{
   
   ArrayList<String> yearList;
   ArrayList<String> monthList;
   ArrayList<String> dayList;
   
   Calendar ci=Calendar.getInstance();
   JLabel jl1= new JLabel();
   JLabel jl2= new JLabel();
   JButton js=new JButton();
   private JLabel sumLabel;
   
   Singleton s; 
   String year,month,day,date;
   int result;
   public dayTotalPanel() {
      
      //Container c= getContentPane();
      //c.setLayout(null);
      JPanel jpd=new JPanel();
      sumLabel = new JLabel("");
      
      this.add(jpd);
      this.setLayout(null);
      jpd.setLayout(null);
      jpd.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY,2)," 일별 매출 "));
      
      
      jpd.setBounds(30, 20, 580, 200);
      jpd.setBackground(Color.white);
      
      //jpd.setLayout(null);
      jl1=new JLabel("『날짜 선택』 ");
      jl2=new JLabel("『매출 총 액』");
      
      
      //f1=new Font("돋움",Font.BOLD,16);
      jl1.setFont(new Font("null", Font.BOLD,15));
      jl2.setFont(new Font("null", Font.BOLD,15));
      
      sumLabel.setFont(new Font("null", Font.BOLD,15));


      jpd.add(jl1);
      jl1.setBounds(60,47,100,40);
      jl1.setBackground(Color.LIGHT_GRAY);
      jl2.setBounds(60,130,110,50);
      
      js=new JButton("매출 조회");
      js.setFont(new Font("null", Font.BOLD,15));
      
      js.setBounds(370,53,100,30);
      JPanel combo=new JPanel();
      
      JComboBox<String> cyear,cmonth,cday;
      
      //       ¥
      int toyear=ci.get(Calendar.YEAR)+1;
      int tomonth=ci.get(Calendar.MONTH)+1;
      int today=ci.get(Calendar.DAY_OF_MONTH);
   
      // ⵵
      yearList=new ArrayList<String>();
      for(int j=toyear; j>=toyear-10; j--) {
          if(j==toyear) {
             yearList.add("--");
          }else {
             yearList.add(String.valueOf(j));
          }
       }
      
      cyear= new JComboBox<String>(yearList.toArray(new String[yearList.size()+1]));
      cyear.setBounds(5, 5, 70, 30);
      cyear.setSelectedItem(String.valueOf(toyear));
      cyear.addItemListener(new ItemListener() {
         @Override
         public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange()==ItemEvent.SELECTED) {
               year=cyear.getSelectedItem().toString();
               System.out.println(Integer.parseInt(year));
            }
         }
      });
   
   
      
   
   //  
      monthList = new ArrayList<String>();
     
    for(int i = 0; i <= 12; i++){
       if(i==0) {
          monthList.add("--");
        }else {
           monthList.add(addZeroString(i));
        }
     }  
     cmonth = new JComboBox<String>(monthList.toArray(new String[monthList.size()]));

     cmonth.setBounds(80, 5, 70, 30);
     String mcom = tomonth >= 10?String.valueOf(tomonth):"0"+tomonth;
   //  cmonth.setSelectedItem(mcom);
     
     cmonth.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
           if(e.getStateChange()==ItemEvent.SELECTED) {
              month=cmonth.getSelectedItem().toString();
              System.out.println(Integer.parseInt(month));
           }
        }
     });
     
     //  
     dayList = new ArrayList<String>();
     
     for(int k = 0; k <= 31; k++){
        if(k==0) {
           dayList.add("--");
         }else {
            dayList.add(addZeroString(k));
         }
      //System.out.println(i);
     }  
     cday = new JComboBox<String>(dayList.toArray(new String[dayList.size()]));

     cday.setBounds(160, 5, 70, 30);
     String dcom = today >= 10?String.valueOf(today):"0"+today;
   //  cday.setSelectedItem(dcom);
     
     cday.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
              if(e.getStateChange()==ItemEvent.SELECTED) {
                 day=cday.getSelectedItem().toString();
                 System.out.println(Integer.parseInt(day));
              }
           }
        });
        

        js.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            date=year+"-"+month+"-"+day+"%";
            s = Singleton.getInstance();
             result = s.ordCtrl.MonthPrice(date);
             sumLabel.setText(Integer.toString(result)+"원");
             System.out.println(date);
             System.out.println(result);                          
         }         
      });   
        
     combo.add(cyear);
     combo.add(cmonth);
     combo.add(cday); 
     combo.setBackground(Color.white);
    
     jpd.add(combo);
     jpd.add(js);
     combo.setBounds(160,50,200,60);
     jpd.add(jl2);
     sumLabel.setBounds(200,80,150,150);  
     jpd.add(sumLabel);
    
     //setVisible(true);
     //setBounds(200, 200, 650, 420);
   
   }
   /*public void paint(Graphics g) {
      super.paint(g);
      g.fillRect(50, 50, 300, 300);
      
   }*/
   private String addZeroString(int k){
        String value=Integer.toString(k);
        
        if(value.length()==1) {
         value="0"+value;
        }
        else value=value;
        
        return value;
 
   }

}


class MenuPanel extends JPanel {
   
   JPanel jp1;
   JButton addBtn;
   JButton deleteBtn;

   JTable jtable;
   JScrollPane jscrPane;    
   DefaultTableModel model;   // 테이블의 넓이, 폭 등을 설정하기 위해 필요함
   private Object rowData[][];
   private String columnNames[] = {"Espresso Beverages", "Short", "Tall" , "Grande"};
   
   Singleton s;    
    private ArrayList<MenuDto> list;    
    
   public MenuPanel() {
    
   //   this.setLayout(new FlowLayout());
      this.setLayout(null);
      jp1=new JPanel();
      jp1.setBounds(0, 0, 660, 500);
      jp1.setBackground(Color.white);
      this.add(jp1);
      
      s = Singleton.getInstance();
     
      this.list=s.ordCtrl.getAllMenu(s.loginId);
      rowData = new Object[list.size()][4];
        // list에서 테이블로 데이터를 삽입하기 위한 처리
        for (int i = 0; i < list.size(); i++) {
           MenuDto dto = list.get(i);
           rowData[i][0] = dto.getName();    // 메뉴 이름
           rowData[i][1] = dto.getPrice();    // 숏 가격 
           rowData[i][2] = dto.getPrice() + 500;    // 톨 가격 
           rowData[i][3] = dto.getPrice() + 1000;    // 그란데 가격   
        }
        model = new DefaultTableModel(columnNames, 0) {   // (폭,높이)
            @Override
            public boolean isCellEditable(int row, int column) {  //수정, 입력 불가
               return false;
            }
         };
         
        model.setDataVector(rowData, columnNames); // (실제데이터:2차원배열, 범주)
         
        // Jtable
        jtable = new JTable(model);
         
        // column의 폭을 설정
        jtable.getColumnModel().getColumn(0).setMaxWidth(300);// 메뉴 이름
        jtable.getColumnModel().getColumn(1).setMaxWidth(200);// 숏
        jtable.getColumnModel().getColumn(2).setMaxWidth(200);// 톨
        jtable.getColumnModel().getColumn(3).setMaxWidth(200);// 그란데
        
        // 테이블 크기와 위치 설정
        jscrPane = new JScrollPane(jtable);
        jscrPane.setBounds(10, 50, 660, 500);       
        jp1.add(jscrPane);            //패널에 붙이기
        addMenu();
        deleteMenu();
   }
   
   private void addMenu() {
      addBtn = new JButton("추가");
      addBtn.setBounds(550, 0, 70, 30);
      addBtn.setFont(new Font("null", Font.BOLD,10));
      jp1.add(addBtn);
         
      addBtn.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            s.memCtrl.adminMenuAdd();            //메뉴 추가 관리
         }         
      });      
   }
   TextField txt[] = null;
   private void deleteMenu() {
	      deleteBtn = new JButton("삭제");
	      deleteBtn.setFont(new Font("null", Font.BOLD,10));
	      jp1.add(deleteBtn); 
	      deleteBtn.addActionListener(new ActionListener() { 
	    	  @Override
	          public void actionPerformed(ActionEvent e) {
	             s.memCtrl.adminMenuDelete();            //메뉴 추가 관리
	          }    
	      });      
	   }

}
