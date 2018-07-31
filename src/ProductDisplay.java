import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.io.*;
//import java.awt.Color;
//import java.util.Calendar;
//import java.util.GregorianCalendar;

/**
 * ProductDisplay
 *
 * Display product and sale information from the selected retailers
 *
 */
public class ProductDisplay
{
    // Window Frame
    private JFrame frame;
    private JPanel contentPane;
    
    // Product Name
    private JLabel productLabel;

    private class RetailerUI
    {
        JCheckBox retailerBox;
        JLabel regPriceLabel;
        JLabel salePriceLabel;
        JLabel percentOffLabel;
        JTextField productQuantityBox;
    }

    ArrayList<RetailerUI> uiElements;
    
    private String productName;
    private String imageName;
    private JLabel imgLabel;
    private ImageIcon productIcon;
    
    private ArrayList<Product> list;
    private ListIterator<Product> lit;
    
    public static void main (String[] args){
        ProductDisplay gui = new ProductDisplay();
        gui.start();
    }
    
    public void start(){
        frame = new JFrame("Product Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeMenus();
        makeContent();

        frame.pack();
        frame.setVisible(true);
    }
    
    public void makeMenus(){
        JMenuBar menuBar;
        
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        // set up menus
        menuBar.add(makeFileMenu());
        menuBar.add(makeHelpMenu());
    }
    
    private JMenu makeFileMenu(){
        JMenu menu;
        JMenuItem menuItem;
        
        // set up the File menu
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        
        // add File menu items
        menuItem = new JMenuItem("New Order");
        menuItem.addActionListener(new newListener());
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        menu.add(menuItem);
        
        // add Open menu item
        menuItem = new JMenuItem("Open...");
        menuItem.addActionListener(new openListener());
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));
        menu.add(menuItem);
        
