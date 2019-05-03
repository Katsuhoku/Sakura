import java.util.ArrayList;
import java.io.Serializable;

//Arbol AVL (para almacenamiento de Kanji)
public class AVLTree implements Serializable {
  //Nodo del Arbol Binario
  private class Node implements Serializable {
    private Kanji kanji;
    private int FB; //Factor de Equilibrio
    private Node left; //Nodo izquierdo
    private Node right; //Nodo derecho

    //Constructor
    public Node(Kanji kanji) {
      this.kanji = kanji;
      left = right = null;
      FB = 0;
    }

    //Actualiza con las alturas de los subárboles (?)
    public void updateFB() {
      int leftHeight = -1;
      int rightHeight = -1;

      if (left != null) leftHeight = left.height();
      if (right != null) rightHeight = right.height();
      FB = rightHeight - leftHeight;
    }

    //Actualiza con referencia a los FB de los subárboles y el valor introducido
    public void updateFB(int freq) {
      if (freq < kanji.getFrequency()) {
        if ((left.getKanji().getFrequency() == freq && left.getRight() == null && left.getLeft() == null)
            || left.getFB() != 0) FB--;
      }
      else if (freq > kanji.getFrequency()) {
        if ((right.getKanji().getFrequency() == freq && right.getRight() == null && right.getLeft() == null)
            || right.getFB() != 0) FB++;
      }
    }

    //Calcula la altura desde el nivel 0
    private int height() {
      return height(0);
    }

    //Calcula la altura (recursivo)
    private int height(int level) {
      int levelL = level;
      int levelR = level;
      if (left != null) levelL = left.height(level + 1);
      if (right != null) levelR = right.height(level + 1);

      return (levelL > levelR ? levelL : levelR);
    }

    //Muestra en Orden
    public ArrayList<Kanji> listInOrder() {
      ArrayList<Kanji> list = new ArrayList<Kanji>();

      if (left != null) list.addAll(left.listInOrder());
      list.add(kanji);
      if (right != null) list.addAll(right.listInOrder());

      return list;
    }

    public void setLeft(Node node) {
      left = node;
    }

    public void setRight(Node node) {
      right = node;
    }

    public void setFB(int value) {
      FB = value;
    }

    public Kanji getKanji() {
      return kanji;
    }

    public Node getLeft() {
      return left;
    }

    public Node getRight() {
      return right;
    }

    public int getFB() {
      return FB;
    }
  }

  private Node root; //Raíz del árbol

  //Constructor sin datos
  public AVLTree() {
    root = null;
  }

  //Constructor con información en la raíz
  public AVLTree(Kanji kanji) {
    root = new Node(kanji);
  }

  //Añadir un nodo al árbol, de forma ordenada
  public boolean add(Kanji kanji) {
    try {
      root = add(kanji, root);
      return true;
    } catch(SameNodeException sne) {
      return false;
    }
  }

  //Añadir un nodo al árbol (recursividad)
  private Node add(Kanji kanji, Node root) throws SameNodeException {
    if (root == null) return (root = new Node(kanji));
    else if (kanji.getFrequency() < root.getKanji().getFrequency()) root.setLeft(add(kanji, root.getLeft()));
    else if (kanji.getFrequency() > root.getKanji().getFrequency()) root.setRight(add(kanji, root.getRight()));
    else if (kanji.getFrequency() == root.getKanji().getFrequency()) throw new SameNodeException("\nKanji already in system.\n");
    root.updateFB(kanji.getFrequency()); //Actualiza Factor de Equilibrio

    //Equiilibrio
    if (root.getFB() == -2) {
      if (root.getLeft().getFB() == 1) root.setLeft(LeftSimpleRotation(root.getLeft()));
      root = RightSimpleRotation(root);
    }
    else if (root.getFB() == 2) {
      if (root.getRight().getFB() == -1) root.setRight(RightSimpleRotation(root.getRight()));
      root = LeftSimpleRotation(root);
    }
    return root;
  }

  //Rotación Derecha Simple
  private Node RightSimpleRotation(Node root) {
    Node aux = root.getLeft();
    root.setLeft(aux.getRight());
    aux.setRight(root);
    root.updateFB();
    aux.updateFB();
    return aux;
  }

  //Rotación Izquierda Simple
  private Node LeftSimpleRotation(Node root) {
    Node aux = root.getRight();
    root.setRight(aux.getLeft());
    aux.setLeft(root);
    root.updateFB();
    aux.updateFB();
    return aux;
  }

  //Eliminar un nodo del árbol (No funcional)
  /*public boolean delete(int inf) {
    if (!contains(inf)) return false;

    root = delete(inf, root);
    return true;
  }*/

  //Eliminar (recursividad) (No funcional)
/*  private Node delete(int inf, Node root) {
    if (inf < root.getInf() && root.getLeft() != null) root.setLeft(delete(inf, root.getLeft()));
    else if (inf > root.getInf() && root.getRight() != null) root.setRight(delete(inf, root.getRight()));
    else if (inf == root.getInf()) {
      if (root.getLeft() == null && root.getRight() == null) root = null; //Sin hijos
      else if (root.getLeft() != null && root.getRight() != null) { //Dos hijos
        //Búsqueda del menor de los mayores
        Node aux = root.getRight();
        while (aux.getLeft() != null) aux = aux.getLeft();

        //Elimina el ciclo creado
        Node aux2 = root.getRight();
        do {
          if (aux2.getLeft() == aux) aux2.setLeft(aux.getRight());
          aux2 = aux2.getLeft();
        } while (aux2 != null);

        aux.setLeft(root.getLeft());
        if (aux != root.getRight()) aux.setRight(root.getRight());
        root = aux;
      }
      else { //Un hijo
        if (root.getLeft() != null) root = root.getLeft();
        else root = root.getRight();
      }
    }
    return root;
  }*/

  /*//Existencia de un nodo (retorna verdader o falso)
  public boolean contains(Kanji kanji) {
    return (binarySearch(kanji.getFrequency()) != null);
  }*/

  //Retorno de un nodo por frecuencia
  public Kanji search(int freq) {
    Node aux = binarySearch(freq, root);

    if (aux != null) return aux.getKanji();
    else return null;
  }

  //Búsqueda Binaria (retorna nodo)
  private Node binarySearch(int freq) {
    return binarySearch(freq, root);
  }

  //Búsqueda Binaria (recursividad)
  private Node binarySearch(int freq, Node root) {
    if (root == null) return null;
    else if (freq < root.getKanji().getFrequency()) return binarySearch(freq, root.getLeft());
    else if (freq > root.getKanji().getFrequency()) return binarySearch(freq, root.getRight());
    return root;
  }

  public ArrayList<Kanji> listTreeInOrder() {
    if (root == null) return null;
    return root.listInOrder();
  }
}
