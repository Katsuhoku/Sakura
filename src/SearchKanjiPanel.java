import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class SearchKanjiPanel extends JPanel {
  //Componentes
  //ScrollPane
  private JScrollPane kanjiDisplay;

  //Campos de Texto
  private JTextField keyField;
  private JTextField strokeCountField;

  //ComboBox
  private JComboBox<String> levelSelector;

  //Etiquetas
  private JLabel messageLabel;

  //Botones
  private JButton searchButton;


  //Métodos
  public SearchKanjiPanel() {
    super();
    setLayout(new BorderLayout());
    setBackground(new Color(255, 241, 243));

    initParametersPanel();
    initDisplayPanel();
  }

  private void initParametersPanel() {
    keyField = new JTextField("N");
    keyField.setFont(MainWindow.getTextFont());
    keyField.setHorizontalAlignment(JTextField.LEFT);
    keyField.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if(((e.getKeyChar() < '0') || (e.getKeyChar() > '9')) && (e.getKeyChar() != '\b'))
           e.consume();
        if (keyField.getText().length() == 8) e.consume();
        if (keyField.getText().length() == 0) keyField.setText("N");
      }
    });

    strokeCountField = new JTextField();
    strokeCountField.setFont(MainWindow.getTextFont());
    strokeCountField.setHorizontalAlignment(JTextField.RIGHT);
    strokeCountField.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if(((e.getKeyChar() < '0') || (e.getKeyChar() > '9')) && (e.getKeyChar() != '\b'))
           e.consume();
        if (strokeCountField.getText().length() == 2) e.consume();

        if (!e.isConsumed()) MainWindow.strokeFieldKeyTyped(e);
      }
    });

    String[] levels = new String[]{"-", "N1", "N2", "N3", "N4", "N5", "Todos"};
    levelSelector = new JComboBox<>(levels);
    levelSelector.setFont(MainWindow.getTextFont());
    levelSelector.setBackground(new Color(255, 255, 255));

    searchButton = new JButton("Buscar");
    searchButton.setFont(MainWindow.getTextFont());
    searchButton.setBackground(new Color(255, 255, 255));

    JLabel keyLabel = new JLabel("Clave:");
    keyLabel.setFont(MainWindow.getTextFont());
    keyLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel levelLabel = new JLabel("Nivel:");
    levelLabel.setFont(MainWindow.getTextFont());
    levelLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel strokeCountLabel = new JLabel("Trazos:");
    strokeCountLabel.setFont(MainWindow.getTextFont());
    strokeCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JPanel parametersPanel = new JPanel();
    parametersPanel.setLayout(new GridBagLayout());
    parametersPanel.setBackground(new Color(195, 130, 158));
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.5;
    parametersPanel.add(keyLabel, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 1.0;
    c.gridwidth = 4;
    parametersPanel.add(keyField, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 5;
    c.gridy = 0;
    c.weightx = 0.5;
    c.gridwidth = 1;
    parametersPanel.add(searchButton, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.5;
    parametersPanel.add(levelLabel, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.gridy = 1;
    c.weightx = 0.5;
    parametersPanel.add(levelSelector, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 2;
    c.gridy = 1;
    c.weightx = 0.5;
    parametersPanel.add(strokeCountLabel, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 3;
    c.gridy = 1;
    c.weightx = 0.5;
    parametersPanel.add(strokeCountField, c);

    add(parametersPanel, BorderLayout.PAGE_START);
  }

  private void initDisplayPanel() {
    messageLabel = new JLabel("Busque un kanji por clave o por características.");
    messageLabel.setFont(MainWindow.getTextFont());
    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

    add(messageLabel, BorderLayout.CENTER);
  }

  public void displayError(String message) {
    try {
      remove(messageLabel);
      remove(kanjiDisplay);
    } catch (Exception e) {}

    messageLabel.setText(message);
    messageLabel.setFont(MainWindow.getErrorFont());
    messageLabel.setForeground(Color.RED);
    add(messageLabel, BorderLayout.CENTER);
    updateUI();
  }

  public void makeGrid(ArrayList<Kanji> kanjiList) {
    try {
      remove(messageLabel);
      remove(kanjiDisplay);
    } catch (Exception e) {}

    KanjiGridPanel grid = new KanjiGridPanel(kanjiList);

    UIManager.put( "ScrollBar.maximumThumbSize", new Dimension(0, 250) );
    kanjiDisplay = new JScrollPane(grid);
    kanjiDisplay.getVerticalScrollBar().setUnitIncrement(16);
    kanjiDisplay.getVerticalScrollBar().setUI(new BasicScrollBarUI()
    {
      @Override
      protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
      }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
      }

      @Override
      protected void configureScrollBarColors(){
        this.thumbColor = new Color(173, 100, 113);
        this.trackColor = new Color(255, 241, 243);
      }

      private JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
      }
    });
    kanjiDisplay.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));

    add(kanjiDisplay, BorderLayout.CENTER);
    updateUI();
  }

  public void deleteGrid() {
    try {
      remove(messageLabel);
      remove(kanjiDisplay);
    } catch (Exception e) {}

    messageLabel.setText("Busque un kanji por clave o por características.");
    messageLabel.setFont(MainWindow.getTextFont());
    messageLabel.setForeground(Color.BLACK);
    add(messageLabel, BorderLayout.CENTER);
    updateUI();
  }

  public void resetParameters() {
    levelSelector.setSelectedIndex(0);
    strokeCountField.setText(null);
  }

  public JButton getSearchButton() {
    return searchButton;
  }

  public JComboBox getLevelSelector() {
    return levelSelector;
  }

  public String getKey() {
    return keyField.getText();
  }

  public int getLevelSelected() {
    int level;

    if (levelSelector.getSelectedItem().toString().compareTo("-") == 0) level = -1;
    else if (levelSelector.getSelectedItem().toString().compareTo("Todos") == 0) level = 0;
    else level = Integer.parseInt(levelSelector.getSelectedItem().toString().substring(1));

    return level;
  }

  public String getStrokeFieldText() {
    return strokeCountField.getText();
  }
}
