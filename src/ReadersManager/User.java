package ReadersManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
class User {
    private int id, kind;
    private String name, address, email, dayBirth,dayCre, dayExpiry, debt;

    public User(int id, String name, int kind, String address, String email, String dayBirth, String dayCre, String dayExpiry, String debt) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.address = address;
        this.email = email;
        this.dayBirth = dayBirth;
        this.dayCre = dayCre;
        this.dayExpiry = dayExpiry;
        this.debt = debt;
    }
    
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getKind() {
        return kind;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getDayBirth() {
        return dayBirth;
    }

    public String getDayCre() {
        return dayCre;
    }

    public String getDayExpiry() {
        return dayExpiry;
    }

    public String getDebt() {
        return debt;
    }
    public String check(int i)
    {
        if(i==1)
            return "X";
        else
            return "Y";
    }
}
    
    
