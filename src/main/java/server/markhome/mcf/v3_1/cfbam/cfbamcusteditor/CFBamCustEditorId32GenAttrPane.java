// Description: Java 25 JavaFX Attribute Pane implementation for Id32Gen.

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
 *	CFBamJavaFXId32GenAttrPane JavaFX Attribute Pane implementation
 *	for Id32Gen.
 */
public class CFBamCustEditorId32GenAttrPane
extends CFGridPane
implements ICFBamJavaFXId32GenPaneCommon
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
				ICFBamId32GenObj cur = getJavaFXFocusAsId32Gen();
				if( cur != null ) {
					ICFBamId32GenEditObj editObj = (ICFBamId32GenEditObj)cur.getEdit();
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
			ICFBamId32GenObj focus = getJavaFXFocusAsId32Gen();
			ICFBamId32GenEditObj editObj  = (ICFBamId32GenEditObj)focus.getEdit();
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
			ICFBamId32GenObj focus = getJavaFXFocusAsId32Gen();
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

	protected class DispenserCFLabel
		extends CFLabel
	{
		public DispenserCFLabel() {
			super();
			setText( "Dispensing Table" );
		}
	}

	protected ICFBamId32GenObj getEffJavaFocus() {
		ICFBamId32GenObj obj = (ICFBamId32GenObj)getJavaFXFocusAsId32Gen().getEdit();
		if( obj == null ) {
			obj = getJavaFXFocusAsId32Gen();
		}
		return( obj );
	}

	protected class DispenserCFEditor
		extends ComboBox<CFBamCustEditorTableEntry>
	{
		public DispenserCFEditor() {
			super();
			setPrefWidth( 500 );
			setItems( CFBamCustEditorTableEntry.getObservableListOfTableEntryForClusterTenant( getEffJavaFocus().getRequiredContainerSchemaDef() ) );
			getSelectionModel().select( 0 );
		}

		public void select( ICFBamTableObj value ) {
			CFBamCustEditorTableEntry entry;
			Iterator<CFBamCustEditorTableEntry> iterItems = getItems().iterator();
			while( iterItems.hasNext() ) {
				entry = iterItems.next();
				if( entry.getTable() == value ) {
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
			setMinValue( CFBamValueBuff.ID_MIN_VALUE );
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

	protected class GenerateIdCFLabel
		extends CFLabel
	{
		public GenerateIdCFLabel() {
			super();
			setText( "Generate Id" );
		}
	}

	protected class GenerateIdEditor
		extends CFBoolEditor
	{
		public GenerateIdEditor() {
			super();
			setIsNullable( true );
			setFieldName( "Generate Id" );
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

	protected class InitValueCFLabel
		extends CFLabel
	{
		public InitValueCFLabel() {
			super();
			setText( "Init. Value" );
		}
	}

	protected class InitValueEditor
		extends CFInt32Editor
	{
		public InitValueEditor() {
			super();
			setMinValue( CFBamInt32DefBuff.INITVALUE_MIN_VALUE );
			setMaxValue( CFBamInt32DefBuff.INITVALUE_MAX_VALUE );
			setFieldName( "Init. Value" );
		}
	}

	protected class MinValueCFLabel
		extends CFLabel
	{
		public MinValueCFLabel() {
			super();
			setText( "Min. Value" );
		}
	}

	protected class MinValueEditor
		extends CFInt32Editor
	{
		public MinValueEditor() {
			super();
			setMinValue( CFBamInt32DefBuff.MINVALUE_MIN_VALUE );
			setMaxValue( CFBamInt32DefBuff.MINVALUE_MAX_VALUE );
			setFieldName( "Min. Value" );
		}
	}

	protected class MaxValueCFLabel
		extends CFLabel
	{
		public MaxValueCFLabel() {
			super();
			setText( "Max. Value" );
		}
	}

	protected class MaxValueEditor
		extends CFInt32Editor
	{
		public MaxValueEditor() {
			super();
			setMinValue( CFBamInt32DefBuff.MAXVALUE_MIN_VALUE );
			setMaxValue( CFBamInt32DefBuff.MAXVALUE_MAX_VALUE );
			setFieldName( "Max. Value" );
		}
	}

	protected class SliceCFLabel
		extends CFLabel
	{
		public SliceCFLabel() {
			super();
			setText( "Slice" );
		}
	}

	protected class SliceEditor
		extends CFInt16Editor
	{
		public SliceEditor() {
			super();
			setMinValue( CFBamId32GenBuff.SLICE_MIN_VALUE );
			setMaxValue( CFBamId32GenBuff.SLICE_MAX_VALUE );
			setFieldName( "Slice" );
		}
	}

	protected class BlockSizeCFLabel
		extends CFLabel
	{
		public BlockSizeCFLabel() {
			super();
			setText( "BlockSize" );
		}
	}

	protected class BlockSizeEditor
		extends CFInt32Editor
	{
		public BlockSizeEditor() {
			super();
			setMinValue( CFBamId32GenBuff.BLOCKSIZE_MIN_VALUE );
			setMaxValue( CFBamId32GenBuff.BLOCKSIZE_MAX_VALUE );
			setFieldName( "BlockSize" );
		}
	}

	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected ICFBamTableObj javafxLookupDispenserObj = null;
	protected DispenserCFLabel javafxLabelLookupDispenser = null;
	protected DispenserCFEditor javafxLookupDispenser = null;
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
	protected GenerateIdCFLabel javafxLabelGenerateId = null;
	protected GenerateIdEditor javafxEditorGenerateId = null;
	protected DbNameCFLabel javafxLabelDbName = null;
	protected DbNameEditor javafxEditorDbName = null;
	protected InitValueCFLabel javafxLabelInitValue = null;
	protected InitValueEditor javafxEditorInitValue = null;
	protected MinValueCFLabel javafxLabelMinValue = null;
	protected MinValueEditor javafxEditorMinValue = null;
	protected MaxValueCFLabel javafxLabelMaxValue = null;
	protected MaxValueEditor javafxEditorMaxValue = null;
	protected SliceCFLabel javafxLabelSlice = null;
	protected SliceEditor javafxEditorSlice = null;
	protected BlockSizeCFLabel javafxLabelBlockSize = null;
	protected BlockSizeEditor javafxEditorBlockSize = null;

	public CFBamCustEditorId32GenAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamId32GenObj argFocus ) {
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
		setJavaFXFocusAsId32Gen( argFocus );
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

		label = getJavaFXLabelShortName();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorShortName();
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

		label = getJavaFXLabelGenerateId();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorGenerateId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupDispenser();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXLookupDispenser();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelSlice();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorSlice();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelBlockSize();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorBlockSize();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelInitValue();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorInitValue();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelMinValue();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorMinValue();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelMaxValue();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorMaxValue();
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
		if( ( value == null ) || ( value instanceof ICFBamId32GenObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamId32GenObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamId32GenObj getJavaFXFocusAsId32Gen() {
		return( (ICFBamId32GenObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsId32Gen( ICFBamId32GenObj value ) {
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

	public ICFBamTableObj getJavaFXLookupDispenserObj() {
		return( javafxLookupDispenserObj );
	}

	public void setJavaFXLookupDispenserObj( ICFBamTableObj value ) {
		javafxLookupDispenserObj = value;
	}

	public CFLabel getJavaFXLabelLookupDispenser() {
		if( javafxLabelLookupDispenser == null ) {
			javafxLabelLookupDispenser = new DispenserCFLabel();
		}
		return( javafxLabelLookupDispenser );
	}

	public DispenserCFEditor getJavaFXLookupDispenser() {
		if( javafxLookupDispenser == null ) {
			javafxLookupDispenser = new DispenserCFEditor();
		}
		return( javafxLookupDispenser );
	}

	public void setJavaFXLookupDispenser( DispenserCFEditor value ) {
		javafxLookupDispenser = value;
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

	public GenerateIdCFLabel getJavaFXLabelGenerateId() {
		if( javafxLabelGenerateId == null ) {
			javafxLabelGenerateId = new GenerateIdCFLabel();
		}
		return( javafxLabelGenerateId );
	}

	public void setJavaFXLabelGenerateId( GenerateIdCFLabel value ) {
		javafxLabelGenerateId = value;
	}

	public GenerateIdEditor getJavaFXEditorGenerateId() {
		if( javafxEditorGenerateId == null ) {
			javafxEditorGenerateId = new GenerateIdEditor();
		}
		return( javafxEditorGenerateId );
	}

	public void setJavaFXEditorGenerateId( GenerateIdEditor value ) {
		javafxEditorGenerateId = value;
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

	public InitValueCFLabel getJavaFXLabelInitValue() {
		if( javafxLabelInitValue == null ) {
			javafxLabelInitValue = new InitValueCFLabel();
		}
		return( javafxLabelInitValue );
	}

	public void setJavaFXLabelInitValue( InitValueCFLabel value ) {
		javafxLabelInitValue = value;
	}

	public InitValueEditor getJavaFXEditorInitValue() {
		if( javafxEditorInitValue == null ) {
			javafxEditorInitValue = new InitValueEditor();
		}
		return( javafxEditorInitValue );
	}

	public void setJavaFXEditorInitValue( InitValueEditor value ) {
		javafxEditorInitValue = value;
	}

	public MinValueCFLabel getJavaFXLabelMinValue() {
		if( javafxLabelMinValue == null ) {
			javafxLabelMinValue = new MinValueCFLabel();
		}
		return( javafxLabelMinValue );
	}

	public void setJavaFXLabelMinValue( MinValueCFLabel value ) {
		javafxLabelMinValue = value;
	}

	public MinValueEditor getJavaFXEditorMinValue() {
		if( javafxEditorMinValue == null ) {
			javafxEditorMinValue = new MinValueEditor();
		}
		return( javafxEditorMinValue );
	}

	public void setJavaFXEditorMinValue( MinValueEditor value ) {
		javafxEditorMinValue = value;
	}

	public MaxValueCFLabel getJavaFXLabelMaxValue() {
		if( javafxLabelMaxValue == null ) {
			javafxLabelMaxValue = new MaxValueCFLabel();
		}
		return( javafxLabelMaxValue );
	}

	public void setJavaFXLabelMaxValue( MaxValueCFLabel value ) {
		javafxLabelMaxValue = value;
	}

	public MaxValueEditor getJavaFXEditorMaxValue() {
		if( javafxEditorMaxValue == null ) {
			javafxEditorMaxValue = new MaxValueEditor();
		}
		return( javafxEditorMaxValue );
	}

	public void setJavaFXEditorMaxValue( MaxValueEditor value ) {
		javafxEditorMaxValue = value;
	}

	public SliceCFLabel getJavaFXLabelSlice() {
		if( javafxLabelSlice == null ) {
			javafxLabelSlice = new SliceCFLabel();
		}
		return( javafxLabelSlice );
	}

	public void setJavaFXLabelSlice( SliceCFLabel value ) {
		javafxLabelSlice = value;
	}

	public SliceEditor getJavaFXEditorSlice() {
		if( javafxEditorSlice == null ) {
			javafxEditorSlice = new SliceEditor();
		}
		return( javafxEditorSlice );
	}

	public void setJavaFXEditorSlice( SliceEditor value ) {
		javafxEditorSlice = value;
	}

	public BlockSizeCFLabel getJavaFXLabelBlockSize() {
		if( javafxLabelBlockSize == null ) {
			javafxLabelBlockSize = new BlockSizeCFLabel();
		}
		return( javafxLabelBlockSize );
	}

	public void setJavaFXLabelBlockSize( BlockSizeCFLabel value ) {
		javafxLabelBlockSize = value;
	}

	public BlockSizeEditor getJavaFXEditorBlockSize() {
		if( javafxEditorBlockSize == null ) {
			javafxEditorBlockSize = new BlockSizeEditor();
		}
		return( javafxEditorBlockSize );
	}

	public void setJavaFXEditorBlockSize( BlockSizeEditor value ) {
		javafxEditorBlockSize = value;
	}

	public void populateFields()
	{
		ICFBamId32GenObj popObj;
		ICFBamId32GenObj focus = getJavaFXFocusAsId32Gen();
		if( focus != null ) {
			popObj = (ICFBamId32GenObj)(focus.getEdit());
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
			javafxLookupDispenserObj = null;
		}
		else {
			javafxLookupDispenserObj = (ICFBamTableObj)popObj.getOptionalLookupDispenser( javafxIsInitializing );
		}
		if( javafxLookupDispenser != null ) {
			javafxLookupDispenser.select( javafxLookupDispenserObj );
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
			getJavaFXEditorGenerateId().setBooleanValue( null );
		}
		else {
			getJavaFXEditorGenerateId().setBooleanValue( popObj.getOptionalGenerateId() );
		}

		if( popObj == null ) {
			getJavaFXEditorDbName().setStringValue( null );
		}
		else {
			getJavaFXEditorDbName().setStringValue( popObj.getOptionalDbName() );
		}

		if( popObj == null ) {
			getJavaFXEditorInitValue().setInt32Value( null );
		}
		else {
			getJavaFXEditorInitValue().setInt32Value( popObj.getOptionalInitValue() );
		}

		if( popObj == null ) {
			getJavaFXEditorMinValue().setInt32Value( null );
		}
		else {
			getJavaFXEditorMinValue().setInt32Value( popObj.getOptionalMinValue() );
		}

		if( popObj == null ) {
			getJavaFXEditorMaxValue().setInt32Value( null );
		}
		else {
			getJavaFXEditorMaxValue().setInt32Value( popObj.getOptionalMaxValue() );
		}

		if( popObj == null ) {
			getJavaFXEditorSlice().setInt16Value( null );
		}
		else {
			getJavaFXEditorSlice().setInt16Value( popObj.getRequiredSlice() );
		}

		if( popObj == null ) {
			getJavaFXEditorBlockSize().setInt32Value( null );
		}
		else {
			getJavaFXEditorBlockSize().setInt32Value( popObj.getRequiredBlockSize() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamId32GenObj focus = getJavaFXFocusAsId32Gen();
		ICFBamId32GenEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamId32GenEditObj)(focus.getEdit());
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

		javafxLookupDispenserObj = javafxLookupDispenser.getSelectionModel().getSelectedItem().getTable();
		editObj.setOptionalLookupDispenser( javafxLookupDispenserObj );

		editObj.setRequiredName( getJavaFXEditorName().getStringValue() );

		editObj.setOptionalShortName( getJavaFXEditorShortName().getStringValue() );

		editObj.setOptionalLabel( getJavaFXEditorLabel().getStringValue() );

		editObj.setOptionalShortDescription( getJavaFXEditorShortDescription().getStringValue() );

		editObj.setOptionalDescription( getJavaFXEditorDescription().getStringValue() );

		editObj.setOptionalGenerateId( getJavaFXEditorGenerateId().getBooleanValue() );

		editObj.setOptionalDbName( getJavaFXEditorDbName().getStringValue() );

		editObj.setOptionalInitValue( getJavaFXEditorInitValue().getInt32Value() );

		editObj.setOptionalMinValue( getJavaFXEditorMinValue().getInt32Value() );

		editObj.setOptionalMaxValue( getJavaFXEditorMaxValue().getInt32Value() );

		editObj.setRequiredSlice( getJavaFXEditorSlice().getInt16Value() );

		editObj.setRequiredBlockSize( getJavaFXEditorBlockSize().getInt32Value() );

		if( ( editObj.getRequiredName() == null ) || ( editObj.getRequiredName().length() <= 0 ) ) {
			throw new CFLibEmptyArgumentException( getClass(),
				S_ProcName,
				0,
				"Name" );
		}

		if( ( ! editObj.getRequiredIsNullable() ) && ( editObj.getOptionalInitValue() == null ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"InitValue" );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamId32GenObj focus = getJavaFXFocusAsId32Gen();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamId32GenEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamId32GenEditObj)focus.getEdit();
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
								editObj = (ICFBamId32GenEditObj)focus.beginEdit();
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
							editObj = (ICFBamId32GenEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFBamId32GenEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFBamId32GenEditObj)focus.beginEdit();
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
						focus = (ICFBamId32GenObj)editObj.create();
						setJavaFXFocus( focus );
					}
					else {
						editObj.update();
					}
					editObj = null;
				}
				setPaneMode( CFPane.PaneMode.View );
				break;
			case Delete:
				switch( oldValue ) {
					case View:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamId32GenEditObj)focus.beginEdit();
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
								editObj = (ICFBamId32GenEditObj)focus.beginEdit();
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
							editObj = (ICFBamId32GenEditObj)focus.beginEdit();
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
			ICFBamId32GenObj focus = getJavaFXFocusAsId32Gen();
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
		if( javafxLookupDispenser != null ) {
			javafxLookupDispenser.setDisable( ! isEditing );
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
		if( javafxEditorGenerateId != null ) {
			javafxEditorGenerateId.setDisable( ! isEditing );
		}
		if( javafxEditorDbName != null ) {
			javafxEditorDbName.setDisable( ! isEditing );
		}
		if( javafxEditorInitValue != null ) {
			javafxEditorInitValue.setDisable( ! isEditing );
		}
		if( javafxEditorMinValue != null ) {
			javafxEditorMinValue.setDisable( ! isEditing );
		}
		if( javafxEditorMaxValue != null ) {
			javafxEditorMaxValue.setDisable( ! isEditing );
		}
		if( javafxEditorSlice != null ) {
			javafxEditorSlice.setDisable( ! isEditing );
		}
		if( javafxEditorBlockSize != null ) {
			javafxEditorBlockSize.setDisable( ! isEditing );
		}
	}
}
