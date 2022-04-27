import java.sql.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Date;

import com.jcraft.jsch.*;

public class Db_conn {
    JFrame my_jframe;
    int forwardedPort;
    JSch jsch;
    String db_url;
    String userName;
    String databaseUser;
    String serverName;
    String databaseHost;
    String databasePass;
    int port;

    public Db_conn(JFrame frame) {
        my_jframe = frame;
        serverName = "pascal.fis.agh.edu.pl";
        userName = "9gaworek";
        databaseUser = "u9gaworek";
        databaseHost = "localhost";
        databasePass = "9gaworek";
        String password = "removedForSecurityReasons";
        port = 5432;
        jsch = new JSch();
        try {
            Session session = jsch.getSession(userName, serverName, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            forwardedPort = session.setPortForwardingL(0, databaseHost, port);
            db_url = "jdbc:postgresql://localhost:" + forwardedPort + "/" + databaseUser;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error : " + e);
        }
    }

    public List<List<String>> czytelnicyList() {
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement(
                        "SELECT id_czytelnik, imie, nazwisko FROM bazy_projekt.czytelnik",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id_czytelnik");
                    String imie = rs.getString("imie");
                    String nazwisko = rs.getString("nazwisko");
                    String ID = "" + id;
                    result.get(0).add(ID);
                    result.get(1).add(imie);
                    result.get(2).add(nazwisko);
                }
                rs.close();
                pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public List<List<String>> ksiazkiList(int mode) {
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        if (mode == 1 || mode == 2)
            result.add(new ArrayList<String>());
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                String call = "{ call informacje_o_ksiazkach(" + mode + ") }";
                CallableStatement cst = c.prepareCall(call);
                cst.execute();
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String tytul = rs.getString("tytul");
                    String autor = rs.getString("autor");
                    String wydawnictwo = rs.getString("wydawnictwo");
                    String ID = "" + id;

                    result.get(0).add(ID);
                    result.get(1).add(tytul);
                    result.get(2).add(autor);
                    result.get(3).add(wydawnictwo);

                    if (mode == 1 || mode == 2) {
                        String kategoria = rs.getString("kategoria");
                        result.get(4).add(kategoria);
                    }
                }
                rs.close();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                System.out.println("Error sql: " + e);
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public List<List<String>> autorzyList() {
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("SELECT id_autor, imie, nazwisko FROM bazy_projekt.autor",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id_autor");
                    String imie = rs.getString("imie");
                    String nazwisko = rs.getString("nazwisko");

                    String ID = "" + id;

                    result.get(0).add(ID);
                    result.get(1).add(imie);
                    result.get(2).add(nazwisko);
                }
                rs.close();
                pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public List<List<String>> kategorieList() {
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                String call = "{ call kategoria_fun() }";
                CallableStatement cst = c.prepareCall(call);
                cst.execute();
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String tytul = rs.getString(2);
                    String ID = "" + id;

                    result.get(0).add(ID);
                    result.get(1).add(tytul);

                }
                rs.close();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error sql: " + e);
            }
        }
        return result;
    }

    public List<List<String>> wydawnictwaList() {
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("SELECT id_wydawnictwo, nazwa FROM bazy_projekt.wydawnictwo",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id_wydawnictwo");
                    String imie = rs.getString("nazwa");

                    String ID = "" + id;

                    result.get(0).add(ID);
                    result.get(1).add(imie);
                }
                rs.close();
                pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public List<List<String>> rezerwacjeList() {
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement(
                        "SELECT r.id_rezerwacja, id_ksiazka, id_czytelnik, nazwa, data_rezerwacji FROM bazy_projekt.rezerwacja r, bazy_projekt.status_rezerwacji s where s.id_status_rezerwacji = r.id_status_rezerwacji order by id_rezerwacja desc",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int id_ksiazka = rs.getInt(2);
                    int id_czytelnik = rs.getInt(3);
                    String nazwa = rs.getString(4);
                    Date data_rezerwacji = rs.getDate(5);

                    String ID = "" + id;
                    String ID_ksiazka = "" + id_ksiazka;
                    String ID_czytelnik = "" + id_czytelnik;
                    String DATA_rezerwacji = data_rezerwacji.toString();

                    result.get(0).add(ID);
                    result.get(1).add(ID_ksiazka);
                    result.get(2).add(ID_czytelnik);
                    result.get(3).add(nazwa);
                    result.get(4).add(DATA_rezerwacji);
                }
                rs.close();
                pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public List<List<String>> wypozyczeniaList() {
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());

        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement(
                        "SELECT * FROM bazy_projekt.wypozyczenie order by id_wypozyczenie desc",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int id_ksiazka = rs.getInt(2);
                    int id_czytelnik = rs.getInt(3);
                    String tytul = rs.getString(4);
                    Date data_wypozyczenia = rs.getDate(5);
                    Date data_zwrotu = rs.getDate(6);

                    String ID = "" + id;
                    String ID_ksiazka = "" + id_ksiazka;
                    String ID_czytelnik = "" + id_czytelnik;
                    String DATA_wypozyczenia = data_wypozyczenia.toString();
                    String DATA_zwrotu;
                    if (data_zwrotu != null)
                        DATA_zwrotu = data_zwrotu.toString();
                    else
                        DATA_zwrotu = "";

                    result.get(0).add(ID);
                    result.get(1).add(ID_ksiazka);
                    result.get(2).add(ID_czytelnik);
                    result.get(3).add(tytul);
                    result.get(4).add(DATA_wypozyczenia);
                    result.get(5).add(DATA_zwrotu);
                }
                rs.close();
                pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public void insert(String query, String... values) {
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                Statement stmt = c.createStatement();
                query = prepareSQL(query, values);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error : " + e);
            }
        }
    }

