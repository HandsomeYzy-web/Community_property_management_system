package LoginRegister;
import Connection.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
//注册界面
public class RegisterFrame {
    JFrame RegisterFrame=new JFrame();
    public RegisterFrame(){
        initJFrame();//初始化界面
        initView();//添加按钮
        RegisterFrame.setVisible(true);//是界面可视，放在最后可以避免图像问题
    }

    private void initView() {
        JLabel UsernameText=new JLabel("用户名");
        UsernameText.setFont(new Font("黑体",Font.PLAIN,30));
        UsernameText.setBounds(200,140,200,100);
        RegisterFrame.add(UsernameText);
        JTextField UserName=new JTextField();
        UserName.setFont(new Font("黑体",Font.PLAIN,18));
        UserName.setBounds(330,170,280,40);
        RegisterFrame.add(UserName);//添加用户名
        JLabel passwordText =new JLabel("密码");
        passwordText.setFont(new Font("黑体",Font.PLAIN,30));
        passwordText.setBounds(200,200,200,100);
        RegisterFrame.add(passwordText);
        JPasswordField password=new JPasswordField();
        password.setBounds(330,230,280,40);
        RegisterFrame.add(password);//添加密码
        JButton RegisterButton=new JButton("注册");
        RegisterButton.setFont(new Font("黑体",Font.PLAIN,20));
        RegisterButton.setBounds(400,330,100,50);
        RegisterFrame.add(RegisterButton);//添加注册
        RegisterButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String psw=new String(password.getPassword());
                if(UserName.getText().equals("")&&psw.equals("")){//判断是否输入用户名与密码，给出相应提示
                    JOptionPane.showMessageDialog(RegisterFrame,"用户名和密码不能为空！","提示",JOptionPane.INFORMATION_MESSAGE);
                }else if(UserName.getText().equals("")){
                    JOptionPane.showMessageDialog(RegisterFrame,"用户名不能为空！","提示",JOptionPane.INFORMATION_MESSAGE);
                }else if(psw.equals("")){
                    JOptionPane.showMessageDialog(RegisterFrame,"密码不能为空！","提示",JOptionPane.INFORMATION_MESSAGE);
                }else {//如果输入的用户名和密码都不为空
                    Connection con = new MyConnection().ConnectionDBS();
                    Statement stmt;
                    ResultSet rs;//建立与数据库的连接
                    String sql = "select UserName,password from administrator" +
                            " where UserName = '" + UserName.getText() + "'";//sql语句用来查找数据库中是否有管理员输入的用户名
                    try {
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {//如果有不允许注册
                            JOptionPane.showMessageDialog(RegisterFrame, "用户名已存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        } else {//如果没有允许注册并向数据库中插入注册的用户名与密码
                            PreparedStatement pst;
                            String sql2 = "insert into administrator values (?,?)";
                            pst = con.prepareStatement(sql2);
                            pst.setString(1, UserName.getText());
                            pst.setString(2, psw);
                            pst.executeUpdate();
                            pst.close();//释放资源
                            JOptionPane.showMessageDialog(RegisterFrame, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            RegisterFrame.setVisible(false);
                            new LoginRegisterFrame();//跳转到登录界面
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
    }

    private void initJFrame() {
        RegisterFrame.setTitle("注册");
        RegisterFrame.setSize(900,507);
        RegisterFrame.setLocationRelativeTo(null);
        RegisterFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        RegisterFrame.setResizable(false);
        RegisterFrame.setLayout(null);
    }
}
