// Description: Java 25 JavaFX List of Obj Pane implementation for RelationCol.

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
 *	CFBamJavaFXRelationColListPane JavaFX List of Obj Pane implementation
 *	for RelationCol.
 */
public class CFBamCustEditorRelationColListPane
extends CFBorderPane
implements ICFBamJavaFXRelationColPaneList
{
	public static String S_FormName = "List RelationCol";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamRelationColObj> javafxDataCollection = null;
	protected ObservableList<ICFBamRelationColObj> observableListOfRelationCol = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddRelationCol = null;
	protected CFButton buttonMoveUpSelected = null;
	protected CFButton buttonMoveDownSelected = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamRelationColObj> dataTable = null;
	protected TableColumn<ICFBamRelationColObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamRelationColObj, String> tableColumnName = null;
	protected TableColumn<ICFBamRelationColObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamRelationColObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamRelationColObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamRelationColObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamRelationColObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamRelationColObj, ICFBamIndexColObj> tableColumnLookupFromCol = null;
	protected TableColumn<ICFBamRelationColObj, ICFBamIndexColObj> tableColumnLookupToCol = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamRelationObj javafxContainer = null;
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


	public CFBamCustEditorRelationColListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamRelationObj argContainer,
		ICFBamRelationColObj argFocus,
		Collection<ICFBamRelationColObj> argDataCollection,
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
		dataTable = new TableView<ICFBamRelationColObj>();
		tableColumnId = new TableColumn<ICFBamRelationColObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamRelationColObj, Long> p ) {
				ICFBamRelationColObj obj = p.getValue();
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
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,Long>,TableCell<ICFBamRelationColObj,Long>>() {
			@Override public TableCell<ICFBamRelationColObj,Long> call(
				TableColumn<ICFBamRelationColObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamRelationColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamRelationColObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationColObj, String> p ) {
				ICFBamRelationColObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,String>,TableCell<ICFBamRelationColObj,String>>() {
			@Override public TableCell<ICFBamRelationColObj,String> call(
				TableColumn<ICFBamRelationColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnLookupDefSchema = new TableColumn<ICFBamRelationColObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamRelationColObj, ICFBamSchemaDefObj> p ) {
				ICFBamRelationColObj obj = p.getValue();
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
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,ICFBamSchemaDefObj>,TableCell<ICFBamRelationColObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamRelationColObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamRelationColObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationColObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnShortName = new TableColumn<ICFBamRelationColObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationColObj, String> p ) {
				ICFBamRelationColObj obj = p.getValue();
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
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,String>,TableCell<ICFBamRelationColObj,String>>() {
			@Override public TableCell<ICFBamRelationColObj,String> call(
				TableColumn<ICFBamRelationColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnDescription = new TableColumn<ICFBamRelationColObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationColObj, String> p ) {
				ICFBamRelationColObj obj = p.getValue();
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
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,String>,TableCell<ICFBamRelationColObj,String>>() {
			@Override public TableCell<ICFBamRelationColObj,String> call(
				TableColumn<ICFBamRelationColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnShortDescription = new TableColumn<ICFBamRelationColObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationColObj, String> p ) {
				ICFBamRelationColObj obj = p.getValue();
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
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,String>,TableCell<ICFBamRelationColObj,String>>() {
			@Override public TableCell<ICFBamRelationColObj,String> call(
				TableColumn<ICFBamRelationColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnLabel = new TableColumn<ICFBamRelationColObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationColObj, String> p ) {
				ICFBamRelationColObj obj = p.getValue();
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
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,String>,TableCell<ICFBamRelationColObj,String>>() {
			@Override public TableCell<ICFBamRelationColObj,String> call(
				TableColumn<ICFBamRelationColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnLookupFromCol = new TableColumn<ICFBamRelationColObj, ICFBamIndexColObj>( "From Column" );
		tableColumnLookupFromCol.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,ICFBamIndexColObj>,ObservableValue<ICFBamIndexColObj> >() {
			public ObservableValue<ICFBamIndexColObj> call( CellDataFeatures<ICFBamRelationColObj, ICFBamIndexColObj> p ) {
				ICFBamRelationColObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexColObj ref = obj.getRequiredLookupFromCol();
					ReadOnlyObjectWrapper<ICFBamIndexColObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexColObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupFromCol.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,ICFBamIndexColObj>,TableCell<ICFBamRelationColObj,ICFBamIndexColObj>>() {
			@Override public TableCell<ICFBamRelationColObj,ICFBamIndexColObj> call(
				TableColumn<ICFBamRelationColObj,ICFBamIndexColObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationColObj,ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupFromCol );
		tableColumnLookupToCol = new TableColumn<ICFBamRelationColObj, ICFBamIndexColObj>( "To Column" );
		tableColumnLookupToCol.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationColObj,ICFBamIndexColObj>,ObservableValue<ICFBamIndexColObj> >() {
			public ObservableValue<ICFBamIndexColObj> call( CellDataFeatures<ICFBamRelationColObj, ICFBamIndexColObj> p ) {
				ICFBamRelationColObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexColObj ref = obj.getRequiredLookupToCol();
					ReadOnlyObjectWrapper<ICFBamIndexColObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexColObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupToCol.setCellFactory( new Callback<TableColumn<ICFBamRelationColObj,ICFBamIndexColObj>,TableCell<ICFBamRelationColObj,ICFBamIndexColObj>>() {
			@Override public TableCell<ICFBamRelationColObj,ICFBamIndexColObj> call(
				TableColumn<ICFBamRelationColObj,ICFBamIndexColObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationColObj,ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupToCol );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamRelationColObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamRelationColObj> observable,
					ICFBamRelationColObj oldValue,
					ICFBamRelationColObj newValue )
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
		if( observableListOfRelationCol != null ) {
			dataTable.setItems( observableListOfRelationCol );
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
		if( ( value == null ) || ( value instanceof ICFBamRelationColObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamRelationColObj" );
		}
		adjustListButtons();
	}

	public ICFBamRelationColObj getJavaFXFocusAsRelationCol() {
		return( (ICFBamRelationColObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsRelationCol( ICFBamRelationColObj value ) {
		setJavaFXFocus( value );
	}

	public class RelationColByQualNameComparator
	implements Comparator<ICFBamRelationColObj>
	{
		public RelationColByQualNameComparator() {
		}

		public int compare( ICFBamRelationColObj lhs, ICFBamRelationColObj rhs ) {
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

	protected RelationColByQualNameComparator compareRelationColByQualName = new RelationColByQualNameComparator();

	public Collection<ICFBamRelationColObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamRelationColObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfRelationCol = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
			if( javafxSortByChain ) {
				Iterator<ICFBamRelationColObj> iter = javafxDataCollection.iterator();
				ICFBamRelationColObj head = null;
				while( ( head == null ) && iter.hasNext() ) {
					head = iter.next();
					if( null != head.getOptionalLookupPrev() ) {
						head = null;
					}
				}
				if( ( head == null ) && ( javafxDataCollection.size() > 0 ) ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Could not locate head of object chain" );
				}
				ICFBamRelationColObj cur = head;
				while( cur != null ) {
					observableListOfRelationCol.add( cur );
					cur = cur.getOptionalLookupNext();
				}
			}
			else {
				Iterator<ICFBamRelationColObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfRelationCol.add( iter.next() );
				}
				observableListOfRelationCol.sort( compareRelationColByQualName );
			}
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfRelationCol );
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
			buttonAddRelationCol = new CFButton();
			buttonAddRelationCol.setMinWidth( 200 );
			buttonAddRelationCol.setText( "Add RelationCol" );
			buttonAddRelationCol.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						ICFBamRelationColObj obj = (ICFBamRelationColObj)schemaObj.getRelationColTableObj().newInstance();
						ICFBamRelationColEditObj edit = (ICFBamRelationColEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
		ICFSecTenantObj secTenant = schemaObj.getSecTenant();
		edit.setRequiredOwnerTenant( secTenant );
			ICFBamRelationObj container = (ICFBamRelationObj)( getJavaFXContainer() );
			if( container == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"JavaFXContainer" );
			}
			edit.setRequiredContainerRelation( container );
						CFBorderPane frame = javafxSchema.getRelationColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFBamJavaFXRelationColPaneCommon jpanelCommon = (ICFBamJavaFXRelationColPaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonAddRelationCol );
			}
			buttonMoveUpSelected = new CFButton();
			buttonMoveUpSelected.setMinWidth( 200 );
			buttonMoveUpSelected.setText( "Move Up Selected" );
			buttonMoveUpSelected.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFBamRelationColObj selectedInstance = getJavaFXFocusAsRelationCol();
						if( selectedInstance != null ) {
							if( null != selectedInstance.getOptionalLookupPrev() ) {
								selectedInstance.moveUp();
								refreshMe();
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonMoveUpSelected );
			}

			buttonMoveDownSelected = new CFButton();
			buttonMoveDownSelected.setMinWidth( 200 );
			buttonMoveDownSelected.setText( "Move Down Selected" );
			buttonMoveDownSelected.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFBamRelationColObj selectedInstance = getJavaFXFocusAsRelationCol();
						if( selectedInstance != null ) {
							if( null != selectedInstance.getOptionalLookupNext() ) {
								selectedInstance.moveDown();
								refreshMe();
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonMoveDownSelected );
			}

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
						ICFBamRelationColObj selectedInstance = getJavaFXFocusAsRelationCol();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "RELC".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getRelationColFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXRelationColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamRelationColObj" );
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
						ICFBamRelationColObj selectedInstance = getJavaFXFocusAsRelationCol();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "RELC".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getRelationColFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXRelationColPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamRelationColObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonEditSelected );
			}

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
						ICFBamRelationColObj selectedInstance = getJavaFXFocusAsRelationCol();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "RELC".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getRelationColFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXRelationColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamRelationColObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonDeleteSelected );
			}

		}
		return( hboxMenu );
	}

	public ICFBamRelationObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamRelationObj value ) {
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
		ICFBamRelationColObj selectedObj = getJavaFXFocusAsRelationCol();
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
				else if( javafxContainer.getOptionalLookupDefSchema() != null ) {
					allowAdds = false;
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

		boolean enableMoveUp = ( ! inEditState )
			&& ( ! inAddMode )
			&& ( selectedObj != null )
			&& ( null != selectedObj.getOptionalLookupPrev() );
		boolean enableMoveDown = ( ! inEditState )
			&& ( ! inAddMode )
			&& ( selectedObj != null )
			&& ( null != selectedObj.getOptionalLookupNext() );
		if( buttonMoveUpSelected != null ) {
			if( inEditState ) {
				buttonMoveUpSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonMoveUpSelected.setDisable( ! enableMoveUp );
			}
			else {
				buttonMoveUpSelected.setDisable( true );
			}
		}
		if( buttonMoveDownSelected != null ) {
			if( inEditState ) {
				buttonMoveDownSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonMoveDownSelected.setDisable( ! enableMoveDown );
			}
			else {
				buttonMoveDownSelected.setDisable( true );
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

		if( buttonAddRelationCol != null ) {
			buttonAddRelationCol.setDisable( ! allowAdds );
		}
	}
}
