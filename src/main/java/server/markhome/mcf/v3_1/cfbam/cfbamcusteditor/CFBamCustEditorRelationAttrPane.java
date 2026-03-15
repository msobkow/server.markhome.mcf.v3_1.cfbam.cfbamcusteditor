// Description: Java 25 JavaFX Attribute Pane implementation for Relation.

/*
 *	Mark's Code Fractal 3.1 CFBam - Business Application Model
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *
 *	These files are part of Mark's Code Fractal CFBam.
 *
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
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
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

/**
 *	CFBamJavaFXRelationAttrPane JavaFX Attribute Pane implementation
 *	for Relation.
 */
public class CFBamCustEditorRelationAttrPane
extends CFGridPane
implements ICFBamJavaFXRelationPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class NarrowedRelationCFLabel
		extends CFLabel
	{
		public NarrowedRelationCFLabel() {
			super();
			setText( "Narrowed Relation" );
		}
	}

	protected class NarrowedRelationCFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public NarrowedRelationCFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfInheritedRelationEntryForTable( getEffFocusAsRelation().getRequiredContainerFromTable() ) );
			getSelectionModel().select( 0 );
		}

		public void select( ICFBamRelationObj value ) {
			CFBamCustEditorRelationEntry entry;
			Iterator<CFBamCustEditorRelationEntry> iterItems = getItems().iterator();
			while( iterItems.hasNext() ) {
				entry = iterItems.next();
				if( entry.getRelation() == value ) {
					getSelectionModel().select( entry );
					return;
				}
			}
			getSelectionModel().select( 0 );
		}
	}

	protected class PopTopDepCFLabel
		extends CFLabel
	{
		public PopTopDepCFLabel() {
			super();
			setText( "Pop. Top" );
		}
	}

	public ICFBamRelationObj getEffFocusAsRelation() {
		ICFBamRelationObj relation = getJavaFXFocusAsRelation().getEditAsRelation();
		if( relation == null ) {
			relation = getJavaFXFocusAsRelation();
		}
		return( relation );
	}

	protected class PopTopDepCFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public PopTopDepCFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( getEffFocusAsRelation().getRequiredContainerFromTable() ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorPopSubDep1().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep1().getSelectionModel().select( 0 );
						getJavaFXEditorPopSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorPopSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep3().getSelectionModel().select( 0 );
					}
					else {
						ICFBamRelationObj selectedRelation = newValue.getRelation();
						ICFBamTableObj targetTable;
						if( selectedRelation == null ) {
							targetTable = null;
						}
						else {
							targetTable = selectedRelation.getRequiredLookupToTable();
						}
						getJavaFXEditorPopSubDep1().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorPopSubDep1().getSelectionModel().select( 0 );
						getJavaFXEditorPopSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorPopSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep3().getSelectionModel().select( 0 );
					}
				}
			});
			getSelectionModel().select( 0 );
		}

		public void select( ICFBamRelationObj value ) {
			CFBamCustEditorRelationEntry entry;
			Iterator<CFBamCustEditorRelationEntry> iterItems = getItems().iterator();
			while( iterItems.hasNext() ) {
				entry = iterItems.next();
				if( entry.getRelation() == value ) {
					getSelectionModel().select( entry );
					return;
				}
			}
			getSelectionModel().select( 0 );
		}
	}

	protected class PopSubDep1CFLabel
		extends CFLabel
	{
		public PopSubDep1CFLabel() {
			super();
			setText( "Pop. Level 1" );
		}
	}

	protected class PopSubDep1CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public PopSubDep1CFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorPopSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorPopSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep3().getSelectionModel().select( 0 );
					}
					else {
						ICFBamRelationObj selectedRelation = newValue.getRelation();
						ICFBamTableObj targetTable;
						if( selectedRelation == null ) {
							targetTable = null;
						}
						else {
							targetTable = selectedRelation.getRequiredLookupToTable();
						}
						getJavaFXEditorPopSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorPopSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorPopSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep3().getSelectionModel().select( 0 );
					}
				}
			});
			getSelectionModel().select( 0 );
		}

		public void select( ICFBamRelationObj value ) {
			CFBamCustEditorRelationEntry entry;
			Iterator<CFBamCustEditorRelationEntry> iterItems = getItems().iterator();
			while( iterItems.hasNext() ) {
				entry = iterItems.next();
				if( entry.getRelation() == value ) {
					getSelectionModel().select( entry );
					return;
				}
			}
			getSelectionModel().select( 0 );
		}
	}

	protected class PopSubDep2CFLabel
		extends CFLabel
	{
		public PopSubDep2CFLabel() {
			super();
			setText( "Pop. Level 2" );
		}
	}

	protected class PopSubDep2CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public PopSubDep2CFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorPopSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorPopSubDep3().getSelectionModel().select( 0 );
					}
					else {
						ICFBamRelationObj selectedRelation = newValue.getRelation();
						ICFBamTableObj targetTable;
						if( selectedRelation == null ) {
							targetTable = null;
						}
						else {
							targetTable = selectedRelation.getRequiredLookupToTable();
						}
						getJavaFXEditorPopSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorPopSubDep3().getSelectionModel().select( 0 );
					}
				}
			});
			getSelectionModel().select( 0 );
		}

		public void select( ICFBamRelationObj value ) {
			CFBamCustEditorRelationEntry entry;
			Iterator<CFBamCustEditorRelationEntry> iterItems = getItems().iterator();
			while( iterItems.hasNext() ) {
				entry = iterItems.next();
				if( entry.getRelation() == value ) {
					getSelectionModel().select( entry );
					return;
				}
			}
			getSelectionModel().select( 0 );
		}
	}

	protected class PopSubDep3CFLabel
		extends CFLabel
	{
		public PopSubDep3CFLabel() {
			super();
			setText( "Pop. Level 3" );
		}
	}

	protected class PopSubDep3CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public PopSubDep3CFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
			getSelectionModel().select( 0 );
		}

		public void select( ICFBamRelationObj value ) {
			CFBamCustEditorRelationEntry entry;
			Iterator<CFBamCustEditorRelationEntry> iterItems = getItems().iterator();
			while( iterItems.hasNext() ) {
				entry = iterItems.next();
				if( entry.getRelation() == value ) {
					getSelectionModel().select( entry );
					return;
				}
			}
			getSelectionModel().select( 0 );
		}
	}

	protected ObservableList<String> observableListOfRelationType =
		FXCollections.observableArrayList(
			"Unknown",
			"Lookup",
			"Superclass",
			"Master",
			"Details",
			"Container",
			"Components",
			"Owner",
			"Parent",
			"Children" );

	protected class DefSchemaCFLabel
		extends CFLabel
	{
		public DefSchemaCFLabel() {
			super();
			setText( "Defining Schema" );
		}
	}

	protected class CallbackDefSchemaChosen
	implements ICFBamJavaFXSchemaDefChosen
	{
		public CallbackDefSchemaChosen() {
		}

		public void choseSchemaDef( ICFBamSchemaDefObj value ) {
			if( javafxReferenceLookupDefSchema != null ) {
				ICFBamRelationObj cur = getEffFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupDefSchema.setReferencedObject( value );
							editObj.setOptionalLookupDefSchema( value );
						}
					}
				}
			}
		}
	}

	protected class DefSchemaReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			ICFBamRelationObj focus = getJavaFXFocusAsRelation();
			ICFBamRelationEditObj editObj  = (ICFBamRelationEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffFocusAsRelation();
			if( focus != null ) {
				ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					String classCode = referencedObj.getClassCode();
					if( "SCHM".equals( classCode ) ) {
						form = javafxSchema.getSchemaDefFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXSchemaDefPaneCommon spec = (ICFBamJavaFXSchemaDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamSchemaDefObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class DefSchemaCFReferenceEditor
		extends CFReferenceEditor
	{
		public DefSchemaCFReferenceEditor() {
			super( new DefSchemaReferenceCallback() );
			setFieldName( "Defining Schema" );
		}
	}

	protected class FromIndexCFLabel
		extends CFLabel
	{
		public FromIndexCFLabel() {
			super();
			setText( "From Index" );
		}
	}

	protected class CallbackFromIndexChosen
	implements ICFBamJavaFXIndexChosen
	{
		public CallbackFromIndexChosen() {
		}

		public void choseIndex( ICFBamIndexObj value ) {
			if( javafxReferenceLookupFromIndex != null ) {
				ICFBamRelationObj cur = getEffFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupFromIndex.setReferencedObject( value );
							editObj.setRequiredLookupFromIndex( value );
						}
					}
				}
			}
		}
	}

	protected class CompareIndexByName
	implements Comparator<ICFBamIndexObj>
	{
		public CompareIndexByName() {
		}

		@Override
		public int compare(ICFBamIndexObj o1, ICFBamIndexObj o2) {
			if( o1 == null ) {
				if( o2 == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( o2 == null ) {
				return( 1 );
			}
			else {
				return( o1.getObjName().compareTo( o2.getObjName() ) );
			}
		}
	}

	protected class FromIndexReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			ICFBamRelationObj focus = getJavaFXFocusAsRelation();
			ICFBamRelationEditObj editObj  = (ICFBamRelationEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupFromIndex.getReferencedObject();
			List<ICFBamIndexObj> listOfIndex = null;
			ICFBamTableObj refFromTable = (ICFBamTableObj)focus.getRequiredContainerFromTable( javafxIsInitializing );
			if( refFromTable == null ) {
				CFConsole.message( "You must specify a From Table before selecting a From Index" );
				return;
			}
			listOfIndex = refFromTable.getOptionalComponentsIndex( javafxIsInitializing );
			if( listOfIndex == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfIndex" );
			}
			ICFBamIndexObj[] arrIndex = new ICFBamIndexObj[listOfIndex.size()];
			Iterator<ICFBamIndexObj> iterIndex = listOfIndex.iterator();
			int idx = 0;
			while( iterIndex.hasNext() ) {
				arrIndex[idx++] = iterIndex.next();
			}
			Arrays.sort( arrIndex, new CompareIndexByName() );
			Collection<ICFBamIndexObj> cltn = new LinkedList<ICFBamIndexObj>();
			for( idx = 0; idx < arrIndex.length; idx++ ) {
				cltn.add( arrIndex[idx] );
			}
			CFBorderPane form = javafxSchema.getIndexFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackFromIndexChosen() );
			((ICFBamJavaFXIndexPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffFocusAsRelation();
			if( focus != null ) {
				ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupFromIndex.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					String classCode = referencedObj.getClassCode();
					if( "IDXD".equals( classCode ) ) {
						form = javafxSchema.getIndexFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXIndexPaneCommon spec = (ICFBamJavaFXIndexPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamIndexObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
}

	protected class FromIndexCFReferenceEditor
		extends CFReferenceEditor
	{
		public FromIndexCFReferenceEditor() {
			super( new FromIndexReferenceCallback() );
			setFieldName( "From Index" );
		}
	}

	protected class ToTableCFLabel
		extends CFLabel
	{
		public ToTableCFLabel() {
			super();
			setText( "To Table" );
		}
	}

	protected class CallbackToTableChosen
	implements ICFBamJavaFXTableChosen
	{
		public CallbackToTableChosen() {
		}

		public void choseTable( ICFBamTableObj value ) {
			if( javafxReferenceLookupToTable != null ) {
				ICFBamRelationObj cur = getJavaFXFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupToTable.setReferencedObject( value );
							editObj.setRequiredLookupToTable( value );
						}
					}
				}
			}
		}
	}

	protected class CompareTableByName
	implements Comparator<ICFBamTableObj>
	{
		public CompareTableByName() {
		}

		@Override
		public int compare(ICFBamTableObj o1, ICFBamTableObj o2) {
			if( o1 == null ) {
				if( o2 == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( o2 == null ) {
				return( 1 );
			}
			else {
				return( o1.getObjName().compareTo( o2.getObjName() ) );
			}
		}
	}

	protected class ToTableReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			ICFBamRelationObj focus = getJavaFXFocusAsRelation();
			ICFBamRelationEditObj editObj  = (ICFBamRelationEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamTableObj referencedObj = (ICFBamTableObj)javafxReferenceLookupToTable.getReferencedObject();
			List<ICFBamTableObj> listOfTable = null;
			ICFBamTableObj refFromTable = (ICFBamTableObj)focus.getRequiredContainerFromTable( javafxIsInitializing );
			if( refFromTable == null ) {
				CFConsole.message( "You must specify a From Table before selecting a To Table" );
				return;
			}
			ICFBamSchemaDefObj refSchemaDef = refFromTable.getRequiredContainerSchemaDef( javafxIsInitializing );
			if( refSchemaDef == null ) {
				CFConsole.message( "You must specify a Containing Schema Definition before selecting a To Table" );
				return;
			}
			listOfTable = refSchemaDef.getOptionalComponentsTables( javafxIsInitializing );
			if( listOfTable == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfTable" );
			}
			ICFBamTableObj[] arrTable = new ICFBamTableObj[listOfTable.size()];
			Iterator<ICFBamTableObj> iterTable = listOfTable.iterator();
			int idx = 0;
			while( iterTable.hasNext() ) {
				arrTable[idx++] = iterTable.next();
			}
			Arrays.sort( arrTable, new CompareTableByName() );
			Collection<ICFBamTableObj> cltn = new LinkedList<ICFBamTableObj>();
			for( idx = 0; idx < arrTable.length; idx++ ) {
				cltn.add( arrTable[idx] );
			}
			CFBorderPane form = javafxSchema.getTableFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackToTableChosen() );
			((ICFBamJavaFXTablePaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffFocusAsRelation();
			if( focus != null ) {
				ICFBamTableObj referencedObj = (ICFBamTableObj)javafxReferenceLookupToTable.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					String classCode = referencedObj.getClassCode();
					if( "TBLD".equals( classCode ) ) {
						form = javafxSchema.getTableFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXTablePaneCommon spec = (ICFBamJavaFXTablePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamTableObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
}

	protected class ToTableCFReferenceEditor
		extends CFReferenceEditor
	{
		public ToTableCFReferenceEditor() {
			super( new ToTableReferenceCallback() );
			setFieldName( "To Table" );
		}
	}

	protected class ToIndexCFLabel
		extends CFLabel
	{
		public ToIndexCFLabel() {
			super();
			setText( "To Index" );
		}
	}

	protected class CallbackToIndexChosen
	implements ICFBamJavaFXIndexChosen
	{
		public CallbackToIndexChosen() {
		}

		public void choseIndex( ICFBamIndexObj value ) {
			if( javafxReferenceLookupToIndex != null ) {
				ICFBamRelationObj cur = getJavaFXFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupToIndex.setReferencedObject( value );
							editObj.setRequiredLookupToIndex( value );
						}
					}
				}
			}
		}
	}

	protected class ToIndexReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			ICFBamRelationObj focus = getJavaFXFocusAsRelation();
			ICFBamRelationEditObj editObj  = (ICFBamRelationEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupToIndex.getReferencedObject();
			List<ICFBamIndexObj> listOfIndex = null;
			ICFBamTableObj refToTable = (ICFBamTableObj)focus.getRequiredLookupToTable( javafxIsInitializing );
			if( refToTable == null ) {
				CFConsole.message( "You must specify a To Table before selecting a To Index" );
				return;
			}
			listOfIndex = refToTable.getOptionalComponentsIndex( javafxIsInitializing );
			if( listOfIndex == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfIndex" );
			}
			ICFBamIndexObj[] arrIndex = new ICFBamIndexObj[listOfIndex.size()];
			Iterator<ICFBamIndexObj> iterIndex = listOfIndex.iterator();
			int idx = 0;
			while( iterIndex.hasNext() ) {
				arrIndex[idx++] = iterIndex.next();
			}
			Arrays.sort( arrIndex, new CompareIndexByName() );
			Collection<ICFBamIndexObj> cltn = new LinkedList<ICFBamIndexObj>();
			for( idx = 0; idx < arrIndex.length; idx++ ) {
				cltn.add( arrIndex[idx] );
			}
			CFBorderPane form = javafxSchema.getIndexFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackToIndexChosen() );
			((ICFBamJavaFXIndexPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getJavaFXFocusAsRelation();
			if( focus != null ) {
				ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupToIndex.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					String classCode = referencedObj.getClassCode();
					if( "IDXD".equals( classCode ) ) {
						form = javafxSchema.getIndexFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXIndexPaneCommon spec = (ICFBamJavaFXIndexPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamIndexObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
}

	protected class ToIndexCFReferenceEditor
		extends CFReferenceEditor
	{
		public ToIndexCFReferenceEditor() {
			super( new ToIndexReferenceCallback() );
			setFieldName( "To Index" );
		}
	}

	protected class IdCFLabel
		extends CFLabel
	{
		public IdCFLabel() {
			super();
			setText( "Id" );
		}
	}

	protected class IdEditor
		extends CFInt64Editor
	{
		public IdEditor() {
			super();
			setMinValue( CFBamScopeBuff.ID_MIN_VALUE );
			setFieldName( "Id" );
		}
	}

	protected class NameCFLabel
		extends CFLabel
	{
		public NameCFLabel() {
			super();
			setText( "Name" );
		}
	}

	protected class NameEditor
		extends CFStringEditor
	{
		public NameEditor() {
			super();
			setMaxLen( 192 );
			setFieldName( "Name" );
		}
	}

	protected class ShortNameCFLabel
		extends CFLabel
	{
		public ShortNameCFLabel() {
			super();
			setText( "Short Name" );
		}
	}

	protected class ShortNameEditor
		extends CFStringEditor
	{
		public ShortNameEditor() {
			super();
			setMaxLen( 16 );
			setFieldName( "Short Name" );
		}
	}

	protected class LabelCFLabel
		extends CFLabel
	{
		public LabelCFLabel() {
			super();
			setText( "Label" );
		}
	}

	protected class LabelEditor
		extends CFStringEditor
	{
		public LabelEditor() {
			super();
			setMaxLen( 64 );
			setFieldName( "Label" );
		}
	}

	protected class ShortDescriptionCFLabel
		extends CFLabel
	{
		public ShortDescriptionCFLabel() {
			super();
			setText( "Short Description" );
		}
	}

	protected class ShortDescriptionEditor
		extends CFStringEditor
	{
		public ShortDescriptionEditor() {
			super();
			setMaxLen( 128 );
			setFieldName( "Short Description" );
		}
	}

	protected class DescriptionCFLabel
		extends CFLabel
	{
		public DescriptionCFLabel() {
			super();
			setText( "Description" );
		}
	}

	protected class DescriptionEditor
		extends CFStringEditor
	{
		public DescriptionEditor() {
			super();
			setMaxLen( 1023 );
			setFieldName( "Description" );
		}
	}

	protected class RelationTypeCFLabel
		extends CFLabel
	{
		public RelationTypeCFLabel() {
			super();
			setText( "RelationType" );
		}
	}

	protected class RelationTypeEditor
		extends ComboBox<String>
	{
		public RelationTypeEditor() {
			super();
			setItems( observableListOfRelationType );
		}
	}

	protected class DbNameCFLabel
		extends CFLabel
	{
		public DbNameCFLabel() {
			super();
			setText( "Db Name" );
		}
	}

	protected class DbNameEditor
		extends CFStringEditor
	{
		public DbNameEditor() {
			super();
			setMaxLen( 32 );
			setFieldName( "Db Name" );
		}
	}

	protected class SuffixCFLabel
		extends CFLabel
	{
		public SuffixCFLabel() {
			super();
			setText( "Suffix" );
		}
	}

	protected class SuffixEditor
		extends CFStringEditor
	{
		public SuffixEditor() {
			super();
			setMaxLen( 16 );
			setFieldName( "Suffix" );
		}
	}

	protected class IsRequiredCFLabel
		extends CFLabel
	{
		public IsRequiredCFLabel() {
			super();
			setText( "Is Required" );
		}
	}

	protected class IsRequiredEditor
		extends CFBoolEditor
	{
		public IsRequiredEditor() {
			super();
			setIsNullable( false );
			setFieldName( "Is Required" );
		}
	}

	protected class IsXsdContainerCFLabel
		extends CFLabel
	{
		public IsXsdContainerCFLabel() {
			super();
			setText( "Is XSD Container" );
		}
	}

	protected class IsXsdContainerEditor
		extends CFBoolEditor
	{
		public IsXsdContainerEditor() {
			super();
			setIsNullable( false );
			setFieldName( "Is XSD Container" );
		}
	}

	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected PopTopDepCFLabel javafxLabelPopTopDep = null;
	protected PopTopDepCFEditor javafxEditorPopTopDep = null;
	protected PopSubDep1CFLabel javafxLabelPopSubDep1 = null;
	protected PopSubDep1CFEditor javafxEditorPopSubDep1 = null;
	protected PopSubDep2CFLabel javafxLabelPopSubDep2 = null;
	protected PopSubDep2CFEditor javafxEditorPopSubDep2 = null;
	protected PopSubDep3CFLabel javafxLabelPopSubDep3 = null;
	protected PopSubDep3CFEditor javafxEditorPopSubDep3 = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected ICFBamIndexObj javafxLookupFromIndexObj = null;
	protected FromIndexCFLabel javafxLabelLookupFromIndex = null;
	protected FromIndexCFReferenceEditor javafxReferenceLookupFromIndex = null;
	protected ICFBamTableObj javafxLookupToTableObj = null;
	protected ToTableCFLabel javafxLabelLookupToTable = null;
	protected ToTableCFReferenceEditor javafxReferenceLookupToTable = null;
	protected ICFBamIndexObj javafxLookupToIndexObj = null;
	protected ToIndexCFLabel javafxLabelLookupToIndex = null;
	protected ToIndexCFReferenceEditor javafxReferenceLookupToIndex = null;
	protected ICFBamRelationObj javafxLookupNarrowedObj = null;
	protected NarrowedRelationCFLabel javafxLabelLookupNarrowed = null;
	protected NarrowedRelationCFEditor javafxEditorLookupNarrowed = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;
	protected ShortNameCFLabel javafxLabelShortName = null;
	protected ShortNameEditor javafxEditorShortName = null;
	protected LabelCFLabel javafxLabelLabel = null;
	protected LabelEditor javafxEditorLabel = null;
	protected ShortDescriptionCFLabel javafxLabelShortDescription = null;
	protected ShortDescriptionEditor javafxEditorShortDescription = null;
	protected DescriptionCFLabel javafxLabelDescription = null;
	protected DescriptionEditor javafxEditorDescription = null;
	protected RelationTypeCFLabel javafxLabelRelationType = null;
	protected RelationTypeEditor javafxEditorRelationType = null;
	protected DbNameCFLabel javafxLabelDbName = null;
	protected DbNameEditor javafxEditorDbName = null;
	protected SuffixCFLabel javafxLabelSuffix = null;
	protected SuffixEditor javafxEditorSuffix = null;
	protected IsRequiredCFLabel javafxLabelIsRequired = null;
	protected IsRequiredEditor javafxEditorIsRequired = null;
	protected IsXsdContainerCFLabel javafxLabelIsXsdContainer = null;
	protected IsXsdContainerEditor javafxEditorIsXsdContainer = null;

	public CFBamCustEditorRelationAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamRelationObj argFocus ) {
		super();
		Control ctrl;
		CFLabel label;
		CFReferenceEditor reference;
		final String S_ProcName = "construct-schema-focus";
		if( formManager == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"formManager" );
		}
		cfFormManager = formManager;
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				2,
				"argSchema" );
		}
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		setJavaFXFocusAsRelation( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints( 125 );
		ColumnConstraints column2 = new ColumnConstraints( 125, 300, 500 );
		column2.setHgrow( Priority.ALWAYS );
		getColumnConstraints().addAll( column1, column2 );
		int gridRow = 0;

		label = getJavaFXLabelId();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupDefSchema();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		reference = getJavaFXReferenceLookupDefSchema();
		setHalignment( reference, HPos.LEFT );
		add( reference, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelName();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelDbName();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorDbName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelShortName();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorShortName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelDescription();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorDescription();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelShortDescription();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorShortDescription();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLabel();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorLabel();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelSuffix();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorSuffix();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelRelationType();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorRelationType();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelIsRequired();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorIsRequired();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelIsXsdContainer();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorIsXsdContainer();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupFromIndex();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		reference = getJavaFXReferenceLookupFromIndex();
		setHalignment( reference, HPos.LEFT );
		add( reference, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupToTable();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		reference = getJavaFXReferenceLookupToTable();
		setHalignment( reference, HPos.LEFT );
		add( reference, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupToIndex();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		reference = getJavaFXReferenceLookupToIndex();
		setHalignment( reference, HPos.LEFT );
		add( reference, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupNarrowed();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorLookupNarrowed();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelPopTopDep();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorPopTopDep();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelPopSubDep1();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorPopSubDep1();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelPopSubDep2();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorPopSubDep2();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelPopSubDep3();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorPopSubDep3();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		populateFields();
		adjustComponentEnableStates();
		javafxIsInitializing = false;
	}

	public ICFFormManager getCFFormManager() {
		return( cfFormManager );
	}

	public void setCFFormManager( ICFFormManager value ) {
		final String S_ProcName = "setCFFormManager";
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"value" );
		}
		cfFormManager = value;
	}

	public ICFBamJavaFXSchema getJavaFXSchema() {
		return( javafxSchema );
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFBamRelationObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamRelationObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamRelationObj getJavaFXFocusAsRelation() {
		return( (ICFBamRelationObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsRelation( ICFBamRelationObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamSchemaDefObj getJavaFXLookupDefSchemaObj() {
		return( javafxLookupDefSchemaObj );
	}

	public void setJavaFXLookupDefSchemaObj( ICFBamSchemaDefObj value ) {
		javafxLookupDefSchemaObj = value;
	}

	public CFLabel getJavaFXLabelLookupDefSchema() {
		if( javafxLabelLookupDefSchema == null ) {
			javafxLabelLookupDefSchema = new DefSchemaCFLabel();
		}
		return( javafxLabelLookupDefSchema );
	}

	public CFReferenceEditor getJavaFXReferenceLookupDefSchema() {
		if( javafxReferenceLookupDefSchema == null ) {
			javafxReferenceLookupDefSchema = new DefSchemaCFReferenceEditor();
		}
		return( javafxReferenceLookupDefSchema );
	}

	public void setJavaFXReferenceLookupDefSchema( DefSchemaCFReferenceEditor value ) {
		javafxReferenceLookupDefSchema = value;
	}

	public ICFBamIndexObj getJavaFXLookupFromIndexObj() {
		return( javafxLookupFromIndexObj );
	}

	public void setJavaFXLookupFromIndexObj( ICFBamIndexObj value ) {
		javafxLookupFromIndexObj = value;
	}

	public CFLabel getJavaFXLabelLookupFromIndex() {
		if( javafxLabelLookupFromIndex == null ) {
			javafxLabelLookupFromIndex = new FromIndexCFLabel();
		}
		return( javafxLabelLookupFromIndex );
	}

	public CFReferenceEditor getJavaFXReferenceLookupFromIndex() {
		if( javafxReferenceLookupFromIndex == null ) {
			javafxReferenceLookupFromIndex = new FromIndexCFReferenceEditor();
		}
		return( javafxReferenceLookupFromIndex );
	}

	public void setJavaFXReferenceLookupFromIndex( FromIndexCFReferenceEditor value ) {
		javafxReferenceLookupFromIndex = value;
	}

	public ICFBamTableObj getJavaFXLookupToTableObj() {
		return( javafxLookupToTableObj );
	}

	public void setJavaFXLookupToTableObj( ICFBamTableObj value ) {
		javafxLookupToTableObj = value;
	}

	public CFLabel getJavaFXLabelLookupToTable() {
		if( javafxLabelLookupToTable == null ) {
			javafxLabelLookupToTable = new ToTableCFLabel();
		}
		return( javafxLabelLookupToTable );
	}

	public CFReferenceEditor getJavaFXReferenceLookupToTable() {
		if( javafxReferenceLookupToTable == null ) {
			javafxReferenceLookupToTable = new ToTableCFReferenceEditor();
		}
		return( javafxReferenceLookupToTable );
	}

	public void setJavaFXReferenceLookupToTable( ToTableCFReferenceEditor value ) {
		javafxReferenceLookupToTable = value;
	}

	public ICFBamIndexObj getJavaFXLookupToIndexObj() {
		return( javafxLookupToIndexObj );
	}

	public void setJavaFXLookupToIndexObj( ICFBamIndexObj value ) {
		javafxLookupToIndexObj = value;
	}

	public CFLabel getJavaFXLabelLookupToIndex() {
		if( javafxLabelLookupToIndex == null ) {
			javafxLabelLookupToIndex = new ToIndexCFLabel();
		}
		return( javafxLabelLookupToIndex );
	}

	public CFReferenceEditor getJavaFXReferenceLookupToIndex() {
		if( javafxReferenceLookupToIndex == null ) {
			javafxReferenceLookupToIndex = new ToIndexCFReferenceEditor();
		}
		return( javafxReferenceLookupToIndex );
	}

	public void setJavaFXReferenceLookupToIndex( ToIndexCFReferenceEditor value ) {
		javafxReferenceLookupToIndex = value;
	}

	public ICFBamRelationObj getJavaFXLookupNarrowedObj() {
		return( javafxLookupNarrowedObj );
	}

	public void setJavaFXLookupNarrowedObj( ICFBamRelationObj value ) {
		javafxLookupNarrowedObj = value;
	}

	public CFLabel getJavaFXLabelLookupNarrowed() {
		if( javafxLabelLookupNarrowed == null ) {
			javafxLabelLookupNarrowed = new NarrowedRelationCFLabel();
		}
		return( javafxLabelLookupNarrowed );
	}

	public NarrowedRelationCFEditor getJavaFXEditorLookupNarrowed() {
		if( javafxEditorLookupNarrowed == null ) {
			javafxEditorLookupNarrowed = new NarrowedRelationCFEditor();
		}
		return( javafxEditorLookupNarrowed );
	}

	public void setJavaFXEditorLookupNarrowed( NarrowedRelationCFEditor value ) {
		javafxEditorLookupNarrowed = value;
	}

	public PopTopDepCFLabel getJavaFXLabelPopTopDep() {
		if( javafxLabelPopTopDep == null ) {
			javafxLabelPopTopDep = new PopTopDepCFLabel();
		}
		return( javafxLabelPopTopDep );
	}

	public void setJavaFXLabelPopTopDep( PopTopDepCFLabel value ) {
		javafxLabelPopTopDep = value;
	}

	public PopTopDepCFEditor getJavaFXEditorPopTopDep() {
		if( javafxEditorPopTopDep == null ) {
			javafxEditorPopTopDep = new PopTopDepCFEditor();
		}
		return( javafxEditorPopTopDep );
	}

	public void setJavaFXEditorPopTopDep( PopTopDepCFEditor value ) {
		javafxEditorPopTopDep = value;
	}

	public PopSubDep1CFLabel getJavaFXLabelPopSubDep1() {
		if( javafxLabelPopSubDep1 == null ) {
			javafxLabelPopSubDep1 = new PopSubDep1CFLabel();
		}
		return( javafxLabelPopSubDep1 );
	}

	public void setJavaFXLabelPopSubDep1( PopSubDep1CFLabel value ) {
		javafxLabelPopSubDep1 = value;
	}

	public PopSubDep1CFEditor getJavaFXEditorPopSubDep1() {
		if( javafxEditorPopSubDep1 == null ) {
			javafxEditorPopSubDep1 = new PopSubDep1CFEditor();
		}
		return( javafxEditorPopSubDep1 );
	}

	public void setJavaFXEditorPopSubDep1( PopSubDep1CFEditor value ) {
		javafxEditorPopSubDep1 = value;
	}

	public PopSubDep2CFLabel getJavaFXLabelPopSubDep2() {
		if( javafxLabelPopSubDep2 == null ) {
			javafxLabelPopSubDep2 = new PopSubDep2CFLabel();
		}
		return( javafxLabelPopSubDep2 );
	}

	public void setJavaFXLabelPopSubDep2( PopSubDep2CFLabel value ) {
		javafxLabelPopSubDep2 = value;
	}

	public PopSubDep2CFEditor getJavaFXEditorPopSubDep2() {
		if( javafxEditorPopSubDep2 == null ) {
			javafxEditorPopSubDep2 = new PopSubDep2CFEditor();
		}
		return( javafxEditorPopSubDep2 );
	}

	public void setJavaFXEditorPopSubDep2( PopSubDep2CFEditor value ) {
		javafxEditorPopSubDep2 = value;
	}

	public PopSubDep3CFLabel getJavaFXLabelPopSubDep3() {
		if( javafxLabelPopSubDep3 == null ) {
			javafxLabelPopSubDep3 = new PopSubDep3CFLabel();
		}
		return( javafxLabelPopSubDep3 );
	}

	public void setJavaFXLabelPopSubDep3( PopSubDep3CFLabel value ) {
		javafxLabelPopSubDep3 = value;
	}

	public PopSubDep3CFEditor getJavaFXEditorPopSubDep3() {
		if( javafxEditorPopSubDep3 == null ) {
			javafxEditorPopSubDep3 = new PopSubDep3CFEditor();
		}
		return( javafxEditorPopSubDep3 );
	}

	public void setJavaFXEditorPopSubDep3( PopSubDep3CFEditor value ) {
		javafxEditorPopSubDep3 = value;
	}

	public IdCFLabel getJavaFXLabelId() {
		if( javafxLabelId == null ) {
			javafxLabelId = new IdCFLabel();
		}
		return( javafxLabelId );
	}

	public void setJavaFXLabelId( IdCFLabel value ) {
		javafxLabelId = value;
	}

	public IdEditor getJavaFXEditorId() {
		if( javafxEditorId == null ) {
			javafxEditorId = new IdEditor();
		}
		return( javafxEditorId );
	}

	public void setJavaFXEditorId( IdEditor value ) {
		javafxEditorId = value;
	}

	public NameCFLabel getJavaFXLabelName() {
		if( javafxLabelName == null ) {
			javafxLabelName = new NameCFLabel();
		}
		return( javafxLabelName );
	}

	public void setJavaFXLabelName( NameCFLabel value ) {
		javafxLabelName = value;
	}

	public NameEditor getJavaFXEditorName() {
		if( javafxEditorName == null ) {
			javafxEditorName = new NameEditor();
		}
		return( javafxEditorName );
	}

	public void setJavaFXEditorName( NameEditor value ) {
		javafxEditorName = value;
	}

	public ShortNameCFLabel getJavaFXLabelShortName() {
		if( javafxLabelShortName == null ) {
			javafxLabelShortName = new ShortNameCFLabel();
		}
		return( javafxLabelShortName );
	}

	public void setJavaFXLabelShortName( ShortNameCFLabel value ) {
		javafxLabelShortName = value;
	}

	public ShortNameEditor getJavaFXEditorShortName() {
		if( javafxEditorShortName == null ) {
			javafxEditorShortName = new ShortNameEditor();
		}
		return( javafxEditorShortName );
	}

	public void setJavaFXEditorShortName( ShortNameEditor value ) {
		javafxEditorShortName = value;
	}

	public LabelCFLabel getJavaFXLabelLabel() {
		if( javafxLabelLabel == null ) {
			javafxLabelLabel = new LabelCFLabel();
		}
		return( javafxLabelLabel );
	}

	public void setJavaFXLabelLabel( LabelCFLabel value ) {
		javafxLabelLabel = value;
	}

	public LabelEditor getJavaFXEditorLabel() {
		if( javafxEditorLabel == null ) {
			javafxEditorLabel = new LabelEditor();
		}
		return( javafxEditorLabel );
	}

	public void setJavaFXEditorLabel( LabelEditor value ) {
		javafxEditorLabel = value;
	}

	public ShortDescriptionCFLabel getJavaFXLabelShortDescription() {
		if( javafxLabelShortDescription == null ) {
			javafxLabelShortDescription = new ShortDescriptionCFLabel();
		}
		return( javafxLabelShortDescription );
	}

	public void setJavaFXLabelShortDescription( ShortDescriptionCFLabel value ) {
		javafxLabelShortDescription = value;
	}

	public ShortDescriptionEditor getJavaFXEditorShortDescription() {
		if( javafxEditorShortDescription == null ) {
			javafxEditorShortDescription = new ShortDescriptionEditor();
		}
		return( javafxEditorShortDescription );
	}

	public void setJavaFXEditorShortDescription( ShortDescriptionEditor value ) {
		javafxEditorShortDescription = value;
	}

	public DescriptionCFLabel getJavaFXLabelDescription() {
		if( javafxLabelDescription == null ) {
			javafxLabelDescription = new DescriptionCFLabel();
		}
		return( javafxLabelDescription );
	}

	public void setJavaFXLabelDescription( DescriptionCFLabel value ) {
		javafxLabelDescription = value;
	}

	public DescriptionEditor getJavaFXEditorDescription() {
		if( javafxEditorDescription == null ) {
			javafxEditorDescription = new DescriptionEditor();
		}
		return( javafxEditorDescription );
	}

	public void setJavaFXEditorDescription( DescriptionEditor value ) {
		javafxEditorDescription = value;
	}

	public RelationTypeCFLabel getJavaFXLabelRelationType() {
		if( javafxLabelRelationType == null ) {
			javafxLabelRelationType = new RelationTypeCFLabel();
		}
		return( javafxLabelRelationType );
	}

	public void setJavaFXLabelRelationType( RelationTypeCFLabel value ) {
		javafxLabelRelationType = value;
	}

	public RelationTypeEditor getJavaFXEditorRelationType() {
		if( javafxEditorRelationType == null ) {
			javafxEditorRelationType = new RelationTypeEditor();
		}
		return( javafxEditorRelationType );
	}

	public void setJavaFXEditorRelationType( RelationTypeEditor value ) {
		javafxEditorRelationType = value;
	}

	public DbNameCFLabel getJavaFXLabelDbName() {
		if( javafxLabelDbName == null ) {
			javafxLabelDbName = new DbNameCFLabel();
		}
		return( javafxLabelDbName );
	}

	public void setJavaFXLabelDbName( DbNameCFLabel value ) {
		javafxLabelDbName = value;
	}

	public DbNameEditor getJavaFXEditorDbName() {
		if( javafxEditorDbName == null ) {
			javafxEditorDbName = new DbNameEditor();
		}
		return( javafxEditorDbName );
	}

	public void setJavaFXEditorDbName( DbNameEditor value ) {
		javafxEditorDbName = value;
	}

	public SuffixCFLabel getJavaFXLabelSuffix() {
		if( javafxLabelSuffix == null ) {
			javafxLabelSuffix = new SuffixCFLabel();
		}
		return( javafxLabelSuffix );
	}

	public void setJavaFXLabelSuffix( SuffixCFLabel value ) {
		javafxLabelSuffix = value;
	}

	public SuffixEditor getJavaFXEditorSuffix() {
		if( javafxEditorSuffix == null ) {
			javafxEditorSuffix = new SuffixEditor();
		}
		return( javafxEditorSuffix );
	}

	public void setJavaFXEditorSuffix( SuffixEditor value ) {
		javafxEditorSuffix = value;
	}

	public IsRequiredCFLabel getJavaFXLabelIsRequired() {
		if( javafxLabelIsRequired == null ) {
			javafxLabelIsRequired = new IsRequiredCFLabel();
		}
		return( javafxLabelIsRequired );
	}

	public void setJavaFXLabelIsRequired( IsRequiredCFLabel value ) {
		javafxLabelIsRequired = value;
	}

	public IsRequiredEditor getJavaFXEditorIsRequired() {
		if( javafxEditorIsRequired == null ) {
			javafxEditorIsRequired = new IsRequiredEditor();
		}
		return( javafxEditorIsRequired );
	}

	public void setJavaFXEditorIsRequired( IsRequiredEditor value ) {
		javafxEditorIsRequired = value;
	}

	public IsXsdContainerCFLabel getJavaFXLabelIsXsdContainer() {
		if( javafxLabelIsXsdContainer == null ) {
			javafxLabelIsXsdContainer = new IsXsdContainerCFLabel();
		}
		return( javafxLabelIsXsdContainer );
	}

	public void setJavaFXLabelIsXsdContainer( IsXsdContainerCFLabel value ) {
		javafxLabelIsXsdContainer = value;
	}

	public IsXsdContainerEditor getJavaFXEditorIsXsdContainer() {
		if( javafxEditorIsXsdContainer == null ) {
			javafxEditorIsXsdContainer = new IsXsdContainerEditor();
		}
		return( javafxEditorIsXsdContainer );
	}

	public void setJavaFXEditorIsXsdContainer( IsXsdContainerEditor value ) {
		javafxEditorIsXsdContainer = value;
	}

	public void populateFields()
	{
		ICFBamRelationObj popObj;
		ICFBamRelationObj focus = getJavaFXFocusAsRelation();
		if( focus != null ) {
			popObj = (ICFBamRelationObj)(focus.getEdit());
			if( popObj == null ) {
				popObj = focus;
			}
		}
		else {
			popObj = null;
		}
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			javafxLookupDefSchemaObj = null;
		}
		else {
			javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)popObj.getOptionalLookupDefSchema( javafxIsInitializing );
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setReferencedObject( javafxLookupDefSchemaObj );
		}

		if( popObj == null ) {
			javafxLookupFromIndexObj = null;
		}
		else {
			javafxLookupFromIndexObj = (ICFBamIndexObj)popObj.getRequiredLookupFromIndex( javafxIsInitializing );
		}
		if( javafxReferenceLookupFromIndex != null ) {
			javafxReferenceLookupFromIndex.setReferencedObject( javafxLookupFromIndexObj );
		}

		if( popObj == null ) {
			javafxLookupToTableObj = null;
		}
		else {
			javafxLookupToTableObj = (ICFBamTableObj)popObj.getRequiredLookupToTable( javafxIsInitializing );
		}
		if( javafxReferenceLookupToTable != null ) {
			javafxReferenceLookupToTable.setReferencedObject( javafxLookupToTableObj );
		}

		if( popObj == null ) {
			javafxLookupToIndexObj = null;
		}
		else {
			javafxLookupToIndexObj = (ICFBamIndexObj)popObj.getRequiredLookupToIndex( javafxIsInitializing );
		}
		if( javafxReferenceLookupToIndex != null ) {
			javafxReferenceLookupToIndex.setReferencedObject( javafxLookupToIndexObj );
		}

		if( popObj == null ) {
			javafxLookupNarrowedObj = null;
		}
		else {
			javafxLookupNarrowedObj = (ICFBamRelationObj)popObj.getOptionalLookupNarrowed( javafxIsInitializing );
		}
		if( javafxEditorLookupNarrowed != null ) {
			javafxEditorLookupNarrowed.select( javafxLookupNarrowedObj );
		}

		if( popObj == null ) {
			getJavaFXEditorId().setInt64Value( null );
		}
		else {
			getJavaFXEditorId().setInt64Value( popObj.getRequiredId() );
		}

		if( popObj == null ) {
			getJavaFXEditorName().setStringValue( null );
		}
		else {
			getJavaFXEditorName().setStringValue( popObj.getRequiredName() );
		}

		if( popObj == null ) {
			getJavaFXEditorShortName().setStringValue( null );
		}
		else {
			getJavaFXEditorShortName().setStringValue( popObj.getOptionalShortName() );
		}

		if( popObj == null ) {
			getJavaFXEditorLabel().setStringValue( null );
		}
		else {
			getJavaFXEditorLabel().setStringValue( popObj.getOptionalLabel() );
		}

		if( popObj == null ) {
			getJavaFXEditorShortDescription().setStringValue( null );
		}
		else {
			getJavaFXEditorShortDescription().setStringValue( popObj.getOptionalShortDescription() );
		}

		if( popObj == null ) {
			getJavaFXEditorDescription().setStringValue( null );
		}
		else {
			getJavaFXEditorDescription().setStringValue( popObj.getOptionalDescription() );
		}

		if( popObj == null ) {
			getJavaFXEditorRelationType().setValue( null );
		}
		else {
			getJavaFXEditorRelationType().setValue(
				( popObj.getRequiredRelationType() == null )
					? null
					: popObj.getRequiredRelationType().toString() );
		}

		if( popObj == null ) {
			getJavaFXEditorDbName().setStringValue( null );
		}
		else {
			getJavaFXEditorDbName().setStringValue( popObj.getOptionalDbName() );
		}

		if( popObj == null ) {
			getJavaFXEditorSuffix().setStringValue( null );
		}
		else {
			getJavaFXEditorSuffix().setStringValue( popObj.getOptionalSuffix() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsRequired().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsRequired().setBooleanValue( popObj.getRequiredIsRequired() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsXsdContainer().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsXsdContainer().setBooleanValue( popObj.getRequiredIsXsdContainer() );
		}

		if( popObj == null ) {
			getJavaFXEditorPopTopDep().select( null );
			getJavaFXEditorPopSubDep1().select( null );
			getJavaFXEditorPopSubDep2().select( null );
			getJavaFXEditorPopSubDep3().select( null );
		}
		else {
			Iterator<ICFBamPopTopDepObj> iterPopTopDep = popObj.getOptionalComponentsPopDep().iterator();
			if( iterPopTopDep.hasNext() ) {
				ICFBamPopTopDepObj topDep = iterPopTopDep.next();
				getJavaFXEditorPopTopDep().select( topDep.getRequiredLookupRelation() );
				Iterator<ICFBamPopSubDep1Obj> iterPopSubDep1 = topDep.getOptionalComponentsPopDep().iterator();
				if( iterPopSubDep1.hasNext() ) {
					ICFBamPopSubDep1Obj subDep1 = iterPopSubDep1.next();
					getJavaFXEditorPopSubDep1().select( subDep1.getRequiredLookupRelation() );
					Iterator<ICFBamPopSubDep2Obj> iterPopSubDep2 = subDep1.getOptionalComponentsPopDep().iterator();
					if( iterPopSubDep2.hasNext() ) {
						ICFBamPopSubDep2Obj subDep2 = iterPopSubDep2.next();
						getJavaFXEditorPopSubDep2().select( subDep2.getRequiredLookupRelation() );
						Iterator<ICFBamPopSubDep3Obj> iterPopSubDep3 = subDep2.getOptionalComponentsPopDep().iterator();
						if( iterPopSubDep3.hasNext() ) {
							ICFBamPopSubDep3Obj subDep3 = iterPopSubDep3.next();
							getJavaFXEditorPopSubDep3().select( subDep3.getRequiredLookupRelation() );
						}
						else {
							getJavaFXEditorPopSubDep3().select( null );
						}
					}
					else {
						getJavaFXEditorPopSubDep2().select( null );
						getJavaFXEditorPopSubDep3().select( null );
					}
				}
				else {
					getJavaFXEditorPopSubDep1().select( null );
					getJavaFXEditorPopSubDep2().select( null );
					getJavaFXEditorPopSubDep3().select( null );
				}
			}
			else {
				getJavaFXEditorPopTopDep().select( null );
				getJavaFXEditorPopSubDep1().select( null );
				getJavaFXEditorPopSubDep2().select( null );
				getJavaFXEditorPopSubDep3().select( null );
			}
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamRelationObj focus = getJavaFXFocusAsRelation();
		ICFBamRelationEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamRelationEditObj)(focus.getEdit());
		}
		else {
			editObj = null;
		}
		if( editObj == null ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Pane is unfocused or is not editing the focus object" );
		}

		javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)( javafxReferenceLookupDefSchema.getReferencedObject() );
		editObj.setOptionalLookupDefSchema( javafxLookupDefSchemaObj );

		javafxLookupFromIndexObj = (ICFBamIndexObj)( javafxReferenceLookupFromIndex.getReferencedObject() );
		editObj.setRequiredLookupFromIndex( javafxLookupFromIndexObj );

		javafxLookupToTableObj = (ICFBamTableObj)( javafxReferenceLookupToTable.getReferencedObject() );
		editObj.setRequiredLookupToTable( javafxLookupToTableObj );

		javafxLookupToIndexObj = (ICFBamIndexObj)( javafxReferenceLookupToIndex.getReferencedObject() );
		editObj.setRequiredLookupToIndex( javafxLookupToIndexObj );

		javafxLookupNarrowedObj = javafxEditorLookupNarrowed.getValue().getRelation();
		editObj.setOptionalLookupNarrowed( javafxLookupNarrowedObj );

		editObj.setRequiredName( getJavaFXEditorName().getStringValue() );

		editObj.setOptionalShortName( getJavaFXEditorShortName().getStringValue() );

		editObj.setOptionalLabel( getJavaFXEditorLabel().getStringValue() );

		editObj.setOptionalShortDescription( getJavaFXEditorShortDescription().getStringValue() );

		editObj.setOptionalDescription( getJavaFXEditorDescription().getStringValue() );

		String strrelationtype = getJavaFXEditorRelationType().getValue();
		ICFBamSchema.RelationTypeEnum currelationtype = CFBamSchema.parseRelationTypeEnum( "RelationType", strrelationtype );
		editObj.setRequiredRelationType( currelationtype );

		editObj.setOptionalDbName( getJavaFXEditorDbName().getStringValue() );

		editObj.setOptionalSuffix( getJavaFXEditorSuffix().getStringValue() );

		editObj.setRequiredIsRequired( getJavaFXEditorIsRequired().getBooleanValue() );

		editObj.setRequiredIsXsdContainer( getJavaFXEditorIsXsdContainer().getBooleanValue() );

		if( ( editObj.getRequiredName() == null ) || ( editObj.getRequiredName().length() <= 0 ) ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"You must specify a Name for the Relation" );
		}

		if( editObj.getRequiredLookupFromIndex() == null ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"You must select a From Index" );
		}

		if( editObj.getRequiredLookupToIndex() == null ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"You must select a To Index" );
		}

		if( editObj.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Unknown ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"You must select a proper Relation Type, not \"Unknown\"" );
		}

		if( editObj.getRequiredIsXsdContainer() && ( editObj.getRequiredRelationType() != ICFBamSchema.RelationTypeEnum.Components ) ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Only Components Relations can specify IsXsdContainer to be true" );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamRelationObj focus = getJavaFXFocusAsRelation();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamRelationEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamRelationEditObj)focus.getEdit();
		}
		else {
			editObj = null;
		}
		switch( value ) {
			case Unknown:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
						}
						break;
				}
				break;
			case Add:
				switch( oldValue ) {
					case Unknown:
					case Add:
					case View:
						if( editObj == null ) {
							if( focus != null ) {
								if( ! focus.getIsNew() ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Transitioning to PaneMode Add requires Focus.getIsNew() to be true" );
								}
								editObj = (ICFBamRelationEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Expected beginEdit() to return a new edition of the focus object" );
								}
							}
							else {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"focus" );
							}
						}
						break;
					case Edit:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode Edit to Add" );
					case Update:
						if( ( editObj == null ) || ( ! editObj.getIsNew() ) ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								"Cannot transition PaneMode Update to Add" );
						}
						break;
					case Delete:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode Delete to Add" );
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode default to Add" );
				}
				break;
			case View:
				switch( oldValue ) {
					case Unknown:
						break;
					case View:
						break;
					case Edit:
						break;
					case Update:
						break;
					case Delete:
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode " + oldValue + " to View" );
				}
				if( editObj != null ) {
					editObj.endEdit();
				}
				break;
			case Edit:
				switch( oldValue ) {
					case Unknown:
						if( editObj == null ) {
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Update:
						if( editObj == null ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								"Cannot transition PaneMode " + oldValue + " to Edit" ); 
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode " + oldValue + " to Edit" ); 
				}
				break;
			case Update:
				if( ( oldValue != CFPane.PaneMode.Edit ) && ( oldValue != CFPane.PaneMode.Add ) ) {
					throw new CFLibUsageException( getClass(),
						S_ProcName,
						"Cannot transition from mode " + oldValue + " to Update" );
				}
				super.setPaneMode( value );
				if( editObj != null ) {
					postFields();
					if( editObj.getIsNew() ) {
						focus = (ICFBamRelationObj)editObj.create();
						setJavaFXFocus( focus );
					}
					else {
						editObj.update();
					}
					ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
					ICFBamTenantObj tenantObj = (ICFBamTenantObj)getEffFocusAsRelation().getRequiredOwnerTenant();
					ICFBamRelationObj relation;
					ICFBamPopTopDepObj popTopDep;
					ICFBamPopTopDepEditObj editTopDep;
					Iterator<ICFBamPopTopDepObj> iterTopDep;
					ICFBamPopSubDep1Obj popSubDep1;
					ICFBamPopSubDep1EditObj editSubDep1;
					Iterator<ICFBamPopSubDep1Obj> iterSubDep1;
					ICFBamPopSubDep2Obj popSubDep2;
					ICFBamPopSubDep2EditObj editSubDep2;
					Iterator<ICFBamPopSubDep2Obj> iterSubDep2;
					ICFBamPopSubDep3Obj popSubDep3;
					ICFBamPopSubDep3EditObj editSubDep3;
					Iterator<ICFBamPopSubDep3Obj> iterSubDep3;
					CFBamCustEditorRelationEntry topDepEntry = getJavaFXEditorPopTopDep().getValue();
					if( topDepEntry == null ) {
						iterTopDep = editObj.getOptionalComponentsPopDep().iterator();
						if( iterTopDep.hasNext() ) {
							popTopDep = iterTopDep.next();
							editTopDep = (ICFBamPopTopDepEditObj)popTopDep.beginEdit();
							editTopDep.deleteInstance();
							editTopDep = null;
						}
					}
					else {
						relation = topDepEntry.getRelation();
						if( relation != null ) {
							iterTopDep = editObj.getOptionalComponentsPopDep().iterator();
							if( iterTopDep.hasNext() ) {
								popTopDep = iterTopDep.next();
								if( popTopDep.getRequiredLookupRelation() != relation ) {
									editTopDep = (ICFBamPopTopDepEditObj)popTopDep.beginEdit();
									editTopDep.setRequiredLookupRelation( relation );
									editTopDep.update();
									editTopDep = null;
								}
							}
							else {
								popTopDep = schemaObj.getPopTopDepTableObj().newInstance();
								editTopDep = (ICFBamPopTopDepEditObj)popTopDep.beginEdit();
								editTopDep.setRequiredOwnerTenant( tenantObj );
								editTopDep.setRequiredContainerContRelation( getJavaFXFocusAsRelation() );
								editTopDep.setRequiredLookupRelation( relation );
								popTopDep = (ICFBamPopTopDepObj)editTopDep.create();
								editTopDep = null;
							}
							CFBamCustEditorRelationEntry subDep1Entry = getJavaFXEditorPopSubDep1().getValue();
							if( subDep1Entry == null ) {
								iterSubDep1 = popTopDep.getOptionalComponentsPopDep().iterator();
								if( iterSubDep1.hasNext() ) {
									popSubDep1 = iterSubDep1.next();
									editSubDep1 = (ICFBamPopSubDep1EditObj)popSubDep1.beginEdit();
									editSubDep1.deleteInstance();
									editSubDep1 = null;
								}
							}
							else {
								relation = subDep1Entry.getRelation();
								if( relation != null ) {
									iterSubDep1 = popTopDep.getOptionalComponentsPopDep().iterator();
									if( iterSubDep1.hasNext() ) {
										popSubDep1 = iterSubDep1.next();
										if( popSubDep1.getRequiredLookupRelation() != relation ) {
											editSubDep1 = (ICFBamPopSubDep1EditObj)popSubDep1.beginEdit();
											editSubDep1.setRequiredLookupRelation( relation );
											editSubDep1.update();
											editSubDep1 = null;
										}
									}
									else {
										popSubDep1 = schemaObj.getPopSubDep1TableObj().newInstance();
										editSubDep1 = (ICFBamPopSubDep1EditObj)popSubDep1.beginEdit();
										editSubDep1.setRequiredOwnerTenant( tenantObj );
										editSubDep1.setRequiredContainerContPopTopDep( popTopDep );
										editSubDep1.setRequiredLookupRelation( relation );
										popSubDep1 = (ICFBamPopSubDep1Obj)editSubDep1.create();
										editSubDep1 = null;
									}
									CFBamCustEditorRelationEntry subDep2Entry = getJavaFXEditorPopSubDep2().getValue();
									if( subDep2Entry == null ) {
										iterSubDep2 = popSubDep1.getOptionalComponentsPopDep().iterator();
										if( iterSubDep2.hasNext() ) {
											popSubDep2 = iterSubDep2.next();
											editSubDep2 = (ICFBamPopSubDep2EditObj)popSubDep2.beginEdit();
											editSubDep2.deleteInstance();
											editSubDep2 = null;
										}
									}
									else {
										relation = subDep2Entry.getRelation();
										if( relation != null ) {
											iterSubDep2 = popSubDep1.getOptionalComponentsPopDep().iterator();
											if( iterSubDep2.hasNext() ) {
												popSubDep2 = iterSubDep2.next();
												if( popSubDep2.getRequiredLookupRelation() != relation ) {
													editSubDep2 = (ICFBamPopSubDep2EditObj)popSubDep2.beginEdit();
													editSubDep2.setRequiredLookupRelation( relation );
													editSubDep2.update();
													editSubDep2 = null;
												}
											}
											else {
												popSubDep2 = schemaObj.getPopSubDep2TableObj().newInstance();
												editSubDep2 = (ICFBamPopSubDep2EditObj)popSubDep2.beginEdit();
												editSubDep2.setRequiredOwnerTenant( tenantObj );
												editSubDep2.setRequiredLookupRelation( relation );
												editSubDep2.setRequiredContainerPopSubDep1( popSubDep1 );
												popSubDep2 = (ICFBamPopSubDep2Obj)editSubDep2.create();
												editSubDep2 = null;
											}
											CFBamCustEditorRelationEntry subDep3Entry = getJavaFXEditorPopSubDep3().getValue();
											if( subDep3Entry == null ) {
												iterSubDep3 = popSubDep2.getOptionalComponentsPopDep().iterator();
												if( iterSubDep3.hasNext() ) {
													popSubDep3 = iterSubDep3.next();
													editSubDep3 = (ICFBamPopSubDep3EditObj)popSubDep3.beginEdit();
													editSubDep3.deleteInstance();
													editSubDep3 = null;
												}
											}
											else {
												relation = subDep3Entry.getRelation();
												if( relation != null ) {
													iterSubDep3 = popSubDep2.getOptionalComponentsPopDep().iterator();
													if( iterSubDep3.hasNext() ) {
														popSubDep3 = iterSubDep3.next();
														if( popSubDep3.getRequiredLookupRelation() != relation ) {
															editSubDep3 = (ICFBamPopSubDep3EditObj)popSubDep3.beginEdit();
															editSubDep3.setRequiredLookupRelation( relation );
															editSubDep3.update();
															editSubDep3 = null;
														}
													}
													else {
														popSubDep3 = schemaObj.getPopSubDep3TableObj().newInstance();
														editSubDep3 = (ICFBamPopSubDep3EditObj)popSubDep3.beginEdit();
														editSubDep3.setRequiredOwnerTenant( tenantObj );
														editSubDep3.setRequiredLookupRelation( relation );
														editSubDep3.setRequiredContainerPopSubDep2( popSubDep2 );
														popSubDep3 = (ICFBamPopSubDep3Obj)editSubDep3.create();
														editSubDep3 = null;
													}
												}
												else {
													iterSubDep3 = popSubDep2.getOptionalComponentsPopDep().iterator();
													if( iterSubDep3.hasNext() ) {
														popSubDep3 = iterSubDep3.next();
														editSubDep3 = (ICFBamPopSubDep3EditObj)popSubDep3.beginEdit();
														editSubDep3.deleteInstance();
														editSubDep3 = null;
													}
												}
											}
										}
										else {
											iterSubDep2 = popSubDep1.getOptionalComponentsPopDep().iterator();
											if( iterSubDep2.hasNext() ) {
												popSubDep2 = iterSubDep2.next();
												editSubDep2 = (ICFBamPopSubDep2EditObj)popSubDep2.beginEdit();
												editSubDep2.deleteInstance();
												editSubDep2 = null;
											}
										}
									}
								}
								else {
									iterSubDep1 = popTopDep.getOptionalComponentsPopDep().iterator();
									if( iterSubDep1.hasNext() ) {
										popSubDep1 = iterSubDep1.next();
										editSubDep1 = (ICFBamPopSubDep1EditObj)popSubDep1.beginEdit();
										editSubDep1.deleteInstance();
										editSubDep1 = null;
									}
								}
							}
						}
						else {
							iterTopDep = editObj.getOptionalComponentsPopDep().iterator();
							if( iterTopDep.hasNext() ) {
								popTopDep = iterTopDep.next();
								editTopDep = (ICFBamPopTopDepEditObj)popTopDep.beginEdit();
								editTopDep.deleteInstance();
								editTopDep = null;
							}
						}
					}
					editObj.endEdit();
					editObj = null;
				}
				setPaneMode( CFPane.PaneMode.View );
				break;
			case Delete:
				switch( oldValue ) {
					case View:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamRelationEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Expected beginEdit() to return a new edition of the focus object" );
								}
							}
						}
						break;
					case Edit:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamRelationEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Expected beginEdit() to return a new edition of the focus object" );
								}
							}
						}
						break;
					case Update:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode Update to Delete" ); 
					case Delete:
						if( editObj == null ) {
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode " + oldValue + " to Delete" ); 
				}
				editObj.deleteInstance();
				editObj = null;
				setJavaFXFocus( null );
				setPaneMode( CFPane.PaneMode.Unknown );
				break;
			default:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
						}
						break;
				}
				break;
		}
		super.setPaneMode( value );
		populateFields();
		adjustComponentEnableStates();
	}

	public void adjustComponentEnableStates() {
		CFPane.PaneMode mode = getPaneMode();
		boolean isEditing;
		switch( mode ) {
			case Unknown:
			case View:
			case Delete:
				isEditing = false;
				break;
			case Add:
			case Edit:
			case Update:
				isEditing = true;
				break;
			default:
				isEditing = false;
				break;
		}
		if( isEditing ) {
			ICFBamRelationObj focus = getJavaFXFocusAsRelation();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setCustomDisable( true );
		}
		if( javafxReferenceLookupFromIndex != null ) {
			javafxReferenceLookupFromIndex.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupToTable != null ) {
			javafxReferenceLookupToTable.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupToIndex != null ) {
			javafxReferenceLookupToIndex.setCustomDisable( ! isEditing );
		}
		if( javafxEditorLookupNarrowed != null ) {
			javafxEditorLookupNarrowed.setDisable( ! isEditing );
		}
		if( javafxEditorId != null ) {
			javafxEditorId.setDisable( true );
		}
		if( javafxEditorName != null ) {
			javafxEditorName.setDisable( ! isEditing );
		}
		if( javafxEditorShortName != null ) {
			javafxEditorShortName.setDisable( ! isEditing );
		}
		if( javafxEditorLabel != null ) {
			javafxEditorLabel.setDisable( ! isEditing );
		}
		if( javafxEditorShortDescription != null ) {
			javafxEditorShortDescription.setDisable( ! isEditing );
		}
		if( javafxEditorDescription != null ) {
			javafxEditorDescription.setDisable( ! isEditing );
		}
		if( javafxEditorRelationType != null ) {
			javafxEditorRelationType.setDisable( ! isEditing );
		}
		if( javafxEditorDbName != null ) {
			javafxEditorDbName.setDisable( ! isEditing );
		}
		if( javafxEditorSuffix != null ) {
			javafxEditorSuffix.setDisable( ! isEditing );
		}
		if( javafxEditorIsRequired != null ) {
			javafxEditorIsRequired.setDisable( ! isEditing );
		}
		if( javafxEditorIsXsdContainer != null ) {
			javafxEditorIsXsdContainer.setDisable( ! isEditing );
		}
		if( javafxEditorPopTopDep != null ) {
			javafxEditorPopTopDep.setDisable( ! isEditing );
		}
		if( javafxEditorPopSubDep1 != null ) {
			javafxEditorPopSubDep1.setDisable( ! isEditing );
		}
		if( javafxEditorPopSubDep2 != null ) {
			javafxEditorPopSubDep2.setDisable( ! isEditing );
		}
		if( javafxEditorPopSubDep3 != null ) {
			javafxEditorPopSubDep3.setDisable( ! isEditing );
		}
	}
}
