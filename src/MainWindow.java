import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame{
  //Ventana
  private static MainWindow mainWindow;

  //Atributos del Programa
  private static KanjiTable kanjiTable;

  private boolean addKanjiEnabled = false;
  private boolean searchKanjiEnabled = false;
  private boolean displayKanjiEnabled = false;

  private boolean changes = false;
  private static final String extension = ".skc";
  private String pathFile = "Sin Titulo" + extension;

  //Atributos de la Interfaz Gráfica
  private final int WINDOW_WIDTH = 540;
  private final int WINDOW_HEIGHT = 720;
  private final int MAXIMUM_WIDTH = 720;
  private final int MAXIMUM_HEIGHT = 960;

  //Componentes
  //Paneles
  private JPanel actionsPanel;
  private JPanel mainPanel;
  private JPanel lastPanel;

  private AddKanjiPanel addKanjiPanel;
  private SearchKanjiPanel searchKanjiPanel;
  private KanjiPanel displayKanjiPanel;

  //Botones
  private JButton addButton;
  private JButton searchButton;
  private JButton returnButton;

  //Menús
  private JMenuBar mainMenu;
  private JMenu fileMenu;
  private JMenuItem newFileMenu;
  private JMenuItem openFileMenu;
  private JMenuItem saveFileMenu;
  private JMenuItem saveAsFileMenu;

  //Logo
  private JLabel logo;

  //Fuentes
  private static Font textFont;
  private static Font kanjiFont;
  private static Font errorFont;
  private static Font kanjiGridFont;
  private static Font menuFont;

  //Inicialización de Ventana
  public MainWindow() {
    Image icon = new ImageIcon(System.getProperty("user.dir") + "\\lib\\icon.jpg").getImage(); //Obtiene la imagen de la ruta actual + /lib
		setIconImage(icon);

    setTitle("Sakura - " + pathFile);
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setResizable(false);
    //setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    //setMaximumSize(new Dimension(MAXIMUM_WIDTH, MAXIMUM_HEIGHT)); //Revisar modificación de tamaño
    setLocationRelativeTo(null);

    initFonts();
    initMenus();
    initComponents();

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        if (changes) {
          int jop = displaySelection("El archivo tiene cambios sin guardar. ¿Deseas guardar los cambios antes de salir?");
          if (jop == JOptionPane.YES_OPTION) {
            if (pathFile.compareTo("Sin Titulo" + extension) == 0) {
              if (saveAsFile()) exit();
            }
            else {
              saveFile();
              exit();
            }
          }
          else if (jop == JOptionPane.NO_OPTION) exit();
        }
        else exit();
      }
    });

    setVisible(true);
  }

  private void initFonts() {
    try {
      textFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/meiryo.ttc"));
      textFont = textFont.deriveFont(Font.PLAIN, 16);
    }catch (FontFormatException ffe) {
      displayError("Fail to set Font.\nFont format incompatible.");
      exit();
    }catch (IOException ioe) {
      displayError("Fail to set Font.\nFont file corrupted or not in the fonts directory.");
      exit();
    }

    try {
      kanjiFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/DFKai71.ttf"));
      kanjiFont = kanjiFont.deriveFont(Font.PLAIN, 100);
    }catch (FontFormatException ffe) {
      displayError("Fail to set Font.\nFont format incompatible.");
      exit();
    }catch (IOException ioe) {
      displayError("Fail to set Font.\nFont file corrupted or not in the fonts directory.");
      exit();
    }

    try {
      errorFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/meiryo.ttc"));
      errorFont = errorFont.deriveFont(Font.PLAIN, 14);
    }catch (FontFormatException ffe) {
      displayError("Fail to set Font.\nFont format incompatible.");
      exit();
    }catch (IOException ioe) {
      displayError("Fail to set Font.\nFont file corrupted or not in the fonts directory.");
      exit();
    }

    try {
      kanjiGridFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/YuGothB.ttc"));
      kanjiGridFont = kanjiGridFont.deriveFont(Font.PLAIN, 60);
    }catch (FontFormatException ffe) {
      displayError("Fail to set Font.\nFont format incompatible.");
      exit();
    }catch (IOException ioe) {
      displayError("Fail to set Font.\nFont file corrupted or not in the fonts directory.");
      exit();
    }

    try {
      menuFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/meiryo.ttc"));
      menuFont = menuFont.deriveFont(Font.PLAIN, 12);
    }catch (FontFormatException ffe) {
      displayError("Fail to set Font.\nFont format incompatible.");
      exit();
    }catch (IOException ioe) {
      displayError("Fail to set Font.\nFont file corrupted or not in the fonts directory.");
      exit();
    }
  }

  private void initMenus() {
    mainMenu = new JMenuBar();

    fileMenu = new JMenu("Archivo");
    fileMenu.setFont(menuFont);

    newFileMenu = new JMenuItem("Nueva Lista");
    newFileMenu.setFont(menuFont);
    newFileMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        newFileMenuMouseClicked();
      }
    });
    fileMenu.add(newFileMenu);

    openFileMenu = new JMenuItem("Abrir Lista");
    openFileMenu.setFont(menuFont);
    openFileMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        openFileMenuMouseClicked();
      }
    });
    fileMenu.add(openFileMenu);
    fileMenu.add(new JPopupMenu.Separator());

    saveFileMenu = new JMenuItem("Guardar Lista");
    saveFileMenu.setFont(menuFont);
    saveFileMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        saveFileMenuMouseClicked();
      }
    });
    fileMenu.add(saveFileMenu);

    saveAsFileMenu = new JMenuItem("Guardar como...");
    saveAsFileMenu.setFont(menuFont);
    saveAsFileMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        saveAsFileMenuMouseClicked();
      }
    });
    fileMenu.add(saveAsFileMenu);

    mainMenu.add(fileMenu);
    this.setJMenuBar(mainMenu);
  }

  private void initComponents() {
    Container contenedorPrincipal = getContentPane();
    contenedorPrincipal.setLayout(new BorderLayout());

    actionsPanel = new JPanel();
    actionsPanel.setBackground(new Color(195, 130, 158)); //Opción Violeta (Sakura)
    actionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    mainPanel = new JPanel();
    mainPanel.setBackground(new Color(255, 241, 243));
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    ImageIcon logoIcon = new ImageIcon(System.getProperty("user.dir") + "\\lib\\bgLogo.png");
    logo = new JLabel();
    logo.setIcon(logoIcon);
    mainPanel.add(logo);

    addButton = new JButton("Agregar");
    addButton.setFont(getTextFont());
    addButton.setBackground(new Color(255, 255, 255));
    addButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        addButtonMouseClicked();
      }
    });

    searchButton = new JButton("Buscar");
    searchButton.setFont(getTextFont());
    searchButton.setBackground(new Color(255, 255, 255));
    searchButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        searchButtonMouseClicked();
      }
    });

    actionsPanel.add(addButton);
    actionsPanel.add(searchButton);

    contenedorPrincipal.add(actionsPanel, BorderLayout.PAGE_START);
    contenedorPrincipal.add(mainPanel, BorderLayout.CENTER);
  }

  //
  /*    EVENTOS    */
  //

  //    Eventos en la Interfaz    //
  private void addButtonMouseClicked() {
    if (!addKanjiEnabled) {
      addKanjiPanel = new AddKanjiPanel();
      addKanjiPanel.getAcceptButton().addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
          acceptButtonMouseClicked();
        }
      });
      addKanjiPanel.getCancelButton().addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
          cancelButtonMouseClicked();
        }
      });

      if (searchKanjiEnabled) {
        mainPanel.remove(searchKanjiPanel);
        searchKanjiEnabled = false;
        searchKanjiPanel = null;
      }
      else {
        mainPanel.remove(logo);
      }

      mainPanel.add(addKanjiPanel);
      mainPanel.updateUI();
    }

    addKanjiEnabled = true;
  }

  private void searchButtonMouseClicked() {
    if (!searchKanjiEnabled) {
      searchKanjiPanel = new SearchKanjiPanel();
      searchKanjiPanel.getSearchButton().addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
          searchByKeyButtonMouseClicked();
        }
      });
      searchKanjiPanel.getLevelSelector().addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent ie) {
          levelSelectorStateChanged(ie);
        }
      });

      if (addKanjiEnabled) {
        mainPanel.remove(addKanjiPanel);
        addKanjiEnabled = false;
        addKanjiPanel = null;
      }
      else {
        mainPanel.remove(logo);
      }

      mainPanel.add(searchKanjiPanel);
      mainPanel.updateUI();
    }

    searchKanjiEnabled = true;
  }

  private void acceptButtonMouseClicked() {
    Kanji newKanji = addKanjiPanel.genKanji();
    if (newKanji != null){
      try {
        if (!kanjiTable.addKanji(newKanji)) {
          addKanjiPanel.displayError("Este Kanji ya fue agregado anteriormente");
        }
        else {
          cancelButtonMouseClicked();
          changes(true);
        }
      } catch (InvalidLevelException ile) {
        addKanjiPanel.displayError(ile.toString());
      } catch (InvalidStrokeCountException isce) {
        addKanjiPanel.displayError(isce.toString());
      }
    }
  }

  private void cancelButtonMouseClicked() {
    mainPanel.remove(addKanjiPanel);
    mainPanel.add(logo);
    mainPanel.updateUI();
    addKanjiPanel = null;

    addKanjiEnabled = false;
  }

  private void searchByKeyButtonMouseClicked() {
    searchKanjiPanel.resetParameters();

    if (searchKanjiPanel.getKey().length() < 8) {
      searchKanjiPanel.displayError("La clave debe ser de 8 caracteres");
    }
    else {
      Kanji kanji;
      try {
        kanji = kanjiTable.searchKanji(searchKanjiPanel.getKey());

        if (kanji == null) {
          searchKanjiPanel.displayError("Ningún kanji coincide con esa clave");
        }
        else {
          ArrayList<Kanji> aux = new ArrayList<>();
          aux.add(kanji);
          searchKanjiPanel.makeGrid(aux);
        }
      } catch (InvalidLevelException ile) {
        searchKanjiPanel.displayError(ile.toString());
      } catch (InvalidStrokeCountException isce) {
        searchKanjiPanel.displayError(isce.toString());
      }
    }
  }

  private void levelSelectorStateChanged(ItemEvent ie) {
    if (ie.getStateChange() == ItemEvent.SELECTED) {
      if (ie.getItem().toString().compareTo("-") == 0) {
        searchKanjiPanel.deleteGrid();
        return;
      }

      ArrayList<Kanji> list = new ArrayList<>();
      boolean displayAll = false;
      boolean strokeCountSpecific = false;
      if (ie.getItem().toString().compareTo("Todos") == 0) displayAll = true;
      String strokes = searchKanjiPanel.getStrokeFieldText();
      if (strokes != null && strokes.length() != 0) strokeCountSpecific = true;

      if (!displayAll) { //Mostrar un nivel en específico
        int level = Integer.parseInt(ie.getItem().toString().substring(1));

        try {
          if (!strokeCountSpecific) list = kanjiTable.getKanjiOfLevel(level);
          else list = kanjiTable.getKanjiWithStrokes(level, Integer.parseInt(strokes));

          if (list == null || list.size() == 0) searchKanjiPanel.displayError("Ningún kanji coincide con el criterio");
          else searchKanjiPanel.makeGrid(list);
        } catch (InvalidLevelException ile) {
          searchKanjiPanel.displayError(ile.toString());
        } catch (InvalidStrokeCountException isce) {
          searchKanjiPanel.displayError(isce.toString());
        }
      }
      else {
        try {
          for (int times = 5; times > 0; times--) { //Toma todos los niveles
            if (!strokeCountSpecific) list.addAll(kanjiTable.getKanjiOfLevel(times));
            else list = kanjiTable.getKanjiWithStrokes(Integer.parseInt(strokes));

            if (list.size() == 0) searchKanjiPanel.displayError("Ningún kanji coincide con el criterio");
            else searchKanjiPanel.makeGrid(list);
          }
        } catch (InvalidLevelException ile) {
          searchKanjiPanel.displayError(ile.toString());
        } catch (InvalidStrokeCountException isce) {
          searchKanjiPanel.displayError(isce.toString());
        }
      }
    }
  }

  public static void strokeFieldKeyTyped(KeyEvent evt) {
    mainWindow.strokeCountFieldKeyTyped(evt);
  }

  private void strokeCountFieldKeyTyped(KeyEvent evt) {
    ArrayList<Kanji> list = new ArrayList<>();
    int level = searchKanjiPanel.getLevelSelected();
    String strokes;
    boolean strokeCountSpecific = false;
    boolean displayAll = false;

    if (evt.getKeyChar() != '\b') strokes = (searchKanjiPanel.getStrokeFieldText() + evt.getKeyChar());
    else strokes = searchKanjiPanel.getStrokeFieldText();
    if (strokes != null && strokes.length() != 0) strokeCountSpecific = true;

    if (level == -1) {
      searchKanjiPanel.deleteGrid();
      return;
    }
    else if (level == 0) displayAll = true;

    if (!displayAll) { //Mostrar un nivel en específico
      try {
        if (!strokeCountSpecific) list = kanjiTable.getKanjiOfLevel(level);
        else list = kanjiTable.getKanjiWithStrokes(level, Integer.parseInt(strokes));

        if (list == null || list.size() == 0) searchKanjiPanel.displayError("Ningún kanji coincide con el criterio");
        else searchKanjiPanel.makeGrid(list);
      } catch (InvalidLevelException ile) {
        searchKanjiPanel.displayError(ile.toString());
      } catch (InvalidStrokeCountException isce) {
        searchKanjiPanel.displayError(isce.toString());
      }
    }
    else {
      try {
        for (int times = 5; times > 0; times--) { //Toma todos los niveles
          if (!strokeCountSpecific) list.addAll(kanjiTable.getKanjiOfLevel(times));
          else list = kanjiTable.getKanjiWithStrokes(Integer.parseInt(strokes));

          if (list.size() == 0) searchKanjiPanel.displayError("Ningún kanji coincide con el criterio");
          else searchKanjiPanel.makeGrid(list);
        }
      } catch (InvalidLevelException ile) {
        searchKanjiPanel.displayError(ile.toString());
      } catch (InvalidStrokeCountException isce) {
        searchKanjiPanel.displayError(isce.toString());
      }
    }
  }

  public static void kanjiButtonMouseClicked(MouseEvent evt) {
    mainWindow.kanjiGridMouseClicked(evt);
  }

  public void kanjiGridMouseClicked(MouseEvent evt) {
    try {
      Kanji selectedKanji = kanjiTable.searchKanji(((KanjiButton) evt.getSource()).getKey());

      displayKanjiPanel = new KanjiPanel(selectedKanji);

      try {
        mainPanel.remove(searchKanjiPanel);
        lastPanel = searchKanjiPanel;
      } catch (Exception e) {}
      mainPanel.add(displayKanjiPanel);
      mainPanel.updateUI();

      returnButton = new JButton("Regresar");
      returnButton.setFont(getTextFont());
      returnButton.setBackground(new Color(255, 255, 255));
      returnButton.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
          returnButtonMouseClicked();
        }
      });

      actionsPanel.remove(addButton);
      actionsPanel.remove(searchButton);
      actionsPanel.add(returnButton);
      actionsPanel.updateUI();
    } catch (InvalidLevelException ile) {
      searchKanjiPanel.displayError(ile.toString());
    } catch (InvalidStrokeCountException isce) {
      searchKanjiPanel.displayError(isce.toString());
    }
  }

  private void returnButtonMouseClicked() {
    mainPanel.remove(displayKanjiPanel);
    mainPanel.add(lastPanel);
    mainPanel.updateUI();

    actionsPanel.remove(returnButton);
    actionsPanel.add(addButton);
    actionsPanel.add(searchButton);
    actionsPanel.updateUI();
  }

  //    Eventos en Menu    //
  private void newFileMenuMouseClicked() {
    if (changes) {
      int jop = displaySelection("El archivo tiene cambios sin guardar. ¿Deseas guardar los cambios?");
      if (jop == JOptionPane.YES_OPTION) {
        saveFile();
        reset();
      }
      else if (jop == JOptionPane.NO_OPTION) reset();
    }
    else reset();
  }

  private void openFileMenuMouseClicked() {
    if (changes) {
      int jop = displaySelection("El archivo tiene cambios sin guardar. ¿Deseas guardar los cambios?");
      if (jop == JOptionPane.YES_OPTION) {
        saveFile();
        openFile();
      }
      else if (jop == JOptionPane.NO_OPTION) openFile();
    }
    else openFile();
  }

  private void saveFileMenuMouseClicked() {
    if (pathFile.compareTo("Sin Titulo" + extension) == 0) saveAsFile();
    else saveFile();
  }

  private void saveAsFileMenuMouseClicked() {
    saveAsFile();
  }

  //
  /*    ACCIONES    */
  //
  private void displayError(String control) {
    JOptionPane.showMessageDialog(this, control, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private int displaySelection(String control) {
    return JOptionPane.showConfirmDialog(null, control, pathFile, JOptionPane.YES_NO_CANCEL_OPTION,
                                         JOptionPane.WARNING_MESSAGE);
  }

  private void changes(boolean value) {
    if (value) setTitle("Sakura - *" + pathFile);
    else setTitle("Sakura - " + pathFile);

    changes = value;
  }

  private void saveFile() {
    boolean saved = false;
    ArrayList<Kanji> kanjiList = new ArrayList<>();

    for (int i = 1; i <= 5; i++) {
      try {
        kanjiList.addAll(kanjiTable.getKanjiOfLevel(i));
      }
      catch (InvalidLevelException e) {}
    }

    ArrayList<String> formats = new ArrayList<>();

    for(Kanji kanji : kanjiList) {
      formats.add(kanji.toString());
    }

    if (FileHandler.openFile(pathFile, 'w')){
      if (!FileHandler.writeFile((Object) String.format("VER %d", Kanji.VERSION))
          || !FileHandler.writeFile((Object) formats)) {
        displayError("No se pudo guardar el archivo.\n(Error al escribir)");
      }
      else changes(false);

      FileHandler.closeFile('w');
    }
    else displayError("No se pudo guardar el archivo.\n(Error al abrir/crear)");
  }

  private boolean saveAsFile() {
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    jfc.setDialogTitle("Guardar como...");
    jfc.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(extension.substring(1).toUpperCase(),
                                                                 extension.substring(1));
    jfc.addChoosableFileFilter(filter);

    do {
      int returnValue = jfc.showSaveDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        pathFile = jfc.getSelectedFile().getAbsolutePath().concat(extension);
        File file = new File(pathFile);

        if (file.exists()) {
          int jop = this.displaySelection("¿Desea sobreescribir el archivo existente?");

          if (jop == JOptionPane.YES_OPTION) {
            saveFile();
            break;
          }
        }
        else {
          saveFile();
          break;
        }
      }
      else return false;
    } while (true);

    return true;
  }

  private void openFile() {
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    jfc.setDialogTitle("Abrir Lista");
    jfc.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(extension.substring(1).toUpperCase(),
                                                                 extension.substring(1));
    jfc.addChoosableFileFilter(filter);

    int returnValue = jfc.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      reset();
      String auxPathFile = jfc.getSelectedFile().getPath();

      if (FileHandler.openFile(auxPathFile, 'r')) {
        KanjiTable auxTable;
        try {
          String ver = (String) FileHandler.readFile();

          ArrayList<String> formats = (ArrayList<String>) FileHandler.readFile();
          FileHandler.closeFile('r');

          kanjiTable = new KanjiTable();
          for (String format : formats) {
            kanjiTable.addKanji(new Kanji(format));
          }
          pathFile = auxPathFile;
          changes(false);
          
          /*auxTable = (KanjiTable) FileHandler.readFile();
          auxTable.getSum(); //Testing object readed
          FileHandler.closeFile('r');

          pathFile = auxPathFile;
          kanjiTable = auxTable;
          changes(false);*/
        }
        catch (Exception e) {
          displayError("El archivo no pudo leerse, puede estar dañado o no ser compatible.");
          System.out.println(e);
        }
      }
      else displayError("El archivo no pudo abrirse, puede estar dañado o no ser compatible.");
    }
  }

  private void reset() {
    if (addKanjiEnabled || searchKanjiEnabled || displayKanjiEnabled) {
      if (addKanjiEnabled) {
        mainPanel.remove(addKanjiPanel);
        addKanjiEnabled = false;
      }
      else if (searchKanjiEnabled) {
        mainPanel.remove(searchKanjiPanel);
        searchKanjiEnabled = false;
      }
      else {
        mainPanel.remove(displayKanjiPanel);
        actionsPanel.remove(returnButton);
        actionsPanel.add(addButton);
        actionsPanel.add(searchButton);
        displayKanjiEnabled = false;
      }

      mainPanel.add(logo);
      mainPanel.updateUI();
      actionsPanel.updateUI();
    }

    pathFile = "Sin Titulo" + extension;
    kanjiTable = new KanjiTable();
    changes(false);
  }

  private void exit() {
    setVisible(false);
    dispose();

    System.exit(0);
  }

  public static void main (String[] args) {
    mainWindow = new MainWindow();
    kanjiTable = new KanjiTable();
  }

  public static Font getTextFont() {
    return textFont;
  }

  public static Font getKanjiFont() {
    return kanjiFont;
  }

  public static Font getErrorFont() {
    return errorFont;
  }

  public static Font getKanjiGridFont() {
    return kanjiGridFont;
  }
}
