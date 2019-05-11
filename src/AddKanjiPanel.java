import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddKanjiPanel extends JPanel {
  //Componentes
  //Campos de Texto
  private JTextField kanjiField;
  private JTextField strokeCountField;
  private JTextField frequencyField;
  private JTextField onyomiField;
  private JTextField kunyomiField;
  private JTextField meaningsField;

  //ComboBox
  private JComboBox<String> levelSelector;

  //Botones
  private JButton acceptButton;
  private JButton cancelButton;

  //Labels
  private JLabel errorLabel;

  //Metodos
  //Constructor
  public AddKanjiPanel() {
    super();
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    setBackground(new Color(255, 241, 243));

    add(Box.createRigidArea(new Dimension(0, 40)));
    initDetailsPanel();
    add(Box.createRigidArea(new Dimension(0, 30)));
    initExtraInfoPanel();
    add(Box.createRigidArea(new Dimension(0, 5)));
    initErrorPanel();
    add(Box.createRigidArea(new Dimension(0, 15)));
    initButtonPanel();
    add(Box.createRigidArea(new Dimension(0, 40)));
  }

  public AddKanjiPanel(Kanji editKanji) {
    this();
    editDetailsPanel(editKanji);
    editExtraInfoPanel(editKanji);
  }

  private void initDetailsPanel() {
    kanjiField = new JTextField();
    kanjiField.setFont(MainWindow.getKanjiFont());
    kanjiField.setHorizontalAlignment(JTextField.CENTER);
    kanjiField.setPreferredSize(new Dimension(150, 150));

    JPanel kanjiPanel = new JPanel();
    kanjiPanel.setLayout(new BoxLayout(kanjiPanel, BoxLayout.PAGE_AXIS));
    kanjiPanel.setBackground(new Color(255, 241, 243));

    JLabel kanjiLabel = new JLabel("Kanji:");
    kanjiLabel.setFont(MainWindow.getTextFont());

    kanjiPanel.add(kanjiLabel);
    kanjiPanel.add(kanjiField);


    strokeCountField = new JTextField();
    strokeCountField.setHorizontalAlignment(JTextField.RIGHT);
    strokeCountField.setFont(MainWindow.getTextFont());
    strokeCountField.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if(((e.getKeyChar() < '0') || (e.getKeyChar() > '9')) && (e.getKeyChar() != '\b'))
           e.consume();
        if (strokeCountField.getText().length() == 2) e.consume();
      }
    });

    frequencyField = new JTextField();
    frequencyField.setHorizontalAlignment(JTextField.RIGHT);
    frequencyField.setFont(MainWindow.getTextFont());
    frequencyField.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if(((e.getKeyChar() < '0') || (e.getKeyChar() > '9')) && (e.getKeyChar() != '\b'))
           e.consume();
        if (frequencyField.getText().length() == 4) e.consume();
      }
    });

    String[] levels = new String[]{"N1", "N2", "N3", "N4", "N5"};
    levelSelector = new JComboBox<>(levels);
    levelSelector.setFont(MainWindow.getTextFont());
    levelSelector.setBackground(new Color(255, 255, 255));

    JPanel kanjiDetailsPanel = new JPanel();
    kanjiDetailsPanel.setLayout(new GridLayout(3, 2, 10, 15));
    kanjiDetailsPanel.setBackground(new Color(255, 241, 243));

    JLabel levelLabel = new JLabel("Nivel JLPT:");
    levelLabel.setFont(MainWindow.getTextFont());

    JLabel strokeCountLabel = new JLabel("Numero de Trazos:");
    strokeCountLabel.setFont(MainWindow.getTextFont());

    JLabel frequencyLabel = new JLabel("Frecuencia:");
    frequencyLabel.setFont(MainWindow.getTextFont());

    kanjiDetailsPanel.add(levelLabel);
    kanjiDetailsPanel.add(levelSelector);
    kanjiDetailsPanel.add(strokeCountLabel);
    kanjiDetailsPanel.add(strokeCountField);
    kanjiDetailsPanel.add(frequencyLabel);
    kanjiDetailsPanel.add(frequencyField);

    JPanel detailsPanel = new JPanel();
    detailsPanel.setBackground(new Color(255, 241, 243));
    detailsPanel.add(kanjiPanel);
    detailsPanel.add(Box.createRigidArea(new Dimension(15, 0)));
    detailsPanel.add(kanjiDetailsPanel);
    add(detailsPanel);
  }

  private void editDetailsPanel(Kanji editKanji) {
    kanjiField.setText(editKanji.getLogogram());
    kanjiField.setEditable(false);
    // Deshabilita el selector de nivel
    levelSelector.setSelectedIndex(editKanji.getLevel() - 1);
    ((JTextField) levelSelector.getEditor().getEditorComponent()).setEditable(false);
    for (Component component : levelSelector.getComponents()) {
      if (component instanceof AbstractButton) {
        component.setEnabled(false);
        for (MouseListener listener : component.getListeners(MouseListener.class)) {
          component.removeMouseListener(listener);
        }
      }
    }
    for (MouseListener listener : levelSelector.getListeners(MouseListener.class)) {
      levelSelector.removeMouseListener(listener);
    }
    strokeCountField.setText(String.format("%d", editKanji.getStrokeCount()));
    strokeCountField.setEditable(false);
    frequencyField.setText(String.format("%d", editKanji.getFrequency()));
    frequencyField.setEditable(false);
  }

  private void initErrorPanel() {
    errorLabel = new JLabel("\t");
    errorLabel.setFont(MainWindow.getErrorFont());
    errorLabel.setForeground(Color.RED);

    JPanel errorPanel = new JPanel();
    errorPanel.setBackground(new Color(255, 241, 243));
    errorPanel.add(errorLabel);

    add(errorPanel);
  }

  private void initExtraInfoPanel() {
    onyomiField = new JTextField();
    onyomiField.setFont(MainWindow.getTextFont());

    kunyomiField = new JTextField();
    kunyomiField.setFont(MainWindow.getTextFont());

    meaningsField = new JTextField();
    meaningsField.setFont(MainWindow.getTextFont());

    JLabel onyomiLabel = new JLabel("Lecturas OnYomi:");
    onyomiLabel.setFont(MainWindow.getTextFont());

    JLabel kunyomiLabel = new JLabel("Lecturas KunYomi:");
    kunyomiLabel.setFont(MainWindow.getTextFont());

    JLabel meaningsLabel = new JLabel("Significados:");
    meaningsLabel.setFont(MainWindow.getTextFont());

    JPanel auxPanel = new JPanel();
    auxPanel.setBackground(new Color(255, 241, 243));
    auxPanel.setLayout(new BorderLayout());
    auxPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.PAGE_START);
    auxPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.PAGE_END);
    auxPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.LINE_START);
    auxPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.LINE_END);

    JPanel extraInfoPanel = new JPanel();
    extraInfoPanel.setBackground(new Color(255, 241, 243));
    extraInfoPanel.setLayout(new BoxLayout(extraInfoPanel, BoxLayout.PAGE_AXIS));
    extraInfoPanel.add(onyomiLabel);
    extraInfoPanel.add(onyomiField);
    extraInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    extraInfoPanel.add(kunyomiLabel);
    extraInfoPanel.add(kunyomiField);
    extraInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    extraInfoPanel.add(meaningsLabel);
    extraInfoPanel.add(meaningsField);
    auxPanel.add(extraInfoPanel, BorderLayout.CENTER);
    add(auxPanel);
  }

  private void editExtraInfoPanel(Kanji editKanji) {
    StringBuffer onyomi = new StringBuffer();
    for (String lecture : editKanji.getOnYomi()) {
      onyomi.append(lecture + "・");
    }
    onyomi.delete(onyomi.length() -1, onyomi.length());
    onyomiField.setText(onyomi.toString());

    StringBuffer kunyomi = new StringBuffer();
    for (String lecture : editKanji.getKunYomi()) {
      kunyomi.append(lecture + "・");
    }
    kunyomi.delete(kunyomi.length() -1, kunyomi.length());
    kunyomiField.setText(kunyomi.toString());

    StringBuffer meanings = new StringBuffer();
    for (String lecture : editKanji.getMeaning()) {
      meanings.append(lecture + ",");
    }
    meanings.delete(meanings.length() -1, meanings.length());
    meaningsField.setText(meanings.toString());
  }

  private void initButtonPanel() {
    acceptButton = new JButton("Aceptar");
    acceptButton.setBackground(new Color(255, 255, 255));
    acceptButton.setFont(MainWindow.getTextFont());

    cancelButton = new JButton("Cancelar");
    cancelButton.setBackground(new Color(255, 255, 255));
    cancelButton.setFont(MainWindow.getTextFont());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(255, 241, 243));
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
    buttonPanel.add(acceptButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
    buttonPanel.add(cancelButton);
    add(buttonPanel);
  }

  public Kanji genKanji() {
    String logogram = kanjiField.getText();
    String lvl = levelSelector.getSelectedItem().toString();
    String strkcnt = strokeCountField.getText();
    String freq = frequencyField.getText();

    //Comprueba que las entradas no estén vacías
    if (logogram == null || logogram.length() == 0 || strkcnt == null ||
        strkcnt.length() == 0 || freq == null || freq.length() == 0) {
      errorLabel.setText("Aún hay campos sin llenar");
      updateUI();
      return null;
    }

    //Comprueba que la entrada del kanji sea la correcta
    if (logogram.codePointAt(0) < 0x4E00 || logogram.codePointAt(0) > 0x9FEF) {
      errorLabel.setText("Solo se permiten Kanji");
      updateUI();
      return null;
    }
    if (logogram.length() > 1) {
      errorLabel.setText("Máximo un Kanji");
      updateUI();
      return null;
    }

    int level = Integer.parseInt(lvl.substring(1));
    int strokeCount = Integer.parseInt(strkcnt);
    int frequency = Integer.parseInt(freq);

    if (strokeCount == 0 || frequency == 0) {
      errorLabel.setText("Número de Trazos y Frecuencia no pueden ser 0");
      updateUI();
      return null;
    }

    //Recogida de lecturas y significados
    ArrayList<String> onYomi = cutStrings(onyomiField.getText());
    ArrayList<String> kunYomi = cutStrings(kunyomiField.getText());
    ArrayList<String> meanings = cutStrings(meaningsField.getText());

    Kanji kanji = new Kanji(logogram, level, strokeCount, frequency);
    kanji.setOnYomi(onYomi);
    kanji.setKunYomi(kunYomi);
    kanji.setMeaning(meanings);
    return kanji;
  }

  private ArrayList<String> cutStrings(String s) {
    if (s == null || s.length() == 0) return null;
    else s = s.concat(",");

    ArrayList<String> strings = new ArrayList<>();
    int start = 0;
    int end = 0;

    //Corta la cadena en un arreglo de subcadenas
    //Corta donde encuentre "," (coma) o "・" (Punto de separación japones)
    for (; end < s.length(); end++) {
      if (s.codePointAt(end) == 0x002C || s.codePointAt(end) == 0x30FB) {
        if (end > start) strings.add(s.substring(start, end));
        start = end + 1;
      }
    }

    return strings;
  }

  public void displayError(String message) {
    errorLabel.setText(message);
    updateUI();
  }

  public JButton getAcceptButton() {
    return acceptButton;
  }

  public JButton getCancelButton() {
    return cancelButton;
  }
}
