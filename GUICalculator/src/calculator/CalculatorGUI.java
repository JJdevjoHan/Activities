/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator;

/**
 *
 * @author JOHAN
 */
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;


public class CalculatorGUI extends JFrame{

    private static final Logger logger = Logger.getLogger(CalculatorGUI.class.getName());

    private JTextField display;

    private JButton btnClear;
    private JButton btnDivide;
    private JButton btnMultiply;
    private JButton btnSub;
    private JButton btnPlus;
    private JButton btnEquals;
    private JButton btnPeriodt;

    private JButton btnSeven;
    private JButton btnEight;
    private JButton btnNine;
    private JButton btnFour;
    private JButton btnFive;
    private JButton btnSix;
    private JButton btnOne;
    private JButton btnTwo;
    private JButton btnThree;
    private JButton btnZero;

    private double firstOperand = 0;
    private CalculatorInterface currentOperation = null;
    private boolean newInput = true;

    public CalculatorGUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.PLAIN, 28));
        display.setPreferredSize(new Dimension(334, 103));

        btnClear    = makeButton("Clear");
        btnDivide   = makeButton("/");
        btnMultiply = makeButton("*");
        btnSub      = makeButton("-");
        btnPlus     = makeButton("+");
        btnEquals   = makeButton("=");
        btnPeriodt  = makeButton(".");

        btnSeven = makeButton("7");
        btnEight = makeButton("8");
        btnNine  = makeButton("9");
        btnFour  = makeButton("4");
        btnFive  = makeButton("5");
        btnSix   = makeButton("6");
        btnOne   = makeButton("1");
        btnTwo   = makeButton("2");
        btnThree = makeButton("3");
        btnZero  = makeButton("0");

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 3, 3, 3);
        c.weightx = 1;
        c.weighty = 1;
        

        addBtn(buttonPanel, btnClear,    c, 0, 0, 1, 1);
        addBtn(buttonPanel, btnDivide,   c, 1, 0, 1, 1);
        addBtn(buttonPanel, btnMultiply, c, 2, 0, 1, 1);
        addBtn(buttonPanel, btnSub,      c, 3, 0, 1, 1);

        addBtn(buttonPanel, btnSeven, c, 0, 1, 1, 1);
        addBtn(buttonPanel, btnEight, c, 1, 1, 1, 1);
        addBtn(buttonPanel, btnNine,  c, 2, 1, 1, 1);
        addBtn(buttonPanel, btnPlus,  c, 3, 1, 1, 2);

        addBtn(buttonPanel, btnFour, c, 0, 2, 1, 1);
        addBtn(buttonPanel, btnFive, c, 1, 2, 1, 1);
        addBtn(buttonPanel, btnSix,  c, 2, 2, 1, 1);

        addBtn(buttonPanel, btnOne,    c, 0, 3, 1, 1);
        addBtn(buttonPanel, btnTwo,    c, 1, 3, 1, 1);
        addBtn(buttonPanel, btnThree,  c, 2, 3, 1, 1);
        addBtn(buttonPanel, btnEquals, c, 3, 3, 1, 2);

        addBtn(buttonPanel, btnZero,    c, 0, 4, 2, 1);
        addBtn(buttonPanel, btnPeriodt, c, 2, 4, 1, 1);

        JPanel mainPanel = new JPanel(new BorderLayout(6, 6));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        mainPanel.add(display, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        wireListeners();
        pack();
        setLocationRelativeTo(null);
    }

    private JButton makeButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btn.setPreferredSize(new Dimension(79, 54));
        return btn;
    }

    private void addBtn(JPanel panel, JButton btn, GridBagConstraints c, int x, int y, int width, int height) 
    {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        panel.add(btn, c);
        c.gridwidth = 1;  
        c.gridheight = 1; 
    }

    private void wireListeners() {
        
        btnZero.addActionListener(e  -> appendDigit("0"));
        btnOne.addActionListener(e   -> appendDigit("1"));
        btnTwo.addActionListener(e   -> appendDigit("2"));
        btnThree.addActionListener(e -> appendDigit("3"));
        btnFour.addActionListener(e  -> appendDigit("4"));
        btnFive.addActionListener(e  -> appendDigit("5"));
        btnSix.addActionListener(e   -> appendDigit("6"));
        btnSeven.addActionListener(e -> appendDigit("7"));
        btnEight.addActionListener(e -> appendDigit("8"));
        btnNine.addActionListener(e  -> appendDigit("9"));

        btnPeriodt.addActionListener(e -> appendDecimal());

        btnPlus.addActionListener(e     -> handleOperator((CalculatorInterface) new Addition()));
        btnSub.addActionListener(e      -> handleOperator((CalculatorInterface) new Subtraction()));
        btnMultiply.addActionListener(e -> handleOperator((CalculatorInterface) new Multiplication()));
        btnDivide.addActionListener(e   -> handleOperator((CalculatorInterface) new Division()));

        btnEquals.addActionListener(e -> handleEquals());
        btnClear.addActionListener(e  -> handleClear());
    }


    private void appendDigit(String digit) {
        if (newInput) {
            display.setText(digit);
            newInput = false;
        } else {
            String current = display.getText();
            display.setText("0".equals(current) ? digit : current + digit);
        }
    }

    private void appendDecimal() {
        if (newInput) {
            display.setText("0.");
            newInput = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void handleOperator(CalculatorInterface operation) {
        firstOperand = Double.parseDouble(display.getText());
        currentOperation = operation;
        newInput = true;
    }

    private void handleEquals() {
        if (currentOperation == null) return;
        try {
            double secondOperand = Double.parseDouble(display.getText());
            double result = currentOperation.calculate(firstOperand, secondOperand);

            // Show as whole number if there is no fractional part
            if (result == (long) result) {
                display.setText(String.valueOf((long) result));
            } else {
                display.setText(String.valueOf(result));
            }
        } catch (ArithmeticException ex) {
            display.setText("Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }

        currentOperation = null;
        newInput = true;
    }

    private void handleClear() {
        display.setText("0");
        firstOperand = 0;
        currentOperation = null;
        newInput = true;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            logger.severe(ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> new CalculatorGUI().setVisible(true));
    }
}
