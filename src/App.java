import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws Exception {

        //Make sure this is always in a 16:9 ratio format or more acurately a 64:33 ratio 
        //Dont try to do 4k it doesnt work on this computer
        Runnable init = () -> new UI();
        
        //Puts stuff in a quene to make sure running long operations doesn't freeze up anything
        //Use when you are about to run along operation on a button click for instance redrawing everything
        SwingUtilities.invokeLater(init); 
    }

}


