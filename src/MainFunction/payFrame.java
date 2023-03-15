package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class payFrame {
    JFrame payFrame=new JFrame();
    public payFrame(){
        initJFrame();
        initView();
        payFrame.setVisible(true);
    }

    private void initView() {
        JLabel OperationText =new JLabel("请输入要缴费的户主!");
        OperationText.setFont(new Font("黑体",Font.PLAIN,30));
        OperationText.setBounds(150,45,420,60);
        payFrame.add(OperationText);
        JLabel roomNumText =new JLabel("房号");
        roomNumText.setFont(new Font("黑体",Font.PLAIN,30));
        roomNumText.setBounds(30,195,180,60);
        payFrame.add(roomNumText);
        JTextField roomNum=new JTextField();
        roomNum.setFont(new Font("黑体",Font.PLAIN,18));
        roomNum.setBounds(210,205,360,40);
        payFrame.add(roomNum);//添加户主
        JButton Button=new JButton("查询");
        Button.setFont(new Font("黑体",Font.PLAIN,20));
        Button.setBounds(76,300,150,75);
        payFrame.add(Button);//添加操作按钮
        JButton ReturnButton=new JButton("返回");
        ReturnButton.setFont(new Font("黑体",Font.PLAIN,20));
        ReturnButton.setBounds(374,300,150,75);
        payFrame.add(ReturnButton);//添加返回
        Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(roomNum.getText().equals("")){
                    JOptionPane.showMessageDialog(payFrame, "输入不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Connection con = new MyConnection().ConnectionDBS();//建立与数据库的连接
                    Statement stmt;
                    ResultSet rs;
                    String sql="select arrears from ownerpay "+
                            "where roomNum = '"+roomNum.getText()+"'";
                    String sql2 = "delete from ownerpay" +
                            " where roomNum = '" + roomNum.getText() + "'";
                    double arrears=0;
                    try{
                        stmt=con.createStatement();
                        rs=stmt.executeQuery(sql);
                        while(rs.next()){
                            arrears+=rs.getDouble(1);
                        }
                        if(arrears==0){
                            JOptionPane.showMessageDialog(payFrame, "未查询到欠费信息！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            String message="一共欠费"+arrears+"元\r\n请输入缴费金额！";
                            double temp;
                            String s=JOptionPane.showInputDialog(payFrame,message,"缴费",JOptionPane.INFORMATION_MESSAGE);
                            if(s==null||s.equals("")){//顺序不能改变，如果选择取消则为空，不能进行字符串比较，若先进行比较，将会报错！
                                temp=0;
                            }else {
                                temp = Double.parseDouble(s);
                            }
                            if(temp==0){
                                JOptionPane.showMessageDialog(payFrame,"缴费失败！","缴费",JOptionPane.INFORMATION_MESSAGE);
                            } else if (temp<arrears){
                                JOptionPane.showMessageDialog(payFrame,"缴费失败！缴费金额不足！","缴费",JOptionPane.INFORMATION_MESSAGE);
                            } else if (temp==arrears) {
                                stmt.executeUpdate(sql2);
                                JOptionPane.showMessageDialog(payFrame,"缴费成功！","缴费",JOptionPane.INFORMATION_MESSAGE);
                            } else if (temp>arrears) {
                                stmt.executeUpdate(sql2);
                                JOptionPane.showMessageDialog(payFrame,"缴费成功！\r\n找零"+(temp-arrears)+"元！","缴费",JOptionPane.INFORMATION_MESSAGE);
                            }
                            rs.close();
                            stmt.close();
                            con.close();
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
                payFrame.setVisible(false);
                new PaymentManagementFrame();
            }
        });
    }
    private void initJFrame() {
        payFrame.setTitle("缴费");
        payFrame.setSize(600,400);
        payFrame.setLocationRelativeTo(null);
        payFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        payFrame.setResizable(false);
        payFrame.setLayout(null);
    }
}
