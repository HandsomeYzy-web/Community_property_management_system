package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class noticeManagementFrame {
    JFrame noticeManagementFrame=new JFrame();
    public noticeManagementFrame(){
        initJFrame();
        initView();
        noticeManagementFrame.setVisible(true);
    }

    private void initView() {
        String s="<html><body>";
        Connection con = new MyConnection().ConnectionDBS();
        Statement stmt;
        ResultSet rs;
        String sql="select num,notice from notice ";
        try {
            stmt= con.createStatement();
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                s=s+rs.getString(1)+"."+rs.getString(2)+"<br>";
            }
            s=s+"<body></html>";
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JLabel notice=new JLabel(s);
        notice.setFont(new Font("黑体",Font.PLAIN,20));
        notice.setBounds(0,0,600,300);
        noticeManagementFrame.add(notice);
        JButton addButton =new JButton("新增");
        addButton.setFont(new Font("黑体",Font.PLAIN,20));
        addButton.setBounds(25,300,150,75);
        noticeManagementFrame.add(addButton);
        JButton deleteButton =new JButton("删除");
        deleteButton.setFont(new Font("黑体",Font.PLAIN,20));
        deleteButton.setBounds(225,300,150,75);
        noticeManagementFrame.add(deleteButton);
        JButton ReturnButton=new JButton("返回");
        ReturnButton.setFont(new Font("黑体",Font.PLAIN,20));
        ReturnButton.setBounds(425,300,150,75);
        noticeManagementFrame.add(ReturnButton);
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String not=JOptionPane.showInputDialog(noticeManagementFrame,"请输入要添加的通知:","新增通知",JOptionPane.INFORMATION_MESSAGE);
                if(!not.equals("")){
                    String sql="insert into notice(notice) values('"+not+"')";
                    Connection con = new MyConnection().ConnectionDBS();
                    Statement stmt;
                    try {
                        stmt= con.createStatement();
                        stmt.executeUpdate(sql);
                        JOptionPane.showMessageDialog(noticeManagementFrame, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        stmt.close();
                        con.close();
                        noticeManagementFrame.setVisible(false);
                        new noticeManagementFrame();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(noticeManagementFrame, "输入为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num=JOptionPane.showInputDialog(noticeManagementFrame,"请输入要删除通知的编号:","删除通知",JOptionPane.INFORMATION_MESSAGE);
                String sql="delete from notice where num='"+num+"'";
                Connection con = new MyConnection().ConnectionDBS();
                Statement stmt;
                try {
                    stmt= con.createStatement();
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(noticeManagementFrame, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    stmt.close();
                    con.close();
                    noticeManagementFrame.setVisible(false);
                    new noticeManagementFrame();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ReturnButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noticeManagementFrame.setVisible(false);
                new MainFunctionFrame();
            }
        });
    }

    private void initJFrame() {
        noticeManagementFrame.setTitle("通知管理");
        noticeManagementFrame.setSize(600,400);
        noticeManagementFrame.setLocationRelativeTo(null);
        noticeManagementFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        noticeManagementFrame.setResizable(false);
        noticeManagementFrame.setLayout(null);
    }
}
