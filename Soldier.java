import javax.swing.*;

/***
 * Soldier extends {@link Person}
 */
public class Soldier extends Person{

    private String personalNum;
    private final JTextField pNum;
    private final JLabel invalidpNum;

    /***
     * Parameterized constructor setting up the Soldier GUI
     * @param id Soldier id
     * @param name Soldier name
     * @param surname Soldier surname
     * @param tel Soldier telephone number
     * @param personalNum Soldier personal number
     */
    public Soldier(String id, String name, String surname, String tel, String personalNum)
    {
        //initialization of properties
        super(id, name, surname, tel);
        this.personalNum = personalNum;

        //Soldier frame configuration
        super.setSize(450,250);
        super.setTitle("Soldier Clubber's Data");

        //create an extra text field for soldier
        this.pNum = new JTextField(this.personalNum, 30);

        //define invalid field label and create personal number panel
        invalidpNum = new JLabel("");
        super.createPanel("Personal No.",invalidpNum, this.pNum);
    }

    /***
     * Checks if the key is equal to id or personal number
     * Overridden method from {@link ClubAbstractEntity #validateData}
     * @param key key to check equality
     * @return Boolean. Are the key and the id or the key and personal number equal?
     */
    @Override
    public boolean match(String key)
    {
        return (super.match(key) || personalNum.equals(key));
    }

    /***
     * Checks whether the user input in the text fields match regular expressions and add asterisk where and if needed.
     * Overridden method from {@link ClubAbstractEntity #validateData}
     * @return Boolean. Do the text fields match regular expressions?
     */
    @Override
    protected boolean validateData() {
        if (!super.validateData())
        {
            invalidpNum.setText("");
            return false;
        }
        else
        {
                if (!this.pNum.getText().matches("[ROC][/][1-9][0-9]{6}"))
                {
                invalidpNum.setText("*");
                this.setVisible(true);
                return false;
                }

            super.setText("","","","");
            invalidpNum.setText("");
            return true;
        }
    }

    /***
     * Rollback the database data
     * Overridden method from {@link ClubAbstractEntity #rollback}
     */
    @Override
    protected void rollBack()
    {
        super.rollBack();
        this.pNum.setText(personalNum);
    }

    /***
     * Save the user input to database.
     * Overridden method from {@link ClubAbstractEntity #commit}
     */
    @Override
    protected void commit()
    {
        super.commit();
        this.personalNum = pNum.getText();
    }
}