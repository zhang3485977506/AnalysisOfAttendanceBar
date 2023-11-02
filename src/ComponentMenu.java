import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import java.util.Set;

public class ComponentMenu {
    JFrame jFrame = new JFrame("学习部考勤条处理系统");

    JTabbedPane jTabbedPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

//    public static void main(String[] args) {
//        new ComponentMenu().init();
//    }

    public void init() {

        Image iconImage = new ImageIcon("ico.png").getImage();
        // 设置窗口的图标
        jFrame.setIconImage(iconImage);

        Font font = new Font("宋体", Font.BOLD, 30); // 创建一个新的字体对象，设置字体名称、样式和大小
        jTabbedPane.setFont(font); // 设置JTabbedPane的字体

        JTabbedPane jTabbedPane1 = ComponentMenu_1.get();
        JTabbedPane jTabbedPane2 = ComponentMenu_2.get();

        jTabbedPane.addTab("处理考勤分配表", new ImageIcon(), jTabbedPane1);

        jTabbedPane.addTab("处理考勤表", jTabbedPane2);


        JMenu jMenu = new JMenu("设置");


        jMenu.add(new JMenuItem(new AbstractAction("设置新生班级") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    File file = new File("Data\\Night_Disposition.properties");

                    Properties properties = new Properties();
                    properties.load(new FileReader(file));

                    JDialog jDialog = new JDialog();
                    jDialog.setTitle("设置新生班级");
                    jDialog.setLocation(200, 300);

                    Font f = new Font("宋体", Font.PLAIN, 30);

                    Box verticalBox = Box.createVerticalBox();
                    JLabel tip = new JLabel("提示:格式很重要，前面是4个字符，后面是7个");
                    tip.setFont(new Font("宋体", Font.PLAIN, 15));
                    tip.setForeground(Color.red);
                    verticalBox.add(tip);
                    Set<Object> objects = properties.keySet();

                    for (Object object : objects) {
                        String key=null;


                        Box horizontalBox = Box.createHorizontalBox();


                        JLabel jLabel = new JLabel("班级:");
                        jLabel.setFont(f);
                        JTextField jTextField = new JTextField((String) object);
                        key=jTextField.getText();
                        jTextField.setFont(f);
                        jTextField.setBackground(Color.PINK);
                        JLabel jLabel1 = new JLabel("晚自习教室:");
                        jLabel1.setFont(f);
                        JTextField jTextField1 = new JTextField((String) properties.get(object));
                        jTextField1.setFont(f);
                        jTextField1.setBackground(Color.LIGHT_GRAY);

                       String finalKey = key;

                        DocumentListener documentListener = new DocumentListener() {
                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                if(jTextField.getText().length()==4&&jTextField1.getText().length()==7) {
                                    properties.remove(finalKey);
                                    properties.put(jTextField.getText(), jTextField1.getText());
                                }
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                if(jTextField.getText().length()==4&&jTextField1.getText().length()==7) {
                                    properties.remove(finalKey);
                                    properties.put(jTextField.getText(), jTextField1.getText());
                                }
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                if(jTextField.getText().length()==4&&jTextField1.getText().length()==7) {
                                    properties.remove(finalKey);
                                    properties.put(jTextField.getText(), jTextField1.getText());
                                }
                            }
                        };

                        jTextField.getDocument().addDocumentListener(documentListener);
                        jTextField1.getDocument().addDocumentListener(documentListener);

                        horizontalBox.add(jLabel);
                        horizontalBox.add(jTextField);
                        horizontalBox.add(Box.createHorizontalGlue());
                        horizontalBox.add(jLabel1);
                        horizontalBox.add(jTextField1);

                        verticalBox.add(horizontalBox);

                    }

                    JPanel jPanel = new JPanel();
                    JButton jButton = new JButton(new AbstractAction("确定") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try (OutputStream output = new FileOutputStream("Data\\Night_Disposition.properties")) {

                                properties.store(output, "新生晚自习班级");

                            } catch (IOException k) {
                                k.printStackTrace();
                            }
                            System.out.println(properties);
                        }
                    });

                    jButton.setPreferredSize(new Dimension(300, 25));

                    jPanel.add(jButton);

                    verticalBox.add(jPanel);


                    jDialog.add(verticalBox);

                    jDialog.setVisible(true);
                    jDialog.pack();


                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }));


        jMenu.add(new JMenuItem(new AbstractAction("系统信息") {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(jFrame,"系统作者：张应胜   版本号 V1.0");

            }
        }));


        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);


        JMenu jMenu1 = new JMenu("风格");

        JMenuItem j1 = new JMenuItem(new AbstractAction("Metal 风格") {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveStyle("javax.swing.plaf.metal.MetalLookAndFeel", jFrame, jTabbedPane1, jTabbedPane2, jTabbedPane);
            }
        });
        JMenuItem j2 = new JMenuItem(new AbstractAction("Nimbus 风格") {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveStyle("javax.swing.plaf.nimbus.NimbusLookAndFeel", jFrame, jTabbedPane1, jTabbedPane2, jTabbedPane);

            }
        });
        JMenuItem j3 = new JMenuItem(new AbstractAction("Windows 风格") {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveStyle("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", jFrame, jTabbedPane1, jTabbedPane2, jTabbedPane);

            }
        });
        JMenuItem j4 = new JMenuItem(new AbstractAction("Windows 经典风格") {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveStyle("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", jFrame, jTabbedPane1, jTabbedPane2, jTabbedPane);

            }
        });
        JMenuItem j5 = new JMenuItem(new AbstractAction("Motif 风格") {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveStyle("com.sun.java.swing.plaf.motif.MotifLookAndFeel", jFrame, jTabbedPane1, jTabbedPane2, jTabbedPane);

            }
        });
        jMenu1.add(j1);
        jMenu1.add(j2);
        jMenu1.add(j3);
        jMenu1.add(j4);
        jMenu1.add(j5);


        jMenuBar.add(jMenu1);

        jFrame.setJMenuBar(jMenuBar);


        jFrame.add(jTabbedPane);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void moveStyle(String style, Component a, Component a1, Component a2, Component a3) {
        try {
            UIManager.setLookAndFeel(style);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(a);
        SwingUtilities.updateComponentTreeUI(a1);
        SwingUtilities.updateComponentTreeUI(a2);
        SwingUtilities.updateComponentTreeUI(a3);
    }
}

