public class InvalidStrokeCountException extends Exception {
  //Atributos
  int minStrokes;
  int maxStrokes;

  //Constructor
  public InvalidStrokeCountException(int minStrokes, int maxStrokes) {
    super();
    this.minStrokes = minStrokes;
    this.maxStrokes = maxStrokes;
  }

  @Override
  public String toString() {
    return "El Número de Trazos debe estar entre " + minStrokes + " y " + maxStrokes;
  }
}
