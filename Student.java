import javax.swing.*;


/***
 * Student extends {@link Person}
 */
public class Student extends Person{

    private String sID;
    private final JTextField studentID;
    private final JLabel invalidStudentID;

    /***
     * Parameterized constructor setting up the Student GUI
     * @param id Student id
     * @param name Student name
     * @param surname Student surname
     * @param tel Student telephone number
     * @param sID Student student id
     */
    public Student(String id, String name, String surname, String tel, String sID) {

        //initialization of properties
        super(id, name, surname,tel);
        this.sID = sID;

        //Student frame configuration
        super.setSize(450,250);
        super.setTitle("Student Clubber's Data");

        //create an extra text field for Student
        this.studentID = new JTextField(this.sID,30);
        super.AddToCenter(studentID);

        //define invalid field label and create student id panel
        invalidStudentID = new JLabel("");
        super.createPanel("Student ID", invalidStudentID, this.studentID);
    }

    /***
     * Checks if the key is equal to id or student id number
     * Overridden method from {@link ClubAbstractEntity #validateData}
     * @param key key to check equality
     * @return Boolean. Are the key and the id or the key and student id number equal?
     */
    @Override
    public boolean match(String key)
    {
        return (super.match(key) || sID.substring(4).equals(key));
    }

    /***
     * Checks whether the user input in the text fields match regular expressions and add asterisk where and if needed.
     * Overridden method from {@link ClubAbstractEntity #validateData}
     * @return Boolean. Do the text fields match regular expressions?
     */
    @Override
    protected boolean validateData()
    {
        if (!super.validateData()) {
            invalidStudentID.setText("");
            return false;
        }
        else{

            if (!this.studentID.getText().matches("[A-Z]{3}[/][1-9][0-9]{4}"))
            {
                invalidStudentID.setText("*");
                this.setVisible(true);
                return false;
            }

            super.setText("","","","");
            invalidStudentID.setText("");
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
        this.studentID.setText(sID);
    }

    /***
     * Save the user input to database.
     * Overridden method from {@link ClubAbstractEntity #commit}
     */
    @Override
    protected void commit()
    {
        super.commit();
        this.sID = studentID.getText();
    }
}
