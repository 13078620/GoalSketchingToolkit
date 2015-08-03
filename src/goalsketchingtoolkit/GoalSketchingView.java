/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Cursor;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.FileDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JMenu;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;
import javax.swing.InputMap;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingView implements Observer {

    private JFrame frame;

    private JMenuBar bar;
    private JMenu menu;

    private FileDialog fileDialog;
    private JDialog dialog;

    private JScrollPane scrollPane;

    private GoalSketchingPanel panel;
    private GoalGraphModelInterface model;
    private GoalSketchingControllerInterface controller;

    private JPopupMenu rootPopUpMenu;
    private JPopupMenu goalPopUpMenu;
    private JPopupMenu entailmentPopUpMenu;
    private JPopupMenu productsPopUpMenu;
    private JPopupMenu assumpTerminationPopUpMenu;
    private JPopupMenu annotationPopUpMenu;

    //private boolean rootNode;
    private int rootStartX;
    private int rootStartY;
    private LoadGoalGraphListener listener;
    private SaveGoalGraphListener listener2;
    private NewGoalGraphListener listener3;

    private GSmouseListener mouseListener;
    private EditGoalListener editGoalListener;
    private EditGOPListener editGOPListener;

    private JTextField propText;
    private JTextField idText;
    private JLabel enterGoalIdLabel;
    private JLabel addPropositionLabel;
    private JLabel selectPrefixLabel;
    private JLabel operationalizerLabel;

    private JComboBox combobox;
    private String prefix = "";

    JMenuItem addRootMenuItem = new JMenuItem(new AbstractAction("Add root goal") {

        @Override
        public void actionPerformed(ActionEvent e) {
            int x = controller.getRootStartX();
            int y = controller.getRootStartY();
            controller.addRootGoal(x, y);
        }
    });

    JMenuItem addANDEntailmentMenuItem = new JMenuItem(new AbstractAction("Add AND entailment") {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.addAndEntailment();
        }
    });

    JMenuItem addOREntailmentMenuItem = new JMenuItem(new AbstractAction("Add OR entailment") {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.addOREntailment();
        }
    });

    JMenuItem addGoalMenuItem = new JMenuItem(new AbstractAction("Add goal") {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.addLeafGoal();
        }
    });

    JMenuItem editGoalIDMenuItem; /*= new JMenuItem(new AbstractAction("Edit goal ID") {
     @Override
     public void actionPerformed(ActionEvent e) {
     controller.setGoalID();            
     }
     });*/


    JMenuItem addProductsMenuItem = new JMenuItem(new AbstractAction("Add operationalizing products") {
        @Override
        public void actionPerformed(ActionEvent e) {

            /*GraphNode cs = (GraphNode) currentSelection;

             if (!cs.isParent() && !cs.isOperationalized2()) {

             //GraphNode child = new GraphNode(cs.getX() - (cs.getWidth() / 2 + 100), cs.getY() + cs.getHeight() + 160, 100, 60, new Proposition("/b/", "test", false), false, true, "Gtest");
             OperationalizerNode child = new OperationalizerNode();
             child.setWidth(80);
             child.setHeight(40);
             double x = cs.getX() + (cs.getWidth() - child.getWidth()) / 2;
             double y = cs.getY() + 100;
             child.setX(x);
             child.setY(y);

             cs.setOperationalizerNode(child);
             cs.setIsOperationalized(true);
             //addDrawable(new OperationalizerNodeDrawer(child));
             controller.addOpToGoalSketchingNodes(child);

             }*/
        }
    });

    JMenuItem addProductMenuItem = new JMenuItem(new AbstractAction("Add product") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem addAssumpTerminationMenuItem = new JMenuItem(new AbstractAction("Add assumption termination") {
        @Override
        public void actionPerformed(ActionEvent e) {

            /*GraphNode cs = (GraphNode) currentSelection;

             if (!cs.isParent() && !cs.isOperationalized2() /*&& cs.getProposition() != null && cs.getProposition().isAssumption()) {

             //GraphNode child = new GraphNode(cs.getX() - (cs.getWidth() / 2 + 100), cs.getY() + cs.getHeight() + 160, 100, 60, new Proposition("/b/", "test", false), false, true, "Gtest");
             double x = cs.getX() + (cs.getWidth() - 30) / 2;
             double y = cs.getY() + 100;
             AssumptionTerminationNode child = new AssumptionTerminationNode(x, y, 30, 30);

             cs.setAssumptionTerminationNode(child);
             cs.setIsOperationalized(true);
             //addDrawable(new OperationalizerNodeDrawer(child));
             controller.addTerminationGoalSketchingNodes(child);

             }*/
        }
    });

    JMenuItem addAnnotationMenuItem = new JMenuItem(new AbstractAction("Add annotation") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem addGOPMenuItem;/*new JMenuItem(new AbstractAction("Add goal oriented proposition") {
     @Override
     public void actionPerformed(ActionEvent e) {

     }
     });*/

    JMenuItem deleteEntailmentMenuItem = new JMenuItem(new AbstractAction("Delete entailment") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem deleteGoalMenuItem = new JMenuItem(new AbstractAction("Delete goal") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem deleteProductsMenuItem = new JMenuItem(new AbstractAction("Delete operationalizing products") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem removeProductMenuItem = new JMenuItem(new AbstractAction("Remove products") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem deleteAssumpTerminationMenuItem = new JMenuItem(new AbstractAction("Delete assumption termination") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem deleteAnnotationMenuItem = new JMenuItem(new AbstractAction("Delete annotation") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    JMenuItem deleteGOPMenuItem = new JMenuItem(new AbstractAction("Delete goal oriented proposition") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    class GSmouseListener extends MouseAdapter {

        private boolean mousePressed = false;
        private boolean dragging = false;
        final int PROX_DIST = 3;

        public boolean isMousePressed() {
            return this.mousePressed;
        }

        public void setMousePressed(boolean pressed) {
            mousePressed = pressed;
        }

        public boolean isDragging() {
            return dragging;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (panel.getCursor() != Cursor.getDefaultCursor()) {
                // If cursor is set for resizing, allow dragging.
                dragging = true;
            }
            if (e.getButton() == 1) {
                controller.setCurrentSelection(e);
            }
            if (e.getButton() == 3) {
                controller.setCurrentSelection(e);
                controller.configureContextualMenuItems(e);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            dragging = false;
            controller.configureMouseReleased(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            controller.configureMouseDragged(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            controller.configureMouseMoved(e);
        }

    }

    class LoadGoalGraphListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            fileDialog = new FileDialog(frame);
            fileDialog.setMode(FileDialog.LOAD);
            fileDialog.setVisible(true);

            String file = fileDialog.getDirectory() + fileDialog.getFile();
            if (file != null) {
                controller.reset();
                controller.loadGraph(file);
            }
        }
    }

    class SaveGoalGraphListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            fileDialog = new FileDialog(frame);
            fileDialog.setMode(FileDialog.SAVE);
            fileDialog.setVisible(true);

            /*GraphNode root = model.getRootGraphNode();
             String file = fileDialog.getDirectory() + fileDialog.getFile();
             //System.out.println(file);
             if (file != null) {
             try {
             controller.saveGraph(root, file);
             } catch (Exception ex) {
             ex.printStackTrace();
             }
             }*/
        }
    }

    class NewGoalGraphListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            controller.reset();
        }
    }

    class EditGOPListener implements ActionListener {
        
        

       @Override
        public void actionPerformed(ActionEvent e) {
            controller.editGOP();
        }

    }

    class EditGoalListener implements ActionListener {

        String id = "";
        String agentName = "";

        Operationalizer op;

        @Override
        public void actionPerformed(ActionEvent e) {

            controller.editGoalID();

            /*if (currentSelection.getClass().toString().contains("GraphNode")) {

             GraphNode cs = (GraphNode) currentSelection;

             dialog = new JDialog(frame);
             dialog.setTitle("Edit ID");
             JPanel p = new JPanel();
             enterGoalIdLabel = new JLabel("Enter goal ID: ");
             addPropositionLabel = new JLabel("Enter Proposition: ");
             selectPrefixLabel = new JLabel("Select Prefix: ");
             text = new JTextField(20);
             text2 = new JTextField(5);
             JButton button = new JButton("Ok");
             ActionListener listener = new EditGoalButtonListener();
             button.addActionListener(listener);

             //System.out.println(cs.isRootNode());
             if (cs.isRootNode()) {
             String[] prefixOptions = {"", "Motivation", "Behaviour"};
             combobox = new JComboBox(prefixOptions);
             } else if ((cs.getProposition() != null && cs.getProposition().getPrefix().contains("a")) || (cs.getParent().getProposition() != null && cs.getParent().getProposition().getPrefix().contains("a"))) {
             String[] prefixOptions = {"Assumption"};
             combobox = new JComboBox(prefixOptions);
             } else if (cs.isOperationalized2()) {
             String[] prefixOptions = {"", "Motivation", "Behaviour"};
             combobox = new JComboBox(prefixOptions);
             } else {
             String[] prefixOptions = {"", "Motivation", "Behaviour", "Assumption"};
             combobox = new JComboBox(prefixOptions);
             }

             ActionListener listener2 = new EditGoalComboBoxListener();
             combobox.addActionListener(listener2);

             if (cs.getProposition() != null && cs.getProposition().getStatement() != null) {
             text.setText(cs.getProposition().getStatement());
             }

             p.add(enterGoalIdLabel, BorderLayout.PAGE_START);
             p.add(text2, BorderLayout.PAGE_START);
             p.add(addPropositionLabel, BorderLayout.PAGE_START);
             p.add(text, BorderLayout.PAGE_START);
             p.add(selectPrefixLabel, BorderLayout.PAGE_START);
             p.add(combobox, BorderLayout.PAGE_END);
             p.add(button, BorderLayout.PAGE_END);

             dialog.add(p);
             dialog.pack();
             dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
             dialog.setVisible(true);

             } else if (currentSelection.getClass().toString().contains("OperationalizerNode")) {

             dialog = new JDialog(frame);
             dialog.setTitle("Edit Operationalizers");
             JPanel p = new JPanel();
             operationalizerLabel = new JLabel("Enter Operationalizer Name: ");
             //addSubDomainLabel = new JLabel("Add Sub Domain: ");
             text = new JTextField(20);
             //text2 = new JTextField(5);
             JButton button = new JButton("Ok");
             JButton button2 = new JButton("Add");
             ActionListener listener = new EditOperationalizerButtonListener();
             //ActionListener listener2 = new AddSubDomainButtonListener();
             button.addActionListener(listener);
             //button2.addActionListener(listener2);

             subDomains = new ArrayList<>();

             p.add(operationalizerLabel);
             p.add(text, BorderLayout.PAGE_START);
             //p.add(addSubDomainLabel, BorderLayout.PAGE_START);
             //p.add(text2, BorderLayout.PAGE_START);
             //p.add(button2, BorderLayout.PAGE_END);
             p.add(button, BorderLayout.PAGE_END);
             dialog.add(p);
             dialog.pack();
             dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
             dialog.setVisible(true);
             }*/
        }
    }

    class EditGoalButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String id = "";
            id += idText.getText();
            controller.setGoalID(id);

            dialog.setVisible(false);

        }
    }
    
    class EditGOPButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String statement = "";
            statement += propText.getText();
            controller.setGOP(prefix, statement);
            

            dialog.setVisible(false);

        }
    }

    class EditOperationalizerButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /*OperationalizerNode cs = (OperationalizerNode) currentSelection;
             agentName = "";
             agentName += text.getText();
             op = new Operationalizer();
             op.setAgentName(agentName);
             //op.setSubDomains(subDomains);
             cs.addOperationalizer(op);
             dialog.setVisible(false);
             model.notifyView();*/
        }
    }

    class AddSubDomainButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /*String subDomain = text2.getText();
             //subDomains.add(subDomain);
             text2.setText("");
             model.notifyView();*/
        }
    }

    class EditGoalComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == combobox) {
                JComboBox cb = (JComboBox) e.getSource();
                String selection = (String) cb.getSelectedItem();
                switch (selection) {
                    case "Motivation":
                        prefix = "";
                        prefix += GoalType.MOTIVATION;
                        break;
                    case "Behaviour":
                        prefix = "";
                        prefix += GoalType.BEHAVIOUR;
                        break;
                    case "Constraint":
                        prefix = "";
                        prefix += GoalType.CONSTRAINT;
                        break;
                    case "Assumption":
                        prefix = "";
                        prefix += GoalType.ASSUMPTION;
                        break;
                    case "Obstacle":
                        prefix = "";
                        prefix += GoalType.OBSTACLE;
                        break;
                    default:
                        prefix = "";
                        prefix = "\\ \\";
                }

            }
        }
    }

    JMenuItem menuItem5 = new JMenuItem(new AbstractAction("Save Goal Graph") {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    /**
     * Constructs a goal sketching view.
     *
     * @param model the observable which this view observes.
     */
    public GoalSketchingView(GoalGraphModelInterface model, GoalSketchingControllerInterface controller) {

        this.model = model;
        this.controller = controller;
        model.addObserver(this);

    }

    public void createGUI() {

        frame = new JFrame("Goal Sketching Toolkit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        panel = new GoalSketchingPanel(2000, 2000);
        panel.setPreferredSize(new Dimension(2000, 2000));

        mouseListener = new GSmouseListener();
        editGoalListener = new EditGoalListener();
        editGOPListener = new EditGOPListener();

        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseListener);
        frame.setResizable(true);

        Container pane = frame.getContentPane();
        bar = new JMenuBar();
        pane.add(bar, BorderLayout.NORTH);

        scrollPane = new JScrollPane(panel);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
        InputMap im = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        InputMap im2 = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
        im.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
        im2.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
        im2.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");
        pane.add(scrollPane, BorderLayout.CENTER);

    }

    public void createContextualMenuControls() {

        rootPopUpMenu = new JPopupMenu();
        rootPopUpMenu.add(addRootMenuItem);
        rootPopUpMenu.setLightWeightPopupEnabled(false);

        goalPopUpMenu = new JPopupMenu();
        goalPopUpMenu.add(addANDEntailmentMenuItem);
        goalPopUpMenu.add(addOREntailmentMenuItem);
        goalPopUpMenu.add(addProductsMenuItem);
        goalPopUpMenu.add(addAssumpTerminationMenuItem);
        goalPopUpMenu.add(addAnnotationMenuItem);

        editGoalIDMenuItem = new JMenuItem("Edit goal ID");
        editGoalIDMenuItem.addActionListener(editGoalListener);
        goalPopUpMenu.add(editGoalIDMenuItem);

        addGOPMenuItem = new JMenuItem("Edit GOP");
        addGOPMenuItem.addActionListener(editGOPListener);
        goalPopUpMenu.add(addGOPMenuItem);

        goalPopUpMenu.setLightWeightPopupEnabled(false);

        entailmentPopUpMenu = new JPopupMenu();
        entailmentPopUpMenu.add(addGoalMenuItem);
        entailmentPopUpMenu.add(deleteEntailmentMenuItem);
        entailmentPopUpMenu.setLightWeightPopupEnabled(false);

        productsPopUpMenu = new JPopupMenu();
        productsPopUpMenu.add(addProductMenuItem);
        productsPopUpMenu.add(deleteProductsMenuItem);
        productsPopUpMenu.add(removeProductMenuItem);
        productsPopUpMenu.setLightWeightPopupEnabled(false);

        assumpTerminationPopUpMenu = new JPopupMenu();
        assumpTerminationPopUpMenu.add(deleteAssumpTerminationMenuItem);
        assumpTerminationPopUpMenu.setLightWeightPopupEnabled(false);

        annotationPopUpMenu = new JPopupMenu();
        annotationPopUpMenu.add(deleteAnnotationMenuItem);
        annotationPopUpMenu.setLightWeightPopupEnabled(false);

    }

    public void createFileControls() {

        //fileDialog = new FileDialog(frame);
        //fileDialog.setMode(FileDialog.LOAD);
        menu = new JMenu("File");
        listener = new LoadGoalGraphListener();
        JMenuItem menuItem4 = new JMenuItem("Load Goal Graph");
        listener2 = new SaveGoalGraphListener();
        JMenuItem menuItem5 = new JMenuItem("Save Goal Graph");
        listener3 = new NewGoalGraphListener();
        JMenuItem menuItem6 = new JMenuItem("New Goal Graph");
        menuItem4.addActionListener(listener);
        menuItem5.addActionListener(listener2);
        menuItem6.addActionListener(listener3);
        menu.add(menuItem4);
        menu.add(menuItem5);
        menu.add(menuItem6);
        menu.getAccessibleContext();
        bar.add(menu);
        frame.setVisible(true);
    }

    public void createComboBox() {

        String[] prefixOptions = {"hello", "Motivation", "Behaviour", "Assumption"};
        combobox = new JComboBox(prefixOptions);
    }

    /**
     * The method which is called when the goal sketching model has changed.
     *
     * @param o the goal sketching model observable.
     * @param arg the argument passed from the goal sketching model observable.
     */
    @Override
    public void update(Observable o, Object arg) {

        panel.repaint();

    }

    /**
     * Appends a an object of the drawable type to the list of this views goal
     * sketching panel.
     *
     * @param d the drawable to add.
     */
    public void addDrawable(Drawable d) {

        panel.addDrawable(d);

    }

    public void reset() {
        panel.reset();
    }

    public void displayErrorMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public ArrayList<Drawable> getDrawables() {
        return panel.getDrawables();
    }

    public void showRootPopUpMenu(MouseEvent e, int eventX, int eventY) {
        rootPopUpMenu.show(e.getComponent(), eventX, eventY);
    }

    public void showGoalPopUpMenu(MouseEvent e, int eventX, int eventY) {
        goalPopUpMenu.show(e.getComponent(), eventX, eventY);
    }

    public void showEntailmentPopUpMenu(MouseEvent e, int eventX, int eventY) {
        entailmentPopUpMenu.show(e.getComponent(), eventX, eventY);
    }

    public void showProductsPopUpMenu(MouseEvent e, int eventX, int eventY) {
        productsPopUpMenu.show(e.getComponent(), eventX, eventY);
    }

    public void showAssumpTerminationPopUpMenu(MouseEvent e, int eventX, int eventY) {
        assumpTerminationPopUpMenu.show(e.getComponent(), eventX, eventY);
    }

    public void showAnnotationPopUpMenu(MouseEvent e, int eventX, int eventY) {
        annotationPopUpMenu.show(e.getComponent(), eventX, eventY);
    }

    public void hidePopUpMenu() {
        panel.setComponentPopupMenu(null);
    }

    public void enableAddANDEntailmentMenuItem() {
        addANDEntailmentMenuItem.setEnabled(true);
    }

    public void disableAddANDEntailmentMenuItem() {
        addANDEntailmentMenuItem.setEnabled(false);
    }

    public void enableAddOREntailmentMenuItem() {
        addOREntailmentMenuItem.setEnabled(true);
    }

    public void disableAddOREntailmentMenuItem() {
        addOREntailmentMenuItem.setEnabled(false);
    }

    public void enableAddGoalMenuItem() {
        addGoalMenuItem.setEnabled(true);
    }

    public void disableAddGoalMenuItem() {
        addGoalMenuItem.setEnabled(false);
    }

    public void enableAddProductsMenuItem() {
        addProductsMenuItem.setEnabled(true);
    }

    public void disableAddProductsMenuItem() {
        addProductsMenuItem.setEnabled(false);
    }

    public void enableAddProductMenuItem() {
        addProductMenuItem.setEnabled(true);
    }

    public void disableAddProductMenuItem() {
        addProductMenuItem.setEnabled(false);
    }

    public void enableAddAssumpTerminationMenuItem() {
        addAssumpTerminationMenuItem.setEnabled(true);
    }

    public void disableAddAssumpTerminationMenuItem() {
        addAssumpTerminationMenuItem.setEnabled(false);
    }

    public void enableAddAnnotationMenuItem() {
        addAnnotationMenuItem.setEnabled(true);
    }

    public void disableAddAnnotationMenuItem() {
        addAnnotationMenuItem.setEnabled(false);
    }

    public void enableAddGOPMenuItem() {
        addGOPMenuItem.setEnabled(true);
    }

    public void disableAddGOPMenuItem() {
        addGOPMenuItem.setEnabled(false);
    }

    public void enableDeleteEntailmentMenuItem() {
        deleteEntailmentMenuItem.setEnabled(true);
    }

    public void disableDeleteEntailmentMenuItem() {
        deleteEntailmentMenuItem.setEnabled(false);
    }

    public void enableDeleteGoalMenuItem() {
        deleteGoalMenuItem.setEnabled(true);
    }

    public void disableDeleteGoalMenuItem() {
        deleteGoalMenuItem.setEnabled(false);
    }

    public void enableDeleteProductsMenuItem() {
        deleteProductsMenuItem.setEnabled(true);
    }

    public void disableDeleteProductsMenuItem() {
        deleteProductsMenuItem.setEnabled(false);
    }

    public void enableRemoveProductMenuItem() {
        removeProductMenuItem.setEnabled(true);
    }

    public void disableRemoveProductMenuItem() {
        removeProductMenuItem.setEnabled(false);
    }

    public void enableDeleteAssumpTerminationMenuItem() {
        deleteAssumpTerminationMenuItem.setEnabled(true);
    }

    public void disableDeleteAssumpTerminationMenuItem() {
        deleteAssumpTerminationMenuItem.setEnabled(false);
    }

    public void enableDeleteAnnotationMenuItem() {
        deleteAnnotationMenuItem.setEnabled(true);
    }

    public void disableDeleteAnnotationMenuItem() {
        deleteAnnotationMenuItem.setEnabled(false);
    }

    public void enableDeleteGOPMenuItem() {
        deleteGOPMenuItem.setEnabled(true);
    }

    public void disableDeleteGOPMenuItem() {
        deleteGOPMenuItem.setEnabled(false);
    }

    public GSmouseListener getMouseListener() {
        return this.mouseListener;
    }

    public EditGoalListener getEditGoalListener() {
        return this.editGoalListener;
    }

    public GoalSketchingPanel getPanel() {
        return this.panel;
    }

    public JDialog getEditGoalIDdialog() {

        dialog = new JDialog(frame);
        dialog.setTitle("Edit Goal ID");
        JPanel p = new JPanel();
        enterGoalIdLabel = new JLabel("Enter goal ID: ");
        idText = new JTextField(5);
        JButton button = new JButton("Ok");
        ActionListener editGoalButtonListener = new EditGoalButtonListener();
        button.addActionListener(editGoalButtonListener);
        p.add(enterGoalIdLabel, BorderLayout.PAGE_START);
        p.add(idText, BorderLayout.PAGE_START);
        p.add(button, BorderLayout.PAGE_END);

        dialog.add(p);
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        return dialog;
    }

    public JDialog getEditGoalGOPdialog() {

        dialog = new JDialog(frame);
        dialog.setTitle("Edit Goal Goal Oriented Proposition");
        JPanel p = new JPanel();
        addPropositionLabel = new JLabel("Enter statement: ");
        propText = new JTextField(20);
        JButton button = new JButton("Ok");
        ActionListener editGOPButtonListener = new EditGOPButtonListener();
        button.addActionListener(editGOPButtonListener);
        p.add(addPropositionLabel, BorderLayout.PAGE_START);
        p.add(propText, BorderLayout.PAGE_START);
        

        String[] prefixOptions = {"", "Motivation", "Behaviour", "Constraint", "Assumption", "Obstacle"};
        combobox = new JComboBox(prefixOptions);
        p.add(combobox);
        
        p.add(button, BorderLayout.PAGE_END);

        dialog.add(p);
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        return dialog;
    }

}
