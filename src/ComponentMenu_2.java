import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ComponentMenu_2 {

//
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
        JPanel jPanel=new JPanel();

        Font font = new Font("宋体", Font.BOLD, 30); // 创建一个新的字体对象，设置字体名称、样式和大小
        jTabbedPane.setFont(font); // 设置JTabbedPane的字体

        JLabel jLabel = new JLabel("处理考勤表");

        jLabel.setFont(new Font("宋体", Font.BOLD, 25));
        jLabel.setForeground(Color.gray);

        JTextArea text=new JTextArea(40,110);

        JButton jButton = new JButton(new AbstractAction("确认") {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    System.out.println(text.getText());
                    FileWriter fileWriter=new FileWriter(new File("Data\\data.txt"));
                    fileWriter.write(text.getText());
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                String run = Handle_Four.run();


                File filePath = new File("Data\\OutFourCsv");
                File[] files = filePath.listFiles();
                for (File file : files) {

                    File filepath = file.getAbsoluteFile();
                    JTable jTable = CsvToJTable.getJTable(String.valueOf(filepath));

                    String name = filepath.getName().split("\\.")[0];
                    jTabbedPane.addTab(name, new JScrollPane(jTable));
                    //todo jTabbedPane.setForegroundAt(1, Color.red);
                }
                jTabbedPane.addTab("缺勤名单",new TextArea(run,60,110));

                jTabbedPane.setSelectedIndex(0);
                JLabel jLabel = new JLabel(" 处理成功  请将数据粘贴即可");
                jLabel.setForeground(Color.GRAY);
                jLabel.setFont(new Font("宋体",Font.BOLD,50));
                jTabbedPane.setComponentAt(0,jLabel);
            }
        });

        jButton.setFont(new Font("宋体", Font.BOLD, 30));
        jButton.setForeground(Color.green);
        jButton.setBorder(new LineBorder(new Color(0, 255, 196)));
        jButton.setPreferredSize(new Dimension(500, 100));


        JPanel jPanel1=new JPanel();
        jPanel1.add(jButton);

        Box horizontalBox = Box.createVerticalBox();
        horizontalBox.add(jLabel);
        horizontalBox.add(new JScrollPane(text));
        horizontalBox.add(jPanel1);


        jPanel.add(horizontalBox);
        jTabbedPane.addTab("主页", new ImageIcon(), jPanel);

//        jTabbedPane.addTab("处理考勤表",CsvToJTable.getJTable(""));


        return jTabbedPane;
    }
}
