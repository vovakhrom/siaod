package siaod1lab;

public class Abonent {
     protected String number ;
     protected String name;
     Abonent(String name,String number){
    	 this.number=number;
    	 this.name=name;
     }
     public void setname(String name) {
 		this.name = name;
 	}

 	public String getname() {
 		return this.name;
 	}

 	public void setnumber(String number) {
 		this.number = number;
 	}

 	public String getnumber() {
 		return this.number;
 	}
 	static boolean checkName(String s) {
        return s.matches("[a-zA-z]+\\s+[a-zA-z]+\\s+[a-zA-z]+");
    }
 	static boolean check(String s) {
        return s.matches("[a-zA-z]+");
    }
 	static boolean checknumber(String s){
 		return s.matches("^(\\d{7})$");
 	}
 	
}
