package model;

import bll.Util;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class ConsultaTest {
    
    private Consulta consulta;
    private Paciente p;
    private Cliente cliente;
    private TipoConsulta tipo;
    
    public ConsultaTest() {
        consulta = null;
        p = null;
        cliente = null;
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException{
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
       
       sqlCommand = "DELETE FROM TIPO_CONSULTA";
       pst = conn.prepareStatement(sqlCommand);
       pst.execute();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tipo = new TipoConsulta();
        tipo.setNome("T");
        tipo.setValor(20.0);
        tipo.create();
        
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
        
        consulta = new Consulta();
        consulta.setDesctratamento("Nada de tratamentos");
        consulta.setDatahora(new Date());
        consulta.setIdPaciente(p);
        consulta.setLocal("Casa");
        consulta.setPago((short) 1);
        consulta.setValor(BigDecimal.valueOf(20.0));
        consulta.setEstado((short) 1);
        consulta.setIdTipo(tipo);
        consulta.createT();
        
        tipo.getConsultaCollection().add(consulta);
        p.addConsulta(consulta);
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
       
       sqlCommand = "DELETE FROM TIPO_CONSULTA";
       pst = conn.prepareStatement(sqlCommand);
       pst.execute();
    }

    /**
     * Test of equals method, of class Consulta.
     */
    @Test
    public void testEquals() {
        Consulta expResult = consulta;
        
        Consulta result = new Consulta();
        result.readById(consulta.getId());
        
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of create method, of class Consulta.
     */
    @Test
    public void testCreate() {
        Consulta consult = new Consulta();
        consult.setDesctratamento("Nada de tratamentos");
        consult.setDatahora(new Date());
        consult.setIdPaciente(p);
        consult.setLocal("Fora");
        consult.setPago((short) 0);
        consult.setValor(BigDecimal.valueOf(30.0));
        consult.setEstado((short) 1);
        consult.createT();
        
        p.addConsulta(consult);
        
        Consulta c = new Consulta();
        c.readById(consult.getId());
        
        assertEquals(consult, c);
    }

    /**
     * Test of read method, of class Consulta.
     */
    @Test
    public void testRead() {
        int id = consulta.getId();
        
        Consulta expResult = consulta;
        
        Consulta result = new Consulta();
        result.readById(id);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of readByNome method, of class Consulta.
     */
    @Test
    public void testReadByDate() {
        Date data = new Date();
        Consulta cn = new Consulta();
        cn.setDesctratamento("Doença no estômago");
        cn.setDatahora(data);
        cn.setIdPaciente(p);
        cn.setLocal("Casa");
        cn.setPago((short) 0);
        cn.setValor(BigDecimal.valueOf(12));
        cn.setEstado((short) 0);
        cn.setIdTipo(tipo);
        cn.createT();
        
        LocalDate ldt = LocalDate.parse("2014-03-01");
        Date d = java.sql.Date.valueOf(ldt);
        Consulta cnX = new Consulta();
        cnX.setDesctratamento("Doença na pata");
        cnX.setDatahora(d);
        cnX.setIdPaciente(p);
        cnX.setLocal("Fora");
        cnX.setPago((short) 1);
        cnX.setValor(BigDecimal.valueOf(19));
        cnX.setEstado((short) 0);
        cnX.setIdTipo(tipo);
        cnX.createT();
        
        assertFalse(cnX.getDatahora().equals(cn.getDatahora()));
        
        Consulta teste = new Consulta();
        teste.readById(cn.getId());
        assertEquals(cn, teste);
        
        List<Consulta> lista = Consulta.readByData(cn.getDatahora());
        assertTrue(lista.size() == 2);
        
        int pos = -1;
        if(lista.get(0).equals(consulta)) pos = 1;
        else if (lista.get(1).equals(consulta)) pos = 0;
        else fail();
        
        assertEquals(lista.get(pos), cn);
    }

    /**
     * Test of retrieveAll method, of class Consulta.
     */
    @Test
    public void testRetrieveAll() {        
        Consulta cn = new Consulta();
        cn.setDesctratamento("Doença no estômago");
        cn.setDatahora(new Date());
        cn.setIdPaciente(p);
        cn.setLocal("Casa");
        cn.setPago((short) 0);
        cn.setValor(BigDecimal.valueOf(12));
        cn.setEstado((short) 0);
        cn.setIdTipo(tipo);
        cn.createT();
        
        List<Consulta> consultas = Consulta.retrieveAll();
        assertTrue(consultas.size() == 2);
        
        int position = -1;
        if (consultas.get(0) == consulta) position = 1;
        else if (consultas.get(1) == consulta) position = 0;
        else fail("Falhou: Faltam clientes");
        
        assertTrue(consultas.get(position) == cn);
    }

    /**
     * Test of remove method, of class Consulta.
     */
    @Test
    public void testRemove() {
        Consulta consult = new Consulta();
        consult.setDesctratamento("Nada de tratamentos");
        consult.setDatahora(new Date());
        consult.setIdPaciente(p);
        consult.setLocal("Fora");
        consult.setPago((short) 0);
        consult.setValor(BigDecimal.valueOf(30.0));
        consult.setEstado((short) 1);
        consult.createT();
        
        consult.remove(consult.getId());
        
        Consulta checkConsulta = new Consulta();
        try {
            checkConsulta.readById(consult.getId());
            fail();
        } catch (NoResultException n) {
            
        }
    }
    
    @Test
    public void testUpdate() {
        String expResult = "Novo Tratamento";
        consulta.setDesctratamento(expResult);
        
        Consulta c = new Consulta();
        c.readById(consulta.getId());
        String result = c.getDesctratamento();
        
        
        assertEquals(consulta, c);
        assertEquals(expResult, result);
    }  
    
    
}
