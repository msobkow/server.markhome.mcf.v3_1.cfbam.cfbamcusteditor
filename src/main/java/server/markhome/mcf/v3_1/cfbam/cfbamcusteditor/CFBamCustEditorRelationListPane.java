// Description: Java 25 JavaFX List of Obj Pane implementation for Relation.

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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *	CFBamJavaFXRelationListPane JavaFX List of Obj Pane implementation
 *	for Relation.
 */
public class CFBamCustEditorRelationListPane
extends CFBorderPane
implements ICFBamJavaFXRelationPaneList
{
	public static String S_FormName = "List Relation";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamRelationObj> javafxDataCollection = null;
	protected ObservableList<ICFBamRelationObj> observableListOfRelation = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddRelation = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamRelationObj> dataTable = null;
	protected TableColumn<ICFBamRelationObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnName = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamRelationObj, ICFBamSchema.RelationTypeEnum> tableColumnRelationType = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnDbName = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnSuffix = null;
	protected TableColumn<ICFBamRelationObj, Boolean> tableColumnIsRequired = null;
	protected TableColumn<ICFBamRelationObj, Boolean> tableColumnIsXsdContainer = null;
	protected TableColumn<ICFBamRelationObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamRelationObj, ICFBamIndexObj> tableColumnLookupFromIndex = null;
	protected TableColumn<ICFBamRelationObj, ICFBamTableObj> tableColumnLookupToTable = null;
	protected TableColumn<ICFBamRelationObj, ICFBamIndexObj> tableColumnLookupToIndex = null;
	protected TableColumn<ICFBamRelationObj, ICFBamRelationObj> tableColumnLookupNarrowed = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamTableObj javafxContainer = null;
	protected ICFRefreshCallback javafxRefreshCallback = null;
	class ViewEditClosedCallback implements ICFFormClosedCallback {
		public ViewEditClosedCallback() {
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected ViewEditClosedCallback viewEditClosedCallback = null;

	public ICFFormClosedCallback getViewEditClosedCallback() {
		if( viewEditClosedCallback == null ) {
			viewEditClosedCallback = new ViewEditClosedCallback();
		}
		return( viewEditClosedCallback );
	}

	class DeleteCallback implements ICFDeleteCallback {
		public DeleteCallback() {
		}

		@Override
		public void deleted( ICFLibAnyObj deletedObject ) {
			if( deletedObject != null ) {
				refreshMe();
			}
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected DeleteCallback deleteCallback = null;

	public ICFDeleteCallback getDeleteCallback() {
		if( deleteCallback == null ) {
			deleteCallback = new DeleteCallback();
		}
		return( deleteCallback );
	}


	public CFBamCustEditorRelationListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamTableObj argContainer,
		ICFBamRelationObj argFocus,
		Collection<ICFBamRelationObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		super();
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
		javaFXFocus = argFocus;
		javafxContainer = argContainer;
		javafxRefreshCallback = refreshCallback;
		javafxSortByChain = sortByChain;
		setJavaFXDataCollection( argDataCollection );
		dataTable = new TableView<ICFBamRelationObj>();
		tableColumnId = new TableColumn<ICFBamRelationObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamRelationObj, Long> p ) {
				ICFBamScopeObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					long value = obj.getRequiredId();
					Long wrapped = Long.valueOf( value );
					ReadOnlyObjectWrapper<Long> observable = new ReadOnlyObjectWrapper<Long>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,Long>,TableCell<ICFBamRelationObj,Long>>() {
			@Override public TableCell<ICFBamRelationObj,Long> call(
				TableColumn<ICFBamRelationObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamRelationObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnLookupDefSchema = new TableColumn<ICFBamRelationObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamSchemaDefObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchemaDefObj ref = obj.getOptionalLookupDefSchema();
					ReadOnlyObjectWrapper<ICFBamSchemaDefObj> observable = new ReadOnlyObjectWrapper<ICFBamSchemaDefObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamSchemaDefObj>,TableCell<ICFBamRelationObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamRelationObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnDbName = new TableColumn<ICFBamRelationObj,String>( "Db Name" );
		tableColumnDbName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalDbName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDbName.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDbName );
		tableColumnShortName = new TableColumn<ICFBamRelationObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnDescription = new TableColumn<ICFBamRelationObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnShortDescription = new TableColumn<ICFBamRelationObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnLabel = new TableColumn<ICFBamRelationObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalLabel();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnSuffix = new TableColumn<ICFBamRelationObj,String>( "Suffix" );
		tableColumnSuffix.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalSuffix();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSuffix.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSuffix );
		tableColumnRelationType = new TableColumn<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>( "Relation Type" );
		tableColumnRelationType.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>,ObservableValue<ICFBamSchema.RelationTypeEnum> >() {
			public ObservableValue<ICFBamSchema.RelationTypeEnum> call( CellDataFeatures<ICFBamRelationObj, ICFBamSchema.RelationTypeEnum> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchema.RelationTypeEnum value = obj.getRequiredRelationType();
					ReadOnlyObjectWrapper<ICFBamSchema.RelationTypeEnum> observable = new ReadOnlyObjectWrapper<ICFBamSchema.RelationTypeEnum>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnRelationType.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>,TableCell<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum> call(
				TableColumn<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum> arg)
			{
				return new CFEnumTableCell<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>();
			}
		});
		dataTable.getColumns().add( tableColumnRelationType );
		tableColumnIsRequired = new TableColumn<ICFBamRelationObj,Boolean>( "Is Required" );
		tableColumnIsRequired.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamRelationObj, Boolean> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsRequired();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsRequired.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,Boolean>,TableCell<ICFBamRelationObj,Boolean>>() {
			@Override public TableCell<ICFBamRelationObj,Boolean> call(
				TableColumn<ICFBamRelationObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsRequired );
		tableColumnIsXsdContainer = new TableColumn<ICFBamRelationObj,Boolean>( "Is XSD Container" );
		tableColumnIsXsdContainer.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamRelationObj, Boolean> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsXsdContainer();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsXsdContainer.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,Boolean>,TableCell<ICFBamRelationObj,Boolean>>() {
			@Override public TableCell<ICFBamRelationObj,Boolean> call(
				TableColumn<ICFBamRelationObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsXsdContainer );
		tableColumnLookupFromIndex = new TableColumn<ICFBamRelationObj, ICFBamIndexObj>( "From Index" );
		tableColumnLookupFromIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamIndexObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getRequiredLookupFromIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupFromIndex.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamIndexObj>,TableCell<ICFBamRelationObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamIndexObj> call(
				TableColumn<ICFBamRelationObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupFromIndex );
		tableColumnLookupToTable = new TableColumn<ICFBamRelationObj, ICFBamTableObj>( "To Table" );
		tableColumnLookupToTable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamTableObj>,ObservableValue<ICFBamTableObj> >() {
			public ObservableValue<ICFBamTableObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamTableObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamTableObj ref = obj.getRequiredLookupToTable();
					ReadOnlyObjectWrapper<ICFBamTableObj> observable = new ReadOnlyObjectWrapper<ICFBamTableObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupToTable.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamTableObj>,TableCell<ICFBamRelationObj,ICFBamTableObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamTableObj> call(
				TableColumn<ICFBamRelationObj,ICFBamTableObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupToTable );
		tableColumnLookupToIndex = new TableColumn<ICFBamRelationObj, ICFBamIndexObj>( "To Index" );
		tableColumnLookupToIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamIndexObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getRequiredLookupToIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupToIndex.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamIndexObj>,TableCell<ICFBamRelationObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamIndexObj> call(
				TableColumn<ICFBamRelationObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupToIndex );
		tableColumnLookupNarrowed = new TableColumn<ICFBamRelationObj, ICFBamRelationObj>( "Narrowed Relation" );
		tableColumnLookupNarrowed.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamRelationObj>,ObservableValue<ICFBamRelationObj> >() {
			public ObservableValue<ICFBamRelationObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamRelationObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamRelationObj ref = obj.getOptionalLookupNarrowed();
					ReadOnlyObjectWrapper<ICFBamRelationObj> observable = new ReadOnlyObjectWrapper<ICFBamRelationObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupNarrowed.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamRelationObj>,TableCell<ICFBamRelationObj,ICFBamRelationObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamRelationObj> call(
				TableColumn<ICFBamRelationObj,ICFBamRelationObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupNarrowed );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamRelationObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamRelationObj> observable,
					ICFBamRelationObj oldValue,
					ICFBamRelationObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getPanelHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );
		javafxIsInitializing = false;
		if( observableListOfRelation != null ) {
			dataTable.setItems( observableListOfRelation );
		}
		adjustListButtons();
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

	public void setPaneMode( CFPane.PaneMode value ) {
		super.setPaneMode( value );
		adjustListButtons();
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
		adjustListButtons();
	}

	public ICFBamRelationObj getJavaFXFocusAsRelation() {
		return( (ICFBamRelationObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsRelation( ICFBamRelationObj value ) {
		setJavaFXFocus( value );
	}

	public class RelationByQualNameComparator
	implements Comparator<ICFBamRelationObj>
	{
		public RelationByQualNameComparator() {
		}

		public int compare( ICFBamRelationObj lhs, ICFBamRelationObj rhs ) {
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
				String lhsValue = lhs.getObjQualifiedName();
				String rhsValue = rhs.getObjQualifiedName();
				if( lhsValue == null ) {
					if( rhsValue == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhsValue == null ) {
					return( 1 );
				}
				else {
					return( lhsValue.compareTo( rhsValue ) );
				}
			}
		}
	}

	protected RelationByQualNameComparator compareRelationByQualName = new RelationByQualNameComparator();

	public Collection<ICFBamRelationObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamRelationObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfRelation = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFBamRelationObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfRelation.add( iter.next() );
				}
				observableListOfRelation.sort( compareRelationByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfRelation );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	protected class CompareCFButtonByText
	implements Comparator<CFButton>
	{
		public CompareCFButtonByText() {
		}

		@Override public int compare( CFButton lhs, CFButton rhs ) {
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
				int retval = lhs.getText().compareTo( rhs.getText() );
				return( retval );
			}
		}
	}

	public CFHBox getPanelHBoxMenu() {
		if( hboxMenu == null ) {
			hboxMenu = new CFHBox( 10 );
			buttonAddRelation = new CFButton();
			buttonAddRelation.setMinWidth( 200 );
			buttonAddRelation.setText( "Add Relation" );
			buttonAddRelation.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						ICFBamRelationObj obj = (ICFBamRelationObj)schemaObj.getRelationTableObj().newInstance();
						ICFBamRelationEditObj edit = (ICFBamRelationEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
		ICFSecTenantObj secTenant = schemaObj.getSecTenant();
		edit.setRequiredOwnerRelTenant( secTenant );
			ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
			if( container == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"JavaFXContainer" );
			}
			edit.setRequiredContainerFromTable( container );
						CFBorderPane frame = javafxSchema.getRelationFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFBamJavaFXRelationPaneCommon jpanelCommon = (ICFBamJavaFXRelationPaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddRelation );
			buttonViewSelected = new CFButton();
			buttonViewSelected.setMinWidth( 200 );
			buttonViewSelected.setText( "View Selected" );
			buttonViewSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamRelationObj selectedInstance = getJavaFXFocusAsRelation();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "RELD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getRelationFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXRelationPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamRelationObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonViewSelected );

			buttonEditSelected = new CFButton();
			buttonEditSelected.setMinWidth( 200 );
			buttonEditSelected.setText( "Edit Selected" );
			buttonEditSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamRelationObj selectedInstance = getJavaFXFocusAsRelation();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "RELD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getRelationFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXRelationPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamRelationObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonEditSelected );

			buttonDeleteSelected = new CFButton();
			buttonDeleteSelected.setMinWidth( 200 );
			buttonDeleteSelected.setText( "Delete Selected" );
			buttonDeleteSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamRelationObj selectedInstance = getJavaFXFocusAsRelation();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "RELD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getRelationFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXRelationPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamRelationObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonDeleteSelected );

		}
		return( hboxMenu );
	}

	public ICFBamTableObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamTableObj value ) {
		javafxContainer = value;
	}

	public void refreshMe() {
		if( javafxRefreshCallback != null ) {
			javafxRefreshCallback.refreshMe();
		}
	}

	public void adjustListButtons() {
		boolean enableState;
		boolean inEditState;
		boolean allowAdds;
		boolean inAddMode = false;
		ICFBamRelationObj selectedObj = getJavaFXFocusAsRelation();
		CFPane.PaneMode mode = getPaneMode();
		if( mode == CFPane.PaneMode.Edit ) {
			inEditState = true;
			allowAdds = false;
		}
		else {
			inEditState = false;
			if( getJavaFXContainer() != null ) {
				if( getLeft() != null ) {
					allowAdds = false;
					inAddMode = true;
				}
				else {
					allowAdds = true;
				}
			}
			else {
				allowAdds = false;
			}
		}
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			if( ( ! inAddMode ) && ( ! inEditState ) ) {
				enableState = true;
			}
			else {
				enableState = false;
			}
		}

		if( buttonViewSelected != null ) {
			if( inEditState ) {
				buttonViewSelected.setDisable( true );
			}
			else {
				buttonViewSelected.setDisable( ! enableState );
			}
		}
		if( buttonEditSelected != null ) {
			if( inEditState ) {
				buttonEditSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() != null ) ) {
				buttonEditSelected.setDisable( true );
			}
			else {
				buttonEditSelected.setDisable( ! enableState );
			}
		}
		if( buttonDeleteSelected != null ) {
			if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() != null ) ) {
				buttonDeleteSelected.setDisable( true );
			}
			else {
				buttonDeleteSelected.setDisable( ! enableState );
			}
		}
		if( buttonAddRelation != null ) {
			buttonAddRelation.setDisable( ! allowAdds );
		}

	}
}
