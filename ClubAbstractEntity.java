import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Abstract class that is using {@link Person} as the base class.
 * extends {@link JFrame} and implements {@link Serializable}
 */
public abstract class ClubAbstractEntity extends JFrame  implements Serializable {

    private final JPanel centerPanel;
    private final JButton cancelButton;

    /***
     * check if the key is equal to id
     * @param key key to check equality
     * @return Boolean. Are the key and the id equal?
     */
    public abstract boolean match(String key);

    /***
     * checks if the user input is valid
     * @return Boolean. Is the input valid?
     */
    protected abstract boolean validateData();

    /***
     * save the user input data to database
     */
    protected abstract void commit();

    /***
     * rollback the database data
     */
    protected abstract void rollBack();

    /***
     * Default constructor setting the infrastructure of the GUI frame
     */
    public ClubAbstractEntity()
    {
        //add panel to center and button panels to the bottom
        centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel bottomPanel = new JPanel();
        add(centerPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

        //add cancel button
        cancelButton = new JButton("Cancel");
        bottomPanel.add(cancelButton);

        //add ok button
        JButton okButton = new JButton("OK");
        bottomPanel.add(okButton);

        //initializing button handler
        ButtonHandler handler = new ButtonHandler(okButton, cancelButton, this);
        cancelButton.addActionListener(handler);
        okButton.addActionListener(handler);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /***
     *  Adds a GUI component to the center of the frame
     * @param guiComponent GUI component that will be added to the frame
     */
    public void AddToCenter(Component guiComponent) {
        this.centerPanel.add(guiComponent);
    }

    /***
     * Button event handler implements {@link ActionListener #actionPerformed} and {@link Serializable}
     */
    private static class ButtonHandler implements ActionListener, Serializable
    {
        private final JButton okButton;
        private final JButton cancelButton;
        private final ClubAbstractEntity clubEntity;

        /***
         * Parameterized constructor for Button Handler
         * @param okButton ok button
         * @param cancelButton cancel button
         * @param clubEntity {@link ClubAbstractEntity}
         */
        public ButtonHandler ( JButton okButton, JButton cancelButton, ClubAbstractEntity clubEntity )
        {
            this.okButton = okButton;
            this.cancelButton = cancelButton;
            this.clubEntity = clubEntity;
        }

        /***
         * handle the action performed
         * @param e action event
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == this.okButton) {
                if (this.clubEntity.validateData()) {
                    this.clubEntity.commit();
                    this.clubEntity.setVisible(false);
                }
            } else if (e.getSource() == this.cancelButton) {
                this.clubEntity.rollBack();
                this.clubEntity.setVisible(false);
            }
        }
    }

    /***
     * set the cancel button to be enabled or disabled
     * @param enabled value to set
     */
    public void setEnabledButton(Boolean enabled)
    {
        this.cancelButton.setEnabled(enabled);
    }
}


