import javax.swing.*;
import java.awt.*;

public class KanjiButton extends JPanel {
  //Atributos
  String kanjiKey;

  //Métodos
  //Constructor
  public KanjiButton(Kanji kanji) {
    super();
    kanjiKey = kanji.genKey();

    setBackground(new Color(255, 241, 243));

    JLabel logogram = new JLabel(kanji.getLogogram());
    logogram.setFont(MainWindow.getKanjiGridFont());
    add(logogram);
  }

  public String getKey() {
    return kanjiKey;
  }
}
