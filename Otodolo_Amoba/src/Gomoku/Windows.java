package Gomoku;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

class Windows extends JDialog implements ActionListener, KeyListener, Interface {

    private final Object[] Other;
    private final String[] Names;

    protected Windows (JRootPane tulajdonos, boolean modal, String s) {
        Other = new Object[12];
        Names = new String[] {"Oszlopok", "", "Sorok", "", "Nehezseg", "Alap", "OK", "Megse", "Torol", "OK", "OK", "Anonymus"};
        for (int i = 0; i < Other.length; i++) {
            if (i < 6 || i == 11) {
                if (i % 2 == 0) {
                    Other[i] = new JLabel (Names[i]);
                }
                else {
                    Other[i] = new JTextField (Names[i]);
                }
            }
            else {
                Other[i] = new JButton (Names[i]);
            }
        }
        setResizable (false);
        setTitle (s);
        setVisible (true);
        pn0.removeAll ();
        pn3.removeAll ();
        pn4.removeAll ();
        switch (s) {
            case "Add meg a neved":
                setBounds (200, 200, 235, 125);
                pn0.add (new JLabel (AmobaStart.getAmoba ().getPlayerLevel () + " szinten nyertél."));
                pn3.add (new JLabel ("Írd be a neved: "));
                ((JTextField) Other[11]).setSelectionStart (0);
                ((JTextField) Other[11]).setSelectionEnd (((JTextField) Other[11]).getText ().length ());
                pn3.add ((JTextField) Other[11]);
                ((JTextField) Other[11]).addActionListener (Windows.this);
                pn4.add ((JTextField) Other[10]);
                add (pn0, BorderLayout.PAGE_START);
                add (pn3, BorderLayout.CENTER);
                add (pn4, BorderLayout.PAGE_END);
                break;
            case "Egyéni":
                setBounds (200, 200, 175, 100);
                setLayout (new GridLayout (4, 2));
                for (int i = 0; i < 8; i++) {
                    add ((Component) Other[i]);
                    if (i > 5) {
                        ((JButton) Other[i]).addActionListener (Windows.this);
                    }
                }
                break;
            case "Legjobbak":
                setBounds (200, 200, 200, 125);
                AmobaStart.getLogic ().getLPlayersName ().setFont (new Font (Font.MONOSPACED, Font.PLAIN, 12));
                pn3.add (AmobaStart.getLogic ().getLPlayersName ());
                ((JButton) (Other[9])).addActionListener (Windows.this);
                ((JButton) (Other[8])).addActionListener (Windows.this);
                pn4.add ((JButton) Other[9]);
                pn4.add ((JButton) Other[8]);
                add (pn3, BorderLayout.NORTH);
                add (pn4, BorderLayout.SOUTH);
                break;
            case "Dontetlen":
                setBounds (200, 200, 175, 100);
                setLayout (new GridLayout (1, 1));
                add (new Label (s));
                addKeyListener (Windows.this);
                break;
            default://Valaki nyert
                setBounds (200, 200, 175, 100);
                setLayout (new GridLayout (1, 1));
                add (new Label (s));
                addKeyListener (Windows.this);
                break;
        }
    }

    @Override
    public void keyPressed (KeyEvent kl) {
        dispose ();
    }

    @Override
    public void actionPerformed (ActionEvent al) {
        if (al.getSource ().equals (Other[6])) {//btCustomOK
            try {
                byte row = (byte) (Byte.parseByte (((JTextComponent) Other[3]).getText ()) + 4);
                byte col = (byte) (Byte.parseByte (((JTextComponent) Other[1]).getText ()) + 4);
                if (row < 7 || col < 7) {
                    JOptionPane.showMessageDialog (rootPane, "Nagyobb értékeket írjon be. Min. 3X3", "Érvénytelen számok", -1);
                }
                else if (row < 21 && col < 21) {
                    String s = "";
                    if (row == 7) {
                        AmobaStart.getAmoba ().imgO = (AmobaStart.getAmoba ().imgTomb[3]);
                        AmobaStart.getAmoba ().imgX = (AmobaStart.getAmoba ().imgTomb[7]);
//          s=strLevelTomb[0];//Easy
                    }
                    else if (row < 13) {
                        AmobaStart.getAmoba ().imgO = (AmobaStart.getAmoba ().imgTomb[2]);
                        AmobaStart.getAmoba ().imgX = (AmobaStart.getAmoba ().imgTomb[6]);
//          s=strLevelTomb[1];//Medium
                    }
                    else if (row < 17) {
                        AmobaStart.getAmoba ().imgO = (AmobaStart.getAmoba ().imgTomb[1]);
                        AmobaStart.getAmoba ().imgX = (AmobaStart.getAmoba ().imgTomb[5]);
//          s=strLevelTomb[2];//Hard
                    }
                    else {
                        AmobaStart.getAmoba ().imgO = (AmobaStart.getAmoba ().imgTomb[0]);
                        AmobaStart.getAmoba ().imgX = (AmobaStart.getAmoba ().imgTomb[4]);
//          s=strLevelTomb[3];//Custom
                    }
                    AmobaStart.getAmoba ().newgame (row, col, s);
                    dispose ();
                }
                else {
                    JOptionPane.showMessageDialog (rootPane, "Kisebb értékeket írjon be. Max. 16X16", "Érvénytelen számok", -1);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog (rootPane, "Számokat írjon be.", "NumberFormetException", -1);
            }
        }
        else if (al.getSource ().equals (Other[7]) || al.getSource ().equals (Other[9])) {//btOK/Megse
            dispose ();
        }
        else if (al.getSource ().equals (Other[10])) {//btHightScoreOK
            String name = ((JTextField) Other[11]).getText ().trim ();
            AmobaStart.getLogic ().setPlayerName (name);
            byte b = (byte) name.length ();
            if (b > 0 == b < 11) {
                dispose ();
                try {
                    AmobaStart.getLogic ().ModifXMLFile (sLT[18]);
                    AmobaStart.getAmoba ().logicInit ();
                    Windows about = new Windows (rootPane, true, AmobaStart.getAmoba ().miTomb[8].getText ());
                } catch (TransformerConfigurationException ex) {
                } catch (TransformerException ex) {
                } catch (ParserConfigurationException | SAXException | IOException ex) {
                }
            }
            else {
                if (b == 0) {
                    JOptionPane.showMessageDialog (rootPane, "<html>Nem megfelelő névhosszúság,<br/>kérem töltse ki a név mezőt!</html>", "Név nincs megadva!", -1);
                }
                else {
                    JOptionPane.showMessageDialog (rootPane, "<html>Nem megfelelő névhosszúság,<br/>a név legyen 11 karakternél kevesebb!</html>", "Túl hosszú név!", -1);
                }
            }
        }
        else if (al.getSource ().equals (Other[8])) {//Torol
            AmobaStart.getLogic ().writeXMLFile (sLT[18]);
            AmobaStart.getAmoba ().logicInit ();
            dispose ();
            Windows about = new Windows (rootPane, true, AmobaStart.getAmoba ().miTomb[8].getText ());
        }
    }

    @Override
    public void keyTyped (KeyEvent kt) {
    }

    @Override
    public void keyReleased (KeyEvent kl) {
    }
}
