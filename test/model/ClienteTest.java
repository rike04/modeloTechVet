/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bll.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rike4
 */
public class ClienteTest {
    
    private Cliente cliente;
    private Paciente p;
    private List<Venda> vendas;
    private List<Paciente> pacientes;
    
    public ClienteTest() {
        cliente = null;
        p = null;
        vendas = null;
        pacientes = null;
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException{
       Connection conn = Util.criarConexao();
       
       String sqlCommand = "DELETE FROM PACIENTE";
       PreparedStatement pst = conn.prepareStatement(sqlCommand);
       pst.execute();
       
       sqlCommand = "DELETE FROM CLIENTE";
       pst = conn.prepareStatement(sqlCommand);
       pst.execute();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        cliente = new Cliente();
        cliente.setNome("Henrique Faria");
        cliente.setMorada("Viana do Castelo");
        cliente.setNif("17778291");
        cliente.setEmail("henriquefaria@ipvc.pt");
        cliente.setTelemovel("927373737");
        cliente.createT();
        
        p = new Paciente();
        p.setEspecie("Cão");
        p.setCor("Azul");
        p.setDatanasc(new Date());
        p.setEstado((short) 1);
        p.setIdCliente(cliente);
        p.setFoto(null);
        p.setHistorico("Nunca houve nada");
        p.setNome("Bobby1234");
        p.setSexo("Macho");
        p.setRaca("Raça");
        p.createT();        
        cliente.getPacienteCollection().add(p);
    }
    
    @After
    public void tearDown() throws SQLException{
        Connection conn = Util.criarConexao();
        String sqlCommand = "DELETE FROM PACIENTE";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);
        pst.execute();
        
        sqlCommand = "DELETE FROM CLIENTE";
        pst = conn.prepareStatement(sqlCommand);
        pst.execute();
    }

    /**
     * Test of equals method, of class Cliente.
     */
    @Test
    public void testGetPacienteCollection() {
        List<Paciente> expResult = new ArrayList<>();
        
        Paciente p2 = new Paciente();
        p2.setEspecie("Gato");
        p2.setCor("Branco");
        p2.setDatanasc(new Date());
        p2.setEstado((short) 0);
        p2.setIdCliente(cliente);
        p2.setFoto(null);
        p2.setHistorico("Faleceu");
        p2.setNome("Marreco");
        p2.setSexo("Macho");
        p2.setRaca("Normal");
        p2.createT();     
        
        cliente.getPacienteCollection().add(p2);
        
        expResult.add(p);
        expResult.add(p2);
        
        Cliente cl = new Cliente();
        cl.read(cliente.getId());
        
        assertTrue(cl.getPacienteCollection().size() == expResult.size());
        
        List<Paciente> lista = cl.getPacienteCollection();
        
        int position = -1;
        if (lista.get(0).equals(p)) position = 1;
        else if (lista.get(1).equals(p)) position = 0;
        else fail("Falhou: Faltam pacientes.");
        
        assertEquals(lista.get(position), p2);
    }

    @Test
    public void testUpdate() {
        String previousResult = cliente.getNome();
        String expResult = "NovoNome";
        cliente.setNome(expResult);
        
        Cliente alterarCliente = new Cliente();
        alterarCliente.read(cliente.getId());
        
        String result = alterarCliente.getNome();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Cliente.
     */
    @Test
    public void testEquals() {
        Cliente c = new Cliente();
        c.read(cliente.getId());
        assertEquals(c, cliente);
    }

    /**
     * Test of create method, of class Cliente.
     */
    @Test
    public void testCreateAndRead() {
      System.out.println("create");
        Cliente cli = new Cliente();
        cli.setNome("McHarden");
        cli.setMorada("Afife");
        cli.setEmail("qwerty@gmail.com");
        cli.setTelemovel("92929292");
        cli.setNif("123132123");
        int id = cli.createT();
        System.out.println("ID = " + id);
        
        Cliente newcli = new Cliente();
        newcli.read(id);
        
        String expectedResult = cli.getNome();
        String result = newcli.getNome();
        assertEquals(expectedResult, result);  
    }

    /**
     * Test of read method, of class Cliente.
     */
    @Test
    public void testRead() {
        int id = cliente.getId();
        
        Cliente novo = new Cliente();
        novo.read(id);
        
        assertEquals(novo, cliente);
    }

    /**
     * Test of readByNome method, of class Cliente.
     */
    @Test
    public void testReadByNome() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jesus");
        cliente.setMorada("Viana do Castelo");
        cliente.setNif("17778291");
        cliente.setEmail("henriquefaria@ipvc.pt");
        cliente.setTelemovel("927373737");
        cliente.createT();
        List<Cliente> listaClientes =
                cliente.readByNome("Jesus");
        
        Cliente retrievedClient = listaClientes.get(0);
        assertEquals(retrievedClient, cliente);    
    }

    /**
     * Test of retrieveAll method, of class Cliente.
     */
    @Test
    public void testRetrieveAll() {
        List<Cliente> expResult = new ArrayList<>();
        
        Cliente cli2 = new Cliente();
        cli2.setNome("Mario");
        cli2.setMorada("Viana do Castelo");
        cli2.setNif("17778291");
        cli2.setEmail("henriquefaria@ipvc.pt");
        cli2.setTelemovel("927373737");
        cli2.createT();
        
        expResult.add(cli2);
        expResult.add(cliente);
        
        List<Cliente> lista = Cliente.retrieveAll();

        assertTrue(lista.size() == expResult.size());
        
        int position = -1;
        if (lista.get(0) == cliente) position = 1;
        else if (lista.get(1) == cliente) position = 0;
        else fail("Falhou: Faltam clientes");
        
        assertTrue(lista.get(position) == cli2);
    }
    
    @Test
    public void testRemove() {
        Cliente cli1 = new Cliente();
        cli1.setNome("Jonas");
        cli1.setMorada("Viana do Castelo");
        cli1.setNif("17778291");
        cli1.setEmail("henriquefaria@ipvc.pt");
        cli1.setTelemovel("927373737");
        cli1.create();
        
        cli1.remove(cli1.getId());
        
        Cliente checkCliente = new Cliente();
        try {
            checkCliente.read(cli1.getId());
            fail();
        } catch (NoResultException n) {
           List<Cliente> clientes = checkCliente.readByNome(cli1.getNome());
           assertTrue(clientes.isEmpty());
        }
    }
    
    
}
