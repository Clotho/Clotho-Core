/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.api.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.TransferHandler;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.dnd.TransferableObject;
import org.openide.util.Exceptions;

/**
 *
 * @author jenhan
 */
public class ObjBaseTransferHandler extends TransferHandler {
public ObjBaseTransferHandler(ObjBaseDropTarget target) {
    _target=target;
}
    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        try {
            ObjBase o= (ObjBase) support.getTransferable().getTransferData(TransferableObject.objBaseFlavor);
            _target.handleDropedObject(o);
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return super.importData(support);
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        try {
            if (support.getTransferable().getTransferData(TransferableObject.objBaseFlavor) != null) {
                return true;
            }
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
            return false;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return false;
        }
        return false;
    }
    
    private ObjBaseDropTarget _target;
}
