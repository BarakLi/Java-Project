import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/***
 * Main class that implements {@link Serializable} and {@link ActionListener}
 */
public class NightClubMgmtApp implements ActionListener, Serializable
{
    private ArrayList<ClubAbstractEntity> clubbers;
    private final JFrame mainFrame;
    private final JButton searchBtn, addBtn, exitBtn;
    private final JComboBox<String> memberChoice;

    /***
     * Default constructor that sets up the main program's GUI
     */
    public NightClubMgmtApp()
    {
        //main frame configuration
        mainFrame= new JFrame();
        mainFrame.setTitle("Night Club Management App");
        mainFrame.setSize(500,200);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Add search button to designated panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        searchPanel.add(searchBtn);
        mainFrame.add(searchPanel,BorderLayout.WEST);

        //Add add button and combo box to designated panel
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtn = new JButton("Add Member");
        addBtn.addActionListener(this);
        String[] memberType = {"Person", "Student", "Soldier"};
        memberChoice = new JComboBox<>(memberType);
        addPanel.add(memberChoice);
        addPanel.add(addBtn);
        mainFrame.add(addPanel,BorderLayout.EAST);

        clubbers = new ArrayList<>();
        loadClubbersDBFromFile();

        //add exit button to designated panel
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(this);
        exitPanel.add(exitBtn);
        mainFrame.add(exitPanel,BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    /***
     * search the input key in the clubbers database and show the result
     */
   private void manipulateDB()
    {
        String input;
        boolean found = false;
            input = JOptionPane.showInputDialog("Enter the ID of the person you would like to search");
            for(ClubAbstractEntity clubber : clubbers)
            {
                if(clubber.match(input))
                {
                    found = true;
                    clubber.setLocationRelativeTo(null);
                    clubber.setEnabledButton(true);
                    clubber.setVisible(true);
                    clubber.toFront();
                    break;
                }
            }
            if(!found)
            {
                if(input == null)
                {
                    JOptionPane.showMessageDialog(mainFrame,"No key entered","Key not found",
                                                  JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(mainFrame,"Clubber with key "+input+" doesn't exist",
                                                 "Key not found",JOptionPane.INFORMATION_MESSAGE);
            }

    }// End of method manipulateDB

    /***
     * load the clubbers objects from the BKCustomers.dat file
     */
    private void loadClubbersDBFromFile()
    {
        try
        {
            FileInputStream fis = new FileInputStream("BKCustomers.dat");
            if(fis.available() > 58) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                clubbers = (ArrayList<ClubAbstractEntity>) ois.readObject();
                ois.close();
            }
            else
            {
                //Read data from file, create the corresponding objects manually and put them into clubbers ArrayList.
                clubbers.add(new Person("0-2423535|1", "Mark", "Mc'Cormic",
                        "+(1)4-9520205"));
                clubbers.add(new Soldier("0-2223335|1", "Zohar", "Couper-Berg",
                        "+(44)206-8208167", "O/4684109"));
                clubbers.add(new Student("2-5554445|3", "Avi", "Avrahami-O'Mally",
                        "+(972)50-6663210", "SCE/12345"));
            }
        }
        catch (EOFException eof)
        {
            JOptionPane.showMessageDialog(mainFrame,"The file is empty","Error",JOptionPane.ERROR_MESSAGE);
        }

        catch(FileNotFoundException fnf)
        {
            JOptionPane.showMessageDialog(mainFrame,"The file was not found","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(mainFrame,"ObjectInputStream error","Error",JOptionPane.ERROR_MESSAGE); //
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException cnf)
        {
            JOptionPane.showMessageDialog(mainFrame,"ClassNotFound error","Error",JOptionPane.ERROR_MESSAGE);
            cnf.printStackTrace();
        }
    }

    //Write all the objects' data in clubbers ArrayList to the file
    private void writeClubbersDBtoFile()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("BKCustomers.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clubbers);
            oos.close();
        }
        catch (FileNotFoundException fnf)
        {
            JOptionPane.showMessageDialog(mainFrame,"The file isn't found","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(mainFrame,"ObjectOutputStream error","Error",JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
        }
    } //end of method writeClubbersDBtoFile

    /***
     * main method
     * @param args main arguments.
     */
    public static void main(String[] args)
    {
        NightClubMgmtApp application = new NightClubMgmtApp();
    }

    /***
     * implements {@link ActionListener #actionPerformed}
     * used to handle button events
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.searchBtn)
        {
            manipulateDB();
        }
        else if(e.getSource() == this.addBtn)
        {
            addEntity();
        }
        if(e.getSource() == this.exitBtn)
        {
            //on exit, save the changes or added entities to the ArrayList
            this.writeClubbersDBtoFile();
            System.exit(0);
        }
    }

    /***
     * Add a new object to the ArrayList
     */
    private void addEntity()
    {
        if("Person".equals(memberChoice.getSelectedItem()))
        {
            Person p = new Person("","","","");
            clubbers.add(p);
            p.setEnabledButton(false);
            p.setLocationRelativeTo(null);
            p.setVisible(true);
        }
        else if("Student".equals(memberChoice.getSelectedItem()))
        {
            Student s = new Student("","","","","");
            clubbers.add(s);
            s.setEnabledButton(false);
            s.setLocationRelativeTo(null);
            s.setVisible(true);
        }
        else if("Soldier".equals(memberChoice.getSelectedItem()))
        {
            Soldier s = new Soldier("","","","","");
            clubbers.add(s);
            s.setEnabledButton(false);
            s.setLocationRelativeTo(null);
            s.setVisible(true);
        }
    } //end of method addEntity

}//End of class NightClubMgmtApp
