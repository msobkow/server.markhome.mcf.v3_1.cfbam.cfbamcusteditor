// Description: Java 25 Custom JavaFX in-memory CFBam editor

/*
 *	server.markhome.mcf.CFBam
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFBam - Business Application Model
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfbam.cfbamcusteditor;

import java.io.Serializable;
import java.math.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import server.markhome.mcf.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfcore.msscf.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfintobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbamobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsecsaxloader.*;
import server.markhome.mcf.v3_1.cfint.cfintsaxloader.*;
import server.markhome.mcf.v3_1.cfbam.cfbamsaxloader.*;
import server.markhome.mcf.v3_1.cfsec.cfsecjavafx.*;
import server.markhome.mcf.v3_1.cfint.cfintjavafx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamjavafx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamram.*;
import server.markhome.mcf.v3_1.cfbam.cfbammsscf.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustmssbamcf.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustxmlloader.*;
import server.markhome.mcf.v3_1.cfsec.cfseccustfx.*;
import server.markhome.mcf.v3_1.cfint.cfintcustfx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustfx.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CFBamCustEditorRelationEntry
{
	protected ICFBamRelationObj relation = null;
	protected String name = null;

	public CFBamCustEditorRelationEntry( ICFBamRelationObj argRelation, String argName ) {
		final String S_ProcName="construct";
		if( argName == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				2,
				"argName" );
		}
		relation = argRelation;
		name = argName;
	}

	public ICFBamRelationObj getRelation() {
		return( relation );
	}

	public String getName() {
		return( name );
	}

	public String toString() {
		return( name );
	}

	public static ObservableList<CFBamCustEditorRelationEntry> getObservableListOfRelationEntryForTable( ICFBamTableObj table ) {
		final String S_ProcName = "getObservableListOfRelationEntryForTable";
		ArrayList<CFBamCustEditorRelationEntry> arrayList = new ArrayList<CFBamCustEditorRelationEntry>();

		CFBamCustEditorRelationEntry entry = new CFBamCustEditorRelationEntry( null, "" );
		arrayList.add( entry );

		RelationTypeEnum relationType;
		ICFBamRelationObj relation;
		ICFBamRelationObj superclassRelation;
		Iterator<ICFBamRelationObj> tableRelationIter;
		while( table != null ) {
			superclassRelation = null;
			tableRelationIter = table.getOptionalComponentsRelation().iterator();
			while( tableRelationIter.hasNext() ) {
				relation = tableRelationIter.next();
				relationType = relation.getRequiredRelationType();
				if( relationType == ICFBamSchema.RelationTypeEnum.Superclass ) {
					if( superclassRelation != null ) {
						throw new CFLibRuntimeException( CFBamCustEditorRelationEntry.class,
							S_ProcName,
							"Superclass relation specified more than once for Table \"" + table.getObjName() + "\"" );
					}
					superclassRelation = relation;
				}
				else if( relationType != ICFBamSchema.RelationTypeEnum.Unknown ) {
					entry = new CFBamCustEditorRelationEntry( relation, relation.getObjName() );
					arrayList.add( entry );
				}
			}
			if( superclassRelation != null ) {
				table = superclassRelation.getRequiredLookupToTable();
			}
			else {
				table = null;
			}
		}

		ObservableList<CFBamCustEditorRelationEntry> observableList = FXCollections.observableList( arrayList );

		observableList.sort( compareName );

		return( observableList );
	}

	public static ObservableList<CFBamCustEditorRelationEntry> getObservableListOfInheritedRelationEntryForTable( ICFBamTableObj table ) {
		final String S_ProcName = "getObservableListOfInheritedRelationEntryForTable";
		if( table == null ) {
			throw new CFLibNullArgumentException( CFBamCustEditorRelationEntry.class,
				S_ProcName,
				1,
				"table" );
		}
		ArrayList<CFBamCustEditorRelationEntry> arrayList = new ArrayList<CFBamCustEditorRelationEntry>();

		CFBamCustEditorRelationEntry entry = new CFBamCustEditorRelationEntry( null, "" );
		arrayList.add( entry );

		RelationTypeEnum relationType;
		ICFBamRelationObj relation;
		ICFBamRelationObj superclassRelation;
		Iterator<ICFBamRelationObj> tableRelationIter;

		superclassRelation = null;
		tableRelationIter = table.getOptionalComponentsRelation().iterator();
		while( ( superclassRelation == null ) && tableRelationIter.hasNext() ) {
			relation = tableRelationIter.next();
			relationType = relation.getRequiredRelationType();
			if( relationType == ICFBamSchema.RelationTypeEnum.Superclass ) {
				superclassRelation = relation;
			}
		}
		if( superclassRelation != null ) {
			table = superclassRelation.getRequiredLookupToTable();
		}
		else {
			table = null;
		}
		while( table != null ) {
			superclassRelation = null;
			tableRelationIter = table.getOptionalComponentsRelation().iterator();
			while( tableRelationIter.hasNext() ) {
				relation = tableRelationIter.next();
				relationType = relation.getRequiredRelationType();
				if( relationType == ICFBamSchema.RelationTypeEnum.Superclass ) {
					if( superclassRelation != null ) {
						throw new CFLibRuntimeException( CFBamCustEditorRelationEntry.class,
							S_ProcName,
							"Superclass relation specified more than once for Table \"" + table.getObjName() + "\"" );
					}
					superclassRelation = relation;
				}
				else if( relationType != ICFBamSchema.RelationTypeEnum.Unknown ) {
					entry = new CFBamCustEditorRelationEntry( relation, table.getObjName() + "." + relation.getObjName() );
					arrayList.add( entry );
				}
			}
			if( superclassRelation != null ) {
				table = superclassRelation.getRequiredLookupToTable();
			}
			else {
				table = null;
			}
		}

		ObservableList<CFBamCustEditorRelationEntry> observableList = FXCollections.observableList( arrayList );

		observableList.sort( compareName );

		return( observableList );
	}

	public static class CompareByName
	implements Comparator<CFBamCustEditorRelationEntry>
	{
		public CompareByName() {
		}

		public int compare( CFBamCustEditorRelationEntry lhs, CFBamCustEditorRelationEntry rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				String lhsName = lhs.getName();
				String rhsName = rhs.getName();
				if( lhsName == null ) {
					if( rhsName == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhsName == null ) {
					return( 1 );
				}
				else {
					return( lhsName.compareTo( rhsName ) );
				}
			}
		}
	}

	protected static CompareByName compareName = new CompareByName();
}
