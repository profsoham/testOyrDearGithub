import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DesktopRecorder extends JFrame implements ActionListener
{
    private JButton startBtn=new JButton("Start Record");
    private JButton stopBtn=new JButton("Stop Record");
    private RecorderUtil util=null;
    public DesktopRecorder() throws Exception{
        super("Soham Sengupta's Desktop Activity Recorder");
        this.getContentPane().setLayout(new FlowLayout());
        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);
        this.getContentPane().add(startBtn);
        this.getContentPane().add(stopBtn);
        this.setSize(300,300);
        this.setLocation(380,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }



    private File getFolder(){
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option=jfc.showSaveDialog(this);
        File folder=option==JFileChooser.APPROVE_OPTION?jfc.getSelectedFile():null;
        return folder;
    }



    public void actionPerformed(ActionEvent ae){
        Object obj=ae.getSource();

        if(obj==startBtn){
            File folder=getFolder();
            if(null!=folder){
                try{
                                    util=new RecorderUtil(folder);
                    }catch(Throwable t){
                        JOptionPane.showMessageDialog(this,t.toString());
                    }
            }
        }

        if(obj==stopBtn){
            try{
                    util.saveMovie();
                }catch(Throwable t){
                    JOptionPane.showMessageDialog(this,t.toString());
                }
        }


    }

public static void main(String[] art) throws Exception{
    new DesktopRecorder();
}

}