import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ComponentMenu_1 {
    public static void main(String[] args) {

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane jTabbedPane = get();
        jFrame.add(jTabbedPane);
        jFrame.setVisible(true);
        jFrame.pack();
    }


    public static JTabbedPane get() {
        JTabbedPane jTabbedPane = new JTabbedPane();

        Font font = new Font("宋体", Font.BOLD, 30); // 创建一个新的字体对象，设置字体名称、样式和大小
        Font f = new Font("宋体", Font.BOLD, 25);

        jTabbedPane.setFont(font); // 设置JTabbedPane的字体

        JPanel jPanel = new JPanel();

        Box horizontalBox = Box.createHorizontalBox();

        JLabel day = new JLabel("白天考勤分配      ");


        day.setFont(f);
        day.setForeground(Color.gray);
        JTextArea jTextArea1 = new JTextArea(50, 50);


        Box verticalBox1 = Box.createVerticalBox();
        verticalBox1.add(day);

        Box hb = Box.createHorizontalBox();
        JTextField year=new JTextField(4);
        JTextField month=new JTextField(4);
        JTextField date=new JTextField(4);
        JLabel jLabel = new JLabel("初始时间(周一)");
        JLabel jLabel0 = new JLabel("年");
        JLabel jLabel1 = new JLabel("月");
        JLabel jLabel2 = new JLabel("日");
        Font font_t=new Font("微软雅黑",Font.PLAIN,20);
        jLabel.setFont(font_t);
        jLabel0.setFont(font_t);
        jLabel1.setFont(font_t);
        jLabel2.setFont(font_t);

        hb.add(jLabel);
        hb.add(year);
        hb.add(jLabel0);
        hb.add(month);
        hb.add(jLabel1);
        hb.add(date);
        hb.add(jLabel2);
        hb.setBorder(new LineBorder(Color.gray,5));
        verticalBox1.add(hb);
        verticalBox1.add(new JScrollPane(jTextArea1));




        JLabel night = new JLabel("晚自习考勤分配      ");
        night.setFont(f);
        night.setForeground(Color.gray);

        JTextArea jTextArea2 = new JTextArea(10, 20);

        JButton jButton = new JButton(new AbstractAction("确认") {
            @Override
            public void actionPerformed(ActionEvent e) {

                String text_day =year.getText()+"年"+month.getText()+"月"+date.getText()+"日\n" +jTextArea1.getText();

                try {
                    FileWriter fileWriter=new FileWriter(new File("Data\\AttendanceAssignment_Day.txt"));
                    fileWriter.write(text_day);
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                String text_night = jTextArea2.getText();
                try {
                    FileWriter fileWriter=new FileWriter(new File("Data\\AttendanceAssignment_Night.txt"));
                    fileWriter.write(text_night);
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }



                Handle.run();

                File filePath = new File("Data\\OutAttendance");
                File[] files = filePath.listFiles();
                for (File file : files) {


                    File filepath = file.getAbsoluteFile();
                    JTable jTable = CsvToJTable.getJTable(String.valueOf(filepath));
                    String name = filepath.getName().split("_")[1].split("\\.")[0];

                    jTabbedPane.addTab(name, new JScrollPane(jTable));
                    jTabbedPane.setForegroundAt(1, Color.red);

                }

                jTabbedPane.setSelectedIndex(1);
                JLabel jLabel = new JLabel(" 处理成功  请将数据复制分发给干事");
                jLabel.setForeground(Color.GRAY);
                jLabel.setFont(new Font("宋体",Font.BOLD,50));
                jTabbedPane.setComponentAt(0,jLabel);
            }
        });


        jButton.setFont(new Font("宋体", Font.BOLD, 30));
        jButton.setForeground(Color.green);
        jButton.setBorder(new LineBorder(new Color(0, 255, 196)));
        jButton.setPreferredSize(new Dimension(500, 100));
        Box verticalBox2 = Box.createVerticalBox();

        JPanel jPanel1 = new JPanel();
        jPanel1.setPreferredSize(new Dimension(50, 10));
        jPanel1.add(jButton);

        verticalBox2.add(night);
        verticalBox2.add(new JScrollPane(jTextArea2));
        verticalBox2.add(Box.createVerticalStrut(10));
        verticalBox2.add(jPanel1);
        verticalBox2.add(Box.createVerticalStrut(10));


        horizontalBox.add(verticalBox1);
        horizontalBox.add(verticalBox2);

        jPanel.add(horizontalBox);


        jTabbedPane.addTab("主页", new ImageIcon(), jPanel);

//        jTabbedPane.addTab("处理考勤表",CsvToJTable.getJTable(""));


        return jTabbedPane;
    }


}
