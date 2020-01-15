import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
public class Cliente
{
   private int idCliente;
   private String nome;
   private String fone;
   private ArrayList<Pedido> listaDePedidos;
   public Cliente()
   {
      listaDePedidos = new ArrayList<Pedido>();
   }
   public Cliente(int idCliente)
   {
      this.idCliente = idCliente;
      listaDePedidos = new ArrayList<Pedido>();
   }
   public Cliente(int idCliente, String nome, String fone)
   {
      this.idCliente = idCliente;
      this.nome = nome;
      this.fone = fone;
      listaDePedidos = new ArrayList<Pedido>();
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
   public ArrayList<Pedido> getListaDePedidos()
   {
      return listaDePedidos;
   }
   public void setListaDePedidos(ArrayList<Pedido> listaDePedidos)
   {
      this.listaDePedidos = listaDePedidos;
   }
   public void adicionaPedido(Pedido novoPedido)
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
            Pedido p = (Pedido) listaDePedidos.get(i);
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
   
   
   public void excluir(Connection conn)
   {
      String sqlDelete = "DELETE FROM cliente WHERE id = ?";
      PreparedStatement stm = null;
      try
      {
         int i = 0;
         while (i < listaDePedidos.size())
         {
            Pedido p = (Pedido) listaDePedidos.get(i);
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
            Pedido p = new Pedido();
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
            Pedido p = new Pedido();
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

}