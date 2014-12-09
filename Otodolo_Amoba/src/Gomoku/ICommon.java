package Gomoku;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

interface ICommon
{

    final String[] sLT = new String[] {
        "Tic_Tac_Toe", "Könnyű", "Közepes", "Nehéz", "Egyéni", "Fájl", "Új játék", "Súgó", "Kilép", "Legjobbak", "Programról", "Felad", "Megold", "X kovetkezik",
        "", " pont.", "./src/img/", ".tiff.png", "./src/xml/x.xml", "3X3-as",
        "Legkönyebb szint (Easy-nek számít a 8x8-as tábla).",
        "Közepes szint (Medium-nak számít a 12x12-es tábla).",
        "Legnehezebb szint (Hard-nak számít a 16x16-os tábla).",
        "Új játék a megadott -helyes- paraméterekkel.",
        "Játék befejezése, játszma elvesztése.",
        "Új játék, az aktuális sor és oszlop számmal.",
        "Program leállítása",
        "Toplista",
        "Rövid leírás a programról.",
        "Amőba Készítette: Dóra Dávid"
    };
    final JMenuItem[] miTomb = new JMenuItem[] {
        new JMenuItem( sLT[0] ), new JMenuItem( sLT[1] ), new JMenuItem( sLT[2] ), new JMenuItem( sLT[3] ), new JMenuItem( sLT[4] ), new JMenuItem( sLT[12] ),
        new JMenu( sLT[6] ), new JMenuItem( sLT[8] ), new JMenuItem( sLT[9] ), new JMenuItem( sLT[10] ) };
    final JPanel pn1 = new JPanel(), pn2 = new JPanel(), pn3 = new JPanel(), pn4 = new JPanel(), pn0 = new JPanel();
}
