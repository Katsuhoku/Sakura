import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KanjiGridPanel extends JPanel {
  //Atributos
  private int rows;

  //Components

  //Métodos
  //Constructor
  public KanjiGridPanel(ArrayList<Kanji> kanjiGroup) {
    super();

    rows = kanjiGroup.size() / 5;
    if (kanjiGroup.size() % 5 != 0) rows++;

    if (rows < 5) setLayout(new GridLayout(5, 5, 5, 5));
    else setLayout(new GridLayout(rows, 5, 5, 5));
    setBackground(new Color(255, 241, 243));

    initKanjiGrid(kanjiGroup);
  }

  private void initKanjiGrid(ArrayList<Kanji> kanjiGroup) {
    int i = 0;
    for (Kanji kanji : kanjiGroup) {
      KanjiButton newButton = new KanjiButton(kanji);
      newButton.addMouseListener(new MouseAdapter(){
        public void mouseEntered(MouseEvent evt) {
          newButton.setBackground(new Color(252, 199, 207));
        }

        public void mouseExited(MouseEvent evt) {
          newButton.setBackground(new Color(255, 241, 243));
        }

        public void mouseClicked(MouseEvent evt) {
          MainWindow.kanjiButtonMouseClicked(evt);
          newButton.setBackground(new Color(255, 241, 243));
        }
      });

      add(newButton);
      i++;
    }

    for (; i < ((rows < 25) ? 25 : rows * 5); i++) {
      JPanel aux = new JPanel();
      aux.setBackground(new Color(255, 241, 243));
      add(aux);
    }
    updateUI();
  }
}
