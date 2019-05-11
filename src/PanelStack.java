import javax.swing.JPanel;

public class PanelStack {
    private NPanel last;
    private int size = 0;

    //Clase privada "NPanel" dentro de Pila
    private final class NPanel{
        private final JPanel panel;
        private NPanel next;

        //Constructor
        public NPanel(JPanel panel){
            this.panel = panel;
            this.next = null;
        }

        public NPanel(JPanel panel, NPanel next){
            this.panel = panel;
            this.next = next;
        }

        public JPanel getPanel(){
            return this.panel;
        }

        public NPanel getNext(){
            return this.next;
        }
    }

    public PanelStack(){
        this.last = null;
    }

    public PanelStack(JPanel panel){
        this.last = new NPanel(panel);
        this.size++;
    }

    private void setLast(NPanel last){
        this.last = last;
    }

    private NPanel getLast(){
        return this.last;
    }

    public int getSize(){
        return this.size;
    }

    public void push(JPanel panel){
        if(this.getLast() == null) this.setLast(new NPanel(panel)); //Se añade el primer elemento
        else{
            NPanel aux = new NPanel(panel, last);
            this.setLast(aux);
        }
        this.size++;
    }

    public JPanel pop() throws StackEmptyException{
        if(this.last == null) throw new StackEmptyException("Pila Vacia");
        JPanel panel = this.getLast().getPanel();
        this.setLast(this.getLast().getNext());
        this.size--;
        return panel;
    }
}
