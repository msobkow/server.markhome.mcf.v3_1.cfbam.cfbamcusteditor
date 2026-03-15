// Description: Java 25 JavaFX List of Obj Pane implementation for Table.

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
 *	CFBamJavaFXTableListPane JavaFX List of Obj Pane implementation
 *	for Table.
 */
public class CFBamCustEditorTableListPane
extends CFBorderPane
implements ICFBamJavaFXTablePaneList
{
	public static String S_FormName = "List Table";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamTableObj> javafxDataCollection = null;
	protected ObservableList<ICFBamTableObj> observableListOfTable = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddTable = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamTableObj> dataTable = null;
	protected TableColumn<ICFBamTableObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnName = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnDbName = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnPageData = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnTableClassCode = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnIsInstantiable = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnHasHistory = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnHasAuditColumns = null;
	protected TableColumn<ICFBamTableObj, ICFBamSchema.LoaderBehaviourEnum> tableColumnLoaderBehaviour = null;
	protected TableColumn<ICFBamTableObj, ICFBamSchema.SecScopeEnum> tableColumnSecScope = null;
	protected TableColumn<ICFBamTableObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamTableObj, ICFBamIndexObj> tableColumnLookupLookupIndex = null;
	protected TableColumn<ICFBamTableObj, ICFBamIndexObj> tableColumnLookupAltIndex = null;
	protected TableColumn<ICFBamTableObj, ICFBamTableObj> tableColumnLookupQualTable = null;
	protected TableColumn<ICFBamTableObj, ICFBamIndexObj> tableColumnLookupPrimaryIndex = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamSchemaDefObj javafxContainer = null;
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


	public CFBamCustEditorTableListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamSchemaDefObj argContainer,
		ICFBamTableObj argFocus,
		Collection<ICFBamTableObj> argDataCollection,
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
		dataTable = new TableView<ICFBamTableObj>();
		tableColumnId = new TableColumn<ICFBamTableObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamTableObj, Long> p ) {
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
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Long>,TableCell<ICFBamTableObj,Long>>() {
			@Override public TableCell<ICFBamTableObj,Long> call(
				TableColumn<ICFBamTableObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamTableObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnLookupDefSchema = new TableColumn<ICFBamTableObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamTableObj, ICFBamSchemaDefObj> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamSchemaDefObj>,TableCell<ICFBamTableObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamTableObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamTableObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamTableObj> observable,
					ICFBamTableObj oldValue,
					ICFBamTableObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});
		tableColumnDbName = new TableColumn<ICFBamTableObj,String>( "Db Name" );
		tableColumnDbName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnDbName.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDbName );
		tableColumnShortName = new TableColumn<ICFBamTableObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnDescription = new TableColumn<ICFBamTableObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnShortDescription = new TableColumn<ICFBamTableObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnLabel = new TableColumn<ICFBamTableObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnTableClassCode = new TableColumn<ICFBamTableObj,String>( "Table Class Code" );
		tableColumnTableClassCode.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredTableClassCode();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnTableClassCode.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnTableClassCode );
		tableColumnIsInstantiable = new TableColumn<ICFBamTableObj,Boolean>( "Is Instantiable" );
		tableColumnIsInstantiable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsInstantiable();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsInstantiable.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsInstantiable );
		tableColumnHasHistory = new TableColumn<ICFBamTableObj,Boolean>( "Has History" );
		tableColumnHasHistory.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredHasHistory();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnHasHistory.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnHasHistory );
		tableColumnHasAuditColumns = new TableColumn<ICFBamTableObj,Boolean>( "Has Audit Columns" );
		tableColumnHasAuditColumns.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredHasAuditColumns();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnHasAuditColumns.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnHasAuditColumns );
		tableColumnLoaderBehaviour = new TableColumn<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>( "Loader Behaviour" );
		tableColumnLoaderBehaviour.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>,ObservableValue<ICFBamSchema.LoaderBehaviourEnum> >() {
			public ObservableValue<ICFBamSchema.LoaderBehaviourEnum> call( CellDataFeatures<ICFBamTableObj, ICFBamSchema.LoaderBehaviourEnum> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchema.LoaderBehaviourEnum value = obj.getRequiredLoaderBehaviour();
					ReadOnlyObjectWrapper<ICFBamSchema.LoaderBehaviourEnum> observable = new ReadOnlyObjectWrapper<ICFBamSchema.LoaderBehaviourEnum>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLoaderBehaviour.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>,TableCell<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum> call(
				TableColumn<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum> arg)
			{
				return new CFEnumTableCell<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>();
			}
		});
		dataTable.getColumns().add( tableColumnLoaderBehaviour );
		tableColumnPageData = new TableColumn<ICFBamTableObj,Boolean>( "Page Data" );
		tableColumnPageData.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredPageData();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnPageData.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPageData );
		tableColumnLookupLookupIndex = new TableColumn<ICFBamTableObj, ICFBamIndexObj>( "Lookup Index" );
		tableColumnLookupLookupIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamTableObj, ICFBamIndexObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getOptionalLookupLookupIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupLookupIndex.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamIndexObj>,TableCell<ICFBamTableObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamIndexObj> call(
				TableColumn<ICFBamTableObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupLookupIndex );
		tableColumnLookupAltIndex = new TableColumn<ICFBamTableObj, ICFBamIndexObj>( "Alt Index" );
		tableColumnLookupAltIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamTableObj, ICFBamIndexObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getOptionalLookupAltIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupAltIndex.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamIndexObj>,TableCell<ICFBamTableObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamIndexObj> call(
				TableColumn<ICFBamTableObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupAltIndex );
		tableColumnLookupQualTable = new TableColumn<ICFBamTableObj, ICFBamTableObj>( "Qualifying Table" );
		tableColumnLookupQualTable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamTableObj>,ObservableValue<ICFBamTableObj> >() {
			public ObservableValue<ICFBamTableObj> call( CellDataFeatures<ICFBamTableObj, ICFBamTableObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamTableObj ref = obj.getOptionalLookupQualTable();
					ReadOnlyObjectWrapper<ICFBamTableObj> observable = new ReadOnlyObjectWrapper<ICFBamTableObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupQualTable.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamTableObj>,TableCell<ICFBamTableObj,ICFBamTableObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamTableObj> call(
				TableColumn<ICFBamTableObj,ICFBamTableObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupQualTable );
		tableColumnLookupPrimaryIndex = new TableColumn<ICFBamTableObj, ICFBamIndexObj>( "Primary Index" );
		tableColumnLookupPrimaryIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamTableObj, ICFBamIndexObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getOptionalLookupPrimaryIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupPrimaryIndex.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamIndexObj>,TableCell<ICFBamTableObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamIndexObj> call(
				TableColumn<ICFBamTableObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupPrimaryIndex );
		tableColumnSecScope = new TableColumn<ICFBamTableObj,ICFBamSchema.SecScopeEnum>( "Sec Scope" );
		tableColumnSecScope.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamSchema.SecScopeEnum>,ObservableValue<ICFBamSchema.SecScopeEnum> >() {
			public ObservableValue<ICFBamSchema.SecScopeEnum> call( CellDataFeatures<ICFBamTableObj, ICFBamSchema.SecScopeEnum> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchema.SecScopeEnum value = obj.getRequiredSecScope();
					ReadOnlyObjectWrapper<ICFBamSchema.SecScopeEnum> observable = new ReadOnlyObjectWrapper<ICFBamSchema.SecScopeEnum>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecScope.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamSchema.SecScopeEnum>,TableCell<ICFBamTableObj,ICFBamSchema.SecScopeEnum>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamSchema.SecScopeEnum> call(
				TableColumn<ICFBamTableObj,ICFBamSchema.SecScopeEnum> arg)
			{
				return new CFEnumTableCell<ICFBamTableObj,ICFBamSchema.SecScopeEnum>();
			}
		});
		dataTable.getColumns().add( tableColumnSecScope );

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getPanelHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );
		javafxIsInitializing = false;
		if( observableListOfTable != null ) {
			dataTable.setItems( observableListOfTable );
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
		if( ( value == null ) || ( value instanceof ICFBamTableObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamTableObj" );
		}
		adjustListButtons();
	}

	public ICFBamTableObj getJavaFXFocusAsTable() {
		return( (ICFBamTableObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTable( ICFBamTableObj value ) {
		setJavaFXFocus( value );
	}

	public class TableByQualNameComparator
	implements Comparator<ICFBamTableObj>
	{
		public TableByQualNameComparator() {
		}

		public int compare( ICFBamTableObj lhs, ICFBamTableObj rhs ) {
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

	protected TableByQualNameComparator compareTableByQualName = new TableByQualNameComparator();

	public Collection<ICFBamTableObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamTableObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfTable = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFBamTableObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfTable.add( iter.next() );
				}
				observableListOfTable.sort( compareTableByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfTable );
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
			buttonAddTable = new CFButton();
			buttonAddTable.setMinWidth( 200 );
			buttonAddTable.setText( "Add Table" );
			buttonAddTable.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						ICFBamTableObj obj = (ICFBamTableObj)schemaObj.getTableTableObj().newInstance();
						ICFBamTableEditObj edit = (ICFBamTableEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
						CFBorderPane frame = javafxSchema.getTableFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFBamJavaFXTablePaneCommon jpanelCommon = (ICFBamJavaFXTablePaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddTable );
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
						ICFBamTableObj selectedInstance = getJavaFXFocusAsTable();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "TBLD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getTableFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTablePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamTableObj" );
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
						ICFBamTableObj selectedInstance = getJavaFXFocusAsTable();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "TBLD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getTableFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTablePaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamTableObj" );
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
						ICFBamTableObj selectedInstance = getJavaFXFocusAsTable();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "TBLD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getTableFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXTablePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamTableObj" );
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

	public ICFBamSchemaDefObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamSchemaDefObj value ) {
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
		ICFBamTableObj selectedObj = getJavaFXFocusAsTable();
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
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonEditSelected.setDisable( ! enableState );
			}
			else {
				buttonEditSelected.setDisable( true );
			}
		}
		if( buttonDeleteSelected != null ) {
			if( inEditState ) {
				buttonDeleteSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonDeleteSelected.setDisable( ! enableState );
			}
			else {
				buttonDeleteSelected.setDisable( true );
			}
		}

		if( buttonAddTable != null ) {
			buttonAddTable.setDisable( ! allowAdds );
		}
	}
}
