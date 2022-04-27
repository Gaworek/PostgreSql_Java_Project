import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class gui {
    static class MyFrame extends JFrame{
        private Db_conn db;
        private JPanel middle;
        private JPanel buttons;
        private JPanel helper;
        private JPanel content;
        private JScrollPane tablePane;
        private JMenuBar mb;

        public MyFrame(String title){
            super(title);
            db = new Db_conn(this);
            content = new JPanel();
            middle = new JPanel();
            mb = new JMenuBar();
            buttons = new JPanel();
            helper = new JPanel();

            content.setLayout(new BorderLayout());
            content.add(tablePane = new JScrollPane(new JTable()));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(750, 600);

            JMenu m1 = new JMenu("Dokumentacja");
            mb.add(m1);

            m1.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e){
                    String [] options = {"Tak", "Nie"};
                    int option = JOptionPane.showOptionDialog(getContentPane(), 
                    "Otworzyc dokumentacje?","Zapytanie", JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(option == 0){
                        File file = new File("Dokumentacja_BD1_Wiktor_Gaworek.pdf");
                        if(Desktop.isDesktopSupported()){
                            Desktop desktop = Desktop.getDesktop();
                            if(file.exists()){
                                try {
                                    desktop.open(file);
                                } catch (IOException f) {
                                    f.printStackTrace();
                                }
                            }
                        }
                    }
                    final JMenu menu = (JMenu) e.getSource();
                    new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            menu.setSelected(false);
                            ((Timer)e.getSource()).stop();
                        }
                        
                    }).start();
                }
                @Override
                public void menuDeselected(MenuEvent e) {
                    
                }
                @Override
                public void menuCanceled(MenuEvent e) {
                    
                }

            });

            JButton buttonCzytelnicy = new JButton("Czytelnicy");
            JButton buttonKsiazki = new JButton("Ksiazki");
            JButton buttonKategorie = new JButton("Kategorie");
            JButton buttonAutorzy = new JButton("Autorzy");
            JButton buttonWydawnictwa = new JButton("Wydawnictwa");
            JButton buttonRezerwacje = new JButton("Rezerwacje");
            JButton buttonWypozyczenia = new JButton("Wypozyczenia");

            buttons.setLayout(new FlowLayout());
            buttons.add(buttonCzytelnicy);
            buttons.add(buttonKsiazki);
            buttons.add(buttonKategorie);
            buttons.add(buttonAutorzy);
            buttons.add(buttonWydawnictwa);
            buttons.add(buttonRezerwacje);
            buttons.add(buttonWypozyczenia);

            buttonCzytelnicy.addActionListener(l -> {
                JPanel bttsCzytelnicy = new JPanel(new FlowLayout());
                JLabel nazwa = new JLabel("Czytelnicy: ");
                JButton wyswietl = new JButton("Wyswietl");
                JButton dodaj = new JButton("Dodaj");
                JButton usun = new JButton("Usun");
                bttsCzytelnicy.add(nazwa);
                bttsCzytelnicy.add(wyswietl);
                bttsCzytelnicy.add(dodaj);
                bttsCzytelnicy.add(usun);
                helper.removeAll();
                helper.add(BorderLayout.NORTH, bttsCzytelnicy);
                content.removeAll();
                helper.add(content);
                helper.revalidate();
                wyswietl.addActionListener(w -> {
                    helper.removeAll();
                    List<List<String>> data = db.czytelnicyList();
                    Object[][] dataObjects = {data.get(0).toArray(), data.get(1).toArray(), data.get(2).toArray()};
                    Object[][] dataoObjectsTransposed = transpose(dataObjects);
                    String [] names = {"id","imie", "nazwisko"};
                    JTable tableCzytelnicy = new JTable(dataoObjectsTransposed, names);
                    tableCzytelnicy.setDefaultEditor(Object.class, null);

                    content.removeAll();
                    content.add(BorderLayout.CENTER, tablePane = new JScrollPane(tableCzytelnicy));
                    Border etchedBorder = BorderFactory.createEtchedBorder();
                    Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Czytelnicy",TitledBorder.CENTER, TitledBorder.TOP);
                    tablePane.setBorder(titledBorder);

                    helper.add(BorderLayout.NORTH, bttsCzytelnicy);
                    helper.add(content);
                    helper.revalidate();
                });

                dodaj.addActionListener(d -> {
                    content.removeAll();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    JLabel imie = new JLabel("Imie: ");
                    content.add(imie);

                    JTextField timie = new JTextField();
                    content.add(timie);

                    JLabel nazwisko = new JLabel("Nazwisko: ");
                    content.add(nazwisko);

                    JTextField tnazwisko = new JTextField();
                    content.add(tnazwisko);

                    JButton wyslij = new JButton("Wyslij");
                    wyslij.addActionListener(w -> {
                        if(timie.getText().isEmpty() || tnazwisko.getText().isEmpty()){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane");
                        }
                        else{
                            String query = "insert into bazy_projekt.czytelnik(imie, nazwisko) values";
                            db.insert(query,timie.getText(),tnazwisko.getText());
                            JOptionPane.showMessageDialog(this, "Dodano czytelnika");
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsCzytelnicy);
                    helper.add(content);
                    helper.revalidate();
                });
                
                usun.addActionListener(us -> {
                    content.removeAll();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    int max = db.selectInt("select max(id_czytelnik) from bazy_projekt.czytelnik");


                    JLabel nazwisko = new JLabel("Id_czytelnik(1-"+max+"): ");
                    content.add(nazwisko);

                    JSpinner tnazwisko = new JSpinner(new SpinnerNumberModel(1,1,max,1));
                    content.add(tnazwisko);

                    JButton wyslij = new JButton("Wyslij");
                    wyslij.addActionListener(w -> {
                        if((Integer)tnazwisko.getValue() < 1 || (Integer)tnazwisko.getValue() > max){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane poprawnie");
                        }
                        else{
                            if(db.canReaderBeDeleted( (Integer)tnazwisko.getValue() )){
                                String query = "delete from czytelnik where id_czytelnik = "+(Integer)tnazwisko.getValue();
                                db.insertAndSet(query);
                            }
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsCzytelnicy);
                    helper.add(content);
                    helper.revalidate();
                });
            });
            
            buttonKsiazki.addActionListener(l -> {
                JPanel bttsKsiazki = new JPanel(new FlowLayout());
                JLabel nazwa = new JLabel("Ksiazki: ");
                JButton wyswietl = new JButton("Wyswietl");
                JButton dodaj = new JButton("Dodaj");

                JCheckBox katPelne = new JCheckBox("Z kategoriami pelnymi");
                JCheckBox katOstatnie = new JCheckBox("Z kategoriami skrotowo");

                katPelne.addActionListener(w -> {
                    katOstatnie.setSelected(false);
                });
                katOstatnie.addActionListener(w -> {
                    katPelne.setSelected(false);
                });

                bttsKsiazki.add(nazwa);
                bttsKsiazki.add(wyswietl);
                bttsKsiazki.add(katPelne);
                bttsKsiazki.add(katOstatnie);
                bttsKsiazki.add(dodaj);
                helper.removeAll();
                helper.add(BorderLayout.NORTH, bttsKsiazki);
                content.removeAll();
                helper.add(content);
                helper.revalidate();
                wyswietl.addActionListener(w -> {
                    int mode = 0;
                    if(katPelne.isSelected())
                        mode = 1;
                    else if(katOstatnie.isSelected())
                        mode = 2;

                    JTable tableKsiazki = new JTable();
                    switch (mode) {
                        case 0:
                            List<List<String>> data0 = db.ksiazkiList(mode);
                            Object[][] dataObjects0 = {data0.get(0).toArray(), data0.get(1).toArray(), data0.get(2).toArray(), data0.get(3).toArray()};
                            Object[][] dataoObjectsTransposed0 = transpose(dataObjects0);
                            String [] names0 = {"id","tytul", "autor", "wydawnictwo"};
                            tableKsiazki = new JTable(dataoObjectsTransposed0, names0);
                            tableKsiazki.setDefaultEditor(Object.class, null);
                            break;
                        case 1:
                            List<List<String>> data1 = db.ksiazkiList(mode);
                            Object[][] dataObjects1 = {data1.get(0).toArray(), data1.get(1).toArray(), data1.get(2).toArray(), data1.get(3).toArray(), data1.get(4).toArray()};
                            Object[][] dataoObjectsTransposed1 = transpose(dataObjects1);
                            String [] names1 = {"id","tytul", "autor", "wydawnictwo", "kategoria"};
                            tableKsiazki = new JTable(dataoObjectsTransposed1, names1);
                            tableKsiazki.setDefaultEditor(Object.class, null);

                            break;

                        case 2:
                            List<List<String>> data = db.ksiazkiList(mode);
                            Object[][] dataObjects = {data.get(0).toArray(), data.get(1).toArray(), data.get(2).toArray(), data.get(3).toArray(), data.get(4).toArray()};
                            Object[][] dataoObjectsTransposed = transpose(dataObjects);
                            String [] names = {"id","tytul", "autor", "wydawnictwo","kategoria"};
                            tableKsiazki = new JTable(dataoObjectsTransposed, names);
                            tableKsiazki.setDefaultEditor(Object.class, null);
                            break;
                        default:
                            break;
                    }
                    

                    content.removeAll();
                    content.add(BorderLayout.CENTER, tablePane = new JScrollPane(tableKsiazki));
                    if(mode == 1){
                        tableKsiazki.getColumnModel().getColumn(0).setPreferredWidth(5);
                        tableKsiazki.getColumnModel().getColumn(4).setPreferredWidth(230);
                    }
                    Border etchedBorder = BorderFactory.createEtchedBorder();
                    Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Ksiazki",TitledBorder.CENTER, TitledBorder.TOP);
                    tablePane.setBorder(titledBorder);

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsKsiazki);
                    helper.add(content);
                    helper.revalidate();
                });

                dodaj.addActionListener(d -> {
                    content.removeAll();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    JLabel tytul = new JLabel("Tytul: ");
                    content.add(tytul);

                    JTextField ttytul = new JTextField();
                    content.add(ttytul);

                    JLabel imie = new JLabel("Imie autora: ");
                    content.add(imie);

                    JTextField timie = new JTextField();
                    content.add(timie);

                    JLabel nazwisko = new JLabel("Nazwisko autora: ");
                    content.add(nazwisko);

                    JTextField tnazwisko = new JTextField();
                    content.add(tnazwisko);

                    JLabel wydawnictwo = new JLabel("Wydawnictwo: ");
                    content.add(wydawnictwo);

                    JTextField twydawnictwo = new JTextField();
                    content.add(twydawnictwo);
                    
                    int max = db.selectInt("select max(id_kategoria) from bazy_projekt.kategoria");
                    JLabel kategoria = new JLabel("Numer kategorii(1-"+max+"): ");
                    content.add(kategoria);
                    
                    JSpinner skategoria = new JSpinner(new SpinnerNumberModel(1, 1, max, 1));
                    content.add(skategoria);

                    JButton wyslij = new JButton("Wyslij");
                    wyslij.addActionListener(w -> {
                        if(timie.getText().isEmpty() || tnazwisko.getText().isEmpty() || ttytul.getText().isEmpty()
                            || twydawnictwo.getText().isEmpty() || ((Integer)skategoria.getValue() > max || (Integer)skategoria.getValue() < 1)){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane poprawnie");
                        }
                        else{
                            db.callSQLfun("dodaj_ksiazke_i_zwiazane", ttytul.getText(), ((Integer)skategoria.getValue()).toString(),
                            timie.getText(), tnazwisko.getText(), twydawnictwo.getText());
                            JOptionPane.showMessageDialog(this, "Dodano ksiazke");
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsKsiazki);
                    helper.add(content);
                    helper.revalidate();
                });
            });
            
            buttonAutorzy.addActionListener(l -> {
                JPanel bttsAutorzy = new JPanel(new FlowLayout());
                JLabel nazwa = new JLabel("Autorzy: ");
                JButton wyswietl = new JButton("Wyswietl");
                JButton dodaj = new JButton("Dodaj");
                bttsAutorzy.add(nazwa);
                bttsAutorzy.add(wyswietl);
                bttsAutorzy.add(dodaj);
                helper.removeAll();
                helper.add(BorderLayout.NORTH, bttsAutorzy);
                content.removeAll();
                helper.add(content);
                helper.revalidate();
                wyswietl.addActionListener(w -> {
                    List<List<String>> data = db.autorzyList();
                    Object[][] dataObjects = {data.get(0).toArray(), data.get(1).toArray(), data.get(2).toArray()};
                    Object[][] dataoObjectsTransposed = transpose(dataObjects);
                    String [] names = {"id","imie", "nazwisko"};
                    JTable tableAutorzy = new JTable(dataoObjectsTransposed, names);
                    tableAutorzy.setDefaultEditor(Object.class, null);

                    content.removeAll();
                    content.add(BorderLayout.CENTER, tablePane = new JScrollPane(tableAutorzy));
                    Border etchedBorder = BorderFactory.createEtchedBorder();
                    Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Autorzy", TitledBorder.CENTER, TitledBorder.TOP);
                    tablePane.setBorder(titledBorder);

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsAutorzy);
                    helper.add(content);
                    helper.revalidate();
                });

                dodaj.addActionListener(d -> {
                    content.removeAll();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    JLabel imie = new JLabel("Imie: ");
                    content.add(imie);

                    JTextField timie = new JTextField();
                    content.add(timie);

                    JLabel nazwisko = new JLabel("Nazwisko: ");
                    content.add(nazwisko);

                    JTextField tnazwisko = new JTextField();
                    content.add(tnazwisko);

                    JButton wyslij = new JButton("Wyslij");
                    wyslij.addActionListener(w -> {
                        if(timie.getText().isEmpty() || tnazwisko.getText().isEmpty()){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane");
                        }
                        else{
                            String query = "insert into bazy_projekt.autor(imie, nazwisko) values";
                            db.insert(query,timie.getText(),tnazwisko.getText());
                            JOptionPane.showMessageDialog(this, "Dodano autora");
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsAutorzy);
                    helper.add(content);
                    helper.revalidate();
                    middle.revalidate();
                    getContentPane().revalidate();
                });
            });
            
            buttonKategorie.addActionListener(l -> {
                JPanel bttsKategorie = new JPanel(new FlowLayout());
                JLabel nazwa = new JLabel("Kategorie: ");
                JButton wyswietl = new JButton("Wyswietl");
                JButton dodaj = new JButton("Dodaj");
                bttsKategorie.add(nazwa);
                bttsKategorie.add(wyswietl);
                bttsKategorie.add(dodaj);
                helper.removeAll();
                helper.add(BorderLayout.NORTH, bttsKategorie);
                content.removeAll();
                helper.add(content);
                helper.revalidate();
                wyswietl.addActionListener(w -> {
                    helper.removeAll();
                    List<List<String>> data = db.kategorieList();
                    Object[][] dataObjects = {data.get(0).toArray(), data.get(1).toArray()};
                    Object[][] dataoObjectsTransposed = transpose(dataObjects);
                    String [] names = {"id","nazwa"};
                    JTable tableCzytelnicy = new JTable(dataoObjectsTransposed, names);
                    tableCzytelnicy.setDefaultEditor(Object.class, null);

                    content.removeAll();
                    content.add(BorderLayout.CENTER, tablePane = new JScrollPane(tableCzytelnicy));
                    Border etchedBorder = BorderFactory.createEtchedBorder();
                    Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Kategorie",TitledBorder.CENTER, TitledBorder.TOP);
                    tablePane.setBorder(titledBorder);
                    tableCzytelnicy.getColumnModel().getColumn(1).setPreferredWidth(350);
                    helper.add(BorderLayout.NORTH, bttsKategorie);
                    helper.add(content);
                    helper.revalidate();
                });

                dodaj.addActionListener(d -> {
                    content.removeAll();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    int max = db.selectInt("select max(id_kategoria) from bazy_projekt.kategoria");

                    JLabel imie = new JLabel("Nazwa kategorii: ");
                    content.add(imie);

                    JTextField timie = new JTextField();
                    content.add(timie);

                    JLabel nazwisko = new JLabel("Id_kategorii_ponad(1-"+max+"): ");
                    content.add(nazwisko);

                    JSpinner tnazwisko = new JSpinner(new SpinnerNumberModel(1,1,max,1));
                    content.add(tnazwisko);

                    JButton wyslij = new JButton("Wyslij");
                    wyslij.addActionListener(w -> {
                        if(timie.getText().isEmpty() || (Integer)tnazwisko.getValue() < 1
                        || (Integer)tnazwisko.getValue() > max){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane poprawnie");
                        }
                        else{
                            String query = "insert into bazy_projekt.kategoria(nazwa, id_kat_wyzej) values";
                            db.insert(query,timie.getText(),((Integer)tnazwisko.getValue()).toString());
                            JOptionPane.showMessageDialog(this, "Dodano kategorie");
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsKategorie);
                    helper.add(content);
                    helper.revalidate();
                });
            });

            buttonWydawnictwa.addActionListener(l -> {
                JPanel bttsWydawnictwa = new JPanel(new FlowLayout());
                JLabel nazwa = new JLabel("Wydawnictwa: ");
                JButton wyswietl = new JButton("Wyswietl");
                JButton dodaj = new JButton("Dodaj");
                bttsWydawnictwa.add(nazwa);
                bttsWydawnictwa.add(wyswietl);
                bttsWydawnictwa.add(dodaj);
                helper.removeAll();
                helper.add(BorderLayout.NORTH, bttsWydawnictwa);
                content.removeAll();
                helper.add(content);
                helper.revalidate();
                wyswietl.addActionListener(w -> {
                    List<List<String>> data = db.wydawnictwaList();
                    Object[][] dataObjects = {data.get(0).toArray(), data.get(1).toArray()};
                    Object[][] dataoObjectsTransposed = transpose(dataObjects);
                    String [] names = {"id","nazwa"};
                    JTable tableAutorzy = new JTable(dataoObjectsTransposed, names);
                    tableAutorzy.setDefaultEditor(Object.class, null);

                    content.removeAll();
                    content.add(BorderLayout.CENTER, tablePane = new JScrollPane(tableAutorzy));
                    Border etchedBorder = BorderFactory.createEtchedBorder();
                    Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Wydawnictwa", TitledBorder.CENTER, TitledBorder.TOP);
                    tablePane.setBorder(titledBorder);

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsWydawnictwa);
                    helper.add(content);
                    helper.revalidate();
                });

                dodaj.addActionListener(d -> {
                    content.removeAll();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    JLabel nazwaWydawnictwa = new JLabel("Nazwa wydawnictwa: ");
                    content.add(nazwaWydawnictwa);

                    JTextField tnazwa = new JTextField();
                    content.add(tnazwa);

                    JButton wyslij = new JButton("Wyslij");
                    wyslij.addActionListener(w -> {
                        if(tnazwa.getText().isEmpty()){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane");
                        }
                        else{
                            String query = "insert into bazy_projekt.wydawnictwo(nazwa) values";
                            db.insert(query,tnazwa.getText());
                            JOptionPane.showMessageDialog(this, "Dodano wydawnictwo");
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsWydawnictwa);
                    helper.add(content);
                    helper.revalidate();
                });
            });

            buttonRezerwacje.addActionListener(l -> {
                JPanel bttsRezerwacje = new JPanel(new FlowLayout());
                JLabel nazwa = new JLabel("Rezerwacje: ");
                JButton wyswietl = new JButton("Wyswietl");
                bttsRezerwacje.add(nazwa);
                bttsRezerwacje.add(wyswietl);
                helper.removeAll();
                helper.add(BorderLayout.NORTH, bttsRezerwacje);
                content.removeAll();
                helper.add(content);
                helper.revalidate();
                wyswietl.addActionListener(w -> {
                    List<List<String>> data = db.rezerwacjeList();
                    Object[][] dataObjects = {data.get(0).toArray(), data.get(1).toArray(),
                        data.get(2).toArray(), data.get(3).toArray(), data.get(4).toArray()};
                    Object[][] dataoObjectsTransposed = transpose(dataObjects);
                    String [] names = {"id","id_ksiazka", "id_czytelnik", "status_rezerwacji", "data_rezerwacji"};
                    JTable tableAutorzy = new JTable(dataoObjectsTransposed, names);
                    tableAutorzy.setDefaultEditor(Object.class, null);

                    content.removeAll();
                    content.add(BorderLayout.CENTER, tablePane = new JScrollPane(tableAutorzy));
                    Border etchedBorder = BorderFactory.createEtchedBorder();
                    Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Rezerwacje", TitledBorder.CENTER, TitledBorder.TOP);
                    tablePane.setBorder(titledBorder);

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsRezerwacje);
                    helper.add(content);
                    helper.revalidate();
                });
            });

            buttonWypozyczenia.addActionListener(l -> {
                JPanel bttsWypozyczenia = new JPanel(new FlowLayout());
                JLabel nazwa = new JLabel("Wypozyczenia: ");
                JButton wyswietl = new JButton("Wyswietl");
                JButton wypozycz = new JButton("Wypozycz");
                JButton zwroc = new JButton("Zwroc");
                bttsWypozyczenia.add(nazwa);
                bttsWypozyczenia.add(wyswietl);
                bttsWypozyczenia.add(wypozycz);
                bttsWypozyczenia.add(zwroc);

                helper.removeAll();
                helper.add(BorderLayout.NORTH, bttsWypozyczenia);
                content.removeAll();
                helper.add(content);
                helper.revalidate();
                wyswietl.addActionListener(w -> {
                    List<List<String>> data = db.wypozyczeniaList();
                    Object[][] dataObjects = {data.get(0).toArray(), data.get(1).toArray(),
                        data.get(2).toArray(), data.get(3).toArray(), data.get(4).toArray(), data.get(5).toArray()};
                    Object[][] dataoObjectsTransposed = transpose(dataObjects);
                    String [] names = {"id","id_ksiazka", "id_czytelnik", "tytul","data_wypozyczenia", "data_zwrotu"};
                    JTable tableAutorzy = new JTable(dataoObjectsTransposed, names);
                    tableAutorzy.setDefaultEditor(Object.class, null);

                    content.removeAll();
                    content.add(BorderLayout.CENTER, tablePane = new JScrollPane(tableAutorzy));
                    Border etchedBorder = BorderFactory.createEtchedBorder();
                    Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Wypozyczenia", TitledBorder.CENTER, TitledBorder.TOP);
                    tablePane.setBorder(titledBorder);

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsWypozyczenia);
                    helper.add(content);
                    helper.revalidate();
                });

                wypozycz.addActionListener(wyp -> {
                    content.removeAll();
                    content.revalidate();
                    helper.removeAll();
                    helper.revalidate();
                    helper.repaint();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    int max = db.selectInt("select max(id_czytelnik) from bazy_projekt.czytelnik");
                    JLabel id_czytelnika = new JLabel("id_czytelnika(1 - "+max+"): ");
                    content.add(id_czytelnika);

                    JSpinner tid = new JSpinner(new SpinnerNumberModel(1,1,max,1));
                    content.add(tid);

                    JLabel tytul = new JLabel("Tytul: ");
                    content.add(tytul);

                    JTextField ttytul = new JTextField();
                    content.add(ttytul);

                    JButton wyslij = new JButton("Wypozycz");
                    wyslij.addActionListener(w -> {
                        if(ttytul.getText().isEmpty() || (Integer)tid.getValue() > max || (Integer)tid.getValue() < 1){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane poprawnie");
                        }
                        else{
                            if(db.doReaderExist( (Integer)tid.getValue()) || db.doBookExist(ttytul.getText()) ){
                                String query = "insert into wypozyczenie(id_czytelnik, tytul) values";
                                    db.insertAndSet(query,((Integer)tid.getValue()).toString(),ttytul.getText());
                                if(db.isBookFreeToBeBorrowed(ttytul.getText()) )
                                    JOptionPane.showMessageDialog(this, "Wypozyczono dana ksiazke");
                                else
                                    JOptionPane.showMessageDialog(this, "Nie ma wolnego egzemplarza danej ksiazki, nastepuje rezerwacja danego tytulu");
                            }
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsWypozyczenia);
                    helper.add(content);
                    helper.revalidate();
                });

                zwroc.addActionListener(z -> {
                    content.removeAll();
                    content.revalidate();
                    helper.removeAll();
                    helper.revalidate();
                    helper.repaint();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                    int max = db.selectInt("select max(id_czytelnik) from bazy_projekt.czytelnik");
                    int max2 = db.selectInt("select max(id_ksiazka) from bazy_projekt.ksiazka");

                    JLabel id_czytelnika = new JLabel("id_czytelnika(1 - "+max+"): ");
                    content.add(id_czytelnika);

                    JSpinner tid = new JSpinner(new SpinnerNumberModel(1,1,max,1));
                    content.add(tid);

                    JLabel id_ksiazka = new JLabel("id_ksiazka(1 - "+max2+"): ");
                    content.add(id_ksiazka);

                    JSpinner tid2 = new JSpinner(new SpinnerNumberModel(1,1,max2,1));
                    content.add(tid2);

                    JButton wyslij = new JButton("Zwroc");
                    wyslij.addActionListener(w -> {
                        if((Integer)tid.getValue() > max || (Integer)tid.getValue() < 1 ||
                            (Integer)tid2.getValue() > max2 || (Integer)tid2.getValue() < 1){
                            JOptionPane.showMessageDialog(this, "Wypelnij dane poprawnie");
                        }
                        else{
                            if(db.doReaderExist( (Integer)tid.getValue()) ){
                                if(db.isBookBorrowedByThisPerson( (Integer)tid.getValue(), (Integer)tid2.getValue() )){
                                    db.insertAndSet("update wypozyczenie set data_zwrotu = current_date where " +
                                        "id_wypozyczenie=(select id_wypozyczenie from wypozyczenie where id_ksiazka="
                                        + (Integer)tid2.getValue() + " and id_czytelnik=" + (Integer)tid.getValue() + " and data_zwrotu is null limit 1)");
                                    JOptionPane.showMessageDialog(this, "Zwrocono dana ksiazke");
                                }
                            }
                        }
                    });
                    content.add(wyslij);
                    

                    helper.removeAll();
                    helper.add(BorderLayout.NORTH, bttsWypozyczenia);
                    helper.add(content);
                    helper.revalidate();
                });
            });
            
            
            
            middle.add(BorderLayout.NORTH, buttons);
            helper.setLayout(new BorderLayout());
            middle.add(helper);


            getContentPane().add(BorderLayout.NORTH, mb);
            getContentPane().add(BorderLayout.CENTER, middle);
            setVisible(true);
        }
    };
    public static void main(String args[]) {
        MyFrame frame = new MyFrame("Projekt - bazy danych");
        frame.setLocationRelativeTo(null);
    }
    public static <T> T[][] transpose(final T[][] array) {
        Objects.requireNonNull(array);
        // get y count
        final int yCount = Arrays.stream(array).mapToInt(a -> a.length).max().orElse(0);
        final int xCount = array.length;
        final Class<?> componentType = array.getClass().getComponentType().getComponentType();
        @SuppressWarnings("unchecked")
        final T[][] newArray = (T[][]) Array.newInstance(componentType, yCount, xCount);
        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                if (array[x] == null || y >= array[x].length) break;
                newArray[y][x] = array[x][y];
            }
        }
        return newArray;
    }
}