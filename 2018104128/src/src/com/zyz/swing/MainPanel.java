package com.zyz.swing;

import com.zyz.jdbc.SqlMenu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainPanel {
    JFrame frame = new JFrame("欢迎使用");

    //标题设置
    JPanel panel = new JPanel();
    private JLabel title = new JLabel("零件库存管理系统");
    private JLabel infos = new JLabel("库存信息");

    //入库
    private JTextField inNumber = new JTextField(14);

    // 各控件的panel
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();
    private JPanel panel6 = new JPanel();
    private JPanel panel8 = new JPanel();

    //各种控件
    private JLabel inNumberName = new JLabel("物料编码");
    private JButton inBase = new JButton("入库");
    private JButton outBase = new JButton("出库");
    private JButton search = new JButton("搜索");
    private JButton modifyPrice = new JButton("修改价格");
    private JButton searchOrder = new JButton("订单记录");

    // 显示数据库里信息
    private JPanel panel7 = new JPanel();

    public void init(JTable jTable) throws SQLException {
        try {
            String[] dataSet = SqlMenu.selectBaseInfo(inNumber.getText().trim(), "goods_info");
            Object[][] table = SqlMenu.selectData("all", 300);
            String[] head = new String[] {"物料编码", "物料名称", "厂家产品码", "计量单位", "结算价", "库存数量"};
            DefaultTableModel tableModel = new DefaultTableModel(table, head){
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            jTable.setModel(tableModel);
            infos.setText("库存总"+dataSet[0]+"件商品, 合计"+dataSet[1]+"元");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    //主面板设置
    public MainPanel() throws SQLException {
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * 添加其他控件
         */
        // JLabel控件
        JTable jTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jTable);

        // 设置字体大小TimesRoman
        title.setFont(new Font("TimesRoman",Font.BOLD, 44));
        inBase.setFont(new Font("黑体",Font.BOLD, 14));
        outBase.setFont(new Font("黑体",Font.BOLD, 14));
        search.setFont(new Font("黑体",Font.BOLD, 15));
        inNumberName.setFont(new Font("黑体",Font.BOLD, 14));
        modifyPrice.setFont(new Font("黑体",Font.BOLD, 13));
        searchOrder.setFont(new Font("黑体",Font.BOLD, 13));
        infos.setFont(new Font("黑体",Font.BOLD, 11));


        panel.add(title);
        panel.setBounds(30,20, 600, 80);


        panel1.add(inNumber);
        panel2.add(search);
        panel3.add(inBase);
        panel4.add(outBase);
        panel5.add(modifyPrice);
        panel8.add(searchOrder);

        panel6.setLayout(new BorderLayout());
        panel6.add(infos, BorderLayout.EAST);


        panel1.setBounds(180, 100, 180, 40);
        panel2.setBounds(380, 99, 65, 40);

        panel3.setBounds(150, 140, 65, 40);
        panel4.setBounds(220, 140, 65, 40);
        panel5.setBounds(280, 140, 105, 40);
        panel8.setBounds(370, 140, 105, 40);

        panel6.setBounds(370, 175, 300, 20);
        scrollPane.setBounds(30,200,640,180);


        frame.setLayout(null);
        frame.getContentPane().add(panel);
        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
        frame.getContentPane().add(panel4);
        frame.getContentPane().add(panel5);
        frame.getContentPane().add(panel6);
        frame.getContentPane().add(panel8);
        frame.getContentPane().add(scrollPane);

        // 初始化面板
        init(jTable);

        /**
         * 三个按钮的事件监听程序
         */

        // 入库按钮监听器
        class InBaseButton implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String number = inNumber.getText().trim();
                int total = 0;
                Object[][] table = new Object[1][6];

                // 校验该数据是否存在数据库中
                try {
                    table = SqlMenu.selectData(number,1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                if(number.equals(table[0][0])){
                    // 查询该商品的库存数
                    total = Integer.parseInt((String)table[0][5]);
                    Object obj = JOptionPane.showInputDialog(frame,"零件"+table[0][0]+"剩余库存数"+table[0][5]+"，请输入入库数量","",JOptionPane.INFORMATION_MESSAGE);
                    int totalInput = Integer.parseInt((String)obj);

                    if(totalInput < 0){
                        JOptionPane.showMessageDialog(frame, "入库数量非法，请重试！！！", "",JOptionPane.ERROR_MESSAGE);
                    }else {
                        int flag = JOptionPane.showConfirmDialog(frame, "确认入库？", "", JOptionPane.ERROR_MESSAGE);
                        if(flag==0){
                            total = total + totalInput;
                            //商品入库操作
                            try {
                                SqlMenu.updateTotalData(number,total);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            // 将操作记录插入数据库
                            table[0][5] = totalInput+"";

                            String[] dataSet = (String[]) table[0];
                            try {
                                SqlMenu.insertRecordData(dataSet, "入库记录");
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    try {
                        init(jTable);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(frame, "请输入正确的物料编码！！！", "",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        //出库按钮事件监听器
        class OutBaseButton implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String number = inNumber.getText().trim();
                int total = 0;
                Object[][] table = new Object[1][6];

                // 校验该数据是否存在数据库中
                try {
                    table = SqlMenu.selectData(number,1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                if(number.equals(table[0][0])){
                    total = Integer.parseInt((String)table[0][5]);
                    Object obj = JOptionPane.showInputDialog(frame,"零件"+table[0][0]+"剩余库存数"+table[0][5]+"，请输入出库数量","",JOptionPane.INFORMATION_MESSAGE);
                    int totalInput = Integer.parseInt((String)obj);
                    if(totalInput > total || totalInput < 0){
                        JOptionPane.showMessageDialog(frame, "出库数量非法，请重试！！！", "",JOptionPane.ERROR_MESSAGE);
                    }else {
                        int flag = JOptionPane.showConfirmDialog(frame, "确认出库？", "", JOptionPane.ERROR_MESSAGE);
                        if(flag == 0) {
                            total = total - totalInput;
                            try {
                                SqlMenu.updateTotalData(number, total);
                                init(jTable);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            // 将操作历史记录插入数据库
                            table[0][5] = totalInput + "";

                            String[] dataSet = (String[]) table[0];
                            try {
                                SqlMenu.insertRecordData(dataSet, "出库记录");
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                }else {
                    JOptionPane.showMessageDialog(frame, "请输入正确的物料编码！！！", "",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // 查询按钮事件监听器
        class SearchButton implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                String number = inNumber.getText().trim();
                try {
                    Object[][] table = SqlMenu.selectData(inNumber.getText(), -1);
                    String[] head = new String[] {"物料编码", "物料名称", "厂家产品码", "计量单位", "结算价", "库存数量"};

                    DefaultTableModel tableModel = new DefaultTableModel(table, head){
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    jTable.setModel(tableModel);

                    String[] dataSet = SqlMenu.selectBaseInfo(inNumber.getText().trim(), "goods_info");
                    if(inNumber.getText().trim().equals("")){
                        infos.setText("库存总"+dataSet[0]+"件, 合计"+dataSet[1]+"元");
                    }else {
                        infos.setText("该类商品库存" + dataSet[0] + "件, 合计" + dataSet[1] + "元");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


        }

        //修改价格
        class ModifyPriceButton implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String number  = inNumber.getText().trim();
                Object[][] table = new Object[1][6];

                try {
                    table = SqlMenu.selectData(number, 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                if(number.equals(table[0][0])){
                    Object obj = JOptionPane.showInputDialog(frame,"零件"+table[0][0]+"当前价格:"+table[0][4]+"，请输入新价格","",JOptionPane.INFORMATION_MESSAGE);
                    double priceInput = Double.parseDouble((String)obj);
                    try {
                        SqlMenu.updatePriceData(number, priceInput);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(frame, "请输入正确的物料编码！！！", "",JOptionPane.ERROR_MESSAGE);
                }

                // 初始化面板
                try {
                    init(jTable);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // 查询历史订单记录
        class SearchOrderButton implements ActionListener{
            // 查询数据
            public void selectData(int param, int width, String text) throws SQLException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();

                c.add(Calendar.MONTH, param);
                Date d = c.getTime();
                String end = sdf.format(d).substring(0, 7)+"-01";

                c.add(Calendar.MONTH, width);
                d = c.getTime();
                String start = sdf.format(d).substring(0,7)+"-01";

                //
                Object table;
                if(text.contains("出库")) {
                    table = SqlMenu.selectRecordData(start, end, "出库记录");
                }else {
                    table = SqlMenu.selectRecordData(start, end, "入库记录");
                }
                String[] head = new String[] {"记录标识", "物料编码", "物料名称", "厂家产品码", "结算价", "出/入库件数", "更新时间"};

                DefaultTableModel tableModel = new DefaultTableModel((String[][]) table, head){
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                jTable.setModel(tableModel);

                //

                if(width == -12){
                    if(text.contains("出库")) {
                        String[] dataSet = SqlMenu.selectRecordBaseInfo(start, end, "出库记录");
                        infos.setText("过去一年到现在 出库记录共" + dataSet[0] + "件, 合计" + dataSet[1] + "元");
                    }else {
                        String[] dataSet = SqlMenu.selectRecordBaseInfo(start, end, "入库记录");
                        infos.setText("过去一年到现在 入库记录共" + dataSet[0] + "件, 合计" + dataSet[1] + "元");
                    }
                }else {
                    if(text.contains("出库")) {
                        String[] dataSet = SqlMenu.selectRecordBaseInfo(start, end, "出库记录");
                        infos.setText(start.substring(0, 4) + "年" + start.substring(5, 7) + "月 出库记录共" + dataSet[0] + "件, 合计" + dataSet[1] + "元");
                    }else {
                        String[] dataSet = SqlMenu.selectRecordBaseInfo(start, end, "入库记录");
                        infos.setText(start.substring(0, 4)+"年" +start.substring(5,7)+ "月 入库记录共" + dataSet[0] + "件, 合计" + dataSet[1] + "元");
                    }
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] table = new Object[8];
                table[0] = "当月入库量";
                table[1] = "当月出库量";
                table[2] = "上月入库量";
                table[3] = "上月出库量";
                table[4] = "上上月入库";
                table[5] = "上上月出库";
                table[6] = "近一年入库";
                table[7] = "近一年出库";

                Object obj = JOptionPane.showInputDialog(frame,"","", JOptionPane.PLAIN_MESSAGE, null, table,0);
                String text = (String)obj;
                switch (text){
                    case "当月出库量":
                    case "当月入库量": {
                        try {
                            selectData(1, -1, text);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    } break;
                    case "上月出库量":
                    case "上月入库量": {
                        try {
                            selectData(0, -1, text);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } break;
                    case "上上月出库":
                    case "上上月入库": {
                        try {
                            selectData(-1, -1, text);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }; break;
                    case "近一年出库":
                    case "近一年入库": {
                        try {
                            selectData(1, -12, text);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } break;
                    default: break;
                }
            }
        }

        //给按钮添加事件处理函数
        InBaseButton ibb = new InBaseButton();
        inBase.addActionListener(ibb);

        OutBaseButton obb = new OutBaseButton();
        outBase.addActionListener(obb);

        SearchButton sb = new SearchButton();
        search.addActionListener(sb);

        ModifyPriceButton mpb = new ModifyPriceButton();
        modifyPrice.addActionListener(mpb);

        SearchOrderButton sob = new SearchOrderButton();
        searchOrder.addActionListener(sob);

        // 设置面板为可见
        frame.setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        MainPanel mainPanel = new MainPanel();
    }
}
