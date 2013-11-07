package petcolorcreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;


public class PetColorCreator extends JFrame implements WindowListener, ActionListener {
	final static String VERSION = "1.0";
	
    private JEditorPane formatter;
    private ImageTextPane formatted;
    private Font mcFont;
    private JMenuBar menubar;
    private JMenu help;
    private JMenu about;
    private JMenu forum;
    private JMenu issues;
    private JMenu exit;

    
    public static void main(String[] args) {
        PetColorCreator minecraftTextFormatter = new PetColorCreator("Pet Name Color Creator" + VERSION);
        
    }
 
    public PetColorCreator(String title) throws HeadlessException {
        super(title);
        this.setLayout(new FlowLayout());
        this.addWindowListener(this);
        
        
        
        menubar = new JMenuBar();
        add(menubar);

        about = new JMenu("About");
        menubar.add(about);
        
        forum = new JMenu("HypixelForum Thread");
        menubar.add(forum);
        
        issues = new JMenu("Bugs/Issues");
        menubar.add(issues);
        
        help = new JMenu("Help");
        menubar.add(help);
        
        exit = new JMenu("Exit");
        menubar.add(exit);

        setJMenuBar(menubar);
        
        about.addActionListener(new java.awt.event.ActionListener(){
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
          
                }
        });

        forum.addActionListener(this);
        issues.addActionListener(this);
        help.addActionListener(this);
        exit.addActionListener(this);
        
        
 
