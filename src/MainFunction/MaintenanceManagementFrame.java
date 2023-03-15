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

public class MaintenanceManagementFrame {
    JFrame MaintenanceManagementFrame = new JFrame();

    public MaintenanceManagementFrame() {
        initJFrame();
        initView();
        MaintenanceManagementFrame.setVisible(true);
    }

    private void initView() {
        JButton solve = new JButton("处理报修");
        solve.setFont(new Font("黑体", Font.PLAIN, 20));
        solve.setBounds(20, 30, 360, 65);
        MaintenanceManagementFrame.add(solve);
        JButton addMaintenance = new JButton("新增报修");
        addMaintenance.setFont(new Font("黑体", Font.PLAIN, 20));
        addMaintenance.setBounds(20, 125, 360, 65);
        MaintenanceManagementFrame.add(addMaintenance);
        JButton exportMaintenance = new JButton("导出报修");
        exportMaintenance.setFont(new Font("黑体", Font.PLAIN, 20));
        exportMaintenance.setBounds(20, 220, 360, 65);
        MaintenanceManagementFrame.add(exportMaintenance);
        JButton Return = new JButton("返回");
        Return.setFont(new Font("黑体", Font.PLAIN, 20));
        Return.setBounds(20, 315, 360, 65);
        MaintenanceManagementFrame.add(Return);
        solve.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaintenanceManagementFrame.setVisible(false);
                new solveFrame();
            }
        });
        addMaintenance.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaintenanceManagementFrame.setVisible(false);
                new addMaintenanceFrame();
            }
        });
        exportMaintenance.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = new MyConnection().ConnectionDBS();
                Statement stmt;
                ResultSet rs;//建立与数据库的连接
                String sql="select name,ownermaintenance.roomNum,phoneNum,item,time from owner,ownermaintenance "+
                        "where owner.roomNum=ownermaintenance.roomNum";
                try{
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(sql);
                    BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\桌面\\报修信息.txt"));
                    while(rs.next()){
                        bw.write(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
                        bw.newLine();
                    }
                    bw.close();
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(MaintenanceManagementFrame, "导出成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaintenanceManagementFrame.setVisible(false);
                new MainFunctionFrame();//返回上级菜单
            }
        });
    }

    private void initJFrame() {
        MaintenanceManagementFrame.setTitle("缴费管理");
        MaintenanceManagementFrame.setSize(400, 430);
        MaintenanceManagementFrame.setLocationRelativeTo(null);
        MaintenanceManagementFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MaintenanceManagementFrame.setResizable(false);
        MaintenanceManagementFrame.setLayout(null);
    }
}
