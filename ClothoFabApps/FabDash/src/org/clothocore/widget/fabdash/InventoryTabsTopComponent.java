/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.widget.fabdash;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.DataListener;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ObjBasePopup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.clothocore.widget.fabdash//LogIn//EN",
autostore = false)
@TopComponent.Description(preferredID = "LogInTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = true)
@ActionID(category = "Window", id = "org.clothocore.widget.fabdash.LogInTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_LogInAction",
preferredID = "LogInTopComponent")
public final class InventoryTabsTopComponent extends TopComponent {

    public InventoryTabsTopComponent() {
        initComponents();
        _connected = false;

        partsTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (partsTable.getSelectedRow() > -1 && partsTable.getSelectedRow() < partsTable.getHeight()) {

                    if (e.getButton() == MouseEvent.BUTTON1) {
                        _obp = new ObjBasePopup(partsTable, Part.retrieveByName((String) partsTable.getValueAt(partsTable.getSelectedRow(), 0)));
                    }
                }
            }
        });
        partsTable.setTransferHandler(new InventoryTransferHandler());
        partsTable.setDragEnabled(true);

        vectorsTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (vectorsTable.getSelectedRow() > -1 && vectorsTable.getSelectedRow() < vectorsTable.getHeight()) {
                        _obp = new ObjBasePopup(vectorsTable, Vector.retrieveByName((String) vectorsTable.getValueAt(vectorsTable.getSelectedRow(), 0)));
                    }
                }
            }
        });
        vectorsTable.setTransferHandler(new InventoryTransferHandler());
        vectorsTable.setDragEnabled(true);

        plasmidsTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (plasmidsTable.getSelectedRow() > -1 && plasmidsTable.getSelectedRow() < plasmidsTable.getHeight()) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        _obp = new ObjBasePopup(plasmidsTable, Plasmid.retrieveByName((String) plasmidsTable.getValueAt(plasmidsTable.getSelectedRow(), 0)));
                    }
                }
            }
        });
        plasmidsTable.setTransferHandler(new InventoryTransferHandler());
        plasmidsTable.setDragEnabled(true);

        oligosTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (oligosTable.getSelectedRow() > -1 && oligosTable.getSelectedRow() < oligosTable.getHeight()) {
                        _obp = new ObjBasePopup(oligosTable, Oligo.retrieveByName((String) oligosTable.getValueAt(oligosTable.getSelectedRow(), 0)));
                    }
                }

            }
        });
        oligosTable.setTransferHandler(new InventoryTransferHandler());
        oligosTable.setDragEnabled(true);



        setName(NbBundle.getMessage(InventoryTabsTopComponent.class, "CTL_LogInTopComponent"));
        setToolTipText(NbBundle.getMessage(InventoryTabsTopComponent.class, "HINT_LogInTopComponent"));

        this.getInputMap().put(KeyStroke.getKeyStroke("F5"), "refresh");
        this.getActionMap().put("refresh",
                new AbstractAction("refresh") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        fetchInventoryInformation();

                    }
                });

        this.getInputMap().put(KeyStroke.getKeyStroke("r"), "r");
        this.getActionMap().put("r",
                new AbstractAction("r") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        fetchInventoryInformation();

                    }
                });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inventoryPanel = new javax.swing.JPanel();
        inventoryTabbedPane = new javax.swing.JTabbedPane();
        partsScrollPane = new javax.swing.JScrollPane();
        partsTable = new javax.swing.JTable();
        vectorsScrollPane = new javax.swing.JScrollPane();
        vectorsTable = new javax.swing.JTable();
        plasmidsScrollPane = new javax.swing.JScrollPane();
        plasmidsTable = new javax.swing.JTable();
        oligosScrollPane = new javax.swing.JScrollPane();
        oligosTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();
        restrictToUserCheckBox = new javax.swing.JCheckBox();
        collectionsComboBox = new javax.swing.JComboBox();

        setDisplayName(org.openide.util.NbBundle.getMessage(InventoryTabsTopComponent.class, "InventoryTabsTopComponent.displayName")); // NOI18N

        inventoryTabbedPane.setMaximumSize(new java.awt.Dimension(900, 900));
        inventoryTabbedPane.setMinimumSize(new java.awt.Dimension(150, 100));
        inventoryTabbedPane.setName("inventoryTabs"); // NOI18N
        inventoryTabbedPane.setPreferredSize(new java.awt.Dimension(300, 300));

        partsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Identifier", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        partsTable.setDragEnabled(true);
        partsTable.setEnabled(false);
        partsTable.setFillsViewportHeight(true);
        partsTable.setName("partsTable"); // NOI18N
        partsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        partsScrollPane.setViewportView(partsTable);

        inventoryTabbedPane.addTab(org.openide.util.NbBundle.getMessage(InventoryTabsTopComponent.class, "InventoryTabsTopComponent.partsScrollPane.TabConstraints.tabTitle"), partsScrollPane); // NOI18N

        vectorsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Identifier", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        vectorsTable.setDragEnabled(true);
        vectorsTable.setEnabled(false);
        vectorsTable.setFillsViewportHeight(true);
        vectorsTable.setName("vectorsTable"); // NOI18N
        vectorsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        vectorsScrollPane.setViewportView(vectorsTable);

        inventoryTabbedPane.addTab(org.openide.util.NbBundle.getMessage(InventoryTabsTopComponent.class, "InventoryTabsTopComponent.vectorsScrollPane.TabConstraints.tabTitle"), vectorsScrollPane); // NOI18N

        plasmidsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Identifier", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        plasmidsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        plasmidsTable.setDragEnabled(true);
        plasmidsTable.setEnabled(false);
        plasmidsTable.setFillsViewportHeight(true);
        plasmidsTable.setName("plasmidsTable"); // NOI18N
        plasmidsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        plasmidsScrollPane.setViewportView(plasmidsTable);

        inventoryTabbedPane.addTab(org.openide.util.NbBundle.getMessage(InventoryTabsTopComponent.class, "InventoryTabsTopComponent.plasmidsScrollPane.TabConstraints.tabTitle"), plasmidsScrollPane); // NOI18N

        oligosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Identifier", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        oligosTable.setDragEnabled(true);
        oligosTable.setEnabled(false);
        oligosTable.setFillsViewportHeight(true);
        oligosTable.setName("oligosTable"); // NOI18N
        oligosTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        oligosScrollPane.setViewportView(oligosTable);

        inventoryTabbedPane.addTab(org.openide.util.NbBundle.getMessage(InventoryTabsTopComponent.class, "InventoryTabsTopComponent.oligosScrollPane.TabConstraints.tabTitle"), oligosScrollPane); // NOI18N

        refreshButton.setFont(new java.awt.Font("Ubuntu", 0, 10));
        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(InventoryTabsTopComponent.class, "InventoryTabsTopComponent.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        restrictToUserCheckBox.setFont(new java.awt.Font("Ubuntu", 0, 10));
        restrictToUserCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(restrictToUserCheckBox, org.openide.util.NbBundle.getMessage(InventoryTabsTopComponent.class, "InventoryTabsTopComponent.restrictToUserCheckBox.text")); // NOI18N

        collectionsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        collectionsComboBox.setSelectedIndex(-1);

        javax.swing.GroupLayout inventoryPanelLayout = new javax.swing.GroupLayout(inventoryPanel);
        inventoryPanel.setLayout(inventoryPanelLayout);
        inventoryPanelLayout.setHorizontalGroup(
            inventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inventoryTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inventoryPanelLayout.createSequentialGroup()
                        .addComponent(restrictToUserCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(collectionsComboBox, 0, 156, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)))
                .addContainerGap())
        );
        inventoryPanelLayout.setVerticalGroup(
            inventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshButton)
                    .addComponent(restrictToUserCheckBox)
                    .addComponent(collectionsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inventoryTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inventoryPanel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inventoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void componentShowing() {
        if (!_connected) {
            fetchInventoryInformation();
            _connected = true;


        }
        super.componentShowing();
    }

    private static ArrayList<ObjBase> getAllInCollection(Collection coll, ObjType type) {
        ArrayList<ObjBase> toReturn = (ArrayList<ObjBase>) coll.getAll(type);
        ArrayList<Collection> collections = (ArrayList<Collection>) coll.getAll(ObjType.COLLECTION);
        for (Collection co : collections) {
            toReturn.addAll(getAllInCollection(co, type));
        }
        return toReturn;
    }

private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    fetchInventoryInformation();
}//GEN-LAST:event_refreshButtonActionPerformed

    private void fetchInventoryInformation() {
        new SwingWorker() {

            Collection viewedCollection = null;

            @Override
            protected Object doInBackground() throws Exception {
                if (!Collector.isConnected()) {
                    StatusDisplayer.getDefault().setStatusText("Need to connect before displaying Inventory");

                    return null;
                }

                try {
                    String currentSelection = null;
                    if (collectionsComboBox.getSelectedIndex() > -1) {
                        currentSelection = (String) collectionsComboBox.getSelectedItem();
                    }
                    collectionsComboBox.removeAllItems();
                    ArrayList<ObjLink> allLinksOfCollections = Collector.getAllLinksOf(ObjType.COLLECTION);
                    for (ObjLink oj : allLinksOfCollections) {
                        collectionsComboBox.addItem(oj.name);
                    }
                    if (currentSelection != null) {
                        collectionsComboBox.setSelectedItem(currentSelection);
                    }
                    if (collectionsComboBox.getSelectedIndex() < 0) {
                        collectionsComboBox.setSelectedItem(Collector.getCurrentUser().getHerCollection().getName());
                    }
                    if (restrictToUserCheckBox.isSelected()) {
                        viewedCollection = Collection.retrieveByName((String) collectionsComboBox.getSelectedItem());
                    }


                    //Populate the Plasmid tab is below
                    ArrayList<ObjBase> allPlasmids = null;
                    if (restrictToUserCheckBox.isSelected()) {
                        allPlasmids = InventoryTabsTopComponent.getAllInCollection(viewedCollection, ObjType.PLASMID);
                    } else {
                        allPlasmids = Collector.getAll(ObjType.PLASMID);
                    }
                    Object[][] plasmidTableModel = new Object[allPlasmids.size()][3];
                    for (int i = 0; i < allPlasmids.size(); i++) {
                        plasmidTableModel[i][0] = allPlasmids.get(i).getName();
                        Plasmid aplas = (Plasmid) allPlasmids.get(i);
                        if (aplas.isChanged()) {
                            plasmidTableModel[i][2] = "changed";
                        } else {
                            plasmidTableModel[i][2] = "saved";
                        }
                        aplas.addDataListener(new DataListener() {

                            @Override
                            public void objectChanged(RefreshEvent evt) {
                                StatusDisplayer.getDefault().setStatusText(((ObjBase) evt.getSource()).getName() + " was changed");
                                TableModel model = plasmidsTable.getModel();
                                for (int i = 0; i < model.getRowCount(); i++) {
                                    if (model.getValueAt(i, 0).equals(((ObjBase) evt.getSource()).getName())) {
                                        model.setValueAt("changed", i, 2);
                                        break;
                                    }
                                }
                            }
                        });
                        Format aform = aplas.getFormat(); //get the Format of aplas
                        plasmidTableModel[i][1] = aform.getName(); //based on the Format, the sequence of the region of interest is retreieved and used to populate the table
                    }
                    plasmidsTable.setModel(new javax.swing.table.DefaultTableModel(plasmidTableModel, new String[]{"Plasmid Name", "Format", "Status"}) {

                        @Override
                        public boolean isCellEditable(int rowIndex, int mColIndex) {
                            return false;
                        }
                    });
                    plasmidsTable.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if (e.getClickCount() == 2) {
                                try {
                                    Plasmid aplas = Plasmid.retrieveByName((String) plasmidsTable.getValueAt(plasmidsTable.getSelectedRow(), 0));
                                    aplas.launchDefaultViewer();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    });
                    //populate the Oligo tab
                    ArrayList<ObjBase> allOligos = null;
                    if (restrictToUserCheckBox.isSelected()) {
                        allOligos = InventoryTabsTopComponent.getAllInCollection(viewedCollection, ObjType.OLIGO);
                    } else {
                        allOligos = Collector.getAll(ObjType.OLIGO);
                    }
                    Object[][] oligoTableModel = new Object[allOligos.size()][3];
                    for (int i = 0; i < allOligos.size(); i++) {
                        oligoTableModel[i][2] = "saved";
                        Oligo oligo = (Oligo) allOligos.get(i);
                        if (oligo.isChanged()) {
                            oligoTableModel[i][2] = "changed";
                        } else {
                            oligoTableModel[i][2] = "saved";
                        }
                        oligo.addDataListener(new DataListener() {

                            @Override
                            public void objectChanged(RefreshEvent evt) {
                                StatusDisplayer.getDefault().setStatusText(((ObjBase) evt.getSource()).getName() + " was changed");
                                TableModel model = oligosTable.getModel();
                                for (int i = 0; i < model.getRowCount(); i++) {
                                    if (model.getValueAt(i, 0).equals(((ObjBase) evt.getSource()).getName())) {
                                        model.setValueAt("changed", i, 2);
                                        break;
                                    }
                                }
                            }
                        });
                        oligoTableModel[i][0] = oligo.getName();
                        oligoTableModel[i][1] = oligo.getDescription();
                    }
                    oligosTable.setModel(new javax.swing.table.DefaultTableModel(oligoTableModel, new String[]{"Name", "Description", "Status"}) {

                        @Override
                        public boolean isCellEditable(int rowIndex, int mColIndex) {
                            return false;
                        }
                    });
                    oligosTable.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if (e.getClickCount() == 2) {
                                try {
                                    Oligo aol = Oligo.retrieveByName((String) oligosTable.getValueAt(oligosTable.getSelectedRow(), 0));
                                    aol.launchDefaultViewer();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    });
                    //populate the Vectors tab is below
                    ArrayList<ObjBase> allVectors = null;
                    if (restrictToUserCheckBox.isSelected()) {
                        allVectors = InventoryTabsTopComponent.getAllInCollection(viewedCollection, ObjType.VECTOR);
                    } else {
                        allVectors = Collector.getAll(ObjType.VECTOR);
                    }
                    Object[][] vectorTableModel = new Object[allVectors.size()][3];
                    for (int i = 0; i < allVectors.size(); i++) {
                        vectorTableModel[i][2] = "saved";
                        vectorTableModel[i][0] = allVectors.get(i).getName();
                        Vector avec = (Vector) allVectors.get(i);
                        if (avec.isChanged()) {
                            vectorTableModel[i][2] = "changed";
                        } else {
                            vectorTableModel[i][2] = "saved";
                        }
                        avec.addDataListener(new DataListener() {

                            @Override
                            public void objectChanged(RefreshEvent evt) {
                                StatusDisplayer.getDefault().setStatusText(((ObjBase) evt.getSource()).getName() + " was changed");
                                TableModel model = vectorsTable.getModel();
                                for (int i = 0; i < model.getRowCount(); i++) {
                                    if (model.getValueAt(i, 0).equals(((ObjBase) evt.getSource()).getName())) {
                                        model.setValueAt("changed", i, 2);
                                        break;
                                    }
                                }
                            }
                        });
                        vectorTableModel[i][1] = avec.getFormat().toString(); //based on the Format, the sequence of the region of interest is retreieved and used to populate the table
                    }
                    vectorsTable.setModel(new javax.swing.table.DefaultTableModel(vectorTableModel, new String[]{"Vector Name", "Format", "Status"}) {

                        @Override
                        public boolean isCellEditable(int rowIndex, int mColIndex) {
                            return false;
                        }
                    });
                    vectorsTable.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if (e.getClickCount() == 2) {
                                try {
                                    Vector avect = Vector.retrieveByName((String) vectorsTable.getValueAt(vectorsTable.getSelectedRow(), 0));
                                    avect.launchDefaultViewer();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    });

                    //Code for populating the Parts tab is below
                    ArrayList<ObjBase> allParts = null;
                    if (restrictToUserCheckBox.isSelected()) {
                        allParts = InventoryTabsTopComponent.getAllInCollection(viewedCollection, ObjType.PART);
                    } else {
                        allParts = Collector.getAll(ObjType.PART);
                    }
                    Object[][] partTableModel = new Object[allParts.size()][3];
                    for (int i = 0; i < allParts.size(); i++) {
                        partTableModel[i][2] = "saved";
                        Part aPart = (Part) allParts.get(i);
                        if (aPart.isChanged()) {
                            partTableModel[i][2] = "changed";
                        } else {
                            partTableModel[i][2] = "saved";
                        }
                        aPart.addDataListener(new DataListener() {

                            @Override
                            public void objectChanged(RefreshEvent evt) {
                                StatusDisplayer.getDefault().setStatusText(((ObjBase) evt.getSource()).getName() + " was changed");
                                TableModel model = partsTable.getModel();
                                for (int i = 0; i < model.getRowCount(); i++) {
                                    if (model.getValueAt(i, 0).equals(((ObjBase) evt.getSource()).getName())) {
                                        model.setValueAt("changed", i, 2);
                                        break;
                                    }
                                }
                            }
                        });
                        partTableModel[i][0] = aPart.getName();
                        partTableModel[i][1] = aPart.getFormat().toString();
                    }
                    partsTable.setModel(new javax.swing.table.DefaultTableModel(partTableModel, new String[]{"Part Name", "Format", "Status"}) {

                        @Override
                        public boolean isCellEditable(int rowIndex, int mColIndex) {
                            return false;
                        }
                    });
                    partsTable.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if (e.getClickCount() == 2) {
                                try {
                                    Part apart = Part.retrieveByName((String) partsTable.getValueAt(partsTable.getSelectedRow(), 0));
                                    apart.launchDefaultViewer();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    });

                    partsTable.setDefaultRenderer(Object.class, new InventoryTableCellRenderer());
                    oligosTable.setDefaultRenderer(Object.class, new InventoryTableCellRenderer());
                    plasmidsTable.setDefaultRenderer(Object.class, new InventoryTableCellRenderer());
                    vectorsTable.setDefaultRenderer(Object.class, new InventoryTableCellRenderer());
                    oligosTable.setEnabled(true);
                    partsTable.setEnabled(true);
                    plasmidsTable.setEnabled(true);
                    vectorsTable.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void done() {
//                browseTree.setModel( CollectionBrowser.generate(personalCollection));
//                browseTree.validate();
                repaint();
            }
        }.execute();


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox collectionsComboBox;
    private javax.swing.JPanel inventoryPanel;
    protected javax.swing.JTabbedPane inventoryTabbedPane;
    protected javax.swing.JScrollPane oligosScrollPane;
    protected javax.swing.JTable oligosTable;
    private javax.swing.JScrollPane partsScrollPane;
    protected javax.swing.JTable partsTable;
    private javax.swing.JScrollPane plasmidsScrollPane;
    protected javax.swing.JTable plasmidsTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JCheckBox restrictToUserCheckBox;
    private javax.swing.JScrollPane vectorsScrollPane;
    protected javax.swing.JTable vectorsTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    private ObjBasePopup _obp;
    private boolean _connected;

    private class InventoryTableCellRenderer extends DefaultTableCellRenderer {

        public InventoryTableCellRenderer() {
            super();
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (table.getValueAt(row, 2).equals("changed")) {
                setForeground(Color.black);
                setBackground(Color.green);
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            if (isSelected) {
                setBackground(Color.lightGray);
            }
            this.setText((String) value);


            return this;
        }
    }
}
