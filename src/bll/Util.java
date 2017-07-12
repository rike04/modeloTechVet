/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 *
 * @author PC
 */
public class Util {
    private static Connection conn = null;

    public static Connection criarConexao() {
        if (conn != null) {
            return conn;
        } else {

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("Oops! Can't find class oracle.jdbc.driver.OracleDriver");
                System.exit(-1);
            }

            try {
                conn = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "testeJunit", "palavrapasse");
            } catch (Exception e) {
                System.out.println("ERRO " + e.getMessage());
                System.exit(-2);
            }
            return conn;
        }
    }
    
    public static LocalDate dateToLocal(Date data) {
        Instant instant = data.toInstant();
        ZoneId zoneID = ZoneId.of("Europe/Lisbon");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneID);
        LocalDate localDate = zdt.toLocalDate();
        return localDate;
    }
    
}
