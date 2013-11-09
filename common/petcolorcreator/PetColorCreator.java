package petcolorcreator;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;

public class PetColorCreator extends JFrame {
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
        PetColorCreator form = new PetColorCreator("Pet Name Color Creator" + VERSION);
        form.setVisible(true);
    }
 
    public PetColorCreator(String title) throws HeadlessException {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title);
        
        menubar = new JMenuBar();
        add(menubar);

        about = new JMenu("About");
        menubar.add(about);
        
        forum = new JMenu("Hypixel Forum Thread");
        menubar.add(forum);
        
        issues = new JMenu("Bugs/Issues");
        menubar.add(issues);
        
        help = new JMenu("Help");
        menubar.add(help);
        
        exit = new JMenu("Exit");
        menubar.add(exit);

        setJMenuBar(menubar);
        
        //Check if desktop is supported
        forum.setEnabled(false);
        issues.setEnabled(false);
        if (Desktop.isDesktopSupported()) {
            final Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                forum.setEnabled(true);
                issues.setEnabled(true);
                forum.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                    	try {
                            URI uri = new URI("http://hypixel.net/threads/pet-name-color-generator.29022");
                            desktop.browse(uri);
                        }
                        catch(IOException ioe) {}
                        catch(URISyntaxException use) {}
                    }
                });
                issues.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                    	try {
                            URI uri = new URI("https://github.com/McKiller5252/PetColorCreator/issues");
                            desktop.browse(uri);
                        }
                        catch(IOException ioe) {}
                        catch(URISyntaxException use) {}
                    }
                });
            }
        }
        
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                About form = new About();
                form.setVisible(true);
            }
        });
        
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                System.exit(0);
            }
        });

        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                Help form = new Help();
            form.setVisible(true);
            }
        });
 
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
 
        add(formatColorButton("&4", new Color(0xbe0000), true));
        add(formatColorButton("&c", new Color(0xfe3f3f)));
        add(formatColorButton("&6", new Color(0xD9A334)));
        add(formatColorButton("&e", new Color(0xfefe3f)));
        add(formatColorButton("&2", new Color(0x00be00)));
        add(formatColorButton("&a", new Color(0x3ffe3f)));
        add(formatColorButton("&b", new Color(0x3ffefe)));
        add(formatColorButton("&3", new Color(0x00bebe)));
        add(formatColorButton("&1", new Color(0x0000be), true));
        add(formatColorButton("&9", new Color(0x3f3ffe)));
        add(formatColorButton("&d", new Color(0xfe3ffe)));
        add(formatColorButton("&5", new Color(0xbe00be)));
        add(formatColorButton("&f", new Color(0xffffff)));
        add(formatColorButton("&7", new Color(0xbebebe)));
        add(formatColorButton("&8", new Color(0x3f3f3f), true));
        add(formatColorButton("&0", new Color(0x000000), true));
        add(Box.createRigidArea(new Dimension(20, 15)));
        add(formatButton("&n"));
        add(formatButton("&l"));
        add(formatButton("&m"));
        add(formatButton("&o"));
        add(formatter);
        add(formatted);
        formatted.setEditable(false);
        setSize(625, 250);
        setBackground(Color.white);
        setResizable(false);
        setVisible(true);
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
            if (text.contains("&")) {
                String[] split = text.split("&");
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

    private class About extends JFrame {
        public About() {
            //Insert About form generation code here.
            JLabel title = new JLabel();
            JLabel auth = new JLabel();
            JLabel ver = new JLabel();
            
            setTitle("About");
            title.setText("Title: Pet Color Creator");
            auth.setText("Author: Killer5252 and pigdevil2010");
            ver.setText("Version: " + VERSION);
            
            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            GroupLayout gl_contentPane = new GroupLayout(contentPane);
            gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_contentPane.createSequentialGroup()
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                            .addComponent(title)
                            .addComponent(auth)
                            .addComponent(ver))
                        .addContainerGap(378, Short.MAX_VALUE))
            );
            gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(title)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(auth)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(ver)
                        .addContainerGap(197, Short.MAX_VALUE))
            );
            contentPane.setLayout(gl_contentPane);
            setSize(300,150);
            setResizable(false);
        }
    }
    
    private class Help extends JFrame {
        public Help() {
            //Insert About form generation code here.
            JLabel inst = new JLabel();
            JLabel info1 = new JLabel();
            JLabel info2 = new JLabel();
            JLabel info3 = new JLabel();
            
            setTitle("Help");
            inst.setText("Instructions:");
            info1.setText("Type anything you want in the textbox and it you want to color a word");
            info2.setText("just press one of the color buttons above and place the color tag before the word");
            info3.setText("Example: &eSexyKitty will give you a yellow word");
            
            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            GroupLayout gl_contentPane = new GroupLayout(contentPane);
            gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_contentPane.createSequentialGroup()
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                            .addComponent(inst)
                            .addComponent(info1)
                            .addComponent(info2)
                            .addComponent(info3))
                        .addContainerGap(197, Short.MAX_VALUE))
            		);
            gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(inst)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(info1)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(info2)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(info3)
                        .addContainerGap(197, Short.MAX_VALUE))
            );
            contentPane.setLayout(gl_contentPane);
            setSize(600,200);
            setResizable(false);
        }
    }
  }