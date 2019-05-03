import java.util.ArrayList;
import java.io.Serializable;

public class Kanji implements Serializable {
  //Atributos de clase
  public static final int VERSION = 1;

  //Atributos
  private final String logogram;
  private final int level;
  private final int strokeCount;
  private final int frequency;
  private ArrayList<String> meaning;
  private ArrayList<String> onyomi;
  private ArrayList<String> kunyomi;

  //Métodos
  //Constructor
  public Kanji(String logogram, int level, int strokeCount, int frequency) {
    this.logogram = logogram;
    this.level = level;
    this.strokeCount = strokeCount;
    this.frequency = frequency;

    meaning = onyomi = kunyomi = null;
  }

  public Kanji(String kanjiFormat) {
    ArrayList<String> fragments = new ArrayList<>();
    for (int end = 0, start = 0; end < kanjiFormat.length(); end++) {
      if (kanjiFormat.charAt(end) == '/' || kanjiFormat.charAt(end) == ';') {
        fragments.add(kanjiFormat.substring(start, end));
        start = end + 1;
        end++;
      }
    }

    this.logogram = fragments.get(0);
    this.level = Integer.parseInt(fragments.get(1));
    this.strokeCount = Integer.parseInt(fragments.get(2));
    this.frequency = Integer.parseInt(fragments.get(3));
    this.onyomi = new ArrayList<>();
    this.kunyomi = new ArrayList<>();
    this.meaning = new ArrayList<>();

    String onreadings = fragments.get(4).concat("*");
    if (onreadings.codePointAt(0) == 0x002A) {
      this.onyomi.add("(No especificado)");
    }
    else {
      for (int end = 0, start = 0; end < onreadings.length(); end++) {
        if (onreadings.codePointAt(end) == 0x002A) {
          onyomi.add(onreadings.substring(start, end));
          start = end + 1;
          end++;
        }
      }
    }

    String kunreadings = fragments.get(5).concat("*");
    if (kunreadings.codePointAt(0) == 0x002A) {
      this.kunyomi.add("(No especificado)");
    }
    else {
      for (int end = 0, start = 0; end < kunreadings.length(); end++) {
        if (kunreadings.codePointAt(end) == 0x002A) {
          kunyomi.add(kunreadings.substring(start, end));
          start = end + 1;
          end++;
        }
      }
    }

    String meanings = fragments.get(6).concat("*");
    if (meanings.codePointAt(0) == 0x002A) {
      this.meaning.add("(No especificado)");
    }
    else {
      for (int end = 0, start = 0; end < meanings.length(); end++) {
        if (meanings.codePointAt(end) == 0x002A) {
          meaning.add(meanings.substring(start, end));
          start = end + 1;
          end++;
        }
      }
    }
  }

  //Genera la clave del objeto
  public String genKey() {
    StringBuffer auxKey = new StringBuffer();

    auxKey.append("N" + level);

    if (strokeCount < 10) auxKey.append("0");
    auxKey.append(strokeCount);

    if (frequency < 10) auxKey.append("000");
    else if (frequency < 100) auxKey.append("00");
    else if (frequency < 1000) auxKey.append("0");
    auxKey.append(frequency);

    return auxKey.toString();
  }

  public String getLogogram() {
    return logogram;
  }

  public int getLevel() {
    return level;
  }

  public int getStrokeCount() {
    return strokeCount;
  }

  public int getFrequency() {
    return frequency;
  }

  public ArrayList<String> getOnYomi() {
    return onyomi;
  }

  public ArrayList<String> getKunYomi() {
    return kunyomi;
  }

  public ArrayList<String> getMeaning() {
    return meaning;
  }

  public void setOnYomi(ArrayList<String> onyomi) {
    if (onyomi == null) {
      onyomi = new ArrayList<String>();
      onyomi.add("(No especificado)");
    }
    this.onyomi = onyomi;
  }

  public void setKunYomi(ArrayList<String> kunyomi) {
    if (kunyomi == null) {
      kunyomi = new ArrayList<String>();
      kunyomi.add("(No especificado)");
    }
    this.kunyomi = kunyomi;
  }

  public void setMeaning(ArrayList<String> meaning) {
    if (meaning == null) {
      meaning = new ArrayList<String>();
      meaning.add("(No especificado)");
    }
    this.meaning = meaning;
  }

  @Override
  public String toString() {
    StringBuffer format = new StringBuffer();

    format.append(logogram + "/" + level + "/" + strokeCount + "/" + frequency + "/");

    int i = 1;
    for (String onreading : onyomi) {
      if (onreading.compareTo("(No especificado)") == 0) {
        format.append("*/");
        break;
      }
      else {
        format.append(onreading);
        if (i == onyomi.size()) format.append("/");
        else format.append("*");
      }
      i++;
    }

    i = 1;
    for (String kunreading : kunyomi) {
      if (kunreading.compareTo("(No especificado)") == 0) {
        format.append("*/");
        break;
      }
      else {
        format.append(kunreading);
        if (i == kunyomi.size()) format.append("/");
        else format.append("*");
      }
      i++;
    }

    i = 1;
    for (String mean : meaning) {
      if (mean.compareTo("(No especificado)") == 0) {
        format.append("*;");
        break;
      }
      else {
        format.append(mean);
        if (i == meaning.size()) format.append(";");
        else format.append("*");
      }
      i++;
    }

    return format.toString();
  }
}
