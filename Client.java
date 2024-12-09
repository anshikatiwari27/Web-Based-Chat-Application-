
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client implements ActionListener{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    static JFrame f1 = new JFrame();
   Client(){
    // setting the layout to null 
    f1.setLayout(null);

    // adding the panel to the frame as header 
    JPanel p1 = new JPanel();
    p1.setBackground(new Color(7,94,84));
    // setting the location to the panel where it will be visible at the frame
    // setBounds x & y tell the location of the panel and next 2 are the size of panel
    p1.setBounds(0,0,450,70);
    p1.setLayout(null);
    f1.add(p1);

    //Adding the back button
    ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("3.png"));
    Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    JLabel back = new JLabel(i3);
    back.setBounds(5,20,25,25);
    p1.add(back);

    //Adding the profile pic
    ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("2.png"));
    Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    ImageIcon i6 = new ImageIcon(i5);
    JLabel profile = new JLabel(i6);
    profile.setBounds(40,10,50,50);
    p1.add(profile);
    
    //Adding the video icon 
    ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("video.png"));
    Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    ImageIcon i9 = new ImageIcon(i8);
    JLabel video = new JLabel(i9);
    video.setBounds(290,20,30,30);
    p1.add(video);

    //Adding the calling option
    ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("phone.png"));
    Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
    ImageIcon i12 = new ImageIcon(i11);
    JLabel call = new JLabel(i12);
    call.setBounds(350,20,35,30);
    p1.add(call);

     //Adding the more option
     ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("3icon.png"));
     Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
     ImageIcon i15 = new ImageIcon(i14);
     JLabel more = new JLabel(i15);
     more.setBounds(410,20,10,25);
     p1.add(more);
    
     //Adding the Title for name
     JLabel name2 = new JLabel("Receiver");
     name2.setBounds(110,15,100,18);
     name2.setForeground(Color.white);
     name2.setFont(new Font("SANS_SARIF",Font.BOLD,18));
     p1.add(name2);

     //Adding the status 
     JLabel status = new JLabel("Active Now");
     status.setBounds(110,35,100,18);
     status.setForeground(Color.white);
     p1.add(status);

     // Creating the JPanel for the chat messages
     a1 = new JPanel();
     a1.setBounds(5,75,440,570);
     f1.add(a1);
     
     //Adding the text feild
     text = new JTextField();
     text.setBounds(5,655,310,40);
     text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
     f1.add(text);

     //Adding the text button
    JButton send = new JButton("Send");
    send.setBounds(320, 655, 123, 40);
    send.setBackground(new Color(7, 94, 84));
    send.setForeground(Color.WHITE);
    //Adding action listner
    send.addActionListener(this);
    send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    f1.add(send);
    // Adding the close option to the back button
    back.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent ae){
            System.exit(0);
        }
    });
    
    
    f1.setUndecorated(true);
    f1.setSize(450,700);
    f1.setLocation(800,50);
    f1.getContentPane().setBackground(Color.white);
    f1.setVisible(true);
    
   }

   public void actionPerformed(ActionEvent ae){
           try{ 
           String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            // sending the messege receiver to sender
            dout.writeUTF(out);
            //making the text feild empty when send messege
            text.setText("");
            f1.repaint();
            f1.invalidate();
            f1.validate();
           }catch(Exception e){
            e.printStackTrace();
           }   
   }
   public static JPanel formatLabel(String out) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
     JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        // by this fn the added color is visible
        output.setOpaque(true);
        //adding the border to the box
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);

        //Adding thi time of the messege
        Calendar cal = Calendar.getInstance();
        //Formatting the date format
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);

    return panel;
   }


    public static void main( String args[]){
    new Client();
     try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                
                f1.validate();
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}