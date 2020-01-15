import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Crud extends JFrame {

	private JRadioButtonMenuItem buttonCliente[], buttonPedido[];
	private ButtonGroup clienteGrupo, pedidoGrupo;
	private JLabel tela;
	private String cliente[] = { "Inserir", "Alterar", "Consultar", "Excluir" };

   DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

     Connection conn = null;
     	 
      private ArrayList<Pedido2> listaDePedidos;
	
	Calendar dateTime = Calendar.getInstance();
   

	public Crud() {
		super("Dados de Pedidos e Clientes");
		char a[] = { '1', '2', '3','4', '0'};
		MostrarC mostrarC = new MostrarC();
      

      
		// Sair
		JMenu mSair = new JMenu("Sair");
		mSair.setMnemonic('S');
		mSair.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				System.exit(0);
			}
		}
    );

		// Cliente

		JMenu mCliente = new JMenu("Dados Cliente");
		mCliente.setMnemonic('C');

		buttonCliente = new JRadioButtonMenuItem[cliente.length];
		clienteGrupo = new ButtonGroup();

		

		for (int count = 0; count < cliente.length; count++) {

			buttonCliente[count] = new JRadioButtonMenuItem(count + 1 + ". " + cliente[count]);

			mCliente.add(buttonCliente[count]);

			clienteGrupo.add(buttonCliente[count]);
			buttonCliente[count].setMnemonic(a[count]);
			buttonCliente[count].addActionListener(mostrarC);

		} // fim do for

		//Hora

			
		
		JMenuBar bar = new JMenuBar();

		setJMenuBar(bar);
		bar.add(mCliente);

		bar.add(mSair);

		tela = new JLabel("", SwingConstants.CENTER);
		add(tela, BorderLayout.CENTER);
		tela.setFont(new Font("Times New Roman", Font.PLAIN, 18));

	}
   
   Cliente2 cl;
   Pedido2 pd;


	private class MostrarC implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			String a = "";
         	String b = "";
			
         {
            //********************INCLUIR************************
            
				if (buttonCliente[0].isSelected()) {
            
             try
      {
      // obtem conexao com o banco
         AcessoBD bd = new AcessoBD();
         conn = bd.obtemConexao();
      // *** IMPORTANTE ***: Força o uso de transação.
         conn.setAutoCommit(false);
      // *** Inclusao do Primeiro Cliente ***
         cl = new Cliente2(1001, "Zé das Couves", "1127991999");
         pd = new Pedido2(10001, (Date)formatter.parse("29/01/2009"), 150.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10002, (Date)formatter.parse("15/03/2009"), 100.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10003, (Date)formatter.parse("18/06/2009"), 75.00);
         cl.adicionaPedido(pd);
         cl.incluir(conn);
      // *** Inclusao do Segundo Cliente ***
         cl = new Cliente2();
         cl.setIdCliente(1002);
         cl.setNome("João das Couves");
         cl.setFone("1160606161");
         pd = new Pedido2(10004, (Date)formatter.parse("01/03/2009"), 250.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10005, (Date)formatter.parse("15/08/2009"), 400.00);
         cl.adicionaPedido(pd);
         cl.incluir(conn);
      // *** Inclusao do Terceiro Cliente ***
         cl = new Cliente2(1003, "Maria das Couves", "1121212121");
         pd = new Pedido2(10006, (Date)formatter.parse("20/09/2009"), 650.00);
         cl.adicionaPedido(pd);
         cl.incluir(conn);
      // *** IMPORTANTE ***: Efetiva inclusões
         conn.commit();
    
      
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
      

             
               
					a = String.format("Dados Inseridos com Sucesso");
					tela.setText(a);
               
               
           //************************** Alterar *********************
					
				} else if (buttonCliente[1].isSelected()) {
           
        try
       {
      // obtem conexao com o banco
         AcessoBD bd = new AcessoBD();
         conn = bd.obtemConexao();
      // *** IMPORTANTE ***: Força o uso de transação.
         conn.setAutoCommit(false);
      // *** Inclusao do Primeiro Cliente ***
           cl = new Cliente2(1001, "Zé das Couves", "0123456789");
         pd = new Pedido2(10001, (Date)formatter.parse("29/01/2009"), 130.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10002, (Date)formatter.parse("15/03/2009"), 100.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10003, (Date)formatter.parse("18/06/2009"), 75.00);
         cl.adicionaPedido(pd);
     
           
         
           JOptionPane.showMessageDialog(null, cl.mostrar(conn), "Dados", 1);

      // *** IMPORTANTE ***: Efetiva inclusões
         conn.commit();
         
        

      // *** Carregar o cliente 1002 a partir do bd ***
        int resultado = JOptionPane.showConfirmDialog(null, "tem certeza que deseja Alterar?", "Alterar", JOptionPane.YES_NO_OPTION);
         if(resultado == JOptionPane.YES_OPTION)
         {
         cl = new Cliente2(1001);
         cl.carregar(conn);
      // *** Excluir o cliente 1002 (carregado em cl) do bd
         cl.alterar(conn);
         }
         else
         {
            System.exit(0);
         }
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

					a = String.format("Dados alterados com sucesso");
					tela.setText(a);
				

			
					
               
           //************************** Consultar *********************
               
				} else if (buttonCliente[2].isSelected()) {
            
               
          try
          {
      // obtem conexao com o banco
         AcessoBD bd = new AcessoBD();
         conn = bd.obtemConexao();
      // *** IMPORTANTE ***: Força o uso de transação.
         conn.setAutoCommit(false);
      // *** Inclusao do Primeiro Cliente ***
         cl = new Cliente2(1001, "Zé das Couves", "1127991999");
         pd = new Pedido2(10001, (Date)formatter.parse("29/01/2009"), 150.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10002, (Date)formatter.parse("15/03/2009"), 100.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10003, (Date)formatter.parse("18/06/2009"), 75.00);
         cl.adicionaPedido(pd);
         a = "<html>" + String.format(cl.consultar(conn)) + "<br>";
                
      // *** Inclusao do Segundo Cliente ***
         cl = new Cliente2();
         cl.setIdCliente(1002);
         cl.setNome("João das Couves");
         cl.setFone("1160606161");
         pd = new Pedido2(10004, (Date)formatter.parse("01/03/2009"), 250.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10005, (Date)formatter.parse("15/08/2009"), 400.00);
         cl.adicionaPedido(pd);
          a = a + String.format(cl.consultar(conn))+ "<br>";
      // *** Inclusao do Terceiro Cliente ***
         cl = new Cliente2(1003, "Maria das Couves", "1121212121");
         pd = new Pedido2(10006, (Date)formatter.parse("20/09/2009"), 650.00);
         cl.adicionaPedido(pd);
         a = a + String.format(cl.consultar(conn))+ "<br>";
         
      
				tela.setText(a);
    
      
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
               
               
                             
				
               
            }
            
            
            
         //****************************EXCLUIR************************************   
         
      else if (buttonCliente[3].isSelected()) {
            
               
      try
      {
      // obtem conexao com o banco
         AcessoBD bd = new AcessoBD();
         conn = bd.obtemConexao();
      // *** IMPORTANTE ***: Força o uso de transação.
         conn.setAutoCommit(false);
    
         cl = new Cliente2();
         cl.setIdCliente(1002);
         cl.setNome("João das Couves");
         cl.setFone("1160606161");
         pd = new Pedido2(10004, (Date)formatter.parse("01/03/2009"), 250.00);
         cl.adicionaPedido(pd);
         pd = new Pedido2(10005, (Date)formatter.parse("15/08/2009"), 400.00);
         cl.adicionaPedido(pd);
     
         
           JOptionPane.showMessageDialog(null, cl.mostrar(conn), "Dados", 1);

      // *** IMPORTANTE ***: Efetiva inclusões
         conn.commit();
         
        

      // *** Carregar o cliente 1002 a partir do bd ***
        int resultado = JOptionPane.showConfirmDialog(null, "tem certeza que deseja exluir?", "Deletar", JOptionPane.YES_NO_OPTION);
         if(resultado == JOptionPane.YES_OPTION)
         {
         cl = new Cliente2(1002);
         cl.carregar(conn);
      // *** Excluir o cliente 1002 (carregado em cl) do bd
         cl.excluir(conn);
         }
         else
         {
            System.exit(0);
         }
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

					a = String.format("Dados exluidos com sucesso");
					tela.setText(a);
				}
            
           			

			}
		}

	}
	
}