public class personDetail {
    private String firstname;
    private String lastname;
    private int age;


    //Constructor concept
    personDetail(String firstname,String lastname,int age){
        this.firstname=firstname;
        this.lastname = lastname;
        this.age = age;


        System.out.println(this.firstname + " " + this.lastname);
        System.out.println("I am "+ this.age + "year old.");
    }

//    public void setValues(String firstname,String lastname,int age){
//        this.firstname=firstname;
//        this.lastname = lastname;
//        this.age = age;
//    }
//
//    public void getValues(){
//        System.out.println(this.firstname + " " + this.lastname);
//        System.out.println("I am "+ this.age + "year old.");
//    }

    public static void main(String[] args){
        personDetail dipak = new personDetail("Dipak","Jha",21);
    }
}

