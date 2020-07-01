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


/*640 x 360 (nHD)
854 x 480 (FWVGA)
960 x 540 (qHD)
1024 x 576 (WSVGA)
1280 x 720 (HD/WXGA)
1366 x 768 (FWXGA)
1600 x 900 (HD+)
1920 x 1080 (FHD)******
2048 x 1152 (QWXGA)******

MAX For THIS COMPUTER ^^^^^^

2560 x 1440 (QHD)
3200 x 1800 (WQXGA+)
3840 x 2160 (UHD)
5120 x 2880 (UHD+)
7680 x 4320 (FUHD)
15360 x 8640 (QUHD)
30720 x 17280 (HHD)
61440 x 34560 (FHHD)
122880 x 69120 (QHHD)*/