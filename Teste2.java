import java.sql.SQLException;
import java.sql.Connection;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class Teste2
{
   public static void main(String[] args)
   {
      Crud c = new Crud(); 
      c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      c.setSize(605, 250); 
      c.setLocation(300, 200);
      c.setVisible(true); 
      
      
     }
}