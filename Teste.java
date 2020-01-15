import java.sql.SQLException;
import java.sql.Connection;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;
public class Teste
{
/**
* Programa principal
*/
   public static void main(String[] args)
   {
        
      Connection conn = null;
      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      Cliente cl;
      Pedido pd;
      try
      {
      // obtem conexao com o banco
         AcessoBD bd = new AcessoBD();
         conn = bd.obtemConexao();
      // *** IMPORTANTE ***: Força o uso de transação.
         conn.setAutoCommit(false);
      // *** Inclusao do Primeiro Cliente ***
         cl = new Cliente(1001, "Zé das Couves", "1127991999");
         pd = new Pedido(10001, (Date)formatter.parse("29/01/2009"), 150.00);
         cl.adicionaPedido(pd);
         pd = new Pedido(10002, (Date)formatter.parse("15/03/2009"), 100.00);
         cl.adicionaPedido(pd);
         pd = new Pedido(10003, (Date)formatter.parse("18/06/2009"), 75.00);
         cl.adicionaPedido(pd);
         cl.incluir(conn);
      // *** Inclusao do Segundo Cliente ***
         cl = new Cliente();
         cl.setIdCliente(1002);
         cl.setNome("João das Couves");
         cl.setFone("1160606161");
         pd = new Pedido(10004, (Date)formatter.parse("01/03/2009"), 250.00);
         cl.adicionaPedido(pd);
         pd = new Pedido(10005, (Date)formatter.parse("15/08/2009"), 400.00);
         cl.adicionaPedido(pd);
         cl.incluir(conn);
         JOptionPane.showMessageDialog(null, cl.mostrar(conn), "Dados", 1);
      // *** Inclusao do Terceiro Cliente ***
         cl = new Cliente(1003, "Maria das Couves", "1121212121");
         pd = new Pedido(10006, (Date)formatter.parse("20/09/2009"), 650.00);
         cl.adicionaPedido(pd);
         cl.incluir(conn);
      
      // *** IMPORTANTE ***: Efetiva inclusões
         conn.commit();
      // *** Carregar o cliente 1002 a partir do bd ***
         int resultado = JOptionPane.showConfirmDialog(null, "tem certeza que deseja exluir?", "Deletar", JOptionPane.YES_NO_OPTION);
         if(resultado == JOptionPane.YES_OPTION)
         {
         cl = new Cliente(1002);
         cl.carregar(conn);
      // *** Excluir o cliente 1002 (carregado em cl) do bd
         cl.excluir(conn);
         }
         else
         {
            System.exit(0);
         }
      // *** IMPORTANTE ***: Efetiva exclusão
         conn.commit();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         if (conn != null)
         {
            try
            {
               conn.rollback();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
      finally
      {
         if (conn != null)
         {
            try
            {
               conn.close();
            }
            catch (SQLException e1)
            {
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
}