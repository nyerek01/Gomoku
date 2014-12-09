package Gomoku;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

  JLabel lbUsrNm;
  JLabel lbPsw;
  JTextField tfUsrNm;
  JTextField tfPsw;
  JButton btOK;
  JButton btCancel;
  JPanel pn;
  String[] arg;
  JPanel pn1;

  public static void main(String[] args) {
    AmobaStart.AmobaStart();
//    new Login(args);
//        if ( checkLogin() ) {
//            AmobaStart.main( args );
//        }
  }

  public Login(String[] args) {
    arg = args;
    setResizable(true);
    setDefaultCloseOperation(3);
    setTitle("Login");
    setBounds(200, 200, 325, 125);
    setLayout(new GridLayout(3, 2));
    lbUsrNm = new JLabel("Name: ");
    lbPsw = new JLabel("Password: ");
    tfUsrNm = new JTextField("");
    tfPsw = new JPasswordField("");
    btOK = new JButton("OK");
    btCancel = new JButton("Cancel");
    pn1 = new JPanel();
    btOK.addActionListener(this);
    btCancel.addActionListener(this);
    pn1.add(btOK);
    pn1.add(btCancel);
    add(lbUsrNm);
    add(tfUsrNm);
    add(lbPsw);
    add(tfPsw);
    add(pn1);
    setVisible(true);
  }

  public static boolean checkLogin() {
    {
      return false;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btOK) {
      System.out.println("Login Checkin...");
      if ("Dave".equals(tfUsrNm.getText()) && "Dave".equals(tfPsw.getText())) {
        System.out.println(tfUsrNm.getText());
        this.dispose();
        AmobaStart.AmobaStart();
        System.out.println("Sikeres Belepes");
      } else {
          System.out.println("Login Failed. Access Denied.");
      }
    } else if (e.getSource() == btCancel) {
      System.exit(0);
    }
  }
}
