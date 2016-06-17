import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import movie.maker.vilius.kraujutis.lt.JpegImagesToMovie;

class RecorderUtil implements Runnable
{
    private Robot robot=null;
    private File folder=null;
    private boolean stopFlag=false;
    private Thread thread=null;
    private int width,height;

    public RecorderUtil(File folder) throws Exception{
        this.folder=folder;
        if(null==folder ||folder.isDirectory()==false){
            throw new IllegalArgumentException("You must choose a Directory");
        }
        this.robot=new Robot();
        //robot.setAutoDelay(500);
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        this.width=dim.width;
        this.height=dim.height;
        this.thread=new Thread(this);
        thread.start();
    }

    public void stopRecording(){
        this.stopFlag=true;
    }

    public BufferedImage getSnapShot() throws Exception{
        return robot.createScreenCapture(new Rectangle(width,height));
    }

    public String getFileName(){
        return String.valueOf(new java.util.Date().getTime())+".jpg";
    }
    public void run(){
        while(!stopFlag){
            try{
                BufferedImage img=getSnapShot();
                String savePath=folder.getAbsolutePath()+File.separator+getFileName();
                ImageIO.write(img,"jpeg",new File(savePath));
                Thread.sleep(200);

            }catch(Throwable t){
                throw new RuntimeException(t);
            }
        }
    }

    public void saveMovie() throws Exception{
        stopRecording();
        thread.join();
        final int FRAME_RATE=10;
        String dirStr=folder.getAbsolutePath()+File.separator;
        String[] strFiles=folder.list();
        java.util.Vector inFilesVector=new java.util.Vector();
        for(int i=0;i<strFiles.length;i++){

            inFilesVector.addElement(dirStr+strFiles[i]);
        }
            javax.media.MediaLocator outML=new javax.media.MediaLocator(new File(dirStr+"desktop.avi").toURL());
            JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
            imageToMovie.doIt(width, height, FRAME_RATE, inFilesVector, outML);



    }

}
