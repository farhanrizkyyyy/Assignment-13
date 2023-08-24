import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    private static final MemberController controller = new MemberController(); // initiate controller object

    public static void main(String[] args) {
        controller.addDummies();
        showMembersMenu();
    }

    private static void showMembersMenu() {
        String[] options = {"Add member", "Update member", "Delete member"};
        String[] tableHeaders = {"Name", "Address", "Phone"};
        String[][] tableRows = new String[controller.getMembersLength()][3];
        final int[] selectedIndex = {0};

        for (int i = 0; i < controller.getMembersLength(); i++) { // loop through members size & show its value on JTable
            Member member = controller.getMemberByIndex(i);
            tableRows[i][0] = member.getName();
            tableRows[i][1] = member.getAddress();
            tableRows[i][2] = member.getPhone();
        }

        JTable table = new JTable(tableRows, tableHeaders) {
            public boolean isCellEditable(int row, int column) { // set editable value to false
                return false;
            }
        };
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) { // add event when user clicked on table's row
                selectedIndex[0] = table.getSelectedRow();
            }
        };
        table.addMouseListener(mouseAdapter);
        JScrollPane scrollPane = new JScrollPane(table); // make table scrollable
        // show the table on a dialog
        int action = JOptionPane.showOptionDialog(null, scrollPane, "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

        try {
            actionMenuNavigation(action, selectedIndex[0]);
        } catch (IndexOutOfBoundsException e) {
            showCustomMessageDialog("No member exist.");
            showMembersMenu();
        }
    }

    private static void actionMenuNavigation(int selectedMenu, int memberIndex) {
        switch (selectedMenu) {
            case 0:
                addMemberMenu();
                break;
            case 1:
                updateMemberMenu(memberIndex);
                break;
            case 2:
                deleteMember(memberIndex);
                break;
            case -1:
                exitConfirmation();
                break;
        }
    }

    private static String[] input(String[] initialValue) {
        // initial value is {"", "", ""} if user clicked on "Add member" button
        // initial value contains member's name, member's address, and member's phone if user clicked on "Update member" button
        String numbersOnly = "[0-9]+";
        String name, address, phone;

        while (true) {
            name = JOptionPane.showInputDialog("Insert your name:", initialValue[0]);
            if (name == null) showMembersMenu(); // go back to showMembersMenu if user click "Cancel"
            else {
                if (name.trim().equals(""))
                    showCustomMessageDialog("Input cannot be empty."); // show message dialog if form is empty
                else break;
            }
        }

        while (true) {
            address = JOptionPane.showInputDialog("Insert your address:", initialValue[1]);
            if (address == null) showMembersMenu(); // go back to showMembersMenu if user click "Cancel"
            else {
                if (address.trim().equals(""))
                    showCustomMessageDialog("Input cannot be empty."); // show message dialog if form is empty
                else break;
            }
        }

        while (true) {
            phone = JOptionPane.showInputDialog("Insert your phone number:", initialValue[2]);
            if (phone == null) showMembersMenu(); // go back to showMembersMenu if user click "Cancel"
            else {
                if (phone.trim().equals(""))
                    showCustomMessageDialog("Input cannot be empty."); // show message dialog if form is empty
                else {
                    if (phone.matches(numbersOnly)) break;
                    else
                        showCustomMessageDialog("Input must be numbers only."); // show message dialog if form value contains value other than numbers
                }
            }
        }

        return new String[]{name, address, phone}; // returns input value as array of string
    }

    private static void addMemberMenu() {
        String[] inputValues = input(new String[]{"", "", ""});
        String name = inputValues[0];
        String address = inputValues[1];
        String phone = inputValues[2];

        controller.addMember(name, address, phone);
        showCustomMessageDialog("Member successfully added!");

        showMembersMenu();
    }

    private static void updateMemberMenu(int memberIndex) {
        Member selectedMember = controller.getMemberByIndex(memberIndex);
        String[] inputValues = input(new String[]{selectedMember.getName(), selectedMember.getAddress(), selectedMember.getPhone()});
        String newName = inputValues[0];
        String newAddress = inputValues[1];
        String newPhone = inputValues[2];

        controller.updateMember(memberIndex, newName, newAddress, newPhone);
        showCustomMessageDialog("Member successfully updated!");

        showMembersMenu();
    }

    private static void deleteMember(int memberIndex) {
        Member selectedMember = controller.getMemberByIndex(memberIndex);
        int option = JOptionPane.showOptionDialog(null, "Are you sure want to delete " + selectedMember.getName() + "?", "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        if (option == JOptionPane.YES_OPTION) {
            controller.deleteMember(memberIndex);
            showCustomMessageDialog("Member successfully deleted!");
        }

        showMembersMenu();
    }

    private static void exitConfirmation() {
        int option = JOptionPane.showConfirmDialog(null, "Are you sure want to exit this program?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.YES_OPTION) System.exit(0);
        else showMembersMenu();
    }

    private static void showCustomMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}