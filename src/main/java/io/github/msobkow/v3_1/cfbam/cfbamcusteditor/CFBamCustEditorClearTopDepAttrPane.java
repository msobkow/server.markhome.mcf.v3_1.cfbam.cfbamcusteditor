// Description: Java 25 JavaFX Attribute Pane implementation for ClearTopDep.

/*
 *	io.github.msobkow.CFBam
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

package io.github.msobkow.v3_1.cfbam.cfbamcusteditor;

import java.io.Serializable;
import java.math.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.javafx.*;
import io.github.msobkow.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import io.github.msobkow.v3_1.cflib.xml.CFLibXmlUtil;
import io.github.msobkow.v3_1.cfcore.msscf.*;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfint.cfint.*;
import io.github.msobkow.v3_1.cfbam.cfbam.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;
import io.github.msobkow.v3_1.cfint.cfintobj.*;
import io.github.msobkow.v3_1.cfbam.cfbamobj.*;
import io.github.msobkow.v3_1.cfsec.cfsecsaxloader.*;
import io.github.msobkow.v3_1.cfint.cfintsaxloader.*;
import io.github.msobkow.v3_1.cfbam.cfbamsaxloader.*;
import io.github.msobkow.v3_1.cfsec.cfsecjavafx.*;
import io.github.msobkow.v3_1.cfint.cfintjavafx.*;
import io.github.msobkow.v3_1.cfbam.cfbamjavafx.*;
import io.github.msobkow.v3_1.cfbam.cfbamram.*;
import io.github.msobkow.v3_1.cfbam.cfbammsscf.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustmssbamcf.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustxmlloader.*;
import io.github.msobkow.v3_1.cfsec.cfseccustfx.*;
import io.github.msobkow.v3_1.cfint.cfintcustfx.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustfx.*;
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
 *	CFBamJavaFXClearTopDepAttrPane JavaFX Attribute Pane implementation
 *	for ClearTopDep.
 */
