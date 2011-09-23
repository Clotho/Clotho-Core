/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.widget.fabdash;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.dnd.TransferableObject;

/**
 *
 * @author jenhan
 */
public class InventoryTransferHandler extends TransferHandler {

    @Override
    public Transferable createTransferable(JComponent c) {
        try {
            JTable t = (JTable) c;
            String toPackage = (String) t.getValueAt(t.getSelectedRow(), 0);
            if (toPackage != null) {
                ObjBase toPackageObjBase = null;
                if (t.getName().contains("oligo")) {
                    toPackageObjBase = Oligo.retrieveByName(toPackage);
                } else if (t.getName().contains("part")) {
                    toPackageObjBase = Part.retrieveByName(toPackage);
                } else if (t.getName().contains("vector")) {
                    toPackageObjBase = Vector.retrieveByName(toPackage);
                } else if (t.getName().contains("plasmid")) {
                    toPackageObjBase = Plasmid.retrieveByName(toPackage);
                }
                if (toPackageObjBase!=null ) {
                return new TransferableObject(toPackageObjBase);
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int getSourceActions(JComponent comp) {
        return COPY;

    }
}
