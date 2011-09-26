
/*
Copyright (c) 2009 The Regents of the University of California.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS..
 */
package org.clothocore.api.data;

import org.clothocore.api.core.Collector;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 * @author J. Christopher Anderson
 */
public abstract class Sample extends ObjBase {

    public Sample( SampleDatum d ) {
        super( d );
        _samDatum = d;
    }

    /**
     * Constructor for a new Sample generated from scratch
     * @param myplasmid the plasmid ("DNA") within the Sample
     * @param mycell the strain from which the plasmid is derived
     * @param mycontainer the Container the Sample is being put in
     * @param myvolume how many uL of liquid are in the new Sample
     * @param author who is creating the Sample
     */
    public Sample( String name, double myvolume, Person author, SampleType stype ) {
        super( );
        switch(stype) {
            case PLASMID_SAMPLE:
                _samDatum = new PlasmidSample.PlasmidSampleDatum();
                break;
            case OLIGO_SAMPLE:
                _samDatum = new OligoSample.OligoSampleDatum();
                break;
            case STRAIN_SAMPLE:
                _samDatum = new StrainSample.StrainSampleDatum();
                break;

        }

        _datum = _samDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _samDatum.uuid = _uuid;

        _samDatum._volume = myvolume;
        _samDatum._authorUUID = author.getUUID();
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        /*
        protected ArrayList<String> _parentSamples;

         */

        if ( Collector.isLocal( _samDatum._containerUUID ) ) {
            Container acon = getContainer();
            if ( !acon.isInDatabase() ) {
                if ( !acon.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _samDatum._authorUUID ) ) {
            Person p = getAuthor();
            if ( !p.isInDatabase() ) {
                if ( !p.save( conn ) ) {
                    return false;
                }
            }
        }

        for ( String uuid : _samDatum.sampleDataLinks ) {
            SampleData s = Collector.getSampleData( uuid );
            if ( !s.isInDatabase() ) {
                if ( !s.save( conn ) ) {
                    return false;
                }
            }
        }
        if(!Collector.getCurrentUser().getUUID().equals(this.getAuthor().getUUID())) {
            if(!Collector.getCurrentUser().isAdmin()) {
                    System.out.println( "Current user "+this.getAuthor().getDisplayName()+ " does not have permission to modify "+this.getName() );
                return false;
            }
        }
        return super.save( conn );
    }

    @Override
    public ObjType getType() {
        return ObjType.SAMPLE;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case SAMPLE_DATA:
                System.out.println("Adding a sampledata to this sample " + this.getUUID());
                SampleData sd = (SampleData) dropObject;
                _samDatum.sampleDataLinks.add( sd.getUUID() );
                sd.addSampleRelay( this );
                setChanged(RefreshEvent.Condition.DATA_TO_SAMPLE);
                return true;
            default:
                return false;
        }
    }

    /* SETTERS
     * */

