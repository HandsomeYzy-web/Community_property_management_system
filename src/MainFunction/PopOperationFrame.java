package MainFunction;

import Connection.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class PopOperationFrame {
    JFrame PopOperationFrame =new JFrame();
    public PopOperationFrame(String operation,String room){
        initJFrame(operation);
        initView(operation,room);
        PopOperationFrame.setVisible(true);
    }

    private void initView(String operation,String room) {
        JLabel OperationText=new JLabel("请输入要"+operation+"的信息!");
        OperationText.setFont(new Font("黑体",Font.PLAIN,30));
        OperationText.setBounds(150,0,420,30);
        PopOperationFrame.add(OperationText);
        JLabel nameText=new JLabel("户主");
        nameText.setFont(new Font("黑体",Font.PLAIN,30));
        nameText.setBounds(30,50,180,60);
        PopOperationFrame.add(nameText);
        JTextField name=new JTextField();
        name.setFont(new Font("黑体",Font.PLAIN,18));
        name.setBounds(210,60,360,40);
        PopOperationFrame.add(name);//添加户主
        JLabel roomNumText =new JLabel("房号");
        roomNumText.setFont(new Font("黑体",Font.PLAIN,30));
        roomNumText.setBounds(30,130,180,60);
        PopOperationFrame.add(roomNumText);
        JTextField roomNum=new JTextField(room);
        roomNum.setFont(new Font("黑体",Font.PLAIN,18));
        roomNum.setBounds(210,140,360,40);
        PopOperationFrame.add(roomNum);//添加房号
        JLabel phoneNumText =new JLabel("电话");
        phoneNumText.setFont(new Font("黑体",Font.PLAIN,30));
        phoneNumText.setBounds(30,220,180,60);
        PopOperationFrame.add(phoneNumText);
        JTextField phoneNum=new JTextField();
        phoneNum.setFont(new Font("黑体",Font.PLAIN,18));
        phoneNum.setBounds(210,230,360,40);
        PopOperationFrame.add(phoneNum);//添加电话
        JButton Button=new JButton(operation);
        Button.setFont(new Font("黑体",Font.PLAIN,20));
        Button.setBounds(76,300,150,75);
        PopOperationFrame.add(Button);//添加操作按钮
        JButton ReturnButton=new JButton("返回");
        ReturnButton.setFont(new Font("黑体",Font.PLAIN,20));
        ReturnButton.setBounds(374,300,150,75);
        PopOperationFrame.add(ReturnButton);//添加返回
        Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText().equals("")||roomNum.getText().equals("")||phoneNum.getText().equals("")){
                    JOptionPane.showMessageDialog(PopOperationFrame, "输入不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    Connection con = new MyConnection().ConnectionDBS();//建立与数据库的连接
                    Statement stmt;
                    if (operation.equals("新增")) {
                        try {
                            ResultSet rs;
                            String sql = "select roomNum from owner" +
                                    " where roomNum = '" + roomNum.getText() + "'";//sql语句用来查找数据库中是否有管理员输入的房号
                            stmt = con.createStatement();
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {//如果有不允许新增
                                JOptionPane.showMessageDialog(PopOperationFrame, "该房间已有户主！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            } else {//如果没有允许注册并向数据库中插入
                                PreparedStatement pst;
                                String sql2 = "insert into owner values (?,?,?)";
                                pst = con.prepareStatement(sql2);
                                pst.setString(1, name.getText());
                                pst.setString(2, roomNum.getText());
                                pst.setString(3, phoneNum.getText());
                                pst.executeUpdate();
                                pst.close();//释放资源
                                JOptionPane.showMessageDialog(PopOperationFrame, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            }
                            rs.close();
                            stmt.close();//释放资源
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else if(operation.equals("修改")){
                        String sql2="update owner"+
                                " set name = '"+name.getText()+"' , phoneNum = '"+phoneNum.getText()+"'"+
                                " where roomNum = '"+roomNum.getText()+"'";
                        try {
                            stmt = con.createStatement();
                            stmt.executeUpdate(sql2);
                            JOptionPane.showMessageDialog(PopOperationFrame, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            stmt.close();
                            con.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        PopOperationFrame.setVisible(false);
                        new PopOperationFrame2("修改");
                    }
                }
            }
        });
        ReturnButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopOperationFrame.setVisible(false);
                if(operation.equals("新增")) {
                    new PopulationManagementFrame();
                }else if(operation.equals("修改")){
                    new PopOperationFrame2("修改");
                }
            }
        });
    }

    public void initJFrame(String operation){
        PopOperationFrame.setTitle(operation);
        PopOperationFrame.setSize(600,400);
        PopOperationFrame.setLocationRelativeTo(null);
        PopOperationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PopOperationFrame.setResizable(false);
        PopOperationFrame.setLayout(null);
    }
}
