import java.util.ArrayList;
import java.util.List;

public class MemberController {
    public List<Member> members = new ArrayList<>();

    public void addMember(String name, String address, String phone) {
        Member newMember = new Member(name, address, phone);
        this.members.add(newMember);
    }

    public Member getMemberByIndex(int index) {
        return this.members.get(index);
    }

    public void deleteMember(int index) {
        this.members.remove(index);
    }

    public void updateMember(int index, String newName, String newAddress, String newPhone) {
        Member selectedMember = getMemberByIndex(index);
        selectedMember.setName(newName);
        selectedMember.setAddress(newAddress);
        selectedMember.setPhone(newPhone);
    }

    public void addDummies() {
        this.addMember("Farhan 1", "Bandung", "1111111111");
        this.addMember("Farhan 2", "Bandung", "2222222222");
        this.addMember("Farhan 3", "Bandung", "3333333333");
    }
}
