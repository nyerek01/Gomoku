package Gomoku;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class StepLogic extends JFrame implements Interface
{

    private short time;
    ImageIcon[] imgTomb;
    ImageIcon imgX, imgO;
    private String playerLevel;
    private JButton[][] btTomb;
    private Timer stopper;
    private boolean xNext, AI, activeGame;
    private byte stepNo, rows, cols, playerID, lastStepRowNo, lastStepColNo, prevStepRowNo, prevStepColNo;

//    @Override
//    public void actionPerformed( ActionEvent e )
//    {
//        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
//    }

    void newgame( byte r, byte c, String s )
    {
//        stopper = new Timer( 0x3e8, StepLogic.this );
        time = 0;
        stepNo = 0;
        lastStepRowNo = 0;
        lastStepColNo = 0;
        prevStepRowNo = 0;
        prevStepColNo = 0;
        AI = true;
        xNext = true;
        activeGame = true;
//        stopperStep();
//        stopper.restart();
        ( ( JLabel ) ( comp[4] ) ).setText( 0 + sLT[15] );
        comp[3].setEnabled( true );
        playerLevel = s;
        pn1.removeAll();
        pn2.removeAll();
        pn1.setLayout( new GridLayout( r - 4, c - 4 ) );
        pn2.setLayout( new GridLayout( 0, 1 ) );
        for ( JComponent o : comp ) {
            pn2.add( o );
        }
        rows = r;
        cols = c;
        btTomb = new JButton[ r ][ c ];
        int rc = r * c;
        for ( byte i = 0; i < r; i++ ) {
            for ( byte j = 0; j < c; j++ ) {
                if ( rc > 0 ) {
                    btTomb[i][j] = new JButton();
                    if ( i >= 4 / 2 && j >= 4 / 2 && i < r - 4 / 2 && j < c - 4 / 2 ) {
                        btTomb[i][j].addActionListener( AmobaStart.getAmoba() );
                        pn1.add( btTomb[i][j] );
                    }
                    else {
                        btTomb[i][j].setEnabled( false );
                    }
                    rc--;
                }
            }
        }
//        add( pn1 );
//        add( pn2, BorderLayout.EAST );
    }

    private void progLogic( byte x, byte y )
    {
        int i = 0, j = 0;
        if ( rows == 7 && cols == 7 ) {
            if ( lastStepRowNo == 3 && lastStepColNo == 3 ) {
                i = lastStepRowNo + ( ( 1 == ( int ) ( Math.random() * 2 ) ) ? -1 : 1 );
                j = lastStepColNo + ( ( 1 == ( int ) ( Math.random() * 2 ) ) ? -1 : 1 );
            }
            else if ( btTomb[3][3].isEnabled() && btTomb[3][3].getIcon() == null ) {
                i = 3;
                j = 3;
            }
            else if ( btTomb[prevStepRowNo][prevStepColNo + 1].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon()
                      || btTomb[prevStepRowNo][prevStepColNo - 1].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon() ) {
                System.out.println( "Attack: Horizontal: Melle" );
                if ( btTomb[prevStepRowNo][prevStepColNo + 1].isEnabled() && btTomb[prevStepRowNo][prevStepColNo + 1].getIcon() == null ) {
                    i = prevStepRowNo;
                    j = prevStepColNo + 1;
                }
                else if ( btTomb[prevStepRowNo][prevStepColNo + 2].isEnabled() && btTomb[prevStepRowNo][prevStepColNo + 2].getIcon() == null ) {
                    i = prevStepRowNo;
                    j = prevStepColNo + 2;
                }
                else if ( btTomb[prevStepRowNo][prevStepColNo - 1].isEnabled() && btTomb[prevStepRowNo][prevStepColNo - 1].getIcon() == null ) {
                    i = prevStepRowNo;
                    j = prevStepColNo - 1;
                }
                else if ( btTomb[prevStepRowNo][prevStepColNo - 2].isEnabled() && btTomb[prevStepRowNo][prevStepColNo - 2].getIcon() == null ) {
                    i = prevStepRowNo;
                    j = prevStepColNo - 2;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( btTomb[prevStepRowNo + 1][prevStepColNo].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon()
                      || btTomb[prevStepRowNo - 1][prevStepColNo].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon() ) {
                System.out.println( "Attack: Vertical: Melle" );
                if ( btTomb[prevStepRowNo + 1][prevStepColNo].isEnabled() && btTomb[prevStepRowNo + 1][prevStepColNo].getIcon() == null ) {
                    i = prevStepRowNo + 1;
                    j = prevStepColNo;
                }
                else if ( btTomb[prevStepRowNo + 2][prevStepColNo].isEnabled() && btTomb[prevStepRowNo + 2][prevStepColNo].getIcon() == null ) {
                    i = prevStepRowNo + 2;
                    j = prevStepColNo;
                }
                else if ( btTomb[prevStepRowNo - 1][prevStepColNo].isEnabled() && btTomb[prevStepRowNo - 1][prevStepColNo].getIcon() == null ) {
                    i = prevStepRowNo - 1;
                    j = prevStepColNo;
                }
                else if ( btTomb[prevStepRowNo - 2][prevStepColNo].isEnabled() && btTomb[prevStepRowNo - 2][prevStepColNo].getIcon() == null ) {
                    i = prevStepRowNo - 2;
                    j = prevStepColNo;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( ( btTomb[prevStepRowNo][prevStepColNo + 2].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon()
                        || btTomb[prevStepRowNo][prevStepColNo - 2].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon() )
                      && ( btTomb[prevStepRowNo][prevStepColNo + 1].isEnabled() && btTomb[prevStepRowNo][prevStepColNo + 1].getIcon() == null
                           || btTomb[prevStepRowNo][prevStepColNo - 1].isEnabled() && btTomb[prevStepRowNo][prevStepColNo - 1].getIcon() == null ) ) {
                System.out.println( "Attack: Horizontal: Koze" );
                if ( btTomb[prevStepRowNo][prevStepColNo + 1].isEnabled() && btTomb[prevStepRowNo][prevStepColNo + 1].getIcon() == null ) {
                    i = prevStepRowNo;
                    j = prevStepColNo + 1;
                }
                else if ( btTomb[prevStepRowNo][prevStepColNo - 1].isEnabled() && btTomb[prevStepRowNo][prevStepColNo - 1].getIcon() == null ) {
                    i = prevStepRowNo;
                    j = prevStepColNo - 1;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( ( btTomb[prevStepRowNo + 2][prevStepColNo].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon()
                        || btTomb[prevStepRowNo - 2][prevStepColNo].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon() )
                      && ( btTomb[prevStepRowNo + 1][prevStepColNo].isEnabled() && btTomb[prevStepRowNo + 1][prevStepColNo].getIcon() == null
                           || btTomb[prevStepRowNo - 1][prevStepColNo].isEnabled() && btTomb[prevStepRowNo - 1][prevStepColNo].getIcon() == null ) ) {
                System.out.println( "Attack: Vertical: Koze" );
                if ( btTomb[prevStepRowNo + 1][prevStepColNo].isEnabled() && btTomb[prevStepRowNo + 1][prevStepColNo].getIcon() == null ) {
                    i = prevStepRowNo + 1;
                    j = prevStepColNo;
                }
                else if ( btTomb[prevStepRowNo - 1][prevStepColNo].isEnabled() && btTomb[prevStepRowNo - 1][prevStepColNo].getIcon() == null ) {
                    i = prevStepRowNo - 1;
                    j = prevStepColNo;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( btTomb[prevStepRowNo + 1][prevStepColNo - 1].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon()
                      || btTomb[prevStepRowNo - 1][prevStepColNo + 1].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon() ) {
                System.out.println( "Attack: Atlos: Melle" );
                if ( btTomb[prevStepRowNo + 2][prevStepColNo - 2].isEnabled() && btTomb[prevStepRowNo + 2][prevStepColNo - 2].getIcon() == null ) {
                    i = prevStepRowNo + 2;
                    j = prevStepColNo - 2;
                }
                else if ( btTomb[prevStepRowNo - 2][prevStepColNo + 2].isEnabled() && btTomb[prevStepRowNo - 2][prevStepColNo + 2].getIcon() == null ) {
                    i = prevStepRowNo - 2;
                    j = prevStepColNo + 2;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( btTomb[prevStepRowNo + 1][prevStepColNo + 1].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon()
                      || btTomb[prevStepRowNo - 1][prevStepColNo - 1].getIcon() == btTomb[prevStepRowNo][prevStepColNo].getIcon() ) {
                System.out.println( "Attack: Atlos: Melle" );
                if ( btTomb[prevStepRowNo + 2][prevStepColNo + 2].isEnabled() && btTomb[prevStepRowNo + 2][prevStepColNo + 2].getIcon() == null ) {
                    i = prevStepRowNo + 2;
                    j = prevStepColNo + 2;
                }
                else if ( btTomb[prevStepRowNo - 2][prevStepColNo - 2].isEnabled() && btTomb[prevStepRowNo - 2][prevStepColNo - 2].getIcon() == null ) {
                    i = prevStepRowNo - 2;
                    j = prevStepColNo - 2;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( ( btTomb[lastStepRowNo][lastStepColNo + 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                        || btTomb[lastStepRowNo][lastStepColNo - 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() )
                      && ( btTomb[lastStepRowNo][lastStepColNo + 1].isEnabled() && btTomb[lastStepRowNo][lastStepColNo + 1].getIcon() == null
                           || btTomb[lastStepRowNo][lastStepColNo + 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo + 2].getIcon() == null
                           || btTomb[lastStepRowNo][lastStepColNo - 1].isEnabled() && btTomb[lastStepRowNo][lastStepColNo - 1].getIcon() == null
                           || btTomb[lastStepRowNo][lastStepColNo - 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo - 2].getIcon() == null ) ) {
                System.out.println( "Deffens: Horizontal: Melle" );
                if ( btTomb[lastStepRowNo][lastStepColNo + 1].isEnabled() && btTomb[lastStepRowNo][lastStepColNo + 1].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo + 1;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo + 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo + 2].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo + 2;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo - 1].isEnabled() && btTomb[lastStepRowNo][lastStepColNo - 1].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo - 1;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo - 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo - 2].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo - 2;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( ( btTomb[lastStepRowNo + 1][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                        || btTomb[lastStepRowNo - 1][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() )
                      && ( btTomb[lastStepRowNo + 1][lastStepColNo].isEnabled() && btTomb[lastStepRowNo + 1][lastStepColNo].getIcon() == null
                           || btTomb[lastStepRowNo + 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo + 2][lastStepColNo].getIcon() == null
                           || btTomb[lastStepRowNo - 1][lastStepColNo].isEnabled() && btTomb[lastStepRowNo - 1][lastStepColNo].getIcon() == null
                           || btTomb[lastStepRowNo - 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo - 2][lastStepColNo].getIcon() == null ) ) {
                System.out.println( "Deffens: Vertical: Melle" );
                if ( btTomb[lastStepRowNo + 1][lastStepColNo].isEnabled() && btTomb[lastStepRowNo + 1][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo + 1;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo + 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo + 2][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo + 2;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo - 1][lastStepColNo].isEnabled() && btTomb[lastStepRowNo - 1][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo - 1;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo - 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo - 2][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo - 2;
                    j = lastStepColNo;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( btTomb[lastStepRowNo][lastStepColNo + 2].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                      || btTomb[lastStepRowNo][lastStepColNo - 2].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() ) {
                System.out.println( "Deffens: Horizontal: Koze" );
                if ( btTomb[lastStepRowNo][lastStepColNo + 1].isEnabled() && btTomb[lastStepRowNo][lastStepColNo + 1].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo + 1;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo - 1].isEnabled() && btTomb[lastStepRowNo][lastStepColNo - 1].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo - 1;
                }
            }
            else if ( btTomb[lastStepRowNo + 2][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                      || btTomb[lastStepRowNo - 2][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() ) {
                System.out.println( "En: " + lastStepRowNo + "; " + lastStepColNo );
                System.out.println( "Deffens: Vertical: Koze" );
                if ( btTomb[lastStepRowNo + 1][lastStepColNo].isEnabled() && btTomb[lastStepRowNo + 1][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo + 1;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo - 1][lastStepColNo].isEnabled() && btTomb[lastStepRowNo - 1][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo - 1;
                    j = lastStepColNo;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( btTomb[lastStepRowNo + 1][lastStepColNo - 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                      || btTomb[lastStepRowNo - 1][lastStepColNo + 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() ) {
                System.out.println( "Deffens: Atlos: Melle" );
                if ( btTomb[lastStepRowNo + 2][lastStepColNo - 2].isEnabled() && btTomb[lastStepRowNo + 2][lastStepColNo - 2].getIcon() == null ) {
                    i = lastStepRowNo + 2;
                    j = lastStepColNo - 2;
                }
                else if ( btTomb[lastStepRowNo - 2][lastStepColNo + 2].isEnabled() && btTomb[lastStepRowNo - 2][lastStepColNo + 2].getIcon() == null ) {
                    i = lastStepRowNo - 2;
                    j = lastStepColNo + 2;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo + 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo + 2].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo + 2;
                }
                else if ( btTomb[lastStepRowNo + 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo + 2][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo + 2;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo - 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo - 2][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo - 2;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo - 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo - 2].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo - 2;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            else if ( btTomb[lastStepRowNo + 1][lastStepColNo + 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                      || btTomb[lastStepRowNo - 1][lastStepColNo - 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() ) {
                System.out.println( "Deffens: Atlos: Melle" );
                if ( btTomb[lastStepRowNo + 2][lastStepColNo + 2].isEnabled() && btTomb[lastStepRowNo + 2][lastStepColNo + 2].getIcon() == null ) {
                    i = lastStepRowNo + 2;
                    j = lastStepColNo + 2;
                }
                else if ( btTomb[lastStepRowNo - 2][lastStepColNo - 2].isEnabled() && btTomb[lastStepRowNo - 2][lastStepColNo - 2].getIcon() == null ) {
                    i = lastStepRowNo - 2;
                    j = lastStepColNo - 2;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo + 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo + 2].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo + 2;
                }
                else if ( btTomb[lastStepRowNo + 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo + 2][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo + 2;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo - 2][lastStepColNo].isEnabled() && btTomb[lastStepRowNo - 2][lastStepColNo].getIcon() == null ) {
                    i = lastStepRowNo - 2;
                    j = lastStepColNo;
                }
                else if ( btTomb[lastStepRowNo][lastStepColNo - 2].isEnabled() && btTomb[lastStepRowNo][lastStepColNo - 2].getIcon() == null ) {
                    i = lastStepRowNo;
                    j = lastStepColNo - 2;
                }
                System.out.println( "O: " + ( i - 1 ) + "; " + ( j - 1 ) );
            }
            if ( btTomb[i][j].getIcon() == null && btTomb[i][j].isEnabled() ) {
                System.out.println( "step" );
//                step( ( byte ) i, ( byte ) j );
            }
            else if ( stepNo < ( rows - 4 ) * ( cols - 4 ) ) {
                do {
                    i = ( int ) ( Math.random() * ( rows - 2 ) + 1 );
                    j = ( int ) ( Math.random() * ( cols - 2 ) + 1 );
//                } while (btTomb[i][j].getIcon () != null || btTomb[i][j].isEnabled () == false);
                    System.out.println( "while" );
                }
                while ( !( btTomb[i][j].getIcon() == null && btTomb[i][j].isEnabled() ) );
//                step( ( byte ) i, ( byte ) j );
            }
        }
        else if ( rows > 9 && cols > 9 ) {
            if ( btTomb[lastStepRowNo][lastStepColNo + 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo][lastStepColNo - 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo + 1][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo - 1][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo + 1][lastStepColNo + 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo - 1][lastStepColNo - 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo + 1][lastStepColNo - 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo - 1][lastStepColNo + 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo][lastStepColNo + 2].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() && btTomb[lastStepRowNo][lastStepColNo
                                                                                                                                                  - 2].getIcon()
                                                                                                                            == btTomb[lastStepRowNo][lastStepColNo].
                    getIcon()
                 || btTomb[lastStepRowNo + 2][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                    && btTomb[lastStepRowNo - 2][lastStepColNo].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo + 2][lastStepColNo + 2].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                    && btTomb[lastStepRowNo - 2][lastStepColNo - 2].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                 || btTomb[lastStepRowNo + 1][lastStepColNo - 1].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon()
                    && btTomb[lastStepRowNo - 2][lastStepColNo + 2].getIcon() == btTomb[lastStepRowNo][lastStepColNo].getIcon() ) {
                do {
                    i = lastStepRowNo + ( ( ( int ) ( Math.random() * 3 ) ) - 1 );
                    j = lastStepColNo + ( ( ( int ) ( Math.random() * 3 ) ) - 1 );
                }
                while ( i == 0 && j == 0 || btTomb[i][j].getIcon() != null || btTomb[i][j].isEnabled() == false );
                if ( btTomb[i][j].getIcon() == null && btTomb[i][j].isEnabled() ) {
//                    step( ( byte ) i, ( byte ) j );
                }
                else if ( stepNo < ( rows - 4 ) * ( cols - 4 ) ) {
                    progLogic( x, y );////StackOverFlow!!!
                }
            }
            else {
                do {
                    i = ( int ) ( Math.random() * ( rows - 4 ) ) + 4 / 2;
                    j = ( int ) ( Math.random() * ( cols - 4 ) ) + 4 / 2;
                }
                while ( i == 0 && j == 0 || btTomb[i][j].getIcon() != null || btTomb[i][j].isEnabled() == false );
                if ( btTomb[i][j].getIcon() == null && btTomb[i][j].isEnabled() ) {
//                    step( ( byte ) i, ( byte ) j );
                }
                else if ( stepNo < ( rows - 4 ) * ( cols - 4 ) ) {
                    progLogic( x, y );////StackOverFlow!!!
                }
            }
        }
    }

    private void step( byte i, byte j )
    {
        stepNo++;
        xNext = !xNext;
        ( ( JLabel ) ( comp[2] ) ).setText( ( xNext ? "X" : "O" ) + " kovetkezik" );
        btTomb[i][j].setIcon( xNext ? imgO : imgX );
        if ( rows == 5 ) {////ELLENORIZNI!!!.........................................
            lastStepRowNo = ( byte ) ( rows == 5 ? i : ( i - 1 ) );
            lastStepColNo = ( byte ) ( rows == 5 ? j : ( j - 1 ) );
        }
        else {
            lastStepRowNo = ( byte ) ( ( rows == 5 ? i : ( i - 1 ) ) + 1 );
            lastStepColNo = ( byte ) ( ( rows == 5 ? j : ( j - 1 ) ) + 1 );
        }
        winChecker();
    }

    private void winChecker()
    {
        for ( int m = 4 / 2; m < rows - 4 / 2; m++ ) {
            for ( int n = 4 / 2; n < cols - 4 / 2; n++ ) {
                if ( btTomb[m][n].getIcon() == imgX || btTomb[m][n].getIcon() == imgO && activeGame ) {
                    if ( rows == 7 && cols == 7 ) {
                        if ( btTomb[m][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m][n - 1].getIcon() == btTomb[m][n].getIcon()
                             || btTomb[m + 1][n].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n].getIcon() == btTomb[m][n].getIcon()
                             || btTomb[m + 1][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n - 1].getIcon() == btTomb[m][n].getIcon()
                             || btTomb[m + 1][n - 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n + 1].getIcon() == btTomb[m][n].getIcon() ) {
                            result( true );
                        }
                    }
                    else if ( rows > 9 && cols > 9 ) {
                        if ( btTomb[m][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m][n - 1].getIcon() == btTomb[m][n].getIcon()
                             && btTomb[m][n + 2].getIcon() == btTomb[m][n].getIcon() && btTomb[m][n - 2].getIcon() == btTomb[m][n].getIcon()
                             || btTomb[m + 1][n].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n].getIcon() == btTomb[m][n].getIcon()
                                && btTomb[m + 2][n].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 2][n].getIcon() == btTomb[m][n].getIcon()
                             || btTomb[m + 1][n + 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n - 1].getIcon() == btTomb[m][n].getIcon()
                                && btTomb[m + 2][n + 2].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 2][n - 2].getIcon() == btTomb[m][n].getIcon()
                             || btTomb[m + 1][n - 1].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 1][n + 1].getIcon() == btTomb[m][n].getIcon()
                                && btTomb[m + 2][n - 2].getIcon() == btTomb[m][n].getIcon() && btTomb[m - 2][n + 2].getIcon() == btTomb[m][n].getIcon() ) {
                            result( true );
                        }
                    }
                }
            }
        }
        if ( activeGame && stepNo == ( rows - 4 ) * ( cols - 4 ) ) {
            new Windows( rootPane, xNext, "Dontetlen." );
        }
    }

    private void result( boolean a )
    {
        stopper.stop();
        comp[3].setEnabled( false );
        ( ( JLabel ) ( comp[2] ) ).setText( a ? ( xNext ? "Vesztettel." : "Nyertel." ) : "Vesztettel." );
        new Windows( rootPane, xNext, ( xNext ? "O" : "X" ) + " nyert." );
        for ( int k = 4 / 2; k < rows - 4 / 2; k++ ) {
            for ( int l = 4 / 2; l < cols - 4 / 2; l++ ) {
                btTomb[k][l].setEnabled( false );
            }
        }
        AI = activeGame = false;
    }

}
