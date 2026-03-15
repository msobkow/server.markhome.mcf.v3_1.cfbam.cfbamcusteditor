// Description: Java 25 JavaFX Attribute Pane implementation for SchemaRef.

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
 *	CFBamJavaFXSchemaRefAttrPane JavaFX Attribute Pane implementation
 *	for SchemaRef.
 */
public class CFBamCustEditorSchemaRefAttrPane
extends CFGridPane
implements ICFBamJavaFXSchemaRefPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected LinkedList<String> linkedListOfModelName = null;
	protected ObservableList<String> observableListOfModelName = null;

	protected ObservableList<String> getObservableListOfModelName() {
		if( linkedListOfModelName == null ) {
			linkedListOfModelName = new LinkedList<String>();
			MSSBamCFPrefs prefs = CFBamCustEditorHierarchyPane.getCFPrefs();
			Iterator<String> iterModelPath = prefs.getModelPathIterator();
			String modelPath;
			DirectoryStream<Path> dirStream;
			File dirFile;
			Path filePath;
			File modelFile;
			String fileName;
			String modelName;
			Iterator<Path> dirIter;
			while( iterModelPath.hasNext() ) {
				modelPath = iterModelPath.next();
				if( ( modelPath != null ) && ( modelPath.length() > 0 ) ) {
					try {
						dirFile = new File( modelPath );
						if( dirFile.exists() && dirFile.isDirectory() && dirFile.canRead() ) {
							dirStream = Files.newDirectoryStream( dirFile.toPath(), "*.xml" );
							if( dirStream != null ) {
								dirIter = dirStream.iterator();
								while( dirIter.hasNext() ) {
									filePath = dirIter.next();
									modelFile = new File( filePath.toString() );
									if( modelFile.exists() && modelFile.isFile() && modelFile.canRead() ) {
										fileName = modelFile.getName();
										if( fileName.length() >= 5 ) {
											modelName = fileName.substring( 0, fileName.length() - 4 );
											linkedListOfModelName.add( modelName );
										}
									}
								}
							}
						}
						else {
							CFConsole.message( "Can not access model path directory \"" + modelPath + "\"" );
						}
					}
					catch (IOException e) {
						CFConsole.message( "Error scanning model path directory \"" + modelPath + "\"" );
					}
					finally {
					}
				}
			}

			observableListOfModelName = FXCollections.observableList( linkedListOfModelName );
		}
		return( observableListOfModelName );
	}

	protected class RefModelNameCFLabel
		extends CFLabel
	{
		public RefModelNameCFLabel() {
			super();
			setText( "Referenced Model Name" );
		}
	}

	protected class RefModelNameEditor
		extends ComboBox<String>
	{
		public RefModelNameEditor() {
			super();
			setPrefWidth( 500 );
			setItems( getObservableListOfModelName() );
		}
	}

	protected class RefSchemaCFLabel
		extends CFLabel
	{
		public RefSchemaCFLabel() {
			super();
			setText( "RefSchema" );
		}
	}

	protected class CallbackRefSchemaChosen
	implements ICFBamJavaFXSchemaDefChosen
	{
		public CallbackRefSchemaChosen() {
		}

		public void choseSchemaDef( ICFBamSchemaDefObj value ) {
			if( javafxReferenceLookupRefSchema != null ) {
				ICFBamSchemaRefObj cur = getJavaFXFocusAsSchemaRef();
				if( cur != null ) {
					ICFBamSchemaRefEditObj editObj = (ICFBamSchemaRefEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupRefSchema.setReferencedObject( value );
							editObj.setOptionalLookupRefSchema( value );
						}
					}
				}
			}
		}
	}

	protected class RefSchemaReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamSchemaRefObj focus = getJavaFXFocusAsSchemaRef();
			ICFBamSchemaRefEditObj editObj  = (ICFBamSchemaRefEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupRefSchema.getReferencedObject();
			List<ICFBamSchemaDefObj> listOfSchemaDef = null;
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackRefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamSchemaRefObj focus = getJavaFXFocusAsSchemaRef();
			if( focus != null ) {
				ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupRefSchema.getReferencedObject();
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

	protected class RefSchemaCFReferenceEditor
		extends CFReferenceEditor
	{
		public RefSchemaCFReferenceEditor() {
			super( new RefSchemaReferenceCallback() );
			setFieldName( "RefSchema" );
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

	protected class IncludeRootCFLabel
		extends CFLabel
	{
		public IncludeRootCFLabel() {
			super();
			setText( "IncludeRoot" );
		}
	}

	protected class IncludeRootEditor
		extends CFStringEditor
	{
		public IncludeRootEditor() {
			super();
			setMaxLen( 1024 );
			setFieldName( "IncludeRoot" );
		}
	}

	protected ICFBamSchemaDefObj javafxLookupRefSchemaObj = null;
	protected RefSchemaCFLabel javafxLabelLookupRefSchema = null;
	protected RefSchemaCFReferenceEditor javafxReferenceLookupRefSchema = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;
	protected RefModelNameCFLabel javafxLabelRefModelName = null;
	protected RefModelNameEditor javafxEditorRefModelName = null;
	protected IncludeRootCFLabel javafxLabelIncludeRoot = null;
	protected IncludeRootEditor javafxEditorIncludeRoot = null;

	public CFBamCustEditorSchemaRefAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamSchemaRefObj argFocus ) {
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
		setJavaFXFocusAsSchemaRef( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints( 125 );
		ColumnConstraints column2 = new ColumnConstraints( 125, 300, 500 );
		column2.setHgrow( Priority.ALWAYS );
		getColumnConstraints().addAll( column1, column2 );
		int gridRow = 0;
		label = getJavaFXLabelLookupRefSchema();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		reference = getJavaFXReferenceLookupRefSchema();
		setHalignment( reference, HPos.LEFT );
		add( reference, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelId();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelName();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelRefModelName();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorRefModelName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelIncludeRoot();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorIncludeRoot();
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
		if( ( value == null ) || ( value instanceof ICFBamSchemaRefObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamSchemaRefObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamSchemaRefObj getJavaFXFocusAsSchemaRef() {
		return( (ICFBamSchemaRefObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSchemaRef( ICFBamSchemaRefObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamSchemaDefObj getJavaFXLookupRefSchemaObj() {
		return( javafxLookupRefSchemaObj );
	}

	public void setJavaFXLookupRefSchemaObj( ICFBamSchemaDefObj value ) {
		javafxLookupRefSchemaObj = value;
	}

	public CFLabel getJavaFXLabelLookupRefSchema() {
		if( javafxLabelLookupRefSchema == null ) {
			javafxLabelLookupRefSchema = new RefSchemaCFLabel();
		}
		return( javafxLabelLookupRefSchema );
	}

	public CFReferenceEditor getJavaFXReferenceLookupRefSchema() {
		if( javafxReferenceLookupRefSchema == null ) {
			javafxReferenceLookupRefSchema = new RefSchemaCFReferenceEditor();
		}
		return( javafxReferenceLookupRefSchema );
	}

	public void setJavaFXReferenceLookupRefSchema( RefSchemaCFReferenceEditor value ) {
		javafxReferenceLookupRefSchema = value;
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

	public RefModelNameCFLabel getJavaFXLabelRefModelName() {
		if( javafxLabelRefModelName == null ) {
			javafxLabelRefModelName = new RefModelNameCFLabel();
		}
		return( javafxLabelRefModelName );
	}

	public void setJavaFXLabelRefModelName( RefModelNameCFLabel value ) {
		javafxLabelRefModelName = value;
	}

	public RefModelNameEditor getJavaFXEditorRefModelName() {
		if( javafxEditorRefModelName == null ) {
			javafxEditorRefModelName = new RefModelNameEditor();
		}
		return( javafxEditorRefModelName );
	}

	public void setJavaFXEditorRefModelName( RefModelNameEditor value ) {
		javafxEditorRefModelName = value;
	}

	public IncludeRootCFLabel getJavaFXLabelIncludeRoot() {
		if( javafxLabelIncludeRoot == null ) {
			javafxLabelIncludeRoot = new IncludeRootCFLabel();
		}
		return( javafxLabelIncludeRoot );
	}

	public void setJavaFXLabelIncludeRoot( IncludeRootCFLabel value ) {
		javafxLabelIncludeRoot = value;
	}

	public IncludeRootEditor getJavaFXEditorIncludeRoot() {
		if( javafxEditorIncludeRoot == null ) {
			javafxEditorIncludeRoot = new IncludeRootEditor();
		}
		return( javafxEditorIncludeRoot );
	}

	public void setJavaFXEditorIncludeRoot( IncludeRootEditor value ) {
		javafxEditorIncludeRoot = value;
	}

	public void populateFields()
	{
		ICFBamSchemaRefObj popObj;
		ICFBamSchemaRefObj focus = getJavaFXFocusAsSchemaRef();
		if( focus != null ) {
			popObj = (ICFBamSchemaRefObj)(focus.getEdit());
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
			javafxLookupRefSchemaObj = null;
		}
		else {
			javafxLookupRefSchemaObj = (ICFBamSchemaDefObj)popObj.getOptionalLookupRefSchema( javafxIsInitializing );
		}
		if( javafxReferenceLookupRefSchema != null ) {
			javafxReferenceLookupRefSchema.setReferencedObject( javafxLookupRefSchemaObj );
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
			getJavaFXEditorRefModelName().setValue( null );
		}
		else {
			getJavaFXEditorRefModelName().setValue(
				( ( popObj.getRequiredRefModelName() == null ) || ( popObj.getRequiredRefModelName().length() <= 0 ) )
					? null
					: popObj.getRequiredRefModelName() );
		}

		if( popObj == null ) {
			getJavaFXEditorIncludeRoot().setStringValue( null );
		}
		else {
			getJavaFXEditorIncludeRoot().setStringValue( popObj.getRequiredIncludeRoot() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamSchemaRefObj focus = getJavaFXFocusAsSchemaRef();
		ICFBamSchemaRefEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamSchemaRefEditObj)(focus.getEdit());
		}
		else {
			editObj = null;
		}
		if( editObj == null ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Pane is unfocused or is not editing the focus object" );
		}

		javafxLookupRefSchemaObj = (ICFBamSchemaDefObj)( javafxReferenceLookupRefSchema.getReferencedObject() );
		editObj.setOptionalLookupRefSchema( javafxLookupRefSchemaObj );

		editObj.setRequiredRefModelName( getJavaFXEditorRefModelName().getValue() );

		editObj.setRequiredIncludeRoot( getJavaFXEditorIncludeRoot().getStringValue() );

		if( editObj.getRequiredRefModelName().length() <= 0 ) {
			throw new CFLibEmptyArgumentException( getClass(),
				S_ProcName,
				0,
				getJavaFXLabelRefModelName().getText() );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamSchemaRefObj focus = getJavaFXFocusAsSchemaRef();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamSchemaRefEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamSchemaRefEditObj)focus.getEdit();
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
								editObj = (ICFBamSchemaRefEditObj)focus.beginEdit();
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
							editObj = (ICFBamSchemaRefEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFBamSchemaRefEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFBamSchemaRefEditObj)focus.beginEdit();
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
						String modelName = editObj.getRequiredRefModelName();
						if( ( modelName == null ) || ( modelName.length() <= 0 ) ) {
							throw new CFLibEmptyArgumentException( getClass(),
								S_ProcName,
								0,
								"modelName" );
						}
						MSSBamCFEngine engine = CFBamCustEditorHierarchyPane.getCFEngine();
						CFBamXmlLoader bamParser = new CFBamXmlLoader( engine, engine.getLog() );
						ICFBamSchemaDefObj schemaObj = editObj.getRequiredContainerSchema();
						ICFBamSchemaObj schema = schemaObj.getSchema();
						bamParser.setSchemaObj( schema );
						bamParser.setTenant( (ICFBamTenantObj)schema.getSecTenant() );

						bamParser.loadTenant( modelName );
						ICFBamMinorVersionObj definedVersion = (ICFBamMinorVersionObj)( bamParser.getDefinedProjectVersion() );
						if( definedVersion == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"definedVersion" );
						}

						List<ICFBamSchemaDefObj> listSchemaDef = definedVersion.getOptionalComponentsSchemaDef();
						if( listSchemaDef.size() != 1 ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								"Loaded version object for model \"" + modelName + "\" must define exactly one SchemaDef" );
						}
						ICFBamSchemaDefObj definedSchema = listSchemaDef.iterator().next();

						boolean notFound;
						ICFBamSchemaRefObj existingSchemaRef;
						Iterator<ICFBamSchemaRefObj> iterExistingSchemaRef;
						ICFBamSchemaRefObj definedSchemaRef;
						ICFBamSchemaDefObj definedSchemaDef;
						Iterator<ICFBamSchemaRefObj> iterDefinedSchemaRef = definedSchema.getOptionalComponentsSchemaRefs().iterator();
						while( iterDefinedSchemaRef.hasNext() ) {
							definedSchemaRef = iterDefinedSchemaRef.next();
							definedSchemaDef = definedSchemaRef.getOptionalLookupRefSchema();
							if( definedSchemaDef == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"definedSchemaDef" );
							}
							notFound = true;
							iterExistingSchemaRef = schemaObj.getOptionalComponentsSchemaRefs().iterator();
							while( notFound && iterExistingSchemaRef.hasNext() ) {
								existingSchemaRef = iterExistingSchemaRef.next();
								if( existingSchemaRef.getOptionalLookupRefSchema() == definedSchemaDef ) {
									notFound = false;
								}
							}
							if( notFound ) {
								ICFBamSchemaRefObj autoAdd = schema.getSchemaRefTableObj().newInstance();
								ICFBamSchemaRefEditObj autoEdit = (ICFBamSchemaRefEditObj)autoAdd.beginEdit();
								autoEdit.setRequiredContainerSchema( schemaObj );
								autoEdit.setRequiredName( definedSchemaDef.getObjName() );
								autoEdit.setOptionalLookupRefSchema( definedSchemaDef );
								autoEdit.setRequiredRefModelName( definedSchemaRef.getRequiredRefModelName() );
								autoEdit.setRequiredIncludeRoot( definedSchemaRef.getRequiredIncludeRoot() );
								autoAdd = (ICFBamSchemaRefObj)autoEdit.create();
								bamParser.getSchemaRefHandler().importMergeSchema( schemaObj, definedSchemaDef );
							}
						}
						notFound = true;
						iterExistingSchemaRef = schemaObj.getOptionalComponentsSchemaRefs().iterator();
						existingSchemaRef = null;
						while( notFound && iterExistingSchemaRef.hasNext() ) {
							existingSchemaRef = iterExistingSchemaRef.next();
							if( existingSchemaRef.getOptionalLookupRefSchema() == definedSchema ) {
								notFound = false;
							}
						}
						if( ( ! notFound ) && ( existingSchemaRef != null ) ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								"Schema model \"" + modelName + "\" was already referenced and defines the schema \"" + existingSchemaRef.getOptionalLookupRefSchema().getObjFullName() + "\"" );
						}

						editObj.setRequiredName( definedSchema.getObjName() );
						editObj.setOptionalLookupRefSchema( definedSchema );
						editObj.setRequiredIncludeRoot( definedSchema.getObjFullName() );
						focus = (ICFBamSchemaRefObj)editObj.create();
						bamParser.getSchemaRefHandler().importMergeSchema( schemaObj, definedSchema );
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
								editObj = (ICFBamSchemaRefEditObj)focus.beginEdit();
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
								editObj = (ICFBamSchemaRefEditObj)focus.beginEdit();
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
							editObj = (ICFBamSchemaRefEditObj)focus.beginEdit();
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
			ICFBamSchemaRefObj focus = getJavaFXFocusAsSchemaRef();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( isEditing ) {
			ICFBamSchemaRefObj focus = getJavaFXFocusAsSchemaRef();
			if( ( focus != null ) && ( ! focus.getIsNew() ) ) {
				isEditing = false;
			}
		}
		if( javafxReferenceLookupRefSchema != null ) {
			javafxReferenceLookupRefSchema.setCustomDisable( true );
		}
		if( javafxEditorId != null ) {
			javafxEditorId.setDisable( true );
		}
		if( javafxEditorName != null ) {
			javafxEditorName.setDisable( true );
		}
		if( javafxEditorRefModelName != null ) {
			javafxEditorRefModelName.setDisable( ! isEditing );
		}
		if( javafxEditorIncludeRoot != null ) {
			javafxEditorIncludeRoot.setDisable( true );
		}
	}
}
