package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class solveFrame {
    JFrame solveFrame=new JFrame();
    public solveFrame(){
        initJFrame();
        initView();
        solveFrame.setVisible(true);
    }

    private void initView() {
        JLabel OperationText =new JLabel("请输入要处理的户主!");
        OperationText.setFont(new Font("黑体",Font.PLAIN,30));
        OperationText.setBounds(150,45,420,60);
        solveFrame.add(OperationText);
        JLabel roomNumText =new JLabel("房号");
        roomNumText.setFont(new Font("黑体",Font.PLAIN,30));
        roomNumText.setBounds(30,195,180,60);
        solveFrame.add(roomNumText);
        JTextField roomNum=new JTextField();
        roomNum.setFont(new Font("黑体",Font.PLAIN,18));
        roomNum.setBounds(210,205,360,40);
        solveFrame.add(roomNum);//添加户主
        JButton Button=new JButton("查询");
        Button.setFont(new Font("黑体",Font.PLAIN,20));
        Button.setBounds(76,300,150,75);
        solveFrame.add(Button);//添加操作按钮
        JButton ReturnButton=new JButton("返回");
        ReturnButton.setFont(new Font("黑体",Font.PLAIN,20));
        ReturnButton.setBounds(374,300,150,75);
        solveFrame.add(ReturnButton);//添加返回
        Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(roomNum.getText().equals("")){
                    JOptionPane.showMessageDialog(solveFrame, "输入不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    Connection con = new MyConnection().ConnectionDBS();
                    Statement stmt;
                    ResultSet rs;
                    String sql = "select roomNum,item,time from ownermaintenance where roomNum='" + roomNum.getText() + "'";
                    try{
                        String s="";
                        OwnerMaintenance om;
                        stmt= con.createStatement();
                        rs=stmt.executeQuery(sql);
                        if(rs.next()) {
                            do {
                                om = new OwnerMaintenance(rs.getString(1), rs.getString(2), rs.getNString(3));
                                s = s + om.toString();
                            } while (rs.next());
                            int option = JOptionPane.showConfirmDialog(solveFrame, "是否已经处理以下报修？\r\n" + s, "提示", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                String sql2 = "delete from ownermaintenance" +
                                        " where roomNum = '" + roomNum.getText() + "'";
                                stmt.executeUpdate(sql2);
                                JOptionPane.showMessageDialog(solveFrame, "处理成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(solveFrame, "未查询到报修信息！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                        rs.close();
                        stmt.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        ReturnButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveFrame.setVisible(false);
                new MaintenanceManagementFrame();
            }
        });
    }

    private void initJFrame() {
        solveFrame.setTitle("处理报修");
        solveFrame.setSize(600,400);
        solveFrame.setLocationRelativeTo(null);
        solveFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        solveFrame.setResizable(false);
        solveFrame.setLayout(null);
    }
}
