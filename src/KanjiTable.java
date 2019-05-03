import java.util.ArrayList;
import java.io.Serializable;

public class KanjiTable implements Serializable {
  //Atributos
  private AVLTree[][] table;
  private int sum;
  private static final int minStrokes[] = {1, 2, 3, 2, 1};
  private static final int maxStrokes[] = {24, 20, 20, 18, 14};

  //Métodos
  //Constructor
  public KanjiTable() {
    table = new AVLTree[5][];

    for (int i = 0; i < 5; i++) {
      table[i] = new AVLTree[maxStrokes[i] - minStrokes[i] + 1];

      for (int j = 0; j < maxStrokes[i] - minStrokes[i] + 1; j++)
        table[i][j] = new AVLTree();
    }

    sum = 0;
  }

  //Funcion Hash
  private int[] hash(String key) throws InvalidStrokeCountException, InvalidLevelException {
    int[] index = new int[2];

    Character lv = new Character(key.charAt(1)); //Row
    index[0] = (Integer.parseInt(lv.toString()) - 1);

    if (index[0] < 0 || index[0] > 4) throw new InvalidLevelException();

    String strk = key.substring(2, 4); //Column
    index[1] = (Integer.parseInt(strk) - minStrokes[index[0]]);

    if (index[1] < minStrokes[index[0]] - 1 || index[1] > maxStrokes[index[0]] - 1)
      throw new InvalidStrokeCountException(minStrokes[index[0]], maxStrokes[index[0]]);

    return index;
  }

  //Insercion
  public boolean addKanji(Kanji kanji) throws InvalidStrokeCountException, InvalidLevelException {
    int[] index = hash(kanji.genKey());

    if (table[index[0]][index[1]].add(kanji)) {
      sum++;
      return true;
    }
    else return false;
  }

  public Kanji searchKanji(String key) throws InvalidStrokeCountException, InvalidLevelException {
    int[] index = hash(key);

    return table[index[0]][index[1]].search(Integer.parseInt(key.substring(4)));
  }

  public int getSum() {
    return sum;
  }

  //Nivel debe ser especificado de 1 a 5
  public ArrayList<Kanji> getKanjiOfLevel(int level) throws InvalidLevelException {
    if (level < 1 || level > 5) throw new InvalidLevelException();

    level--;
    ArrayList<Kanji> list = new ArrayList<>();
    for (AVLTree kanjiTree : table[level]) {
      ArrayList<Kanji> aux = kanjiTree.listTreeInOrder();
      if (aux != null) list.addAll(aux);
    }

    return list;
  }

  //Obtiene todos los kanji con un número de trazos en todos los Niveles
  public ArrayList<Kanji> getKanjiWithStrokes(int strokes) throws InvalidStrokeCountException {
    if (strokes < minStrokes[0] || strokes > maxStrokes[0])
      throw new InvalidStrokeCountException(minStrokes[0], maxStrokes[0]);

    ArrayList<Kanji> list = new ArrayList<>();
    for (int level = 4; level >= 0; level--) {
      if (strokes >= minStrokes[level] && strokes <= maxStrokes[level]) {
        ArrayList<Kanji> aux = table[level][strokes - minStrokes[level]].listTreeInOrder();
        if (aux != null) list.addAll(aux);
      }
    }

    return list;
  }

  //Obtiene todos los kanji con un número de trazos en el nivel especificado (entre 1 y 5)
  public ArrayList<Kanji> getKanjiWithStrokes(int level, int strokes)
      throws InvalidStrokeCountException, InvalidLevelException {
    if (level < 1 || level > 5) throw new InvalidLevelException();
    level--;

    strokes -= minStrokes[level];
    if (strokes < minStrokes[level] - 1 || strokes > maxStrokes[level] - 1)
      throw new InvalidStrokeCountException(minStrokes[level], maxStrokes[level]);

    return table[level][strokes].listTreeInOrder();
  }
}
