package contact;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class phonebook extends JFrame {
   private JPanel contentPane;
   private JTextField textField;
   private JTextField textName;
   private JTextField textField_1;
   private JTextField textField_2;
   List list;

   public static int selectedIndex =  -1;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               phonebook frame = new phonebook();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   void fill(String st)
   {
      try
      {
         File f=new File("c:\\temptxt\\phoneDB.txt");
         FileReader fw=new FileReader(f);
         BufferedReader br=new BufferedReader(fw);
         list.removeAll();
         String str;
         while((str=br.readLine())!=null)
         {
            if(str.startsWith(st))
               list.add(str);
         }
         br.close();
         fw.close();
      }
      catch(Exception e)
      {

      }
   }

   void find(String st)
   {
      try
      {
         File f=new File("c:\\temptxt\\phoneDB.txt");
         FileReader fw=new FileReader(f);
         BufferedReader br=new BufferedReader(fw);
         list.removeAll();
         String str;
         int i = 0;
         while((str=br.readLine())!=null)
         {
            if(str.equals(st)) {
               String[] itemInfo = str.split(",");
               textField.setText(itemInfo[0]);
               textField_1.setText(itemInfo[1]);
               selectedIndex = i;
            }
            // JOptionPane.showMessageDialog(null,str.split(",")[1]);
            i++;
         }
         br.close();
         fw.close();
      }
      catch(Exception e)
      {
         JOptionPane.showMessageDialog(null, e.getMessage());
      }
   }




   public phonebook() throws IOException {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 298, 428);

      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

      setContentPane(contentPane);
      contentPane.setLayout(null);

      textField = new JTextField();
      textField.setBounds(88, 30, 170, 20);
      contentPane.add(textField);

      JLabel jLabelName = new JLabel("�̸�");
      contentPane.add(jLabelName);
      Dimension size = jLabelName.getPreferredSize();
      jLabelName.setBounds(50, 30, size.width, size.height);

      textField.setColumns(10);


      textField_1 = new JTextField();
      textField_1.setBounds(88, 52, 170, 20);
      contentPane.add(textField_1);

      JLabel jLabePhone = new JLabel("��ȭ ��ȣ");
      contentPane.add(jLabePhone);
      Dimension size1 = jLabePhone.getPreferredSize();
      jLabePhone.setBounds(30, 53, size1.width, size1.height);
      textField_1.setColumns(10);

      JButton btnSave = new JButton("����");
      btnSave.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0)
         {
            try
            {
               if ("".equals(textField.getText().trim())) {
                  // �̸��� ������� ���
                  JOptionPane.showMessageDialog(null,"�̸��� �Է����ּ���.");
                  textField.requestFocus();
                  return;
               }
               if ("".equals(textField_1.getText().trim())) {
                  // ��ȭ��ȣ�� ������� ���
                  JOptionPane.showMessageDialog(null,"��ȭ��ȣ�� �Է����ּ���.");
                  textField_1.requestFocus();
                  return;
               }

               File f=new File("c:\\temptxt\\phoneDB.txt");
               FileWriter fw=new FileWriter(f,true);
               PrintWriter pw=new PrintWriter(fw);
               pw.println(textField.getText()+","+textField_1.getText());
               pw.close();
               fw.close();
               list.add(textField.getText()+","+textField_1.getText());
               JOptionPane.showMessageDialog(null,"������ ����");
               textField.requestFocus(); // �ٽ� �̸� �Է¶����� ���ư�.
               refreshInputField(); // �Է¶� �ʱ�ȭ
            }
            catch(Exception e)
            {
               JOptionPane.showMessageDialog(null,e.getMessage());
            }
         }
      });
      btnSave.setBounds(30, 90, 60, 23);
      contentPane.add(btnSave);


      JButton btnUpdate = new JButton("����");
      btnUpdate.setBounds(110, 90, 60, 23);
      contentPane.add(btnUpdate);

      //������ư
      btnUpdate.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0) {
            try
            {
               StringBuilder fullMsg = new StringBuilder();
               String msg;
               BufferedReader br=new BufferedReader(new FileReader("c:\\temptxt\\phoneDB.txt"));
               int i = 0;

               /**
                * ���� �д� ����
                */
               while((msg=br.readLine())!=null) {
                  //Ư�� ���ڰ� ���Ե� ���� �ǳ� �ٰ�, ���� ���� �� ���Ͽ� ����
                  if(selectedIndex != i) {
                     fullMsg.append(msg + "\n");
                  } else {
                     fullMsg.append(textField.getText() + "," + textField_1.getText() + "\n");
                  }
                  i++;
               }
               String result = fullMsg.toString();
               result = result.substring(0, result.lastIndexOf("\n"));
               System.out.println(result);

               File f=new File("c:\\temptxt\\phoneDB.txt");
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);
               f.createNewFile();

               /**
                * ���� ���� ����
                */
               //Ư�� ���ڰ� ���Ե� ���� �ǳ� �ٰ�, ���� ���� �� ���Ͽ� ����
               bw.write(result); // ���� ��� ���ڿ� ����ġ��
               bw.write("\n");
               bw.flush();

               refreshInputField(); // �Է¶� �ʱ�ȭ
               bw.close();
               fw.close();

               fill(""); // ��� ���ΰ�ħ�ؼ� �����ش�.
            }
            catch(Exception e)
            {
               JOptionPane.showMessageDialog(null,e.getMessage());
            }
         }
      });

      //������ư
      JButton btndelete = new JButton("����");
      btndelete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0)
         {
            try
            {
               StringBuilder fullMsg = new StringBuilder();
               String msg;
               BufferedReader br=new BufferedReader(new FileReader("c:\\temptxt\\phoneDB.txt"));
               int i = 0;

               /**
                * ���� �д� ����
                */
               while((msg=br.readLine())!=null) {
                  //Ư�� ���ڰ� ���Ե� ���� �ǳ� �ٰ�, ���� ���� �� ���Ͽ� ����
                  if(selectedIndex != i) {
                     fullMsg.append(msg + "\n");
                  }
                  i++;
               }
               String result = fullMsg.toString();
               if (result.contains("\n")) {
                  // ���� �̻� ���� ��� ������ ���� ���� ����
                  result = result.substring(0, result.lastIndexOf("\n"));
               }
               System.out.println(result);

               File f=new File("c:\\temptxt\\phoneDB.txt");
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);
               f.createNewFile();

               /**
                * ���� ���� ����
                */
               //Ư�� ���ڰ� ���Ե� ���� �ǳ� �ٰ�, ���� ���� �� ���Ͽ� ����
               bw.write(result); // ���� ��� ���ڿ� ����ġ��
               bw.write("\n");
               bw.flush();

               refreshInputField(); // �Է¶� �ʱ�ȭ
               bw.close();
               fw.close();

               fill(""); // ��� ���ΰ�ħ�ؼ� �����ش�.
            }
            catch(Exception e)
            {
               JOptionPane.showMessageDialog(null,e.getMessage());
            }
         }
      });
      btndelete.setBounds(190, 90, 60, 23);
      contentPane.add(btndelete);

      JLabel jLabeselect = new JLabel("�˻���");
      contentPane.add(jLabeselect);
      Dimension size2 = jLabeselect.getPreferredSize();
      jLabeselect.setBounds(130, 125, size2.width, size2.height);

      textField_2 = new JTextField();
      textField_2.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent arg0)
         {
            fill(textField_2.getText());
         }
         @Override
         public void keyPressed(KeyEvent arg0)
         {
            fill(textField_2.getText());
         }
      });
      textField_2.setBounds(36, 144, 221, 20);
      contentPane.add(textField_2);
      textField_2.setColumns(10);
      JLabel jLaberesult = new JLabel("�����");
      contentPane.add(jLaberesult);
      Dimension size3 = jLaberesult.getPreferredSize();
      jLaberesult.setBounds(130, 178, size3.width, size3.height);
      list = new List();
      list.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0)
         {
            find(list.getSelectedItem());
            fill(""); // ��� ���ΰ�ħ�ؼ� �����ش�.
         }
      });
      list.setBounds(36, 209, 221, 159);
      contentPane.add(list);
      fill(""); // ��� ���ΰ�ħ�ؼ� �����ش�.
   }

   //�Է¶� �ʱ�ȭ
   void refreshInputField() {
      textField.setText("");
      textField_1.setText("");
   }

   void delete() {
      int index = list.getSelectedIndex(); // ������ �ɼ��� �ε���
      try
      {
         File f=new File("c:\\temptxt\\phoneDB.txt");
         FileReader fr=new FileReader(f);
         FileWriter fw = new FileWriter(f);
         BufferedWriter bw = new BufferedWriter(fw);
         BufferedReader br=new BufferedReader(fr);
         list.removeAll();
         String str;
         int i=0;
         while((str=br.readLine())!=null)
         {
            if (i != index) {
               // �ش� �� (Index) �����ϰ� ���� �ۼ�
               bw.write(br.readLine());
               bw.write("\n");
            }
            i++;
            bw.flush();
         }
         br.close();
         fr.close();
      }
      catch(Exception e)
      {
         JOptionPane.showMessageDialog(null, e.getMessage());
      }
   }
}