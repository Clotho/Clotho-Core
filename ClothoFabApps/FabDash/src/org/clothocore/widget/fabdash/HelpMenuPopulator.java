/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.widget.fabdash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import org.openide.util.actions.Presenter;

/**
 *
 * @author jenhan
 */
public class HelpMenuPopulator extends AbstractAction implements Presenter.Menu{
        @Override
    public JMenuItem getMenuPresenter() {
        JMenuItem m = new JMenuItem("About Clotho") {
        };
        m.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JFrame jf = new JFrame("moo");
                jf.add(new JLabel("said the cow"));
                jf.pack();
                jf.setVisible(true);
            }
        });
        return m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
