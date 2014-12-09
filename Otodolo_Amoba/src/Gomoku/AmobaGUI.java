package Gomoku;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class AmobaGUI extends JFrame implements ActionListener, Interface {

    private int time;
    private short stepNo;//Statisztikakhoz meg jo lehet. Legkevesebb lepesbol gyozelem, atlagos lepesszam/gyozelem stb.
    ImageIcon[] imgTomb;
    ImageIcon imgX, imgO;
    private String playerLevel;
    private JButton[][] btTomb;
    private final Timer stopper;
    private boolean xNext, AI, activeGame, noRandomStep;
    private byte rows, cols, playerID, lr, lc, pr, pc, aiR, aiC;
    private ArrayList<String> availableFields;
    private ArrayList<String> xPlayerSteps, oPlayerSteps;
    //Szukseges az osszes valtozo?

    AmobaGUI() {
        setResizable(false);
        mbMenuBar.add(mFile);
        mbMenuBar.add(mHelp);
        setJMenuBar(mbMenuBar);
        stopper = new Timer(0x3e8, AmobaGUI.this);//0x3e8=1000
        setDefaultCloseOperation(3);//EXIT_ON_CLOSE
        setTitle(String.format("%-82s", text));
        setBounds(windowsWidth - 0x12c, windowsHeight - 0x12c, 0x2a3, 0x28a);
        for (byte i = 0; i < miTomb.length; i++) {
            if (i < 5) {
                miTomb[6].add(miTomb[i]);
                if (i == 3) {
                    ((JMenu) (miTomb[2 * i])).addSeparator();
                }
            } else if (i < 8) {
                mFile.add(miTomb[i]);
                if (i == 6) {
                    mFile.addSeparator();
                }
            } else {
                mHelp.add(miTomb[i]);
            }
            miTomb[i].setToolTipText(sLT[19 + i]);
            miTomb[i].addActionListener(AmobaGUI.this);
        }
        imgTomb = new ImageIcon[8];
        for (byte i = 0; i < imgTomb.length; i++) {
            imgTomb[i] = new ImageIcon(sLT[16] + i + sLT[17]);
        }
        rows = 7;
        cols = 7;
        imgO = imgTomb[3];
        imgX = imgTomb[7];
        ((AbstractButton) (comp[5])).addActionListener(AmobaGUI.this);
        ((AbstractButton) (comp[3])).addActionListener(AmobaGUI.this);
        ((AbstractButton) (comp[1])).addActionListener(AmobaGUI.this);
        newgame(rows, cols, sLT[0]);
        setVisible(true);
    }

    void newgame(byte r, byte c, String s) {
        lr = 0;
        lc = 0;
        pr = 0;
        pc = 0;
        aiR = 0;
        aiC = 0;
        rows = r;
        cols = c;
        time = 0;
        stepNo = 0;

        AI = true;
        xNext = true;
        activeGame = true;
        noRandomStep = true;
        xPlayerSteps = new ArrayList<>((rows - 4) * (cols - 4));
        oPlayerSteps = new ArrayList<>((rows - 4) * (cols - 4));//Osszes mezo/2 azert nem jo, mert adtam lehetoseget ra, hogy ez kijatszhato legyen. Lasd actionPerformd for ciklusanak if feltetele!
        availableFields = new ArrayList<>((rows - 4) * (cols - 4));

        for (int i = 0; i < rows - 4; i++) {
            for (int j = 0; j < cols - 4; j++) {
                availableFields.add((rows - 4) * i + j, "" + ((rows - 4) * i + j));
            }
        }
        ((JLabel) (comp[4])).setText(0 + sLT[15]);
        comp[3].setEnabled(true);
        playerLevel = s;
        pn1.removeAll();
        pn2.removeAll();
        pn1.setLayout(new GridLayout(r - 4, c - 4));
        pn2.setLayout(new GridLayout(0, 1));
        for (JComponent o : comp) {
            pn2.add(o);
        }
        btTomb = new JButton[r][c];
        short rc = (short) (r * c);
        for (byte i = 0; i < r; i++) {
            for (byte j = 0; j < c; j++) {
                if (rc > 0) {
                    btTomb[i][j] = new JButton();
                    if (i >= 2 && j >= 2 && i < r - 2 && j < c - 2) {
                        btTomb[i][j].addActionListener(AmobaGUI.this);
                        pn1.add(btTomb[i][j]);
                    } else {
                        btTomb[i][j].setEnabled(false);
                    }
                    rc--;
                }
            }
        }
        add(pn1);
        add(pn2, BorderLayout.EAST);

        stopperStep();
        stopper.restart();
    }

    private boolean isEnabled(int r, int c) {
        return btTomb[r][c].isEnabled() && btTomb[r][c].getIcon() == null;
    }

    private boolean isIconEqual(int r, int c, int xr, int xc) {
        return btTomb[r][c].getIcon().equals(btTomb[xr][xc].getIcon());
    }

    private void progLogic() {
        aiR = 0;
        aiC = 0;
        if (rows == 7 && cols == 7) {//Tic Tac Toe
            if (lr == 3 && lc == 3) {
                if (stepNo == 1) {
//        Középen történő kezdésre valamelyik sarokba rak
                    aiR = (byte) (lr + ((1 == (byte) (Math.random() * 2)) ? -1 : 1));
                    aiC = (byte) (lc + ((1 == (byte) (Math.random() * 2)) ? -1 : 1));
                } else {
                    calcStep(pr,pc);//Tamadas
                    if (noRandomStep) {
                        calcStep(lr,lc);//Vedekezes
                    }
                }
            } else if (isEnabled((byte) 3, (byte) 3)) {
// Ha nem kozepen kezdtem, a gep kozepre rak
                aiR = 3;
                aiC = 3;
            } else {
// Ha egy lepesbol tud nyerni, minden esetben nyer, ugyan ez vedekezesre
                calcStep(pr,pc);
                if (noRandomStep) {
                    calcStep(lr,lc);
                }
            }
            if (aiR == 0 && aiC == 0) {
                Random r = new Random();
                int a = Integer.parseInt(availableFields.get(r.nextInt(availableFields.size())));
                aiR = (byte) (a / 3 + 2);
                aiC = (byte) (a % 3 + 2);
                //////////////////////////////////////////////////////////
                ////Ez a random miatt verheto a gep///////////////////////
                //////////////////////////////////////////////////////////
            }
            oPlayerSteps.add(0, "" + ((rows - 4) * (aiR - 2) + aiC - 2));
            //
            doStep(aiR, aiC);
        } else if (rows > 9 && cols > 9) {//Nincs kidolgozva tetszoleges jatekterre a lepesi logika
//            Random r = new Random();
//            int a = Integer.parseInt(availableFields.get(r.nextInt(availableFields.size())));
//            aiR = (byte) (a / 3);
//            aiC = (byte) (a % 3);
//            doStep(aiR, aiC);
//            else {
// Ha egy lepesbol tud nyerni, minden esetben nyer, ugyan ez vedekezesre
                calcStep(pr,pc);
                if (noRandomStep) {
                    calcStep(lr,lc);
                }
//            }
            if (aiR == 0 && aiC == 0) {
                Random r = new Random();
                int a = Integer.parseInt(availableFields.get(r.nextInt(availableFields.size())));
                aiR = (byte) (a / 3 + 2);
                aiC = (byte) (a % 3 + 2);
                //////////////////////////////////////////////////////////
                ////Ez a random miatt verheto a gep///////////////////////
                //////////////////////////////////////////////////////////
            }
            oPlayerSteps.add(0, "" + ((rows - 4) * (aiR - 2) + aiC - 2));
            //
            doStep(aiR, aiC);
        }
    }

    public void calcStep(byte a, byte b) {
        noRandomStep = true;
        for (byte row = -2; row <= 2 && noRandomStep; row++) {
            for (byte col = -2; col <= 2 && noRandomStep; col++) {
                if ((row == 0 && col == 0) || isEnabled(a + row, b + col) || !isIconEqual(a, b, a + row, b + col)) {
                    continue;
                    //nem viszgaljuk azokat a mezokat ahova nem lehet ervenyesen lepni
                }
                byte m;
                if (row == 0) {//azonos sorban, vizszintesen van azonos
                    for (m = -2; m <= 2 && noRandomStep; m++) {
                        if (b + m < 2 || b + m > 4 || m == 0 || !isEnabled(a, b + m)) {
                            continue;
                        }
                        aiR = a;
                        aiC = (byte) (b + m);
                        noRandomStep = false;
                    }
                } else if (col == 0) {//azonos oszlopban, fuggolegesen van azonos
                    for (m = -2; m <= 2 && noRandomStep; m++) {
                        if (a + m < 2 || a + m > 4 || m == 0 || !isEnabled(a + m, b)) {
                            continue;
                        }
                        aiR = (byte) (a + m);
                        aiC = b;
                        noRandomStep = false;
                    }
                } else if (row == col) {//azonos sor,oszlop index, foatlo
                    for (m = -2; m <= 2 && noRandomStep; m++) {
                        if (b + m < 2 || b + m > 4 || m == 0 || !isEnabled(a + m, b + m)) {//Feltetel lehet hogy nem megfelelo! a nincs vizsgalva
                            continue;
                        }
                        aiR = (byte) (a + m);
                        aiC = (byte) (b + m);
                        noRandomStep = false;
                    }
                } else if (row == -col) {////ellentetes sor,oszlop index, mellekatlo
                    for (m = -2; m <= 2 && noRandomStep; m++) {
                        if (m == 0 || !isEnabled(a + m, b - m)) {//Elegendo szigoru a feltetel?
                            continue;
                        }
                        aiR = (byte) (a + m);
                        aiC = (byte) (b - m);
                        noRandomStep = false;
                    }
                } else {
                    //Nincs kidolgozva logika
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stopper) {
            stopperStep();
            if ((time - 1) % 0xa == 0) {//hexadecimal, 0xa = 10
                ((JLabel) (comp[4])).setText(time / 0xa + sLT[15]);
            }
        } else {
            for (byte i = 2; i < rows - 2; i++) {
                for (byte j = 2; j < cols - 2; j++) {
                    if (e.getSource() == btTomb[i][j] //                            && isEnabled(i, j)
                            ) {//A masodik feltetel elhagyasa elonyt enged adni az ellenfelnek! (Tobbet is rakhat mint egy)
                        xPlayerSteps.add(0, "" + ((rows - 4) * (i - 2) + j - 2));
                        doStep(i, j);
                        if (AI) {
                            progLogic();//Ha kommentes, a gep nem jatszik
                        }
                    }
                }
            }
            if (e.getSource() == miTomb[5]) {//Vissza
                back();
            } else if (e.getSource() == comp[1]) {//Restart
                newgame(rows, cols, playerLevel);
            } else if (e.getSource() == miTomb[2]) {//Medium
                imgO = imgTomb[1];
                imgX = imgTomb[5];
                newgame((byte) 16, (byte) 16, sLT[2]);
            } else if (e.getSource() == miTomb[1]) {//Easy
                imgO = imgTomb[2];
                imgX = imgTomb[6];
                newgame((byte) 14, (byte) 14, sLT[1]);
            } else if (e.getSource() == miTomb[3]) {//Hard
                imgO = imgTomb[0];
                imgX = imgTomb[4];
                newgame((byte) 20, (byte) 20, sLT[3]);
            } else if (e.getSource() == miTomb[0]) {//Tic-Tac-Toe
                imgO = imgTomb[3];
                imgX = imgTomb[7];
                newgame((byte) 7, (byte) 7, sLT[0]);
            } else if (e.getSource() == miTomb[4]) {//Custom
                new Windows(rootPane, true, miTomb[4].getText());
            } else if (e.getSource() == miTomb[8]) {//HightScore
                logicInit();
                Windows about = new Windows(rootPane, true, miTomb[8].getText()
                );
            } else if (e.getSource() == miTomb[9]) {//About
                Windows about = new Windows(rootPane, true, miTomb[9].getText());
            } else if (e.getSource() == comp[5] || e.getSource() == miTomb[7]) {
                System.exit(3);
            } else if (e.getSource() == comp[3]) {//Felad
                result("Vesztettél");
            }
        }
    }

    private void back() {//Ezt fontos lenne kijavitani. a listakbol kene szedni az elozo lepeseket
        btTomb[lr][lc].setIcon(null);
        btTomb[pr][pc].setIcon(null);
        btTomb[lr][lc].setBackground(null);
        btTomb[pr][pc].setBackground(null);
        availableFields.add(0, "" + (3 * (lr - 2) + lc - 2));
        availableFields.add(0, "" + (3 * (pr - 2) + pc - 2));
        btTomb[lr][lc].setIcon(xNext ? imgO : imgX);
        btTomb[lr][lc].setBackground(Color.RED);
        System.out.println("Igy jo lenne?" + availableFields);
    }

    private void stopperStep() {
        byte hr = (byte) (time / 3600);
        short min = (short) (time % 3600 / 60);
        byte sec = (byte) (time % 60);
        ((JLabel) (comp[0])).setText((hr == 0 ? "" : ((hr < 10 ? "0" : "") + hr + ":")) + (min < 10 ? "0" : "") + min + ":" + (sec < 10 ? "0" : "") + sec);
        time++;
    }

    private void doStep(byte i, byte j) {
        stepNo++;
        xNext = !xNext;
        ((JLabel) (comp[2])).setText((xNext ? "X" : "O") + " kovetkezik");

        System.out.println("xPlayerSteps= "+xPlayerSteps+", oPlayerSteps= "+oPlayerSteps);

        if (!xPlayerSteps.isEmpty()) {
            int b = Byte.parseByte(xPlayerSteps.get(0));
            pr = (byte) (b / 3 + 2);
            pc = (byte) (b % 3 + 2);            
        }

        btTomb[i][j].setIcon(xNext ? imgO : imgX);
        btTomb[i][j].setBackground(Color.RED);
        btTomb[lr][lc].setBackground(null);
        availableFields.remove("" + (3 * (i - 2) + j - 2));

        System.out.println("pr= "+pr+", pc= "+pc);

        pr = lr;
        pc = lc;
        lr = i;
        lc = j;
        
        System.out.println("lr= "+lr+", lc= "+lc);
        winChecker();
    }

    private void winChecker() {
        for (byte m = 2; m < rows - 2; m++) {
            for (byte n = 2; n < cols - 2; n++) {
                if (btTomb[m][n].getIcon() != null) {
                    if (rows == 7 && cols == 7) {
                        if (btTomb[m][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m][n - 1].getIcon() == btTomb[m][n].getIcon()
                                || btTomb[m + 1][n].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n].getIcon() == btTomb[m][n].getIcon()
                                || btTomb[m + 1][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n - 1].getIcon() == btTomb[m][n].getIcon()
                                || btTomb[m + 1][n - 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n + 1].getIcon() == btTomb[m][n].getIcon()) {
                            result("");
                        }
                    } else if (rows > 9 && cols > 9) {
                        if (btTomb[m][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m][n - 1].getIcon() == btTomb[m][n].getIcon()
                                && btTomb[m][n + 2].getIcon() == btTomb[m][n].getIcon() && btTomb[m][n - 2].getIcon() == btTomb[m][n].getIcon()
                                || btTomb[m + 1][n].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n].getIcon() == btTomb[m][n].getIcon()
                                && btTomb[m + 2][n].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 2][n].getIcon() == btTomb[m][n].getIcon()
                                || btTomb[m + 1][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n - 1].getIcon() == btTomb[m][n].getIcon()
                                && btTomb[m + 2][n + 2].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 2][n - 2].getIcon() == btTomb[m][n].getIcon()
                                || btTomb[m + 1][n - 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n + 1].getIcon() == btTomb[m][n].getIcon()
                                && btTomb[m + 2][n - 2].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 2][n + 2].getIcon() == btTomb[m][n].getIcon()) {

                            result("");
                        }
                    }
                }
            }
        }
        if (activeGame && availableFields.isEmpty()) {
            result("Dontetlen");
        }
    }

    private void result(String a) {
        stopper.stop();
        comp[3].setEnabled(false);
        ((JLabel) (comp[2])).setText(a.isEmpty() ? (xNext ? "Vesztettel." : "Nyertel.") : a);
        new Windows(rootPane, xNext, !a.equals("Dontetlen") ? ((xNext ? "O" : "X") + " nyert.") : a);
        for (byte k = 4 / 2; k < rows - 4 / 2; k++) {
            for (byte l = 4 / 2; l < cols - 4 / 2; l++) {
                btTomb[k][l].setEnabled(false);
            }
        }
        AI = false;
        activeGame = false;
    }

    void logicInit() {
        AmobaStart.getLogic().loadXMLFile(sLT[18]);
        AmobaStart.getLogic().PlayersName();
    }

    String getPlayerLevel() {
        return playerLevel;
    }

    int getTime() {
        return time;
    }

    short getID() {
        return playerID;
    }

}
