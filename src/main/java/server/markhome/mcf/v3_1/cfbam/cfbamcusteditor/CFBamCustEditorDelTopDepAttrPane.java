// Description: Java 25 JavaFX Attribute Pane implementation for DelTopDep.

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
 *	CFBamJavaFXDelTopDepAttrPane JavaFX Attribute Pane implementation
 *	for DelTopDep.
 */
public class CFBamCustEditorDelTopDepAttrPane
extends CFGridPane
implements ICFBamJavaFXDelTopDepPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

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
				ICFBamDelTopDepObj cur = getJavaFXFocusAsDelTopDep();
				if( cur != null ) {
					ICFBamDelTopDepEditObj editObj = (ICFBamDelTopDepEditObj)cur.getEdit();
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
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamDelTopDepObj focus = getJavaFXFocusAsDelTopDep();
			ICFBamDelTopDepEditObj editObj  = (ICFBamDelTopDepEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
			List<ICFBamSchemaDefObj> listOfSchemaDef = null;
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamDelTopDepObj focus = getJavaFXFocusAsDelTopDep();
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

	protected class DelTopDepCFLabel
		extends CFLabel
	{
		public DelTopDepCFLabel() {
			super();
			setText( "Delete Top" );
		}
	}

	protected ICFBamDelTopDepObj getEffJavaFocus() {
		ICFBamDelTopDepObj obj = (ICFBamDelTopDepObj)getJavaFXFocusAsDelTopDep().getEdit();
		if( obj == null ) {
			obj = getJavaFXFocusAsDelTopDep();
		}
		return( obj );
	}

	protected class DelTopDepCFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public DelTopDepCFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( getEffJavaFocus().getRequiredContainerTable() ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorDelSubDep1().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep1().getSelectionModel().select( 0 );
						getJavaFXEditorDelSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorDelSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep3().getSelectionModel().select( 0 );
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
						getJavaFXEditorDelSubDep1().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorDelSubDep1().getSelectionModel().select( 0 );
						getJavaFXEditorDelSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorDelSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep3().getSelectionModel().select( 0 );
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

	protected class DelSubDep1CFLabel
		extends CFLabel
	{
		public DelSubDep1CFLabel() {
			super();
			setText( "Delete Level 1" );
		}
	}

	protected class DelSubDep1CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public DelSubDep1CFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorDelSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorDelSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep3().getSelectionModel().select( 0 );
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
						getJavaFXEditorDelSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorDelSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorDelSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep3().getSelectionModel().select( 0 );
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

	protected class DelSubDep2CFLabel
		extends CFLabel
	{
		public DelSubDep2CFLabel() {
			super();
			setText( "Delete Level 2" );
		}
	}

	protected class DelSubDep2CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public DelSubDep2CFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorDelSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorDelSubDep3().getSelectionModel().select( 0 );
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
						getJavaFXEditorDelSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorDelSubDep3().getSelectionModel().select( 0 );
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

	protected class DelSubDep3CFLabel
		extends CFLabel
	{
		public DelSubDep3CFLabel() {
			super();
			setText( "Delete Level 3" );
		}
	}

	protected class DelSubDep3CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public DelSubDep3CFEditor() {
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

	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected DelTopDepCFLabel javafxLabelDelTopDep = null;
	protected DelTopDepCFEditor javafxEditorDelTopDep = null;
	protected DelSubDep1CFLabel javafxLabelDelSubDep1 = null;
	protected DelSubDep1CFEditor javafxEditorDelSubDep1 = null;
	protected DelSubDep2CFLabel javafxLabelDelSubDep2 = null;
	protected DelSubDep2CFEditor javafxEditorDelSubDep2 = null;
	protected DelSubDep3CFLabel javafxLabelDelSubDep3 = null;
	protected DelSubDep3CFEditor javafxEditorDelSubDep3 = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;

	public CFBamCustEditorDelTopDepAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamDelTopDepObj argFocus ) {
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
		setJavaFXFocusAsDelTopDep( argFocus );
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

		label = getJavaFXLabelDelTopDep();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorDelTopDep();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelDelSubDep1();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorDelSubDep1();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelDelSubDep2();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorDelSubDep2();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelDelSubDep3();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorDelSubDep3();
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
		if( ( value == null ) || ( value instanceof ICFBamDelTopDepObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamDelTopDepObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamDelTopDepObj getJavaFXFocusAsDelTopDep() {
		return( (ICFBamDelTopDepObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsDelTopDep( ICFBamDelTopDepObj value ) {
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

	public DelTopDepCFLabel getJavaFXLabelDelTopDep() {
		if( javafxLabelDelTopDep == null ) {
			javafxLabelDelTopDep = new DelTopDepCFLabel();
		}
		return( javafxLabelDelTopDep );
	}

	public void setJavaFXLabelDelTopDep( DelTopDepCFLabel value ) {
		javafxLabelDelTopDep = value;
	}

	public DelTopDepCFEditor getJavaFXEditorDelTopDep() {
		if( javafxEditorDelTopDep == null ) {
			javafxEditorDelTopDep = new DelTopDepCFEditor();
		}
		return( javafxEditorDelTopDep );
	}

	public void setJavaFXEditorDelTopDep( DelTopDepCFEditor value ) {
		javafxEditorDelTopDep = value;
	}

	public DelSubDep1CFLabel getJavaFXLabelDelSubDep1() {
		if( javafxLabelDelSubDep1 == null ) {
			javafxLabelDelSubDep1 = new DelSubDep1CFLabel();
		}
		return( javafxLabelDelSubDep1 );
	}

	public void setJavaFXLabelDelSubDep1( DelSubDep1CFLabel value ) {
		javafxLabelDelSubDep1 = value;
	}

	public DelSubDep1CFEditor getJavaFXEditorDelSubDep1() {
		if( javafxEditorDelSubDep1 == null ) {
			javafxEditorDelSubDep1 = new DelSubDep1CFEditor();
		}
		return( javafxEditorDelSubDep1 );
	}

	public void setJavaFXEditorDelSubDep1( DelSubDep1CFEditor value ) {
		javafxEditorDelSubDep1 = value;
	}

	public DelSubDep2CFLabel getJavaFXLabelDelSubDep2() {
		if( javafxLabelDelSubDep2 == null ) {
			javafxLabelDelSubDep2 = new DelSubDep2CFLabel();
		}
		return( javafxLabelDelSubDep2 );
	}

	public void setJavaFXLabelDelSubDep2( DelSubDep2CFLabel value ) {
		javafxLabelDelSubDep2 = value;
	}

	public DelSubDep2CFEditor getJavaFXEditorDelSubDep2() {
		if( javafxEditorDelSubDep2 == null ) {
			javafxEditorDelSubDep2 = new DelSubDep2CFEditor();
		}
		return( javafxEditorDelSubDep2 );
	}

	public void setJavaFXEditorDelSubDep2( DelSubDep2CFEditor value ) {
		javafxEditorDelSubDep2 = value;
	}

	public DelSubDep3CFLabel getJavaFXLabelDelSubDep3() {
		if( javafxLabelDelSubDep3 == null ) {
			javafxLabelDelSubDep3 = new DelSubDep3CFLabel();
		}
		return( javafxLabelDelSubDep3 );
	}

	public void setJavaFXLabelDelSubDep3( DelSubDep3CFLabel value ) {
		javafxLabelDelSubDep3 = value;
	}

	public DelSubDep3CFEditor getJavaFXEditorDelSubDep3() {
		if( javafxEditorDelSubDep3 == null ) {
			javafxEditorDelSubDep3 = new DelSubDep3CFEditor();
		}
		return( javafxEditorDelSubDep3 );
	}

	public void setJavaFXEditorDelSubDep3( DelSubDep3CFEditor value ) {
		javafxEditorDelSubDep3 = value;
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

	public void populateFields()
	{
		ICFBamDelTopDepObj delObj;
		ICFBamDelTopDepObj focus = getJavaFXFocusAsDelTopDep();
		if( focus != null ) {
			delObj = (ICFBamDelTopDepObj)(focus.getEdit());
			if( delObj == null ) {
				delObj = focus;
			}
		}
		else {
			delObj = null;
		}
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			delObj = null;
		}

		if( delObj == null ) {
			javafxLookupDefSchemaObj = null;
		}
		else {
			javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)delObj.getOptionalLookupDefSchema( javafxIsInitializing );
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setReferencedObject( javafxLookupDefSchemaObj );
		}

		if( delObj == null ) {
			getJavaFXEditorId().setInt64Value( null );
		}
		else {
			getJavaFXEditorId().setInt64Value( delObj.getRequiredId() );
		}

		if( delObj == null ) {
			getJavaFXEditorName().setStringValue( null );
		}
		else {
			getJavaFXEditorName().setStringValue( delObj.getRequiredName() );
		}

		if( delObj == null ) {
			getJavaFXEditorDelTopDep().select( null );
			getJavaFXEditorDelSubDep1().select( null );
			getJavaFXEditorDelSubDep2().select( null );
			getJavaFXEditorDelSubDep3().select( null );
		}
		else {
			getJavaFXEditorDelTopDep().select( delObj.getRequiredLookupRelation() );
			Iterator<ICFBamDelSubDep1Obj> iterDelSubDep1 = delObj.getOptionalComponentsDelDep().iterator();
			if( iterDelSubDep1.hasNext() ) {
				ICFBamDelSubDep1Obj subDep1 = iterDelSubDep1.next();
				getJavaFXEditorDelSubDep1().select( subDep1.getRequiredLookupRelation() );
				Iterator<ICFBamDelSubDep2Obj> iterDelSubDep2 = subDep1.getOptionalComponentsDelDep().iterator();
				if( iterDelSubDep2.hasNext() ) {
					ICFBamDelSubDep2Obj subDep2 = iterDelSubDep2.next();
					getJavaFXEditorDelSubDep2().select( subDep2.getRequiredLookupRelation() );
					Iterator<ICFBamDelSubDep3Obj> iterDelSubDep3 = subDep2.getOptionalComponentsDelDep().iterator();
					if( iterDelSubDep3.hasNext() ) {
						ICFBamDelSubDep3Obj subDep3 = iterDelSubDep3.next();
						getJavaFXEditorDelSubDep3().select( subDep3.getRequiredLookupRelation() );
					}
					else {
						getJavaFXEditorDelSubDep3().select( null );
					}
				}
				else {
					getJavaFXEditorDelSubDep2().select( null );
					getJavaFXEditorDelSubDep3().select( null );
				}
			}
			else {
				getJavaFXEditorDelSubDep1().select( null );
				getJavaFXEditorDelSubDep2().select( null );
				getJavaFXEditorDelSubDep3().select( null );
			}
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamDelTopDepObj focus = getJavaFXFocusAsDelTopDep();
		ICFBamDelTopDepEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamDelTopDepEditObj)(focus.getEdit());
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

		editObj.setRequiredName( getJavaFXEditorName().getStringValue() );

		if( editObj.getRequiredName().length() <= 0 ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"You must specify a Name" );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamDelTopDepObj focus = getJavaFXFocusAsDelTopDep();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamDelTopDepEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamDelTopDepEditObj)focus.getEdit();
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
								editObj = (ICFBamDelTopDepEditObj)focus.beginEdit();
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
							editObj = (ICFBamDelTopDepEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFBamDelTopDepEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFBamDelTopDepEditObj)focus.beginEdit();
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
					ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
					ICFBamTenantObj tenantObj = (ICFBamTenantObj)getJavaFXFocusAsDelTopDep().getRequiredOwnerTenant();
					ICFBamRelationObj relation;
					ICFBamDelSubDep1Obj delSubDep1;
					ICFBamDelSubDep1EditObj editSubDep1;
					Iterator<ICFBamDelSubDep1Obj> iterSubDep1;
					ICFBamDelSubDep2Obj delSubDep2;
					ICFBamDelSubDep2EditObj editSubDep2;
					Iterator<ICFBamDelSubDep2Obj> iterSubDep2;
					ICFBamDelSubDep3Obj delSubDep3;
					ICFBamDelSubDep3EditObj editSubDep3;
					Iterator<ICFBamDelSubDep3Obj> iterSubDep3;
					CFBamCustEditorRelationEntry topDepEntry = getJavaFXEditorDelTopDep().getValue();
					if( topDepEntry == null ) {
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"You must select the top relationship before you can save your data" );
					}
					else {
						relation = topDepEntry.getRelation();
						if( relation != null ) {
							editObj.setRequiredLookupRelation( relation );
							if( editObj.getIsNew() ) {
								focus = (ICFBamDelTopDepObj)editObj.create();
							}
							else {
								editObj.update();
							}
							CFBamCustEditorRelationEntry subDep1Entry = getJavaFXEditorDelSubDep1().getValue();
							if( subDep1Entry == null ) {
								iterSubDep1 = editObj.getOptionalComponentsDelDep().iterator();
								if( iterSubDep1.hasNext() ) {
									delSubDep1 = iterSubDep1.next();
									editSubDep1 = (ICFBamDelSubDep1EditObj)delSubDep1.beginEdit();
									editSubDep1.deleteInstance();
									editSubDep1 = null;
								}
							}
							else {
								relation = subDep1Entry.getRelation();
								if( relation != null ) {
									iterSubDep1 = editObj.getOptionalComponentsDelDep().iterator();
									if( iterSubDep1.hasNext() ) {
										delSubDep1 = iterSubDep1.next();
										if( delSubDep1.getRequiredLookupRelation() != relation ) {
											editSubDep1 = (ICFBamDelSubDep1EditObj)delSubDep1.beginEdit();
											editSubDep1.setRequiredLookupRelation( relation );
											editSubDep1.update();
											editSubDep1 = null;
										}
									}
									else {
										delSubDep1 = schemaObj.getDelSubDep1TableObj().newInstance();
										editSubDep1 = (ICFBamDelSubDep1EditObj)delSubDep1.beginEdit();
										editSubDep1.setRequiredOwnerTenant( tenantObj );
										editSubDep1.setRequiredContainerDelTopDep( editObj );
										editSubDep1.setRequiredLookupRelation( relation );
										delSubDep1 = (ICFBamDelSubDep1Obj)editSubDep1.create();
										editSubDep1 = null;
									}
									CFBamCustEditorRelationEntry subDep2Entry = getJavaFXEditorDelSubDep2().getValue();
									if( subDep2Entry == null ) {
										iterSubDep2 = delSubDep1.getOptionalComponentsDelDep().iterator();
										if( iterSubDep2.hasNext() ) {
											delSubDep2 = iterSubDep2.next();
											editSubDep2 = (ICFBamDelSubDep2EditObj)delSubDep2.beginEdit();
											editSubDep2.deleteInstance();
											editSubDep2 = null;
										}
									}
									else {
										relation = subDep2Entry.getRelation();
										if( relation != null ) {
											iterSubDep2 = delSubDep1.getOptionalComponentsDelDep().iterator();
											if( iterSubDep2.hasNext() ) {
												delSubDep2 = iterSubDep2.next();
												if( delSubDep2.getRequiredLookupRelation() != relation ) {
													editSubDep2 = (ICFBamDelSubDep2EditObj)delSubDep2.beginEdit();
													editSubDep2.setRequiredLookupRelation( relation );
													editSubDep2.update();
													editSubDep2 = null;
												}
											}
											else {
												delSubDep2 = schemaObj.getDelSubDep2TableObj().newInstance();
												editSubDep2 = (ICFBamDelSubDep2EditObj)delSubDep2.beginEdit();
												editSubDep2.setRequiredOwnerTenant( tenantObj );
												editSubDep2.setRequiredLookupRelation( relation );
												editSubDep2.setRequiredContainerDelSubDep1( delSubDep1 );
												delSubDep2 = (ICFBamDelSubDep2Obj)editSubDep2.create();
												editSubDep2 = null;
											}
											CFBamCustEditorRelationEntry subDep3Entry = getJavaFXEditorDelSubDep3().getValue();
											if( subDep3Entry == null ) {
												iterSubDep3 = delSubDep2.getOptionalComponentsDelDep().iterator();
												if( iterSubDep3.hasNext() ) {
													delSubDep3 = iterSubDep3.next();
													editSubDep3 = (ICFBamDelSubDep3EditObj)delSubDep3.beginEdit();
													editSubDep3.deleteInstance();
													editSubDep3 = null;
												}
											}
											else {
												relation = subDep3Entry.getRelation();
												if( relation != null ) {
													iterSubDep3 = delSubDep2.getOptionalComponentsDelDep().iterator();
													if( iterSubDep3.hasNext() ) {
														delSubDep3 = iterSubDep3.next();
														if( delSubDep3.getRequiredLookupRelation() != relation ) {
															editSubDep3 = (ICFBamDelSubDep3EditObj)delSubDep3.beginEdit();
															editSubDep3.setRequiredLookupRelation( relation );
															editSubDep3.update();
															editSubDep3 = null;
														}
													}
													else {
														delSubDep3 = schemaObj.getDelSubDep3TableObj().newInstance();
														editSubDep3 = (ICFBamDelSubDep3EditObj)delSubDep3.beginEdit();
														editSubDep3.setRequiredOwnerTenant( tenantObj );
														editSubDep3.setRequiredLookupRelation( relation );
														editSubDep3.setRequiredContainerDelSubDep2( delSubDep2 );
														delSubDep3 = (ICFBamDelSubDep3Obj)editSubDep3.create();
														editSubDep3 = null;
													}
												}
												else {
													iterSubDep3 = delSubDep2.getOptionalComponentsDelDep().iterator();
													if( iterSubDep3.hasNext() ) {
														delSubDep3 = iterSubDep3.next();
														editSubDep3 = (ICFBamDelSubDep3EditObj)delSubDep3.beginEdit();
														editSubDep3.deleteInstance();
														editSubDep3 = null;
													}
												}
											}
										}
										else {
											iterSubDep2 = delSubDep1.getOptionalComponentsDelDep().iterator();
											if( iterSubDep2.hasNext() ) {
												delSubDep2 = iterSubDep2.next();
												editSubDep2 = (ICFBamDelSubDep2EditObj)delSubDep2.beginEdit();
												editSubDep2.deleteInstance();
												editSubDep2 = null;
											}
										}
									}
								}
								else {
									iterSubDep1 = editObj.getOptionalComponentsDelDep().iterator();
									if( iterSubDep1.hasNext() ) {
										delSubDep1 = iterSubDep1.next();
										editSubDep1 = (ICFBamDelSubDep1EditObj)delSubDep1.beginEdit();
										editSubDep1.deleteInstance();
										editSubDep1 = null;
									}
								}
							}
						}
						else {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								"You must select the top relationship before you can save your data" );
						}
					}
					setJavaFXFocus( focus );
					editObj = null;
				}
				setPaneMode( CFPane.PaneMode.View );
				break;
			case Delete:
				switch( oldValue ) {
					case View:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamDelTopDepEditObj)focus.beginEdit();
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
								editObj = (ICFBamDelTopDepEditObj)focus.beginEdit();
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
							editObj = (ICFBamDelTopDepEditObj)focus.beginEdit();
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
			ICFBamDelTopDepObj focus = getJavaFXFocusAsDelTopDep();
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
		if( javafxEditorId != null ) {
			javafxEditorId.setDisable( true );
		}
		if( javafxEditorName != null ) {
			javafxEditorName.setDisable( ! isEditing );
		}
		if( javafxEditorDelTopDep != null ) {
			javafxEditorDelTopDep.setDisable( ! isEditing );
		}
		if( javafxEditorDelSubDep1 != null ) {
			javafxEditorDelSubDep1.setDisable( ! isEditing );
		}
		if( javafxEditorDelSubDep2 != null ) {
			javafxEditorDelSubDep2.setDisable( ! isEditing );
		}
		if( javafxEditorDelSubDep3 != null ) {
			javafxEditorDelSubDep3.setDisable( ! isEditing );
		}
	}
}
