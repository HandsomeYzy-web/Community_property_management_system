package MainFunction;

import Connection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addMaintenanceFrame {
    JFrame addMaintenanceFrame=new JFrame();
    public addMaintenanceFrame(){
        initJFrame();
        initView();
        addMaintenanceFrame.setVisible(true);
    }

    private void initView() {
        JLabel OperationText=new JLabel("请输入要添加的信息!");
        OperationText.setFont(new Font("黑体",Font.PLAIN,30));
        OperationText.setBounds(150,0,420,30);
        addMaintenanceFrame.add(OperationText);
        JLabel itemText =new JLabel("报修项目");
        itemText.setFont(new Font("黑体",Font.PLAIN,30));
        itemText.setBounds(30,50,180,60);
        addMaintenanceFrame.add(itemText);
        JTextField item =new JTextField();
        item.setFont(new Font("黑体",Font.PLAIN,18));
        item.setBounds(210,60,360,40);
        addMaintenanceFrame.add(item);//添加户主
        JLabel roomNumText =new JLabel("房号");
        roomNumText.setFont(new Font("黑体",Font.PLAIN,30));
        roomNumText.setBounds(30,130,180,60);
        addMaintenanceFrame.add(roomNumText);
        JTextField roomNum=new JTextField();
        roomNum.setFont(new Font("黑体",Font.PLAIN,18));
        roomNum.setBounds(210,140,360,40);
        addMaintenanceFrame.add(roomNum);//添加房号
        JLabel timeText =new JLabel("预计上门时间");
        timeText.setFont(new Font("黑体",Font.PLAIN,30));
        timeText.setBounds(30,220,180,60);
        addMaintenanceFrame.add(timeText);
        JTextField time =new JTextField();
        time.setFont(new Font("黑体",Font.PLAIN,18));
        time.setBounds(210,230,360,40);
        addMaintenanceFrame.add(time);//添加预计上门时间
        JButton addButton =new JButton("添加");
        addButton.setFont(new Font("黑体",Font.PLAIN,20));
        addButton.setBounds(76,300,150,75);
        addMaintenanceFrame.add(addButton);//添加操作按钮
        JButton ReturnButton=new JButton("返回");
        ReturnButton.setFont(new Font("黑体",Font.PLAIN,20));
        ReturnButton.setBounds(374,300,150,75);
        addMaintenanceFrame.add(ReturnButton);//添加返回
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(item.getText().equals("")||roomNum.getText().equals("")||time.getText().equals("")){
                    JOptionPane.showMessageDialog(addMaintenanceFrame, "输入不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Connection con = new MyConnection().ConnectionDBS();//建立与数据库的连接
                    PreparedStatement pst;
                    String sql = "insert into ownermaintenance values (?,?,?)";
                    try {
                        pst = con.prepareStatement(sql);
                        pst.setString(1, item.getText());
                        pst.setString(2, roomNum.getText());
                        pst.setString(3, time.getText());
                        pst.executeUpdate();
                        pst.close();//释放资源
                        JOptionPane.showMessageDialog(addMaintenanceFrame, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        pst.close();
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
                addMaintenanceFrame.setVisible(false);
                new MaintenanceManagementFrame();
            }
        });
    }

    private void initJFrame() {
        addMaintenanceFrame.setTitle("新增报修");
        addMaintenanceFrame.setSize(600,400);
        addMaintenanceFrame.setLocationRelativeTo(null);
        addMaintenanceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMaintenanceFrame.setResizable(false);
        addMaintenanceFrame.setLayout(null);
    }
}