        // add Save menu item
        menuItem = new JMenuItem("Save...");
        menuItem.addActionListener(new saveListener());
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));
        menu.add(menuItem);
        
        // add Exit menu item
        menu.addSeparator();
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new exitListener());
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,Event.CTRL_MASK));
        menu.add(menuItem);
        
        return menu;
    }
    
    private JMenu makeHelpMenu(){
        JMenu menu;
        JMenuItem menuItem;
        
        // set up the Help menu
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        
        // add About menu item
        menuItem = new JMenuItem("About Product Display");
        menuItem.addActionListener(new aboutListener());
        menuItem.setMnemonic(KeyEvent.VK_A);
        menu.add(menuItem);
        
        return menu;
    }
    
    private class newListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            // reset all options
            int count = 1;
            for (RetailerUI rui : uiElements)
            {
                rui.retailerBox.setSelected(false);
                rui.retailerBox.setText("Retailer " + count);
                rui.regPriceLabel.setText("");
                rui.salePriceLabel.setText("");
                rui.productQuantityBox.setText("");
                count++;
            }

            productLabel.setText("Product Name");
            imageName = "";
            imgLabel.setIcon(new ImageIcon(imageName));
        }
    }
    
    private class openListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(frame);
            File productFile = fc.getSelectedFile();
            if (productFile == null){
                return;
            }

            list = new ArrayList<Product>();

            try{
                Scanner scan = new Scanner(productFile);
                productName = scan.nextLine();
                imageName = scan.nextLine();
                while(scan.hasNext()){
                    String retailerName = scan.next();
                    double regularPrice = scan.nextDouble();
                    double salePrice = scan.nextDouble();
                    String url = scan.next();
                    list.add(new Product(productName, retailerName, regularPrice, salePrice, url));
                }
                
                scan.close();
                
                /*System.out.println("The length of retailer array: " + list.size()+"\n\n");
                for(Product p : list){
                    System.out.print(p.toString());
                    System.out.print("\n\n");
                }*/
                
                lit = list.listIterator();
                int count = 0;
                productLabel.setText(productName);

                while (lit.hasNext()){
                    Product p = lit.next();
                    getProduct(p, count);
                    count++;
                }

                // Originally, loading the image was done in the West Region.
                // However, if a user chooses to open another product file, the product image must change.
                ImageIcon imageIcon = new ImageIcon("../product-files/"+ imageName);
                Image image = imageIcon.getImage();
                productIcon = new ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
                imgLabel.setIcon(productIcon);
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(frame,
                    "I/O error in file\n\n\n     " + productFile.getName()+
                    "\n\nThis program will close",
                    "I/O Error",
                    JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
            }
        }
    }
    
    private class saveListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String order = "\nProduct Order\n\n";
            order += "-------------------------\n";
            for (RetailerUI rui : uiElements)
            {
                if (rui.retailerBox.isSelected()) {
                    order += retailerInfo(productName,
                            rui.retailerBox.getText(),
                            rui.regPriceLabel.getText(),
                            rui.salePriceLabel.getText(),
                            rui.productQuantityBox.getText());
                }
            }
            order += "\n-------------------------\n\n";
            Calendar c = new GregorianCalendar();
            order += c.get(Calendar.YEAR) + "/" + c.get(Calendar.MONTH) +"/" +c.get(Calendar.DAY_OF_MONTH)+"     "+ c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);

            // Add in date as well
            try{
                PrintStream outFile = new PrintStream("../orders/ProductOrderReceipt.txt");
                outFile.print(order);
                outFile.close();
                
            }catch(IOException ioe){
                System.out.println("\n\n*-*-*-* I/O ERROR *-*-*-*\n\n" + ioe);
            }
        }
    }
    
    private class exitListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
        
    }
    
    private class aboutListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(frame,
                    "Product Display\n\n Version 1.0\nBuild B20180312\n\n" +
                    "(c) Copyright mforycki 2018\n All rights reserved\n\n" +
                    "Visit http://www.github.com/mforycki\n" +
                    "Github",
                    "About Product Display",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void makeContent(){

        contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout(6,6));
        contentPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        makeNorthRegion();
        makeWestRegion();
        makeCenterRegion();
        makeEastRegion();
    }
    
    private void makeNorthRegion(){
        
        productLabel = new JLabel("Product Name");
        productLabel.setFont(productLabel.getFont().deriveFont(32.0f));
        productLabel.setBorder(new EmptyBorder(10,10,10,10));
        contentPane.add(productLabel, BorderLayout.NORTH);
        
    }
    
    private void makeWestRegion(){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Product Image"));
        panel.setPreferredSize(new Dimension(310,320));

        imgLabel = new JLabel(productIcon, JLabel.CENTER);
        panel.add(imgLabel);
        contentPane.add(panel, BorderLayout.WEST);
    }
    
    private void makeCenterRegion(){
        
        JPanel mainPanel = new JPanel();
        //mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        //mainPanel.setBackground(Color.WHITE);
        //mainPanel.setLayout(new FlowLayout());
        mainPanel.setLayout(new GridLayout(1,4));
        mainPanel.setPreferredSize(new Dimension(745,0));

        // Panels
        JPanel retNamePanel = new JPanel();
        retNamePanel.setLayout(new BoxLayout(retNamePanel,BoxLayout.Y_AXIS));
        retNamePanel.setBorder(BorderFactory.createTitledBorder("Retailers"));
        //retNamePanel.setBackground(Color.CYAN);
        mainPanel.add(retNamePanel);

        JPanel regPricePanel = new JPanel();
        regPricePanel.setLayout(new BoxLayout(regPricePanel,BoxLayout.Y_AXIS));
        regPricePanel.setBorder(BorderFactory.createTitledBorder("Regular Price"));
        regPricePanel.setPreferredSize(new Dimension(125,0));
        mainPanel.add(regPricePanel);

        JPanel salePanel = new JPanel();
        salePanel.setLayout(new BoxLayout(salePanel,BoxLayout.Y_AXIS));
        salePanel.setBorder(BorderFactory.createTitledBorder("Sale Price"));
        //salePanel.setBackground(Color.GREEN);
        mainPanel.add(salePanel);

        JPanel percentOffPanel = new JPanel();
        percentOffPanel.setLayout(new BoxLayout(percentOffPanel,BoxLayout.Y_AXIS));
        percentOffPanel.setBorder(BorderFactory.createTitledBorder("% Off"));
        //percentOffPanel.setBackground(Color.RED);
        mainPanel.add(percentOffPanel);

        final int kNumRetailers = 5;
        uiElements = new ArrayList<>();
        for (int i = 1; i <= kNumRetailers; i++)
        {
            RetailerUI rui = new RetailerUI();
            rui.retailerBox = new JCheckBox("Retailer " + i, false);
            retNamePanel.add(rui.retailerBox);

            rui.regPriceLabel = new JLabel();
            rui.regPriceLabel.setBorder(new EmptyBorder(4,10,4,0));
            regPricePanel.add(rui.regPriceLabel);

            rui.salePriceLabel = new JLabel();
            rui.salePriceLabel.setBorder(new EmptyBorder(4,10,4,0));
            salePanel.add(rui.salePriceLabel);

            rui.percentOffLabel = new JLabel();
            rui.percentOffLabel.setBorder(new EmptyBorder(4,10,4,0));
            percentOffPanel.add(rui.percentOffLabel);

            uiElements.add(rui);
        }
        
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }
    
    private void makeEastRegion(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Enter Quantity: "));
        panel.setPreferredSize(new Dimension(125,0));


        for (RetailerUI rui : uiElements)
        {
            JPanel quantityPanel = new JPanel();
            quantityPanel.setLayout(new BoxLayout(quantityPanel,BoxLayout.X_AXIS));
            rui.productQuantityBox = new JTextField("",5);
            rui.productQuantityBox.setMaximumSize(new Dimension(26,24));
            rui.productQuantityBox.setHorizontalAlignment(JTextField.CENTER);
            quantityPanel.add(rui.productQuantityBox);
            panel.add(quantityPanel);
        }

        contentPane.add(panel, BorderLayout.EAST);
    }
    
    private void getProduct(Product p, int count){

        if (uiElements == null || count >= uiElements.size())
        {
            return;
        }

        RetailerUI rui = uiElements.get(count);
        rui.retailerBox.setText(p.getRetailer());
        rui.regPriceLabel.setText(Double.toString(p.getRegularPrice()));
        rui.salePriceLabel.setText(Double.toString(p.getSalePrice()));
        rui.percentOffLabel.setText(Integer.toString(p.percentOff())+"%");

    }
    
    private String retailerInfo(String product, String retailer, String price, String salePrice, String quantity){
        String info = "\nProduct: " + product + 
                "\nRetailer: "+ retailer +
                "\nRegular Price: " + price +
                "\nSale Price: " + salePrice +
                "\nQuantity: " + quantity + "\n";
        return info;
    }
}
