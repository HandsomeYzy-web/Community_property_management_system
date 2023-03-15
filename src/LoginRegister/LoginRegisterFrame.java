package LoginRegister;
import Connection.*;
import java.sql.Connection;
import MainFunction.MainFunctionFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginRegisterFrame {
    JFrame LoginRegisterFrame=new JFrame();
    public LoginRegisterFrame(){
        initJFrame();//初始化界面
        initView();//添加按钮
        LoginRegisterFrame.setVisible(true);//是界面可视，放在最后可以避免图像问题
    }

    private void initView(){
        JLabel LoginRegisterText=new JLabel("社区物业管理系统");
        LoginRegisterText.setFont(new Font("黑体",Font.PLAIN,50));
        LoginRegisterText.setBounds(280,0,800,100);
        LoginRegisterFrame.add(LoginRegisterText);//添加标题
        JLabel UsernameText=new JLabel("用户名");
        UsernameText.setFont(new Font("黑体",Font.PLAIN,30));
        UsernameText.setBounds(200,140,200,100);
        LoginRegisterFrame.add(UsernameText);
        JTextField UserName=new JTextField();
        UserName.setFont(new Font("黑体",Font.PLAIN,18));
        UserName.setBounds(330,170,280,40);
        LoginRegisterFrame.add(UserName);//添加用户名
        JLabel passwordText =new JLabel("密码");
        passwordText.setFont(new Font("黑体",Font.PLAIN,30));
        passwordText.setBounds(200,200,200,100);
        LoginRegisterFrame.add(passwordText);
        JPasswordField password=new JPasswordField();
        password.setBounds(330,230,280,40);
        LoginRegisterFrame.add(password);//添加密码
        JButton LoginButton=new JButton("登录");
        LoginButton.setFont(new Font("黑体",Font.PLAIN,20));
        LoginButton.setBounds(300,330,100,50);
        LoginRegisterFrame.add(LoginButton);//添加登录
        JButton RegisterButton=new JButton("注册");
        RegisterButton.setFont(new Font("黑体",Font.PLAIN,20));
        RegisterButton.setBounds(500,330,100,50);
        LoginRegisterFrame.add(RegisterButton);//添加注册
        LoginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {//登录按钮事件
                String psw=new String(password.getPassword());
                if(UserName.getText().equals("")||psw.equals("")){
                    JOptionPane.showMessageDialog(LoginRegisterFrame,"用户名和密码不能为空！","提示",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    Connection con = new MyConnection().ConnectionDBS();
                    Statement stmt;
                    ResultSet rs;//创建与数据库的连接
                    String sql = "select UserName,password from administrator" +
                            " where UserName = '" + UserName.getText() + "'";//sql语句用来查找数据库中是否有管理员输入的用户名
                    try {
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(sql);
                        if (!rs.next()) {//数据库中未查找到管理员输入的用户名，不允许登录
                            JOptionPane.showMessageDialog(LoginRegisterFrame, "用户名或密码错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        } else {//检查密码是否正确
                            if (rs.getString(2).equals(psw)) {//如果正确进入系统
                                LoginRegisterFrame.setVisible(false);
                                new MainFunctionFrame();//跳转到系统功能界面
                            } else {//密码不正确不允许进入系统
                                JOptionPane.showMessageDialog(LoginRegisterFrame, "用户名或密码错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            }
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
        RegisterButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {//注册按钮时间用于跳转到注册界面
                LoginRegisterFrame.setVisible(false);
                new RegisterFrame();
            }
        });
    }

    public void initJFrame(){
        LoginRegisterFrame.setTitle("登录");
        LoginRegisterFrame.setSize(900,507);
        LoginRegisterFrame.setLocationRelativeTo(null);
        LoginRegisterFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LoginRegisterFrame.setResizable(false);
        LoginRegisterFrame.setLayout(null);
    }
}
