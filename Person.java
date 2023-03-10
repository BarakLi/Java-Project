import javax.swing.*;
import java.awt.*;

/***
 * Person class extends {@link ClubAbstractEntity}
 * base class of {@link Soldier} and {@link Student}
 */
public class Person extends ClubAbstractEntity
{
    private String id, name, surname, tel;

    private final JTextField idTextField, nameTextField, surnameTextField, telTextField;

    protected JLabel invalidId, invalidName, invalidSurname, invalidTel;

    /***
     * Parameterized constructor setting the GUI for Person entity
     * @param id Person id
     * @param name Person name
     * @param surname Person surname
     * @param tel Person telephone number
     */
    public Person(String id, String name, String surname, String tel)
    {
        //initialization of properties
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.tel = tel;

        //Person frame configuration
        super.setSize(450,220);
        super.setTitle("Person Clubber's Data");

        //add text fields for Person frame
        invalidId = new JLabel("");
        this.idTextField = new JTextField(this.id,30);
        this.nameTextField = new JTextField(this.name, 30);
        this.surnameTextField = new JTextField(this.surname, 30);
        this.telTextField = new JTextField(this.tel, 30);

        //define invalid field label
        invalidId = new JLabel("");
        invalidName = new JLabel("");
        invalidSurname = new JLabel("");
        invalidTel = new JLabel("");

        //create panels for required text fields
        createPanel("ID", invalidId,  this.idTextField);
        createPanel("Name", invalidName,  this.nameTextField);
        createPanel("Surname", invalidSurname,  this.surnameTextField);
        createPanel("Tel", invalidTel,  this.telTextField);
    }

    /***
     * Creates panels and adds them to the center of the GUI
     * @param label name of the label
     * @param invalid invalid label of the name label
     * @param textField text field of the name label
     */
    protected void createPanel(String label, JLabel invalid, JTextField textField){
        JPanel Panel = new JPanel();
        Panel.add(new JLabel(label));
        Panel.add(textField);
        invalid.setForeground(Color.RED);
        Panel.add(invalid);
        super.AddToCenter(Panel);
    }

    /***
     * Checks if the key is equal to id
     * Overridden method from {@link ClubAbstractEntity #validateData}
     * @param key key to check equality
     * @return Boolean. Are the key and the id equal?
     */
    @Override
    public boolean match(String key)
    {
        return this.id.equals(key);
    }

    /***
     * Checks whether the user input in the text fields match regular expressions and add asterisk where and if needed.
     * Overridden method from {@link ClubAbstractEntity #validateData}
     * @return Boolean. Do the text fields match regular expressions?
     */
    @Override
    protected boolean validateData()
    {
        if(!this.idTextField.getText().matches("[0-9]-[0-9]{7}[|][1-9]") )
        {
            //add asterisk after id text field
            this.setText("*","","","");
            this.setVisible(true);
            return false;
        }
        else if(!this.nameTextField.getText().matches("[A-Z][a-z]*"))
        {
            //add asterisk after name text field
            this.setText("","*","","");
            this.setVisible(true);
            return false;
        }
        else if(!this.surnameTextField.getText().matches("([A-Z]([a-z])*(['-])?)+"))
        {
            //add asterisk after surname text field
            this.setText("","","*","");
            this.setVisible(true);
            return false;
        }
        else if(!this.telTextField.getText().matches("[+][(][1-9][0-9]{0,2}[)][1-9][0-9]{0,2}[-][1-9][0-9]{6}"))
        {
            //add asterisk after tel text field
            this.setText("","","","*");
            this.setVisible(true);
            return false;
        }
        //there are no invalid text fields
        this.setText("","","","");
        return true;
    }

    /***
     * Rollback the database data
     * Overridden method from {@link ClubAbstractEntity #rollback}
     */
    @Override
    protected void rollBack()
    {
        this.idTextField.setText(id);
        this.nameTextField.setText(name);
        this.surnameTextField.setText(surname);
        this.telTextField.setText(tel);
    }

    /***
     * Saves the user input to database
     * Overridden method from {@link ClubAbstractEntity #commit}
     */
    @Override
    protected void commit()
    {
        this.id = idTextField.getText();
        this.name = nameTextField.getText();
        this.surname = surnameTextField.getText();
        this.tel = telTextField.getText();
    }

    /***
     * Adds text near invalid field
     * @param id_ Person id
     * @param name_ Person name
     * @param surname_ Person surname
     * @param tel_ Person telephone number
     */
    public void setText(String id_, String name_, String surname_, String tel_ )
    {
        invalidId.setText(id_);
        invalidName.setText(name_);
        invalidSurname.setText(surname_);
        invalidTel.setText(tel_);
    }
}