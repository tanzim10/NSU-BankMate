package LabProject;
import java.io.*;
import java.util.*;
class Account implements Serializable,Bank{
    private String Name;
    private int AccountNumber;
    private String Password;
    private double Balance;
    public Account(){
    }
    public Account(String Name, int AccountNumber, String Password, double Balance){
        this.Name=Name;
        this.AccountNumber=AccountNumber;
        this.Password=Password;
        this.Balance=Balance;
    }
    public String getName(){
        return this.Name;
    }
    public int getAccountNumber(){
        return this.AccountNumber;
    }
    public String getPassword(){
        return this.Password;
    }
    public void setName(String Name){
        this.Name=Name;
    }
    public double getBalance() {
        return Balance;
    }
    public void setAccountNumber(int AccountNumber){
        this.AccountNumber=AccountNumber;
    }
    public void setPassword(String Password){
        this.Password=Password;
    }
    public void setBalance(double Balance) {
        this.Balance = Balance;
    }
    public void Register() throws Exception {
        Scanner scan = new Scanner(System.in);
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter your name :");
            this.setName(scan.nextLine());
            if(this.getName().equals("")){
                System.out.println("Invalid input, please try again!");
                continue;
            }
            else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Invalid input, please try again!");
                scan.nextLine();
          }
        }
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter your 6 digit account number to set: ");
            this.setAccountNumber(scan.nextInt());
            scan.nextLine();//fix input buffer
                if(this.getAccountNumber()/100000 < 1){
                    System.out.println("Account number must be an integer and within 6 digits, please try again!");
                    continue;
                }
                else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Account number must be an integer and within 6 digits, please try again!");
                scan.nextLine();
          }
        }
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter your password to set: ");
            this.setPassword(scan.nextLine());
            if(this.getPassword().equals("")){
                System.out.println("Invalid input, please try again!");
                continue;
            }
            else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Invalid input, please try again!");
                scan.nextLine();
          }
        }
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(this.getAccountNumber()+".dat"))) {
            output.writeUTF(this.getName());
            output.writeInt(this.getAccountNumber());
            output.writeUTF(this.getPassword());
            output.writeDouble(this.getBalance());
        }
        System.out.println("Account created successfully!");
        System.out.flush();
        System.out.println();
        this.UiMain();
    }
    public void Removal(int AccountNumber) throws Exception {
         File file = new File(AccountNumber+".dat");
            if(file.delete())
                System.out.println("Account removed successfully!");
    }
    public int Login() throws Exception {
        int temp = -1;
        int AccountNumber = 0;
        String Passwordcheck = "";
        Scanner scan = new Scanner(System.in);
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter your 6 digit account number: ");
            AccountNumber = scan.nextInt();
            scan.nextLine();//fix input buffer
                if(AccountNumber/100000 < 1){
                    System.out.println("Account number must be an integer and within 6 digits, please try again!");
                    continue;
                }
                else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Account number must be an integer and within 6 digits, please try again!");
                scan.nextLine();
          }
        }
        try ( DataInputStream input = new DataInputStream(new FileInputStream(AccountNumber + ".dat"))) {
            input.readUTF();
            input.readInt();
            String OriginalPass = input.readUTF();
            input.readDouble();
            for (boolean exit = false; exit == false;) {
                try {
                    System.out.print("Enter your password: ");
                    Passwordcheck = scan.nextLine();
                    if (Passwordcheck.equals("")) {
                        System.out.println("Invalid input, please try again!");
                        continue;
                    } else {
                        exit = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again!");
                    scan.nextLine();
                }
            }

            if (OriginalPass.equals(Passwordcheck)) {
                temp = AccountNumber;
            } else {
                System.out.println("Incorrect password");
                temp = -1;
            }
        }
        catch (Exception e){
            System.out.println("Account not found!");
        }
        return temp;
    }
    public void Withdraw(int AccountNumber) throws Exception{
        String name ="";
        String password ="";
        double balance = 0;
        double withdraw = 0;
        Scanner scan = new Scanner(System.in);
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter the amount you want to withdraw: ");
            withdraw = scan.nextDouble();
            if(withdraw <= 0){
                System.out.println("Withdrawal amount can't be zero or negative, please try again!");
                continue;
            }
            else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Invalid input, please try again!");
                scan.nextLine();
                continue;
          }
        }
        try (DataInputStream input = new DataInputStream(new FileInputStream(AccountNumber+".dat"))) {
            name = input.readUTF();
            input.readInt();
            password = input.readUTF();
            balance = input.readDouble();
        }
        if(balance == 0)
            System.out.println("Your current balance is "+balance+", so you can't withdraw!");
        else if(balance < withdraw)
            System.out.println("Your current balance is "+balance+", which is lower than the withdrawal amount!");
        else{
            balance -= withdraw;
            System.out.println("Withdrawal successful!");
        }
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(AccountNumber+".dat"))) {
            output.writeUTF(name);
            output.writeInt(AccountNumber);
            output.writeUTF(password);
            output.writeDouble(balance);
        }
    };
    public void Deposit(int AccountNumber) throws Exception{
        String name ="";
        String password ="";
        double balance = 0;
        double deposit = 0;
        Scanner scan = new Scanner(System.in);
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter the amount you want to deposit: ");
            deposit = scan.nextDouble();
            if(deposit <= 0){
                System.out.println("Deposit amount can't be zero or negative, please try again!");
                continue;
            }
            else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Invalid input, please try again!");
                scan.nextLine();
                continue;
          }
        }
        try (DataInputStream input = new DataInputStream(new FileInputStream(AccountNumber+".dat"))) {
            name = input.readUTF();
            input.readInt();
            password = input.readUTF();
            balance = input.readDouble();
        }
        balance += deposit;
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(AccountNumber+".dat"))) {
            output.writeUTF(name);
            output.writeInt(AccountNumber);
            output.writeUTF(password);
            output.writeDouble(balance);
        }
        System.out.println("Deposit successful!");
    };
    public void displayInfo(int AccountNumber) throws Exception{
        try (DataInputStream input = new DataInputStream(new FileInputStream(AccountNumber+".dat"))) {
            System.out.println("Account holder name: "+input.readUTF());
            System.out.println("Account number: "+input.readInt());
            input.readUTF();
            input.readDouble();
        }
    }
    public void displayBalance(int AccountNumber) throws Exception{
        try (DataInputStream input = new DataInputStream(new FileInputStream(AccountNumber+".dat"))) {
            input.readUTF();
            input.readInt();
            input.readUTF();
            System.out.println("Your current balance: "+input.readDouble());
        }
    }
    public void UiMain(){
        System.out.println("Welcome to "+BANK+" "+APP+"\n   1. Create Account\n   2. User Login\n   3. Admin login\n   4. Exit the app\nEnter an option to continue: ");
    }
    public void UiClient(){
        System.out.println("Welcome to "+APP+" User panel\n   1. Show current balance\n   2. Display account information\n   3. Withdraw\n   4. Deposit\n   5. Delete your account\n   6. Logout\n   7. Exit the app\nEnter an option to continue: ");
    }
    public void UiAdmin(){
        System.out.println("Welcome to "+APP+" Admin panel\n   1. Search for an account\n   2. Remove an account\n   3. Logout\n   4. Exit the app\nEnter an option to continue: ");
    }
}
class Admin extends Account implements Serializable{
    Admin(){
        this.setName("admin");
        this.setPassword("admin");
    }
    public int AdminLogin() throws Exception {
        int temp = -1;
        String tempID="";
        String tempPassword="";
        Scanner scan = new Scanner(System.in);
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter Admin ID: ");
            tempID = scan.nextLine();
            if(tempID.equals("")){
                System.out.println("Invalid input, please try again!");
                continue;
            }
            else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Invalid input, please try again!");
                scan.nextLine();
          }
        }
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter Admin password: ");
            tempPassword = scan.nextLine();
            if(tempPassword.equals("")){
                System.out.println("Invalid input, please try again!");
                continue;
            }
            else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Invalid input, please try again!");
                scan.nextLine();
          }
        }
        if( (tempID.equals(this.getName())) && (tempPassword.equals(this.getPassword())) ){
            temp = 1;
        }
        else{
            temp = -1;
        }
        return temp;
    }
    public void AdminRemove() throws Exception {
        Scanner scan = new Scanner(System.in);
        int RemoveAcc=0;
        for(boolean exit=false;exit==false;){
          try{
            System.out.print("Enter an account number to remove: ");
            RemoveAcc = scan.nextInt();
            scan.nextLine();//fix input buffer
                if(RemoveAcc/100000 < 1){
                    System.out.println("Account number must be an integer and within 6 digits, please try again!");
                    continue;
                }
                else{ exit=true; }
          }
          catch(InputMismatchException e){
                System.out.println("Account number must be an integer and within 6 digits, please try again!");
                scan.nextLine();
          }
        }
        File file = new File(RemoveAcc + ".dat");
        if (file.delete()) {
            System.out.println("Account removed successfully by Admin!");
        } else {
            System.out.println("Delete failed!");
        }
    }
    public void SearchAccount() throws Exception {
        Scanner scan = new Scanner(System.in);
        int SearchAcc = 0;
        for (boolean exit = false; exit == false;) {
            try {
                System.out.print("Enter an account number to search: ");
                SearchAcc = scan.nextInt();
                scan.nextLine();//fix input buffer
                if (SearchAcc / 100000 < 1) {
                    System.out.println("Account number must be an integer and within 6 digits, please try again!");
                    continue;
                } else {
                    for (boolean exit2 = false; exit2 == false;) {
                        try ( DataInputStream input = new DataInputStream(new FileInputStream(SearchAcc + ".dat"))) {
                            System.out.println("Account found!");
                            String tempName = input.readUTF();
                            System.out.println("Account holder name: "+tempName);
                            int tempAccNum = input.readInt();
                            System.out.println("Account number: "+tempAccNum);
                            input.readUTF();
                            double tempBalance = input.readDouble();
                            System.out.println("Account balance: "+tempBalance);
                            exit2 = true;
                            exit = true;
                        } catch (FileNotFoundException e) {
                            System.out.println("Account not found, please try again!");
                            exit2 = true;
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Account number must be an integer and within 6 digits, please try again!");
                scan.nextLine();
            }
        }
    }
}

interface Bank {
    String BANK = "Northern Bank Ltd";
    String APP = "TurboBanking";
    public abstract void UiMain();
    public abstract void UiClient();
    public abstract void UiAdmin();
}
        
public class Driver {
    public static void main(String[] args) throws Exception {
        Account account = new Account();
        Account admin = new Admin();
        Scanner scan = new Scanner(System.in);
        int temp=0;
        int choice=0;
        
        account.UiMain();
        for(boolean exit=false;exit==false;){
            try{
                choice = scan.nextInt();
            switch(choice){
                case 1:
                    account.Register();
                    break;
                case 2:
                    temp = account.Login();
                    if(temp!=-1){
                        System.out.flush();
                        System.out.println("Login successful!");
                        System.out.println();
                        account.UiClient();
                        for(boolean exit2=false;exit2==false;){
                            try{
                                choice = scan.nextInt();
                                switch(choice){
                                    case 1:
                                        account.displayBalance(temp);
                                        try{
                                            System.out.flush();
                                            System.out.println();
                                            System.out.println("Press Enter to continue");
                                            System.in.read();
                                            account.UiClient();
                                            break;
                                        }
                                        catch(Exception e){}
                                        break;
                                    case 2:
                                        account.displayInfo(temp);
                                        try{
                                            System.out.flush();
                                            System.out.println();
                                            System.out.println("Press Enter to continue");
                                            System.in.read();
                                            account.UiClient();
                                            break;
                                        }
                                        catch(Exception e){}
                                        break;
                                    case 3:
                                        account.Withdraw(temp);
                                        System.out.flush();
                                        System.out.println();
                                        account.UiClient();
                                        break;
                                    case 4:
                                        account.Deposit(temp);
                                        System.out.flush();
                                        System.out.println();
                                        account.UiClient();
                                        break;
                                    case 5:
                                        account.Removal(temp);
                                        System.out.flush();
                                        System.out.println();
                                        account.UiClient();
                                        break;
                                    case 6:
                                        System.out.flush();
                                        account.UiMain();
                                        exit2 = true;
                                        break;
                                    case 7:
                                        System.out.println("Thanks for using our service!");
                                        System.exit(0);
                                    default:
                                        System.out.flush();
                                        System.out.println("Invalid choice, please try again!");
                                        account.UiClient();
                                        break;
                                }
                            }
                             catch(InputMismatchException e){
                                System.out.flush();
                                System.out.println("Invalid choice, please try again!");
                                account.UiClient();
                                scan.nextLine();
                            }
                        }
                    }
                    else{
                        System.out.flush();
                        System.out.println("Login unsuccessful!");
                        account.UiMain();
                        break;
                    }
                    break;
                case 3:
                    temp = ((Admin)admin).AdminLogin();
                    if(temp==1){
                        System.out.flush();
                        System.out.println();
                        account.UiAdmin();
                        for(boolean exit2=false;exit2==false;){
                            try{
                                choice = scan.nextInt();
                                switch(choice){
                                    case 1:
                                        ((Admin)admin).SearchAccount();
                                        try{
                                            System.out.flush();
                                            System.out.println();
                                            System.out.println("Press Enter to continue");
                                            System.in.read();
                                            account.UiAdmin();
                                            break;
                                        }
                                        catch(Exception e){}
                                        break;
                                    case 2:
                                        ((Admin)admin).AdminRemove();
                                        try{
                                            System.out.flush();
                                            System.out.println();
                                            System.out.println("Press Enter to continue");
                                            System.in.read();
                                            account.UiAdmin();
                                            break;
                                        }
                                        catch(Exception e){}
                                        break;
                                    case 3:
                                        System.out.flush();
                                        account.UiMain();
                                        exit2 = true;
                                        break;
                                    case 4:
                                        System.out.println("Thanks for using our service!");
                                        System.exit(0);
                                    default:
                                        System.out.flush();
                                        System.out.println("Invalid choice, please try again!");
                                        account.UiAdmin();
                                        break;
                                }
                            }
                             catch(InputMismatchException e){
                                System.out.flush();
                                System.out.println("Invalid choice, please try again!");
                                account.UiAdmin();
                                scan.nextLine();
                            }
                        }
                    }
                    else{
                        System.out.flush();
                        System.out.println("Admin login unsuccessful!");
                        account.UiMain();
                        break;
                    }
                    break;
                case 4:
                    System.out.println("Thanks for using our service!");
                    System.exit(0);
                default:
                    System.out.flush();
                    System.out.println("Invalid choice, please try again!");
                    account.UiMain();
                    break;
                }
            }
            catch(InputMismatchException e){
                System.out.flush();
                System.out.println("Invalid choice, please try again!");
                account.UiMain();
                scan.nextLine();
            }
        }
        
    }
}