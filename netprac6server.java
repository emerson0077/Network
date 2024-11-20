import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class netprac6server{
  public static void main(String[] args){
     int port=12345;
     try(ServerSocket ss=new ServerSocket(port)){
        System.out.println("Server starting at port "+port);
        while(true){
           Socket cs=ss.accept();
           System.out.println("Client Connected: "+cs.getInetAddress().getHostAddress());
           new ClientHandler(cs).start();
         }
      }
     catch(IOException e){
          System.out.println("Server error:"+e.getMessage());
     }
   }
 }
class ClientHandler extends Thread{
     private Socket cs;
     public ClientHandler(Socket s){
       this.cs=s;
     }
     public void run(){
         try(BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
             PrintWriter out = new PrintWriter(cs.getOutputStream(),true)){
                 String il;
                 while((il=in.readLine())!=null){
                      String cp[] = il.split(" ");
                      String c = cp[0].toUpperCase();
                      switch(c){
                        case "ECHO":
                              out.println("ECHO: "+il.substring(5));
                              out.println("---END OF ECHO---");
                              break;
                        case "PING":
                              if(cp.length>1){
                                   String d=cp[1];
                                   epc(d,out);
                              }
                              else{
                                   out.println("Ping command requires an ipaddress or domain name");
                              }
                              break;
                       case "TIME":
                             LocalDateTime ct=LocalDateTime.now();
                             DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                             out.println("current date and time: "+ct.format(formatter));
                              out.println("---END OF TIME---");
                             break;
                       case "EXIT":
                             out.println("Goodbye!");
                             return;
                       default:
                             out.println("Unknown Command: "+il);
                             break;
                     }
                  }
               }
          catch(IOException e){
               System.out.println("error:"+e.getMessage());
           }
           finally{
                  try{
                      cs.close();
                     }
                   catch(IOException e){
                       System.out.println("Can able to close connection: "+e.getMessage());
                    }
                 }
             }
             private void epc(String d,PrintWriter out){
                try{
                     String p = "ping "+d;
                     Process process=Runtime.getRuntime().exec(p);
                     BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
                     String line;
                     while((line=reader.readLine())!=null){
                         out.println(line);
                     }
                     out.println("---END OF PING---");  // Signal end of PING response
                    }
                catch(IOException e){
                    out.println("Error executing ping command: "+e.getMessage());
                }
              }
            }
                 