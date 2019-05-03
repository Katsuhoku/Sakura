/*
  File Handler class

  General purpose class to open, close, read from an write to files.

  Class manage binary files and writes whatever objects in file as Object.
  Objects readed with this class may require a cast to its corresponding
  class when implementing.

  All methods are static, so object creation is not required. Also, for this
  reason only one file can be opened. Before opening another file close
  any previous opened file.
*/

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.Exception;
import java.lang.NullPointerException;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FileHandler {
  private static ObjectOutputStream writer;
  private static ObjectInputStream reader;

  //File Opening
  /*Parameters:
    - pathName : path of the file that will be opened/created
    - mode : 'w' for writting, 'r' for reading
    Returns:
    - true : if the file was opened/created correctly
    - false : if cannot open/create file, or the opening mode is not valid
  */
  public static boolean openFile(String pathFile, char mode) {
    switch (mode) {
      case 'r':
      try {
        reader = new ObjectInputStream(new FileInputStream(pathFile));
      }
      catch (IOException e) {
        return false;
      }
      return true;

      case 'w':
      try {
        writer = new ObjectOutputStream(new FileOutputStream(pathFile));
      }
      catch (IOException e) {
        return false;
      }
      return true;

      default: return false;
    }
  }

  //File Closing
  /*Parameters:
    - mode : mode with was previously opened the file
    Returns:
    - true : if the file was closed correctly
    - false : if not, or mode is invalid or do not correspond with opening mode
  */
  public static boolean closeFile(char mode) {
    switch(mode){
      case 'r':
        try {
          reader.close();
        }
        catch (IOException | NullPointerException ioe){
          return false;
        }

        return true;
      case 'w':
        try {
          writer.close();
        }
        catch (IOException | NullPointerException ioe){
          return false;
        }

        return true;
      default: return false;
    }
  }

  //File Writing
  /*Parameters:
    - wObject : object to be writted in file
    Returns:
    - true : if the object was correctly writed
    - false : if not
  */
  public static boolean writeFile(Object wObject) {
    try {
      writer.writeObject(wObject);
      return true;
    }
    catch (Exception e) {}
    return false;
  }

  //File Reading
  /*No parameters.
    Returns:
    - One object readed from file as Object. (Casting may be required when
      implementing)
    - null : if cannot read correctly (corrupted or empty file)
  */
  public static Object readFile() {
    Object rObject;
    try {
      rObject = reader.readObject();
      return rObject;
    }
    catch (Exception e) {}
    return null;
  }
}
