package Gomoku;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

interface Interface
{

    final Dimension pic = Toolkit.getDefaultToolkit().getScreenSize();
    final short windowsWidth = ( short ) ( pic.width / 2 ), windowsHeight = ( short ) ( pic.height / 2 );
    final String[] sLT = new String[] {
        "Tic_Tac_Toe", "Könnyű", "Közepes", "Nehéz", "Egyéni", "Fájl", "Új játék", "Súgó", "Kilép", "Legjobbak", "Programról", "Felad", "Vissza", "X kovetkezik",//13
        "", " pont.", "./src/Gomoku/img/", ".tiff.png", "./src/Gomoku/xml/x.xml", "3X3-as",//19
        "Legkönyebb szint (Easy-nek számít a 8x8-as tábla).",
        "Közepes szint (Medium-nak számít a 12x12-es tábla).",
        "Legnehezebb szint (Hard-nak számít a 16x16-os tábla).",
        "Új játék a megadott -helyes- paraméterekkel.",
        "Elozo lepes visszavonasa.",
        "Új játék, az aktuális sor és oszlop számmal.",
        "Program leállítása",
        "Toplista",
        "Rövid leírás a programról."};//28
    final String text = "Amoba Készítette: Dóra Dávid";
    final JMenuBar mbMenuBar = new JMenuBar();
    final JMenu mFile = new JMenu( sLT[5] ),
            mHelp = new JMenu( sLT[7] );
    final JMenuItem[] miTomb = new JMenuItem[] {
        new JMenuItem( sLT[0] ), new JMenuItem( sLT[1] ), new JMenuItem( sLT[2] ), new JMenuItem( sLT[3] ), new JMenuItem( sLT[4] ), new JMenuItem( sLT[12] ),//5
        new JMenu( sLT[6] ), new JMenuItem( sLT[8] ), new JMenuItem( sLT[9] ), new JMenuItem( sLT[10] ) };//9
    final JPanel pn1 = new JPanel(), pn2 = new JPanel(), pn3 = new JPanel(), pn4 = new JPanel(), pn0 = new JPanel();
    final JComponent[] comp = new JComponent[] { new JLabel(), new JButton( sLT[6] ), new JLabel( sLT[13] ), new JButton( sLT[11] ), new JLabel(), new JButton( 
        sLT[8] ) };
    
}
