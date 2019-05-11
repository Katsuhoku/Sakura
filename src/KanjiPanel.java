import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class KanjiPanel extends JPanel {
  //Atributos
  private Kanji displayKanji;

  //Métodos
  //Constructor
  public KanjiPanel(Kanji displayKanji) {
    super();
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    this.displayKanji = displayKanji;
    initDetailsPanel();
    initReadingsPanel();
  }

  private void initDetailsPanel() {
    JLabel kanjiLabel = new JLabel(displayKanji.getLogogram());
    kanjiLabel.setFont(MainWindow.getKanjiFont());
    kanjiLabel.setHorizontalAlignment(SwingConstants.CENTER);
    kanjiLabel.setVerticalAlignment(SwingConstants.CENTER);

    JLabel levelLabel = new JLabel("Nivel JLPT: " + displayKanji.getLevel());
    levelLabel.setFont(MainWindow.getTextFont());

    JLabel strokeCountLabel = new JLabel("Número de Trazos: " + displayKanji.getStrokeCount());
    strokeCountLabel.setFont(MainWindow.getTextFont());

    JLabel frequencyLabel = new JLabel("Frecuencia: " + displayKanji.getFrequency());
    frequencyLabel.setFont(MainWindow.getTextFont());

    JLabel detailsLabel = new JLabel("DETALLES");
    detailsLabel.setFont(MainWindow.getTextFont());
    JPanel auxDetails = new JPanel();
    auxDetails.setBackground(new Color(247, 215, 220));
    auxDetails.add(detailsLabel);

    //Sección kanji
    JPanel kanjiPanel = new JPanel();
    kanjiPanel.setLayout(new BorderLayout());
    kanjiPanel.setBackground(new Color(255, 241, 243));
    kanjiPanel.add(kanjiLabel, BorderLayout.CENTER);
    kanjiPanel.setPreferredSize(new Dimension(200, 200));

    JPanel kanjiDetailsPanel = new JPanel();
    kanjiDetailsPanel.setLayout(new GridLayout(4, 1, 10, 15));
    kanjiDetailsPanel.setBackground(new Color(255, 241, 243));
    kanjiDetailsPanel.add(auxDetails);
    kanjiDetailsPanel.add(levelLabel);
    kanjiDetailsPanel.add(strokeCountLabel);
    kanjiDetailsPanel.add(frequencyLabel);

    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.PAGE_AXIS));
    detailsPanel.setBackground(new Color(255, 241, 243));
    detailsPanel.add(kanjiPanel);
    detailsPanel.add(Box.createRigidArea(new Dimension(35, 0)));
    detailsPanel.add(kanjiDetailsPanel);
    add(detailsPanel);
  }

  public void initReadingsPanel() {
    StringBuffer onYomi = new StringBuffer();
    for (String reading : displayKanji.getOnYomi()) {
      onYomi.append(reading + ", ");
    }
    JLabel onYomiLabel = new JLabel("\tOnYomi: " + onYomi.toString().substring(0, onYomi.length() - 2));
    onYomiLabel.setFont(MainWindow.getTextFont());

    StringBuffer kunYomi = new StringBuffer();
    for (String reading : displayKanji.getKunYomi()) {
      kunYomi.append(reading + ", ");
    }
    JLabel kunYomiLabel = new JLabel("\tKunYomi: " + kunYomi.toString().substring(0, kunYomi.length() - 2));
    kunYomiLabel.setFont(MainWindow.getTextFont());

    StringBuffer meanings = new StringBuffer();
    for (String reading : displayKanji.getMeaning()) {
      meanings.append(reading + ", ");
    }
    JLabel meaningsLabel = new JLabel("\t" + meanings.toString().substring(0, meanings.length() - 2));
    meaningsLabel.setFont(MainWindow.getTextFont());


    JLabel readingsLabel = new JLabel("LECTURAS");
    readingsLabel.setFont(MainWindow.getTextFont());
    readingsLabel.setBackground(new Color(247, 215, 220));
    JPanel auxReadings = new JPanel();
    auxReadings.setBackground(new Color(247, 215, 220));
    auxReadings.add(readingsLabel);

    JLabel meaningLabel = new JLabel("SIGNIFICADO");
    meaningLabel.setFont(MainWindow.getTextFont());
    meaningLabel.setBackground(new Color(247, 215, 220));
    JPanel auxMeanings = new JPanel();
    auxMeanings.setBackground(new Color(247, 215, 220));
    auxMeanings.add(meaningLabel);

    JPanel extraInfoPanel = new JPanel();
    extraInfoPanel.setBackground(new Color(255, 241, 243));
    extraInfoPanel.setLayout(new GridLayout(6, 1, 20, 20));
    extraInfoPanel.add(auxReadings);
    extraInfoPanel.add(onYomiLabel);
    extraInfoPanel.add(kunYomiLabel);
    extraInfoPanel.add(auxMeanings);
    extraInfoPanel.add(meaningsLabel);
    extraInfoPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    add(extraInfoPanel);
  }

  public Kanji getKanji() {
    return displayKanji;
  }
}