    public boolean PUT_SampleToContainer( Container newContainer ) {
        if ( newContainer.EVT_SampleToContainer( this ) ) {
            System.out.println("PUT_SampleToContainer called");
            _samDatum._containerUUID = newContainer.getUUID();
            System.out.println( "PUT_SampleToContainer put in container as " + _samDatum._containerUUID );
            setChanged(RefreshEvent.Condition.SAMPLE_TO_CONTAINER);
            return true;
        } else {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SAMPLE_TO_CONTAINER));
            JOptionPane.showMessageDialog( null, "You are trying to put a sample into an occupied container, how should I proceed?", "Error", JOptionPane.ERROR_MESSAGE );
            return false;
        }
    }

    /**
     * Method for transferring liquid from one Container to another.  Involves duplicating the Sample
     * and putting it into the new Container and adjusting the volumes.
     *
     * Method is rejected if the Container already has a Sample or there is insufficient liquid present.
     *
     * @param newcon
     * @param dvol
     * @return
     */
    public boolean transferAnAliquot( Container newcon, Double dvol ) {
        if ( _samDatum._volume < dvol ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.VOLUME_CHANGED));
            return false;
        }
        if ( newcon.getSample() != null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.VOLUME_CHANGED));
            return false;
        }

        Sample asample = this.duplicateTo( newcon, dvol );
        _samDatum._volume = _samDatum._volume - dvol;
        setChanged(RefreshEvent.Condition.VOLUME_CHANGED);
        return true;
    }

    /**
     * Change the quality of the sample.  Quality refers to the degree of confidence to which the sample
     * reflects its composition.  Values are:
     * 0 - Wrong / Fails
     * 1 - Unknown / Untested
     * 2 - Caution
     * 3 - works / functional
     * 4 - Sequencing confirmed
     * @param quality
     */
    public void changeQuality(short quality) {
        if(quality>-1 && quality < 5) {
            _samDatum._quality =  quality;
            setChanged(RefreshEvent.Condition.QUALITY_CHANGED);
        } else {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.QUALITY_CHANGED));
        }
    }

    /**
     * Change the volume of the Sample
     * @param adub
     */
    public void changeVolume(Double adub) {
        if(adub<0) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.VOLUME_CHANGED));
            return;
        }
        _samDatum._volume = adub;
        setChanged(RefreshEvent.Condition.VOLUME_CHANGED);
    }

    public void changeConcentration(double conc)
    {
        if (conc < 0) {
            fireData(new RefreshEvent(this,RefreshEvent.Condition.PLASMID_CHANGED));
            return;
        }
        _samDatum._concentration = conc;
        setChanged(RefreshEvent.Condition.VOLUME_CHANGED);
    }
    public void changeAuthor(Person author) {
        if(author==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
        }
        _samDatum._authorUUID = author.getUUID();
        setChanged(RefreshEvent.Condition.AUTHOR_CHANGED);
    }

    /* GETTERS
     * */
    abstract protected Sample duplicateTo( Container acon, Double volume );

    public Person getAuthor() {
        return Collector.getPerson( _samDatum._authorUUID );
    }

    public Container getContainer() {
        return Collector.getContainer( _samDatum._containerUUID );
    }

    public Plate getPlate() {
        Container acon = Collector.getContainer( _samDatum._containerUUID );
        return acon.getPlate();
    }

    public short getQuality() {
        return _samDatum._quality;
    }

    public double getVolume() {
        return _samDatum._volume;
    }

    public double getConcentration() {
        return _samDatum._concentration;
    }

    public Date getDateLastUsed() {
        return _samDatum._lastUsed;
    }

    public ArrayList<Sample> getParentSamples() {
        ArrayList<Sample> out = new ArrayList<Sample>();
        for ( String uuid : _samDatum._parentSamples ) {
            Sample asample = Collector.getSample( uuid );
            out.add( asample );
        }
        return out;
    }

    public HashSet<String> getDataLinks() {
        return _samDatum.sampleDataLinks;
    }

    public HashSet<SampleData> getData() {
        HashSet<SampleData> out = new HashSet<SampleData>();
        for ( String s : _samDatum.sampleDataLinks ) {
            SampleData sd = Collector.getSampleData( s );
            out.add( sd );
        }
        return out;
    }

    public static Sample retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.SAMPLE );
        cq.contains( Sample.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Sample p = (Sample) l.get( 0 );
        return p;
    }

    public abstract SampleType getSampleType();
    /*-----------------
    variables
    -----------------*/
    protected SampleDatum _samDatum;

    public static class SampleDatum extends ObjBaseDatum {

        public String _containerUUID = "";
        public String _authorUUID = "";
        public short _quality = 1;
        public double _volume;
        public double _concentration;
        public Date _lastUsed;
        public ArrayList<String> _parentSamples = new ArrayList<String>();
        public HashSet<String> sampleDataLinks = new HashSet<String>();

        @Override
        public ObjType getType() {
            return ObjType.SAMPLE;
        }
    }

    public enum SampleType {

        PLASMID_SAMPLE, OLIGO_SAMPLE, STRAIN_SAMPLE, CELL_SAMPLE
    };

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        QUALITY,
        VOLUME,
        CONCENTRATION,
        LAST_USED,
    }
}