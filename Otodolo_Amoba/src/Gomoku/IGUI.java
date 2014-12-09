package Gomoku;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public interface IGUI
{

    final Dimension pic = Toolkit.getDefaultToolkit().getScreenSize();
    final short windowsWidth = ( short ) ( pic.width / 2 ), windowsHeight = ( short ) ( pic.height / 2 );
//    final String text = "Amoba Készítette: Dóra Dávid";
    final JMenuBar mbMenuBar = new JMenuBar();
    final JMenu mFile = new JMenu( "Fájl" ), mHelp = new JMenu( "Súgó" );
    final JComponent[] comp = new JComponent[] { new JLabel(), new JButton( "Új Játék" ), new JLabel( "X következik" ), new JButton( "Felad" ), new JLabel(), new JButton( "Kilép" ) };
}
