import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class Cliente2
{
   private int idCliente;
   private String nome;
   private String fone;
   private ArrayList<Pedido2> listaDePedidos;
   Connection conn = null;
   DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
   Cliente2 cl;
   Pedido2 pd;
   public Cliente2()
   {
      listaDePedidos = new ArrayList<Pedido2>();
      
        }

   
   public Cliente2(int idCliente)
   {
      this.idCliente = idCliente;
      listaDePedidos = new ArrayList<Pedido2>();
   }
   public Cliente2(int idCliente, String nome, String fone)
   {
      this.idCliente = idCliente;
      this.nome = nome;
      this.fone = fone;
      listaDePedidos = new ArrayList<Pedido2>();
   }
   public int getIdCliente()
   {
      return idCliente;
   }
   public void setIdCliente (int idCliente)
   {
      this.idCliente = idCliente;
   }
   public String getNome()
   {
      return nome;
   }
   public void setNome(String nome)
   {
      this.nome = nome;
   }
   public String getFone()
   {
      return fone;
   }
   public void setFone(String fone)
   {
      this.fone = fone;
   }
   public ArrayList<Pedido2> getListaDePedidos()
   {
      return listaDePedidos;
   }
   public void setListaDePedidos(ArrayList<Pedido2> listaDePedidos)
   {
      this.listaDePedidos = listaDePedidos;
   }
   public void adicionaPedido(Pedido2 novoPedido)
   {
      listaDePedidos.add(novoPedido);
   }
/**
* Inclusao de clientes
*/
   public void incluir(Connection conn)
   {
      String sqlInsert = "INSERT INTO cliente(id, nome, fone) VALUES (?, ?, ?)";
      PreparedStatement stm = null;
      try
      {
      //
      // Inclusao dos dados na tabela CLIENTE
      //
         stm = conn.prepareStatement(sqlInsert);
         stm.setInt(1, getIdCliente());
         stm.setString(2, getNome());
         stm.setString(3, getFone());
         stm.execute();
      //
      // Inclusao dos PEDIDOS do cliente
      //
         int i = 0;
         while (i < listaDePedidos.size())
         {
            Pedido2 p = (Pedido2) listaDePedidos.get(i);
            p.incluir(getIdCliente(), conn);
            i++;
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
   
   public boolean inserir(Connection conn)
   {
     
      boolean inserir =  false;
      
      String sqlInsert = "INSERT INTO cliente(id, nome, fone) VALUES (?, ?, ?)";
      PreparedStatement stm = null;
      try
      {
      //
      // Inclusao dos dados na tabela CLIENTE
      //
         stm = conn.prepareStatement(sqlInsert);
         stm.setInt(1, getIdCliente());
         stm.setString(2, getNome());
         stm.setString(3, getFone());
         stm.execute();
         inserir = true;
      //
      // Inclusao dos PEDIDOS do cliente
      //
         int i = 0;
         while (i < listaDePedidos.size())
         {
            Pedido2 p = (Pedido2) listaDePedidos.get(i);
            p.incluir(getIdCliente(), conn);
            i++;
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
      
      return inserir;
   }

   public void excluir(Connection conn)
   {
      String sqlDelete = "DELETE FROM cliente WHERE id = ?";
      PreparedStatement stm = null;
      try
      {
         int i = 0;
         while (i < listaDePedidos.size())
         {
            Pedido2 p = (Pedido2) listaDePedidos.get(i);
            p.excluir(conn);
            i++;
         }
         stm = conn.prepareStatement(sqlDelete);
         stm.setInt(1, getIdCliente());
         stm.execute();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
   
   
      public void alterar(Connection conn)
   {
      String sqlUpdate = "UPDATE cliente SET fone = '0123456789' WHERE id = ?";
      PreparedStatement stm = null;
      try
      {
         int i = 0;
         while (i < listaDePedidos.size())
         {
            Pedido2 p = (Pedido2) listaDePedidos.get(i);
            p.alterar(conn);
            i++;
         }
         stm = conn.prepareStatement(sqlUpdate);
         stm.setInt(1, getIdCliente());
         stm.execute();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
   
   
   
   public void carregar(Connection conn)
   {
      String sqlSelect = "SELECT * FROM cliente WHERE cliente.id = ?";
      PreparedStatement stm = null;
      ResultSet rs = null;
      try
      {
         stm = conn.prepareStatement(sqlSelect);
         stm.setInt(1, getIdCliente());
         rs = stm.executeQuery();
         if (rs.next())
         {
            this.setNome(rs.getString(2));
            this.setFone(rs.getString(3));
            Pedido2 p = new Pedido2();
            this.setListaDePedidos(p.carregar(idCliente, conn));
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
   
   
   public String mostrar(Connection conn)
   {
      String dados = "";
      String sqlSelect = "SELECT * FROM cliente WHERE cliente.id = ?";
      PreparedStatement stm = null;
      ResultSet rs = null;
      try
      {
         stm = conn.prepareStatement(sqlSelect);
         stm.setInt(1, getIdCliente());
         rs = stm.executeQuery();
         if (rs.next())
         {
            this.setNome(rs.getString(2));
            dados+= rs.getString(2)+ "\n";
            this.setFone(rs.getString(3));
            dados+= rs.getString(3);
            Pedido2 p = new Pedido2();
            this.setListaDePedidos(p.carregar(idCliente, conn));
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
      
      return dados;
   }
   
   
   
    public String consultarP(Connection conn)
    {
      String dados = "";
      String retorno = "Lista Vazia";
      String sqlSelect = "select * from pedido where id = ?";
      PreparedStatement stm = null;

      ResultSet rs =  null;
    
     

      try
      {
         Pedido2 p = new Pedido2();
         
         stm = conn.prepareStatement(sqlSelect);
         stm.setInt(1, getIdCliente());         
         rs = stm.executeQuery();
         
        
         if (rs.next())
         {
            
           
           p.setValor(rs.getDouble(2));
           dados += "Valor: " + (rs.getDouble(2));
            
            p.setData(rs.getDate(3));
            dados += "Data: " + rs.getDate(3);
            
             this.setListaDePedidos(p.carregar(idCliente, conn));
 
          }
          
      }
      
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
               
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
       
      }
      
      return dados;
   }

   
   
   

    public String consultar(Connection conn)
    {
      String dados = "";
      String retorno = "Lista Vazia";
      String sqlSelect = "select * from cliente where id = ?";
      PreparedStatement stm = null;

      ResultSet rs =  null;
      ResultSet result = null;
     

      try
      {
         Pedido2 p = new Pedido2();
         
         stm = conn.prepareStatement(sqlSelect);
         stm.setInt(1, getIdCliente());         
         rs = stm.executeQuery();
         
        
         if (rs.next())
         {
            
            
           
            this.setNome(rs.getString(2));
            dados+= "Nome:  " + rs.getString(2);
            
            this.setFone(rs.getString(3));
            dados+= "         Fone:  " + rs.getString(3) + "\n";
            
             this.setListaDePedidos(p.carregar(idCliente, conn));
             
             

          }
          
          else if(result.next())
          {
            
           p.setIdPedido(result.getInt(2));
           dados += "Id: " + result.getInt(2);
           
           p.setValor(result.getDouble(3));
           dados += "Valor: " + (result.getDouble(3));
            
            p.setData(result.getDate(4));
            dados += "Data: " + result.getDate(4);
          
            }  
                    
      }
      
      catch (Exception e)
      {
         e.printStackTrace();
         try
         {
            conn.rollback();
         }
         catch (SQLException e1)
         {
            System.out.print(e1.getStackTrace());
         }
      }
      finally
      {
         if (stm != null)
         {
            try
            {
               stm.close();
               
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
       
      }
      
      return dados;
   }
      
}