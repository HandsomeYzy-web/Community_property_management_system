package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class PopOperationFrame2 {
    JFrame PopOperationFrame2=new JFrame();
    public PopOperationFrame2(String operation){
        initJFrame(operation);
        initView(operation);
        PopOperationFrame2.setVisible(true);
    }

    private void initView(String operation) {
        JLabel OperationText =new JLabel("请输入要"+operation+"的户主!");
        OperationText.setFont(new Font("黑体",Font.PLAIN,30));
        OperationText.setBounds(150,45,420,60);
        PopOperationFrame2.add(OperationText);
        JLabel roomNumText =new JLabel("房号");
        roomNumText.setFont(new Font("黑体",Font.PLAIN,30));
        roomNumText.setBounds(30,195,180,60);
        PopOperationFrame2.add(roomNumText);
        JTextField roomNum=new JTextField();
        roomNum.setFont(new Font("黑体",Font.PLAIN,18));
        roomNum.setBounds(210,205,360,40);
        PopOperationFrame2.add(roomNum);//添加户主
        JButton Button=new JButton(operation);
        Button.setFont(new Font("黑体",Font.PLAIN,20));
        Button.setBounds(76,300,150,75);
        PopOperationFrame2.add(Button);//添加操作按钮
        JButton ReturnButton=new JButton("返回");
        ReturnButton.setFont(new Font("黑体",Font.PLAIN,20));
        ReturnButton.setBounds(374,300,150,75);
        PopOperationFrame2.add(ReturnButton);//添加返回
        Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(roomNum.getText().equals("")){
                    JOptionPane.showMessageDialog(PopOperationFrame2, "输入不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Connection con = new MyConnection().ConnectionDBS();
                    Statement stmt;
                    ResultSet rs;//建立与数据库的连接
                    String sql = "select * from owner" +
                            " where roomNum = '" + roomNum.getText() + "'";//sql语句用来查找数据库中是否有管理员输入的房号
                    try {
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            Owner ow=new Owner(rs.getString(1), rs.getString(2),rs.getString(3) );
                            if(operation.equals("删除")) {
                                int option = JOptionPane.showConfirmDialog(PopOperationFrame2, "是否删除如下数据\r\n" + ow.toString(), "提示", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.YES_OPTION) {
                                    String sql2 = "delete from owner" +
                                            " where roomNum = '" + roomNum.getText() + "'";
                                    stmt.executeUpdate(sql2);
                                    JOptionPane.showMessageDialog(PopOperationFrame2, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }else if(operation.equals("查询")){
                                JOptionPane.showMessageDialog(PopOperationFrame2,ow.toString(),"查询到如下数据",JOptionPane.INFORMATION_MESSAGE);
                            } else if(operation.equals("修改")) {
                                PopOperationFrame2.setVisible(false);
                                new PopOperationFrame("修改",roomNum.getText());
                            }
                        }else{
                            JOptionPane.showMessageDialog(PopOperationFrame2,"未查询到要"+operation+"的房号！","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                        rs.close();
                        stmt.close();
                        con.close();//释放资源
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        ReturnButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopOperationFrame2.setVisible(false);
                new PopulationManagementFrame();
            }
        });
    }

    private void initJFrame(String operation) {
        PopOperationFrame2.setTitle(operation);
        PopOperationFrame2.setSize(600,400);
        PopOperationFrame2.setLocationRelativeTo(null);
        PopOperationFrame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PopOperationFrame2.setResizable(false);
        PopOperationFrame2.setLayout(null);
    }
}
