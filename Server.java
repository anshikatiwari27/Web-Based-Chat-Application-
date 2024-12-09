

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;

public class Server implements ActionListener{
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    static Socket socket;

   Server(){
    // setting the layout to null 
    f.setLayout(null);

    // adding the panel to the frame as header 
    JPanel p1 = new JPanel();
    p1.setBackground(new Color(7,94,84));
    // setting the location to the panel where it will be visible at the frame
    // setBounds x & y tell the location of the panel and next 2 are the size of panel
    p1.setBounds(0,0,450,70);
    p1.setLayout(null);
    f.add(p1);
    //Adding the back button
    ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("3.png"));
    Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    JLabel back = new JLabel(i3);
    back.setBounds(5,20,25,25);
    p1.add(back);

    //Adding the profile pic
    ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("1.png"));
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
     JLabel name = new JLabel("Sender");
     name.setBounds(110,15,100,18);
     name.setForeground(Color.white);
     name.setFont(new Font("SANS_SARIF",Font.BOLD,18));
     p1.add(name);

     //Adding the status 
     JLabel status = new JLabel("Active Now");
     status.setBounds(110,35,100,18);
     status.setForeground(Color.white);
     p1.add(status);

     // Creating the JPanel for the chat messages
     a1 = new JPanel();
     a1.setBounds(5,75,440,570);
     f.add(a1);
     
     //Adding the text feild
     text = new JTextField();
     text.setBounds(5,655,310,40);
     text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
     f.add(text);

     //Adding the text button
    JButton send = new JButton("Send");
    send.setBounds(320, 655, 123, 40);
    send.setBackground(new Color(7, 94, 84));
    send.setForeground(Color.WHITE);
    //Adding action listner
    send.addActionListener(this);
    send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    f.add(send);
    // Adding the close option to the back button
    back.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent ae){
            System.exit(0);
        }
    });
    
    
    f.setUndecorated(true);
    f.setSize(450,700);
    f.setLocation(200,50);
    f.getContentPane().setBackground(Color.white);
    f.setVisible(true);
    
   }

   public void actionPerformed(ActionEvent ae){
    try {
        String out = text.getText();
        if(dout == null){
            System.out.println("Data outputstream is null");
        }

        JPanel p2 = formatLabel(out);

        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical, BorderLayout.PAGE_START);
        // sending the message to the server
        dout.writeUTF(out);
        // making the text feild empty when press on send button
        text.setText("");

        f.repaint();
        f.invalidate();
        f.validate();   
    } catch (Exception e) {
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
    new Server();

    try {
            ServerSocket skt = new ServerSocket(6001);
            while(true) {
                Socket s = skt.accept();
                //ceating input stream to receive the messege
                DataInputStream din = new DataInputStream(s.getInputStream());
                
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    // to read the recieve messege we use readUTF method 
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}