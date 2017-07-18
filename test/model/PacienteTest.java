/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bll.Util;
import java.math.BigDecimal;
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
public class PacienteTest {
    
    private Paciente p;
    private Cliente cliente;
    
    public PacienteTest() {
        p = null;
        cliente = null;
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
        p.setNome("Bobby");
        p.setSexo("Macho");
        p.setRaca("Raça");
        p.createT();
       
        List<Paciente> ps = new ArrayList<>();
        ps.add(p);
        cliente.setListaPacientes(ps);
        
    }
    
    @After
    public void tearDown() throws SQLException{
       Connection conn = Util.criarConexao();
       
       String sqlCommand = "DELETE FROM CONSULTA";
       PreparedStatement pst = conn.prepareStatement(sqlCommand);
       pst.execute();
       
       sqlCommand = "DELETE FROM PACIENTE";
       pst = conn.prepareStatement(sqlCommand);
       pst.execute();
       
       sqlCommand = "DELETE FROM CLIENTE";
       pst = conn.prepareStatement(sqlCommand);
       pst.execute();
    }

    /**
     * Test of getIdCliente method, of class Paciente.
     */
    @Test
    public void testGetCliente() {
        Paciente paciente = new Paciente();
        paciente.setEspecie("Cavalo");
        paciente.setCor("Castanho");
        paciente.setDatanasc(new Date());
        paciente.setEstado((short) 1);
        paciente.setFoto(null);
        paciente.setHistorico("Pata aleijada");
        paciente.setNome("Zandra");
        paciente.setSexo("Fêmea");
        paciente.setRaca("Zephyr");
        
        paciente.setIdCliente(cliente);
        paciente.createT();
        
        Paciente result = new Paciente();
        result.readById(paciente.getId());
        
        assertEquals(result.getIdCliente(), cliente);
    }
    
     /**
     * Test of getConsultas method, of class Paciente.
     */
    @Test
    public void testGetConsultas() {
        List<Consulta> expResult = new ArrayList<>();
        
        Consulta c = new Consulta();
        c.setDesctratamento("Nada de tratamentos");
        c.setDatahora(new Date());
        c.setPaciente(p);
        c.setLocal("Fora");
        c.setPago((short) 0);
        c.setValor(BigDecimal.valueOf(30.0));
        c.setEstado((short) 1);
        c.createT();
        
        Consulta c2 = new Consulta();
        c2.setDesctratamento("Injeção");
        c2.setDatahora(new Date());
        c2.setPaciente(p);
        c2.setLocal("Fora");
        c2.setPago((short) 0);
        c2.setValor(BigDecimal.valueOf(30.0));
        c2.setEstado((short) 1);
        c2.createT();
        
        p.addConsulta(c);
        p.addConsulta(c2);
        
        expResult.add(c);
        expResult.add(c2);
        
        
        Paciente pTeste = new Paciente();
        pTeste.readById(p.getId());
        
        List<Consulta> result = pTeste.getListaConsultas();
        
        assertTrue(expResult.size() == result.size());
        
        int position = -1;
        if (result.get(0) == c) position = 1;
        else if (result.get(1) == c) position = 0;
        else fail("Falhou: Faltam consultas");
        
        assertEquals(result.get(position), c2);
    }

    /**
     * Test of equals method, of class Paciente.
     */
    @Test
    public void testEquals() {
        Paciente p2 = new Paciente();
        p2.readById(p.getId());
        
        assertEquals(p2, p);
    }

    @Test
    public void testUpdate() {
        String expResult = "NovoNome";
        p.setNome(expResult);
        
        Paciente p2 = new Paciente();
        p2.readById(p.getId());
        
        assertEquals(expResult, p2.getNome());
    }
    
    /**
     * Test of create method, of class Paciente.
     */
    @Test
    public void testCreateAndRead() {
        System.out.println("create");
        Paciente p1 = new Paciente();
        p1.setEspecie("Peixe");
        p1.setCor("Azul");
        p1.setDatanasc(new Date());
        p1.setEstado((short) 1);
        p1.setIdCliente(cliente);
        p1.setFoto(null);
        p1.setHistorico("Nunca houve nada");
        p1.setNome("Rufus");
        p1.setSexo("Macho");
        p1.setRaca("Raça");
       
        int id = p1.createT();
        
        Paciente result = new Paciente();
        result.readById(id);
        
        Paciente expectedResult = p1;
       
        assertEquals(expectedResult, result);
    }

    /**
     * Test of remove method, of class Paciente.
     */
    @Test
    public void testRemove() {        
        Paciente paciente = new Paciente();
        paciente.setEspecie("Gatoa");
        paciente.setCor("Branco");
        paciente.setDatanasc(new Date());
        paciente.setEstado((short) 1);
        paciente.setIdCliente(cliente);
        paciente.setFoto(null);
        paciente.setHistorico("Nunca houve nada");
        paciente.setNome("Rato");
        paciente.setSexo("Macho");
        paciente.setRaca("Raça");
        paciente.create();
        
        paciente.remove(paciente.getId());
        
        Paciente checkPaciente = new Paciente();
        try {
            checkPaciente.readById(paciente.getId());
            fail();
        } catch (NoResultException n) {
           List<Paciente> pacientes = Paciente.retrivePacientesVivos();
           assertFalse(pacientes.contains(paciente));
        }
        
    }
    
    /**
     * Test of readByID method, of class Paciente.
     */
    @Test
    public void testReadById() {
        
        int id = p.getId();
        
        Paciente expResult = p;
        
        Paciente result = new Paciente();
        result.readById(id);
        
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testRetrievePacientesVivos() { 
        Paciente p2 = new Paciente();
        
        p2.setEspecie("Cavalo");
        p2.setCor("Castanho");
        p2.setDatanasc(new Date());
        p2.setEstado((short) 0);
        p2.setIdCliente(cliente);
        p2.setFoto(null);
        p2.setHistorico("Ataque cardíaco");
        p2.setNome("ShadowFax");
        p2.setSexo("Macho");
        p2.setRaca("Raça");
        p2.createT();
        
        List<Paciente> pacientes = Paciente.retrivePacientesVivos();
        
        assertTrue(pacientes.size() == 1);
        assertEquals(pacientes.get(0), p);
    }
    
    @Test
    public void testRetrievePacientesByName() { 
        Paciente p2 = new Paciente();
        p2.setEspecie("Cavalo");
        p2.setCor("Castanho");
        p2.setDatanasc(new Date());
        p2.setEstado((short) 0);
        p2.setIdCliente(cliente);
        p2.setFoto(null);
        p2.setHistorico("Ataque cardíaco");
        p2.setNome("ShadowFax");
        p2.setSexo("Macho");
        p2.setRaca("Raça");
        p2.createT();
        
        Paciente p3 = new Paciente();
        p3.setEspecie("Cavalo");
        p3.setCor("Castanho");
        p3.setDatanasc(new Date());
        p3.setEstado((short) 0);
        p3.setIdCliente(cliente);
        p3.setFoto(null);
        p3.setHistorico("Ataque cardíaco");
        p3.setNome("Marreco");
        p3.setSexo("Macho");
        p3.setRaca("Raça");
        p3.createT();
                
        List<Paciente> pacientes = Paciente.retrievePacientesbyNome(p2.getNome());
        
        assertTrue(pacientes.size() == 1);
        assertEquals(pacientes.get(0), p2);
    }
    
}
