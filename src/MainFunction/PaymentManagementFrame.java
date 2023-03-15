package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentManagementFrame {
    JFrame PaymentManagementFrame=new JFrame();
    public PaymentManagementFrame(){
        initJFrame();
        initView();
        PaymentManagementFrame.setVisible(true);
    }

    private void initView() {
        JButton pay=new JButton("缴费");
        pay.setFont(new Font("黑体",Font.PLAIN,20));
        pay.setBounds(20,30,360,65);
        PaymentManagementFrame.add(pay);
        JButton addPay=new JButton("新增缴费");
        addPay.setFont(new Font("黑体",Font.PLAIN,20));
        addPay.setBounds(20,125,360,65);
        PaymentManagementFrame.add(addPay);
        JButton exportOwnerPay=new JButton("导出缴费");
        exportOwnerPay.setFont(new Font("黑体",Font.PLAIN,20));
        exportOwnerPay.setBounds(20,220,360,65);
        PaymentManagementFrame.add(exportOwnerPay);
        JButton Return=new JButton("返回");
        Return.setFont(new Font("黑体",Font.PLAIN,20));
        Return.setBounds(20,315,360,65);
        PaymentManagementFrame.add(Return);
        pay.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentManagementFrame.setVisible(false);
                new payFrame();
            }
        });
        addPay.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentManagementFrame.setVisible(false);
                new addPayFrame();
            }
        });
        exportOwnerPay.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = new MyConnection().ConnectionDBS();
                Statement stmt;
                ResultSet rs;//建立与数据库的连接
                String sql="select name,ownerPay.roomNum,phoneNum,arrears,arrearsProject from owner,ownerpay "+
                        "where owner.roomNum=ownerpay.roomNum";
                try{
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(sql);
                    BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\桌面\\缴费信息.txt"));
                    while(rs.next()){
                        bw.write(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDouble(4)+" "+rs.getString(5));
                        bw.newLine();
                    }
                    bw.close();
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(PaymentManagementFrame,"导出成功！","提示",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentManagementFrame.setVisible(false);
                new MainFunctionFrame();//返回上级菜单
            }
        });
    }

    private void initJFrame() {
        PaymentManagementFrame.setTitle("缴费管理");
        PaymentManagementFrame.setSize(400,430);
        PaymentManagementFrame.setLocationRelativeTo(null);
        PaymentManagementFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PaymentManagementFrame.setResizable(false);
        PaymentManagementFrame.setLayout(null);
    }
}
