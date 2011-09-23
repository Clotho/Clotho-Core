package org.clothocore.widget.fabdash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.ObjType;
import org.openide.awt.StatusDisplayer;
import org.openide.util.actions.Presenter;

/**
 *
 * @author Jenhan Tao
 */
public class FileMenuPopulator extends AbstractAction implements Presenter.Menu {

    @Override
    public JMenuItem getMenuPresenter() {
        
        JMenu m = new JMenu("New") {
        };
        JMenuItem partsItem = new JMenuItem("Part");
        partsItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Collector.isConnected()) {
                    StatusDisplayer.getDefault().setStatusText("Connect before creating a new Part");
                    return;
                }
                MakeObjectFrame mof = new MakeObjectFrame(ObjType.PART);
                mof.pack();
                mof.setVisible(true);
            }
        });
        m.add(partsItem);

        JMenuItem oligoItem = new JMenuItem("Oligo");
        oligoItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Collector.isConnected()) {
                    StatusDisplayer.getDefault().setStatusText("Connect before creating a new Oligo");
                    return;
                }
                MakeObjectFrame mof = new MakeObjectFrame(ObjType.OLIGO);
                mof.pack();
                mof.setVisible(true);
            }
        });
        m.add(oligoItem);

        JMenuItem vectorItem = new JMenuItem("Vector");
        vectorItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Collector.isConnected()) {
                    StatusDisplayer.getDefault().setStatusText("Connect before creating a new Vector");
                    return;
                }
                MakeObjectFrame mof = new MakeObjectFrame(ObjType.VECTOR);
                mof.pack();
                mof.setVisible(true);
            }
        });
        m.add(vectorItem);

        JMenuItem featureItem = new JMenuItem("Feature");
        featureItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Collector.isConnected()) {
                    StatusDisplayer.getDefault().setStatusText("Connect before creating a new Feature");
                    return;
                }
                MakeObjectFrame mof = new MakeObjectFrame(ObjType.FEATURE);
                mof.pack();
                mof.setVisible(true);
            }
        });
        m.add(featureItem);

//        JMenuItem sampleItem = new JMenuItem("Sample");
//        sampleItem.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!Collector.isConnected()) {
//                    StatusDisplayer.getDefault().setStatusText("Connect before creating a new Sample");
//                    return;
//                }
//                MakeObjectFrame mof = new MakeObjectFrame(ObjType.SAMPLE);
//                mof.pack();
//                mof.setVisible(true);
//            }
//        });
//        m.add(sampleItem);

        JMenuItem collectionItem = new JMenuItem("Collection");
        collectionItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Collector.isConnected()) {
                    StatusDisplayer.getDefault().setStatusText("Connect before creating a new Collection");
                    return;
                }
                MakeObjectFrame mof = new MakeObjectFrame(ObjType.COLLECTION);
                mof.pack();
                mof.setVisible(true);
            }
        });
        m.add(collectionItem);
        return m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