    public void insertAndSet(String query, String... values) {
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                Statement stmt = c.createStatement();
                query = prepareSQL(query, values);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error : " + e);
            }
        }
    }

    public int selectInt(String query) {
        int result = -1;
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    result = rs.getInt(1);
                }
                rs.close();
                pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public String prepareSQL(String query, String... values) {
        if (values.length > 0) {
            query += "('" + values[0];
            for (int i = 1; i < values.length; i++) {
                query += "','" + values[i];
            }
            query += "')";
        }
        return query;
    }

    public void callSQLfun(String fun, String... values) {
        Connection c = null;
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                CallableStatement cst = c.prepareCall("{ call " + prepareSQL(fun, values) + " }");
                cst.execute();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error sql: " + e);
            }
        }
    }

    public boolean canReaderBeDeleted(int ID_czytelnik) {
        boolean answer = false;
        Connection c = null;
        String fun = "czy_do_usuniecia";
        String id_czytelnik = Integer.toString(ID_czytelnik);
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                CallableStatement cst = c.prepareCall("{ call " + prepareSQL(fun, id_czytelnik) + " }");
                cst.execute();
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    switch (rs.getString(1)) {
                        case "can be deleted":
                            answer = true;
                            JOptionPane.showMessageDialog(my_jframe, "Usunieto", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        case "not found":
                            JOptionPane.showMessageDialog(my_jframe, "Nie ma osoby o takim id", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        case "not returned":
                            JOptionPane.showMessageDialog(my_jframe,
                                    "Osoba o podanym id nie oddala wszystkich wypozyczonych ksiazek", "Warning",
                                    JOptionPane.ERROR_MESSAGE);

                        default:

                            break;
                    }
                }
                rs.close();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error sql: " + e);
            }
        }
        return answer;
    }

    public boolean doReaderExist(int ID_czytelnik) {
        boolean answer = false;
        Connection c = null;
        String fun = "czy_jest_czytelnik";
        String id_czytelnik = Integer.toString(ID_czytelnik);
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                CallableStatement cst = c.prepareCall("{ call " + prepareSQL(fun, id_czytelnik) + " }");
                cst.execute();
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    switch (rs.getString(1)) {
                        case "found":
                            answer = true;
                            break;

                        case "not found":
                            JOptionPane.showMessageDialog(my_jframe, "Nie ma osoby o takim id", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        default:

                            break;
                    }
                }
                rs.close();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error sql: " + e);
            }
        }
        return answer;
    }

    public boolean doBookExist(String tytul) {
        boolean answer = false;
        Connection c = null;
        String fun = "czy_jest_ksiazka";
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                CallableStatement cst = c.prepareCall("{ call " + prepareSQL(fun, tytul) + " }");
                cst.execute();
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    switch (rs.getString(1)) {
                        case "found":
                            answer = true;
                            break;

                        case "not found":
                            JOptionPane.showMessageDialog(my_jframe, "Nie ma ksiazki o takim tytule", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        default:

                            break;
                    }
                }
                rs.close();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error sql: " + e);
            }
        }
        return answer;
    }

    public boolean isBookBorrowedByThisPerson(int ID_czytelnik, int ID_ksiazka) {
        boolean answer = false;
        Connection c = null;
        String fun = "czy_jest_wypozyczenie";
        String id_czytelnik = Integer.toString(ID_czytelnik);
        String id_ksiazka = Integer.toString(ID_ksiazka);
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                CallableStatement cst = c.prepareCall("{ call " + prepareSQL(fun, id_czytelnik, id_ksiazka) + " }");
                cst.execute();
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    switch (rs.getString(1)) {
                        case "found":
                            answer = true;
                            break;

                        case "not found":
                            JOptionPane.showMessageDialog(my_jframe,
                                    "Podany czytelnik aktulanie nie wypozycza podanej ksiazki", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        default:

                            break;
                    }
                }
                rs.close();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error sql: " + e);
            }
        }
        return answer;
    }

    public boolean isBookFreeToBeBorrowed(String tytul) {
        boolean answer = false;
        Connection c = null;
        String fun = "czy_jest_do_wypozyczenia";
        try {
            c = DriverManager.getConnection(db_url, databaseUser, databasePass);
        } catch (SQLException se) {
        }
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement("set search_path to bazy_projekt");
                pst.execute();
                CallableStatement cst = c.prepareCall("{ call " + prepareSQL(fun, tytul) + " }");
                cst.execute();
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    switch (rs.getString(1)) {
                        case "found":
                            answer = true;
                            break;

                        case "not found":
                            JOptionPane.showMessageDialog(my_jframe,
                                    "Podany czytelnik aktulanie nie wypozycza podanej ksiazki", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        default:

                            break;
                    }
                }
                rs.close();
                pst.close();
                cst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(my_jframe, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error sql: " + e);
            }
        }
        return answer;
    }

}