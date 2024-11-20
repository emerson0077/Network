import java.io.*;
import java.net.*;
public class netprac6client{
   public static void main(String[] args){
          String sa="localhost";
          int port=12345;
          try(Socket s=new Socket(sa,port);
              BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
              PrintWriter out = new PrintWriter(s.getOutputStream(),true);
              BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))){
                 System.out.println("Client connected at "+port);
                 String ui;
                 while(true){
                    System.out.print("enter command(ECHO<message>, PING<address>, TIME, EXIT): ");
                    ui=stdIn.readLine();
                    if(ui.equalsIgnoreCase("EXIT")){
                           out.println(ui);
                           break;
                    }
                    out.println(ui);
                    String r;
                    while((r=in.readLine())!=null){
                        System.out.println("Server response: "+r);
                        if (r.equals("---END OF PING---") ||
                        r.equals("---END OF ECHO---") ||
                        r.equals("---END OF TIME---")) {
                        break;
                    }
                        if(r.startsWith("Goodbye!") || r.startsWith("Unknown Command")){
                             break;
                        }
                    }
                 }
              }
            catch(IOException e){
                System.out.println("CLient error: "+e.getMessage());
             }
         }
     }
                         
              