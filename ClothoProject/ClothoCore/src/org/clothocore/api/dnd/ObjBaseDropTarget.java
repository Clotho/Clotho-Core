/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.api.dnd;

import org.clothocore.api.data.ObjBase;

/**
 *
 * @author jenhan
 */
public interface ObjBaseDropTarget {
    /**
     * this method should be called after an ObjBase has been dropped
     */
    public void handleDropedObject(ObjBase o);
}
