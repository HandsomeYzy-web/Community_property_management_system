package MainFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
//系统主功能界面，用于选择要进行的操作
public class MainFunctionFrame {
    JFrame MainFunctionFrame=new JFrame();
    public MainFunctionFrame(){
        initJFrame();
        initView();
        MainFunctionFrame.setVisible(true);
    }

    private void initView() {
        JButton PopulationManagement=new JButton("社区人口管理");
        PopulationManagement.setFont(new Font("黑体",Font.PLAIN,20));
        PopulationManagement.setBounds(20,30,360,96);
        MainFunctionFrame.add(PopulationManagement);
        JButton PaymentManagement=new JButton("缴费管理");
        PaymentManagement.setFont(new Font("黑体",Font.PLAIN,20));
        PaymentManagement.setBounds(20,144,360,96);
        MainFunctionFrame.add(PaymentManagement);
        JButton WarrantyManagement=new JButton("报修管理");
        WarrantyManagement.setFont(new Font("黑体",Font.PLAIN,20));
        WarrantyManagement.setBounds(20,258,360,96);
        MainFunctionFrame.add(WarrantyManagement);
        JButton NotificationManagement=new JButton("通知管理");
        NotificationManagement.setFont(new Font("黑体",Font.PLAIN,20));
        NotificationManagement.setBounds(20,372,360,96);
        MainFunctionFrame.add(NotificationManagement);
        JButton ExitSystem=new JButton("退出系统");
        ExitSystem.setFont(new Font("黑体",Font.PLAIN,20));
        ExitSystem.setBounds(20,486,360,96);
        MainFunctionFrame.add(ExitSystem);
        PopulationManagement.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFunctionFrame.setVisible(false);
                new PopulationManagementFrame();//跳转到人口管理界面
            }
        });
        PaymentManagement.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFunctionFrame.setVisible(false);
                new PaymentManagementFrame();//跳转到缴费管理界面
            }
        });
        WarrantyManagement.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFunctionFrame.setVisible(false);
                new MaintenanceManagementFrame();//跳转到保修管理界面
            }
        });
        NotificationManagement.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFunctionFrame.setVisible(false);
                new noticeManagementFrame();//跳转到通知管理界面
            }
        });
        ExitSystem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void initJFrame() {
        MainFunctionFrame.setTitle("社区物业管理系统");
        MainFunctionFrame.setSize(400,630);
        MainFunctionFrame.setLocationRelativeTo(null);
        MainFunctionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainFunctionFrame.setResizable(false);
        MainFunctionFrame.setLayout(null);
    }
}
