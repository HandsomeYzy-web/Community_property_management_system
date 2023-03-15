package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class addPayFrame {
    JFrame addPayFrame=new JFrame();
    public addPayFrame(){
        initJFrame();
        initView();
        addPayFrame.setVisible(true);
    }

    private void initView() {
        JLabel OperationText=new JLabel("请输入要新增的缴费信息!");
        OperationText.setFont(new Font("黑体",Font.PLAIN,30));
        OperationText.setBounds(150,0,420,30);
        addPayFrame.add(OperationText);
        JLabel roomNumText=new JLabel("房号");
        roomNumText.setFont(new Font("黑体",Font.PLAIN,30));
        roomNumText.setBounds(30,50,180,60);
        addPayFrame.add(roomNumText);
        JTextField roomNum=new JTextField();
        roomNum.setFont(new Font("黑体",Font.PLAIN,18));
        roomNum.setBounds(210,60,360,40);
        addPayFrame.add(roomNum);//添加户主
        JLabel arrearsText =new JLabel("欠款");
        arrearsText.setFont(new Font("黑体",Font.PLAIN,30));
        arrearsText.setBounds(30,130,180,60);
        addPayFrame.add(arrearsText);
        JTextField arrears=new JTextField();
        arrears.setFont(new Font("黑体",Font.PLAIN,18));
        arrears.setBounds(210,140,360,40);
        addPayFrame.add(arrears);//添加房号
        JLabel arrearsProjectText =new JLabel("项目");
        arrearsProjectText.setFont(new Font("黑体",Font.PLAIN,30));
        arrearsProjectText.setBounds(30,220,180,60);
        addPayFrame.add(arrearsProjectText);
        JTextField arrearsProject =new JTextField();
        arrearsProject.setFont(new Font("黑体",Font.PLAIN,18));
        arrearsProject.setBounds(210,230,360,40);
        addPayFrame.add(arrearsProject);//添加电话
        JButton addButton=new JButton("新增");
        addButton.setFont(new Font("黑体",Font.PLAIN,20));
        addButton.setBounds(76,300,150,75);
        addPayFrame.add(addButton);//添加操作按钮
        JButton ReturnButton=new JButton("返回");
        ReturnButton.setFont(new Font("黑体",Font.PLAIN,20));
        ReturnButton.setBounds(374,300,150,75);
        addPayFrame.add(ReturnButton);//添加返回
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(roomNum.getText().equals("")||arrears.getText().equals("")||arrearsProject.getText().equals("")){
                    JOptionPane.showMessageDialog(addPayFrame,"输入为空！","提示",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Connection con = new MyConnection().ConnectionDBS();//建立与数据库的连接
                    Statement stmt;
                    ResultSet rs;
                    PreparedStatement pst;
                    String sql="select roomNum from owner where roomNum='"+roomNum.getText()+"'";
                    String sql2="insert into ownerpay values(?,?,?)";
                    try {
                        stmt=con.createStatement();
                        rs=stmt.executeQuery(sql);
                        if(rs.next()) {
                            pst = con.prepareStatement(sql2);
                            pst.setString(1, roomNum.getText());
                            pst.setDouble(2, Double.parseDouble(arrears.getText()));
                            pst.setString(3, arrearsProject.getText());
                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(addPayFrame, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            pst.close();
                            con.close();
                        }else{
                            JOptionPane.showMessageDialog(addPayFrame, "没有该房主！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        ReturnButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPayFrame.setVisible(false);
                new PaymentManagementFrame();
            }
        });
    }

    private void initJFrame() {
        addPayFrame.setTitle("新增缴费");
        addPayFrame.setSize(600,400);
        addPayFrame.setLocationRelativeTo(null);
        addPayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addPayFrame.setResizable(false);
        addPayFrame.setLayout(null);
    }
}
