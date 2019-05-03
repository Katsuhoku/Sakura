public class InvalidLevelException extends Exception {
  //Constructor
  public InvalidLevelException() {
    super();
  }

  @Override
  public String toString() {
    return "El Nivel debe estar entre 1 y 5";
  }
}
