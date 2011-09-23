/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocore.api.data;

import org.clothocore.api.core.Collector;
import java.sql.*;
import java.util.ArrayList;
import org.openide.util.Exceptions;

/**
 *
 * @author rozagh
 */
public class PhagebookListener {

    public static void phagebookListener (ObjBase obj , ObjBase oldObj) 
    {

       ArrayList<String> objChanges =   compareObjs (obj, oldObj);
       //objChanges.add("Other");
         System.out.println(obj.getName() + "is saved by"+ Collector.getCurrentUser() +" at time " + obj.getLastModifiedAsString() );

         Timestamp ts = new Timestamp(obj.getLastModified().getTime());

          String action = "Edited";
         if (oldObj == null)
         {
            action= "Added";
         }

         for (int i=0; i< objChanges.size(); i++)
        {
        insertEvent(Collector.getCurrentUser().getUUID() , obj.getUUID(), obj.getType().toString(), action , objChanges.get(i), ts  );
        }
    }

    public static void insertEvent(String  uID, String objID, String objType, String action, String objChange,  Timestamp ts )
                                            {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        //java.sql.Date d = new Date(jd.getTime());
            Connection con = null;
            PreparedStatement pstmt = null;
            try{
            con = DriverManager.getConnection("jdbc:mysql://128.197.164.27/phagebook", "phagebook.rwdu", "cidar"); //("jdbc:mysql://bioed.bu.edu/phagebook", "rozagh", "rozagh");
            pstmt = con.prepareStatement("INSERT INTO PhagebookEvent (clothoUserId, itemId, eventType, eventField, eventDate, eventAction) VALUES ( ? , ?, ?,?, ?, ?)");
            pstmt.setString(1, uID);
            pstmt.setString(2, objID);
            pstmt.setString(3, objType);
            pstmt.setString(4, objChange);
            pstmt.setTimestamp(5, ts);
            pstmt.setString(6, action);
            pstmt.executeUpdate();
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
       
    }

    private static ArrayList<String> compareObjs(ObjBase newObj, ObjBase oldObj) {
        
        ArrayList<String> result = new ArrayList<String>();

        if (oldObj == null)
        {
           result.add("other");
        }
        else
        {
            try {


        if (newObj.getType() == ObjType.PART)
        {
            try
            {
           if (! ((Part) newObj).getFormat().equals(((Part)oldObj).getFormat()))
               result.add("format");
           } catch (Exception e) {
            System.out.println(e);
            }
           if (! ((Part) newObj).getSeq().equals(((Part)oldObj).getSeq()))
               result.add("nucseq");
           if (! ((Part) newObj).getShortDescription().equals(((Part)oldObj).getShortDescription()))
               result.add("description");
           if (! ((Part) newObj).getName().equals(((Part)oldObj).getName()))
               result.add("name");
           if (((Part) newObj).getRiskGroup() != (((Part)oldObj).getRiskGroup()))
               result.add("riskgroup");
           if (((Part) newObj).hashCode() != (((Part)oldObj).hashCode())   & result.isEmpty())
               result.add("other");
        }

        if (newObj.getType() == ObjType.NUCSEQ)
        {
           if (! ((NucSeq) newObj).getSeq().equals(((NucSeq)oldObj).getSeq()))
               result.add("nucseq");
           if (! ((NucSeq) newObj).getName().equals(((NucSeq)oldObj).getName()))
               result.add("name");
           if (((NucSeq) newObj).hashCode() != (((NucSeq)oldObj).hashCode())   & result.isEmpty())
               result.add("other");
        }

        if (newObj.getType() == ObjType.PLASMID)
        {
           if (! ((Plasmid) newObj).getSeq().equals(((Plasmid)oldObj).getSeq()))
               result.add("nucseq");
           if (! ((Plasmid) newObj).getName().equals(((Plasmid)oldObj).getName()))
               result.add("name");
           try
           {
           if (! ((Plasmid) newObj).getFormat().equals(((Plasmid)oldObj).getFormat()))
               result.add("format");
           } catch (Exception e) {
            System.out.println(e);
            }
           try
           {
           if (! ((Plasmid) newObj).getVector().equals(((Plasmid)oldObj).getVector()))
               result.add("vector");
           } catch (Exception e) {
            System.out.println(e);
            }
           if (! ((Plasmid) newObj).getConstructionFileAsString().equals(((Plasmid)oldObj).getConstructionFileAsString()))
               result.add("constructionfile");
           if (((Plasmid) newObj).getRiskGroup() != (((Plasmid)oldObj).getRiskGroup()))
               result.add("riskgroup");
           if (((Plasmid) newObj).hashCode() != (((Plasmid)oldObj).hashCode())   & result.isEmpty())
               result.add("other");
        }

        if (newObj.getType() == ObjType.VECTOR)
        {
           if (! ((Vector) newObj).getSeq().equals(((Vector)oldObj).getSeq()))
               result.add("nucseq");
           if (! ((Vector) newObj).getName().equals(((Vector)oldObj).getName()))
               result.add("name");
           try{
           if (! ((Vector) newObj).getFormat().equals(((Vector)oldObj).getFormat()))
               result.add("format");
           } catch (Exception e) {
            System.out.println(e);
            }
           if (! ((Vector) newObj).getShortDescription().equals(((Vector)oldObj).getShortDescription()))
               result.add("description");
           if (((Vector) newObj).getRiskGroup() != (((Vector)oldObj).getRiskGroup()))
               result.add("riskgroup");
           if (((Vector) newObj).hashCode() != (((Vector)oldObj).hashCode())   & result.isEmpty())
               result.add("other");
        }

        if (newObj.getType() == ObjType.OLIGO)
        {
           if (! ((Oligo) newObj).getSeq().equals(((Oligo)oldObj).getSeq()))
               result.add("nucseq");
           if (! ((Oligo) newObj).getName().equals(((Oligo)oldObj).getName()))
               result.add("name");
           if (((Oligo) newObj).hashCode() != (((Oligo)oldObj).hashCode())   & result.isEmpty())
               result.add("other");
        }

        if (newObj.getType() == ObjType.FEATURE)
        {
            try
            {
           if (! ((Feature) oldObj).getGenbankId().equals(((Feature)newObj).getGenbankId()))
               result.add("genbank");
            } catch (Exception e) {
            System.out.println(e);
            }
           if (! ((Feature) newObj).getSeq().equals(((Feature)oldObj).getSeq()))
               result.add("nucseq");
           if (! ((Feature) newObj).getName().equals(((Feature)oldObj).getName()))
               result.add("name");
           if (((Feature) newObj).getRiskGroup() != (((Feature)oldObj).getRiskGroup()))
               result.add("riskgroup");
           if (((Feature) newObj).hashCode() != (((Feature)oldObj).hashCode())   & result.isEmpty())
               result.add("other");
        }
        } catch (Exception e) {
            System.out.println(e);
            }
        }
        return result;
        
    }
}
