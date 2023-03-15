package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PopulationManagementFrame {
    JFrame PopulationManagementFrame=new JFrame();
    public PopulationManagementFrame(){
        initJFrame();
        initView();
        PopulationManagementFrame.setVisible(true);
    }

    private void initView() {
        JButton AddOwner=new JButton("新增人口");
        AddOwner.setFont(new Font("黑体",Font.PLAIN,20));
        AddOwner.setBounds(20,30,360,65);
        PopulationManagementFrame.add(AddOwner);
        JButton DeleteOwner=new JButton("删除人口");
        DeleteOwner.setFont(new Font("黑体",Font.PLAIN,20));
        DeleteOwner.setBounds(20,125,360,65);
        PopulationManagementFrame.add(DeleteOwner);
        JButton QueryOwner=new JButton("查询人口");
        QueryOwner.setFont(new Font("黑体",Font.PLAIN,20));
        QueryOwner.setBounds(20,220,360,65);
        PopulationManagementFrame.add(QueryOwner);
        JButton ModifyOwner=new JButton("修改人口");
        ModifyOwner.setFont(new Font("黑体",Font.PLAIN,20));
        ModifyOwner.setBounds(20,315,360,65);
        PopulationManagementFrame.add(ModifyOwner);
        JButton ExportOwner=new JButton("导出人口");
        ExportOwner.setFont(new Font("黑体",Font.PLAIN,20));
        ExportOwner.setBounds(20,410,360,65);
        PopulationManagementFrame.add(ExportOwner);
        JButton Return=new JButton("返回");
        Return.setFont(new Font("黑体",Font.PLAIN,20));
        Return.setBounds(20,505,360,65);
        PopulationManagementFrame.add(Return);
        AddOwner.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopulationManagementFrame.setVisible(false);
                new PopOperationFrame("新增","");
            }
        });
        DeleteOwner.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopulationManagementFrame.setVisible(false);
                new PopOperationFrame2("删除");
            }
        });
        QueryOwner.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopulationManagementFrame.setVisible(false);
                new PopOperationFrame2("查询");
            }
        });
        ModifyOwner.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopulationManagementFrame.setVisible(false);
                new PopOperationFrame2("修改");
            }
        });
        ExportOwner.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = new MyConnection().ConnectionDBS();
                Statement stmt;
                ResultSet rs;//建立与数据库的连接
                String sql="select * from owner";
                try{
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(sql);
                    BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\桌面\\房主信息.txt"));
                    while(rs.next()){
                        bw.write(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
                        bw.newLine();
                    }
                    bw.close();
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(PopulationManagementFrame,"导出成功！","提示",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopulationManagementFrame.setVisible(false);
                new MainFunctionFrame();//返回上级菜单
            }
        });
    }

    private void initJFrame() {
        PopulationManagementFrame.setTitle("社区人口管理");
        PopulationManagementFrame.setSize(400,630);
        PopulationManagementFrame.setLocationRelativeTo(null);
        PopulationManagementFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PopulationManagementFrame.setResizable(false);
        PopulationManagementFrame.setLayout(null);
    }
}