public class CFBamCustEditorClearTopDepAttrPane
extends CFGridPane
implements ICFBamJavaFXClearTopDepPaneCommon
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
				ICFBamClearTopDepObj cur = getJavaFXFocusAsClearTopDep();
				if( cur != null ) {
					ICFBamClearTopDepEditObj editObj = (ICFBamClearTopDepEditObj)cur.getEdit();
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
			ICFBamClearTopDepObj focus = getJavaFXFocusAsClearTopDep();
			ICFBamClearTopDepEditObj editObj  = (ICFBamClearTopDepEditObj)focus.getEdit();
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
			ICFBamClearTopDepObj focus = getJavaFXFocusAsClearTopDep();
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

	protected class ClearTopDepCFLabel
		extends CFLabel
	{
		public ClearTopDepCFLabel() {
			super();
			setText( "Clear Top" );
		}
	}

	protected ICFBamClearTopDepObj getEffJavaFocus() {
		ICFBamClearTopDepObj obj = (ICFBamClearTopDepObj)getJavaFXFocusAsClearTopDep().getEdit();
		if( obj == null ) {
			obj = getJavaFXFocusAsClearTopDep();
		}
		return( obj );
	}

	protected class ClearTopDepCFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public ClearTopDepCFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( getEffJavaFocus().getRequiredContainerTable() ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorClearSubDep1().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep1().getSelectionModel().select( 0 );
						getJavaFXEditorClearSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorClearSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep3().getSelectionModel().select( 0 );
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
						getJavaFXEditorClearSubDep1().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorClearSubDep1().getSelectionModel().select( 0 );
						getJavaFXEditorClearSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorClearSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep3().getSelectionModel().select( 0 );
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

	protected class ClearSubDep1CFLabel
		extends CFLabel
	{
		public ClearSubDep1CFLabel() {
			super();
			setText( "Clear Level 1" );
		}
	}

	protected class ClearSubDep1CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public ClearSubDep1CFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorClearSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorClearSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep3().getSelectionModel().select( 0 );
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
						getJavaFXEditorClearSubDep2().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorClearSubDep2().getSelectionModel().select( 0 );
						getJavaFXEditorClearSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep3().getSelectionModel().select( 0 );
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

	protected class ClearSubDep2CFLabel
		extends CFLabel
	{
		public ClearSubDep2CFLabel() {
			super();
			setText( "Clear Level 2" );
		}
	}

	protected class ClearSubDep2CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public ClearSubDep2CFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
			valueProperty().addListener( new ChangeListener<CFBamCustEditorRelationEntry>() {
				@Override public void changed( ObservableValue obs,
					CFBamCustEditorRelationEntry oldValue,
					CFBamCustEditorRelationEntry newValue )
				{
					if( newValue == null ) {
						getJavaFXEditorClearSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( null ) );
						getJavaFXEditorClearSubDep3().getSelectionModel().select( 0 );
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
						getJavaFXEditorClearSubDep3().setItems( CFBamCustEditorRelationEntry.getObservableListOfRelationEntryForTable( targetTable ) );
						getJavaFXEditorClearSubDep3().getSelectionModel().select( 0 );
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

	protected class ClearSubDep3CFLabel
		extends CFLabel
	{
		public ClearSubDep3CFLabel() {
			super();
			setText( "Clear Level 3" );
		}
	}

	protected class ClearSubDep3CFEditor
		extends ComboBox<CFBamCustEditorRelationEntry>
	{
		public ClearSubDep3CFEditor() {
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
	protected ClearTopDepCFLabel javafxLabelClearTopDep = null;
	protected ClearTopDepCFEditor javafxEditorClearTopDep = null;
	protected ClearSubDep1CFLabel javafxLabelClearSubDep1 = null;
	protected ClearSubDep1CFEditor javafxEditorClearSubDep1 = null;
	protected ClearSubDep2CFLabel javafxLabelClearSubDep2 = null;
	protected ClearSubDep2CFEditor javafxEditorClearSubDep2 = null;
	protected ClearSubDep3CFLabel javafxLabelClearSubDep3 = null;
	protected ClearSubDep3CFEditor javafxEditorClearSubDep3 = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;

	public CFBamCustEditorClearTopDepAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamClearTopDepObj argFocus ) {
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
		setJavaFXFocusAsClearTopDep( argFocus );
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

		label = getJavaFXLabelClearTopDep();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorClearTopDep();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelClearSubDep1();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorClearSubDep1();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelClearSubDep2();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorClearSubDep2();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelClearSubDep3();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorClearSubDep3();
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
		if( ( value == null ) || ( value instanceof ICFBamClearTopDepObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamClearTopDepObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamClearTopDepObj getJavaFXFocusAsClearTopDep() {
		return( (ICFBamClearTopDepObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsClearTopDep( ICFBamClearTopDepObj value ) {
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

	public ClearTopDepCFLabel getJavaFXLabelClearTopDep() {
		if( javafxLabelClearTopDep == null ) {
			javafxLabelClearTopDep = new ClearTopDepCFLabel();
		}
		return( javafxLabelClearTopDep );
	}

	public void setJavaFXLabelClearTopDep( ClearTopDepCFLabel value ) {
		javafxLabelClearTopDep = value;
	}

	public ClearTopDepCFEditor getJavaFXEditorClearTopDep() {
		if( javafxEditorClearTopDep == null ) {
			javafxEditorClearTopDep = new ClearTopDepCFEditor();
		}
		return( javafxEditorClearTopDep );
	}

	public void setJavaFXEditorClearTopDep( ClearTopDepCFEditor value ) {
		javafxEditorClearTopDep = value;
	}

	public ClearSubDep1CFLabel getJavaFXLabelClearSubDep1() {
		if( javafxLabelClearSubDep1 == null ) {
			javafxLabelClearSubDep1 = new ClearSubDep1CFLabel();
		}
		return( javafxLabelClearSubDep1 );
	}

	public void setJavaFXLabelClearSubDep1( ClearSubDep1CFLabel value ) {
		javafxLabelClearSubDep1 = value;
	}

	public ClearSubDep1CFEditor getJavaFXEditorClearSubDep1() {
		if( javafxEditorClearSubDep1 == null ) {
			javafxEditorClearSubDep1 = new ClearSubDep1CFEditor();
		}
		return( javafxEditorClearSubDep1 );
	}

	public void setJavaFXEditorClearSubDep1( ClearSubDep1CFEditor value ) {
		javafxEditorClearSubDep1 = value;
	}

	public ClearSubDep2CFLabel getJavaFXLabelClearSubDep2() {
		if( javafxLabelClearSubDep2 == null ) {
			javafxLabelClearSubDep2 = new ClearSubDep2CFLabel();
		}
		return( javafxLabelClearSubDep2 );
	}

	public void setJavaFXLabelClearSubDep2( ClearSubDep2CFLabel value ) {
		javafxLabelClearSubDep2 = value;
	}

	public ClearSubDep2CFEditor getJavaFXEditorClearSubDep2() {
		if( javafxEditorClearSubDep2 == null ) {
			javafxEditorClearSubDep2 = new ClearSubDep2CFEditor();
		}
		return( javafxEditorClearSubDep2 );
	}

	public void setJavaFXEditorClearSubDep2( ClearSubDep2CFEditor value ) {
		javafxEditorClearSubDep2 = value;
	}

	public ClearSubDep3CFLabel getJavaFXLabelClearSubDep3() {
		if( javafxLabelClearSubDep3 == null ) {
			javafxLabelClearSubDep3 = new ClearSubDep3CFLabel();
		}
		return( javafxLabelClearSubDep3 );
	}

	public void setJavaFXLabelClearSubDep3( ClearSubDep3CFLabel value ) {
		javafxLabelClearSubDep3 = value;
	}

	public ClearSubDep3CFEditor getJavaFXEditorClearSubDep3() {
		if( javafxEditorClearSubDep3 == null ) {
			javafxEditorClearSubDep3 = new ClearSubDep3CFEditor();
		}
		return( javafxEditorClearSubDep3 );
	}

	public void setJavaFXEditorClearSubDep3( ClearSubDep3CFEditor value ) {
		javafxEditorClearSubDep3 = value;
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
		ICFBamClearTopDepObj clearObj;
		ICFBamClearTopDepObj focus = getJavaFXFocusAsClearTopDep();
		if( focus != null ) {
			clearObj = (ICFBamClearTopDepObj)(focus.getEdit());
			if( clearObj == null ) {
				clearObj = focus;
			}
		}
		else {
			clearObj = null;
		}
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			clearObj = null;
		}

		if( clearObj == null ) {
			javafxLookupDefSchemaObj = null;
		}
		else {
			javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)clearObj.getOptionalLookupDefSchema( javafxIsInitializing );
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setReferencedObject( javafxLookupDefSchemaObj );
		}

		if( clearObj == null ) {
			getJavaFXEditorId().setInt64Value( null );
		}
		else {
			getJavaFXEditorId().setInt64Value( clearObj.getRequiredId() );
		}

		if( clearObj == null ) {
			getJavaFXEditorName().setStringValue( null );
		}
		else {
			getJavaFXEditorName().setStringValue( clearObj.getRequiredName() );
		}

		if( clearObj == null ) {
			getJavaFXEditorClearTopDep().select( null );
			getJavaFXEditorClearSubDep1().select( null );
			getJavaFXEditorClearSubDep2().select( null );
			getJavaFXEditorClearSubDep3().select( null );
		}
		else {
			getJavaFXEditorClearTopDep().select( clearObj.getRequiredLookupRelation() );
			Iterator<ICFBamClearSubDep1Obj> iterClearSubDep1 = clearObj.getOptionalComponentsClearDep().iterator();
			if( iterClearSubDep1.hasNext() ) {
				ICFBamClearSubDep1Obj subDep1 = iterClearSubDep1.next();
				getJavaFXEditorClearSubDep1().select( subDep1.getRequiredLookupRelation() );
				Iterator<ICFBamClearSubDep2Obj> iterClearSubDep2 = subDep1.getOptionalComponentsClearDep().iterator();
				if( iterClearSubDep2.hasNext() ) {
					ICFBamClearSubDep2Obj subDep2 = iterClearSubDep2.next();
					getJavaFXEditorClearSubDep2().select( subDep2.getRequiredLookupRelation() );
					Iterator<ICFBamClearSubDep3Obj> iterClearSubDep3 = subDep2.getOptionalComponentsClearDep().iterator();
					if( iterClearSubDep3.hasNext() ) {
						ICFBamClearSubDep3Obj subDep3 = iterClearSubDep3.next();
						getJavaFXEditorClearSubDep3().select( subDep3.getRequiredLookupRelation() );
					}
					else {
						getJavaFXEditorClearSubDep3().select( null );
					}
				}
				else {
					getJavaFXEditorClearSubDep2().select( null );
					getJavaFXEditorClearSubDep3().select( null );
				}
			}
			else {
				getJavaFXEditorClearSubDep1().select( null );
				getJavaFXEditorClearSubDep2().select( null );
				getJavaFXEditorClearSubDep3().select( null );
			}
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamClearTopDepObj focus = getJavaFXFocusAsClearTopDep();
		ICFBamClearTopDepEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamClearTopDepEditObj)(focus.getEdit());
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
		ICFBamClearTopDepObj focus = getJavaFXFocusAsClearTopDep();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamClearTopDepEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamClearTopDepEditObj)focus.getEdit();
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
								editObj = (ICFBamClearTopDepEditObj)focus.beginEdit();
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
							editObj = (ICFBamClearTopDepEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFBamClearTopDepEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFBamClearTopDepEditObj)focus.beginEdit();
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
					ICFBamTenantObj tenantObj = (ICFBamTenantObj)getJavaFXFocusAsClearTopDep().getRequiredOwnerTenant();
					ICFBamRelationObj relation;
					ICFBamClearSubDep1Obj clearSubDep1;
					ICFBamClearSubDep1EditObj editSubDep1;
					Iterator<ICFBamClearSubDep1Obj> iterSubDep1;
					ICFBamClearSubDep2Obj clearSubDep2;
					ICFBamClearSubDep2EditObj editSubDep2;
					Iterator<ICFBamClearSubDep2Obj> iterSubDep2;
					ICFBamClearSubDep3Obj clearSubDep3;
					ICFBamClearSubDep3EditObj editSubDep3;
					Iterator<ICFBamClearSubDep3Obj> iterSubDep3;
					CFBamCustEditorRelationEntry topDepEntry = getJavaFXEditorClearTopDep().getValue();
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
								focus = (ICFBamClearTopDepObj)editObj.create();
							}
							else {
								editObj.update();
							}
							CFBamCustEditorRelationEntry subDep1Entry = getJavaFXEditorClearSubDep1().getValue();
							if( subDep1Entry == null ) {
								iterSubDep1 = editObj.getOptionalComponentsClearDep().iterator();
								if( iterSubDep1.hasNext() ) {
									clearSubDep1 = iterSubDep1.next();
									editSubDep1 = (ICFBamClearSubDep1EditObj)clearSubDep1.beginEdit();
									editSubDep1.deleteInstance();
									editSubDep1 = null;
								}
							}
							else {
								relation = subDep1Entry.getRelation();
								if( relation != null ) {
									iterSubDep1 = editObj.getOptionalComponentsClearDep().iterator();
									if( iterSubDep1.hasNext() ) {
										clearSubDep1 = iterSubDep1.next();
										if( clearSubDep1.getRequiredLookupRelation() != relation ) {
											editSubDep1 = (ICFBamClearSubDep1EditObj)clearSubDep1.beginEdit();
											editSubDep1.setRequiredLookupRelation( relation );
											editSubDep1.update();
											editSubDep1 = null;
										}
									}
									else {
										clearSubDep1 = schemaObj.getClearSubDep1TableObj().newInstance();
										editSubDep1 = (ICFBamClearSubDep1EditObj)clearSubDep1.beginEdit();
										editSubDep1.setRequiredOwnerTenant( tenantObj );
										editSubDep1.setRequiredContainerClearTopDep( editObj );
										editSubDep1.setRequiredLookupRelation( relation );
										clearSubDep1 = (ICFBamClearSubDep1Obj)editSubDep1.create();
										editSubDep1 = null;
									}
									CFBamCustEditorRelationEntry subDep2Entry = getJavaFXEditorClearSubDep2().getValue();
									if( subDep2Entry == null ) {
										iterSubDep2 = clearSubDep1.getOptionalComponentsClearDep().iterator();
										if( iterSubDep2.hasNext() ) {
											clearSubDep2 = iterSubDep2.next();
											editSubDep2 = (ICFBamClearSubDep2EditObj)clearSubDep2.beginEdit();
											editSubDep2.deleteInstance();
											editSubDep2 = null;
										}
									}
									else {
										relation = subDep2Entry.getRelation();
										if( relation != null ) {
											iterSubDep2 = clearSubDep1.getOptionalComponentsClearDep().iterator();
											if( iterSubDep2.hasNext() ) {
												clearSubDep2 = iterSubDep2.next();
												if( clearSubDep2.getRequiredLookupRelation() != relation ) {
													editSubDep2 = (ICFBamClearSubDep2EditObj)clearSubDep2.beginEdit();
													editSubDep2.setRequiredLookupRelation( relation );
													editSubDep2.update();
													editSubDep2 = null;
												}
											}
											else {
												clearSubDep2 = schemaObj.getClearSubDep2TableObj().newInstance();
												editSubDep2 = (ICFBamClearSubDep2EditObj)clearSubDep2.beginEdit();
												editSubDep2.setRequiredOwnerTenant( tenantObj );
												editSubDep2.setRequiredLookupRelation( relation );
												editSubDep2.setRequiredContainerClearSubDep1( clearSubDep1 );
												clearSubDep2 = (ICFBamClearSubDep2Obj)editSubDep2.create();
												editSubDep2 = null;
											}
											CFBamCustEditorRelationEntry subDep3Entry = getJavaFXEditorClearSubDep3().getValue();
											if( subDep3Entry == null ) {
												iterSubDep3 = clearSubDep2.getOptionalComponentsClearDep().iterator();
												if( iterSubDep3.hasNext() ) {
													clearSubDep3 = iterSubDep3.next();
													editSubDep3 = (ICFBamClearSubDep3EditObj)clearSubDep3.beginEdit();
													editSubDep3.deleteInstance();
													editSubDep3 = null;
												}
											}
											else {
												relation = subDep3Entry.getRelation();
												if( relation != null ) {
													iterSubDep3 = clearSubDep2.getOptionalComponentsClearDep().iterator();
													if( iterSubDep3.hasNext() ) {
														clearSubDep3 = iterSubDep3.next();
														if( clearSubDep3.getRequiredLookupRelation() != relation ) {
															editSubDep3 = (ICFBamClearSubDep3EditObj)clearSubDep3.beginEdit();
															editSubDep3.setRequiredLookupRelation( relation );
															editSubDep3.update();
															editSubDep3 = null;
														}
													}
													else {
														clearSubDep3 = schemaObj.getClearSubDep3TableObj().newInstance();
														editSubDep3 = (ICFBamClearSubDep3EditObj)clearSubDep3.beginEdit();
														editSubDep3.setRequiredOwnerTenant( tenantObj );
														editSubDep3.setRequiredLookupRelation( relation );
														editSubDep3.setRequiredContainerClearSubDep2( clearSubDep2 );
														clearSubDep3 = (ICFBamClearSubDep3Obj)editSubDep3.create();
														editSubDep3 = null;
													}
												}
												else {
													iterSubDep3 = clearSubDep2.getOptionalComponentsClearDep().iterator();
													if( iterSubDep3.hasNext() ) {
														clearSubDep3 = iterSubDep3.next();
														editSubDep3 = (ICFBamClearSubDep3EditObj)clearSubDep3.beginEdit();
														editSubDep3.deleteInstance();
														editSubDep3 = null;
													}
												}
											}
										}
										else {
											iterSubDep2 = clearSubDep1.getOptionalComponentsClearDep().iterator();
											if( iterSubDep2.hasNext() ) {
												clearSubDep2 = iterSubDep2.next();
												editSubDep2 = (ICFBamClearSubDep2EditObj)clearSubDep2.beginEdit();
												editSubDep2.deleteInstance();
												editSubDep2 = null;
											}
										}
									}
								}
								else {
									iterSubDep1 = editObj.getOptionalComponentsClearDep().iterator();
									if( iterSubDep1.hasNext() ) {
										clearSubDep1 = iterSubDep1.next();
										editSubDep1 = (ICFBamClearSubDep1EditObj)clearSubDep1.beginEdit();
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
								editObj = (ICFBamClearTopDepEditObj)focus.beginEdit();
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
								editObj = (ICFBamClearTopDepEditObj)focus.beginEdit();
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
							editObj = (ICFBamClearTopDepEditObj)focus.beginEdit();
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
			ICFBamClearTopDepObj focus = getJavaFXFocusAsClearTopDep();
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
		if( javafxEditorClearTopDep != null ) {
			javafxEditorClearTopDep.setDisable( ! isEditing );
		}
		if( javafxEditorClearSubDep1 != null ) {
			javafxEditorClearSubDep1.setDisable( ! isEditing );
		}
		if( javafxEditorClearSubDep2 != null ) {
			javafxEditorClearSubDep2.setDisable( ! isEditing );
		}
		if( javafxEditorClearSubDep3 != null ) {
			javafxEditorClearSubDep3.setDisable( ! isEditing );
		}
	}
}