        formatter = new JEditorPane();
        try {
            formatted = new ImageTextPane(ImageIO.read(this.getClass().getResourceAsStream("img.jpg")));
        } catch (IOException ex) {
            Logger.getLogger(PetColorCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        formatter.setPreferredSize(new Dimension(600, 60));
        formatted.setContentType("text/html");
 
        try {
            final Font f = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("font.ttf"));
            mcFont = f;
            final String bodyRule = "body { font-family: " + f.getFamily() + "; "
                    + "font-size: " + 16 + "pt; "
                    + "color: #ffffff;"
                    + "text-shadow: 0.1em 0.1em 0.2em black; }";
            ((HTMLDocument) formatted.getDocument()).getStyleSheet().addRule(bodyRule);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
            formatted.setFont(f.deriveFont(Font.PLAIN, 20));
            formatter.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    formatted.setText(formatText(formatter.getText()));
                }
            });
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(PetColorCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        formatted.setPreferredSize(new Dimension(600, 60));
 
        this.add(formatColorButton("§4", new Color(0xbe0000), true));
        this.add(formatColorButton("§c", new Color(0xfe3f3f)));
        this.add(formatColorButton("§6", new Color(0xD9A334)));
        this.add(formatColorButton("§e", new Color(0xfefe3f)));
        this.add(formatColorButton("§2", new Color(0x00be00)));
        this.add(formatColorButton("§a", new Color(0x3ffe3f)));
        this.add(formatColorButton("§b", new Color(0x3ffefe)));
        this.add(formatColorButton("§3", new Color(0x00bebe)));
        this.add(formatColorButton("§1", new Color(0x0000be), true));
        this.add(formatColorButton("§9", new Color(0x3f3ffe)));
        this.add(formatColorButton("§d", new Color(0xfe3ffe)));
        this.add(formatColorButton("§5", new Color(0xbe00be)));
        this.add(formatColorButton("§f", new Color(0xffffff)));
        this.add(formatColorButton("§7", new Color(0xbebebe)));
        this.add(formatColorButton("§8", new Color(0x3f3f3f), true));
        this.add(formatColorButton("§0", new Color(0x000000), true));
        this.add(Box.createRigidArea(new Dimension(20, 15)));
        this.add(formatButton("§n"));
        this.add(formatButton("§l"));
        this.add(formatButton("§m"));
        this.add(formatButton("§o"));
        this.add(formatter);
        this.add(formatted);
        formatted.setEditable(false);
        this.setSize(625, 250);
        this.setBackground(Color.white);
        this.setResizable(false);
        this.setVisible(true);
        formatter.grabFocus();
    }
 
    private JButton formatButton(final String insert) {
        JButton btn = formatColorButton(insert, new Color(0xbebebe));
        if (insert.contains("n")) {
            btn.setText("<html><u>" + btn.getText() + "</u></html>");
        } else if (insert.contains("l")) {
            btn.setText("<html><b>" + btn.getText() + "</b></html>");
        } else if (insert.contains("m")) {
            btn.setText("<html><s>" + btn.getText() + "</s></html>");
        } else if (insert.contains("o")) {
            btn.setText("<html><i>" + btn.getText() + "</i></html>");
        }
        return btn;
    }
 
    private JButton formatColorButton(final String insert, Color c) {
        JButton red = new JButton(insert);
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = formatter.getText();
                int caretpos = formatter.getCaretPosition();
                text = text.substring(0, caretpos) + insert + text.substring(caretpos);
                formatter.setText(text);
                formatter.setCaretPosition(caretpos + 2);
                formatted.setText(formatText(formatter.getText()));
                formatter.grabFocus();
            }
        });
        red.setBackground(c);
        red.setFont(red.getFont().deriveFont(10f));
        red.setMargin(new Insets(0, 0, 0, 0));
        red.setBorder(null);
        red.setPreferredSize(new Dimension(20, 15));
        return red;
    }
 
    private JButton formatColorButton(final String insert, Color c, boolean whiteText) {
        JButton btn = formatColorButton(insert, c);
        if (whiteText) {
            btn.setForeground(Color.white);
        }
        return btn;
    }
 
    private String formatText(String text) {
        text = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<font face='Minecraft'>");
            if (text.contains("§")) {
                String[] split = text.split("§");
                String splitStr;
                for (int i = 0; i < split.length; i++) {
                    splitStr = split[i];
                    if (i != 0) {
                        String currentString = splitStr.substring(1, splitStr.length());
                        System.out.println(currentString);
                        sb.append(formatCode(splitStr.charAt(0))).append(currentString);
                    } else {
                        sb.append(splitStr);
                    }
                }
                sb.append("</font>");
                return sb.toString();
            } else {
                return text;
            }
        } catch (Exception e) {
            return text;
        }
    }
    
 
    private String formatCode(char format) {
        System.out.println("formatting: " + format);
        switch (format) {
            case '4':
                return "</b></u></i></s><font color='#be0000'>";
            case 'c':
                return "</b></u></i></s><font color='#fe3f3f'>";
            case '6':
                return "</b></u></i></s><font color='#D9A334'>";
            case 'e':
                return "</b></u></i></s><font color='#fefe3f'>";
            case '2':
                return "</b></u></i></s><font color='#00be00'>";
            case 'a':
                return "</b></u></i></s><font color='#3ffe3f'>";
            case 'b':
                return "</b></u></i></s><font color='#3ffefe'>";
            case '3':
                return "</b></u></i></s><font color='#00bebe'>";
            case '1':
                return "</b></u></i></s><font color='#0000be'>";
            case '9':
                return "</b></u></i></s><font color='#3f3ffe'>";
            case 'd':
                return "</b></u></i></s><font color='#fe3ffe'>";
            case '5':
                return "</b></u></i></s><font color='#be00be'>";
            case 'f':
                return "</b></u></i></s><font color='#ffffff'>";
            case '7':
                return "</b></u></i></s><font color='#bebebe'>";
            case '8':
                return "</b></u></i></s><font color='#3f3f3f'>";
            case '0':
                return "</b></u></i></s><font color='#000000'>";
 
            case 'n':
                return "</b><u></i></s>";
            case 'l':
                return "<b></u></i></s>";
            case 'm':
                return "<b></u></i><s>";
            case 'o':
                return "</b></u><i></s>";
        }
        return "";
    }
 
    @Override
    public void windowOpened(WindowEvent e) {
    }
 
    @Override
    public void windowClosing(WindowEvent e) {
    }
 
    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }
 
    @Override
    public void windowIconified(WindowEvent e) {
    }
 
    @Override
    public void windowDeiconified(WindowEvent e) {
    }
 
    @Override
    public void windowActivated(WindowEvent e) {
    }
 
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
   
 
    private static class ImageTextPane extends JTextPane {
 
        BufferedImage img;
 
        public ImageTextPane(BufferedImage img) {
            super();
            this.img = img;
            setOpaque(false);
            setBackground(new Color(0, 0, 0, 0));
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, this);
            super.paintComponent(g);
        }
    }

	@Override
	public void actionPerformed(ActionEvent click){
		
	
	}
  }

