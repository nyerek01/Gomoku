package Gomoku;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

final class XMLLogic extends JFrame{

static ArrayList<Player> playerList;
private JList lPlayersName, lPlayersLevel, lPlayersTime;
private String playerName, playerLevel;
private short playerWin,playerLose,playerTime, playerID;

protected XMLLogic() throws HeadlessException{
  try{
    loadXMLFile("./src/xml/x.xml");
  }catch(Exception e){
    writeXMLFile("./src/xml/x.xml");
  }
}

void writeXMLFile(String str){
  try{
    DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
    Document doc=docBuilder.newDocument();
    Element rootElement=doc.createElement("Players");
    doc.appendChild(rootElement);
    Element Player=doc.createElement("Player");
    rootElement.appendChild(Player);
    Player.setAttribute("ID","1");
    Element Name=doc.createElement("Name");
    Name.appendChild(doc.createTextNode("Name"));
    Player.appendChild(Name);
    Element Level=doc.createElement("Level");
    Level.appendChild(doc.createTextNode("Level"));
    Player.appendChild(Level);
        Element Win=doc.createElement("Win");
    Win.appendChild(doc.createTextNode(79+""));
    Player.appendChild(Win);
        Element Lose=doc.createElement("Lose");
    Lose.appendChild(doc.createTextNode(3+""));
    Player.appendChild(Lose);
    Element Time=doc.createElement("Time");
    Time.appendChild(doc.createTextNode(99+""));
    Player.appendChild(Time);
    TransformerFactory transformerFactory=TransformerFactory.newInstance();
    Transformer transformer=transformerFactory.newTransformer();
    DOMSource source=new DOMSource(doc);
    StreamResult result=new StreamResult(new File(str));
    transformer.transform(source,result);
  }catch(ParserConfigurationException|TransformerException pce){
  }
}

private final class Player{

private String playerName, playerLevel;
private short playerWin, playerLose, playerTime, playerID;

private Player(String playerName,String playerLevel,short playerWin,short playerLose,short playerTime,short playerID){
  this.playerName=playerName;
  this.playerLevel=playerLevel;
  this.playerWin=playerWin;
  this.playerLose=playerLose;
  this.playerTime=playerTime;
  this.playerID=playerID;
}

String getName(){
  return playerName;
}

void setName(String playerName){
  this.playerName=playerName;
}

String getLevel(){
  return playerLevel;
}

short getTime(){
  return playerTime;
}

short getWin(){
return playerWin;
}

short getLose(){
  return playerLose;
}

short getID(){
  return playerID;
}

@Override
public String toString(){
  return (String.format("%-8s %-10s %6s %n",getLevel(),getName(),getTime(),getWin (), getLose ()));
}
}

void loadXMLFile(String str){
  playerList=new ArrayList<>();
  File xmlFile=null;
  try{
    xmlFile=new File(str);
  }catch(Exception e){
  }
  Document d=null;
  try{
    d=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
  }catch(ParserConfigurationException|SAXException|IOException e){
  }
  try{
    NodeList nodeList=d.getDocumentElement().getElementsByTagName("Player");
    for(int i=0;i<nodeList.getLength();i++){
      Element node=(Element)nodeList.item(i);
      
      Node name=node.getElementsByTagName("Name").item(0);
      playerName=name.getFirstChild().getNodeValue();
      
      Node level=node.getElementsByTagName("Level").item(0);
      playerLevel=level.getFirstChild().getNodeValue();
      
      Node w=node.getElementsByTagName("Win").item(0);
      playerWin=Short.parseShort(w.getFirstChild().getNodeValue());
      
      Node l=node.getElementsByTagName("Lose").item(0);
      playerLose=Short.parseShort(l.getFirstChild().getNodeValue());
      
      Node t=node.getElementsByTagName("Time").item(0);
      playerTime=Short.parseShort(t.getFirstChild().getNodeValue());
      
      playerList.add(new Player(playerName,playerLevel,playerWin,playerLose,playerTime,playerID));
    }
  }catch(NumberFormatException|DOMException e){
  }
}

@SuppressWarnings("unchecked")
void PlayersName(){
  lPlayersName=new JList();
  DefaultListModel dlm=new DefaultListModel();
  lPlayersName.setModel(dlm);
  TreeSet<String> playerNameSet=new TreeSet<>(), playerLevelSet=new TreeSet<>();
  TreeSet<Short> playerTimeSet=new TreeSet<>();
  for(Player player:playerList){
    playerNameSet.add(String.format("%-8s %-10s %7s %n",player.getLevel(),player.getName(),player.getTime()+" sec"));
  }
  for(String pL:playerLevelSet){
    dlm.addElement(pL+":");
  }
  for(Iterator<Short> it=playerTimeSet.iterator();it.hasNext();){
    Short pT;
    pT=it.next();
    dlm.addElement(pT+" sec.");
  }
  for(String pN:playerNameSet){
    dlm.addElement(pN);
  }
}

void PlayersID(){
  TreeSet<Short> playerIDSet=new TreeSet<>();
  for(Player player:playerList){
    playerIDSet.add(player.getID());
  }
}

void ModifXMLFile(String str) throws TransformerConfigurationException,TransformerException,ParserConfigurationException,SAXException,IOException{
  try{
    boolean mod=true;
    DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
    Document doc=docBuilder.parse(str);
    NodeList nodeList=doc.getDocumentElement().getElementsByTagName("Player");
    String m;
    for(int i=0;i<nodeList.getLength();i++){
      Element node=(Element)nodeList.item(i);
      Node level=node.getElementsByTagName("Level").item(0);
      m=level.getFirstChild().getNodeValue();
      if(AmobaStart.getAmoba().getPlayerLevel().equals(m)){
        Node n=node.getElementsByTagName("Name").item(0);
        Node t=node.getElementsByTagName("Time").item(0);
        n.getFirstChild().setTextContent(playerName);
        t.getFirstChild().setTextContent(AmobaStart.getAmoba().getTime()+"");
        mod=false;
      }
//        playerTime = Short.parseShort(t.getFirstChild().getNodeValue());
    }
    if(mod){
      Node Players=doc.getFirstChild();
      Element newplayer=doc.createElement("Player");
      Players.appendChild(newplayer);
      Element newplayerName=doc.createElement("Name");
      newplayerName.appendChild(doc.createTextNode(playerName));
      newplayer.appendChild(newplayerName);
      Element newplayerLevel=doc.createElement("Level");
      newplayerLevel.appendChild(doc.createTextNode(AmobaStart.getAmoba().getPlayerLevel()));
      newplayer.appendChild(newplayerLevel);
      Element newplayerTime=doc.createElement("Time");
      newplayerTime.appendChild(doc.createTextNode(AmobaStart.getAmoba().getTime()+""));
      newplayer.appendChild(newplayerTime);
    }
    // loop the player child node
//      NodeList list = player.getChildNodes();
//      for (int i = 0; i < list.getLength(); i++) {
//        Node node = list.item(i);
    // update
//          if ("Level".equals(node.getNodeName())) {
//            node.setTextContent(playerLevel);
//          }
    //remove Time
//          if ("Time".equals(node.getNodeName())) {
//            player.removeChild(node);
//          }
//      }
    // write the content into xml file
    TransformerFactory transformerFactory=TransformerFactory.newInstance();
    Transformer transformer=transformerFactory.newTransformer();
    DOMSource source=new DOMSource(doc);
    StreamResult result=new StreamResult(new File(str));
    transformer.transform(source,result);
  }catch(ParserConfigurationException|TransformerException|IOException|SAXException pce){
  }
}

JList getLPlayersName(){
  return lPlayersName;
}

JList getLPlayersLevel(){
  return lPlayersLevel;
}

JList getLPlayersTime(){
  return lPlayersTime;
}

void setPlayerName(String playerName){
  this.playerName=playerName;
}

void setPlayerLevel(String playerLevel){
  this.playerLevel=playerLevel;
}
}
