
public class ContactsBean{                 //Used as DTO (Data Transfer Object) -Contains Constructor & Getter,Settter Methods Only
    
    private String name,mobile,email;     //This Class Carries Data between Process 

    public ContactsBean(String name, String mobile, String email){
        this.name = name;
        this.mobile= mobile;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}