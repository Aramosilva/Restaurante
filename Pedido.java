import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
public class Pedido
{
   private int idPedido;
   private Date data;
   private Double valor;
   public Pedido()
   {
      
   }
   public Pedido(int idPedido, Date data, Double valor)
   {
      this.idPedido = idPedido;
      this.data = data;
      this.valor = valor;
   }
   public int getIdPedido()
   {
      return idPedido;
   }
   public void setIdPedido (int idPedido)
   {
      this.idPedido = idPedido;
   }
   public Date getData()
   {
      return data;
   }
   public void setData(Date data)
   {
      this.data = data;
   }

   public Double getValor()
   {
      return valor;
   }
   public void setValor(Double valor)
   {
      this.valor = valor;
   }
/**
* Inclusao de pedidos
*/
   public void incluir(int idCliente, Connection conn)
   {
      String sqlInsert = "INSERT INTO pedido(id, data, valor, id_cliente) VALUES (?, ?,?, ?)";
      PreparedStatement stm = null;
      try
      {
         stm = conn.prepareStatement(sqlInsert);
         stm.setInt(1, getIdPedido());
         stm.setDate(2, new java.sql.Date(getData().getTime()));
         stm.setDouble(3, getValor());
         stm.setInt(4, idCliente);
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
   
   public void excluir(Connection conn)
   {
      String sqlDelete = "DELETE FROM pedido WHERE id = ?";
      PreparedStatement stm = null;
      try
      {
         stm = conn.prepareStatement(sqlDelete);
         stm.setLong(1, getIdPedido());
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
   
   public ArrayList<Pedido> carregar(int idCliente, Connection conn)
   {
      String sqlSelect = "SELECT * FROM pedido WHERE id_cliente = ?";
      PreparedStatement stm = null;
      ResultSet rs = null;
      ArrayList<Pedido> lp = new ArrayList<Pedido>();
      try
      {
         stm = conn.prepareStatement(sqlSelect);
         stm.setInt(1, idCliente);
         rs = stm.executeQuery();
         while (rs.next())
         {
            Pedido p = new Pedido();
            p.setIdPedido(rs.getInt(1));
            p.setData(rs.getDate(2));
            p.setValor(rs.getDouble(3));
            lp.add(p);
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
      return (lp);
   }
}